package com.mursitaffandi.mursitaffandi_baking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mursitaffandi.mursitaffandi_baking.ApplicationBase;
import com.mursitaffandi.mursitaffandi_baking.R;
import com.mursitaffandi.mursitaffandi_baking.event.RecyleClick_step;
import com.mursitaffandi.mursitaffandi_baking.model.MultiStep;

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
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.black));
            holder.tv_step.setTextColor(ApplicationBase.getInstance().getResources().getColor(R.color.cardview_light_background));
            holder.iv_step.setImageResource(R.mipmap.ic_item_step_selected);
        } else {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.white));
        }

        holder.itemView.setOnClickListener(view -> {
            mSelectedStep = position;
            mFootStepClick.onStepClick(mSelectedStep);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return mMultiStep.getStepList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_step;
        ImageView iv_step;
        CardView cv_step;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_step = itemView.findViewById(R.id.tv_itemStep);
            iv_step = itemView.findViewById(R.id.iv_itemStep);
            cv_step = itemView.findViewById(R.id.cv_itemstep);
        }
    }
}
