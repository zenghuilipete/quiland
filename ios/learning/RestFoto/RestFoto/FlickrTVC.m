//
//  FlickrTVC.m
//  RestFoto
//
//  Created by feuyeux@gmail.com on 14-1-22.
//  Copyright (c) 2014年 feuyeux@gmail.com. All rights reserved.
//

#import "FlickrTVC.h"
#import "FlickrFetcher.h"
#import "ImageVC.h"

@interface FlickrTVC ()

@end

@implementation FlickrTVC

- (void)setPhotos:(NSArray *)photos
{
	_photos = photos;
	[self.tableView reloadData];
}

- (id)initWithStyle:(UITableViewStyle)style
{
	self = [super initWithStyle:style];
	if (self)
	{
		[self fetchPhotos];
	}
	return self;
}

- (void)viewDidLoad
{
	[super viewDidLoad];
	[self fetchPhotos];
}

- (void)didReceiveMemoryWarning
{
	[super didReceiveMemoryWarning];
	// Dispose of any resources that can be recreated.
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
	return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
	return [self.photos count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
	static NSString *CellIdentifier = @"Flickr Photo Cell";             // Flickr Photo Cell
	UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier forIndexPath:indexPath];

	NSDictionary *photo = self.photos[indexPath.row];

	cell.textLabel.text = [photo valueForKeyPath:FLICKR_PHOTO_TITLE];
	cell.detailTextLabel.text = [photo valueForKeyPath:FLICKR_PHOTO_DESCRIPTION];
	return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
	// get the Detail view controller in our UISplitViewController (nil if not in one)
	id detail = self.splitViewController.viewControllers[1];

	// if Detail is a UINavigationController, look at its root view controller to find it
	if ([detail isKindOfClass:[UINavigationController class]])
	{
		detail = [((UINavigationController *)detail).viewControllers firstObject];
	}
	if ([detail isKindOfClass:[ImageVC class]])
	{
		[self prepareImageViewController:detail toDisplayPhoto:self.photos[indexPath.row]];
	}
}

/*
 * #pragma mark - Navigation
 *
 * // In a story board-based application, you will often want to do a little preparation before navigation
 * - (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
 * {
 *  // Get the new view controller using [segue destinationViewController].
 *  // Pass the selected object to the new view controller.
 * }
 *
 */
- (void)fetchPhotos0
{
	NSURL *url = [FlickrFetcher URLforRecentGeoreferencedPhotos];
	NSData *jsonResults = [NSData dataWithContentsOfURL:url];
	NSError *error = nil;
	NSDictionary *propertyListResults = [NSJSONSerialization JSONObjectWithData:jsonResults options:0 error:&error];

	NSLog(@"Flickr result= %@", propertyListResults);

	NSArray *photos = [propertyListResults valueForKeyPath:FLICKR_RESULTS_PHOTOS];
	self.photos = photos;
}

- (IBAction)fetchPhotos
{
	[self.refreshControl beginRefreshing]; // start the spinner
	NSURL *url = [FlickrFetcher URLforRecentGeoreferencedPhotos];
	// create a (non-main) queue to do fetch on
	dispatch_queue_t fetchQ = dispatch_queue_create("flickr fetcher", NULL);
	// put a block to do the fetch onto that queue
	dispatch_async(fetchQ, ^{
	                                       // fetch the JSON data from Flickr
					   NSData *jsonResults = [NSData dataWithContentsOfURL:url];
	                                       // convert it to a Property List (NSArray and NSDictionary)
					   NSDictionary *propertyListResults = [NSJSONSerialization JSONObjectWithData:jsonResults
																						   options:0
																							 error:NULL];
					   NSLog(@"foto json: %@", propertyListResults);
        
	                                       // get the NSArray of photo NSDictionarys out of the results
					   NSArray *photos = [propertyListResults valueForKeyPath:FLICKR_RESULTS_PHOTOS];
	                                       // update the Model (and thus our UI), but do so back on the main queue
					   dispatch_async(dispatch_get_main_queue(), ^{
										  [self.refreshControl endRefreshing];                                         // stop the spinner
										  self.photos = photos;
									  });
				   });
}

- (void)prepareImageViewController:(ImageVC *)ivc toDisplayPhoto:(NSDictionary *)photo
{
	ivc.imageURL = [FlickrFetcher URLforPhoto:photo format:FlickrPhotoFormatLarge];
	ivc.title = [photo valueForKeyPath:FLICKR_PHOTO_TITLE];
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
	// Get the new view controller using [segue destinationViewController].
	// Pass the selected object to the new view controller.

	if ([sender isKindOfClass:[UITableViewCell class]])
	{
		// find out which row in which section we're seguing from
		NSIndexPath *indexPath = [self.tableView indexPathForCell:sender];
		if (indexPath)
		{
			// found it ... are we doing the Display Photo segue?
			if ([segue.identifier isEqualToString:@"Show Photo"])
			{
				// yes ... is the destination an ImageViewController?
				if ([segue.destinationViewController isKindOfClass:[ImageVC class]])
				{
					// yes ... then we know how to prepare for that segue!
					[self prepareImageViewController:segue.destinationViewController
										 toDisplayPhoto:self.photos[indexPath.row]];
				}
			}
		}
	}
}

@end