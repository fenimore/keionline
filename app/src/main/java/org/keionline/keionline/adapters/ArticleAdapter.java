package org.keionline.keionline.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.keionline.keionline.feeds.Article;
import org.keionline.keionline.R;

import java.util.List;

/**
 * Created by fen on 1/11/16.
 */
public class ArticleAdapter extends ArrayAdapter<Article> {

    public ArticleAdapter(Context context, int textViewResourceId){
        super(context, textViewResourceId);
    }

    public ArticleAdapter(Context context, int resource, List<Article> articles){
        super(context, resource, articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;

        if(v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.article_row, null);
        }

        Article a = getItem(position);
        if (a != null) {
            //ImageView img = (ImageView) v.findViewById(R.id.row_image);
            TextView tag = (TextView) v.findViewById(R.id.row_creator);
            TextView txt = (TextView) v.findViewById(R.id.row_title);
            //try {
            //    Picasso.with(getContext()).load(e.getImageUrl()).into(img);
                //Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                //img.setImageBitmap(image);
            //} catch (Exception ex) {
            //    Log.v("Episode Adapter", "exception");
            //}
            if (txt != null) {
                    txt.setText(a.getTitle());
            }
            if (tag != null) {
                    tag.setText(a.getCreator());
            }

        }

        return v;
    }

}
