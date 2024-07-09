package com.example.upworktask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.upworktask.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'upworktask' library on application startup.
    static {
        System.loadLibrary("upworktask");
    }

    private final String fileName = "upwork.txt";
    private ActivityMainBinding binding;
    private EditText editTextInput;
    private TextView textViewOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize views
        editTextInput = findViewById(R.id.edit_text_input);
        textViewOutput = findViewById(R.id.text_view_output);
        Button buttonWrite = findViewById(R.id.button_write);
        Button buttonRead = findViewById(R.id.button_read);

        // Set click listeners for buttons
        buttonWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText = editTextInput.getText().toString();
                saveDataToFile(inputText);
            }
        });

        buttonRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileContent = readFromFile();
                if (fileContent != null) {
                    if (!fileContent.isEmpty()) {
                        textViewOutput.setText(fileContent);
                        showToast("Successfully read from file.");
                    } else {
                        showToast("File is empty.");
                    }
                }
            }
        });

        // Example of a call to a native method
        TextView tv = binding.sampleText;
        tv.setText(stringFromJNI());
    }

    private void saveDataToFile(String data) {
        try (FileOutputStream fileOutputStream = openFileOutput(fileName, MODE_PRIVATE)) {
            fileOutputStream.write(data.getBytes());
            showToast("Successfully saved to file.");
        } catch (IOException e) {
            e.printStackTrace();
            showToast("Error saving data to file.");
        }
    }

    private String readFromFile() {
        StringBuilder stringBuilder = new StringBuilder();
        try (FileInputStream fileInputStream = openFileInput(fileName);
             InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            return stringBuilder.toString().trim(); // Trim to remove extra newline at end

        } catch (IOException e) {
            e.printStackTrace();
            showToast("Error reading file.");
            return null;
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * A native method that is implemented by the 'upworktask' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    /**
     * A native method to read file content from C++.
     */
    public native String readFileFromCpp(String filePath);
}
