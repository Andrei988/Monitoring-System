package com.example.monitoringsystem.ui.settings;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.monitoringsystem.MainActivity;
import com.example.monitoringsystem.R;
import com.example.monitoringsystem.repository.Database.Preferences;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import lombok.SneakyThrows;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    private View root;

    private EditText minCo2;
    private EditText maxCo2;
    private EditText minHumidity;
    private EditText maxHumidity;
    private EditText minTemp;
    private EditText maxTemp;
    private Button setValues;

    private FirebaseUser user;

    @SneakyThrows
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);






    }

    @SuppressLint("SetTextI18n")
    @SneakyThrows
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
        setValues = root.findViewById(R.id.setvalues);
        user = FirebaseAuth.getInstance().getCurrentUser();

        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        Preferences prefs = settingsViewModel.getPreferences();

        if(prefs == null) {
            prefs = new Preferences();
            prefs.setMaxCo2(100);
            prefs.setMinCo2(0);
            prefs.setMaxHumidity(100);
            prefs.setMinHumidity(0);
            prefs.setMaxTemp(100);
            prefs.setMinTemp(0);
        }

        minHumidity.setText(prefs.getMinHumidity()+"");
        maxHumidity.setText(prefs.getMaxHumidity()+"");
        minTemp.setText(prefs.getMinTemp()+"");
        maxTemp.setText(prefs.getMaxTemp()+"");
        minCo2.setText(prefs.getMinCo2()+"");
        maxCo2.setText(prefs.getMaxCo2()+"");

        setValues.setOnClickListener(v -> {
            if (user != null){
                if(Integer.parseInt(minHumidity.getText().toString())<Integer.parseInt(maxHumidity.getText().toString()) &&
                        Integer.parseInt(minCo2.getText().toString())<Integer.parseInt(maxCo2.getText().toString()) &&
                        Integer.parseInt(minTemp.getText().toString())<Integer.parseInt(maxTemp.getText().toString())) {
                    settingsViewModel.savePref(user.getDisplayName(), Integer.parseInt(minCo2.getText().toString()), Integer.parseInt(maxCo2.getText().toString()),
                            Integer.parseInt(minHumidity.getText().toString()), Integer.parseInt(maxHumidity.getText().toString()), Integer.parseInt(minTemp.getText().toString()),
                            Integer.parseInt(maxTemp.getText().toString()));
                    Toast.makeText(root.getContext(), "Preferences added", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(requireContext(), MainActivity.class));
                }
                else {
                    Toast.makeText(root.getContext(), "Unable to execute! Min Values are higher than Max Values", Toast.LENGTH_SHORT).show();
                }

            }
            else
                Toast.makeText(root.getContext(), "No user logged in", Toast.LENGTH_SHORT).show();
        });

        return root;
    }


}
