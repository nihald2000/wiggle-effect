package com.nihal.wiggle;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;
import java.util.Random;

/**
 * WiggleAnimation class handles the animation of a view to create a wiggling effect.
 */
public class WiggleEffect {
    private final View view;
    private ObjectAnimator animator;
    private final Random random;
    private float originalX;
    private float originalY;
    private boolean isRunning;

    /**
     * Constructor to initialize the WiggleAnimation with the given view.
     *
     * @param view The view to be animated.
     */
    public WiggleEffect(View view) {
        this.view = view;
        this.random = new Random();
        this.isRunning = false;
    }

    /**
     * Gets the view associated with this WiggleAnimation.
     *
     * @return The view being animated.
     */
    public View getView() {
        return view;
    }

    /**
     * Starts the wiggle animation if it is not already running and the view is not selected.
     */
    public void start() {
        if (view.isSelected()) return;
        originalX = view.getTranslationX();
        originalY = view.getTranslationY();
        isRunning = true;
        animate();
    }

    /**
     * Stops the wiggle animation and resets the view to its original position.
     */
    public void stop() {
        isRunning = false;
        if (animator != null) {
            animator.removeAllListeners();
            animator.cancel();
        }
        resetPosition();
    }

    /**
     * Checks if the wiggle animation is currently running.
     *
     * @return true if the animation is running, false otherwise.
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Performs the wiggle animation by randomly adjusting the view's translation.
     * This method recursively calls itself to continue the animation until stopped.
     */
    private void animate() {
        if (!isRunning || view.isSelected()) return;

        if (animator != null) {
            animator.cancel();
        }

        float nextXValue = (random.nextFloat() * 2 - 1) * view.getWidth() / Constants.WIGGLE_FACTOR;
        float nextYValue = (random.nextFloat() * 2 - 1) * view.getHeight() / Constants.WIGGLE_FACTOR;

        PropertyValuesHolder animationValueOfX = PropertyValuesHolder.ofFloat(View.TRANSLATION_X,
                view.getTranslationX(), originalX + nextXValue);
        PropertyValuesHolder animationValueOfY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y,
                view.getTranslationY(), originalY + nextYValue);

        animator = ObjectAnimator.ofPropertyValuesHolder(view,
                animationValueOfX, animationValueOfY);
        animator.setDuration(Constants.WIGGLE_ANIMATION_DURATION);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (isRunning) {
                    animate();
                }
            }
        });
        animator.start();
    }

    /**
     * Resets the view's position to its original translation values.
     */
    private void resetPosition() {
        view.animate()
                .translationX(originalX)
                .translationY(originalY)
                .setDuration(Constants.RESET_DURATION)
                .start();
    }
}
