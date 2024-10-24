<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>

<h1>react-native-bluetooth-microphone</h1>

<h2>Getting Started</h2>

<h3>Installation</h3>
<pre>
<code>
$ npm install react-native-bluetooth-microphone --save
</code>
</pre>

<h3>Automatic Installation</h3>
<pre>
<code>
$ react-native link react-native-bluetooth-microphone
</code>
</pre>

<h3>Manual Installation</h3>

<h4>Android</h4>

<ol>
    <li>
        Open <code>android/app/src/main/java/[...]/MainActivity.java</code>
        <ul>
            <li>Add the following import at the top of the file:</li>
            <pre><code>import br.com.codificar.pacote_a_criar.RNBluetoothMicrophonePackage;</code></pre>
            <li>Add <code>new RNBluetoothMicrophonePackage()</code> to the list returned by the <code>getPackages()</code> method.</li>
        </ul>
    </li>
    <li>
        Append the following lines to <code>android/settings.gradle</code>:
        <pre><code>
include ':react-native-bluetooth-microphone'
project(':react-native-bluetooth-microphone').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-bluetooth-microphone/android')
        </code></pre>
    </li>
    <li>
        Inside the <code>dependencies</code> block in <code>android/app/build.gradle</code>, insert the following line:
        <pre><code>
compile project(':react-native-bluetooth-microphone')
        </code></pre>
    </li>
</ol>

<h2>Usage</h2>

<pre>
<code>
import RNBluetoothMicrophone from 'react-native-bluetooth-microphone';

// TODO: What to do with the module?
RNBluetoothMicrophone;
</code>
</pre>

</body>
</html>
