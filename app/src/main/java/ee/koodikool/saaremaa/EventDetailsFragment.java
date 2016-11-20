package ee.koodikool.saaremaa;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EventDetailsFragment extends Fragment {

    private static String KEY_EVENT_LINK = "KEY_EVENT_LINK";
    private String detailsLink;

    public EventDetailsFragment() {
        // Required empty public constructor
    }

    public static EventDetailsFragment newInstance(String eventLink) {
        EventDetailsFragment fragment = new EventDetailsFragment();
        Bundle arguments = new Bundle();
        arguments.putString(KEY_EVENT_LINK, eventLink);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_details, container, false);

        detailsLink = getArguments().getString(KEY_EVENT_LINK);
        return view;
    }

}
