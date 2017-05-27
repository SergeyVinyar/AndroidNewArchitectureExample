package ru.vinyarsky.arcexample.ui;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;

import ru.vinyarsky.arcexample.R;

public class MainActivity extends LifecycleActivity {

    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        viewModel.showCity().observe(this, NOT_USED -> {
            getSupportFragmentManager().beginTransaction()
                    .remove(getSupportFragmentManager().findFragmentById(R.id.fragment_frame))
                    .add(R.id.fragment_frame, new CityFragment())
                    .addToBackStack(null)
                    .commit();
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_frame, new CityListFragment())
                .commit();
    }
}
