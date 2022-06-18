package com.example.littlebeachblog.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 播放记录Room实体表
 * Created by AK on 2022/2/26 11:22.
 * God bless my code!
 */
@Entity(tableName = "playHistory")
data class PlayHistoryEntity(

    @PrimaryKey
    val id: String,
    @ColumnInfo
    val name: String,
    @ColumnInfo
    val arId: Int,
    @ColumnInfo
    val arName: String,
    @ColumnInfo
    val alId: Int,
    @ColumnInfo
    val alName: String,
    @ColumnInfo
    val pic: Long,
    @ColumnInfo
    val picUrl: String,
    @ColumnInfo
    val playTime: Long
) {
    override fun toString(): String {
        return "PlayHistoryEntity(id='$id', name='$name', arId=$arId, arName='$arName', alId=$alId, alName='$alName', pic=$pic, picUrl='$picUrl', playTime=$playTime)"
    }
}