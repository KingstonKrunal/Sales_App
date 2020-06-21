package com.letsbiz.salesapp.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.letsbiz.salesapp.model.Feedback;
import com.letsbiz.salesapp.model.FirebaseConstants;
import com.letsbiz.salesapp.R;

public class FeedbackList extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference feedbackRef = db.collection(FirebaseConstants.FEEDBACK);
    EditFeedbackDialogFragment dialogFragment;

    private FeedbackListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_list);
        setUpRecyclerView();

        dialogFragment = new EditFeedbackDialogFragment();
    }

    void setUpRecyclerView() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null) return;

        Query query = feedbackRef.whereEqualTo("uid", user.getUid()).orderBy("date", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Feedback> options = new FirestoreRecyclerOptions.Builder<Feedback>()
                .setQuery(query, Feedback.class)
                .setLifecycleOwner(this)
                .build();

        adapter = new FeedbackListAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.feedback_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

//        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                adapter.deleteItem(viewHolder.getAdapterPosition());
//            }
//        }).attachToRecyclerView(recyclerView);

        adapter.setFeedbackAdapterClickListeners(new FeedbackListAdapter.FeedbackAdapterClickListeners() {
            @Override
            public void onEditClickedListener(final DocumentSnapshot documentSnapshot, final int position) {
                dialogFragment.setOnItemSelectedListener(new EditFeedbackDialogFragment.OnItemSelectedListener() {
                    @Override
                    public void deleteSelected() {
                        adapter.deleteItem(position);
                    }

                    @Override
                    public void editSelected() {
                        Intent i = new Intent(FeedbackList.this, AddFeedback.class);
                        i.putExtra(FeedbackDetails.FEEDBACK, documentSnapshot.toObject(Feedback.class));
                        i.putExtra(FeedbackDetails.FEEDBACK_ID, documentSnapshot.getId());
                        startActivity(i);
                    }
                });

                dialogFragment.show(getSupportFragmentManager(), "Edit");
            }

            @Override
            public void onViewClickedListener(DocumentSnapshot documentSnapshot, int position) {
                Intent i = new Intent(FeedbackList.this, FeedbackDetails.class);
                i.putExtra(FeedbackDetails.FEEDBACK, documentSnapshot.toObject(Feedback.class));
                i.putExtra(FeedbackDetails.FEEDBACK_ID, documentSnapshot.getId());
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}