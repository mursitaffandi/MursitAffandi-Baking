package com.mursitaffandi.mursitaffandi_baking.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Ingat Mati on 18/09/2017.
 */

public class MultiStep implements Parcelable {
    List<Step> stepList;

    public MultiStep(List<Step> stepList) {
        this.stepList = stepList;
    }

    public List<Step> getStepList() {
        return stepList;
    }

    public void setStepList(List<Step> stepList) {
        this.stepList = stepList;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.stepList);
    }

    protected MultiStep(Parcel in) {
        this.stepList = in.createTypedArrayList(Step.CREATOR);
    }

    public static final Parcelable.Creator<MultiStep> CREATOR = new Parcelable.Creator<MultiStep>() {
        @Override
        public MultiStep createFromParcel(Parcel source) {
            return new MultiStep(source);
        }

        @Override
        public MultiStep[] newArray(int size) {
            return new MultiStep[size];
        }
    };
}
