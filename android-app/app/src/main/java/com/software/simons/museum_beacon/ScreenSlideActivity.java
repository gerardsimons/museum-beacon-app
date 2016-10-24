package com.software.simons.museum_beacon;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ScreenSlideActivity extends FragmentActivity {

    private ViewPager viewPager;

    private PagerAdapter pagerAdapter;

    private int imageIdCounter = 0;
    private final int[] imageIds = {R.drawable.handleiding, R.drawable.main_deck, R.drawable.upper_promenade_deck, R.drawable.bridge_deck};

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putInt(ScreenSlidePageFragment.SSPF_IMAGE_KEY, imageIds[position]);
            ScreenSlidePageFragment sspFragment = new ScreenSlidePageFragment();

            sspFragment.setArguments(bundle);
            return sspFragment;
        }

        @Override
        public int getCount() {
            return imageIds.length;
        }
    }

    class CustomPagerAdapter extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;

        public CustomPagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return imageIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((View) object);
        }

        public Object instantiateItem(ViewGroup context, int position) {
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), imageIds[position]));

            ((ViewPager) context).addView(imageView);

            return imageView;
        }

        @Override
//        public void destroyItem(ViewGroup container, int position, Object object)
        public void destroyItem(ViewGroup collection, int position, Object o) {
            View view = (View)o;
            ((ViewPager) collection).removeView(view);
            view = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_screen);

        // Instantiate a ViewPager and a PagerAdapter.
        viewPager = (ViewPager) findViewById(R.id.pager);
//        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        pagerAdapter = new CustomPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
    }
}
