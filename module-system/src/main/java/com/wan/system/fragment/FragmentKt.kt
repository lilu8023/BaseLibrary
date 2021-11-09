package com.wan.system.fragment

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Description:
 * @author lilu0916 on 2021/11/5
 *  No one knows this better than me
 */
fun <T:ViewModel>Fragment.get(clazz:Class<T>) : ViewModel{

    return ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(clazz)

}