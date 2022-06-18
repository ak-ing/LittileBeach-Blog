/*
 * Copyright 2018-present KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.littlebeachblog.ui.state;

import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.ViewModel;

import com.example.littlebeachblog.R;
import com.example.littlebeachblog.app.App;
import com.example.littlebeachblog.player.PlayerManager;
import com.kunminx.player.PlayingInfoManager;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

/**
 * Create by KunMinX at 19/10/29
 */
public class PlayerViewModel extends ViewModel {

    public final ObservableField<String> title = new ObservableField<>(App.getApp().getString(R.string.app_name));

    public final ObservableField<String> artist = new ObservableField<>(App.getApp().getString(R.string.app_name));

    public final ObservableField<String> coverImg = new ObservableField<>();

    public final ObservableField<Drawable> placeHolder = new ObservableField<>(
            ContextCompat.getDrawable(App.getApp(), R.drawable.bg_album_default));

    public final ObservableInt maxSeekDuration = new ObservableInt();

    public final ObservableInt currentSeekPosition = new ObservableInt();

    public final ObservableBoolean isPlaying = new ObservableBoolean();

    public final ObservableField<MaterialDrawableBuilder.IconValue> playModeIcon = new ObservableField<>();

    {
        if (PlayerManager.getInstance().getRepeatMode() == PlayingInfoManager.RepeatMode.LIST_CYCLE) {
            playModeIcon.set(MaterialDrawableBuilder.IconValue.REPEAT);
        } else if (PlayerManager.getInstance().getRepeatMode() == PlayingInfoManager.RepeatMode.SINGLE_CYCLE) {
            playModeIcon.set(MaterialDrawableBuilder.IconValue.REPEAT_ONCE);
        } else {
            playModeIcon.set(MaterialDrawableBuilder.IconValue.SHUFFLE);
        }
    }
}
