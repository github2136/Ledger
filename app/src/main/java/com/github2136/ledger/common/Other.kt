package com.github2136.ledger.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AlertDialog
import com.github2136.ledger.R
import com.github2136.ledger.view.dialog.MediaDialog
import com.github2136.photopicker.activity.PhotoViewActivity
import com.github2136.util.FileUtil

/**
 * Created by 44569 on 2024/1/9
 */
object Other {
    /**
     * 生成图片本地地址
     */
    fun getImageLocalPath(context: Context) =
        FileUtil.getExternalStorageProjectPath(context) + "/${Constants.DIR_IMAGE}/" + FileUtil.createFileName(".jpg")
    /**
     * 将媒体文件添加到指定控件中
     * @param context Context
     * @param layoutInflater LayoutInflater
     * @param mediaPaths MutableList<String> 媒体路径集合
     * @param url String 媒体文件路径
     * @param mediaWidth Int 图片宽高
     * @param viewGroup ViewGroup 添加的父控件
     * @param del Boolean 是否有删除功能
     * @param video Boolean 是否是热点抓拍视频
     */
    fun getMediaPath(
        context: Context, layoutInflater: LayoutInflater, mediaPaths: MutableList<String>, url: String, mediaWidth: Int, viewGroup: ViewGroup, del: Boolean = false
    ) {
        mediaPaths.add(url)
        val v = layoutInflater.inflate(R.layout.view_media, viewGroup, false)
        v.layoutParams = ViewGroup.LayoutParams(mediaWidth, mediaWidth)
        val iv: ImageView = v.findViewById(R.id.ivMedia)
        if (del) {
            val ibDelete: ImageButton = v.findViewById(R.id.ibDelete)
            ibDelete.visibility = View.VISIBLE
            viewGroup.addView(v, viewGroup.childCount - 1)
            ibDelete.setOnClickListener {
                AlertDialog.Builder(context).setTitle("提示")
                    .setMessage("是否删除该文件")
                    .setPositiveButton("删除") { _, _ ->
                        viewGroup.removeView(v)
                        mediaPaths.remove(url)
                    }
                    .show()
            }
        } else {
            viewGroup.addView(v)
        }

        v.setOnClickListener {
            val intent = Intent(context, PhotoViewActivity::class.java)
            val paths =
                arrayListOf<String>().apply {
                    addAll(mediaPaths)
                }
            intent.putStringArrayListExtra(PhotoViewActivity.ARG_PHOTOS, paths)
            intent.putExtra(PhotoViewActivity.ARG_CURRENT_INDEX, paths.indexOf(url))
            context.startActivity(intent)
        }
        GlideApp.with(context)
            .load(url)
            .apply(glideOptionsCenterCrop)
            .into(iv)
    }
}

/**
 * Created by YB on 2022/9/22
 * 获取媒体文件
 */
class GetMedia : ActivityResultContract<Int, Pair<String, Uri>?>() {
    override fun createIntent(context: Context, input: Int): Intent {
        val intent = Intent(context, MediaDialog::class.java)
        intent.putExtra(MediaDialog.ARG_MEDIA_TYPE, input)
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Pair<String, Uri>? {
        return if (resultCode == Activity.RESULT_OK && intent != null) {
            val path = intent.getStringExtra(MediaDialog.RESULT_PATH)
            val uri = intent.getParcelableExtra<Uri>(MediaDialog.RESULT_URI)
            if (path != null && uri != null) {
                Pair(path, uri)
            } else {
                null
            }
        } else {
            null
        }
    }
}