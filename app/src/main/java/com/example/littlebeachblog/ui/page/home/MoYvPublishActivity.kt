package com.example.littlebeachblog.ui.page.home

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.littlebeachblog.BR
import com.example.littlebeachblog.R
import com.example.littlebeachblog.app.BaseActivity
import com.example.littlebeachblog.app.DataBindingConfig
import com.example.littlebeachblog.app.utils.getToken
import com.example.littlebeachblog.app.utils.snackBar
import com.example.littlebeachblog.data.bean.ImageBean
import com.example.littlebeachblog.data.bean.MoYv
import com.example.littlebeachblog.data.config.GlideEngine
import com.example.littlebeachblog.databinding.DialogTagSelectBinding
import com.example.littlebeachblog.domain.message.SharedViewModel
import com.example.littlebeachblog.ui.adapter.FishTagAdapter
import com.example.littlebeachblog.ui.adapter.MoYvImgAdapter
import com.example.littlebeachblog.ui.state.home_state_child.MoYvViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.gyf.immersionbar.ImmersionBar
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnExternalPreviewEventListener
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class MoYvPublishActivity : BaseActivity() {
    private val mState: MoYvViewModel by viewModels()
    private lateinit var mEvent: SharedViewModel
    private val moYvImgAdapter = MoYvImgAdapter()

    private val dialog by lazy { BottomSheetDialog(this@MoYvPublishActivity) }

    override fun initViewModel() {
        ImmersionBar.with(this).fitsSystemWindows(true)
            .statusBarDarkFont(true, 0.2f).init()
        mEvent = getApplicationScopeViewModel(SharedViewModel::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.activity_mo_yv_publish, BR.vm, mState)
            .addBindingParam(BR.click, MoYvPublishClickProxy())
            .addBindingParam(BR.adapter, moYvImgAdapter)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initToolbar(R.id.materialToolbar, "")
        initEvent()

        val dlView = DialogTagSelectBinding.inflate(layoutInflater)
        val fishTagAdapter = FishTagAdapter()
        dlView.rvFishTag.adapter = fishTagAdapter
        dlView.rvFishTag.layoutManager = LinearLayoutManager(this)
        dialog.setContentView(dlView.root)
        fishTagAdapter.setOnItemClickListener { viewDataBinding, fishTagBean, i ->
            dialog.dismiss()
            mState.fishTag.value = fishTagBean
        }
        mState.fishTagLd.observe(this) {
            val list = it.getData() ?: return@observe
            fishTagAdapter.submitList(list)
        }

        mState.fishTag.observe(this) {
            findViewById<TextView>(R.id.tv_fish_tag).text = it.topicName
        }

        mState.imageLd.observe(this) {
            val result = it.getOrNull()
            if (result == null) {
                "网络错误~o(╥﹏╥)o".snackBar(binding.root).show()
                return@observe isShowLoad(false)
            }
            if (result.isSuccess()) return@observe putFsh(result.getData())
            else result.getMessage().snackBar(binding.root).show()
            isShowLoad(false)
        }

        mState.putFishLd.observe(this) {
            val result = it.getOrNull()
            if (result == null) {
                "网络错误~o(╥﹏╥)o!".snackBar(binding.root).show()
                return@observe isShowLoad(false)
            }
            if (result.isSuccess()) {
                mEvent.moYvRefresh.value = true
                finish()
            }
        }

        mState.getFishTags()
    }

    private fun initEvent() {
        moYvImgAdapter.addClick = {
            PictureSelector.create(this)
                .openGallery(SelectMimeType.ofImage())
                .setImageEngine(GlideEngine.createGlideEngine())
                .setMaxSelectNum(9)
                .setSelectedData(moYvImgAdapter.currentList)
                .isGif(true)
                .forResult(object : OnResultCallbackListener<LocalMedia> {
                    override fun onResult(result: ArrayList<LocalMedia>?) {
                        moYvImgAdapter.submitList(result)
                    }

                    override fun onCancel() {
                    }
                })
        }

        moYvImgAdapter.itemClick = {
            PictureSelector.create(this)
                .openPreview()
                .setImageEngine(GlideEngine.createGlideEngine())
                .setExternalPreviewEventListener(object : OnExternalPreviewEventListener {
                    override fun onPreviewDelete(position: Int) {
                        val currentList = moYvImgAdapter.currentList.toMutableList()
                        moYvImgAdapter.submitList(currentList.apply { removeAt(position) })
                    }

                    override fun onLongPressDownload(media: LocalMedia?): Boolean {
                        return false
                    }
                }).startActivityPreview(it, true, ArrayList(moYvImgAdapter.currentList))
        }
    }

    private fun putFsh(images: List<ImageBean>?) {
        val fish = MoYv()
        fish.content = mState.contentLd.value
        if (mState.fishTag.value != null) {
            fish.topPicId = mState.fishTag.value!!.id
        }
        if (!images.isNullOrEmpty()) {
            val str = StringBuilder("[\"")
            images.forEachIndexed { index, imageBean ->
                if (index > 0) str.append(",")
                str.append(imageBean.imgUrl)
            }
            str.append("\"]")
            fish.images = str.toString()
        }
        mState.putFish(getToken(), fish)
    }

    inner class MoYvPublishClickProxy {

        fun putImg() {
            isShowLoad(true)
            val list = moYvImgAdapter.currentList.toMutableList()
            if (list.isNullOrEmpty()) {
                putFsh(arrayListOf())
                return
            }
            val image = arrayListOf<MultipartBody.Part>()
            list.forEach {
                image.add(
                    MultipartBody.Part.createFormData(
                        "image",
                        it.fileName,
                        File(it.realPath).asRequestBody()
                    )
                )
            }
            mState.uploadImg(getToken(), image)
        }

        fun selectTag() {
            dialog.show()
        }

        fun after(s: Editable) {
            mState.textCount.value = "${s.length}/512"
            mState.putBtnState.set(s.isNotEmpty())
            mState.putBtnColor.set(if (s.isEmpty()) R.color.color_search else R.color.white)
            mState.contentLd.value = "$s"
        }

    }

}