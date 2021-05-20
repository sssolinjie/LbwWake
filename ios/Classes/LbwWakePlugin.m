#import "LbwWakePlugin.h"
@implementation LbwWakePlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  FlutterMethodChannel* channel = [FlutterMethodChannel
      methodChannelWithName:@"lbwWake"
            binaryMessenger:[registrar messenger]];
  LbwWakePlugin* instance = [[LbwWakePlugin alloc] init];
  [registrar addMethodCallDelegate:instance channel:channel];
    
    
//    NSString* licensePath = [[NSBundle mainBundle] pathForResource:FACE_LICENSE_NAME ofType:FACE_LICENSE_SUFFIX];
//       NSAssert([[NSFileManager defaultManager] fileExistsAtPath:licensePath], @"license文件路径不对，请仔细查看文档");
//       [[FaceSDKManager sharedInstance] setLicenseID:FACE_LICENSE_ID andLocalLicenceFile:licensePath andRemoteAuthorize:true];
}

- (void)handleMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result {
  if ([@"getPlatformVersion" isEqualToString:call.method]) {
    result([@"iOS " stringByAppendingString:[[UIDevice currentDevice] systemVersion]]);
  } else if ([@"initsdk" isEqualToString:call.method]) {
      //NSLog(@"TTS该插件不支持ios");
      
  }else {
    result(FlutterMethodNotImplemented);
  }
}
@end
