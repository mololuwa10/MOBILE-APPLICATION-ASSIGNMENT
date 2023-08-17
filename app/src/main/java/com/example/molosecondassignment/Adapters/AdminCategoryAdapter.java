package com.example.molosecondassignment.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.molosecondassignment.Classes.Category;
import com.example.molosecondassignment.R;

import java.util.ArrayList;
public class AdminCategoryAdapter extends RecyclerView.Adapter<AdminCategoryAdapter.ViewHolder> {
    Context context;
    ArrayList<Category> categoryArrayList;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onEditClick(int position);
    }


    public void setOnItemClickListener(OnItemClickListener clickListener) {
        listener = clickListener;
    }

    public AdminCategoryAdapter(Context context, ArrayList<Category> categoryArrayList) {
        this.context = context;
        this.categoryArrayList = categoryArrayList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView subTextView, IdTextView;
        private ImageView categoryImageView;
        private Button editCategoryButton, deleteCategoryButton;
        public ViewHolder(View view, OnItemClickListener listener) {
            super(view);

            subTextView = view.findViewById(R.id.subTextView);
            editCategoryButton = view.findViewById(R.id.editCategoryButton);
            deleteCategoryButton = view.findViewById(R.id.deleteCategoryButton);
            categoryImageView = view.findViewById(R.id.categoryImageView);
            IdTextView = view.findViewById(R.id.IdTextView);

            editCategoryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onEditClick(getAdapterPosition());
                }
            });

            deleteCategoryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getAdapterPosition());
                }
            });

        }
        public TextView getTextView() {
            return subTextView;
        }
        public ImageView getImageView() {
            return categoryImageView;
        }
        public TextView getIdTextView() {
            return IdTextView;
        }
    }

    @NonNull
    @Override
    public AdminCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_categories_items, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminCategoryAdapter.ViewHolder holder, int position) {
        Category category = categoryArrayList.get(position);

        holder.getIdTextView().setText(Integer.toString(category.getId()) + ". ");
        holder.getTextView().setText(category.getCategoryName());
        holder.getImageView().setImageBitmap(category.getCategoryImage());
    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }
}
