package com.github2136.ledger.base

import android.app.Application
import com.github2136.basemvvm.download.DownloadUtil
import com.github2136.basemvvm.loadmore.BaseLoadMoreVM
import com.jxgis.jxmismobile.repository.CollectDataRepository
import com.jxgis.jxmismobile.repository.FireRepository
import com.jxgis.jxmismobile.repository.MapRepository
import com.jxgis.jxmismobile.repository.OtherRepository
import com.jxgis.jxmismobile.repository.PatrolTrackRepository
import com.jxgis.jxmismobile.repository.SettingRepository
import com.jxgis.jxmismobile.repository.UserRepository
import com.jxgis.jxmismobile.repository.WildFireRepository

/**
 * Created by 44569 on 2023/12/22
 */
abstract class AppBaseLoadMoreVM<T>(app: Application) : BaseLoadMoreVM<T>(app) {
    protected val collectDataRepository by lazy { CollectDataRepository(app) }
    protected val fireRepository by lazy { FireRepository(app) }
    protected val mapRepository by lazy { MapRepository(app) }
    protected val otherRepository by lazy { OtherRepository(app) }
    protected val patrolTrackRepository by lazy { PatrolTrackRepository(app) }
    protected val settingRepository by lazy { SettingRepository(app) }
    protected val userRepository by lazy { UserRepository(app) }
    protected val wildFireRepository by lazy { WildFireRepository(app) }

    val downloadUtil by lazy { DownloadUtil.getInstance(app) }

    val userId by lazy { userRepository.getUserId() }
}