package com.czy.yq_wanandroid.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.czy.yq_wanandroid.room.dao.ReadHistoryDao
import com.czy.yq_wanandroid.room.entity.ReadHistory
import com.czy.yq_wanandroid.utils.ContentWrapperUtils

@Database(entities = arrayOf(ReadHistory::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun readHistoryDao(): ReadHistoryDao
    companion object{
        val instance: AppDatabase by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            Room.databaseBuilder(
                ContentWrapperUtils.mContext,
                AppDatabase::class.java,
                "wanAndroid.db"
            ).build()
        }
    }


}