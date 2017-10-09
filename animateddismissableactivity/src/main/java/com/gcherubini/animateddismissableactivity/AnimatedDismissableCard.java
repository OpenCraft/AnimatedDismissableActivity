package com.gcherubini.animateddismissableactivity;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Point;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class AnimatedDismissableCard implements View.OnTouchListener {

    private int DISMISS_ANIMATION_DURATION = 400;
    private int DISMISS_TOUCH_PERCENTAGE = 20;
    private ViewGroup animatedLayout;
    private final Activity activity;

    private int previousFingerPosition = 0;
    private int layoutPosition = 0;
    private int defaultLayoutHeight;
    private int defaultLayoutY = -1;

    private boolean isClosing = false;
    private boolean isScrollingUp = false;
    private boolean isScrollingDown = false;

    public AnimatedDismissableCard(Activity activity, ViewGroup animatedlayout) {
        this.activity = activity;
        DISMISS_ANIMATION_DURATION = activity.getResources().getInteger(R.integer.animated_dismissable_activity_animations_duration);
        DISMISS_TOUCH_PERCENTAGE = activity.getResources().getInteger(R.integer.animated_dismissable_activity_dismiss_with_touch_percentage);

        this.animatedLayout = animatedlayout;
        if(animatedlayout != null) {
            animatedlayout.setOnTouchListener(this);
        }
    }

    public boolean onTouch(View view, MotionEvent event) {

        if(animatedLayout == null) return false;

        final int y = (int) event.getRawY();

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                defaultLayoutHeight = animatedLayout.getHeight();
                previousFingerPosition = y;
                layoutPosition = (int) animatedLayout.getY();
                if (defaultLayoutY == -1) defaultLayoutY = (int) animatedLayout.getY();
                break;

            case MotionEvent.ACTION_UP:
                if (isScrollingUp || isScrollingDown) {
                    float layoutY = animatedLayout.getY();
                    if (hasUserScrolledDownEnoughToAutoClose(layoutY)) {
                        closeDownAndDismissDialog(layoutY);
                    } else {
                        ObjectAnimator animator = ObjectAnimator.ofFloat(animatedLayout, "y", layoutY, defaultLayoutY);
                        animator.setDuration(DISMISS_ANIMATION_DURATION);
                        animator.start();
                    }
                }

                if (isScrollingUp) isScrollingUp = false;
                if (isScrollingDown) isScrollingDown = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isClosing) {
                    int currentYPosition = (int) animatedLayout.getY();

                    if (isBeingScrolledUp(y)) {
                        if (!isScrollingUp) isScrollingUp = true;

                        if (hasUserScrolledDownBefore()) {
                            animatedLayout.getLayoutParams().height = animatedLayout.getHeight() - (currentYPosition - previousFingerPosition);
                            animatedLayout.requestLayout();
                        }

                        if(currentYPosition >= layoutPosition) {
                            animatedLayout.setY(currentYPosition + (y - previousFingerPosition));
                        }
                    }
                    else if (isBeingScrolledDown(y)) {
                        if (!isScrollingDown) isScrollingDown = true;

                        animatedLayout.setY(animatedLayout.getY() + (y - previousFingerPosition));
                        animatedLayout.requestLayout();
                    }

                    previousFingerPosition = y;
                }
                break;
        }
        return true;
    }

    /**
     * View is smaller than it's default size -> resize it instead of change it position
     */
    private boolean hasUserScrolledDownBefore() {
        return animatedLayout.getHeight() < defaultLayoutHeight;
    }

    private boolean hasUserScrolledDownEnoughToAutoClose(float currentYPosition) {
        return Math.abs(layoutPosition - currentYPosition) > defaultLayoutHeight * (DISMISS_TOUCH_PERCENTAGE * 0.01);
    }

    private boolean isBeingScrolledUp(int y) {
        return previousFingerPosition > y;
    }

    private boolean isBeingScrolledDown(int y) {
        return !isBeingScrolledUp(y);
    }

    public void closeDownAndDismissDialog(float currentPosition) {
        isClosing = true;
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenHeight = size.y;

        ObjectAnimator animator = ObjectAnimator.ofFloat(animatedLayout, "y", currentPosition, screenHeight + animatedLayout.getHeight());
        animator.setDuration(DISMISS_ANIMATION_DURATION);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                activity.finish();
            }

            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        animator.start();
    }

    public void dismiss() {
        if(animatedLayout == null) return;
        closeDownAndDismissDialog(animatedLayout.getY());
    }
}