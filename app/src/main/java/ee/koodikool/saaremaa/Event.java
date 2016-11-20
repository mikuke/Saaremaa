package ee.koodikool.saaremaa;

import java.util.ArrayList;
import java.util.List;

public class Event {
    String day;
    String date;
    String heading;
    String location;
    String category;
    String detailsLink;

    public Event(String day, String date, String heading, String location, String category, String detailsLink) {
        this.day = day;
        this.date = date;
        this.heading = heading;
        this.location = location;
        this.category = category;
        this.detailsLink = detailsLink;
    }

    public List<Event> events;

    public void createDummyEventList() {
        events = new ArrayList<>();
        events.add(new Event("Esmaspäev", "19.10", "Ürituse nimi 1", "Kuressaare lossihoov", "Kino", ""));
        events.add(new Event("Teisipäev", "20.10", "Ürituse nimi 2", "Kuressaare linnateater", "Teater", ""));
        events.add(new Event("Kolmapäev", "21.10", "Ürituse nimi 3", "Auriga parkla", "Random", ""));
        events.add(new Event("Neljapäev", "22.10", "Ürituse nimi 4", "Veski trahter", "Võidusõit", ""));
    }

}
