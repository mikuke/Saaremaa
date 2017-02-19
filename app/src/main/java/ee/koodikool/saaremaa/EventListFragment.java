package ee.koodikool.saaremaa;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventListFragment extends Fragment {

    public List<Event> events;
    private static String TAG_LINK = "TAG_LINK";
    private static String TAG_TOOLBAR = "TAG_TOOLBAR";
    public String browserTag = "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36";

    @BindView(R.id.main_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.eventListProgressbar)
    ProgressBar progressBar;
    @BindView(R.id.events_missing)
    LinearLayout empty_list_textview;

    public EventListFragment() {
        // Required empty public constructor
    }

    public static EventListFragment newInstance(String link, boolean usingToolbar) {
        EventListFragment fragment = new EventListFragment();
        Bundle args = new Bundle();
        args.putString(TAG_LINK, link);
        args.putBoolean(TAG_TOOLBAR, usingToolbar);
        fragment.setArguments(args);
        return fragment;
    }

    public static EventListFragment newInstance(String link) {
        EventListFragment fragment = new EventListFragment();
        Bundle args = new Bundle();
        args.putString(TAG_LINK, link);
        args.putBoolean(TAG_TOOLBAR, true);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_event_list, container, false);
        ButterKnife.bind(this, view.getRootView());

        ((MainActivity) getActivity()).showBackArrow(false);

        if (events == null) new ParseListPageTask().execute();
        else setupRecycleView();

        return view;
    }

    private void setupRecycleView() {
        progressBar.setVisibility(View.INVISIBLE);
        Log.d("!!!!!!!!!!!!!!", "settingUpRecyclerView, events.size(): " + events.size());
        if(events.size() == 0) {
            empty_list_textview.setVisibility(View.VISIBLE);
            return;
        }
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        EventAdapter adapter = new EventAdapter(events, getActivity());
        adapter.setOnClickListener(new EventAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                openDetailsFragment(events.get(position));
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private class ParseListPageTask extends AsyncTask<Void, List<Event>, List<Event>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        private List<Event> eventList = new ArrayList<>();

        protected List<Event> doInBackground(Void... params) {
            org.jsoup.nodes.Document doc = null;
            try {
                doc = Jsoup.connect(getArguments().getString(TAG_LINK)).userAgent(browserTag)
                        .timeout(10000).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements events = null;
            if(doc == null) return eventList;
            if(getArguments().getString(TAG_LINK) != null &&
                    getArguments().getString(TAG_LINK).equals("http://www.saaremaasuvi.ee"))
                events = doc.select("ul#uritused").select("li");
            else events = doc.select("div#yritus");
            if (events == null) return eventList;
            for (int i = 0; i < events.size(); i++) {
                Element event = events.get(i);

                String day = event.getElementById("paevanimi").text();
                String date = event.getElementById("kuupaev").text();
                String heading = event.getElementById("pealk").text();
                String detailsLink = event.attr("onclick").replace("location.href='", "").replace("';", "");
                String location = event.getElementById("asukoht").text();
                String category = event.getElementById("kategooria").text().replace("| Kategooria: ", "");

                eventList.add(new Event(day, date, heading, location, category, detailsLink));
            }
            return eventList;
        }


        protected void onPostExecute(List<Event> eventList) {
            events = eventList;
            setupRecycleView();
        }
    }

    public void openDetailsFragment(Event event) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        EventDetailsFragment fragment = EventDetailsFragment.newInstance(event.day, event.date,
                event.heading, event.location, event.category, event.detailsLink, getArguments().getBoolean(TAG_TOOLBAR));
        fragmentTransaction.replace(R.id.fragmentContainer, fragment).addToBackStack(null).commit();
        ((MainActivity)getActivity()).currentDetailsFragment = fragment;
    }
}