package com.mursitaffandi.mursitaffandi_baking.controller;

import android.util.Log;

import com.mursitaffandi.mursitaffandi_baking.ApplicationBase;
import com.mursitaffandi.mursitaffandi_baking.event.Progres;
import com.mursitaffandi.mursitaffandi_baking.model.Baking;
import com.mursitaffandi.mursitaffandi_baking.model.MultiBaking;
import com.mursitaffandi.mursitaffandi_baking.utilities.BakingService;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Welcome {
    Progres event = new Progres();
    EventBus eventBus = ApplicationBase.getInstance().getEventBus();

    public void getBaking() {
        BakingService apiService =
                BakingService.client.create(BakingService.class);
        Call<List<Baking>> listBakingCall = apiService.getJsonBaking();
        listBakingCall.enqueue(new Callback<List<Baking>>() {
            @Override
            public void onResponse(Call<List<Baking>> call, Response<List<Baking>> response) {
                event.setSuccess(true);
                event.setMessage(response.message());
                MultiBaking multiBaking = new MultiBaking(response.body());
                event.setBakings(multiBaking);
                eventBus.post(event);
            }

            @Override
            public void onFailure(Call<List<Baking>> call, Throwable t) {
                event.setMessage(t.getMessage());
                event.setSuccess(false);
                eventBus.post(event);
            }
        });
    }

}
