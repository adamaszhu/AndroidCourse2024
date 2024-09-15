package com.adamas.androidcourse;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.adamas.androidcourse.databinding.ActivityRecyclePersonCellBinding;

import java.util.Objects;

public class RecycleListAdapter extends ListAdapter<Person, RecycleListAdapter.RecycleViewHolder> {

    static class PersonDiff extends DiffUtil.ItemCallback<Person> {

        @Override
        public boolean areItemsTheSame(@NonNull Person oldItem, @NonNull Person newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Person oldItem, @NonNull Person newItem) {
            return Objects.equals(oldItem.lastname, newItem.lastname) && Objects.equals(oldItem.firstname, newItem.firstname);
        }
    }

    protected RecycleListAdapter(@NonNull PersonDiff diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ActivityRecyclePersonCellBinding binding = ActivityRecyclePersonCellBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new RecycleListAdapter.RecycleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder holder, int position) {
        Person person = getItem(position);
        holder.setPerson(person);
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
