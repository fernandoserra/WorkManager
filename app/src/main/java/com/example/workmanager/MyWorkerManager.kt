package com.example.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorkerManager(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {


   // val handler= Handler()

    companion object {
        const val CHANNEL_ID = "channel_id"
        const val NOTIFICATION_ID = 1
        const val TAG= "MyWorkerManager"
    }

    override fun doWork(): Result {
        Log.d("success",
            "doWork: Success function called")

        //showNotification()

        dummyTime()
       // startTimeCounter()

        return Result.success()
    }

    private fun dummyTime() {
        for(num in 1 ..100){
            Log.d(TAG, "dummyTime: $num ")
            Thread.sleep(1000)
        }
    }

    fun startTimeCounter() {
        var counter = 0
        object : CountDownTimer(50000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.d(TAG, "counter: $counter ")
                counter++
            }
            override fun onFinish() {
                Log.d(TAG, "FINALIZADO ")
            }
        }.start()
    }


    private fun showNotification() {

        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0, intent, 0
        )


        val notification = NotificationCompat.Builder(
            applicationContext,
            CHANNEL_ID
        )
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("New Task")
            .setContentText("Subscribe on the channel")
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channelName = "Channel Name"
            val channelDescription = "Channel Description"
            val channelImportance = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel(CHANNEL_ID, channelName, channelImportance).apply {
                description = channelDescription
            }

            val notificationManager = applicationContext.getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }


        with(NotificationManagerCompat.from(applicationContext)) {
            notify(NOTIFICATION_ID, notification.build())
        }

    }


}
