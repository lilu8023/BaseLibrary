package com.lilu.appcommon.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

/**
 * Description:
 *
 * @author lilu0916 on 2021/11/9
 * No one knows this better than me
 */
public class ViewPageAdapter extends FragmentStateAdapter {

    private List<Fragment> mFragments;

    public ViewPageAdapter(@NonNull FragmentActivity fragmentActivity,List<Fragment> fragments) {
        super(fragmentActivity);
        this.mFragments = fragments;
    }

    public ViewPageAdapter(@NonNull Fragment fragment,List<Fragment> fragments) {
        super(fragment);
        this.mFragments = fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getItemCount() {
        return mFragments == null?0:mFragments.size();
    }
}
