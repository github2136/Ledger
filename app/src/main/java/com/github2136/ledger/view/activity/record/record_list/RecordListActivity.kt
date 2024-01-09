package com.github2136.ledger.view.activity.record.record_list

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.documentfile.provider.DocumentFile
import com.github2136.ledger.R
import com.github2136.ledger.base.AppBaseLoadMoreActivity
import com.github2136.ledger.databinding.ActivityRecordListBinding
import com.github2136.ledger.view.activity.record.record_add.RecordAddActivity
import com.permissionx.guolindev.PermissionX

/**
 * Created by 44569 on 2024/1/4
 *
 */
class RecordListActivity : AppBaseLoadMoreActivity<RecordListVM, ActivityRecordListBinding>() {

    override fun initData(savedInstanceState: Bundle?) {
        bind.view = this
        bind.vm = vm
        vm.titleTextLD.value = "记录"
        bind.title.ibLeft.isInvisible = true
        // 方案一：跳转到系统文件访问页面，手动赋予
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
                // putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)//目录默认位置
            }
            openDocResult.launch(intent)
        } else {
            PermissionX.init(activity)
                .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .explainReasonBeforeRequest()
                .onExplainRequestReason { scope, deniedList ->
                    scope.showRequestReasonDialog(deniedList, "需要文件读写权限。是否立刻授权？", "立即授权", "取消")
                }.onForwardToSettings { scope, deniedList ->
                    scope.showForwardToSettingsDialog(deniedList, "缺少文件读写权限是否跳转设置修改权限状态", "打开设置", "取消")
                }.request { allGranted, grantedList, deniedList ->
                    if (allGranted) {
                        // val intent = Intent(this, PhotoPickerActivity::class.java)
                        // intent.putExtra(PhotoPickerActivity.ARG_PICKER_COUNT, 1)
                        // resultPhotoPicker.launch(intent)
                    } else {
                        AlertDialog.Builder(activity)
                            .setTitle("警告")
                            .setMessage("缺少外部存储权限")
                            .setPositiveButton("") { _, _ -> }
                            .setNegativeButton("") { _, _ -> }
                            .show()
                    }
                }
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.fabAdd -> {
                addResult.launch(Intent(activity, RecordAddActivity::class.java))
            }
        }
    }


    val addResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            refreshData()
        }
    }
    val openDocResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {}
}