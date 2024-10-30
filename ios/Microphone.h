
#ifdef RCT_NEW_ARCH_ENABLED
#import "RNMicrophoneSpec.h"

@interface Microphone : NSObject <NativeMicrophoneSpec>
#else
#import <React/RCTBridgeModule.h>

@interface Microphone : NSObject <RCTBridgeModule>
#endif

@end
