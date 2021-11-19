package com.bidamcat.tp11kakaoplacesearchapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class SearchListFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_list, container, false);
    }

    RecyclerView recyclerView;
    PlaceListRecyclerAdaper placeListRecyclerAdaper;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView= view.findViewById(R.id.recyclerview);

        //MainActivity의 Place데이터들을 가져오기 위해 MainActivity를 참조
        MainActivity ma= (MainActivity)getActivity();
        if(ma.searchLocalApiResponse==null) return;

        placeListRecyclerAdaper= new PlaceListRecyclerAdaper(getActivity(), ma.searchLocalApiResponse.documents);
        recyclerView.setAdapter(placeListRecyclerAdaper);

    }
}
