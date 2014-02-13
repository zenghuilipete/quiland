//
//  KeyController.m
//  UdpCmd
//
//  Created by feuyeux@gmail.com on 14-2-13.
//  Copyright (c) 2014年 feuyeux@gmail.com. All rights reserved.
//

#import "KeyController.h"
#import "KeyControlInfo.h"

@implementation KeyController

-(void) process:(ControlInfo *) controlInfo{
    if ([controlInfo isKindOfClass:[KeyControlInfo class]]){
        KeyControlInfo *keyControlInfo = (KeyControlInfo *)controlInfo;
        NSLog(@"processing %@",keyControlInfo.keyPress);
    }
}
@end
