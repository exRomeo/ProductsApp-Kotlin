package com.example.myproductsapp_kotlin

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [Product::class], version = 1)
abstract class RoomClient : RoomDatabase() {
    abstract fun getProductDao(): ProductDao
    companion object{
        @Volatile
        private var INSTANCE:RoomClient? = null

        fun getInstance(context : Context):RoomClient{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext, RoomClient::class.java,"product_database").build()
                INSTANCE=instance
                instance
            }
        }
    }
}
