package editor.avilaksh.com.editorapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class OnBoardingActivity extends FragmentActivity {
    ViewPager mPager;
    PagerAdapter mPagerAdapter;
    public LinearLayout dot_layout;
    public ImageView[] dots;
    Button browse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        browse=(Button)findViewById(R.id.buttonbrowse);
        Typeface typeface = ResourcesCompat.getFont(OnBoardingActivity.this, R.font.poppins);
        browse.setTypeface(typeface);
       // signin=(Button)findViewById(R.id.buttonsignin);
        //signin.setTypeface(typeface);
        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OnBoardingActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });

        mPager=(ViewPager)findViewById(R.id.viewpageronboarding);
        dot_layout=(LinearLayout)findViewById(R.id.dotslayoutonboarding);
        mPagerAdapter=new OnBoardingViewPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
       createdots(mPager.getCurrentItem());
       mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
           @Override
           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

           }

           @Override
           public void onPageSelected(int position) {
               for (int i=0;i<3;i++){

                   dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_dots));
               }
               dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dots));


           }

           @Override
           public void onPageScrollStateChanged(int state) {

           }
       });

    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem()==0){
            super.onBackPressed();


        }
        else {
            mPager.setCurrentItem(mPager.getCurrentItem()-1);
        }


    }
    public void createdots(int current_position) {
        if (dot_layout != null) {
            dot_layout.removeAllViews();
        }
        dots = new ImageView[3];
        for (int i = 0; i < 3; i++) {
            dots[i] = new ImageView(this);
            if (i == current_position) {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.active_dots));
            } else {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.default_dots));
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(3, 0, 3, 0);
            dot_layout.addView(dots[i], params);


        }

    }
}
