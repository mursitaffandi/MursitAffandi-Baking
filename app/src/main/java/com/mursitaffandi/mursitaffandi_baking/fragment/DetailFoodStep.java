package com.mursitaffandi.mursitaffandi_baking.fragment;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import com.mursitaffandi.mursitaffandi_baking.ApplicationBase;
import com.mursitaffandi.mursitaffandi_baking.R;
import com.mursitaffandi.mursitaffandi_baking.activity.DetailFoodActivity;
import com.mursitaffandi.mursitaffandi_baking.event.FootStepClick;
import com.mursitaffandi.mursitaffandi_baking.model.Step;
import com.mursitaffandi.mursitaffandi_baking.utilities.ConstantString;

import org.greenrobot.eventbus.EventBus;
/**
 * An activity representing a single DetailFood detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link DetailFoodActivity}.
 */
public class DetailFoodStep extends Fragment {
    SimpleExoPlayerView stepExoplayer;
    ImageView ivDetailStep;
    TextView tvDetailStep;
    Button btnPrev;
    Button btnNext;

    private Step mStep;
    private int mNumber;
    private boolean mFirst;
    private boolean mLast;

    private SimpleExoPlayer mSimpleExoPlayer;
    private Bundle mBundle;

    private boolean mPlayWhenReady = true;
    private int mCurrentWindow;
    private long mPlayBackPosition;

    public DetailFoodStep() {
    }

    private final EventBus eventBus = ApplicationBase.getInstance().getEventBus();
    private final FootStepClick event = new FootStepClick();
    private SharedPreferences.Editor preferencesExo = ApplicationBase.getInstance().getPrefs().edit();
    private SharedPreferences preferencesExoGet = ApplicationBase.getInstance().getPrefs();

    /*@Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mPlayBackPosition = savedInstanceState.getLong(ConstantString.TAG_EXOPOSITION);
            mCurrentWindow = savedInstanceState.getInt(ConstantString.TAG_CURRENTWINDOW);
            mPlayWhenReady = savedInstanceState.getBoolean(ConstantString.TAG_PLAYWHENREADY);
        }
            mPlayBackPosition = preferencesExoGet.getLong(ConstantString.TAG_PREF_EXOPOSITION, 0L);

    }
    */

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            mPlayBackPosition = savedInstanceState.getLong(ConstantString.TAG_EXOPOSITION);
            mCurrentWindow = savedInstanceState.getInt(ConstantString.TAG_CURRENTWINDOW);
            mPlayWhenReady = savedInstanceState.getBoolean(ConstantString.TAG_PLAYWHENREADY);
        }
        mPlayBackPosition = preferencesExoGet.getLong(ConstantString.TAG_PREF_EXOPOSITION, 0L);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detailfoodstep, container, false);
        stepExoplayer = view.findViewById(R.id.exoui_frgdetailstep);
        ivDetailStep = view.findViewById(R.id.detail_step_image);
        tvDetailStep = view.findViewById(R.id.tv_frgdetailstep_stepinstruction);
        btnPrev = view.findViewById(R.id.btn_frgdetailstep_prev);
        btnNext = view.findViewById(R.id.btn_frgdetailstep_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoNextStep();
            }
        });
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoPrevStep();
            }
        });
        ivDetailStep.setVisibility(View.GONE);
        mBundle = getArguments();
        mStep = mBundle.getParcelable(ConstantString.TAG_BUNDLE_STEP);
        mNumber = mBundle.getInt(ConstantString.TAG_STEP_NUMBER);
        mFirst = mBundle.getBoolean(ConstantString.TAG_STEP_FIRST);
        mLast = mBundle.getBoolean(ConstantString.TAG_STEP_LAST);
        tvDetailStep.setText(mStep.getDescription());
        ivDetailStep.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(mStep.getThumbnailURL()) && !mStep.getThumbnailURL().substring(mStep.getThumbnailURL().length() - 4, mStep.getThumbnailURL().length()).equals(".mp4")) {
            ivDetailStep.setVisibility(View.VISIBLE);
            /*Glide.with(view.getContext())
                    .load(mStep.getThumbnailURL())
                    .placeholder(R.mipmap.ic_launcher)
                    .into(ivDetailStep);*/
        }

        btnNext.setVisibility(View.VISIBLE);
        btnPrev.setVisibility(View.VISIBLE);

        if (mFirst) btnPrev.setVisibility(View.GONE);
        if (mLast) btnNext.setVisibility(View.GONE);

        return view;
    }

    private void gotoPrevStep() {
        event.setClickPosition(mNumber - 1);
        eventBus.post(event);
        preferencesExo.putLong(ConstantString.TAG_PREF_EXOPOSITION, 0L);
        preferencesExo.apply();
    }

    private void gotoNextStep() {
        event.setClickPosition(mNumber + 1);
        eventBus.post(event);
        preferencesExo.putLong(ConstantString.TAG_PREF_EXOPOSITION, 0L);
        preferencesExo.apply();
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

        mSimpleExoPlayer.seekTo(mCurrentWindow, mPlayBackPosition);
        mSimpleExoPlayer.setPlayWhenReady(mPlayWhenReady);

        if (TextUtils.isEmpty(mStep.getVideoURL()) && TextUtils.isEmpty(mStep.getThumbnailURL())) {
            stepExoplayer.setVisibility(View.GONE);
        } else {
            stepExoplayer.setVisibility(View.VISIBLE);
            Uri uri = null;
            if (!TextUtils.isEmpty(mStep.getVideoURL())) {
                uri = Uri.parse(mStep.getVideoURL());
            } else if (
                    !TextUtils.isEmpty(mStep.getThumbnailURL())
                            && mStep.getThumbnailURL()
                            .substring(mStep.getThumbnailURL()
                                    .length() - 4,
                                    mStep.getThumbnailURL()
                                            .length())
                            .equals(".mp4")) {
                uri = Uri.parse(mStep.getThumbnailURL());
            }
            MediaSource mediaSource = createMediaSource(uri);
            mSimpleExoPlayer.prepare(mediaSource, true, false);
        }
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
            mSimpleExoPlayer.release();
            mSimpleExoPlayer = null;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mSimpleExoPlayer != null) {
            mPlayWhenReady = mSimpleExoPlayer.getPlayWhenReady();
            mCurrentWindow = mSimpleExoPlayer.getCurrentWindowIndex();
            mPlayBackPosition = mSimpleExoPlayer.getCurrentPosition();
            outState.putLong(ConstantString.TAG_EXOPOSITION, mPlayBackPosition);
            outState.putBoolean(ConstantString.TAG_PLAYWHENREADY, mPlayWhenReady);
            outState.putInt(ConstantString.TAG_CURRENTWINDOW, mCurrentWindow);
            preferencesExo.putLong(ConstantString.TAG_PREF_EXOPOSITION, mPlayBackPosition);
            preferencesExo.apply();
        } else {
            outState.putLong(ConstantString.TAG_EXOPOSITION, mPlayBackPosition);
        }
    }
}
