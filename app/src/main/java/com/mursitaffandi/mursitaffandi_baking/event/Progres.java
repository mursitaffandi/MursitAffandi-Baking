package com.mursitaffandi.mursitaffandi_baking.event;

import com.mursitaffandi.mursitaffandi_baking.model.MultiBaking;

/**
 * Created by mursitaffandi on 16/09/17.
 */

public class Progres {
    private String message;
    private boolean success;
    private MultiBaking bakings;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public MultiBaking getBakings() {
        return bakings;
    }

    public void setBakings(MultiBaking bakings) {
        this.bakings = bakings;
    }
}
