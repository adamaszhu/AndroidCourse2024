package com.adamas.androidcourse;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import com.adamas.androidcourse.databinding.ActivityMainBinding;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    static final Integer REQUEST_CODE = 0;
    static final Integer PERMISSION_REQUEST_CODE = 0;
    static final String INTENT_MESSAGE_KEY = "MESSAGE";

    private ActivityMainBinding binding;
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        setupSecondActivityButton();
        setupThreadActivityButton();
        setupStorageActivityButton();
        setupRecycleViewButton();
        setupFragmentButton();
        setupWebsiteButton();
        setupMenuButton();
        setupAlertDialogButton();
        setupSpinner();
        setupListButton();

        checkLocationPermission();
        checkNotificationPermission();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        binding.tvSensor.setText(String.format("Pressure: %f", event.values[0]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getLatestLocation();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((resultCode == SecondActivity.RESULT_CODE) && data != null) {
            String backMessage = data.getStringExtra(SecondActivity.INTENT_BACK_MESSAGE_KEY);
            Log.i(SecondActivity.INTENT_BACK_MESSAGE_KEY, backMessage);
        }
    }

    private void setupSecondActivityButton() {
        binding.btnSecondActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra(MainActivity.INTENT_MESSAGE_KEY, "This is a message");
                startActivityForResult(intent, MainActivity.REQUEST_CODE);
            }
        });
    }

    private void setupThreadActivityButton() {
        binding.btnThreadActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ThreadActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupStorageActivityButton() {
        binding.btnStorageActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StorageActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupRecycleViewButton() {
        binding.btnRecycleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecycleActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupFragmentButton() {
        binding.btnFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SampleFragment fragment = SampleFragment.newInstance("Name", "Description");
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            }
        });
    }

    private void setupWebsiteButton() {
        binding.btnWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(getString(R.string.url_google));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    private void setupMenuButton() {
        binding.btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(MainActivity.this, binding.btnMenu);
                menu.getMenuInflater().inflate(R.menu.main_menu, menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(MainActivity.this, "Clicked" + item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                menu.show();
            }
        });
    }

    private void setupAlertDialogButton() {
        binding.btnAlertDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.main_alert_title);
                builder.setMessage(R.string.main_alert_message);
                builder.setPositiveButton(R.string.main_alert_primary_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Confirm clicked", Toast.LENGTH_LONG).show();
                    }
                });
                builder.create().show();
            }
        });
    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner, android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(adapter);
    }

    private void setupListButton() {
        Button btn = new Button(this);
        btn.setText(R.string.main_title);
        btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });
        binding.main.addView(btn);
    }

    private void checkLocationPermission() {
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (permission == PackageManager.PERMISSION_GRANTED) {
            getLatestLocation();
        } else if (permission == PackageManager.PERMISSION_DENIED) {
            // Tell the user in a way
        } else {
            ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.ACCESS_COARSE_LOCATION }, MainActivity.PERMISSION_REQUEST_CODE);
        }
    }


    @SuppressLint({"MissingPermission"})
    private void getLatestLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean isNetworkProviderEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (isNetworkProviderEnabled) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 100, new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    Log.i("LOCATION_UPDATE", location.toString());
                }
            });
        } else {
            // Switch provider or show an error
        }
    }

    private void checkNotificationPermission() {
        ActivityResultLauncher<String[]> launcher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
            @Override
            public void onActivityResult(Map<String, Boolean> o) {
                // Do something when permission
            }
        });
        int permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS);
        if (permission == PackageManager.PERMISSION_GRANTED) {
            showNotification();
        } else if (permission == PackageManager.PERMISSION_DENIED) {
            // Show something
        } else {
            launcher.launch(new String[]{ android.Manifest.permission.POST_NOTIFICATIONS });
        }
    }

    @SuppressLint("NotificationPermission")
    private void showNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel("android", "Sample Chanel", NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("This is a sample notification channel");
        notificationManager.createNotificationChannel(channel);

        Notification notification = new Notification.Builder(this, "android")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Sample Notification")
                .setContentText("Message")
                .build();
        notificationManager.notify(1, notification);
    }
}