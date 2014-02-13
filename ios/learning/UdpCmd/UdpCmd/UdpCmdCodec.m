//
//  UdpCmdCodec.m
//  UdpCmd
//
//  Created by feuyeux@gmail.com on 14-2-13.
//  Copyright (c) 2014年 feuyeux@gmail.com. All rights reserved.
//

#import "UdpCmdCodec.h"


@implementation UdpCmdCodec

+ (NSData *)  encode:(UdpCommand*) udpCommand
{
    NSMutableData *data=[[NSMutableData alloc] init];
    CommandType commandType = udpCommand.commandType;
    [data appendBytes: &commandType length:sizeof(commandType)];
    [data appendData: [[ControlInfoCodecFactory getInstance:commandType] encode:udpCommand.controlInfo]];
    return data;
}

+(UdpCommand *) decode:(NSData *) data
{    
    CommandType commandType;
    [data getBytes:&commandType length:sizeof(commandType)];
    NSLog(@"%@",[UdpCmdCodec convertToString:commandType]);

    NSInteger begin = sizeof(commandType);
    NSInteger len =[data length]-begin;
    
    ControlInfo *controlInfo = [[ControlInfoCodecFactory getInstance:commandType] decode:[data subdataWithRange:NSMakeRange(begin, len)]];
    UdpCommand *cmd=[[UdpCommand alloc] init];
    [cmd setControlInfo:controlInfo];
    [cmd setCommandType: commandType];
    return cmd;
}

+ (NSString*) convertToString:(CommandType) commandType {
    NSString *result = nil;
    switch(commandType) {
        case KEY:
            result = @"KEY";
            break;
        case Mouse:
            result = @"MOUSE";
            break;
        default:
            result = @"unknown";
    }
    return result;
}

@end
