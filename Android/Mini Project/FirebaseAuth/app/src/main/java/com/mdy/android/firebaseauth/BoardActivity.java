package com.mdy.android.firebaseauth;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BoardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private List<ImageDTO> imageDTOs = new ArrayList<>();
    private List<String> uidLists = new ArrayList<>();
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        database = FirebaseDatabase.getInstance();

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final RecyclerAdapter adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);

        database.getReference().child("images").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                imageDTOs.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ImageDTO imageDTO = snapshot.getValue(ImageDTO.class);
                    imageDTOs.add(imageDTO);
                }
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }





    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder>{

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_board, parent, false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            holder.txtTitle.setText(imageDTOs.get(position).title);
            holder.txtDescription.setText(imageDTOs.get(position).description);

            Glide.with(holder.imageView.getContext()).load(imageDTOs.get(position).imageUrl).into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return imageDTOs.size();
        }

        class Holder extends RecyclerView.ViewHolder{
            ImageView imageView;
            TextView txtTitle, txtDescription;
            public Holder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.imageView);
                txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
                txtDescription = (TextView) itemView.findViewById(R.id.txtDescription);
            }
        }
    }
}
