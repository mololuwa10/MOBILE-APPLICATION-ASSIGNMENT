package com.example.molosecondassignment.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.molosecondassignment.Classes.Category;
import com.example.molosecondassignment.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    Context context;
    ArrayList<Category> categoryArrayList;
    int selectedPosition = RecyclerView.NO_POSITION;
    CategoryViewHolder.OnItemClickListener listener;

    public void setOnItemClickListener(CategoryViewHolder.OnItemClickListener listener) {
        this.listener = listener;
    }

    public CategoryAdapter(Context context, ArrayList<Category> categoryArrayList) {
        this.context = context;
        this.categoryArrayList = categoryArrayList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.categories_item, parent, false), (AdapterView.OnItemClickListener) listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categoryArrayList.get(position);
        holder.getTextView().setText(category.getCategoryName());
        holder.getCategoryImage().setImageBitmap(category.getCategoryImage());

        if (position == selectedPosition) {
            holder.categoryLayout.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.light_grey));
        } else {
            holder.resetBackground();
        }
    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    public void setSelectedPosition(int position) {
        int previousSelectedPosition = selectedPosition;
        selectedPosition = position;

        notifyItemChanged(previousSelectedPosition);
        notifyItemChanged(selectedPosition);
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView categoryImage;
        TextView categoryName;
        private ConstraintLayout categoryLayout;
        private AdapterView.OnItemClickListener listener;
        public CategoryViewHolder(@NonNull View itemView, AdapterView.OnItemClickListener listener) {
            super(itemView);

            this.listener = listener;
            itemView.setOnClickListener(this);
            categoryImage = (ImageView) itemView.findViewById(R.id.categoryImage);
            categoryName = (TextView) itemView.findViewById(R.id.categoryName);
            categoryLayout = itemView.findViewById(R.id.category_layout);
        }

        public TextView getTextView() {
            return categoryName;
        }

        public ImageView getCategoryImage() {
            return categoryImage;
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                categoryLayout.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.light_grey));
            }
        }

        public void resetBackground() {
            categoryLayout.setBackgroundResource(android.R.color.transparent);
        }

        public interface OnItemClickListener {
            void onItemClick(int position);
        }

    }
}
