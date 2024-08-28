package com.adamas.androidcourse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.adamas.androidcourse.databinding.ActivityMainBinding;
import com.adamas.androidcourse.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {

    static final Integer RESULT_CODE = 0;
    static final String INTENT_BACK_MESSAGE_KEY = "BACK_MESSAGE";

    private ActivitySecondBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupMainTextView();
        setupBackButton();
    }

    private void setupMainTextView() {
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.INTENT_MESSAGE_KEY);
        binding.tvMain.setText(message);
    }

    private void setupBackButton() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent();
                backIntent.putExtra(SecondActivity.INTENT_BACK_MESSAGE_KEY, "Come back from activity 2");
                setResult(SecondActivity.RESULT_CODE, backIntent);
                finish();
            }
        });
    }
}