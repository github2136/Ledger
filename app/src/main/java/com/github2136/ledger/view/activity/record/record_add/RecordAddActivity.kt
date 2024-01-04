package com.github2136.ledger.view.activity.record.record_add

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.github2136.ledger.base.AppBaseActivity
import com.github2136.ledger.databinding.ActivityRecordAddBinding

/**
 * Created by 44569 on 2024/1/4
 */
class RecordAddActivity : AppBaseActivity<RecordAddVM, ActivityRecordAddBinding>() {
    override fun initData(savedInstanceState: Bundle?) {
        bind.view = this
        bind.vm = vm
        vm.titleTextLD.value = "添加"
    }
}