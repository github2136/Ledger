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
            "[\"工资\",\"奖金补贴\",\"卖二手\",\"投资理财\",\"利息\",\"兼职\",\"其他\",\"人情社交\",\"报销\",\"生意\",\"生活费\",\"中奖\",\"转账\",\"保险理赔\"]"
        )
    )

    fun getDisburseType() = jsonUtil.fromJson<List<String>>(
        spUtil.getString(
            Constants.SP_DISBURSE_TYPE,
            "[\"交通\", \"爱车\", \"生活服务\", \"休闲娱乐\", \"餐饮\", \"其他\", \"穿搭美容\", \"买菜\", \"住房\", \"家具家电\", \"运动\", \"生活日用\", \"水果零食\", \"购物\", \"人情社交\", \"宠物\", \"学习\", \"转账\", \"烟酒\", \"医疗保险\", \"金融保险\", \"酒店旅游\"]"
        )
    )


    ///////////////////////////////////////////////////////////////////////////
    // sqlite
    ///////////////////////////////////////////////////////////////////////////
    fun postRecord(record: Record) = recordDao.post(record)
}