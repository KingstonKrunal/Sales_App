package com.letsbiz.salesapp.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.letsbiz.salesapp.model.Feedback;
import com.letsbiz.salesapp.R;

import java.text.DateFormat;

public class FeedbackListAdapter extends FirestoreRecyclerAdapter<Feedback, FeedbackListAdapter.ViewHolder> {

    private FeedbackAdapterClickListeners listener;

    public FeedbackListAdapter(@NonNull FirestoreRecyclerOptions<Feedback> options) {
        super(options);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_list_view_item, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Feedback model) {
        holder.shopNameText.setText(model.getShopName());
        String res = "Not added to servers";
        if(model.getDate() != null) {
            res = DateFormat.getDateInstance(DateFormat.MEDIUM).format(model.getDate()) + " ";
            res += DateFormat.getTimeInstance(DateFormat.SHORT).format(model.getDate());
        }

        holder.dateAddedText.setText(res);
        holder.ownerNameText.setText(model.getOwnerName());
    }

    public void deleteItem(final int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView shopNameText, ownerNameText, dateAddedText;
        ImageButton editFeedBackImgBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shopNameText = itemView.findViewById(R.id.flv_shop_name_text);
            ownerNameText = itemView.findViewById(R.id.flv_owner_name_text);
            dateAddedText = itemView.findViewById(R.id.flv_date_added_text);
            editFeedBackImgBtn = itemView.findViewById(R.id.flv_edit_img_btn);

            editFeedBackImgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onEditClickedListener(getSnapshots().getSnapshot(position), position);
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onViewClickedListener(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface FeedbackAdapterClickListeners {
        void onEditClickedListener(DocumentSnapshot documentSnapshot, int position);
        void onViewClickedListener(DocumentSnapshot documentSnapshot, int position);
    }

    public void setFeedbackAdapterClickListeners(FeedbackAdapterClickListeners listener) {
        this.listener = listener;
    }
}
