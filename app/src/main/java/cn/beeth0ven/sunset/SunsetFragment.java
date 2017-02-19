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

/**
 * Created by Air on 2017/2/19.
 */

public class SunsetFragment extends Fragment {

    private View view;
    private View sunView;
    private View skyView;
    private int blueSkyColor;
    private int sunsetSkyColor;
    private int nightSkyColor;


    public static SunsetFragment newInstance() {
        return new SunsetFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sunset_fragment, container, false);
        sunView = view.findViewById(R.id.sunImageView);
        skyView = view.findViewById(R.id.frameLayout);

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

        ObjectAnimator heightAnimator = ObjectAnimator
                .ofFloat(sunView, "y", sunStartY, sunEndY)
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

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet
                .play(heightAnimator)
                .with(sunsetSkyAnimator)
                .before(nightSkyAnimator);
        animatorSet.start();
    }
}
