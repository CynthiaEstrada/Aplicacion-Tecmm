package mx.com.othings.edcore.Adapters.Schedule;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
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
