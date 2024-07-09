package com.example.upworktask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Example of a call to a native method
        TextView tv = binding.sampleText;
        tv.setText(stringFromJNI());

        // Check if the file exists and handle accordingly
        File file = new File(getFilesDir(), fileName);

        if (file.exists()) {
            // If the file exists, show a toast message and read its content
            showToast("File exists.");
            String fileContent = readFromFile();
            if (fileContent != null) {
                Log.d("FileContent", fileContent);
                showToast("File content: " + fileContent);
            } else {
                Log.e("FileContent", "Failed to read file.");
                showToast("Failed to read file.");
            }
        } else {
            // If the file does not exist, create it and write data
            saveDataToFile("my name is manoj bhatta.");
            String fileContent = readFromFile();
            if (fileContent != null) {
                Log.d("FileContent", fileContent);
                showToast("File content: " + fileContent);
            } else {
                Log.e("FileContent", "Failed to read file.");
                showToast("Failed to read file.");
            }
        }
    }

    private void saveDataToFile(String data) {
        try (FileOutputStream fileOutputStream = openFileOutput(fileName, MODE_PRIVATE)) {
            fileOutputStream.write(data.getBytes());
            Log.d("SaveData", "Data saved to file.");
            showToast("Data saved to file.");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("SaveData", "Error saving data to file: " + e.getMessage());
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
            return stringBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("ReadFile", "Error reading file: " + e.getMessage());
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
}
