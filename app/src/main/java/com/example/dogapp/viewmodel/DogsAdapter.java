package com.example.dogapp.viewmodel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dogapp.R;
import com.example.dogapp.model.DogBreed;
import com.example.dogapp.view.DetailsFragment;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;

public class DogsAdapter extends RecyclerView.Adapter<DogsAdapter.ViewHolder> {
    ArrayList<DogBreed> dogBreeds;

    public DogsAdapter(ArrayList<DogBreed> dogBreeds) {
        this.dogBreeds = dogBreeds;
    }

    @NonNull
    @Override
    public DogsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dogs_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DogsAdapter.ViewHolder holder, int position) {
        DogBreed dogBreed = dogBreeds.get(position);
        holder.title.setText(dogBreed.getName());
        holder.origin.setText(dogBreed.getOrigin());
        holder.card_name.setText(dogBreed.getName());
        holder.card_lifespan.setText(dogBreed.getLifeSpan());
        holder.card_origin.setText(dogBreed.getOrigin());
        holder.card_temperament.setText(dogBreed.getTemperament());

        if (dogBreed.getShowCard() == null)
            dogBreed.setShowCard(false);

        holder.dogView.setVisibility(dogBreed.getShowCard() == false ? View.VISIBLE : View.INVISIBLE);
        holder.dogCard.setVisibility(dogBreed.getShowCard() == true ? View.VISIBLE : View.INVISIBLE);

        Picasso.get().load(dogBreed.getUrl()).into(holder.img);

        holder.bind(dogBreed);
    }

    @Override
    public int getItemCount() {
        return dogBreeds.size();
    }

    public void filter(ArrayList<DogBreed> dogBreedsTemp) {
        this.dogBreeds = new ArrayList<>(dogBreedsTemp);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView img;
        private final TextView title;
        private final TextView origin;
        private final TextView card_name;
        private final TextView card_lifespan;
        private final TextView card_origin;
        private final TextView card_temperament;
        private final LinearLayout dogView, dogCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.iv_img);
            title = itemView.findViewById(R.id.tv_name);
            origin = itemView.findViewById(R.id.tv_origin);
            card_name = itemView.findViewById(R.id.dogcard_name);
            card_lifespan = itemView.findViewById(R.id.dogcard_lifespan);
            card_origin = itemView.findViewById(R.id.dogcard_origin);
            card_temperament = itemView.findViewById(R.id.dogcard_temperament);
            dogView = itemView.findViewById(R.id.dogview);
            dogCard = itemView.findViewById(R.id.dogcard);
        }

        public void bind(final DogBreed dogBreed) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (dogBreed != null) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("item", dogBreed);
                        Navigation.findNavController(view).navigate(R.id.detailsFragment, bundle);
                    }
                }
            });
        }
    }

    public void switchDisplayCard(Integer position) {
        dogBreeds.get(position).setShowCard(!dogBreeds.get(position).getShowCard());
        notifyDataSetChanged();
    }
}
