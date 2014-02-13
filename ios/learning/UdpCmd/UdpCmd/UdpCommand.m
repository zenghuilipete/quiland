//
//  UdpCommand.m
//  UdpCmd
//
//  Created by feuyeux@gmail.com on 14-2-13.
//  Copyright (c) 2014年 feuyeux@gmail.com. All rights reserved.
//

#import "UdpCommand.h"

@implementation UdpCommand
-(NSString*) toString{
    NSString *result = [NSString stringWithFormat:@"%d-%@",_commandType,[_controlInfo toString]];
    return result;
}
@end
