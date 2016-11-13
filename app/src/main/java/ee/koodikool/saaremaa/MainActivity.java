package ee.koodikool.saaremaa;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public List<Event> events;
    public String browserTag = "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParsePageTask task = new ParsePageTask();
        task.execute();
    }

    private void setupRecycleView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        EventAdapter adapter = new EventAdapter(events, this);
        recyclerView.setAdapter(adapter);
    }

    private class ParsePageTask extends AsyncTask<Void, List<Event>, List<Event>> {

        private List<Event> eventList = new ArrayList<>();

        protected List<Event> doInBackground(Void... params) {
            org.jsoup.nodes.Document doc = null;
            try {
                doc = Jsoup.connect("http://www.saaremaasuvi.ee/").userAgent(browserTag).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements events = doc.select("div#yritus");

            for (int i = 0; i < events.size(); i++) {
                Element event = events.get(i);

                String day = event.getElementById("paevanimi").text();
                String date = event.getElementById("kuupaev").text();
                String heading = event.getElementById("pealk").text();
                String detailsLink = event.attr("onclick").replace("location.href='", "").replace("';", "");
                String location = event.getElementById("asukoht").text();
                String category = event.getElementById("kategooria").text().replace("| Kategooria: ", "");

                eventList.add(new Event(day, date, heading, location, category));
            }
            return eventList;
        }


        protected void onPostExecute(List<Event> eventList) {
            events = eventList;
            setupRecycleView();
        }
        
    }
}
