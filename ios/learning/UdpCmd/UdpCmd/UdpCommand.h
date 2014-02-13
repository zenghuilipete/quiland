//
//  UdpCommand.h
//  UdpCmd
//
//  Created by feuyeux@gmail.com on 14-2-13.
//  Copyright (c) 2014年 feuyeux@gmail.com. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ControlInfo.h"

enum InputType { KEY=0, Mouse=1};
typedef enum InputType CommandType;

@interface UdpCommand : NSObject
   @property(nonatomic) CommandType commandType;
   @property  ControlInfo *controlInfo;
-(NSString*) toString;
@end
