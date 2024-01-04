package com.github2136.ledger.base

import android.app.Application
import com.github2136.basemvvm.BaseVM
import com.github2136.basemvvm.download.DownloadUtil
import com.github2136.ledger.repository.RecordRepository

/**
 * Created by 44569 on 2023/12/22
 */
open class AppBaseVM(app: Application) : BaseVM(app) {
    protected val recordRepository by lazy { RecordRepository(app) }
}