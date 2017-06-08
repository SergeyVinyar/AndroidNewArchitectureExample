package ru.vinyarsky.arcexample.ui;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import ru.vinyarsky.arcexample.R;

public class MainActivity extends LifecycleActivity {

    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        viewModel.showCity().observe(this, NOT_USED -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_frame);
            if (currentFragment instanceof CityListFragment) {
                fragmentManager.beginTransaction()
                        .remove(currentFragment)
                        .add(R.id.fragment_frame, new CityFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentById(R.id.fragment_frame) == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_frame, new CityListFragment())
                    .commit();
        }
    }
}
