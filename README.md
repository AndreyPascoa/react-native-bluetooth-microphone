
# react-native-bluetooth-microphone

## Getting started

`$ npm install react-native-bluetooth-microphone --save`

### Mostly automatic installation

`$ react-native link react-native-bluetooth-microphone`

### Manual installation


#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import br.com.codificar.pacote_a_criar.RNBluetoothMicrophonePackage;` to the imports at the top of the file
  - Add `new RNBluetoothMicrophonePackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-bluetooth-microphone'
  	project(':react-native-bluetooth-microphone').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-bluetooth-microphone/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-bluetooth-microphone')
  	```


## Usage
```javascript
import RNBluetoothMicrophone from 'react-native-bluetooth-microphone';

// TODO: What to do with the module?
RNBluetoothMicrophone;
```
  