package com.github2136.ledger.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Created by 44569 on 2024/1/4
 */
@Entity
data class Record(
    @PrimaryKey(autoGenerate = true)
    var Id: Int = 0, //主键
    var Ledger: String = "", //账本
    var Type: String = "", //类型
    var Date: Date = Date(), //日期
    var Amount: Double? = null, //金额
    var Remark: String = "", //备注
    var Image: String = "", //图片
    var Tag: String = "", //标签
)
