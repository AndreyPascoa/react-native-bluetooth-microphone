<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>

  <h1>react-native-microphone</h1>
  <p>Uma biblioteca React Native para ativar e desativar o Bluetooth SCO, permitindo o uso de áudio via fones de ouvido Bluetooth, com suporte para gravação de áudio.</p>

<h2>Instalação</h2>
  <p>Para instalar a biblioteca, execute o seguinte comando:</p>

  <pre><code>npm install react-native-microphone</code></pre>

<h3>Configuração Adicional para Android</h3>
  <p>Caso você esteja usando Android, certifique-se de que as permissões adequadas estão definidas no arquivo <code>AndroidManifest.xml</code>. Adicione as seguintes permissões:</p>

  <pre><code>
  &lt;uses-permission android:name="android.permission.BLUETOOTH" /&gt;
  &lt;uses-permission android:name="android.permission.BLUETOOTH_ADMIN" /&gt;
  &lt;uses-permission android:name="android.permission.BLUETOOTH_CONNECT" /&gt;
  &lt;uses-permission android:name="android.permission.RECORD_AUDIO" /&gt;
  &lt;uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" /&gt;
  &lt;uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" /&gt;
  &lt;uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS" /&gt;
  </code></pre>

  <p>Se você estiver desenvolvendo para Android 6.0 (API 23) ou superior, não se esqueça de solicitar permissões em tempo de execução, como mostrado no exemplo de uso abaixo.</p>

<h2>Uso</h2>
<h3>Função principal: Ativando e Desativando o Bluetooth SCO</h3>
  <p>A biblioteca fornece métodos para ativar e desativar o Bluetooth SCO, que permite a captura de áudio via fones de ouvido Bluetooth.</p>

<h4>Exemplo de uso:</h4>

  <pre><code>
  import React, { useState } from 'react';
  import { View, Text, Button, PermissionsAndroid, Platform } from 'react-native';
  import { startBluetoothSco, stopBluetoothSco } from 'react-native-microphone';

  const App = () => {
    const [scoStatus, setScoStatus] = useState('');

    // Função para solicitar permissões no Android
    const requestPermissions = async () => {
      if (Platform.OS === 'android') {
        await PermissionsAndroid.requestMultiple([
          PermissionsAndroid.PERMISSIONS.BLUETOOTH_CONNECT,
          PermissionsAndroid.PERMISSIONS.RECORD_AUDIO,
        ]);
      }
    };

    const handleStartSCO = async () => {
      try {
        await requestPermissions();
        const result = await startBluetoothSco();
        setScoStatus(result);
      } catch (error) {
        console.error('Erro ao ativar Bluetooth SCO:', error);
      }
    };

    const handleStopSCO = async () => {
      try {
        const result = await stopBluetoothSco();
        setScoStatus(result);
      } catch (error) {
        console.error('Erro ao desativar Bluetooth SCO:', error);
      }
    };

    return (
      &lt;View&gt;
        &lt;Text&gt;Status do Bluetooth SCO: {scoStatus}&lt;/Text&gt;
        &lt;Button title="Ativar Bluetooth SCO" onPress={handleStartSCO} /&gt;
        &lt;Button title="Desativar Bluetooth SCO" onPress={handleStopSCO} /&gt;
      &lt;/View&gt;
    );
  };

  export default App;
  </code></pre>

<h2>Contribuição</h2>
  <p>Veja o <a href="CONTRIBUTING.md">guia de contribuição</a> para aprender como contribuir para o repositório e o fluxo de desenvolvimento.</p>

<h2>Licença</h2>
  <p>MIT</p>

  <hr>

  <p>Feito com <a href="https://github.com/callstack/react-native-builder-bob">create-react-native-library</a></p>

</body>
</html>
