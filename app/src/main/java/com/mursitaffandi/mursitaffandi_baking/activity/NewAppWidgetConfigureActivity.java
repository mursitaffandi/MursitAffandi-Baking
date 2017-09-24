package com.mursitaffandi.mursitaffandi_baking.activity;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;

import com.mursitaffandi.mursitaffandi_baking.ApplicationBase;
import com.mursitaffandi.mursitaffandi_baking.NewAppWidget;
import com.mursitaffandi.mursitaffandi_baking.R;
import com.mursitaffandi.mursitaffandi_baking.adapter.WidgetConfiguration_adapter;
import com.mursitaffandi.mursitaffandi_baking.controller.Welcome;
import com.mursitaffandi.mursitaffandi_baking.event.Progres;
import com.mursitaffandi.mursitaffandi_baking.event.WidgetClick_food;
import com.mursitaffandi.mursitaffandi_baking.model.Ingredient;
import com.mursitaffandi.mursitaffandi_baking.model.MultiBaking;
import com.mursitaffandi.mursitaffandi_baking.model.MultiIngredient;
import com.mursitaffandi.mursitaffandi_baking.utilities.ConstantString;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The configuration screen for the {@link NewAppWidget NewAppWidget} AppWidget.
 */
public class NewAppWidgetConfigureActivity extends Activity implements WidgetClick_food {
    private WidgetConfiguration_adapter widgetConfiguration_adapter;
    private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private Welcome mControllerWelcome;
    @BindView(R.id.rv_list_config_widget)
    RecyclerView mRecyclerView;

    @BindView(R.id.btn_rerequest_widgetfood)
    Button btn_request;
    @BindView(R.id.rl_error_widgetfood)
    RelativeLayout error_layout;

    @BindView(R.id.rl_progress_widgetfood)
    RelativeLayout progress_layout;
    MultiBaking mBaking;
    private EventBus eventBus;

    public NewAppWidgetConfigureActivity() {
        super();

    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        setContentView(R.layout.new_app_widget_configure);
        eventBus = ApplicationBase.getInstance().getEventBus();
        ButterKnife.bind(this);
        if (icicle != null && icicle.containsKey(ConstantString.TAG_WIDGET_STATE)) {
            progress_layout.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mBaking = icicle.getParcelable(ConstantString.TAG_WIDGET_STATE);
            widgetConfiguration_adapter = new WidgetConfiguration_adapter(mBaking, this);
            mRecyclerView.setAdapter(widgetConfiguration_adapter);
        } else {
            mControllerWelcome = new Welcome();
            getData();
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);


        btn_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });
        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

//        mAppWidgetText.setText(loadTitlePref(NewAppWidgetConfigureActivity.this, mAppWidgetId));
    }

    private void getData() {
        if (View.VISIBLE == error_layout.getVisibility()) {
            error_layout.setVisibility(View.INVISIBLE);
        }
        progress_layout.setVisibility(View.VISIBLE);
        mControllerWelcome.getBaking();
    }

    @Override
    public void OnWidgetItemClick(MultiIngredient ingredients, int position) {
        final Context context = NewAppWidgetConfigureActivity.this;

        // When the button is clicked, store the string locally
//        String widgetText = mAppWidgetText.getText().toString();
//        saveTitlePref(context, mAppWidgetId, widgetText);

        // It is the responsibility of the configuration activity to update the app widget
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        NewAppWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);

        // Make sure we pass back the original appWidgetId


        RemoteViews views = new RemoteViews(getBaseContext().getPackageName(), R.layout.new_app_widget);

        views.setTextViewText(R.id.tv_widget_title, getString(R.string.widget_title, mBaking.getBaking().get(position).getName()));

        String strIngredient = "";
        int numberOfIngredient = 1;
        for (Ingredient ingredient : ingredients.getIngredientList()) {
            strIngredient += String.valueOf(numberOfIngredient) + ". " + ingredient.getQuantity()
                    + " " + ingredient.getMeasure() + " of " + ingredient.getIngredient() + ".";
            strIngredient += "\n";
            numberOfIngredient++;
        }

        views.setTextViewText(R.id.appwidget_text, strIngredient);
        appWidgetManager.updateAppWidget(mAppWidgetId, views);

        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
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
            mRecyclerView.setVisibility(View.VISIBLE);
            mBaking = event.getBakings();
            widgetConfiguration_adapter = new WidgetConfiguration_adapter(mBaking, this);
            mRecyclerView.setAdapter(widgetConfiguration_adapter);
        } else {
            progress_layout.setVisibility(View.GONE);
            error_layout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mBaking != null)
            outState.putParcelable(ConstantString.TAG_WIDGET_STATE, mBaking);
    }
}