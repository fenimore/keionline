/*
 *
 *   Copyright (C) 2016 Fenimore Love
 *
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.keionline.keionline;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
        setContentView(R.layout.fragment_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayOptions(0);
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.setTitle(R.string.KEI);
        toolbar.setLogo(R.drawable.ic_action_keiicon);
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