package com.wan.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lilu.apptool.imageloader.ImageLoader
import com.wan.main.R
import com.wan.main.entity.BannerEntity
import com.youth.banner.adapter.BannerAdapter

/**
 * Description:
 * @author lilu0916 on 2021/6/16
 *  No one knows this better than me
 */
class WanBannerAdapter(var mList:MutableList<BannerEntity>) : BannerAdapter<BannerEntity, RecyclerView.ViewHolder>(mList) {


    inner class WanBannerImgHolder(itemView:View) : RecyclerView.ViewHolder(itemView){

        var ivItemBanner : ImageView ?= null
        init {
            ivItemBanner = itemView.findViewById(R.id.iv_item_banner)
        }
    }

    inner class WanBannerTextHolder(itemView:View) : RecyclerView.ViewHolder(itemView){

        var tvItemBanner : TextView ?= null
        init {
            tvItemBanner = itemView.findViewById(R.id.tv_item_banner)
        }

    }

//    override fun getItemViewType(position: Int): Int {
//
//        return getData(getRealPosition(position)).type
//    }

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            0 ->{
                WanBannerTextHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_banner_text,parent,false))
            }
            else ->{
                WanBannerImgHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_banner_img,parent,false))
            }
        }
    }

    override fun onBindView(holder: RecyclerView.ViewHolder?, data: BannerEntity?, position: Int, size: Int) {
        if(holder is WanBannerImgHolder){

            ImageLoader.getInstance().displayImage(holder.ivItemBanner?.context,mList[position].imagePath,holder.ivItemBanner)
        }else{
            (holder as WanBannerTextHolder ).tvItemBanner?.text = mList[position].imagePath
        }
    }
}