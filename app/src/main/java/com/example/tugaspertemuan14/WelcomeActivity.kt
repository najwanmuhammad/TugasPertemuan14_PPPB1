package com.example.tugaspertemuan14

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.tugaspertemuan14.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var prefManager: PrefManager

    private val channelId = "logout_channel"
    private val channelName = "Logout Notification"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi PrefManager
        prefManager = PrefManager(this)

        // Gunakan binding untuk tombol logout
        binding.btnLogout.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                // Cek apakah aplikasi sudah memiliki izin POST_NOTIFICATIONS pada Android 13+
                if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                    showLogoutNotification()
                } else {
                    // Jika izin belum diberikan, minta izin
                    requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
                }
            } else {
                // Untuk Android di bawah 13, tidak perlu izin khusus
                showLogoutNotification()
            }
        }
    }

    private fun showLogoutNotification() {
        val notifManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Buat NotificationChannel (untuk Android O ke atas)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notifManager.createNotificationChannel(channel)
        }

        // Intent untuk menangani logout
        val logoutIntent = Intent(this, NotifLogout::class.java)
        val logoutPendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            logoutIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Intent untuk membuka aplikasi jika notifikasi diklik
        val logoutActionIntent = Intent(this, MainActivity::class.java)
        val logoutActionPendingIntent = PendingIntent.getActivity(
            this,
            0,
            logoutActionIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Buat notifikasi dengan tombol Logout
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.baseline_notifications_24) // Ganti dengan ikon notifikasi
            .setContentTitle("Logout")
            .setContentText("Klik tombol di bawah untuk logout.")
            .addAction(R.drawable.baseline_logout_24, "Logout", logoutPendingIntent) // Tombol logout
            .setContentIntent(logoutActionPendingIntent) // Intent ketika notifikasi diklik
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        // Tampilkan notifikasi
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            NotificationManagerCompat.from(this).notify(1, notification)
        }
    }

}