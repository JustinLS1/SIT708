package com.example.unitconverter1;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Spinner sourceSpinner;
    private Spinner destinationSpinner;
    private EditText inputValue;
    private TextView convertedValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        sourceSpinner = findViewById(R.id.source);
        destinationSpinner = findViewById(R.id.destination);
        inputValue = findViewById(R.id.input);
        convertedValue = findViewById(R.id.converted);
        Button convertButton = findViewById(R.id.convert_button);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.unit_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceSpinner.setAdapter(adapter);
        destinationSpinner.setAdapter(adapter);

        convertButton.setOnClickListener(v -> performConversion());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void performConversion() {
        String sourceUnit = sourceSpinner.getSelectedItem().toString();
        String destinationUnit = destinationSpinner.getSelectedItem().toString();
        String inputValueStr = inputValue.getText().toString();

        // Validation & Error Handling
        if (inputValueStr.isEmpty()) {
            convertedValue.setText(getString(R.string.empty));
            return;
        }

        double value;
        try {
            value = Double.parseDouble(inputValueStr);
        } catch (NumberFormatException e) {
            convertedValue.setText(getString(R.string.invalidn));
            return;
        }

        if (sourceUnit.equals(destinationUnit)) {
            convertedValue.setText(getString(R.string.same));
            return;
        }

        double result = convert(sourceUnit, destinationUnit, value);

        if (Double.isNaN(result)) {
            convertedValue.setText(getString(R.string.invalidc));
        } else {
            convertedValue.setText(String.valueOf(result));
        }
    }

    // Conversion Logic for all values
    private double convert(String sourceUnit, String destinationUnit, double value) {

        double convertedValue = value;

        // Convert all length types into Centimeters as basis
        if (sourceUnit.equals(getString(R.string.inches))) {
            convertedValue *= 2.54;
        } else if (sourceUnit.equals(getString(R.string.feet))) {
            convertedValue *= 30.48;
        } else if (sourceUnit.equals(getString(R.string.yard))) {
            convertedValue *= 91.44;
        } else if (sourceUnit.equals(getString(R.string.mile))) {
            convertedValue *= 160934;
        } else if (sourceUnit.equals(getString(R.string.km))) {
            convertedValue *= 100000;
        }

        // Convert Centimeters to destination units
        if (destinationUnit.equals(getString(R.string.inches))) {
            return convertedValue / 2.54; // Convert cm to inches
        } else if (destinationUnit.equals(getString(R.string.feet))) {
            return convertedValue / 30.48; // Convert cm to feet
        } else if (destinationUnit.equals(getString(R.string.yard))) {
            return convertedValue / 91.44; // Convert cm to yards
        } else if (destinationUnit.equals(getString(R.string.mile))) {
            return convertedValue / 160934; // Convert cm to miles
        } else if (destinationUnit.equals(getString(R.string.km))) {
            return convertedValue / 100000; // Convert cm to kilometers
        } else if (destinationUnit.equals(getString(R.string.cm))) {
            return convertedValue; // Already in cm
        }

        // Convert all weight types into Kilograms as basis
        if (sourceUnit.equals(getString(R.string.pound))) {
            convertedValue *= 0.453592;
        } else if (sourceUnit.equals(getString(R.string.ounce))) {
            convertedValue *= 0.0283495;
        } else if (sourceUnit.equals(getString(R.string.ton))) {
            convertedValue *= 907.185;
        } else if (sourceUnit.equals(getString(R.string.gram))) {
            convertedValue *= 0.001;
        }

        // Convert Kilograms to destination units
        if (destinationUnit.equals(getString(R.string.pound))) {
            return convertedValue / 0.453592;
        } else if (destinationUnit.equals(getString(R.string.ounce))) {
            return convertedValue / 0.0283495;
        } else if (destinationUnit.equals(getString(R.string.ton))) {
            return convertedValue / 907.185;
        } else if (destinationUnit.equals(getString(R.string.kilo))) {
            return convertedValue;
        } else if (destinationUnit.equals(getString(R.string.gram))) {
            return convertedValue / 0.001;
        }

        // Temperature conversions
        if (sourceUnit.equals(getString(R.string.celsius))) {
            if (destinationUnit.equals(getString(R.string.fahrenheit))) {
                return (value * 1.8) + 32;
            } else if (destinationUnit.equals(getString(R.string.kelvin))) {
                return value + 273.15;
            }
        } else if (sourceUnit.equals(getString(R.string.fahrenheit))) {
            if (destinationUnit.equals(getString(R.string.celsius))) {
                return (value - 32) / 1.8;
            } else if (destinationUnit.equals(getString(R.string.kelvin))) {
                return (value - 32) / 1.8 + 273.15;
            }
        } else if (sourceUnit.equals(getString(R.string.kelvin))) {
            if (destinationUnit.equals(getString(R.string.celsius))) {
                return value - 273.15;
            } else if (destinationUnit.equals(getString(R.string.fahrenheit))) {
                return (value - 273.15) * 1.8 + 32;
            }
            return Double.NaN;
        }
        return convertedValue;
    }
}