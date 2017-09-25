package com.mursitaffandi.mursitaffandi_baking.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mursitaffandi.mursitaffandi_baking.ApplicationBase;
import com.mursitaffandi.mursitaffandi_baking.R;
import com.mursitaffandi.mursitaffandi_baking.event.RecyclerClick_food;
import com.mursitaffandi.mursitaffandi_baking.model.Baking;
import com.mursitaffandi.mursitaffandi_baking.model.MultiBaking;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Recyclerfood_adapter extends RecyclerView.Adapter<Recyclerfood_adapter.Food> {
    private MultiBaking mMultiBaking;
    private RecyclerClick_food mRecyclerClickFood;

    public Recyclerfood_adapter(MultiBaking mMultiBaking, RecyclerClick_food mRecyclerClickFood) {
        this.mMultiBaking = mMultiBaking;
        this.mRecyclerClickFood = mRecyclerClickFood;
    }

    @Override
    public Food onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new Food(view);
    }

    @Override
    public void onBindViewHolder(Food holder, int position) {
        Baking baking = mMultiBaking.getBaking().get(holder.getAdapterPosition());

        holder.foodName.setText(baking.getName());
        holder.foodIngredient.setText(baking.getIngredients().size() + ApplicationBase.getInstance().getString(R.string.ingredient));
        holder.foodStep.setText(baking.getSteps().size() +ApplicationBase.getInstance().getString(R.string.step));
        holder.foodServing.setText(String.valueOf(baking.getServings())+  ApplicationBase.getInstance().getString(R.string.servingfor));
        if (!TextUtils.isEmpty(baking.getImage()) || !baking.getImage().equals(""))
        Glide.with(holder.itemView.getContext())
                .load(baking.getImage())
                .dontAnimate()
                .placeholder(R.mipmap.ic_item_food)
                .error(R.mipmap.ic_error)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .fitCenter()
                .into(holder.iv_item);
    }

    @Override
    public int getItemCount() {
        return mMultiBaking.getBaking().size();
    }

    public class Food extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_item_food)
        ImageView iv_item;

        @BindView(R.id.tv_item_title)
        TextView foodName;

        @BindView(R.id.tv_item_ingredient)
        TextView foodIngredient;

        @BindView(R.id.tv_item_step)
        TextView foodStep;

        @BindView(R.id.tv_item_serving)
        TextView foodServing;

        @BindView(R.id.card_food)
        CardView foodCardView;

        public Food(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            foodCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mRecyclerClickFood.onFoodSelected(mMultiBaking.getBaking().get(getAdapterPosition()));
                }
            });
        }
    }
}
