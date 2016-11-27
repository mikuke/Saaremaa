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
    private EventPagesFragment eventFragment;
    private RestaurantsFragment restaurantsFragment;
    private HotelsFragment hotelsFragment;
    private NightclubsFragment nightclubsFragment;

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
                switch (item.getItemId()) {
                    case R.id.events:
                        if(currentDetailsFragment != null) currentDetailsFragment.navigateBack(getSupportActionBar());
                        else { openFragment(new EventPagesFragment());
                        showTabs(true); }
                        break;
                    case R.id.accomodation:
                        openFragment(new HotelsFragment());
                        showTabs(false);
                        break;
                    case R.id.nightclubs:
                        openFragment(new NightclubsFragment());
                        showTabs(false);
                        break;
                    case R.id.restaurants:
                        openFragment(new RestaurantsFragment());
                        showTabs(false);
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
    }

    @Override
    public void onBackPressed() {
        if (currentDetailsFragment != null) {
            currentDetailsFragment.navigateBack(getSupportActionBar());
            currentDetailsFragment = null;
        } else {
            super.onBackPressed();
        }

    }
    private void showTabs(boolean show) {
        tabLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
