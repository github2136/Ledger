package com.github2136.ledger.base

import androidx.databinding.ViewDataBinding
import com.github2136.basemvvm.loadmore.BaseLoadMoreActivity
import com.github2136.basemvvm.loadmore.BaseLoadMoreFragment
import com.github2136.basemvvm.loadmore.BaseLoadMoreVM

/**
 * Created by 44569 on 2023/7/24
 */
abstract class AppBaseLoadMoreFragment<V : BaseLoadMoreVM<*>, B : ViewDataBinding> : BaseLoadMoreFragment<V, B>() {
}