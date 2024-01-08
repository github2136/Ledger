package com.github2136.ledger.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.github2136.ledger.model.entity.Record

/**
 * Created by 44569 on 2024/1/4
 */
@Dao
interface RecordDao {
    @Insert
    fun post(record: Record): Long
}