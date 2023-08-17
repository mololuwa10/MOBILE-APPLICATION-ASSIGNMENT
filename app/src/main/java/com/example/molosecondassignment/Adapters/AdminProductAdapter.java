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


import com.example.molosecondassignment.Classes.Products;
import com.example.molosecondassignment.R;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class AdminProductAdapter extends RecyclerView.Adapter<AdminProductAdapter.ViewHolder> {
    Context context;
    ArrayList<Products> productsArrayList;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onEditItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        listener = clickListener;
    }

    public AdminProductAdapter(Context context, ArrayList<Products> productsArrayList) {
        this.context = context;
        this.productsArrayList = productsArrayList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImageView;
        TextView subTextView, productIdTextView;
        private Button editProductButton, deleteProductButton;
        public ViewHolder(View view, OnItemClickListener listener) {
            super(view);

            productIdTextView = view.findViewById(R.id.productIdTextView);
            productImageView = view.findViewById(R.id.productImageView);
            editProductButton = view.findViewById(R.id.editProductButton);
            deleteProductButton = view.findViewById(R.id.deleteProductButton);
            subTextView = view.findViewById(R.id.subTextView);

            deleteProductButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getAdapterPosition());
                }
            });

            editProductButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onEditItemClick(getAdapterPosition());
                }
            });
        }
        public TextView getProductNameTextView() {
            return subTextView;
        }
        public ImageView getProductImageView() {
            return productImageView;
        }
        public TextView getIdTextView() {
            return productIdTextView;
        }
    }

    @NonNull
    @Override
    public AdminProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_products_item, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminProductAdapter.ViewHolder holder, int position) {
        Products products = productsArrayList.get(position);

        holder.getIdTextView().setText(Integer.toString(products.getId()) + ". ");
        holder.getProductNameTextView().setText(products.getProductName());
        holder.getProductImageView().setImageBitmap(products.getProductImage());
    }

    @Override
    public int getItemCount() {
        return productsArrayList.size();
    }
}
