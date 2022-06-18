package com.example.littlebeachblog.ui.page.me.detailAc

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.example.littlebeachblog.BR
import com.example.littlebeachblog.R
import com.example.littlebeachblog.app.BaseActivity
import com.example.littlebeachblog.app.DataBindingConfig
import com.example.littlebeachblog.app.utils.snackBar
import com.example.littlebeachblog.data.bean.MoYv
import com.example.littlebeachblog.data.config.GlideEngine
import com.example.littlebeachblog.domain.message.SharedViewModel
import com.example.littlebeachblog.ui.state.PersonViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.gyf.immersionbar.ImmersionBar
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class PersonEditActivity : BaseActivity() {

    val mState by viewModels<PersonViewModel>()
    private lateinit var mEvent: SharedViewModel
    private lateinit var nikeDialog: AlertDialog
    private lateinit var labelDialog: AlertDialog
    private lateinit var sexDialog: AlertDialog
    private val edit by lazy { EditText(this) }
    override fun initViewModel() {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init()
        mEvent = getApplicationScopeViewModel(SharedViewModel::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.activity_person_edit, BR.vm, mState)
            .addBindingParam(BR.click, PersonEditClickProxy())
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbar(R.id.toolbar, "我的资料")

        mEvent.userInfo.observe(this) {
            mState.userInfoLd.value = it
            mState.sexLd.value = when (it.sex) {
                0 -> "女"
                1 -> "男"
                else -> "未知QAQ"
            }
            mState.labelLd.value = if (it.label.isNullOrEmpty()) "不是懒得签名！只是在思考" else it.label
        }

        nikeDialog = MaterialAlertDialogBuilder(this)
            .setView(edit)
            .setTitle("修改昵称")
            .setNegativeButton("取消") { _, _ -> }
            .setPositiveButton("保存") { _, _ ->
                if (edit.text.isNullOrEmpty()) return@setPositiveButton "昵称不能为空".snackBar(binding.root).show()
                isShowLoad(true)
                mState.userInfoLd.value?.nickName = "${edit.text}"
                updateUser(mState.userInfoLd.value!!)
            }.create()

        labelDialog = MaterialAlertDialogBuilder(this)
            .setView(edit)
            .setTitle("修改标签")
            .setNegativeButton("取消") { _, _ -> }
            .setPositiveButton("保存") { _, _ ->
                isShowLoad(true)
                mState.userInfoLd.value?.label = "${edit.text}"
                updateUser(mState.userInfoLd.value!!)
            }.create()

        sexDialog = MaterialAlertDialogBuilder(this)
            .setItems(arrayOf("女", "男")) { _, which ->
                isShowLoad(true)
                mState.userInfoLd.value?.sex = which
                updateUser(mState.userInfoLd.value!!)
            }.create()
    }

    /**
     * 修改用户信息
     */
    fun updateUser(userInfo: MoYv.UserInfo) {
        lifecycleScope.launch {
            val updateUserInfo = mState.updateUserInfo(userInfo)
            if (updateUserInfo.isSuccess()) {
                mEvent.userInfo.value = updateUserInfo.getData()
            }
            isShowLoad(false)
            updateUserInfo.getMessage().snackBar(binding.root).show()
        }
    }

    inner class PersonEditClickProxy {

        fun avatarClick() {
            PictureSelector.create(this@PersonEditActivity)
                .openGallery(SelectMimeType.ofImage())
                .setImageSpanCount(3)
                .setImageEngine(GlideEngine.createGlideEngine())
                .setMaxSelectNum(1)
                .forResult(object : OnResultCallbackListener<LocalMedia> {
                    override fun onResult(result: ArrayList<LocalMedia>) {
                        isShowLoad(true)
                        lifecycleScope.launch {
                            val image = arrayListOf<MultipartBody.Part>()
                            result.forEach {
                                image.add(
                                    MultipartBody.Part.createFormData(
                                        "image",
                                        it.fileName,
                                        File(it.realPath).asRequestBody()
                                    )
                                )
                            }
                            val upLoadAvatar = mState.upLoadAvatar(image)
                            if (upLoadAvatar.isSuccess()) {
                                mState.userInfoLd.value?.avatar = upLoadAvatar.getData()[0].imgUrl
                                updateUser(mState.userInfoLd.value!!)
                            } else {
                                isShowLoad(false)
                                "头像上传失败，${upLoadAvatar.getMessage()}".snackBar(binding.root).show()
                            }
                        }
                    }

                    override fun onCancel() {
                    }
                })
        }

        fun nickClick() {
            edit.setText(mState.userInfoLd.value?.nickName)
            nikeDialog.show()
        }

        fun sexClick() {
            sexDialog.show()
        }

        fun labelClick() {
            edit.setText(mState.userInfoLd.value?.label)
            labelDialog.show()
        }

    }

}