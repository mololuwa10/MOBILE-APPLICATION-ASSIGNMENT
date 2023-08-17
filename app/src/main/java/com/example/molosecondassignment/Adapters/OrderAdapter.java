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

import com.example.molosecondassignment.Classes.Baskets;
import com.example.molosecondassignment.Classes.DbClass;
import com.example.molosecondassignment.Classes.Products;
import com.example.molosecondassignment.Classes.SharedPreferenceManager;
import com.example.molosecondassignment.OrderActivity;
import com.example.molosecondassignment.R;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    Context context;
    ArrayList<Baskets> baskets;
    SharedPreferenceManager sharedPreferenceManager;
    DbClass dbClass;

    public OrderAdapter(Context context, ArrayList<Baskets> baskets) {
        this.context = context;
        this.baskets = baskets;
        dbClass = new DbClass(context);
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkout_layout_item, parent, false);
        return new OrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        Baskets baskets1 = baskets.get(position);

        sharedPreferenceManager = SharedPreferenceManager.getInstance(context);
        int userId = sharedPreferenceManager.getUserId();
        String userName = sharedPreferenceManager.getFullName();
        String address = sharedPreferenceManager.getAddress();

        if(baskets1.getUserId() == userId) {
            int productId = baskets1.getProductId();
            Products product = dbClass.getProductById(productId);

            holder.getCheckOutProductName().setText(product.getProductName());
            holder.getCheckOutProductPrice().setText(product.getProductPrice());
            int productQuantity = Integer.parseInt(baskets1.getBasketQuantity());
            holder.getCheckOutQuantity().setText("Quantity: " + String.valueOf(productQuantity));
            holder.getCheckoutTotalProduct().setText("Total: " + baskets1.getBasketPrice());
            holder.getCheckoutProductImageView().setImageBitmap(product.getProductImage());
        } else {
            holder.itemView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return baskets.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView checkOutProductName, checkOutProductPrice,
                checkOutQuantity, checkoutTotalProduct;
        private ImageView checkoutProductImageView;

        public ViewHolder(View view) {
            super(view);

//            userNameAddress = view.findViewById(R.id.userNameAddress);
            checkOutProductName = view.findViewById(R.id.checkOutProductName);
            checkOutProductPrice = view.findViewById(R.id.checkOutProductPrice);
            checkOutQuantity = view.findViewById(R.id.checkOutQuantity);
            checkoutTotalProduct = view.findViewById(R.id.checkoutTotalProduct);
            checkoutProductImageView = view.findViewById(R.id.checkoutProductImageView);
        }

        public TextView getCheckOutProductName() {
            return checkOutProductName;
        }
        public TextView getCheckOutProductPrice() {
            return checkOutProductPrice;
        }
        public TextView getCheckOutQuantity() {
            return checkOutQuantity;
        }
        public TextView getCheckoutTotalProduct() {
            return checkoutTotalProduct;
        }
        public ImageView getCheckoutProductImageView() {
            return checkoutProductImageView;
        }
    }
}
