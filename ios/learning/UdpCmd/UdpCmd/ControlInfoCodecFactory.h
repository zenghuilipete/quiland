//
//  ControlInfoCodecFactory.h
//  UdpCmd
//
//  Created by feuyeux@gmail.com on 14-2-13.
//  Copyright (c) 2014年 feuyeux@gmail.com. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "UdpCommand.h"
#import "ControlInfoCodec.h"
#import "KeyControlInfoCodec.h"

@interface ControlInfoCodecFactory : NSObject
+ (id<ControlInfoCodec>) getInstance:(CommandType)commandType;

@end
