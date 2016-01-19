package org.keionline.keionline;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.TweetUi;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.TweetView;
import com.twitter.sdk.android.tweetui.UserTimeline;

import io.fabric.sdk.android.Fabric;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.keionline.keionline.adapters.VideoAdapter;
import org.keionline.keionline.feedlib.SimpleRss2ParserCallback;
import org.keionline.keionline.feedlib.SimpleRss2Parser;
import org.keionline.keionline.feedlib.RSSItem;

import org.keionline.keionline.adapters.ArticleAdapter;
import org.keionline.keionline.adapters.PagerAdapter;
import org.keionline.keionline.feeds.Article;
import org.keionline.keionline.feeds.Video;
import org.keionline.keionline.helpers.YoutubeConnector;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "";
    private static final String TWITTER_SECRET = "";


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
     * {@link FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.setLogo(R.mipmap.ic_launcher);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Videos"));
        tabLayout.addTab(tabLayout.newTab().setText("Blog"));
        tabLayout.addTab(tabLayout.newTab().setText("Tweets"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabTextColors(Color.BLACK, R.color.colorAccent);


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(1);
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

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
        if (id == R.id.action_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivityForResult(intent, 0);
            //finish();
            return true;
        }
        if (id == R.id.action_refresh) {
            finish();
            startActivity(getIntent());
            return true;
        }
        if (id == R.id.action_donate) {
            String url = "http://keionline.org/donate.html";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://org.keionline.keionline/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://org.keionline.keionline/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    /**
     * An article fragment containing a list of blog posts.
     */
    public static class ArticleFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        //Declaire some variables
        public ListView mList;
        ArrayList<Article> articles = new ArrayList<Article>(15);


        private static final String ARG_SECTION_NUMBER = "section_number";
        private SimpleRss2ParserCallback mCallback;


        public ArticleFragment() {
        }

        public void populateArticleList(ArrayList<Article> articles) {
            mList.setAdapter(new ArticleAdapter(getContext(), R.layout.row_article, articles));
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static ArticleFragment newInstance(int sectionNumber) {
            ArticleFragment fragment = new ArticleFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_main, container, false);

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
                    loadArticle(a.getUrl(), a.getTitle(), a.getPubdate(), a.getCreator());
                    /**
                     * TODO:Have the APP GALLERY play the video
                     */
                }
            });


            return rootView;
        }


        private void loadArticle(String url, String title, String date, String author) { //author does'nt work
            Intent intent = new Intent(getContext(), ArticleView.class);
            intent.putExtra("url", url); //can't pass in article object?
            intent.putExtra("title", title);
            intent.putExtra("date", date);
            intent.putExtra("author", author);
            startActivityForResult(intent, 0); //Activity load = 0
        }

        private SimpleRss2ParserCallback getCallback() {
            if (mCallback == null) {
                mCallback = new SimpleRss2ParserCallback() {

                    @Override
                    public void onFeedParsed(List<RSSItem> items) {
                        for (int i = 0; i < items.size(); i++) {
                            Log.d("SimpleRss2ParserDemo", items.get(i).getTitle());
                            Article a = new Article();
                            a.setTitle(items.get(i).getTitle());
                            a.setPubdate(items.get(i).getDate());
                            a.setUrl(items.get(i).getLink().toString());
                            a.setPubdate(items.get(i).getDate());
                            a.setCreator(items.get(i).getAuthor());
                            a.setDescription(items.get(i).getDescription());
                            articles.add(a);
                        }

                        //mList.setAdapter(
                        //        new MyListAdapter(getContext(),R.layout.row_article, (ArrayList<RSSItem>) items)
                        //);
                        populateArticleList(articles);
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
     * A Video fragment containing a video list view.
     */
    public static class VideoFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        //Declaire some variables
        public ListView mVideoList;

        private static final String ARG_SECTION_NUMBER = "section_number";

        private List<Video> searchResults;

        public VideoFragment() {
        }

        public void populateVideoList(List<Video> videos) {
            mVideoList.setAdapter(new VideoAdapter(getContext(), R.layout.row_video, videos));
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static VideoFragment newInstance(int sectionNumber) {
            VideoFragment fragment = new VideoFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            new GetVideoFeed().execute();
            final View rootView = inflater.inflate(R.layout.fragment_video, container, false);
            mVideoList = (ListView) rootView.findViewById(R.id.list);
            registerForContextMenu(mVideoList);


            mVideoList.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                }
            });
            mVideoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //do somethin
                    Intent y = new Intent(Intent.ACTION_VIEW, Uri.parse(searchResults.get(i).getVideoUrl()));
                    startActivityForResult(y, 0); //ACTIVITY_LOAD = 0?
                }
            });
            /**
             mVideoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Video v = videos.get(i);
            // CHANGE INTENT depending on the
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
            Intent y = new Intent(Intent.ACTION_VIEW, Uri.parse(v.getUrl()));
            startActivityForResult(y, 0); //ACTIVITY_LOAD = 0?
            }

             * TODO:Have the APP GALLERY play the video

            }
            });*/

            return rootView;

        }

        private class GetVideoFeed extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Log.v("Error Parsing Data", "maybe");
                    YoutubeConnector yc = new YoutubeConnector(getContext());
                    searchResults = yc.search();
                    //EpisodeAdapter episodeAdapter = new EpisodeAdapter(getContext(), R.layout.episode_row, episodes);
                } catch (Exception e) {
                    Log.v("Error Parsing Data", e + "");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                //VideoListAdapter.notifyDataSetChanged();
                // AudioListAdapter.notifyDataSetChanged();
                populateVideoList(searchResults);
            }
        }

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class TweetFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public TweetFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static TweetFragment newInstance(int sectionNumber) {
            TweetFragment fragment = new TweetFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Change this to twitter4j???
            final View rootView = inflater.inflate(R.layout.fragment_tweet, container, false);
            final ListView tList = (ListView) rootView.findViewById(R.id.list);

            TwitterAuthConfig authConfig =  new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
            Fabric.with(getActivity(), new TwitterCore(authConfig), new TweetUi());

            final UserTimeline userTimeline = new UserTimeline.Builder()
                    .screenName("KEI_DC")
                    .build();
            final TweetTimelineListAdapter tweetadapter = new TweetTimelineListAdapter.Builder(getActivity())
                    .setTimeline(userTimeline)
                    .build();
            tList.setAdapter(tweetadapter);
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
            // Return a ArticleFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return VideoFragment.newInstance(position + 1);
                case 1:
                    return ArticleFragment.newInstance(position + 1);
                case 2:
                    return TweetFragment.newInstance(position + 1);
                default:
                    return ArticleFragment.newInstance(position + 1);
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
                    return "Videos";
                case 1:
                    return "Blogs";
                case 2:
                    return "Tweets";
            }
            return null;
        }
    }


}
