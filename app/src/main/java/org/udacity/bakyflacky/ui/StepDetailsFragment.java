package org.udacity.bakyflacky.ui;

import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ClippingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.udacity.bakyflacky.R;
import org.udacity.bakyflacky.recipe.Step;
import org.udacity.bakyflacky.utility.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailsFragment extends Fragment {

    private final static String TAG = StepDetailsFragment.class.getSimpleName();

    private final static String IMAGE_TYPE = "image/";
    private final static String VIDEO_TYPE = "video/";

    @BindView(R.id.tv_step_short_description) TextView description;
    @BindView(R.id.btn_step_instruction) Button instructions;
    @BindView(R.id.img_step_thumbnail) ImageView thumbnail;
    @BindView(R.id.player_view) SimpleExoPlayerView playerView;

    private static final String STEP_OBJECT = "StepObject";

    private static final String PLAYER_STATE = "PlayerState";
    private static final String PLAYER_POSITION = "PlayerPosition";

    private boolean isPlaying = false;
    private long position = 0;

    private Step step;
    private SimpleExoPlayer player;

    private boolean isFullscreenMode = false;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(STEP_OBJECT, step);
        if (player == null) {
            outState.putBoolean(PLAYER_STATE, isPlaying);
            outState.putLong(PLAYER_POSITION, position);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            step = savedInstanceState.getParcelable(STEP_OBJECT);
            isPlaying = savedInstanceState.getBoolean(PLAYER_STATE);
            position = savedInstanceState.getLong(PLAYER_POSITION);
        } else {
            isPlaying = false;
            position = 0;
        }

        View rootView = inflater.inflate(R.layout.fragment_steps_details, container, false);
        ButterKnife.bind(this, rootView);

        isFullscreenMode = (getResources().getBoolean(R.bool.isLandscape) &&
                !getResources().getBoolean(R.bool.isTablet));

        updateView();
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFullscreenMode) {
            hideSystemUi();
        }
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    public void setStep(Step step) {
        this.step= step;
    }

    private void updateView() {
        if (isFullscreenMode) {
            this.description.setVisibility(View.GONE);
            this.instructions.setVisibility(View.GONE);
            this.thumbnail.setVisibility(View.GONE);
        } else {
            this.description.setText(step.shortDescription);
            this.description.setVisibility(View.VISIBLE);
            this.instructions.setText(step.description);
            this.instructions.setVisibility(View.VISIBLE);
            updateImageView();
       }
    }

    private void updateImageView() {
        String type = getMimeType(step.thumbnailURL);
        if (type != null && type.contains(IMAGE_TYPE)) {
            ImageLoader.fetchIntoView(step.thumbnailURL, thumbnail);
        }
    }

    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private static String getMimeType(String url) {
        String type = "";
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }


    private void initializePlayer() {
        if (step == null)
            return;

        String type = getMimeType(step.videoURL);
        if (!TextUtils.isEmpty(type) && type.contains(VIDEO_TYPE)) {
            Context context = getContext();
            player = ExoPlayerFactory.newSimpleInstance(getContext());
            playerView.setPlayer(player);
            playerView.setVisibility(View.VISIBLE);

            Uri videoUri = Uri.parse(step.videoURL);
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                    Util.getUserAgent(context, getString(R.string.app_name)));
            MediaSource source = new ExtractorMediaSource(
                    videoUri,
                    dataSourceFactory,
                    new DefaultExtractorsFactory(),
                    null,
                    null);

            player.prepare(source, false, true);
            player.seekTo(position);
            player.setPlayWhenReady(isPlaying);
        }
    }

    private void releasePlayer() {
        if (player != null) {
            isPlaying = player.getPlayWhenReady();
            position = player.getCurrentPosition();
            player.release();
            player = null;
        }
    }
}
