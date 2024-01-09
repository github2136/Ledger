package com.github2136.ledger.view.dialog

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.github2136.photopicker.activity.CaptureActivity
import com.github2136.photopicker.activity.PhotoPickerActivity
import com.github2136.util.FileUtil
import com.permissionx.guolindev.PermissionX

/**
 * Created by YB on 2020/4/9
 * 媒体文件添加
 */
class MediaDialog : AppCompatActivity() {

    private var mediaType: Int = 0
    private val photo_picker = "本地图片"
    private val capture = "拍照"
    private var whitch = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(TextView(this))
        mediaType = intent.getIntExtra(ARG_MEDIA_TYPE, 0)
        val item = mutableListOf<String>()
        if (mediaType and PHOTO_PICKER == PHOTO_PICKER) {
            item.add(photo_picker)
        }
        if (mediaType and CAPTURE == CAPTURE) {
            item.add(capture)
        }
        AlertDialog.Builder(this)
            .setTitle("操作")
            .setItems(item.toTypedArray()) { _, whitch ->
                this.whitch = whitch
                when (item[whitch]) {
                    photo_picker -> {
                        //图片选择
                        PermissionX.init(this)
                            .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                            .explainReasonBeforeRequest()
                            .onExplainRequestReason { scope, deniedList ->
                                scope.showRequestReasonDialog(deniedList, "图片选择需要文件读写权限。是否立刻授权？", "立即授权", "取消")
                            }.onForwardToSettings { scope, deniedList ->
                                scope.showForwardToSettingsDialog(deniedList, "缺少文件读写权限是否跳转设置修改权限状态", "打开设置", "取消")
                            }.request { allGranted, grantedList, deniedList ->
                                if (allGranted) {
                                    val intent = Intent(this, PhotoPickerActivity::class.java)
                                    intent.putExtra(PhotoPickerActivity.ARG_PICKER_COUNT, 1)
                                    resultPhotoPicker.launch(intent)
                                } else {
                                    finish()
                                }
                            }
                    }
                    capture -> {
                        //拍照
                        PermissionX.init(this)
                            .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                            .explainReasonBeforeRequest()
                            .onExplainRequestReason { scope, deniedList ->
                                scope.showRequestReasonDialog(deniedList, "图片拍摄需要文件读写、摄像头权限。是否立刻授权？", "立即授权", "取消")
                            }.onForwardToSettings { scope, deniedList ->
                                scope.showForwardToSettingsDialog(deniedList, "缺少以下权限是否跳转设置修改权限状态", "打开设置", "取消")
                            }.request { allGranted, grantedList, deniedList ->
                                if (allGranted) {
                                    resultOther.launch(Intent(this, CaptureActivity::class.java))
                                } else {
                                    finish()
                                }
                            }
                    }
                }
            }.setOnDismissListener {
                if (whitch == -1) {
                    finish()
                }
            }
            .show()
    }

    private fun result(uri: Uri?) {
        uri?.run {
            val path = FileUtil.getFileAbsolutePath(this@MediaDialog, uri)
            val intent = Intent()
            intent.putExtra(RESULT_PATH, path)
            intent.putExtra(RESULT_URI, this)
            setResult(Activity.RESULT_OK, intent)
        }
    }

    private val resultPhotoPicker = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            val uri = it.data?.getParcelableArrayListExtra<Uri>(PhotoPickerActivity.ARG_RESULT_URI)?.first()
            result(uri)
        }
        finish()
    }
    private val resultOther = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            val uri = it.data?.data
            result(uri)
        }
        finish()
    }

    companion object {
        //本地图片
        const val PHOTO_PICKER = 0b0001
        //拍照
        const val CAPTURE = 0b0010

        const val ARG_MEDIA_TYPE = "MEDIA_TYPE"

        const val RESULT_PATH = "PATH"
        const val RESULT_URI = "URI"
    }
}