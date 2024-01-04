package com.github2136.ledger.view.activity.record.record_list

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isGone
import androidx.core.view.isInvisible
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
            val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
            intent.data = Uri.parse("package:" + this.packageName);
            startActivity(intent);
        } else {
            PermissionX.init(activity)
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
}