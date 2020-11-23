package com.czy.yq_wanandroid.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.czy.yq_wanandroid.room.entity.ReadHistory

@Dao
interface ReadHistoryDao {

    @Query("select * from read_history order by time DESC LIMIT (:offset),(:count)")
    fun getReadHistory(offset: Int, count: Int): List<ReadHistory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addReadHistory(readHistory: ReadHistory)

    @Query("delete from read_history where link = :link")
    fun delete(link: String)

    @Query("delete from read_history")
    fun deleteAll()

    @Query(
        """DELETE FROM read_history WHERE link NOT IN 
        (SELECT link FROM read_history ORDER BY time DESC LIMIT 0, :maxCount)"""
    )
    fun autoDeleteOverMax(maxCount: Int)
}