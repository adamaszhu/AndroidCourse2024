package com.adamas.androidcourse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.adamas.androidcourse.databinding.ActivityListPersonCellBinding;

import java.util.List;

public class SampleListArrayAdapter extends ArrayAdapter<Person> {

    public SampleListArrayAdapter(@NonNull Context context, int resource, @NonNull List<Person> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        ActivityListPersonCellBinding binding;
        if (view == null) {
            binding = ActivityListPersonCellBinding.inflate(LayoutInflater.from(parent.getContext()));
            view = binding.getRoot();
        } else {
            binding = ActivityListPersonCellBinding.bind(convertView);
        }
        Person person = getItem(position);
        binding.tvFirstName.setText(person.firstname);
        binding.tvLastName.setText(person.lastname);
        return view;
    }
}
