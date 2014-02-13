//
//  KeyControlInfo.m
//  UdpCmd
//
//  Created by feuyeux@gmail.com on 14-2-13.
//  Copyright (c) 2014年 feuyeux@gmail.com. All rights reserved.
//

#import "KeyControlInfo.h"

@implementation KeyControlInfo

-(NSString*) toString{
    return _keyPress;
}

-(KeyControlInfo *)initWithKeyPress:(NSString*)keyPress
{
    self=[super init];
    [self setKeyPress:keyPress];
    return self;
}
@end
