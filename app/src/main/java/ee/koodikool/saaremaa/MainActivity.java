package ee.koodikool.saaremaa;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment("http://www.saaremaasuvi.ee", "KÖIK");
        adapter.addFragment("http://www.saaremaasuvi.ee/kategooria/festival/", "FESTIVAL");
        adapter.addFragment("http://www.saaremaasuvi.ee/kategooria/kino/", "KINO");
        adapter.addFragment("http://www.saaremaasuvi.ee/kategooria/konkurss/", "KONKURSS");
        adapter.addFragment("http://www.saaremaasuvi.ee/kategooria/kontsert/", "KONTSERT");
        adapter.addFragment("http://www.saaremaasuvi.ee/kategooria/konverents/", "KONVERENTS");
        adapter.addFragment("http://www.saaremaasuvi.ee/kategooria/laager/", "LAAGER");
        adapter.addFragment("http://www.saaremaasuvi.ee/kategooria/laat/", "LAAT");
        adapter.addFragment("http://www.saaremaasuvi.ee/kategooria/lastele/", "LASTELE");
        adapter.addFragment("http://www.saaremaasuvi.ee/kategooria/muinastulede-oo/", "MUINASTULEDE ÖÖ");
        adapter.addFragment("http://www.saaremaasuvi.ee/kategooria/muu/", "MUU");
        adapter.addFragment("http://www.saaremaasuvi.ee/kategooria/naitus/", "NÄITUS");
        adapter.addFragment("http://www.saaremaasuvi.ee/kategooria/opituba/", "ÕPITUBA");
        adapter.addFragment("http://www.saaremaasuvi.ee/kategooria/ooklubi/", "ÖÖKLUBI");
        adapter.addFragment("http://www.saaremaasuvi.ee/kategooria/seminar/", "SEMINAR");
        adapter.addFragment("http://www.saaremaasuvi.ee/kategooria/sport/", "SPORT");
        adapter.addFragment("http://www.saaremaasuvi.ee/kategooria/sundmus/", "SÜNDMUS");
        adapter.addFragment("http://www.saaremaasuvi.ee/kategooria/teater/", "TEATER");

        viewPager.setAdapter(adapter);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public void addFragment(String link, String title) {
            Fragment fragment = EventListFragment.newInstance(link);
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }
}
