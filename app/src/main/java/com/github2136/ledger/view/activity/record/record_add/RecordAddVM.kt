package com.github2136.ledger.view.activity.record.record_add

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.github2136.ledger.base.AppBaseVM
import com.github2136.ledger.model.entity.Record
import java.util.Date

/**
 * Created by 44569 on 2024/1/4
 */
class RecordAddVM(app: Application) : AppBaseVM(app) {
    val ledgerLD = MutableLiveData<List<String>>()
    val incomeTypeLD = MutableLiveData<List<String>>()
    val disburseTypeLD = MutableLiveData<List<String>>()
    val recordLD=MutableLiveData(Record())

    fun getLedger() = launch {
        ledgerLD.value = recordRepository.getLedger()
    }

    fun getType() = launch {
        incomeTypeLD.value = recordRepository.getIncomeType()
        disburseTypeLD.value = recordRepository.getDisburseType()
    }

    fun postRecord() = launch {
        recordRepository.postRecord(Record(0, "默认", "类型", Date(), 1.0))
    }
}