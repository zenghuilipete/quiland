//
//  KeyControlInfoCodec.m
//  UdpCmd
//
//  Created by feuyeux@gmail.com on 14-2-13.
//  Copyright (c) 2014年 feuyeux@gmail.com. All rights reserved.
//

#import "KeyControlInfoCodec.h"
#import "KeyControlInfo.h"

@implementation KeyControlInfoCodec

-(NSData *) encode:( ControlInfo  *)controlInfo{
    if ([controlInfo isKindOfClass:[KeyControlInfo class]])
    {
        KeyControlInfo *keyControlInfo = (KeyControlInfo *)controlInfo;
        NSData *data= [keyControlInfo.keyPress dataUsingEncoding:NSUTF8StringEncoding];
        return data;
    }else{
        return NULL;
    }
}

-(ControlInfo *) decode:(NSData *)data{
    NSString* keyPress = [NSString stringWithUTF8String:[data bytes]];
    KeyControlInfo *keyControlInfo = [[KeyControlInfo alloc] initWithKeyPress:keyPress];
    return keyControlInfo;
}

@end
