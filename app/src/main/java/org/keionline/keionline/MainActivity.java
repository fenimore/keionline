package org.keionline.keionline;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import org.keionline.keionline.feedlib.SimpleRss2ParserCallback;
import org.keionline.keionline.feedlib.SimpleRss2Parser;
import org.keionline.keionline.feedlib.RSSItem;

import org.keionline.keionline.adapters.ArticleAdapter;
import org.keionline.keionline.adapters.MyListAdapter;
import org.keionline.keionline.adapters.PagerAdapter;
import org.keionline.keionline.feeds.Article;

public class MainActivity extends AppCompatActivity {

    private Context mContext;

    private EditText etFeedUrl;
    private Button btnLoad;
    private ListView mList;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Blog"));
        tabLayout.addTab(tabLayout.newTab().setText("Video"));
        tabLayout.addTab(tabLayout.newTab().setText("About"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount()); //but why is it final?



        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void loadArticle(String url) {
        Intent intent = new Intent(this, ArticleView.class);
        intent.putExtra("key", url); //can't pass in article object?
        startActivityForResult(intent, 0); //Activity load = 0
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        //Declaire some variables
        public ListView mList;
        ArrayList<Article> articles = new ArrayList<Article>(15);

        private static final String ARG_SECTION_NUMBER = "section_number";
        private SimpleRss2ParserCallback mCallback;


        public PlaceholderFragment() {
        }

        public void populateList(ArrayList<Article> articles) {
            mList.setAdapter(new ArticleAdapter(getContext(), R.layout.article_row, articles));
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            SimpleRss2Parser parser = new SimpleRss2Parser("http://keionline.org/blog/feed", getCallback());
            parser.parseAsync();
            //new GetBlog().execute("http://keionline.org/blog/feed");
            //new GetAudioFeed().execute("https://www.youtube.com/feeds/videos.xml?channel_id=UCKCwOrg52WVg9-VQ4IIi6hg"); // must be called second

            mList = (ListView) rootView.findViewById(R.id.list);
            registerForContextMenu(mList);

            mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Article a = articles.get(i);
                    // CHANGE INTENT depending on the
                    if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                        Intent y = new Intent(Intent.ACTION_VIEW, Uri.parse(a.getUrl()));
                        startActivityForResult(y, 0); //ACTIVITY_LOAD = 0?
                    } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                        // Audio Array is broken
                        // play as a service
                        //Intent y = new Intent(Intent.ACTION_VIEW, Uri.parse(a.getAudioUrl()));
                        //startActivityForResult(y, 0); //ACTIVITY_LOAD = 0?
                    }
                    /**
                     * TODO:Have the APP GALLERY play the video
                     */
                }
            });

            return rootView;
        }

        private SimpleRss2ParserCallback getCallback(){
            if(mCallback == null){
                mCallback = new SimpleRss2ParserCallback() {

                    @Override
                    public void onFeedParsed(List<RSSItem> items) {
                        for(int i = 0; i < items.size(); i++){
                            Log.d("SimpleRss2ParserDemo",items.get(i).getTitle());
                            Article a = new Article();
                            a.setTitle(items.get(i).getTitle());
                            a.setPubdate(items.get(i).getDate());
                            a.setUrl(items.get(i).getLink().toString());
                            a.setDescription(items.get(i).getContent());
                            articles.add(a);
                        }

                        mList.setAdapter(
                                new MyListAdapter(getContext(),R.layout.article_row, (ArrayList<RSSItem>) items)
                        );
                    }

                    @Override
                    public void onError(Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                };
            }

            return mCallback;
        }
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class AboutFragment extends Fragment {

        //Declaire some variables
        public TextView Txt1;
        public TextView Txt2;
        public TextView Txt3;
        public TextView Txt4;
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public AboutFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static AboutFragment newInstance(int sectionNumber) {
            AboutFragment fragment = new AboutFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.about_fragment, container, false);
            // TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            // textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

            Txt1 = (TextView) rootView.findViewById(R.id.about_app);
            Txt1.setText(R.string.about_app);
            Txt2 = (TextView) rootView.findViewById(R.id.about_kei);
            Txt2.setText(R.string.about_kei);
            Txt3 = (TextView) rootView.findViewById(R.id.about_instructions);
            Txt3.setText(R.string.about_instructions);
            Txt4 = (TextView) rootView.findViewById(R.id.about_info);
            Txt4.setText(R.string.about_info);
            return rootView;

        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch(position) {
                case 0: return PlaceholderFragment.newInstance(position + 1);
                case 1: return PlaceholderFragment.newInstance(position + 1);
                case 2: return AboutFragment.newInstance(position + 1);
                default: return PlaceholderFragment.newInstance(position + 1);
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
