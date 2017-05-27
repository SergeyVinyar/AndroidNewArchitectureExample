package ru.vinyarsky.arcexample.ui;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Collections;

import ru.vinyarsky.arcexample.R;

/**
 * Список городов
 */
public class CityListFragment extends LifecycleFragment {

    private MainActivityViewModel viewModel;
    private ListView cityList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_citylist, container, false);

        cityList = (ListView) view.findViewById(R.id.citylist);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(getActivity()).get(MainActivityViewModel.class);
        viewModel.listOfCities().observe(this, cities -> {
            if (cities == null)
                cities = Collections.emptyList();
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, cities);
            cityList.setAdapter(adapter);
        });

        cityList.setOnItemClickListener((parent, view, position, id) -> {
            String cityName = (String) parent.getItemAtPosition(position);
            viewModel.selectCity(cityName);
        });
    }
}
