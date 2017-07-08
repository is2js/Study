package com.mdy.android.firebaseauth;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class BoardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private List<ImageDTO> imageDTOs = new ArrayList<>();   // 키값들 아래 있는 한세트씩의 값들
    private List<String> uidLists = new ArrayList<>();      // DB에서 images 아래에 있는 키값들
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final RecyclerAdapter adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);

        database.getReference().child("images").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                imageDTOs.clear();
                uidLists.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ImageDTO imageDTO = snapshot.getValue(ImageDTO.class);
                    String uidKey = snapshot.getKey();
                    imageDTOs.add(imageDTO);
                    uidLists.add(uidKey);
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_board, parent, false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(Holder holder, final int position) {
            holder.txtTitle.setText(imageDTOs.get(position).title);
            holder.txtDescription.setText(imageDTOs.get(position).description);

            Glide.with(holder.imageView.getContext()).load(imageDTOs.get(position).imageUrl).into(holder.imageView);
            holder.imageViewStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onStarClicked(database.getReference().child("images").child(uidLists.get(position)));
                }
            });

            if (imageDTOs.get(position).stars.containsKey(auth.getCurrentUser().getUid())) {
                holder.imageViewStar.setImageResource(R.drawable.black_heart);
            } else {
                holder.imageViewStar.setImageResource(R.drawable.black_heart_outline);
            }

            holder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteContent(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return imageDTOs.size();
        }

        private void onStarClicked(DatabaseReference postRef) {
            postRef.runTransaction(new Transaction.Handler() {
                @Override
                public Transaction.Result doTransaction(MutableData mutableData) {
                    ImageDTO p = mutableData.getValue(ImageDTO.class);
                    if (p == null) {
                        return Transaction.success(mutableData);
                    }

                    if (p.stars.containsKey(auth.getCurrentUser().getUid())) {
                        // Unstar the post and remove self from stars
                        p.starCount = p.starCount - 1;
                        p.stars.remove(auth.getCurrentUser().getUid());
                    } else {
                        // Star the post and add self to stars
                        p.starCount = p.starCount + 1;
                        p.stars.put(auth.getCurrentUser().getUid(), true);
                    }

                    // Set value and report transaction success
                    mutableData.setValue(p);
                    return Transaction.success(mutableData);
                }

                @Override
                public void onComplete(DatabaseError databaseError, boolean b,
                                       DataSnapshot dataSnapshot) {
                    // Transaction completed
                    Log.d("onComplete TAG", "postTransaction:onComplete:" + databaseError);
                }
            });
        }

        private void deleteContent(final int position){

            storage.getReference().child("images").child(imageDTOs.get(position).imageName).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // 삭제가 완료되었을때 서버가 응답해주는 부분
                    database.getReference().child("images").child(uidLists.get(position)).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(BoardActivity.this, "삭제가 완료 되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(BoardActivity.this, "삭제가 되지 않았습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // 삭제가 완료되지 않았을때 서버가 응답해주는 부분
                    Toast.makeText(BoardActivity.this, "이미지가 삭제되지 않았습니다", Toast.LENGTH_SHORT).show();
                }
            });

            // 데이터베이스 삭제 방법 1
            // database.getReference().child("images").child("DB 키값").setValue(null);

            // 데이터베이스 삭제 방법 2
            // database.getReference().child("images").child(uidLists.get(position)).removeValue();


        }

        class Holder extends RecyclerView.ViewHolder{
            ImageView imageView;
            TextView txtTitle, txtDescription;
            ImageView imageViewStar;
            ImageView imageViewDelete;
            public Holder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.imageView);
                txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
                txtDescription = (TextView) itemView.findViewById(R.id.txtDescription);
                imageViewStar = (ImageView) itemView.findViewById(R.id.imageViewStar);
                imageViewDelete = (ImageView) itemView.findViewById(R.id.imageViewDelete);
            }
        }
    }
}
