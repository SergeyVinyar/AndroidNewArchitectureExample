package ru.vinyarsky.arcexample.ui;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import ru.vinyarsky.arcexample.R;
import ru.vinyarsky.arcexample.repository.Status;

/**
 * Данные одного города
 */
public class CityFragment extends LifecycleFragment {

    private MainActivityViewModel viewModel;

    private TextView cityName;
    private TextView temperature;
    private TextView description;

    private ProgressBar progressBar;

    private Button refreshButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city, container, false);

        cityName = (TextView) view.findViewById(R.id.cityname);
        temperature = (TextView) view.findViewById(R.id.temperature);
        description = (TextView) view.findViewById(R.id.description);

        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);

        refreshButton = (Button) view.findViewById(R.id.refreshbutton);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(getActivity()).get(MainActivityViewModel.class);
        viewModel.weatherInfo().observe(this, weatherInfo -> {
            if (Status.LOADING.equals(weatherInfo.getStatus()))
                progressBar.setVisibility(View.VISIBLE);
            else
                progressBar.setVisibility(View.GONE);

            cityName.setText(weatherInfo.getCityName());
            temperature.setText(weatherInfo.getTemperature());
            description.setText(weatherInfo.getDescription());
        });

        refreshButton.setOnClickListener(view -> viewModel.refreshData());
    }
}
