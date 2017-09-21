package com.mursitaffandi.mursitaffandi_baking.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import com.google.android.exoplayer2.util.Util;
import com.mursitaffandi.mursitaffandi_baking.activity.DetailFoodListActivity;
import com.mursitaffandi.mursitaffandi_baking.R;
import com.mursitaffandi.mursitaffandi_baking.model.Step;
import com.mursitaffandi.mursitaffandi_baking.utilities.ConstantString;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * An activity representing a single DetailFood detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link DetailFoodListActivity}.
 */
public class DetailFoodStep extends Fragment implements View.OnClickListener {
    @BindView(R.id.exoui_frgdetailstep)
    SimpleExoPlayerView stepExoplayer;


    @BindView(R.id.detail_step_image)
    ImageView ivDetailStep;

    @BindView(R.id.tv_frgdetailstep_stepinstruction)
    TextView tvDetailStep;

    @BindView(R.id.btn_frgdetailstep_prev)
    Button btnPrev;

    @BindView(R.id.btn_frgdetailstep_next)
    Button btnNext;

    private Step mStep;
    private long mPlaybackPosition;
    private int mCurrentWindow;
    private boolean mPlayWhenReady;
    private int mNumber;
    private boolean mFirst;
    private boolean mLast;

    private SimpleExoPlayer mSimpleExoPlayer;
    private Bundle mBundle;

    public DetailFoodStep() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detailfoodstep, container, false);
        ButterKnife.bind(this, view);
        btnNext.setOnClickListener(this);
        btnPrev.setOnClickListener(this);
        ivDetailStep.setVisibility(View.GONE);
        mBundle = getArguments();
        mStep = mBundle.getParcelable(ConstantString.TAG_BUNDLE_STEP);
        mNumber = mBundle.getInt(ConstantString.TAG_STEP_NUMBER);
        mFirst = mBundle.getBoolean(ConstantString.TAG_STEP_FIRST);
        mLast = mBundle.getBoolean(ConstantString.TAG_STEP_LAST);

        ivDetailStep.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(mStep.getThumbnailURL()) && !mStep.getThumbnailURL().substring(mStep.getThumbnailURL().length() - 4, mStep.getThumbnailURL().length()).equals(".mp4")) {
            ivDetailStep.setVisibility(View.VISIBLE);
            Glide.with(getContext())
                    .load(mStep.getThumbnailURL())
                    .placeholder(R.mipmap.ic_launcher)
                    .into(ivDetailStep);
        }

        btnNext.setVisibility(View.VISIBLE);
        btnPrev.setVisibility(View.VISIBLE);

        if (mFirst) btnPrev.setVisibility(View.GONE);
        if (mLast) btnNext.setVisibility(View.GONE);

        return view;
    }

    private MediaSource createMediaSource(Uri uri) {
        return new ExtractorMediaSource(uri,
                new DefaultHttpDataSourceFactory("ua"),
                new DefaultExtractorsFactory(), null, null);
    }

    private void initPlayer() {
        mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getContext()),
                new DefaultTrackSelector(),
                new DefaultLoadControl()
        );

        stepExoplayer.setPlayer(mSimpleExoPlayer);

        mSimpleExoPlayer.setPlayWhenReady(mPlayWhenReady);
mSimpleExoPlayer.seekTo(mCurrentWindow, mPlaybackPosition);

        if (TextUtils.isEmpty(mStep.getVideoURL()) && TextUtils.isEmpty(mStep.getThumbnailURL())) {
            stepExoplayer.setVisibility(View.GONE);
        } else {
            stepExoplayer.setVisibility(View.VISIBLE);
            Uri uri = null;
            if (!TextUtils.isEmpty(mStep.getVideoURL())) {
                uri = Uri.parse(mStep.getVideoURL());
            } else if (!TextUtils.isEmpty(mStep.getThumbnailURL()) && mStep.getThumbnailURL().substring(mStep.getThumbnailURL().length() - 4, mStep.getThumbnailURL().length()).equals(".mp4")) {
                uri = Uri.parse(mStep.getThumbnailURL());
            }
            MediaSource mediaSource = createMediaSource(uri);
            mSimpleExoPlayer.prepare(mediaSource, true, false);
        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 24) {
            initPlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || mSimpleExoPlayer == null)) {
            initPlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releaseExoPlayer();
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24) {
            releaseExoPlayer();
        }
    }

    private void releaseExoPlayer() {
        if (mSimpleExoPlayer != null) {
            mPlaybackPosition = mSimpleExoPlayer.getCurrentPosition();
            mCurrentWindow = mSimpleExoPlayer.getCurrentWindowIndex();
            mPlayWhenReady = mSimpleExoPlayer.getPlayWhenReady();
            mSimpleExoPlayer.release();
            mSimpleExoPlayer = null;
        }
    }
}
