package com.example.littlebeachblog.player.contract

import com.example.littlebeachblog.data.room.PlayHistoryEntity

/**
 * 播放回调
 * Created by AK on 2022/2/17 11:43.
 * God bless my code!
 */
interface IMusicPlayCallBack {
    fun afterPlayed(music: PlayHistoryEntity)
}