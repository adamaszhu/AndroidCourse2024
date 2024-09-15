package com.adamas.androidcourse;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.adamas.androidcourse.databinding.ActivityRecycleBinding;

import java.util.ArrayList;
import java.util.List;

public class RecycleActivity extends AppCompatActivity {

    private ActivityRecycleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityRecycleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupRecycleView();
    }

    private void setupRecycleView() {
        List<Person> personArray = new ArrayList<>();
        for(int i = 0; i < 1000; i++) {
            personArray.add(new Person("Apple", String.valueOf(i)));
        }
        // RecycleAdapter adapter = new RecycleAdapter(personArray);
        RecycleListAdapter adapter = new RecycleListAdapter(new RecycleListAdapter.PersonDiff());
        adapter.submitList(personArray);
        binding.recycleView.setAdapter(adapter);
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
    }
}