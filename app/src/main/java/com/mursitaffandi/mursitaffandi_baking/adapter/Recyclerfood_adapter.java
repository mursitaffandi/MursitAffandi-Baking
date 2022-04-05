package com.mursitaffandi.mursitaffandi_baking.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mursitaffandi.mursitaffandi_baking.ApplicationBase;
import com.mursitaffandi.mursitaffandi_baking.R;
import com.mursitaffandi.mursitaffandi_baking.event.RecyclerClick_food;
import com.mursitaffandi.mursitaffandi_baking.model.Baking;
import com.mursitaffandi.mursitaffandi_baking.model.MultiBaking;

public class Recyclerfood_adapter extends RecyclerView.Adapter<Recyclerfood_adapter.Food> {
    private MultiBaking mMultiBaking;
    private RecyclerClick_food mRecyclerClickFood;
    private Context ctx;

    public Recyclerfood_adapter(Context ctx, MultiBaking mMultiBaking, RecyclerClick_food mRecyclerClickFood) {
        this.ctx = ctx;
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
        Baking baking = mMultiBaking.getBaking().get(position);

        holder.foodName.setText(baking.getName());
        holder.foodIngredient.setText(baking.getIngredients().size() + ApplicationBase.getInstance().getString(R.string.ingredient));
        holder.foodStep.setText(baking.getSteps().size() + ApplicationBase.getInstance().getString(R.string.step));
        holder.foodServing.setText(baking.getServings() + ApplicationBase.getInstance().getString(R.string.servingfor));
        /*if (!TextUtils.isEmpty(baking.getImage()) || !baking.getImage().equals(""))
            Glide.with(ctx)
                    .load(baking.getImage())
                    .dontAnimate()
                    .placeholder(R.mipmap.ic_item_food)
                    .error(R.mipmap.ic_error)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .fitCenter()
                    .into(holder.iv_item);*/
        holder.itemView.setOnClickListener(view ->
                mRecyclerClickFood.onFoodSelected(baking)
        );
    }

    @Override
    public int getItemCount() {
        return mMultiBaking.getBaking().size();
    }

    public class Food extends RecyclerView.ViewHolder {
        ImageView iv_item;

        TextView foodName;

        TextView foodIngredient;

        TextView foodStep;

        TextView foodServing;

        CardView foodCardView;

        public Food(View itemView) {
            super(itemView);
            iv_item = itemView.findViewById(R.id.iv_item_food);
            foodName = itemView.findViewById(R.id.tv_item_title);
            foodIngredient = itemView.findViewById(R.id.tv_item_ingredient);
            foodStep = itemView.findViewById(R.id.tv_item_step);
            foodServing = itemView.findViewById(R.id.tv_item_serving);
            foodCardView = itemView.findViewById(R.id.card_food);
        }
    }
}
