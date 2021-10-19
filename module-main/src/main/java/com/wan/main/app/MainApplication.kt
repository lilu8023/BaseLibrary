package com.wan.main.app

import android.app.Application
import com.lilu.appcommon.app.IApplication
import com.lilu.apptool.update.Update
import com.wan.main.update.UpdateService

/**
 * Description:
 * @author lilu0916 on 2021/8/11
 *  No one knows this better than me
 */
class MainApplication : IApplication {

    override fun init(application: Application?) {

        application?.let {
            Update.getInstance()
                    .setIUpdateHttpService(UpdateService())
                    .init(it)
        }

    }
}