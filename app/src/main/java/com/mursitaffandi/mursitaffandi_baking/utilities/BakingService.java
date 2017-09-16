package com.mursitaffandi.mursitaffandi_baking.utilities;

import com.mursitaffandi.mursitaffandi_baking.model.Baking;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by mursitaffandi on 16/09/17.
 */

public interface BakingService {
    Retrofit client = new Retrofit.Builder()
            .baseUrl(ConstantString.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
@GET("baking.json")
    Call<List<Baking>> getJsonBaking();
}
