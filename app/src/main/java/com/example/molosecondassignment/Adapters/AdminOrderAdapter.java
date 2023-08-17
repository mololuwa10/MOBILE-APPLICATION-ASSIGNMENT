package com.example.molosecondassignment.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.molosecondassignment.Classes.DbClass;
import com.example.molosecondassignment.Classes.Orders;
import com.example.molosecondassignment.Classes.Products;
import com.example.molosecondassignment.Classes.SharedPreferenceManager;
import com.example.molosecondassignment.R;

import java.util.ArrayList;


public class AdminOrderAdapter extends RecyclerView.Adapter<AdminOrderAdapter.ViewHolder> {
    Context context;
    ArrayList<Orders> orders;
    SharedPreferenceManager sharedPreferenceManager;
    DbClass dbClass;
    private AdminOrderAdapter.OnItemClickListener listener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(AdminOrderAdapter.OnItemClickListener clickListener) {
        listener = clickListener;
    }

    public AdminOrderAdapter(Context context, ArrayList<Orders> orders) {
        this.context = context;
        this.orders = orders;
        dbClass = new DbClass(context);
    }

    @NonNull
    @Override
    public AdminOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_order_item, parent, false);
        return new AdminOrderAdapter.ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminOrderAdapter.ViewHolder holder, int position) {
        Orders orders1 = orders.get(position);

        int userId = orders1.getUserId();
        String orderStatus = orders1.getOrderStatus();

        // Access products associated with the order
        ArrayList<Products> productsList = orders1.getProducts();

        for (Products product : productsList) {
            int productId = product.getId();
            Log.d("ORDERS ADAPTER", String.valueOf(productId));
            String productName = product.getProductName();
            String productPrice = product.getProductPrice();
            Bitmap productImage = product.getProductImage();

            Log.d("ORDERS ADAPTER", productName);

            // Set the product details in the ViewHolder
            holder.getAllOrderProductName().setText(productName);
            holder.getAllOrderProductPrice().setText("Price: " + productPrice);
            holder.getAllOrderProductImageView().setImageBitmap(productImage);

            String fullName = dbClass.getUserFullName(userId);
            holder.getUserWhoOrdered().setText("Ordered By: " + fullName);
        }
        holder.getOrderStatus().setText(orderStatus);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView adminAllOrderProductImageView;
        private TextView adminAllOrderProductName, adminAllOrderProductPrice, adminOrderStatus, userWhoOrdered;
        private CardView adminAllOrderProductCardView;
        public ViewHolder(View view, AdminOrderAdapter.OnItemClickListener listener) {
            super(view);

            adminAllOrderProductImageView = view.findViewById(R.id.adminAllOrderProductImageView);
            adminAllOrderProductName = view.findViewById(R.id.adminAllOrderProductName);
            adminAllOrderProductPrice = view.findViewById(R.id.adminAllOrderProductPrice);
            userWhoOrdered = view.findViewById(R.id.userWhoOrdered);
            adminOrderStatus = view.findViewById(R.id.adminOrderStatus);
            adminAllOrderProductCardView = view.findViewById(R.id.adminAllOrderProductCardView);

            adminAllOrderProductCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getAdapterPosition());
                }
            });
        }
        public TextView getAllOrderProductName() {
            return adminAllOrderProductName;
        }
        public TextView getAllOrderProductPrice() {
            return adminAllOrderProductPrice;
        }
        public ImageView getAllOrderProductImageView() {
            return adminAllOrderProductImageView;
        }
        public TextView getOrderStatus() {
            return adminOrderStatus;
        }
        public TextView getUserWhoOrdered() {
            return userWhoOrdered;
        }
    }
}
