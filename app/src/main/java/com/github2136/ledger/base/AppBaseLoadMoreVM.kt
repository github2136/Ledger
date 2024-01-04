package com.github2136.ledger.base

import android.app.Application
import com.github2136.basemvvm.download.DownloadUtil
import com.github2136.basemvvm.loadmore.BaseLoadMoreVM
import com.github2136.ledger.repository.RecordRepository

/**
 * Created by 44569 on 2023/12/22
 */
abstract class AppBaseLoadMoreVM<T>(app: Application) : BaseLoadMoreVM<T>(app) {
    protected val recordRepository by lazy { RecordRepository(app) }
}