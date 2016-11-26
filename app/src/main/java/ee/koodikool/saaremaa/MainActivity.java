package ee.koodikool.saaremaa;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    public EventDetailsFragment currentDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.events:
                        openFragment(new EventPagesFragment());
                        break;
                    case R.id.accomodation:
                        break;
                    case R.id.nightclubs:
                        break;
                    case R.id.restaurants:
                        break;
                }
                return true;
            }
        });

        openFragment(new EventPagesFragment());
    }

    private void openFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().add(R.id.fContainer, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        if(currentDetailsFragment != null) {
            currentDetailsFragment.navigateBack(getSupportActionBar());
            currentDetailsFragment = null;
        }
        else {
            super.onBackPressed();
        }

    }
}
