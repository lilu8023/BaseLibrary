package com.lilu.appcommon.fragment;

/**
 * Description:
 *
 * @author lilu0916 on 2021/6/2 16:37
 * No one knows this better than me
 */
public interface IFragmentVisibility {

    /**
     * Fragment可见时调用。
     */
    void onVisible();

    /**
     * Fragment不可见时调用。
     */
    void onInvisible();

    /**
     * Fragment第一次可见时调用。
     */
    void onVisibleFirst();

    /**
     * Fragment可见时（第一次除外）调用。
     */
    void onVisibleExceptFirst();

    /**
     * Fragment当前是否对用户可见
     * @return
     */
    boolean isVisibleToUser();
}
