package com.czy.yq_wanandroid.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.czy.lib_base.utils.ContentWrapperUtils
import com.czy.yq_wanandroid.room.dao.ReadHistoryDao
import com.czy.yq_wanandroid.room.dao.SearchHistoryDao
import com.czy.yq_wanandroid.room.entity.ReadHistory
import com.czy.yq_wanandroid.room.entity.SearchHistory
import com.infoholdcity.basearchitecture.self_extends.log

@Database(entities = arrayOf(ReadHistory::class, SearchHistory::class), version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun readHistoryDao(): ReadHistoryDao
    abstract fun searchHistoryDao(): SearchHistoryDao

    companion object {
        val instance: AppDatabase by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            Room.databaseBuilder(
                ContentWrapperUtils.mContext,
                AppDatabase::class.java,
                "wanAndroid.db"
            ).addMigrations(DBMigration())
                .build()
        }
    }

    //数据库从2版本升级到3版本操作： 搜索历史新增时间字段
    class DBMigration : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            //执行 "alter table search_history add column time"
            "alter table search_history add column time".log("DDDDD")
            database.execSQL("alter table search_history add column time ")
            database.execSQL("update search_history set time = 0")
        }
    }

}