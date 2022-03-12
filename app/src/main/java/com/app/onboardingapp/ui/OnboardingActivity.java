package com.app.onboardingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.app.onboardingapp.MainActivity;
import com.app.onboardingapp.R;
import com.app.onboardingapp.databinding.ActivityOnboardingBinding;
import com.app.onboardingapp.ui.adapter.OnboardingAdapter;
import com.app.onboardingapp.utils.DepthPageTransformer;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class OnboardingActivity extends AppCompatActivity {

    private ActivityOnboardingBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnboardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupViewPager();
        setOnClickListeners();
    }

    private void setupViewPager() {
        OnboardingAdapter onboardingAdapter = new OnboardingAdapter(this, getFragments());
        binding.viewPager.setAdapter(onboardingAdapter);
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 2) {
                    binding.textNext.setText(getResources().getString(R.string.finish));
                    binding.textSkip.setVisibility(View.GONE);
                } else {
                    binding.textNext.setText(getResources().getString(R.string.next));
                    binding.textSkip.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.viewPager.setPageTransformer(new DepthPageTransformer());
        new TabLayoutMediator(binding.pageIndicator, binding.viewPager,
                (tab, position) -> { }
        ).attach();
    }

    private List<Fragment> getFragments() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(OnboardingFragment.newInstance(
                getResources().getString(R.string.title_onboarding_1),
                getResources().getString(R.string.description_onboarding_1),
                R.raw.lottie_developer));
        fragmentList.add(OnboardingFragment.newInstance(
                getResources().getString(R.string.title_onboarding_2),
                getResources().getString(R.string.description_onboarding_2),
                R.raw.lottie_delivery_boy_bumpy_ride));
        fragmentList.add(OnboardingFragment.newInstance(
                getResources().getString(R.string.title_onboarding_3),
                getResources().getString(R.string.description_onboarding_3),
                R.raw.lottie_messaging));
        return fragmentList;
    }

    private void setOnClickListeners() {
        binding.textNext.setOnClickListener(view -> {
            if (getItem() > binding.viewPager.getChildCount()) {
                proceedToHome();
            } else {
                binding.viewPager.setCurrentItem(getItem() + 1, true);
            }
        });
        binding.textSkip.setOnClickListener(view -> {
            proceedToHome();
        });
    }

    private void proceedToHome() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private int getItem() {
        return binding.viewPager.getCurrentItem();
    }
}
