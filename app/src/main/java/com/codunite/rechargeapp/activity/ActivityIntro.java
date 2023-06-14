package com.codunite.rechargeapp.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.codunite.commonutility.GlobalData;
import com.codunite.rechargeapp.R;

public class ActivityIntro extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalData.Fullscreen(ActivityIntro.this);
        setContentView(R.layout.act_intro);
        (findViewById(R.id.ll_get_start)).setOnClickListener(view -> {
            Intent intent=new Intent(ActivityIntro.this, ActivityMain.class);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}