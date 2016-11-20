package ee.koodikool.saaremaa;

import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EventListFragment extends Fragment {

    public List<Event> events;
    public String browserTag = "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36";
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    public EventListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_event_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.main_recyclerview);
        progressBar = (ProgressBar) view.findViewById(R.id.eventListProgressbar);

        ParsePageTask task = new ParsePageTask();
        task.execute();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void setupRecycleView() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        EventAdapter adapter = new EventAdapter(events, getActivity());
        adapter.setOnClickListener(new EventAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                String detailsLink = events.get(position).detailsLink;
                openDetailsFragment(detailsLink);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private class ParsePageTask extends AsyncTask<Void, List<Event>, List<Event>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        private List<Event> eventList = new ArrayList<>();

        protected List<Event> doInBackground(Void... params) {
            org.jsoup.nodes.Document doc = null;
            try {
                doc = Jsoup.connect("http://www.saaremaasuvi.ee/").userAgent(browserTag).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements events = doc != null ? doc.select("div#yritus") : null;

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
            progressBar.setVisibility(View.INVISIBLE);
            setupRecycleView();
        }
    }

    public void openDetailsFragment(String link) {
        FragmentManager fragmentManager = this.getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        EventDetailsFragment fragment = EventDetailsFragment.newInstance(link);
        fragmentTransaction.replace(R.id.fragmentContainer, fragment).addToBackStack(null).commit();
    }
}
