package com.example.dogapp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dogapp.R;
import com.example.dogapp.databinding.FragmentDetailsBinding;
import com.example.dogapp.model.DogBreed;
import com.squareup.picasso.Picasso;

public class DetailsFragment extends Fragment {
    private FragmentDetailsBinding binding;
    private DogBreed dogBreed;

    public DetailsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentDetailsBinding.inflate(getLayoutInflater());

        if (getArguments() != null) {
            dogBreed = (DogBreed) getArguments().getSerializable("item");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (dogBreed != null) {
            Picasso.get().load(dogBreed.getUrl()).into(binding.imageView2);
            binding.setDog(dogBreed);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return binding.getRoot();
    }
}
