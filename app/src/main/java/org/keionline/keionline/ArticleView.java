package org.keionline.keionline;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.keionline.keionline.R;

import java.io.IOException;

/**
 * Created by fen on 1/11/16.
 */
public class ArticleView extends AppCompatActivity {


    private String title;
    private String url;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webview = new WebView(this);
        setContentView(webview);
        webview.loadData("Loading", "text/html", "UTF-8");

        Bundle extras = getIntent().getExtras();
        final String url = (String) extras.get("url");
        final String title = (String) extras.get("title");
        // Author
        new RetrieveContent().execute(url);
        //WebView webview = new WebView(this);
        //setContentView(webview);
        //webview.loadData(content, "text/html", "UTF-8"); //but don't just load the URL, but load te content div within. Yikes.
    }
    class RetrieveContent extends AsyncTask<String, Void, String>{

        private Exception exception;

        protected String doInBackground(String... urls){
            try {
                String cont = getContent(urls[0]);
                return cont;
            }catch (Exception e){
                this.exception = e;
                return null;
            }
        }

        protected void onPostExecute(String result){
            super.onPostExecute(result);
            WebView webview = new WebView(getBaseContext());
            setContentView(webview);

            webview.loadData(result, "text/html", "UTF-8"); //but don't just
        }
    }
    private String getContent(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Element data = doc.getElementsByClass("content").get(3);// get the third content div,
        String thisone = data.select("img").first().attr("abs:src");
        String cont = data.toString();
        return cont;
    }


}
