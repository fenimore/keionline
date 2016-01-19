package org.keionline.keionline.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.keionline.keionline.feeds.Article;
import org.keionline.keionline.R;

import java.util.List;

/**
 * Created by fen on 1/11/16.
 */
public class ArticleAdapter extends ArrayAdapter<Article> {
    private static final String CSS = "<head><style type='text/css'> "
            + "body {max-width: 100%; margin: 0.3cm; font-family: sans-serif-light; color: black; background-color: #f6f6f6; line-height: 150%} "
            + "* {max-width: 100%; word-break: break-word}"
            + "h1, h2 {font-weight: normal; line-height: 130%} "
            + "h1 {font-size: 170%; margin-bottom: 0.1em} "
            + "h2 {font-size: 140%} "
            + "a {color: #0099CC}"
            + "h1 a {color: inherit; text-decoration: none}"
            + "img {height: auto} "
            + "pre {white-space: pre-wrap;} "
            + "blockquote {border-left: thick solid #a6a6a6; background-color: #e6e6e6; margin: 0.5em 0 0.5em 0em; padding: 0.5em} "
            + "p {margin: 0.8em 0 0.8em 0} "
            + ".submitted {color: #666666; border-top:1px cyan; border-bottom:1px blue; padding-top:2px; padding-bottom:2px; font-weight:800 } "
            + "ul, ol {margin: 0 0 0.8em 0.6em; padding: 0 0 0 1em} "
            + "ul li, ol li {margin: 0 0 0.8em 0; padding: 0} "
            + "div.button-section {padding: 0.4cm 0; margin: 0; text-align: center} "
            + ".button-section p {margin: 0.1cm 0 0.2cm 0}"
            + ".button-section p.marginfix {margin: 0.5cm 0 0.5cm 0}"
            + ".button-section input, .button-section a {font-family: sans-serif-light; font-size: 100%; color: #FFFFFF; background-color:#52A7DF; text-decoration: none; border: none; border-radius:0.2cm; padding: 0.3cm} "
            + "</style><meta name='viewport' content='width=device-width'/></head>";

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
            v = vi.inflate(R.layout.row_article, null);
        }

        Article a = getItem(position);
        if (a != null) {
            //ImageView img = (ImageView) v.findViewById(R.id.row_image);
            WebView desc = (WebView) v.findViewById(R.id.row_description);
            TextView txt = (TextView) v.findViewById(R.id.row_title);
            if (txt != null) {
                    txt.setText(a.getTitle());
            }
            if (desc != null) {
                    Document doc = Jsoup.parse(a.getDescription());
                    Elements select = doc.select("img");
                    // Change the links to absolute!! so that images work
                    for(Element e:select){
                        e.attr("src", e.absUrl("src"));
                    }
                    select = doc.select("a");
                    for(Element e:select){
                        e.attr("href", e.absUrl("href"));
                    }
                    String description = doc.toString();
                    desc.loadData(CSS + a.getPubdate()+ "<hr>" + description, "text/html", "UTF-8");

            }
            desc.setOnTouchListener(new WebViewClickListener(desc, parent, position));

        }

        return v;
    }
    // http://stackoverflow.com/questions/4973228/android-webview-inside-listview-onclick-event-issues
    private class WebViewClickListener implements View.OnTouchListener {
        private int position;
        private ViewGroup vg;
        private WebView wv;

        public WebViewClickListener(WebView wv, ViewGroup vg, int position) {
            this.vg = vg;
            this.position = position;
            this.wv = wv;
        }

        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();

            switch (action) {
                case MotionEvent.ACTION_CANCEL:
                    return true;
                case MotionEvent.ACTION_UP:
                    sendClick();
                    return true;
            }

            return false;
        }

        public void sendClick() {
            ListView lv = (ListView) vg;
            lv.performItemClick(wv, position, 0);
        }
    }
}
