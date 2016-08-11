package org.keionline.keionline.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.keionline.keionline.R;
import org.keionline.keionline.feeds.Video;

import java.util.List;

/**
 * Created by fen on 1/11/16.
 */
public class VideoAdapter extends ArrayAdapter<Video> {
    Bitmap thumbnail;

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
            cv = vi.inflate(R.layout.row_video, null);
        }

        Video v = getItem(position);
        if (v != null) {
            TextView description = (TextView) cv.findViewById(R.id.row_summary);
            TextView title = (TextView) cv.findViewById(R.id.row_heading);
            ImageView image = (ImageView) cv.findViewById(R.id.row_image);
            try {
                Picasso.with(getContext()).load(v.getThumbnailURL()).into(image);
                //Picasso.with(getContext()).load(v.getThumbnailURL()).into(image);
                // //load(.resize(300, 200))
            } catch (Exception ex) {
                Log.v("Video Adapter", "exception");
            }
            if (title != null) {
                title.setText(v.getTitle());
            }
            if (description != null) {
                description.setText(v.getDescription());
            }

        }

        return cv;
    }
    private Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = 12;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
}
