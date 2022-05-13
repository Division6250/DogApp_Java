package com.example.dogapp.view;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.dogapp.MainActivity;
import com.example.dogapp.R;
import com.example.dogapp.model.DogBreed;
import com.example.dogapp.viewmodel.DogsAdapter;
import com.example.dogapp.viewmodel.DogsApiService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ListFragment extends Fragment {

    private DogsApiService dogsApiService;
    private DogsAdapter dogsAdapter;
    private ArrayList<DogBreed> dogBreeds;

    private RecyclerView rvDogs;

    public ListFragment() {

    }

    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

        setHasOptionsMenu(true);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvDogs = view.findViewById(R.id.rv_dogs);
        rvDogs.setLayoutManager(new GridLayoutManager(getContext(), 2));

        dogBreeds = new ArrayList<DogBreed>();

        dogsAdapter = new DogsAdapter(dogBreeds);
        rvDogs.setAdapter(dogsAdapter);

        // Init API Service
        dogsApiService = new DogsApiService();
        dogsApiService.getDogs()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<DogBreed>>() {
                    @Override
                    public void onSuccess(@NonNull List<DogBreed> dogBreedsList) {
                        for (DogBreed dog: dogBreedsList) {
                            dogBreeds.add(dog);
                            dogsAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

        ItemTouchHelper.SimpleCallback touchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                dogsAdapter.switchDisplayCard(viewHolder.getAdapterPosition());
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelperCallback);
        itemTouchHelper.attachToRecyclerView(rvDogs);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    SearchView searchView;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menu.clear();

        menuInflater.inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = new SearchView(((MainActivity)getContext()).getSupportActionBar().getThemedContext());
        searchItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);
        searchItem.setActionView(searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void filter(String query) {
        ArrayList<DogBreed> filterList = new ArrayList<DogBreed>();

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!query.isEmpty() && query.length() > 0) {
                    for (DogBreed dog: dogBreeds) {
                        if (dog.getName().toLowerCase().contains(query.toLowerCase()))
                            filterList.add(dog);
                    }

                    dogsAdapter.filter(filterList);
                }
                else dogsAdapter.filter(dogBreeds);
            }
        });

    }
}
