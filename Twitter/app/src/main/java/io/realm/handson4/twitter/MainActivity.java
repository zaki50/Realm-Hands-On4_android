package io.realm.handson4.twitter;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ViewPager pager = (ViewPager) findViewById(R.id.pager);
        //noinspection ConstantConditions
        pager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
    }

    @Override
    protected void onStart() {
        super.onStart();

        startService(new Intent(this, UpdateService.class));
    }

    private static final class MainPagerAdapter extends FragmentStatePagerAdapter {

        MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "タイムライン";
                default:
                    throw new RuntimeException("unexpected position: " + position);
            }
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new TimelineFragment();
                default:
                    throw new RuntimeException("unexpected position: " + position);
            }
        }
    }
}
