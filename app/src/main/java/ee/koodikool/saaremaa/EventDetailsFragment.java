package ee.koodikool.saaremaa;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventDetailsFragment extends Fragment {

    private static String KEY_EVENT_DAY = "KEY_EVENT_DAY";
    private static String KEY_EVENT_DATE = "KEY_EVENT_DATE";
    private static String KEY_EVENT_HEADING = "KEY_EVENT_HEADING";
    private static String KEY_EVENT_LOCATION = "KEY_EVENT_LOCATION";
    private static String KEY_EVENT_CATEGORY = "KEY_EVENT_CATEGORY";
    private static String KEY_EVENT_LINK = "KEY_EVENT_LINK";
    private static String KEY_USING_TABS = "KEY_USING_TABS";
    public String browserTag = "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36";
    private String detailsLink;
    @BindView(R.id.detailsProgressbar)
    ProgressBar progressBar;
    @BindView(R.id.details_description)
    TextView descriptionTextView;
    @BindView(R.id.detailsCard)
    CardView card;
    @BindView(R.id.details_heading)
    TextView headingView;
    @BindView(R.id.details_day)
    TextView dayView;
    @BindView(R.id.details_date)
    TextView dateView;
    @BindView(R.id.details_location)
    TextView locationView;
    @BindView(R.id.details_category)
    TextView categoryView;

    private String descriptionText = "";

    public EventDetailsFragment() {
        // Required empty public constructor
    }

    public static EventDetailsFragment newInstance(String day, String date, String heading, String location,
                                                   String category, String eventLink, boolean usingTabs) {
        EventDetailsFragment fragment = new EventDetailsFragment();
        Bundle arguments = new Bundle();
        arguments.putString(KEY_EVENT_DAY, day);
        arguments.putString(KEY_EVENT_DATE, date);
        arguments.putString(KEY_EVENT_HEADING, heading);
        arguments.putString(KEY_EVENT_LOCATION, location);
        arguments.putString(KEY_EVENT_CATEGORY, category);
        arguments.putString(KEY_EVENT_LINK, eventLink);
        arguments.putBoolean(KEY_USING_TABS, usingTabs);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_details, container, false);
        ButterKnife.bind(this, view.getRootView());
        ((MainActivity) getActivity()).showTabs(false);
        ((MainActivity) getActivity()).showBackArrow(true);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateBack();
            }
        });

        detailsLink = getArguments().getString(KEY_EVENT_LINK);
        ParseDetailsPageTask task = new ParseDetailsPageTask();
        task.execute();

        return view;
    }

    private class ParseDetailsPageTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        protected Void doInBackground(Void... params) {
            org.jsoup.nodes.Document doc = null;
            try {
                doc = Jsoup.connect(detailsLink).userAgent(browserTag).timeout(10000).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Element eventElement = doc != null ? doc.getElementById("sisu-single") : null;

            if (eventElement== null) return null;

            Elements paragraphs = eventElement.select("p");
            for(Element e : paragraphs) {
                descriptionText += e.text();
                if(!(paragraphs.last() == e)) descriptionText += "\n \n";
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            setupView();
            descriptionTextView.setText(descriptionText);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void setupView() {
        Bundle args = getArguments();

        dayView.setText(args.getString(KEY_EVENT_DAY));
        dateView.setText(args.getString(KEY_EVENT_DATE));
        headingView.setText(args.getString(KEY_EVENT_HEADING));
        locationView.setText(args.getString(KEY_EVENT_LOCATION));
        categoryView.setText(args.getString(KEY_EVENT_CATEGORY));

        card.setVisibility(View.VISIBLE);
    }

    public void navigateBack(){
        FragmentManager fragmentManager = getFragmentManager();
        if(fragmentManager != null && fragmentManager.getBackStackEntryCount() != 0)
            fragmentManager.popBackStack();
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        if(getArguments().getBoolean(KEY_USING_TABS)) getActivity().findViewById(R.id.tabs).setVisibility(View.VISIBLE);
    }

}
