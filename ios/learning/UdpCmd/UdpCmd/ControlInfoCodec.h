//
//  ControlInfoCodec.h
//  UdpCmd
//
//  Created by feuyeux@gmail.com on 14-2-13.
//  Copyright (c) 2014年 feuyeux@gmail.com. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ControlInfo.h"

@protocol ControlInfoCodec <NSObject>

-(NSData *) encode:( ControlInfo  *)controlInfo;
-( ControlInfo  *) decode:(NSData *)data;
@end
