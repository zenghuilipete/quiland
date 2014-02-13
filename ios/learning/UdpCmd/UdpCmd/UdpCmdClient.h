//
//  UdpCmdClient.h
//  UdpCmd
//
//  Created by feuyeux@gmail.com on 14-2-12.
//  Copyright (c) 2014年 feuyeux@gmail.com. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "AsyncUdpSocket.h"
#import "AsyncSocket.h"
#import "UdpCmdCodec.h"

@interface UdpCmdClient:NSObject<AsyncSocketDelegate>
@property (nonatomic, strong) AsyncUdpSocket *socket;
- (UdpCmdClient *)initSocket:(UInt16)port;
- (void)broadcast:(NSString *)host port: (UInt16)port command:(UdpCommand *) cmd;

@end
