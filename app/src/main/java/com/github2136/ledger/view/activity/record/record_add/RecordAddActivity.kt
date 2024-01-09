package com.github2136.ledger.view.activity.record.record_add

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import com.github2136.Util
import com.github2136.datetime.DatePickerDialog
import com.github2136.ledger.R
import com.github2136.ledger.base.AppBaseActivity
import com.github2136.ledger.common.GetMedia
import com.github2136.ledger.common.Other
import com.github2136.ledger.databinding.ActivityRecordAddBinding
import com.github2136.ledger.view.dialog.MediaDialog
import com.github2136.util.BitmapUtil
import com.github2136.util.date
import com.github2136.util.dp2px
import com.github2136.util.str

/**
 * Created by 44569 on 2024/1/4
 */
class RecordAddActivity : AppBaseActivity<RecordAddVM, ActivityRecordAddBinding>() {
    lateinit var incomeAdapter: ArrayAdapter<String>
    lateinit var disburseAdapter: ArrayAdapter<String>
    val medias = mutableListOf<String>()
    val datePickerDialog by lazy {
        DatePickerDialog { date ->
            vm.recordLD.value!!._date = date.date(Util.DATE_PATTERN_YMD)!!
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        bind.view = this
        bind.vm = vm
        vm.titleTextLD.value = "添加"
        vm.rightBtnLD.value = "保存"
        vm.getLedger()
        vm.getType()
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btnDate -> {
                datePickerDialog.show(vm.recordLD.value!!.Date.str(Util.DATE_PATTERN_YMD), supportFragmentManager)
            }
            R.id.btnAddMedia -> {
                if (bind.llImg.childCount < 2) {
                    addMedia.launch(MediaDialog.CAPTURE or MediaDialog.PHOTO_PICKER)
                } else {
                    showToast("最多添加1个媒体文件")
                }
            }
        }
    }

    override fun rightBtnClick(btnRight: View) {
        vm.recordLD.value?.apply {
            Ledger = bind.spLedger.selectedItem.toString()
            Type = bind.spType.selectedItem.toString()
            Amount = bind.etAmount.text.toString().toDouble()
        }
        vm.postRecord()
    }

    fun onCheckedChanged(group: RadioGroup, checkedId: Int) {
        when (checkedId) {
            R.id.rbIncome -> {
                bind.spType.adapter = incomeAdapter
                vm.recordLD.value!!.Income = true
            }
            R.id.rbDisburse -> {
                bind.spType.adapter = disburseAdapter
                vm.recordLD.value!!.Income = false
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

    private val addMedia = registerForActivityResult(GetMedia()) {
        it?.apply {
            //图片压缩
            showProgressDialog("图片压缩中")
            BitmapUtil.getInstance(activity, this.second)
                .limitSize(1024 * 1024 * 3)
                .rotation()
                .limitPixel(2048)
                .save(Other.getImageLocalPath(activity)) { path ->
                    dismissProgressDialog()
                    if (path == null) {
                        showToast("图片压缩失败")
                    } else {
                        Other.getMediaPath(activity, layoutInflater, medias, path, 100f.dp2px, bind.llImg, true)
                    }
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
        vm.addLD.observe(this) {
            setResult(RESULT_OK)
            finish()
        }
    }
}