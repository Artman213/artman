package ua.in.zeusapps.mediar.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

import ua.in.zeusapps.mediar.R;
import ua.in.zeusapps.mediar.models.Event;
import ua.in.zeusapps.mediar.utilities.Utils;

/**
 * Created by- slava on 20.01.18.
 */

public class CustomInfoWindowAdapter implements InfoWindowAdapter {

    // These are both view groups containing an ImageView with id "badge" and two TextViews with id
    // "title" and "snippet".
    private final View mWindow;
    private Context mContext;
    private List<Event> mEvents;

    public CustomInfoWindowAdapter(LayoutInflater inflater, @NonNull List<Event> events, Context context) {
        mWindow = inflater.inflate(R.layout.custom_info_window, null);
        mEvents = events;
        mContext = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        render(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    private void render(Marker marker, View view) {
        int badge = 0;
        // Use the equals() method on a Marker to check for equals.  Do not use ==.
        for (Event event : mEvents) {
            if (marker.getTitle().equals(event.getCard().getTitle())) {
                badge = /*getIconId(event.getCard().getKindID())*/ Utils.getAnimalTypeResource(event.getCard());
            }
        }
        ((ImageView) view.findViewById(R.id.badge)).setImageResource(badge);

        String title = marker.getTitle();
        TextView titleUi = ((TextView) view.findViewById(R.id.title));
        if (title != null) {
            // Spannable string allows us to edit the formatting of the text.
            SpannableString titleText = new SpannableString(title);
            titleText.setSpan(new ForegroundColorSpan(Color.RED), 0, titleText.length(), 0);
            titleUi.setText(titleText);
        } else {
            titleUi.setText("");
        }

        String snippet = marker.getSnippet();
        TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
        if (snippet != null && snippet.length() > 12) {
            SpannableString snippetText = new SpannableString(snippet);
            snippetText.setSpan(new ForegroundColorSpan(Color.MAGENTA), 0, 10, 0);
            snippetText.setSpan(new ForegroundColorSpan(Color.BLUE), 12, snippet.length(), 0);
            snippetUi.setText(snippetText);
        } else {
            snippetUi.setText("");
        }
    }

    ///
    /*private int getIconId(String name) {
        return mContext.getResources().getIdentifier("card_" + name.toLowerCase(), "drawable", mContext.getPackageName());
    }*/
}
