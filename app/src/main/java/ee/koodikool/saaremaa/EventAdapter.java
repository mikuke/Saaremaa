package ee.koodikool.saaremaa;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    List<Event> eventList;
    Context context;

    public EventAdapter(List<Event> eventList, Context context) {
        this.eventList = eventList;
        this.context = context;
    }
    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_layout, parent, false);
        EventViewHolder viewHolder = new EventViewHolder(card);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        holder.day.setText(eventList.get(position).day);
        holder.date.setText(eventList.get(position).date);
        holder.heading.setText(eventList.get(position).heading);
        holder.location.setText(eventList.get(position).location);
        holder.category.setText(eventList.get(position).category);
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardView;
        TextView day;
        TextView date;
        TextView heading;
        TextView location;
        TextView category;

        public EventViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.mainCardView);
            day = (TextView) itemView.findViewById(R.id.dayName);
            date = (TextView) itemView.findViewById(R.id.date);
            heading = (TextView) itemView.findViewById(R.id.heading);
            location = (TextView) itemView.findViewById(R.id.location);
            category = (TextView) itemView.findViewById(R.id.category);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Event event = eventList.get(position);
                //Todo: Call method from mainActivity to open detailed view;
                Toast.makeText(context, "Event clicked: " + event.heading, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
