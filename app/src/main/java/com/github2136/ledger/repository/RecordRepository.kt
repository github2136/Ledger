package com.github2136.ledger.repository

import android.app.Application
import android.content.Context
import com.github2136.basemvvm.BaseRepository
import com.github2136.ledger.model.db.DBHelper

/**
 * Created by 44569 on 2024/1/4
 */
class RecordRepository(context: Context) : BaseRepository(context) {
    private val recordDao by lazy { DBHelper.getInstance(context) }


}