package com.example.detector_billetes

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import android.app.Service
import android.widget.Toast

class VolumeService : Service() {

    private lateinit var volumeReceiver: VolumeReceiver

    override fun onCreate() {
        super.onCreate()

        volumeReceiver = VolumeReceiver()
        val filter = IntentFilter()
        filter.addAction("android.media.VOLUME_CHANGED_ACTION")
        registerReceiver(volumeReceiver, filter)

        val notification = createNotification()
        startForeground(1, notification)
    }

    private fun createNotification(): Notification {
        val channelId = "VolumeServiceChannel"
        val channelName = "Volume Service"
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW)
            manager.createNotificationChannel(channel)
        }

        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE // Add FLAG_IMMUTABLE here
        )

        return Notification.Builder(this, channelId)
            .setContentTitle("Volume Service")
            .setContentText("Listening for volume button presses")
            .setSmallIcon(R.drawable.ic_notification) // Reemplaza con tu icono
            .setContentIntent(pendingIntent) // Acción al hacer clic en la notificación
            .build()
    }


    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
       
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    inner class VolumeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.media.VOLUME_CHANGED_ACTION") {
            // Mostrar el Toast
            

            // Abrir la aplicación (opcional)
            val appIntent = Intent(context, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            Toast.makeText(context, "Abriendo la Aplicacion", Toast.LENGTH_SHORT).show()

            context.startActivity(appIntent)
        }
    }
}

}
