package com.mursitaffandi.mursitaffandi_baking.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mursitaffandi.mursitaffandi_baking.ApplicationBase;
import com.mursitaffandi.mursitaffandi_baking.activity.DetailFoodActivity;
import com.mursitaffandi.mursitaffandi_baking.R;
import com.mursitaffandi.mursitaffandi_baking.adapter.StepList_adapter;
import com.mursitaffandi.mursitaffandi_baking.event.FootStepClick;
import com.mursitaffandi.mursitaffandi_baking.event.RecyleClick_step;
import com.mursitaffandi.mursitaffandi_baking.model.Ingredient;
import com.mursitaffandi.mursitaffandi_baking.model.MultiIngredient;
import com.mursitaffandi.mursitaffandi_baking.model.MultiStep;
import com.mursitaffandi.mursitaffandi_baking.utilities.ConstantString;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A fragment representing a single DetailFood detail screen.
 * This fragment is either contained in a {@link DetailFoodActivity}
 * in two-pane mode (on tablets) or a {@link DetailFoodStep}
 * on handsets.
 */
public class DetailFood extends Fragment implements RecyleClick_step{
    @BindView(R.id.rv_detaillistfood_step)
    RecyclerView rc_detailFoot;

    @BindView(R.id.tv_detaillistfood_ingredient)
    TextView tv_ingredient;
    private MultiStep mMultiStep;
    private MultiIngredient mMultiIngredient;
    private String fullIngredient;
    private final EventBus eventBus = ApplicationBase.getInstance().getEventBus();
    StepList_adapter stepsAdapter;
    SharedPreferences.Editor preferencesExo = ApplicationBase.getInstance().getPrefs().edit();
    public DetailFood() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detailfood_list, container, false);

        ButterKnife.bind(this, rootView);
        Bundle bundle = getArguments();
        mMultiIngredient = bundle.getParcelable(ConstantString.TAG_BUNDLE_INGREDIENT);
        mMultiStep = bundle.getParcelable(ConstantString.TAG_BUNDLE_STEP);
        fullIngredient = "";
        int numberOfIngredient = 1;
        for (Ingredient gotIngredient : mMultiIngredient.getIngredientList()) {
            fullIngredient += String.valueOf(numberOfIngredient) + ". " + gotIngredient.getQuantity()
                    + " " + gotIngredient.getMeasure() + " of " + gotIngredient.getIngredient() + ".";
            fullIngredient += "\n";
            numberOfIngredient++;
        }

        tv_ingredient.setText(fullIngredient);

        LinearLayoutManager stepLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rc_detailFoot.setLayoutManager(stepLayoutManager);

        stepsAdapter = new StepList_adapter(this, mMultiStep);

        rc_detailFoot.setAdapter(stepsAdapter);
        stepsAdapter.notifyDataSetChanged();

        ViewCompat.setNestedScrollingEnabled(rc_detailFoot, false);
        return rootView;
    }
    @Override
    public void onStepClick(int stepPosition) {
        FootStepClick event = new FootStepClick();
        event.setClickPosition(stepPosition);
        eventBus.post(event);
        preferencesExo.putLong(ConstantString.TAG_PREF_EXOPOSITION, 0L);
        preferencesExo.apply();
    }
}
