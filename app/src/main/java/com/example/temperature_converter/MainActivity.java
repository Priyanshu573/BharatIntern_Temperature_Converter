package com.example.temperature_converter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText inputTemperature, outputTemperature;
    private Spinner inputUnitSpinner, outputUnitSpinner;
    private Button convertButton;

    private String[] temperatureUnits = {"Celsius", "Fahrenheit", "Kelvin"};
    private double inputTempValue = 0.0;
    private String inputUnit, outputUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputTemperature = findViewById(R.id.inputTemperature);
        outputTemperature = findViewById(R.id.outputTemperature);
        inputUnitSpinner = findViewById(R.id.inputUnitSpinner);
        outputUnitSpinner = findViewById(R.id.outputUnitSpinner);
        convertButton = findViewById(R.id.convertButton);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, temperatureUnits);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        inputUnitSpinner.setAdapter(adapter);
        outputUnitSpinner.setAdapter(adapter);

        inputUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                inputUnit = temperatureUnits[i];
                convertTemperature();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        outputUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                outputUnit = temperatureUnits[i];
                convertTemperature();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertTemperature();
            }
        });
    }

    public void convertTemperature() {
        String inputValue = inputTemperature.getText().toString().trim();

        if (inputValue.isEmpty()) {
            outputTemperature.setText("");
            return;
        }

        try {
            inputTempValue = Double.parseDouble(inputValue);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
            return;
        }

        double result = 0.0;

        // Perform the temperature conversion based on the selected units
        if (inputUnit.equals("Celsius")) {
            if (outputUnit.equals("Fahrenheit")) {
                result = (inputTempValue * 9 / 5) + 32;
            } else if (outputUnit.equals("Kelvin")) {
                result = inputTempValue + 273.15;
            } else {
                result = inputTempValue; // Celsius to Celsius
            }
        } else if (inputUnit.equals("Fahrenheit")) {
            if (outputUnit.equals("Celsius")) {
                result = (inputTempValue - 32) * 5 / 9;
            } else if (outputUnit.equals("Kelvin")) {
                result = (inputTempValue + 459.67) * 5 / 9;
            } else {
                result = inputTempValue; // Fahrenheit to Fahrenheit
            }
        } else if (inputUnit.equals("Kelvin")) {
            if (outputUnit.equals("Celsius")) {
                result = inputTempValue - 273.15;
            } else if (outputUnit.equals("Fahrenheit")) {
                result = (inputTempValue * 9 / 5) - 459.67;
            } else {
                result = inputTempValue; // Kelvin to Kelvin
            }
        }

        outputTemperature.setText(String.valueOf(result));
    }
}

