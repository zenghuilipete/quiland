//
//  UdpCmdFlipsideViewController.h
//  UdpCmd
//
//  Created by feuyeux@gmail.com on 14-2-12.
//  Copyright (c) 2014年 feuyeux@gmail.com. All rights reserved.
//

#import <UIKit/UIKit.h>

@class UdpCmdFlipsideViewController;

@protocol UdpCmdFlipsideViewControllerDelegate
- (void)flipsideViewControllerDidFinish:(UdpCmdFlipsideViewController *)controller;
@end

@interface UdpCmdFlipsideViewController : UIViewController

@property (weak, nonatomic) id <UdpCmdFlipsideViewControllerDelegate> delegate;

- (IBAction)done:(id)sender;

@end
