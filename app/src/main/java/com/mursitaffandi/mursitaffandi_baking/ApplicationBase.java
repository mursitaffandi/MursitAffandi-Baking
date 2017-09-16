package com.mursitaffandi.mursitaffandi_baking;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.EventBusBuilder;

/**
 * Created by mursitaffandi on 15/09/17.
 */

public class ApplicationBase extends Application{
private static ApplicationBase instance;
    private Gson gson;
    private EventBus eventBus;
    public ApplicationBase(){
        instance = this;
    }

    public static ApplicationBase getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createGson();
        createEventBus();
    }

    private void createEventBus() {
        eventBus = EventBus.builder().logNoSubscriberMessages(false).sendNoSubscriberEvent(false).build();
    }

    private void createGson() {
        gson = new GsonBuilder().create();
    }

    public Gson getGson() {
        return gson;
    }

    public EventBus getEventBus() {
        return eventBus;
    }
}

