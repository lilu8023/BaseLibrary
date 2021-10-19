package com.wan.main.entity

import com.lilu.apptool.gson.IAfterDeserializeAction
import com.lilu.apptool.gson.NonNullField
import com.lilu.apptool.utils.StringUtils

/**
 * Description:
 * 首页轮播图实体类
 * @author lilu0916 on 2021/6/16
 *  No one knows this better than me
 */
data class BannerEntity(var desc:String,
                        var id:Int,
                        var imagePath:String,
                        var isVisible:Int,
                        var order:Int,
                        var title:String,
                        var type:Int,
                        var url:String,
                        @NonNullField var nonNull:String):IAfterDeserializeAction {


    override fun doAfterDeserialize() {
        if(StringUtils.isEmpty(nonNull)){
            nonNull = "不能为空"
        }
    }


}