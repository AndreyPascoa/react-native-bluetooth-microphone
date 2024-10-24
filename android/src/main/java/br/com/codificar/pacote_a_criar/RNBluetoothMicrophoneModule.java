
package br.com.codificar.pacote_a_criar;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

public class RNBluetoothMicrophoneModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNBluetoothMicrophoneModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNBluetoothMicrophone";
  }
}