package com.github2136.ledger.base

import android.os.Build
import com.github2136.basemvvm.BaseApplication
import com.github2136.basemvvm.SSLUtil
import com.github2136.basemvvm.download.OkHttpManager
import com.github2136.util.CrashHandler
import com.github2136.util.DateUtil
import com.github2136.util.JsonUtil
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder
import com.jxgis.jxmismobile.common.Constants
import com.jxgis.jxmismobile.common.NotificationUtil
import com.jxgis.jxmismobile.model.entity.AppError
import com.jxgis.jxmismobile.repository.OtherRepository
import com.jxgis.jxmismobile.repository.UserRepository
import com.jxgis.jxmismobile.work.AppErrorWorker
import com.orhanobut.logger.Logger
import com.tencent.smtt.export.external.TbsCoreSettings
import com.tencent.smtt.sdk.QbSdk
import com.tencent.smtt.sdk.QbSdk.PreInitCallback
import okhttp3.OkHttpClient
import org.android.agoo.huawei.HuaWeiRegister
import org.android.agoo.xiaomi.MiPushRegistar
import java.util.*
import javax.net.ssl.HostnameVerifier


/**
 * Created by YB on 2021/4/14
 */
class App : BaseApplication() {
    override val logEnable = BuildConfig.DEBUG

    private val otherRepository by lazy { OtherRepository(this) }
    private val userRepository by lazy { UserRepository(this) }


    override fun onCreate() {
        super.onCreate()
        app = this
        val sSlObj = SSLUtil.notVerified()
        OkHttpManager.client = OkHttpClient().newBuilder()
            .sslSocketFactory(sSlObj.socketFactory, sSlObj.trustManager)
            .hostnameVerifier(HostnameVerifier { hostname, session -> true })
            .build()
        initUmeng()
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
        NotificationUtil.init(this)
        CrashHandler.getInstance(this, BuildConfig.DEBUG).setCallback(object : CrashHandler.CrashHandlerCallback {
            override fun addParam(info: HashMap<String, String>) {
                info["UserId"] = userRepository.getUserId()
            }

            override fun finishAll() {
                this@App.finishAll()
            }

            override fun submitLog(info: Map<String, String>, exception: String) {
                otherRepository.postAppErrorLocal(
                    AppError(
                        BuildConfig.PID,
                        info[CrashHandler.APP_VERSION_CODE]!!,
                        info[CrashHandler.APP_VERSION_NAME]!!,
                        info[CrashHandler.BRAND]!!,
                        info[CrashHandler.MODEL]!!,
                        info[CrashHandler.SYS_RELEASE_CODE]!!,
                        info[CrashHandler.SYS_RELEASE_NAME]!!,
                        info[CrashHandler.ABIS]!!,
                        exception,
                        userRepository.getUserId(),
                        UUID.randomUUID().toString(),
                        Date(),
                        false
                    )
                )
                AppErrorWorker.launch(this@App)
            }
        })
    }

    fun initUmeng() {
        if (userRepository.getAgreePrivacy()) {
            UMConfigure.setLogEnabled(logEnable)
            UMConfigure.preInit(this, BuildConfig.UMENG_APP_KEY, BuildConfig.UMENG_CHANNEL)
            // 在此处调用基础组件包提供的初始化函数 相应信息可在应用管理 -> 应用信息 中找到 http://message.umeng.com/list/apps
            UMConfigure.init(this, BuildConfig.UMENG_APP_KEY, BuildConfig.UMENG_CHANNEL, UMConfigure.DEVICE_TYPE_PHONE, BuildConfig.UMENG_MESSAGE_SECRET)
            HuaWeiRegister.register(this)
            MiPushRegistar.register(this, BuildConfig.UMENG_XIAOMI_ID, BuildConfig.UMENG_XIAOMI_KEY, false)
            //获取消息推送代理示例
            val mPushAgent = PushAgent.getInstance(this)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                // val channelId: String? = when {
                //     SettingUtil.isXiaomi -> Constants.CHANNEL_XIAOMI_FIRE
                //     SettingUtil.isHuawei -> Constants.CHANNEL_HUAWEI_FIRE
                //     else -> null
                // }
                // channelId?.apply {
                //     val fireChannel = NotificationChannel(this, "告警", NotificationManager.IMPORTANCE_HIGH)
                //     fireChannel.description = "卫星热点，视频告警等推送信息"
                //     fireChannel.enableLights(true)
                //     fireChannel.lightColor = Color.RED
                //     fireChannel.setShowBadge(true)
                //     fireChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                //     fireChannel.enableVibration(true)
                //     manager.createNotificationChannel(fireChannel)
                // }
                //
                // val defChannel = NotificationChannel(Constants.CHANNEL_DEFAULT, "Default", NotificationManager.IMPORTANCE_HIGH)
                // defChannel.enableLights(true)
                // defChannel.lightColor = Color.RED
                // defChannel.setShowBadge(true)
                // defChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                // defChannel.enableVibration(true)
                // manager.createNotificationChannel(defChannel)

            }
            // 自定义通知栏样式
            // val messageHandler: UmengMessageHandler = object : UmengMessageHandler() {
            //     override fun getNotification(context: Context, msg: UMessage): Notification {
            //         return when (msg.builder_id) {
            //             1 -> {
            //                 //热点告警
            //                 val channelId: String = when {
            //                     SettingUtil.isXiaomi -> Constants.CHANNEL_XIAOMI_FIRE
            //                     SettingUtil.isHuawei -> Constants.CHANNEL_HUAWEI_FIRE
            //                     else -> Constants.CHANNEL_DEFAULT
            //                 }
            //                 val mNotification = NotificationCompat.Builder(context, channelId)
            //                 mNotification
            //                     .setSmallIcon(R.mipmap.ic_launcher)
            //                     .setWhen(System.currentTimeMillis())
            //                     .setDefaults(Notification.DEFAULT_VIBRATE or Notification.DEFAULT_LIGHTS)
            //                     .setTicker(resources.getString(R.string.app_name))
            //                     .setContentTitle(msg.title)
            //                     .setContentText(msg.text)
            //                     .setAutoCancel(true)
            //                 mNotification.build()
            //             }
            //             else -> {
            //                 val mNotification = NotificationCompat.Builder(context, Constants.CHANNEL_DEFAULT)
            //                 mNotification
            //                     .setSmallIcon(R.mipmap.ic_launcher)
            //                     .setWhen(System.currentTimeMillis())
            //                     .setDefaults(Notification.DEFAULT_VIBRATE or Notification.DEFAULT_LIGHTS)
            //                     .setTicker(resources.getString(R.string.app_name))
            //                     .setContentTitle(msg.title)
            //                     .setContentText(msg.text)
            //                     .setAutoCancel(true)
            //                 mNotification.build()
            //             }
            //         }
            //     }
            // }
            // mPushAgent.messageHandler = messageHandler

            //不限制推送通知条数
            mPushAgent.displayNotificationNumber = 0
            //注册推送服务，每次调用register方法都会回调该接口
            mPushAgent.register(object : UPushRegisterCallback {
                override fun onSuccess(deviceToken: String) {
                    //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                    Logger.t(Constants.TAG_UMENG).i("注册成功:deviceToken:-------->  $deviceToken")
                }

                override fun onFailure(s: String, s1: String) {
                    Logger.t(Constants.TAG_UMENG).e("注册失败:-------->  s:$s,s1:$s1")
                }
            })
            MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO)
            //为了提高内核占比，在初始化前可配置允许移动网络下载内核（大小 40-50 MB）。默认移动网络不下载
            QbSdk.setDownloadWithoutWifi(true)
            QbSdk.initX5Environment(this, object : PreInitCallback {
                override fun onCoreInitFinished() {
                    // 内核初始化完成，可能为系统内核，也可能为系统内核
                }
                /**
                 * 预初始化结束
                 * 由于X5内核体积较大，需要依赖网络动态下发，所以当内核不存在的时候，默认会回调false，此时将会使用系统内核代替
                 * @param isX5 是否使用X5内核
                 */
                override fun onViewInitFinished(isX5: Boolean) {}
            })
            val map = HashMap<String, Any>()
            map[TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER] = true
            map[TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE] = true
            QbSdk.initTbsSettings(map)
        }
    }

    companion object {
        lateinit var app: App
    }
}