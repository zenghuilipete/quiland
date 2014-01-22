//
//  RestFotoViewController.m
//  RestFoto
//
//  Created by feuyeux@gmail.com on 14-1-22.
//  Copyright (c) 2014年 feuyeux@gmail.com. All rights reserved.
//

#import "RestFotoViewController.h"

@interface RestFotoViewController ()
@property (weak, nonatomic) IBOutlet UILabel *timeLabel;

@end

@implementation RestFotoViewController

- (void)viewDidLoad
{
	[super viewDidLoad];
	[self renderTime];
}

- (void)didReceiveMemoryWarning
{
	[super didReceiveMemoryWarning];
	// Dispose of any resources that can be recreated.
}

- (IBAction)timeButton:(id)sender
{
	[self renderTime];
}

-(void) renderTime
{
    self.timeLabel.text = [@"Hello iOS7. " stringByAppendingString :[self showTime]];
}

- (NSString *)showTime
{
	NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
	[dateFormatter setDateFormat:@"YY/MM/dd hh:mm:ss Z"];
	NSString *now = [dateFormatter stringFromDate:[NSDate date]];
	NSLog(@"dateString = %@", now);
	return now;
}

@end