package com.github2136.ledger.repository

import android.app.Application
import android.content.Context
import com.github2136.basemvvm.BaseRepository
import com.github2136.ledger.common.Constants
import com.github2136.ledger.model.db.DBHelper
import com.github2136.ledger.model.entity.Record

/**
 * Created by 44569 on 2024/1/4
 */
class RecordRepository(context: Context) : BaseRepository(context) {
    private val recordDao by lazy { DBHelper.getInstance(context).recordDao() }

    ///////////////////////////////////////////////////////////////////////////
    // sp
    ///////////////////////////////////////////////////////////////////////////
    fun getLedger() = jsonUtil.fromJson<List<String>>(spUtil.getString(Constants.SP_LEDGER, "[\"默认\"]"))
    fun getIncomeType() = jsonUtil.fromJson<List<String>>(
        spUtil.getString(
            Constants.SP_INCOME_TYPE,
            "[\"工资\", \"兼职\", \"投资理财\", \"人情社交\", \"奖金补贴\", \"报销\", \"生意\", \"卖二手\", \"生活费\", \"中奖\", \"转账\", \"保险理赔\", \"其他\"]"
        )
    )

    fun getDisburseType() = jsonUtil.fromJson<List<String>>(
        spUtil.getString(
            Constants.SP_DISBURSE_TYPE,
            "[\"餐饮\", \"休闲娱乐\", \"购物\", \"穿搭美容\", \"水果零食\", \"交通\", \"生活日用\", \"人情社交\", \"宠物\", \"养娃\", \"运动\", \"生活服务\", \"买菜\", \"住房\", \"爱车\", \"学习\", \"网络虚拟\", \"烟酒\", \"医疗保险\", \"金融保险\", \"家具家电\", \"酒店旅游\", \"转账\", \"公益\", \"其他\"]"
        )
    )


    ///////////////////////////////////////////////////////////////////////////
    // sqlite
    ///////////////////////////////////////////////////////////////////////////
    fun postRecord(record: Record) {
        recordDao.post(record)
        getIncomeType()
        record.Ledger
        record.Type
    }
}