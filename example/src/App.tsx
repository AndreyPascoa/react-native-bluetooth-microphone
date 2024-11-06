import React, { useState, useEffect } from 'react';
import { StyleSheet, View, Text, Button, PermissionsAndroid, Platform, Alert, Linking } from 'react-native';
import { startBluetoothSco, stopBluetoothSco } from 'react-native-microphone';
import AudioRecorderPlayer from 'react-native-audio-recorder-player';

const audioRecorderPlayer = new AudioRecorderPlayer();

export default function App() {
  const [result, setResult] = useState<string | undefined>();
  const [error, setError] = useState<string | undefined>();
  const [recording, setRecording] = useState<boolean>(false);

  useEffect(() => {
    console.log("App loaded - attempting to start Bluetooth SCO...");
    startBluetoothSco()
      .then((message) => {
        console.log("Bluetooth SCO started successfully:", message);
        setResult(message);
      })
      .catch((err) => {
        console.error("Error starting Bluetooth SCO:", err.message);
        setError(err.message);
      });
  }, []);

  const handleStartBluetoothSco = () => {
    console.log("Start Bluetooth SCO button clicked...");
    startBluetoothSco()
      .then((message) => {
        console.log("Bluetooth SCO started successfully:", message);
        setResult(message);
      })
      .catch((err) => {
        console.error("Error starting Bluetooth SCO:", err.message);
        setError(err.message);
      });
  };

  const handleStopBluetoothSco = () => {
    console.log("Stop Bluetooth SCO button clicked...");
    stopBluetoothSco()
      .then((message) => {
        console.log("Bluetooth SCO stopped successfully:", message);
        setResult(message);
      })
      .catch((err) => {
        console.error("Error stopping Bluetooth SCO:", err.message);
        setError(err.message);
      });
  };

  const requestPermissions = async () => {
    if (Platform.OS === 'android') {
      try {
        console.log("Solicitando permissões para gravação de áudio e armazenamento...");
        const granted = await PermissionsAndroid.requestMultiple([
          PermissionsAndroid.PERMISSIONS.RECORD_AUDIO,
          PermissionsAndroid.PERMISSIONS.WRITE_EXTERNAL_STORAGE,
          PermissionsAndroid.PERMISSIONS.READ_EXTERNAL_STORAGE,
        ]);

        console.log("Resultado da solicitação de permissões:", granted);

        // Verifica se o usuário marcou "never_ask_again"
        if (granted['android.permission.RECORD_AUDIO'] === PermissionsAndroid.RESULTS.GRANTED &&
            granted['android.permission.WRITE_EXTERNAL_STORAGE'] === PermissionsAndroid.RESULTS.GRANTED &&
            granted['android.permission.READ_EXTERNAL_STORAGE'] === PermissionsAndroid.RESULTS.GRANTED) {
          console.log("Todas as permissões foram concedidas.");
          return true;
        } else if (
          granted['android.permission.WRITE_EXTERNAL_STORAGE'] === PermissionsAndroid.RESULTS.NEVER_ASK_AGAIN ||
          granted['android.permission.READ_EXTERNAL_STORAGE'] === PermissionsAndroid.RESULTS.NEVER_ASK_AGAIN
        ) {
          console.warn("Permissões foram negadas permanentemente (never_ask_again). Redirecionando para as configurações.");
          Alert.alert(
            "Permissões Necessárias",
            "As permissões de armazenamento são necessárias para gravar o áudio. Por favor, vá até as configurações do aplicativo e ative manualmente as permissões.",
            [
              {
                text: "Cancelar",
                style: "cancel"
              },
              {
                text: "Abrir Configurações",
                onPress: () => Linking.openSettings()
              }
            ],
            { cancelable: true }
          );
          return false;
        } else {
          console.warn('Permissões necessárias não foram concedidas');
          Alert.alert('Permissões necessárias não foram concedidas');
          return false;
        }
      } catch (err) {
        console.error('Erro ao solicitar permissões:', err);
        return false;
      }
    } else {
      console.log("iOS detectado, permissões já configuradas no Info.plist.");
      return true;
    }
  };

  const startRecording = async () => {
    console.log("Iniciando processo de gravação...");
    const permissionGranted = await requestPermissions();
    if (!permissionGranted) {
      setError('Permissões necessárias para gravação de áudio não foram concedidas.');
      console.error("Permissões para gravação de áudio não foram concedidas.");
      return;
    }

    const path = Platform.select({
      android: 'sdcard/testAudio.mp4',
      ios: 'testAudio.m4a',
    });

    console.log("Caminho do arquivo de gravação:", path);

    try {
      await audioRecorderPlayer.startRecorder(path);
      setRecording(true);
      console.log("Gravação iniciada com sucesso.");
    } catch (error) {
      setError('Erro ao iniciar gravação de áudio.');
      console.error("Erro ao iniciar gravação de áudio:", error);
    }
  };

  const stopRecording = async () => {
    console.log("Parando gravação...");
    try {
      const result = await audioRecorderPlayer.stopRecorder();
      setRecording(false);
      console.log("Gravação parada com sucesso. Arquivo:", result);
    } catch (error) {
      setError('Erro ao parar gravação de áudio.');
      console.error("Erro ao parar gravação de áudio:", error);
    }
  };

  return (
    <View style={styles.container}>
      <Text>Bluetooth SCO Result: {result}</Text>
      {error && <Text style={styles.errorText}>Error: {error}</Text>}

      <Button title="Start Bluetooth SCO" onPress={handleStartBluetoothSco} />
      <Button title="Stop Bluetooth SCO" onPress={handleStopBluetoothSco} />
      <Button title={recording ? "Stop Recording" : "Start Recording"} onPress={recording ? stopRecording : startRecording} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  errorText: {
    color: 'red',
    marginTop: 10,
  },
});
