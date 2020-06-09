package com.example.cultucesar.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.cultucesar.Adaptadores.AdapterMain;
import com.example.cultucesar.R;

public class MainFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        ViewPager viewPager = view.findViewById(R.id.viewPager);
        AdapterMain adapter = new AdapterMain(getActivity().getApplicationContext());
        viewPager.setAdapter(adapter);
        return view;
    }
}
