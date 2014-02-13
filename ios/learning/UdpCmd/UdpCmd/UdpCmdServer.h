//
//  UdpCmdServer.h
//  UdpCmd
//
//  Created by feuyeux@gmail.com on 14-2-12.
//  Copyright (c) 2014年 feuyeux@gmail.com. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "AsyncUdpSocket.h"
#import "AsyncSocket.h"

@interface UdpCmdServer :NSObject<AsyncSocketDelegate>
@property (nonatomic, strong) AsyncUdpSocket *socket;
@end
