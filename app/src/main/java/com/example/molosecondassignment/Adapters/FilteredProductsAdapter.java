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

import com.example.molosecondassignment.Classes.Products;
import com.example.molosecondassignment.R;

import java.util.ArrayList;

public class FilteredProductsAdapter extends RecyclerView.Adapter<FilteredProductsAdapter.ViewHolder>{
    Context context;
    ArrayList<Products> productsArrayList;
    private FilteredProductsAdapter.OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(FilteredProductsAdapter.OnItemClickListener clickListener) {
        listener = clickListener;
    }

    public FilteredProductsAdapter(Context context, ArrayList<Products> productsArrayList) {
        this.context = context;
        this.productsArrayList = productsArrayList;
    }

    @NonNull
    @Override
    public FilteredProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filtered_products_home_layout, parent, false);
        return new FilteredProductsAdapter.ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull FilteredProductsAdapter.ViewHolder holder, int position) {
        Products products = productsArrayList.get(position);

        holder.getProductNameTextView().setText(products.getProductName());
        holder.getProductPriceTextView().setText(products.getProductPrice());
        holder.getProductImageView().setImageBitmap(products.getProductImage());
    }

    @Override
    public int getItemCount() {
        return productsArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView filteredProductImageView;
        private TextView filteredProductName, filteredProductPrice;
        private CardView filteredProductCardView;
        private int position;
        public ViewHolder(View view, FilteredProductsAdapter.OnItemClickListener listener) {
            super(view);

            filteredProductImageView = view.findViewById(R.id.filteredProductImageView);
            filteredProductName = view.findViewById(R.id.filteredProductName);
            filteredProductPrice = view.findViewById(R.id.filteredProductPrice);
            filteredProductCardView = view.findViewById(R.id.filteredProductCardView);

            filteredProductCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getAdapterPosition());
                }
            });
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public TextView getProductNameTextView() {
            return filteredProductName;
        }
        public TextView getProductPriceTextView() {
            return filteredProductPrice;
        }
        public ImageView getProductImageView() {
            return filteredProductImageView;
        }
    }
}
