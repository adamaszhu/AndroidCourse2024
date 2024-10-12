package com.adamas.androidcourse;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.adamas.androidcourse.databinding.ActivityStorageBinding;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.ref.Cleaner;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class StorageActivity extends AppCompatActivity {

    private ActivityStorageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityStorageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loadPref();
        loadDB();
        loadFile();

        setupPrefButton();
        setupDBButton();
        setupFileButton();
        setupAPIButton();
    }

    private void loadPref() {
        SharedPreferences pref = getSharedPreferences("SAMPLE", MODE_PRIVATE);
        String value = pref.getString("KEY", "DEFAULT_VALUE");
        binding.tvPref.setText(value);
    }

    private void setupPrefButton() {
        binding.btnPref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("SAMPLE", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("KEY", "NEW_VALUE");
                editor.apply();
                loadPref();
            }
        });
    }

    private void loadDB() {
        DBHelper dbHelper = new DBHelper(StorageActivity.this, "Person.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Person", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            StringBuilder builder = new StringBuilder();
            do {
                String firstName = cursor.getString(cursor.getColumnIndex("first_name"));
                String lastName = cursor.getString(cursor.getColumnIndex("last_name"));
                builder.append(firstName + " " + lastName + ", ");
            } while (cursor.moveToNext());
            binding.tvDb.setText(builder.toString());
        }
    }

    private void setupDBButton() {
        binding.btnDbInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelper = new DBHelper(StorageActivity.this, "Person.db", null, 1);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("first_name", "Adamas");
                values.put("last_name", "Zhu");
                db.insert("Person", null, values);
                loadDB();
            }
        });
        binding.btnDbUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelper = new DBHelper(StorageActivity.this, "Person.db", null, 1);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("first_name", "Adam");
                db.update("Person", values, "first_name = ?", new String[]{"Adamas"});
                loadDB();
            }
        });
        binding.btnDbDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelper = new DBHelper(StorageActivity.this, "Person.db", null, 1);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("Person", "first_name = ?", new String[]{"Adam"});
                loadDB();
            }
        });
    }

    private void loadFile() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            File[] files = getCacheDir().listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.startsWith("Sample");
                }
            });

            if (files.length > 0) {
                File file = files[0];
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while((line = reader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                reader.close();
            }

            FileInputStream inputStream = openFileInput("Sample");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            reader.close();
            binding.tvFile.setText(stringBuilder.toString());
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
    }

    private void setupFileButton() {
        binding.btnFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    File file = File.createTempFile("TEMP", "", getCacheDir());
                    BufferedWriter tempWriter = new BufferedWriter(new FileWriter(file));
                    tempWriter.write("This is a temp file");
                    tempWriter.close();
                    FileOutputStream outputStream = openFileOutput("Sample", MODE_PRIVATE);
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
                    writer.write("This is a test file");
                    writer.close();
                } catch (FileNotFoundException e) {

                } catch (IOException e) {

                }
                loadFile();
            }
        });
    }

    private void setupAPIButton() {
        binding.btnApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cleaner GsonConverterFactory;
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://universities.hipolabs.com")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                UniversityService service = retrofit.create(UniversityService.class);
                Call<List<University>> universities = service.getUniversities();
                universities.enqueue(new Callback<List<University>>() {
                    @Override
                    public void onResponse(Call<List<University>> call, Response<List<University>> response) {
                        response.body();
                    }

                    @Override
                    public void onFailure(Call<List<University>> call, Throwable throwable) {

                    }
                });
            }
        });
    }
}