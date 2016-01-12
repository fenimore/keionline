package org.keionline.keionline;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import org.keionline.keionline.R;

/**
 * Created by fen on 1/11/16.
 */
public class ArticleView extends Activity {


    private String title;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webview = new WebView(this);
        setContentView(webview);


        webview.loadUrl(url); //but don't just load the URL, but load te content div within. Yikes.

        //    words = (TextView) findViewById(R.id.words);
        //   btnGuess = (Button) findViewById(R.id.btnGuess);



        //    Typeface Dejatf = Typeface.createFromAsset(getAssets(),"fonts/DejaVuSansCondensed.ttf");
        //      words.setTypeface(Dejatf);
//        btnGuess.setTypeface(Dejatf);



    }


}
