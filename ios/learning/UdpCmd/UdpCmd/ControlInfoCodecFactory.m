//
//  ControlInfoCodecFactory.m
//  UdpCmd
//
//  Created by feuyeux@gmail.com on 14-2-13.
//  Copyright (c) 2014年 feuyeux@gmail.com. All rights reserved.
//

#import "ControlInfoCodecFactory.h"

@implementation ControlInfoCodecFactory
+ (id<ControlInfoCodec>) getInstance:(CommandType)commandType{
    switch (commandType){
        case KEY:
            return [[KeyControlInfoCodec  alloc]  init];
        case Mouse:
            return NULL;
    }
    return NULL;
}

@end
