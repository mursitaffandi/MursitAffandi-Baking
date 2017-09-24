package com.mursitaffandi.mursitaffandi_baking.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mursitaffandi.mursitaffandi_baking.R;
import com.mursitaffandi.mursitaffandi_baking.event.WidgetClick_food;
import com.mursitaffandi.mursitaffandi_baking.model.MultiBaking;
import com.mursitaffandi.mursitaffandi_baking.model.MultiIngredient;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ingat Mati on 23/09/2017.
 */

public class WidgetConfiguration_adapter extends RecyclerView.Adapter<WidgetConfiguration_adapter.ItemWidget_viewholder>{
    MultiBaking mMultiBaking;
    WidgetClick_food mWidgetClickFood;

    public WidgetConfiguration_adapter(MultiBaking mMultiBaking, WidgetClick_food mWidgetClickFood) {
        this.mMultiBaking = mMultiBaking;
        this.mWidgetClickFood = mWidgetClickFood;
    }

    @Override
    public ItemWidget_viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_widget, parent, false);
        return new ItemWidget_viewholder(view);
    }

    @Override
    public void onBindViewHolder(ItemWidget_viewholder holder, final int position) {
        holder.tv_itemWidget.setText(mMultiBaking.getBaking().get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWidgetClickFood.OnWidgetItemClick(new MultiIngredient(mMultiBaking.getBaking().get(position).getIngredients()), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMultiBaking.getBaking().size();
    }

    public class ItemWidget_viewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_itemWidget)
        TextView tv_itemWidget;

        public ItemWidget_viewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
