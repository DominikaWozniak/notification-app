import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
class MainActivity : AppCompatActivity() {
    var count = 0
    private val channelId = "10001"
    private val defaultChannelId = "default"
    override fun onResume() {
        super.onResume()
        count = 0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "KotlinApp"
    }
    fun createNotification(view: View) {
        count++
        val notificationIntent = Intent(applicationContext, MainActivity::class.java)
        notificationIntent.putExtra("fromNotification", true)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager
        val builder = NotificationCompat.Builder(applicationContext, defaultChannelId)
        builder.setContentTitle("My Notification")
        builder.setContentIntent(pendingIntent)
        builder.setContentText("Hello World, to jest napis")
        builder.setSmallIcon(R.drawable.ic_launcher_foreground)
        builder.setAutoCancel(true)
        builder.setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
        builder.setNumber(count)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(channelId,
                "NOTIFICATION_CHANNEL_NAME", importance)
            builder.setChannelId(channelId)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(System.currentTimeMillis().toInt(), builder.build())
    }
}