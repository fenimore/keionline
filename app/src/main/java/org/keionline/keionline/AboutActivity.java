package org.keionline.keionline;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by fen on 1/14/16.
 */
public class AboutActivity extends AppCompatActivity {

    //Declaire some variables
    public TextView Txt1;
    public TextView Txt2;
    public TextView Txt3;
    public TextView Txt4;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_fragment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayOptions(0);
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.setTitle(R.string.KEI);
        toolbar.setLogo(R.mipmap.ic_launcher);
        // TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        // textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

        Txt1 = (TextView) findViewById(R.id.about_app);
        Txt1.setText(R.string.about_app);
        Txt2 = (TextView) findViewById(R.id.about_kei);
        Txt2.setText(R.string.about_kei);
        Txt3 = (TextView) findViewById(R.id.about_instructions);
        Txt3.setText(R.string.about_instructions);
        Txt4 = (TextView) findViewById(R.id.about_info);
        Txt4.setText(R.string.about_info);

    }
}