package mx.com.othings.edcore.Adapters.Schedule;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import java.util.List;

public class SchedulePagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;

    public SchedulePagerAdapter(FragmentManager fragmentManager , List<Fragment> fragmentList){
        super(fragmentManager);

        this.fragmentList = fragmentList;

    }


    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return false;
    }



}
