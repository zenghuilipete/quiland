//
//  UdpCmdCodec.h
//  UdpCmd
//
//  Created by feuyeux@gmail.com on 14-2-13.
//  Copyright (c) 2014年 feuyeux@gmail.com. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "UdpCommand.h"
#import "ControlInfoCodecFactory.h"

@interface UdpCmdCodec : NSObject
+ (NSData *)  encode:(UdpCommand *) udpCommand;
+(UdpCommand *) decode:(NSData *) data;

@end
