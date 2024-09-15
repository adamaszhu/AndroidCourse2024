package com.adamas.androidcourse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adamas.androidcourse.databinding.ActivityRecyclePersonCellBinding;

import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.RecycleViewHolder> {

    private List<Person> personList;

    public RecycleAdapter(List<Person> personList) {
        this.personList = personList;
    }

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ActivityRecyclePersonCellBinding binding = ActivityRecyclePersonCellBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new RecycleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder holder, int position) {
        // Update the view holder with new data
        Person person = personList.get(position);
        holder.setPerson(person);
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    static class RecycleViewHolder extends RecyclerView.ViewHolder {

        private ActivityRecyclePersonCellBinding binding;

        public RecycleViewHolder(ActivityRecyclePersonCellBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setPerson(Person person) {
            binding.tvFirstName.setText(person.firstname);
            binding.tvLastName.setText(person.lastname);
        }
    }
}
