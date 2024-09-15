package com.adamas.androidcourse;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adamas.androidcourse.databinding.FragmentSampleBinding;

public class SampleFragment extends Fragment {

    private static final String NAME_PARAM = "name";
    private static final String DESCRIPTION_PARAM = "description";

    private String name;
    private String description;

    public SampleFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param name Name of the fragment.
     * @param description Description of the fragment.
     * @return A new instance of fragment SampleFragment.
     */
    public static SampleFragment newInstance(String name, String description) {
        SampleFragment fragment = new SampleFragment();
        Bundle args = new Bundle();
        args.putString(NAME_PARAM, name);
        args.putString(DESCRIPTION_PARAM, description);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(NAME_PARAM);
            description = getArguments().getString(DESCRIPTION_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentSampleBinding binding = FragmentSampleBinding.inflate(inflater);
        binding.tvFragmentSample.setText(String.format("%s %s", name, description));
        return binding.getRoot();
    }
}