package com.example.littlecafeshop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShopFragment extends Fragment {

    List<Items> items;
    DatabaseReference mDatabase;

    String type = "";

    private void getData(final View v) {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        items = new ArrayList<>();

        if(type.equals("all")) {

            mDatabase.child("menu").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {

                        Log.w("DATABASE",data.getKey().toString());

                        for (DataSnapshot d : data.getChildren()) {

                            Log.w("CHILD", d.getKey().toString());
                            Log.w("ITEMS", d.child("price").getValue().toString());

                            items.add(new Items(d.getKey().toString(), d.child("description").getValue().toString(), d.child("imgURL").getValue().toString(), d.child("price").getValue().toString()));

                        }
                    }
                    fetchData(v);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else if (type.equals("breakfast")) {
            mDatabase.child("menu").child("Breakfast").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot data : dataSnapshot.getChildren()) {
                        items.add(new Items(data.getKey(), data.child("description").getValue().toString(), data.child("imgURL").getValue().toString(), data.child("price").getValue().toString()));
                    }
                    fetchData(v);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else if (type.equals("espresso")) {

            mDatabase.child("menu").child("Expresso").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot data : dataSnapshot.getChildren()) {
                        items.add(new Items(data.getKey(), data.child("description").getValue().toString(), data.child("imgURL").getValue().toString(), data.child("price").getValue().toString()));
                    }
                    fetchData(v);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else if (type.equals("lunch")) {

            mDatabase.child("menu").child("Lunch").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot data : dataSnapshot.getChildren()) {
                        items.add(new Items(data.getKey(), data.child("description").getValue().toString(), data.child("imgURL").getValue().toString(), data.child("price").getValue().toString()));
                    }
                    fetchData(v);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else if (type.equals("munchles")) {

            mDatabase.child("menu").child("Munchies").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot data : dataSnapshot.getChildren()) {
                        items.add(new Items(data.getKey(), data.child("description").getValue().toString(), data.child("imgURL").getValue().toString(), data.child("price").getValue().toString()));
                    }
                    fetchData(v);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }

    private void fetchData(View v) {

        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);

        Log.w("DATA", items.size() + "");

        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getActivity().getApplicationContext(),items);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 2));

        recyclerView.setAdapter(myAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_shop, container, false);

        type = getArguments().get("type").toString();

        Log.w("SHOPTYPE", type);

        getData(view);

        return view;
    }

}
