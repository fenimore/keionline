package org.keionline.keionline.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.keionline.keionline.R;

import java.util.ArrayList;

import org.keionline.keionline.feedlib.RSSItem;

/**
 * Created by fen on 1/12/16.
 */
public class MyListAdapter extends ArrayAdapter<RSSItem> {

    private ArrayList<RSSItem> items;
    private Context ctx;
    private int layout;

    public MyListAdapter(Context context, int layout, ArrayList<RSSItem> items) {
        super(context, layout, items);
        this.items = items;
        this.ctx = context;
        this.layout = layout;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(layout, null);
        }

        RSSItem o = items.get(position);
        if (o != null) {
            TextView pubDate = ((TextView) v.findViewById(R.id.row_creator));
            TextView title = ((TextView) v.findViewById(R.id.row_title));
            //TextView tvDescription = ((TextView) v.findViewById(R.id.tvDescription));
            //TextView tvLnk = ((TextView) v.findViewById(R.id.tvLnk));

            if (pubDate != null) {
                pubDate.setText(o.getDate() + o.getClass());
            }

            if (title != null) {
                title.setText(o.getTitle());
            }

            /**
            if (tvDescription != null) {
                tvDescription.setText(o.getContent());
            }

            if (tvLnk != null) {
                tvLnk.setText(o.getLink().toExternalForm());
                Linkify.addLinks(tvLnk, Linkify.ALL);
            }
             */
        }

        return v;
    }
}