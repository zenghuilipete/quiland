//
//  UdpCmdMainViewController.m
//  UdpCmd
//
//  Created by feuyeux@gmail.com on 14-2-12.
//  Copyright (c) 2014年 feuyeux@gmail.com. All rights reserved.
//

#import "UdpCmdMainViewController.h"
#import "UdpCmdClient.h"
#import "UdpCommand.h"
#import "KeyControlInfo.h"

@interface UdpCmdMainViewController ()

@end

@implementation UdpCmdMainViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    UdpCmdClient  *client = [[UdpCmdClient alloc] initSocket:9876];
    UdpCommand *cmd=[UdpCommand alloc];
    [cmd setCommandType:KEY];
    KeyControlInfo *keyControlInfo = [[KeyControlInfo alloc] initWithKeyPress:@"MAC_KEY_13_中文"];
    [cmd setControlInfo:keyControlInfo];
    [client broadcast:@"127.0.0.1" port:9876 command:cmd];
}
@end