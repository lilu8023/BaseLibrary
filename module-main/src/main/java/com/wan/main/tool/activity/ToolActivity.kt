package com.wan.main.tool.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.lilu.appcommon.activity.BaseActivity
import com.lilu.apptool.livedata.LiveStatus
import com.lilu.apptool.router.RouterPath
import com.orhanobut.logger.Logger
import com.wan.main.R
import com.wan.main.databinding.ActivityToolBinding
import com.wan.main.entity.ToolEntity
import com.wan.main.model.MainModel
import com.wan.main.router.MainRouterPath
import com.wan.main.tool.adapter.ActivityToolAdapter
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * Description:
 * 常用工具页
 * @author lilu0916 on 2021/11/3
 *  No one knows this better than me
 */
@Route(path = MainRouterPath.ACTIVITY_TOOL)
class ToolActivity : BaseActivity() {

    var toolBinding : ActivityToolBinding ?= null

    private var toolList= ArrayList<ToolEntity.ToolChild>()
    lateinit var mAdapter: ActivityToolAdapter

    private val vm by lazy {
        ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(MainModel::class.java)
    }

    override fun getContentView(): Int {
        return R.layout.activity_tool
    }

    override fun init(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)

        setTitle("常用工具")

        toolBinding = DataBindingUtil.bind(mContentView)

        mAdapter = ActivityToolAdapter(toolList)
        //列表item的点击事件
        mAdapter.setOnItemClickListener{_,_,position ->
            //点击跳转到H5页面，并携带地址
            ARouter.getInstance()
                    .build(RouterPath.ACTIVITY_WEB)
                    .withString("url",toolList[position].link)
                    .navigation()


        }
        toolBinding?.rvAcTool?.apply {

            layoutManager = LinearLayoutManager(this@ToolActivity)
            adapter = mAdapter

        }
        vm.toolList.observe(this,{
            if (it.status == LiveStatus.SUCCESS){
                toolList.clear()
                toolList.addAll(it.data)

                mAdapter.notifyDataSetChanged()

                showSuccess()
            }else{
                showError()
            }
        })

        vm.getMainTool()
    }
}