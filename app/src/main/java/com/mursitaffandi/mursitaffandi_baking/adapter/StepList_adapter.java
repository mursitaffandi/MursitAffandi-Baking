package com.mursitaffandi.mursitaffandi_baking.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mursitaffandi.mursitaffandi_baking.ApplicationBase;
import com.mursitaffandi.mursitaffandi_baking.R;
import com.mursitaffandi.mursitaffandi_baking.event.RecyleClick_step;
import com.mursitaffandi.mursitaffandi_baking.model.MultiStep;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ingat Mati on 19/09/2017.
 */

public class StepList_adapter extends RecyclerView.Adapter<StepList_adapter.ViewHolder> {
    private RecyleClick_step mFootStepClick;
    private MultiStep mMultiStep;
    private int mSelectedStep;
    private int reviousSelectedStep;
    public StepList_adapter(RecyleClick_step mFootStepClick, MultiStep mMultiStep) {
        this.mFootStepClick = mFootStepClick;
        this.mMultiStep = mMultiStep;
        this.reviousSelectedStep = -1;
        this.mSelectedStep = -1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_step, parent, false);
        return new ViewHolder(contentView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_step.setText(mMultiStep.getStepList().get(position).getShortDescription());
        final Context context = holder.itemView.getContext();
        if (mSelectedStep == position) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.black));
            holder.tv_step.setTextColor(ApplicationBase.getInstance().getResources().getColor(R.color.cardview_light_background));
            holder.iv_step.setImageResource(R.mipmap.ic_item_step_selected);
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.white));
        }
    }

    @Override
    public int getItemCount() {
        return mMultiStep.getStepList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_itemStep)
        TextView tv_step;
        @BindView(R.id.iv_itemStep)
        ImageView iv_step;
        @BindView(R.id.cv_itemstep)
        CardView cv_step;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            cv_step.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mSelectedStep = getAdapterPosition();
                    mFootStepClick.onStepClick(mSelectedStep);

                    notifyDataSetChanged();
                }
            });
        }
    }
}
