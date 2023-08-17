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

public class UserProductAdapter extends RecyclerView.Adapter<UserProductAdapter.ViewHolder> {
    Context context;
    ArrayList<Products> productsArrayList;
    private UserProductAdapter.OnItemClickListener listener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(UserProductAdapter.OnItemClickListener clickListener) {
        listener = clickListener;
    }

    public UserProductAdapter(Context context, ArrayList<Products> productsArrayList) {
        this.context = context;
        this.productsArrayList = productsArrayList;
    }

    public void setProducts(ArrayList<Products> products) {
        this.productsArrayList = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_products_item, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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
        private ImageView productImageView;
        private TextView productName, productPrice;
        private CardView productCardView;
        public ViewHolder(View view, UserProductAdapter.OnItemClickListener listener) {
            super(view);

            productImageView = view.findViewById(R.id.productImageView);
            productName = view.findViewById(R.id.productName);
            productPrice = view.findViewById(R.id.productPrice);
            productCardView = view.findViewById(R.id.productCardView);

            productCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getAdapterPosition());
                }
            });
        }
        public TextView getProductNameTextView() {
            return productName;
        }
        public TextView getProductPriceTextView() {
            return productPrice;
        }
        public ImageView getProductImageView() {
            return productImageView;
        }
    }
}
