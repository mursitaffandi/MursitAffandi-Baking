package com.mursitaffandi.mursitaffandi_baking.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.mursitaffandi.mursitaffandi_baking.ApplicationBase;
import com.mursitaffandi.mursitaffandi_baking.R;
import com.mursitaffandi.mursitaffandi_baking.event.FootStepClick;
import com.mursitaffandi.mursitaffandi_baking.fragment.DetailFood;
import com.mursitaffandi.mursitaffandi_baking.fragment.DetailFoodStep;
import com.mursitaffandi.mursitaffandi_baking.model.Baking;
import com.mursitaffandi.mursitaffandi_baking.model.MultiIngredient;
import com.mursitaffandi.mursitaffandi_baking.model.MultiStep;
import com.mursitaffandi.mursitaffandi_baking.model.Step;
import com.mursitaffandi.mursitaffandi_baking.utilities.ConstantString;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

/**
 * An activity representing a list of Baking. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link DetailFoodStep} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class DetailFoodActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane = false;
    private Baking mBaking;
    private final EventBus mEventBus = ApplicationBase.getInstance().getEventBus();
    private MultiStep stepList;
    private MultiIngredient ingredientList;
    private int mPositionSelected = -1;
    private FragmentManager mFragmentManager;
    private Bundle mBundleFood;
    private Bundle mBundleStep;
    private boolean mBakingList = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailfood_list);
       ButterKnife.bind(this);

        mBaking = getIntent().getParcelableExtra(ConstantString.TAG_DETAIL_FOOT);
        stepList = new MultiStep(mBaking.getSteps());
        ingredientList = new MultiIngredient(mBaking.getIngredients());


        // The detail container view will be present only in the
        // large-screen layouts (res/values-w900dp).
        // If this view is present, then the
        // activity should be in two-pane mode.
        mTwoPane = findViewById(R.id.frg_listFoodDetail) != null;
        displayDetailFoot();
        if (savedInstanceState != null) {
            mBakingList = savedInstanceState.getBoolean(ConstantString.TAG_DETAILFOOD_LIST_STATE);
            mPositionSelected = savedInstanceState.getInt(ConstantString.TAG_DETAILFOOD_POSITION_STATE);
            if (mBakingList) {
                displayDetailFoot();
            } else {
                showDetailStepFragment(mPositionSelected);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mTwoPane) {
            super.onBackPressed();
            return;
        }
        Fragment fragment = mFragmentManager.findFragmentById(R.id.frg_foodDetail);
        if (fragment instanceof DetailFoodStep) {
            displayDetailFoot();
        } else {
            super.onBackPressed();
        }
    }

    private void displayDetailFoot() {
        setTitle(mBaking.getName());
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().replace(R.id.frg_foodDetail, new Fragment()).commit();
        DetailFood fragmentDetailFood = new DetailFood();
        mBundleFood = new Bundle();
        mBundleFood.putParcelable(ConstantString.TAG_BUNDLE_INGREDIENT, ingredientList);
        mBundleFood.putParcelable(ConstantString.TAG_BUNDLE_STEP, stepList);
        fragmentDetailFood.setArguments(mBundleFood);

        if (mTwoPane)
            mFragmentManager.beginTransaction().replace(R.id.frg_listFoodDetail, fragmentDetailFood).commit();
        else
            mFragmentManager.beginTransaction().replace(R.id.frg_foodDetail, fragmentDetailFood).commit();
        mBakingList = true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mEventBus.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mEventBus.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void displayFootStep(FootStepClick footStepClick) {
        if (mPositionSelected != footStepClick.getClickPosition()) {
            mPositionSelected = footStepClick.getClickPosition();
            showDetailStepFragment(footStepClick.getClickPosition());
        }

    }

    private void showDetailStepFragment(int clickPosition) {
        Step step = mBaking.getSteps().get(clickPosition);
        setTitle(mBaking.getName() + " - " + step.getShortDescription());
        mBundleStep = new Bundle();
        mBundleStep.putParcelable(ConstantString.TAG_BUNDLE_STEP, step);
        mBundleStep.putBoolean(ConstantString.TAG_STEP_LAST, clickPosition == (mBaking.getSteps().size() - 1));
        mBundleStep.putBoolean(ConstantString.TAG_STEP_FIRST, clickPosition == 0);
        mBundleStep.putInt(ConstantString.TAG_STEP_NUMBER, clickPosition);
        DetailFoodStep frg_detailFoodStep = new DetailFoodStep();
        frg_detailFoodStep.setArguments(mBundleStep);
        mFragmentManager
                .beginTransaction()
                .replace(R.id.frg_foodDetail, frg_detailFoodStep)
                .commit();
        mBakingList = false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ConstantString.TAG_DETAILFOOD_LIST_STATE, mBakingList);
        outState.putInt(ConstantString.TAG_DETAILFOOD_POSITION_STATE, mPositionSelected);
    }
}
