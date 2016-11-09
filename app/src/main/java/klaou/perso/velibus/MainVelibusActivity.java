package klaou.perso.velibus;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import klaou.perso.velibus.R;
import klaou.perso.velibus.fragments.MyBusFragment;
import klaou.perso.velibus.fragments.MyVelibFragment;

public class MainVelibusActivity extends AppCompatActivity {

    // The PagerAdapter that will provide
    //  fragments for each of the sections.
    // We use a FragmentPagerAdapter derivative, which will keep every
    // loaded fragment in memory. If this becomes too memory intensive, it
    // may be best to switch to a
    // android.support.v4.app.FragmentStatePagerAdapter
    private SectionsPagerAdapter mSectionsPagerAdapter;

    // host the section contents
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_velibus);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }



    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                                                                                                                  //
    //          Fragment Pager Adapter                                                                                                  //
    //          > returns a fragment corresponding to one of the sections/tabs/pages.                                                   //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch(position)
            {
                case 0 : return MyBusFragment.newInstance(position);

                case 1 : return MyVelibFragment.newInstance(position);

            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.bus_tab_name);
                case 1:
                    return getString(R.string.velib_tab_name);
            }
            return null;
        }
    }




    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                                                                                                                  //
    //          USELESS STUFF                                                                                                           //
    //                                                                                                                                  //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // No menu
        return true;
    }
}
