package com.mursitaffandi.mursitaffandi_baking.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Ingat Mati on 18/09/2017.
 */

public class MultiIngredient implements Parcelable {
    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public MultiIngredient(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    List<Ingredient> ingredientList;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.ingredientList);
    }

    protected MultiIngredient(Parcel in) {
        this.ingredientList = in.createTypedArrayList(Ingredient.CREATOR);
    }

    public static final Parcelable.Creator<MultiIngredient> CREATOR = new Parcelable.Creator<MultiIngredient>() {
        @Override
        public MultiIngredient createFromParcel(Parcel source) {
            return new MultiIngredient(source);
        }

        @Override
        public MultiIngredient[] newArray(int size) {
            return new MultiIngredient[size];
        }
    };
}
