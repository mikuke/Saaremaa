package ee.koodikool.saaremaa;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EventPagesFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private EventsPagerAdapter adapter;

    public EventPagesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_pages, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    private void setupViewPager(ViewPager viewPager) {

        if (adapter == null) adapter = new EventsPagerAdapter(getActivity().getSupportFragmentManager());

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
}
