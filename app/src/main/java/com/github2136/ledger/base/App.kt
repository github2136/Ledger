package com.github2136.ledger.base

import com.github2136.basemvvm.BaseApplication
import com.github2136.ledger.BuildConfig
import com.github2136.util.CrashHandler
import com.github2136.util.DateUtil
import com.github2136.util.JsonUtil
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder

/**
 * Created by YB on 2021/4/14
 */
class App : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        app = this


        JsonUtil.mGson = GsonBuilder()
            .setDateFormat(DateUtil.DATE_PATTERN_YMDHMS)
            .addSerializationExclusionStrategy(object : ExclusionStrategy {
                override fun shouldSkipClass(clazz: Class<*>?): Boolean {
                    return false
                }

                override fun shouldSkipField(f: FieldAttributes?): Boolean {
                    return f?.name?.startsWith("_") == true
                }
            })
            .create()

        CrashHandler.getInstance(this, BuildConfig.DEBUG).setCallback(object : CrashHandler.CrashHandlerCallback {
            override fun addParam(info: HashMap<String, String>) {

            }

            override fun finishAll() {
                this@App.finishAll()
            }

            override fun submitLog(info: Map<String, String>, exception: String) {
                // otherRepository.postAppErrorLocal(
                //     AppError(
                //         BuildConfig.PID,
                //         info[CrashHandler.APP_VERSION_CODE]!!,
                //         info[CrashHandler.APP_VERSION_NAME]!!,
                //         info[CrashHandler.BRAND]!!,
                //         info[CrashHandler.MODEL]!!,
                //         info[CrashHandler.SYS_RELEASE_CODE]!!,
                //         info[CrashHandler.SYS_RELEASE_NAME]!!,
                //         info[CrashHandler.ABIS]!!,
                //         exception,
                //         userRepository.getUserId(),
                //         UUID.randomUUID().toString(),
                //         Date(),
                //         false
                //     )
                // )

            }
        })
    }

    companion object {
        lateinit var app: App
    }
}