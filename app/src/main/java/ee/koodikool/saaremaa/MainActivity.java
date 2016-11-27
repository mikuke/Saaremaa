package ee.koodikool.saaremaa;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private BottomNavigationView bottomNavigationView;
    public EventDetailsFragment currentDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                switch (item.getItemId()) {
                    case R.id.events:
                        openFragment(new EventPagesFragment());
                        break;
                    case R.id.accomodation:
                        openFragment(new HotelsFragment());
                        break;
                    case R.id.nightclubs:
                        openFragment(EventListFragment
                                .newInstance("http://saaremaasuvi.ee/kategooria/ooklubi/", false));
                        break;
                    case R.id.restaurants:
                        openFragment(new RestaurantsFragment());
                        break;
                }
                currentDetailsFragment = null;
                return true;
            }
        });

        openFragment(new EventPagesFragment());

    }

    private void openFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fContainer, fragment).commit();
        if(fragment.getClass() == EventPagesFragment.class) showTabs(true);
        else showTabs(false);
    }

    @Override
    public void onBackPressed() {
        if (currentDetailsFragment != null) {
            currentDetailsFragment.navigateBack();
            currentDetailsFragment = null;
        } else {
            super.onBackPressed();
        }
    }
    private void showTabs(boolean show) {
        tabLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
