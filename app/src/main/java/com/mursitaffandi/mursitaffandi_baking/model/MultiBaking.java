package com.mursitaffandi.mursitaffandi_baking.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by mursitaffandi on 17/09/17.
 */

public class MultiBaking implements Parcelable {
    private List<Baking> baking;
    public List<Baking> getBaking() {
        return baking;
    }

    public MultiBaking(List<Baking> baking) {
        this.baking = baking;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.baking);
    }

    protected MultiBaking(Parcel in) {
        this.baking = in.createTypedArrayList(Baking.CREATOR);
    }

    public static final Parcelable.Creator<MultiBaking> CREATOR = new Parcelable.Creator<MultiBaking>() {
        @Override
        public MultiBaking createFromParcel(Parcel source) {
            return new MultiBaking(source);
        }

        @Override
        public MultiBaking[] newArray(int size) {
            return new MultiBaking[size];
        }
    };
}
