package com.gcherubini.animateddismissableactivitysample;


import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.gcherubini.animateddismissableactivity.AnimatedDismissableCard;
import com.gcherubini.animateddismissableactivitysample.agipagcartoespocs.R;
import com.gcherubini.animateddismissableactivitysample.agipagcartoespocs.databinding.ActivityAnimatedCardBinding;

public class AnimatedCardDismissableSampleActivity extends Activity {

    private AnimatedDismissableCard animatedCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAnimatedCardBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_animated_card);
        animatedCard = new AnimatedDismissableCard(this, binding.animatedActivityLayout);
    }

    public void onDismissCardBtnClick(View view) {
        animatedCard.dismiss();
    }
}