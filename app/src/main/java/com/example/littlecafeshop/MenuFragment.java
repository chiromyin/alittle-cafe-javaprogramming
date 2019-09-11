package com.example.littlecafeshop;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;

import java.util.List;



public class MenuFragment extends Fragment {

    Fragment dataFragment;
    Button breakfastbutton,espressobtn,lunchbtn,munchlesbtn;
    Bundle args;
    private FragmentActivity myContext;

    @Override
    public void onAttach(Context context) {
        myContext=(FragmentActivity) context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_menu, container, false);

        breakfastbutton = view.findViewById(R.id.breakfastbtn);
        espressobtn = view.findViewById(R.id.espressobtn);
        lunchbtn = view.findViewById(R.id.lunchbtn);
        munchlesbtn = view.findViewById(R.id.munchlesbtn);

        final FragmentManager fragManager = myContext.getSupportFragmentManager();

        dataFragment = new ShopFragment();
        args = new Bundle();

        breakfastbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                args.putString("type", "breakfast");
                dataFragment.setArguments(args);
                fragManager.beginTransaction().replace(R.id.containerFragment, dataFragment).commit();
            }
        });

        espressobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                args.putString("type", "espresso");
                dataFragment.setArguments(args);
                fragManager.beginTransaction().replace(R.id.containerFragment, dataFragment).commit();
            }
        });

        lunchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                args.putString("type", "lunch");
                dataFragment.setArguments(args);
                fragManager.beginTransaction().replace(R.id.containerFragment, dataFragment).commit();
            }
        });

        munchlesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                args.putString("type", "munchles");
                dataFragment.setArguments(args);
                fragManager.beginTransaction().replace(R.id.containerFragment, dataFragment).commit();
            }
        });

        return view;
    }



}
