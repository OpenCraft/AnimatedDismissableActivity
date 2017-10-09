package com.gcherubini.animateddismissableactivitysample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gcherubini.animateddismissableactivitysample.agipagcartoespocs.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onCartaoAnimadoBtnClick(View view) {
        Intent intent = new Intent(this, AnimatedCardDismissableSampleActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.animated_dismissable_card_slide_up_anim, R.anim.animated_dismissable_card_stay_anim);
    }
}
