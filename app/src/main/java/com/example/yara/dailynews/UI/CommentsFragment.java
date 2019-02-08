package com.example.yara.dailynews.UI;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.yara.dailynews.Adapter.CommentaAdapter;
import com.example.yara.dailynews.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class CommentsFragment extends Fragment {
    private String TAG = CommentsFragment.class.getSimpleName();

    private EditText comment;
    private Button btn_send_comment;
    private RecyclerView recyclerView;
    private ImageView image;
    private TextView title;
    private ProgressBar progressBar;
    private LinearLayoutManager mlayoutManager;


    private DatabaseReference myRef;
    private SharedPreferences pref;

    private String news_title;
    private String image_url;
    private String user_id;
    private String user_email;
    ArrayList<String> comments = new ArrayList<>();



    public CommentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comments, container, false);
        image=view.findViewById(R.id.news_image);
        title=view.findViewById(R.id.tv_news_title);
        comment = view.findViewById(R.id.et_comment);
        btn_send_comment = view.findViewById(R.id.btn_send_comment);
        recyclerView=view.findViewById(R.id.rv_comments);
        progressBar=view.findViewById(R.id.pro_comments);

        mlayoutManager=new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(mlayoutManager);


        //get User ID
        pref=getActivity().getSharedPreferences(getString(R.string.ref_key),Context.MODE_PRIVATE);
        user_id=pref.getString(getString(R.string.id),null);
        user_email=pref.getString(getString(R.string.email),null);
        Log.d(TAG,"user_id:  "+user_id+"  user_email: "+user_email);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            news_title = bundle.getString("title");
            image_url = bundle.getString("image");
        }
        title.setText(news_title);
        Picasso.with(getContext()).load(image_url).placeholder(R.drawable.ic_no_photo_available).into(image);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("my_app_user").child("news").push();



        readComments();

        btn_send_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.child(user_id).setValue(comment.getText().toString()+"/"+user_email);
            }
        });

        return view;
    }

    private void readComments() {
        progressBar.setVisibility(View.VISIBLE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("my_app_user").child("news");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
                Log.d(TAG, " value."+  dataSnapshot.getValue());
                getComments((Map<String, String>) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }


    private void getComments(Map<String, String> value) {

        for (Map.Entry<String, String> entry : value.entrySet()){
            String singleUser =  entry.getValue();
            Log.d(TAG,"  "+singleUser);
            comments.add( singleUser);

        }
        CommentaAdapter commentaAdapter=new CommentaAdapter(comments,getActivity());
        recyclerView.setAdapter(commentaAdapter);
        Log.d(TAG,"  "+comments.size());
    }
}