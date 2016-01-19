package org.keionline.keionline.helpers;

import android.content.Context;
import android.util.Log;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import org.keionline.keionline.R;
import org.keionline.keionline.feeds.Video;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fen on 1/17/16.
 */
public class YoutubeConnector {
    private YouTube youtube;
    private YouTube.Search.List query;
    // Must be static?
    private static final String APIKEYVERYPRIVATE = "";

    public YoutubeConnector(Context context) {
        YouTube yogurttube;
        yogurttube = new YouTube.Builder(new NetHttpTransport(),
                new JacksonFactory(), new HttpRequestInitializer(){
            @Override
            public void initialize(HttpRequest hr) throws IOException {}
        }).setApplicationName(context.getString(R.string.app_name)).build();

        youtube = new YouTube.Builder(new NetHttpTransport(),
                new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest httpRequest) throws IOException {}
        }).setApplicationName(context.getString(R.string.app_name)).build();

        try {
            query = youtube.search().list("id,snippet");
            query.setKey(APIKEYVERYPRIVATE);
            query.setType("video");
            query.setFields("items(id/videoId,snippet/title,snippet/description,snippet/thumbnails/default/url)");
        }catch(IOException e){
            Log.d("YC", "Could not initialize: " + e);
        }
    }
    public List<Video> search(){
        query.setChannelId("UCKCwOrg52WVg9-VQ4IIi6hg");//kei youtube channel UCKCwOrg52WVg9-VQ4IIi6hg
        try{
            SearchListResponse response = query.execute();
            List<SearchResult> results = response.getItems();

            List<Video> items = new ArrayList<Video>();
            for(SearchResult result:results){
                Video item = new Video();
                item.setTitle(result.getSnippet().getTitle());
                item.setDescription(result.getSnippet().getDescription());
                item.setThumbnailURL(result.getSnippet().getThumbnails().getDefault().getUrl());
                item.setId(result.getId().getVideoId());
                item.setVideoUrl("https://www.youtube.com/watch?v="+result.getId().getVideoId());

                items.add(item);
            }
            return items;
        }catch(IOException e){
            Log.d("YC", "Could not search: "+e);
            return null;
        }
    }
}
