package org.keionline.keionline;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

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

    private TextView testing;
    private WebView webview;

    private String title;
    private String url;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        webview = (WebView) findViewById(R.id.webview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.setLogo(R.mipmap.ic_launcher);
        //setContentView(R.layout.activity_article);
        //TextView testing = (TextView) findViewById(R.id.testing);

        //WebView webview = new WebView(this);
        //setContentView(webview);
        //webview.loadData("Loading", "text/html", "UTF-8");


        Bundle extras = getIntent().getExtras();
        url = (String) extras.get("url");
        title = (String) extras.get("title");
        //content = (String) extras.get("content");
        //Log.d("content", content);
        //webview.loadData("Loading", "text/html", "UTF-8");
        // Author
        new RetrieveContent().execute(url);


        //WebView webview = new WebView(this);
        //setContentView(webview);
        //webview.loadData(content, "text/html", "UTF-8"); //but don't just load the URL, but load te content div within. Yikes.
    }

    public interface OnTaskCompleted{
        void onTaskCompleted();
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
            //setContentView(R.layout.activity_article);
            //webview = new WebView(getBaseContext());
            setContentView(webview);
            webview.loadData(result, "text/html", "UTF-8"); //but don't just
            // webview.getSettings().setLoadWithOverviewMode(true);
           // if(result != null) testing.setText(result);
            //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            //setSupportActionBar(toolbar);
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //toolbar.setTitleTextColor(Color.BLACK);
            //toolbar.setLogo(R.mipmap.ic_launcher);
        }
    }

    // Use Jsoup to get the content? This is sloppy
    private String getContent(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Element data = doc.getElementsByClass("content").get(3);// get the third content div,
        String cont = data.toString();
        Log.d("woop", cont);
        return cont;
    }


}
