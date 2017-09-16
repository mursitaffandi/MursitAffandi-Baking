package com.mursitaffandi.mursitaffandi_baking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.mursitaffandi.mursitaffandi_baking.model.MultiBaking;

public class List_food extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_food);
        MultiBaking list_baking = getIntent().getParcelableExtra("list_baking");
    }
}
