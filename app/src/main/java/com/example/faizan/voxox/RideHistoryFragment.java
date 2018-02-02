package com.example.faizan.voxox;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


public class RideHistoryFragment extends Fragment {

    RecyclerView grid;
    historyCardAdapter adapter;
    GridLayoutManager manager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ride_history , container , false);

        grid = (RecyclerView) view.findViewById(R.id.grid);

        grid = view.findViewById(R.id.grid);
        manager = new GridLayoutManager(getContext() , 1);
        adapter = new historyCardAdapter(getContext());
        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);


        return view;

    }


}
