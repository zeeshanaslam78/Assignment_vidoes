package com.xeeshi.assignment_vidoes;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.serhatsurguvec.swipablelayout.SwipeableLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ZEESHAN on 21/02/2018.
 */

public class PlayerActivity extends AppCompatActivity {
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();

    private static final String TAG = PlayerActivity.class.getSimpleName();

    @BindView(R.id.exoplayer_view) SimpleExoPlayerView playerView;
    @BindView(R.id.swipableLayout) SwipeableLayout swipeableLayout;
    @BindView(R.id.imgBtn_volume) ImageButton imgBtnVolume;
    @BindView(R.id.progressbar) ProgressBar progressBar;

    SimpleExoPlayer exoPlayer;

    private float currentVolume = 0f;
    private boolean isVolumeUp = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_activity);
        ButterKnife.bind(this);


        Uri videoUri = getVideoUriFromIntent();


        TrackSelector trackSelector = new DefaultTrackSelector(BANDWIDTH_METER);
        exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

        playerView.setUseController(true);
        playerView.setPlayer(exoPlayer);



        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory defaultDataSourceFactory = new DefaultDataSourceFactory(
                this,
                Util.getUserAgent(this, "Football Video"),
                BANDWIDTH_METER);

        MediaSource mediaSource = new HlsMediaSource.Factory(defaultDataSourceFactory).createMediaSource(videoUri);
        //final LoopingMediaSource loopingMediaSource = new LoopingMediaSource(ms);

        // Prepare the player with the source.
        exoPlayer.prepare(mediaSource);

        exoPlayer.setPlayWhenReady(true);
        currentVolume = exoPlayer.getVolume();


        exoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest) {
                Log.d(TAG, "onTimelineChanged timeline " + timeline.getPeriodCount());
            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                Log.d(TAG, "onTracksChanged");
            }

            @Override
            public void onLoadingChanged(boolean isLoading) {
                Log.d(TAG, "onLoadingChanged isLoading " + isLoading);
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                Log.d(TAG, "onPlayerStateChanged playWhenReady " + playWhenReady + " playbackState " + playbackState);
                if (playbackState == Player.STATE_BUFFERING)
                    progressBar.setVisibility(View.VISIBLE);
                else if (playbackState == Player.STATE_ENDED)
                    exoPlayer.stop();
                else
                    progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {
                Log.d(TAG, "onRepeatModeChanged");
            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
                Log.d(TAG, "onShuffleModeEnabledChanged");
            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                Log.d(TAG, "onPlayerError");
                exoPlayer.stop();

                exoPlayer.setPlayWhenReady(true);
                currentVolume = exoPlayer.getVolume();
            }

            @Override
            public void onPositionDiscontinuity(int reason) {
                Log.d(TAG, "onPositionDiscontinuity");
            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
                Log.d(TAG, "onPlaybackParametersChanged playbackParameters " + playbackParameters.speed);
            }

            @Override
            public void onSeekProcessed() {
                Log.d(TAG, "onSeekProcessed");
            }
        });


        swipeableLayout.setOnLayoutCloseListener(new SwipeableLayout.OnLayoutCloseListener() {
            @Override
            public void OnLayoutClosed() {
                finish();
            }
        });


    }

    private Uri getVideoUriFromIntent() {
        String url = getIntent().getStringExtra("URL");
        Log.d(TAG, "url " + url);
        return Uri.parse(url);
    }

    @OnClick(R.id.btn_close)
    public void closeActivity(View view) {
        finish();
    }

    @OnClick(R.id.imgBtn_volume)
    public void muteOrUnmute(View view) {

        if (isVolumeUp) {
            isVolumeUp = false;
            imgBtnVolume.setImageResource(R.drawable.ic_volume_up_white_24dp);
            exoPlayer.setVolume(currentVolume);

        } else {
            isVolumeUp = true;
            imgBtnVolume.setImageResource(R.drawable.ic_volume_off_white_24dp);
            exoPlayer.setVolume(0f);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        exoPlayer.setPlayWhenReady(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        exoPlayer.setPlayWhenReady(true);
    }

    @Override
    protected void onDestroy() {
        exoPlayer.release();
        super.onDestroy();
    }
}
