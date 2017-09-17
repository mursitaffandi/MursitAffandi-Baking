package com.mursitaffandi.mursitaffandi_baking.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.mursitaffandi.mursitaffandi_baking.ApplicationBase;
import com.mursitaffandi.mursitaffandi_baking.R;
import com.mursitaffandi.mursitaffandi_baking.controller.Welcome;
import com.mursitaffandi.mursitaffandi_baking.event.Progres;
import com.mursitaffandi.mursitaffandi_baking.model.MultiBaking;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends AppCompatActivity {
    @BindView(R.id.main_error_layout)
    RelativeLayout error_layout;

    @BindView(R.id.main_progress)
    RelativeLayout progress_layout;

    @BindView(R.id.btn_rerequest)
    Button btn_retry;

    Welcome controllerWelcome;
    EventBus eventBus = ApplicationBase.getInstance().getEventBus();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        controllerWelcome = new Welcome();
        getData();
        btn_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });
    }

    private void getData() {
        if (View.VISIBLE == error_layout.getVisibility()){
            error_layout.setVisibility(View.INVISIBLE);
        }
        progress_layout.setVisibility(View.VISIBLE);
        controllerWelcome.getBaking();
    }

    @Override
    protected void onStart() {
        super.onStart();
        eventBus.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        eventBus.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getRecipes(Progres event) {
        progress_layout.setVisibility(View.GONE);
        if (event.isSuccess()) {
            final MultiBaking mBaking = event.getBakings();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(WelcomeActivity.this, List_food.class);
                    i.putExtra("list_baking",mBaking);
                    startActivity(i);
                    finish();
                }
            }, 1000L);
        } else {
            progress_layout.setVisibility(View.GONE);
            error_layout.setVisibility(View.VISIBLE);
        }
    }
}
