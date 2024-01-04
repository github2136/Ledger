package com.github2136.ledger.base

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import com.github2136.basemvvm.BaseFragment
import com.github2136.basemvvm.BaseVM

/**
 * Created by 44569 on 2023/7/24
 */
abstract class AppBaseFragment<V : BaseVM, B : ViewDataBinding> : BaseFragment<V, B>() {}