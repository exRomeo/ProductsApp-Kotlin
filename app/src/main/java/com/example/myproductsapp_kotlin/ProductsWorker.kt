package com.example.myproductsapp_kotlin

import android.content.Context
import android.util.Log
import androidx.core.net.toUri
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectOutputStream

class ProductsWorker(val context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    override fun doWork(): Result {
        try {
            val response = RetrofitClient.api.getAllProducts().execute()
            if (response.isSuccessful) {
                val file = File(context.cacheDir, "products")
                val fos = FileOutputStream(file)
                val oos = ObjectOutputStream(fos)
                return try {
                    oos.writeObject(
                        response.body()?.products ?: listOf(
                            Product(
                                title = "NO DATA RETRIEVED",
                                description = "NO DATA RETRIEVED"
                            )
                        )
                    )
                    Result.success(
                        workDataOf(Constants.FILE_NAME to file.toUri().toString())
                    )
                } catch (e: IOException) {
                    Log.i(TAG, "doWork: io ex")
                    Result.failure(workDataOf(Constants.FAILURE_REASON to e.message))
                }
            } else {
                Log.i(TAG, "doWork:response")
                return Result.failure(workDataOf(Constants.FAILURE_REASON to response.errorBody()))
            }
        } catch (e: java.lang.Exception) {

            Log.i(TAG, "doWork:  something else " + e.message)
            return Result.failure(workDataOf(Constants.FAILURE_REASON to e.message))
        }
    }

}