package com.example.monitoringsystem.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.monitoringsystem.R;
import com.example.monitoringsystem.repository.Database.Preferences;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        EditText minCo2 = root.findViewById(R.id.co2min);
        EditText maxCo2 = root.findViewById(R.id.co2max);
        EditText minHumidity = root.findViewById(R.id.humMin);
        EditText maxHumidity = root.findViewById(R.id.humMax);
        EditText minTemp = root.findViewById(R.id.tempMin);
        EditText maxTemp = root.findViewById(R.id.tempMax);
        Button setvalues = root.findViewById(R.id.setvalues);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        setvalues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user!= null)
                    settingsViewModel.savePref(user.getDisplayName(), minCo2, maxCo2, minHumidity, maxHumidity, minTemp, maxTemp);
                else Toast.makeText(root.getContext(), "No user logged in", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}
