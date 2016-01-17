package org.keionline.keionline.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.keionline.keionline.R;
import org.keionline.keionline.feeds.Article;
import org.keionline.keionline.feeds.Video;

import java.util.List;

/**
 * Created by fen on 1/11/16.
 */
public class VideoAdapter extends ArrayAdapter<Video> {

    public VideoAdapter(Context context, int textViewResourceId){
        super(context, textViewResourceId);
    }

    public VideoAdapter(Context context, int resource, List<Video> videos){
        super(context, resource, videos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View cv = convertView;

        if(cv == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            cv = vi.inflate(R.layout.article_row, null);
        }

        Video v = getItem(position);
        if (v != null) {
            //ImageView img = (ImageView) v.findViewById(R.id.row_image);
            TextView tag = (TextView) cv.findViewById(R.id.row_creator);
            TextView txt = (TextView) cv.findViewById(R.id.row_title);
            //try {
            //    Picasso.with(getContext()).load(e.getImageUrl()).into(img);
            //Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            //img.setImageBitmap(image);
            //} catch (Exception ex) {
            //    Log.v("Episode Adapter", "exception");
            //}
            if (txt != null) {
                txt.setText(v.getTitle());
            }

        }

        return cv;
    }
}
