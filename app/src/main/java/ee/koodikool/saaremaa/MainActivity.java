package ee.koodikool.saaremaa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public List<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupRecycleView();
    }

    private void setupRecycleView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        createDummyEventList();
        EventAdapter adapter = new EventAdapter(events, this);
        recyclerView.setAdapter(adapter);
    }

    public void createDummyEventList() {
        events = new ArrayList<>();
        events.add(new Event("Esmaspäev", "19.10", "Ürituse nimi 1", "Kuressaare lossihoov", "Kino"));
        events.add(new Event("Teisipäev", "20.10", "Ürituse nimi 2", "Kuressaare linnateater", "Teater"));
        events.add(new Event("Kolmapäev", "21.10", "Ürituse nimi 3", "Auriga parkla", "Random"));
        events.add(new Event("Neljapäev", "22.10", "Ürituse nimi 4", "Veski trahter", "Võidusõit"));
    }
}
