package com.github2136.ledger.view.activity.record.record_list

import android.app.Application
import com.github2136.basemvvm.loadmore.BaseLoadMoreAdapter
import com.github2136.ledger.base.AppBaseLoadMoreVM
import com.github2136.ledger.model.entity.Record

/**
 * Created by 44569 on 2024/1/4
 */
class RecordListVM(app: Application) : AppBaseLoadMoreVM<Record>(app) {
    override fun initAdapter() = RecordAdapter()

    override fun initData() {
        setData(mutableListOf())
    }

    override fun loadMoreData() {

    }
}