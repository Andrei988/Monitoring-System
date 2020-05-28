package com.example.monitoringsystem.ui.co2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.monitoringsystem.R;

public class CO2Fragment extends Fragment {

    private CO2ViewModel co2ViewModel;

    public static Fragment newInstance() {
        return new CO2Fragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        co2ViewModel = new ViewModelProvider(this).get(CO2ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tab_co2, container, false);

        return root;
    }

}
