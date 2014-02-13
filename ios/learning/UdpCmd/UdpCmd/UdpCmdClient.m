//
//  UdpCmdClient.m
//  UdpCmd
//
//  Created by feuyeux@gmail.com on 14-2-12.
//  Copyright (c) 2014年 feuyeux@gmail.com. All rights reserved.
//

#import "UdpCmdClient.h"
#import "UdpCommand.h"

@interface UdpCmdClient ()

@end

@implementation UdpCmdClient

- (UdpCmdClient *)initSocket:(UInt16)port{
    AsyncUdpSocket *tempSocket=[[AsyncUdpSocket alloc] initIPv4];
    self.socket = tempSocket;
    [self.socket setDelegate:self];
    tempSocket = nil;

    NSError *error = nil;
	[self.socket bindToPort:port error:&error];
    [self.socket enableBroadcast:YES error:&error];
    //[self.socket joinMulticastGroup:@"127.0.0.1" error:&error];
	[self.socket receiveWithTimeout:-1 tag:0];
    return self;
}

- (void)broadcast:(NSString *)host port: (UInt16)port command:(UdpCommand *) cmd{
    [self.socket sendData:[UdpCmdCodec encode:cmd]
                   toHost:host
                     port:port
              withTimeout:-1
                      tag:0];
}
- (BOOL)onUdpSocket:(AsyncUdpSocket *)sock didReceiveData:(NSData *)data withTag:(long)tag fromHost:(NSString *)host port:(UInt16)port {
    NSLog(@"sending host: %@ %d",host,port);
    [self.socket receiveWithTimeout:-1 tag:0];
    UdpCommand *cmd = [UdpCmdCodec decode:data];
    
    NSLog(@"receiving data: %@",[cmd toString]);
    return YES;
}

-(void)onUdpSocket:(AsyncUdpSocket *)sock didSendDataWithTag:(long)tag{
    NSLog(@"didSendDataWithTag: Message send success!");
}

-(void)onUdpSocket:(AsyncUdpSocket *)sock didNotReceiveDataWithTag:(long)tag dueToError:(NSError *)error{
    NSLog(@"Message not received for error: %@", error);
}

-(void)onUdpSocket:(AsyncUdpSocket *)sock didNotSendDataWithTag:(long)tag dueToError:(NSError *)error{
    NSLog(@"Message not send for error: %@",error);
}

-(void)onUdpSocketDidClose:(AsyncUdpSocket *)sock{
    NSLog(@"socket closed!");
}

@end
