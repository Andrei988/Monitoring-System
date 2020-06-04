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
    private View root;
    private EditText minCo2;
    private EditText maxCo2;
    private EditText minHumidity;
    private EditText maxHumidity;
    private EditText minTemp;
    private EditText maxTemp;
    private Button setvalues;
    private FirebaseUser user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        root = inflater.inflate(R.layout.fragment_settings, container, false);
        minCo2 = root.findViewById(R.id.co2min);
        maxCo2 = root.findViewById(R.id.co2max);
        minHumidity = root.findViewById(R.id.humMin);
        maxHumidity = root.findViewById(R.id.humMax);
        minTemp = root.findViewById(R.id.tempMin);
        maxTemp = root.findViewById(R.id.tempMax);
        setvalues = root.findViewById(R.id.setvalues);
        user = FirebaseAuth.getInstance().getCurrentUser();
        setvalues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null){
                    settingsViewModel.savePref(user.getDisplayName(), Integer.parseInt(minCo2.getText().toString()), Integer.parseInt(maxCo2.getText().toString()),
                            Integer.parseInt(minHumidity.getText().toString()), Integer.parseInt(maxHumidity.getText().toString()), Integer.parseInt(minTemp.getText().toString()),
                            Integer.parseInt(maxTemp.getText().toString()));
                    Toast.makeText(root.getContext(), "Preferences added", Toast.LENGTH_SHORT).show();

                    //TODO : close fragment
                }
                else
                    Toast.makeText(root.getContext(), "No user logged in", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }


}
