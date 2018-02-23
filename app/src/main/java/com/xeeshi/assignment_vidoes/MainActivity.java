package com.xeeshi.assignment_vidoes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview) RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(
                        MainActivity.this,
                        LinearLayoutManager.HORIZONTAL,
                        false);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        List<String> videoUrlList = new ArrayList<>();
        videoUrlList.add("https://d2nsbgzitfs2gt.cloudfront.net/vods3/_definst_/mp4:amazons3/pitch-video-prod/22b1747fa3074b5482e60bf9c0fb25eb500bd0a2/playlist.m3u8");
        videoUrlList.add("https://d2nsbgzitfs2gt.cloudfront.net/vods3/_definst_/mp4:amazons3/pitch-video-prod/a2f95065ac92313c3bf5d053d7779e5185f0aea4/playlist.m3u8");
        videoUrlList.add("https://d2nsbgzitfs2gt.cloudfront.net/vods3/_definst_/mp4:amazons3/pitch-video-prod/5c1d2de9407ea0935c9ca49f5c8f45bbf9646dac/playlist.m3u8");


        VideoAdapter videoAdapter = new VideoAdapter(this, videoUrlList);
        recyclerView.setAdapter(videoAdapter);

    }
}
