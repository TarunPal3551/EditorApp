package editor.avilaksh.com.edi;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class OnBoardingViewPagerAdapter extends FragmentStatePagerAdapter {
    int numberofpages = 3;

    public OnBoardingViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return new Onboarding2fragment();
        } else if (position == 2) {
            return new Onboarding3fragment();
        }
        else {
            return new Onboarding1fragment();
        }


    }

    @Override
    public int getCount()
    {
        return numberofpages;
    }
}
