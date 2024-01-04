package com.github2136.ledger.view.activity.record.record_list

import com.github2136.base.ViewHolderRecyclerView
import com.github2136.basemvvm.loadmore.BaseLoadMoreAdapter
import com.github2136.ledger.R
import com.github2136.ledger.databinding.ItemRecordBinding
import com.github2136.ledger.model.entity.Record

/**
 * Created by 44569 on 2024/1/4
 */
class RecordAdapter : BaseLoadMoreAdapter<Record, ItemRecordBinding>() {
    override fun getLayoutIdByList(viewType: Int) = R.layout.item_record
    override fun onBindView(t: Record, bind: ItemRecordBinding, holder: ViewHolderRecyclerView, position: Int) {

    }
}