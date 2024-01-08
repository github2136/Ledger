package com.github2136.ledger.view.activity.record.record_add

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import com.github2136.Util
import com.github2136.datetime.DatePickerDialog
import com.github2136.ledger.R
import com.github2136.ledger.base.AppBaseActivity
import com.github2136.ledger.databinding.ActivityRecordAddBinding
import com.github2136.util.str

/**
 * Created by 44569 on 2024/1/4
 */
class RecordAddActivity : AppBaseActivity<RecordAddVM, ActivityRecordAddBinding>() {
    lateinit var incomeAdapter: ArrayAdapter<String>
    lateinit var disburseAdapter: ArrayAdapter<String>
    val datePickerDialog by lazy {
        DatePickerDialog() {

        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        bind.view = this
        bind.vm = vm
        vm.titleTextLD.value = "添加"
        vm.getLedger()
        vm.getType()
        bind.spType.adapter = ArrayAdapter(activity, R.layout.item_spinner, R.id.tvTxt, arrayOf("sss"))
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btnDate -> {
                datePickerDialog.show(vm.recordLD.value!!.Date.str(Util.DATE_PATTERN_YMD), supportFragmentManager)
            }
        }
    }

    fun onCheckedChanged(group: RadioGroup, checkedId: Int) {
        when (checkedId) {
            R.id.rbIncome -> {
                bind.spType.adapter = incomeAdapter
            }
            R.id.rbDisburse -> {
                bind.spType.adapter = disburseAdapter
            }
        }
    }

    fun afterTextChanged(s: Editable) {
        val i = s.indexOf(".")
        if (i > 0) {
            if (s.substring(i).length > 3) {
                val txt = s.removeRange(i + 3, s.length)
                bind.etAmount.setText(txt)
                bind.etAmount.setSelection(txt.length)
            }
        }
    }

    override fun initObserve() {
        vm.ledgerLD.observe(this) {
            bind.spLedger.adapter = ArrayAdapter(activity, R.layout.item_spinner, R.id.tvTxt, it)
        }
        vm.incomeTypeLD.observe(this) {
            incomeAdapter = ArrayAdapter(activity, R.layout.item_spinner, R.id.tvTxt, it)
        }
        vm.disburseTypeLD.observe(this) {
            disburseAdapter = ArrayAdapter(activity, R.layout.item_spinner, R.id.tvTxt, it)
            bind.spType.adapter = disburseAdapter
        }
    }
}