package com.letsbiz.salesapp.Controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.letsbiz.salesapp.R;

public class FeedbackListAdapter extends RecyclerView.Adapter<FeedbackListAdapter.ViewHolder> {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_list_view_item, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView shopNameText, ownerNameText, dateAddedText;
        ImageButton editFeedBackImgBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shopNameText = itemView.findViewById(R.id.flv_shop_name_text);
            ownerNameText = itemView.findViewById(R.id.flv_owner_name_text);
            dateAddedText = itemView.findViewById(R.id.flv_date_added_text);
            editFeedBackImgBtn = itemView.findViewById(R.id.flv_edit_img_btn);
        }
    }
}
