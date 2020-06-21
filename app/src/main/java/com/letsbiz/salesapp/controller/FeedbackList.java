package com.letsbiz.salesapp.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.letsbiz.salesapp.model.Feedback;
import com.letsbiz.salesapp.model.FirebaseConstants;
import com.letsbiz.salesapp.R;
import com.letsbiz.salesapp.model.User;

public class FeedbackList extends AppCompatActivity implements FeedbackListAdapter.FeedbackAdapterClickListeners {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference feedbackRef = db.collection(FirebaseConstants.FEEDBACK);
    EditFeedbackDialogFragment dialogFragment;

    private FeedbackListAdapter adapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_list);

        dialogFragment = new EditFeedbackDialogFragment();

        setUpRecyclerView();

        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setTitle("Feedback List");
        }
    }

    private Query getQueryForRecycler() {
        Intent i = getIntent();
        if(i.getBooleanExtra("isAdmin", false)) {
            return feedbackRef.orderBy("date", Query.Direction.DESCENDING);
        } else {
            return feedbackRef.whereEqualTo("uid", User.getUID()).orderBy("date", Query.Direction.DESCENDING);
        }
    }

    void setUpRecyclerView() {
        FirestoreRecyclerOptions<Feedback> options = new FirestoreRecyclerOptions.Builder<Feedback>()
                .setQuery(getQueryForRecycler(), Feedback.class)
                .setLifecycleOwner(this)
                .build();

        adapter = new FeedbackListAdapter(options);

        mRecyclerView = findViewById(R.id.feedback_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(adapter);

//        setSlideToDelete();

        adapter.setFeedbackAdapterClickListeners(this);
    }

    private void setSlideToDelete() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.deleteItem(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(mRecyclerView);

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
}