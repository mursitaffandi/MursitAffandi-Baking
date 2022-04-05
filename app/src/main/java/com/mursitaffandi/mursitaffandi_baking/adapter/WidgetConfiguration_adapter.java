package com.mursitaffandi.mursitaffandi_baking.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mursitaffandi.mursitaffandi_baking.R;
import com.mursitaffandi.mursitaffandi_baking.event.WidgetClick_food;
import com.mursitaffandi.mursitaffandi_baking.model.MultiBaking;
import com.mursitaffandi.mursitaffandi_baking.model.MultiIngredient;

/**
 * Created by Ingat Mati on 23/09/2017.
 */

public class WidgetConfiguration_adapter extends RecyclerView.Adapter<WidgetConfiguration_adapter.ItemWidget_viewholder>{
    private MultiBaking mMultiBaking;
    private WidgetClick_food mWidgetClickFood;

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
        holder.itemView.setOnClickListener(view ->
                mWidgetClickFood.OnWidgetItemClick(new MultiIngredient(mMultiBaking.getBaking().get(holder.getBindingAdapterPosition()).getIngredients()), holder.getBindingAdapterPosition())
        );
    }

    @Override
    public int getItemCount() {
        return mMultiBaking.getBaking().size();
    }

    public class ItemWidget_viewholder extends RecyclerView.ViewHolder {

        TextView tv_itemWidget;

        public ItemWidget_viewholder(View itemView) {
            super(itemView);
            tv_itemWidget = itemView.findViewById(R.id.tv_itemWidget);
        }
    }
}
