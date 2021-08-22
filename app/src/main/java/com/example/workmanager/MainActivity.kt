package com.example.workmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.work.*
import com.example.workmanager.ui.theme.WorkManagerTheme
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkManagerTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("WorkManager")
                }
            }
            myWorkManager()
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WorkManagerTheme {
        Greeting("Android")
    }
}



@Composable
private fun myWorkManager() {
    val context = LocalContext.current
    val constraints = Constraints.Builder()
        .setRequiresCharging(false)
        .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
        .setRequiresCharging(false)
        .setRequiresBatteryNotLow(true)
        .build()

    val myRequest = PeriodicWorkRequest.Builder(
        MyWorkerManager::class.java,
        15,
        TimeUnit.MINUTES
    ).setConstraints(constraints)
        .build()
    // El intervalo mínimo es de 15 minutos

    //WorkManager.getInstance(this)
    WorkManager.getInstance(context)
        .enqueueUniquePeriodicWork(
            "my_id",
            ExistingPeriodicWorkPolicy.KEEP,
            myRequest
        )
}

/*
private fun simpleWork() {
    val mRequest: WorkRequest = OneTimeWorkRequestBuilder<MyWorkerManager>()
        .build()
    WorkManager.getInstance(this)
        .enqueue(mRequest)
}*/