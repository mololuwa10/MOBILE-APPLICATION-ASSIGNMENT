package com.example.molosecondassignment.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.molosecondassignment.Classes.Category;
import com.example.molosecondassignment.Classes.Products;
import com.example.molosecondassignment.R;

import java.util.ArrayList;

public class UserCategoryAdapter extends RecyclerView.Adapter<UserCategoryAdapter.ViewHolder>  {
    Context context;
    ArrayList<Category> categories;

    private UserCategoryAdapter.OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(UserCategoryAdapter.OnItemClickListener clickListener) {
        listener = clickListener;
    }

    public UserCategoryAdapter(Context context, ArrayList<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public UserCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_categories_layout, parent, false);
        return new UserCategoryAdapter.ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull UserCategoryAdapter.ViewHolder holder, int position) {
        Category category = categories.get(position);

        holder.getCategoryNameTextView().setText(category.getCategoryName());
        holder.getCategoryImageView().setImageBitmap(category.getCategoryImage());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView categoryImageView;
        private TextView categoryName;
        private CardView categoryCardView;
        public ViewHolder(View view, UserCategoryAdapter.OnItemClickListener listener) {
            super(view);

            categoryImageView = view.findViewById(R.id.categoryImageView);
            categoryName = view.findViewById(R.id.categoryName);
            categoryCardView = view.findViewById(R.id.categoryCardView);

            categoryCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getAdapterPosition());
                }
            });
        }
        public TextView getCategoryNameTextView() {
            return categoryName;
        }
        public ImageView getCategoryImageView() {
            return categoryImageView;
        }
    }
}
