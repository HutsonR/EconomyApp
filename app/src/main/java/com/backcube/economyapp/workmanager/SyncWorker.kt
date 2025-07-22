package com.backcube.economyapp.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.backcube.domain.repositories.SyncRepository
import java.util.concurrent.TimeUnit

class SyncWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val syncRepository: SyncRepository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        Log.i("SyncWorker", "Sync worker started.")
        return syncRepository.syncData()
            .fold(
                onSuccess = {
                    Log.i("SyncWorker", "Sync completed successfully.")
                    Result.success()
                },
                onFailure = {
                    Log.e("SyncWorker", "Sync failed with exception", it)
                    Result.retry()
                }
            )
    }

    companion object {
        private const val WORKER_NAME = "com.backcube.economyapp.workmanager.SyncWorker"

        fun setupPeriodicSync(context: Context) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(3, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                WORKER_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                syncRequest
            )
        }
    }

}