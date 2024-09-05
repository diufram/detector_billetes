package com.example.detector_billetes

import android.content.Intent
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity : FlutterActivity() {
    private val CHANNEL = "com.example.volume"

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler { call, result ->
            if (call.method == "startVolumeService") {
                startVolumeService()
                result.success("SERVICIO INICIADO")
            } else {
                result.notImplemented()
            }
        }
    }

    private fun startVolumeService() {
        Intent(this, VolumeService::class.java).also{startService(it)}
        
    }

    override fun onDestroy(){
        super.onDestroy()
    }
}
