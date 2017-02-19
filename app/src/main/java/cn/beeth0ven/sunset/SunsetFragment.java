package cn.beeth0ven.sunset;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by Air on 2017/2/19.
 */

public class SunsetFragment extends Fragment {

    private View view;
    private View sunView;
    private View skyView;
    private View seaView;
    private View reflectionSunView;
    private int blueSkyColor;
    private int sunsetSkyColor;
    private int nightSkyColor;
    private boolean isSunset = false;
    public static SunsetFragment newInstance() {
        return new SunsetFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sunset_fragment, container, false);
        sunView = view.findViewById(R.id.sunImageView);
        skyView = view.findViewById(R.id.frameLayout);
        seaView = view.findViewById(R.id.seaFrameLayout);
        reflectionSunView = view.findViewById(R.id.reflectionSunImageView);

        Resources resources = getResources();
        blueSkyColor = resources.getColor(R.color.blue_sky);
        sunsetSkyColor = resources.getColor(R.color.sunset_sky);
        nightSkyColor = resources.getColor(R.color.night_sky);

        view.setOnClickListener(v -> startAnimation());

        return view;
    }

    private void startAnimation() {
        float sunStartY = sunView.getTop();
        float sunEndY = skyView.getHeight();
        float reflectionSunStartY = reflectionSunView.getTop();
        float reflectionSunEndY = -reflectionSunView.getHeight();

        AnimatorSet animatorSet = new AnimatorSet();

        if (!isSunset) {

            ObjectAnimator heightAnimator = ObjectAnimator
                    .ofFloat(sunView, "y", sunStartY, sunEndY)
                    .setDuration(3000);
            heightAnimator.setInterpolator(new AccelerateInterpolator());

            ObjectAnimator reflectionHeightAnimator = ObjectAnimator
                    .ofFloat(reflectionSunView, "y", reflectionSunStartY, reflectionSunEndY)
                    .setDuration(3000);
            heightAnimator.setInterpolator(new AccelerateInterpolator());

            ObjectAnimator sunsetSkyAnimator = ObjectAnimator
                    .ofInt(skyView, "backgroundColor", blueSkyColor, sunsetSkyColor)
                    .setDuration(3000);
            sunsetSkyAnimator.setEvaluator(new ArgbEvaluator());

            ObjectAnimator nightSkyAnimator = ObjectAnimator
                    .ofInt(skyView, "backgroundColor", sunsetSkyColor, nightSkyColor)
                    .setDuration(1500);
            nightSkyAnimator.setEvaluator(new ArgbEvaluator());

            animatorSet
                    .play(heightAnimator)
                    .with(reflectionHeightAnimator)
                    .with(sunsetSkyAnimator)
                    .before(nightSkyAnimator);
        } else {

            ObjectAnimator morningSkyAnimator = ObjectAnimator
                    .ofInt(skyView, "backgroundColor", nightSkyColor, sunsetSkyColor)
                    .setDuration(1500);
            morningSkyAnimator.setEvaluator(new ArgbEvaluator());

            ObjectAnimator heightAnimator = ObjectAnimator
                    .ofFloat(sunView, "y", sunEndY, sunStartY)
                    .setDuration(3000);
            heightAnimator.setInterpolator(new DecelerateInterpolator());

            ObjectAnimator reflectionHeightAnimator = ObjectAnimator
                    .ofFloat(reflectionSunView, "y", reflectionSunEndY, reflectionSunStartY)
                    .setDuration(3000);
            heightAnimator.setInterpolator(new DecelerateInterpolator());

            ObjectAnimator sunriseSkyAnimator = ObjectAnimator
                    .ofInt(skyView, "backgroundColor", sunsetSkyColor, blueSkyColor)
                    .setDuration(3000);
            sunriseSkyAnimator.setEvaluator(new ArgbEvaluator());

            animatorSet
                    .play(morningSkyAnimator)
                    .before(heightAnimator)
                    .before(reflectionHeightAnimator)
                    .before(sunriseSkyAnimator);
        }

        isSunset = !isSunset;

        animatorSet.start();
    }
}
