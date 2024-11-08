package com.microphone

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.media.AudioManager
import android.os.Build
import android.os.Handler
import android.util.Log
import androidx.core.content.ContextCompat
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import java.util.*

class MicrophoneModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

  private var audioManager: AudioManager? = null
  private val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()

  override fun getName(): String {
    return "Microphone"
  }

  @ReactMethod
  fun startBluetoothSco(promise: Promise) {
    Log.d("BluetoothSCO", "startBluetoothSco chamado. Inicializando AudioManager e BluetoothAdapter.")

    // Inicializa o AudioManager
    audioManager = reactApplicationContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    // Verifica permissões de Bluetooth e gravação de áudio
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      if (ContextCompat.checkSelfPermission(reactApplicationContext, Manifest.permission.BLUETOOTH_CONNECT) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
        Log.e("BluetoothSCO", "Permissão BLUETOOTH_CONNECT não concedida.")
        promise.reject("PERMISSION_ERROR", "Permissão BLUETOOTH_CONNECT não concedida.")
        return
      }
    }

    if (ContextCompat.checkSelfPermission(reactApplicationContext, Manifest.permission.RECORD_AUDIO) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
      Log.e("BluetoothSCO", "Permissão RECORD_AUDIO não concedida.")
      promise.reject("PERMISSION_ERROR", "Permissão RECORD_AUDIO não concedida.")
      return
    }

    // Verifica se o Bluetooth está ativado e se há dispositivos emparelhados
    if (bluetoothAdapter != null && bluetoothAdapter.isEnabled) {
      Log.d("BluetoothSCO", "Bluetooth está ativado.")

      val pairedDevices: Set<BluetoothDevice> = bluetoothAdapter.bondedDevices

      if (pairedDevices.isNotEmpty()) {
        Log.d("BluetoothSCO", "Dispositivos emparelhados encontrados.")

        // Tenta iniciar o SCO com o primeiro dispositivo emparelhado disponível
        for (device in pairedDevices) {
          Log.d("BluetoothSCO", "Verificando dispositivo emparelhado: ${device.name}")

          // Verifica se o dispositivo está conectado
          if (device.bondState == BluetoothDevice.BOND_BONDED) {
            Log.d("BluetoothSCO", "Dispositivo Bluetooth de áudio conectado encontrado: ${device.name}")
            iniciarBluetoothSco(promise) // Inicia o SCO com o dispositivo conectado
            return
          }
        }
        Log.e("BluetoothSCO", "Nenhum dispositivo Bluetooth de áudio conectado foi encontrado.")
        promise.reject("DEVICE_NOT_CONNECTED", "Nenhum dispositivo Bluetooth de áudio conectado foi encontrado.")
      } else {
        Log.e("BluetoothSCO", "Nenhum dispositivo Bluetooth emparelhado foi encontrado.")
        promise.reject("NO_PAIRED_DEVICES", "Nenhum dispositivo Bluetooth emparelhado foi encontrado.")
      }
    } else {
      Log.e("BluetoothSCO", "Bluetooth não está ativado ou o adaptador não está disponível.")
      promise.reject("BLUETOOTH_ERROR", "Bluetooth não está ativado ou o adaptador não está disponível.")
    }
  }

  private fun iniciarBluetoothSco(promise: Promise) {
    Log.d("BluetoothSCO", "Entrando no método iniciarBluetoothSco.")

    if (audioManager != null) {
      if (!audioManager!!.isBluetoothScoOn) {
        try {
          Log.d("BluetoothSCO", "Tentando iniciar o Bluetooth SCO...")
          audioManager!!.mode = AudioManager.MODE_IN_COMMUNICATION

          // Inicia a conexão SCO
          audioManager!!.startBluetoothSco()
          Log.d("BluetoothSCO", "SCO ativação solicitada.")

          // Verifica se o SCO foi ativado após 3 segundos
          Handler().postDelayed({
            if (audioManager!!.isBluetoothScoOn) {
              Log.d("BluetoothSCO", "Bluetooth SCO ativado com sucesso.")
              promise.resolve("Bluetooth SCO ativado com sucesso.")
            } else {
              Log.e("BluetoothSCO", "Falha ao ativar Bluetooth SCO.")
              promise.reject("SCO_ERROR", "Falha ao ativar Bluetooth SCO.")
            }
          }, 1000)
        } catch (e: SecurityException) {
          Log.e("BluetoothSCO", "Erro de permissão ao iniciar Bluetooth SCO: ${e.message}")
          promise.reject("SCO_PERMISSION_ERROR", "Erro de permissão ao iniciar Bluetooth SCO: ${e.message}")
        }
      } else {
        Log.d("BluetoothSCO", "Bluetooth SCO já está ativado.")
        promise.resolve("Bluetooth SCO já está ativado.")
      }
    } else {
      Log.e("BluetoothSCO", "AudioManager é nulo. Não foi possível ativar Bluetooth SCO.")
      promise.reject("AUDIO_MANAGER_ERROR", "AudioManager é nulo.")
    }
  }

  @ReactMethod
  fun stopBluetoothSco(promise: Promise) {
    Log.d("BluetoothSCO", "Desligando Bluetooth SCO.")
    if (audioManager != null) {
      audioManager!!.stopBluetoothSco()
      audioManager!!.isBluetoothScoOn = false
      Log.d("BluetoothSCO", "Bluetooth SCO desligado com sucesso.")
      promise.resolve("Bluetooth SCO desligado com sucesso.")
    } else {
      Log.e("BluetoothSCO", "AudioManager é nulo. Não foi possível desligar Bluetooth SCO.")
      promise.reject("AUDIO_MANAGER_ERROR", "AudioManager é nulo.")
    }
  }
}
