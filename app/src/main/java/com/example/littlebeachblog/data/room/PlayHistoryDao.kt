package com.example.littlebeachblog.data.room

import androidx.room.*

/**
 * 播放记录操作
 * Created by AK on 2022/2/26 11:28.
 * God bless my code!
 */
@Dao
interface PlayHistoryDao {
    //新增实体
    @Insert
    fun insertUser(music: PlayHistoryEntity)

    //新增多个实体
    @Insert
    fun insertUsers(musics: List<PlayHistoryEntity>)

    //更新数据
    @Update
    fun updateUser(music: PlayHistoryEntity)

    //删除数据
    @Delete
    fun deleteUser(music: PlayHistoryEntity)

    //编写自己的 SQL 查询(query)方法
    @Query("SELECT * FROM playHistory ORDER BY playTime")
    fun getAll(): List<PlayHistoryEntity>

//    //根据id查询 playHistory 表，将参数集合传递给查询
//    @Query("SELECT * FROM playHistory WHERE name IN (:ids)")
//    fun loadAllByNames(ids: IntArray): List<PlayHistoryEntity>

    //将简单参数传递给查询
    @Query("SELECT * FROM playHistory WHERE id == :id")
    fun loadById(id: String): PlayHistoryEntity?

}