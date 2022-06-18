package com.example.littlebeachblog.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Room数据库
 * Created by AK on 2022/2/26 11:37.
 * God bless my code!
 */
@Database(entities = [PlayHistoryEntity::class], version = 1,exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun playHistoryDao(): PlayHistoryDao

}