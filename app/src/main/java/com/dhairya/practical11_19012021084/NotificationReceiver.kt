package com.dhairya.practical11_19012021084

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {


        val id = intent?.getIntExtra("id",0)
        val title = intent?.getStringExtra("title")
        val subTitle = intent?.getStringExtra("subTitle")
        val description = intent?.getStringExtra("description")
        val modifiedTime = intent?.getLongExtra("modifiedTime", 0)
        val timeStamp = intent?.getLongExtra("timeStamp", 0)

        val intentOpenActivity = Intent(context, NoteInfoActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("title", title)
            putExtra("subtitle", subTitle)
            putExtra("description", description)
            putExtra("modifiedTime", modifiedTime)
            putExtra("timeStamp", timeStamp)
        }

        val contentIntent = PendingIntent.getActivity(
            context,
            id!!,
            intentOpenActivity,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(context!!, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(description)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .setContentIntent(contentIntent)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)

        with(NotificationManagerCompat.from(context)) {
            notify(id, builder.build())
        }
    }
}