//
//  Controller.h
//  UdpCmd
//
//  Created by feuyeux@gmail.com on 14-2-13.
//  Copyright (c) 2014年 feuyeux@gmail.com. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ControlInfo.h"

@protocol Controller <NSObject>

-(void) process:(ControlInfo *) controlInfo;

@end
