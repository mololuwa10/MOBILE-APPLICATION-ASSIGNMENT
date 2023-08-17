package com.example.molosecondassignment.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.molosecondassignment.Classes.DbClass;
import com.example.molosecondassignment.Classes.Orders;
import com.example.molosecondassignment.Classes.Products;
import com.example.molosecondassignment.Classes.SharedPreferenceManager;
import com.example.molosecondassignment.Classes.Users;
import com.example.molosecondassignment.R;

import java.util.ArrayList;

public class AllOrdersAdapter extends RecyclerView.Adapter<AllOrdersAdapter.ViewHolder> {
    Context context;
    ArrayList<Orders> orders;
    SharedPreferenceManager sharedPreferenceManager;
    DbClass dbClass;
    private AllOrdersAdapter.OnItemClickListener listener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(AllOrdersAdapter.OnItemClickListener clickListener) {
        listener = clickListener;
    }

    public AllOrdersAdapter(Context context, ArrayList<Orders> orders) {
        this.context = context;
        this.orders = orders;
        dbClass = new DbClass(context);
    }

    @NonNull
    @Override
    public AllOrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_orders_item, parent, false);
        return new AllOrdersAdapter.ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull AllOrdersAdapter.ViewHolder holder, int position) {
        Orders orders1 = orders.get(position);

        sharedPreferenceManager = SharedPreferenceManager.getInstance(context);
        int userId = sharedPreferenceManager.getUserId();
        String dateOrdered = orders1.getDateOrdered();

        String totalAmount = orders1.getTotalAmount();
        String orderStatus = orders1.getOrderStatus();
        String shippingAddress = orders1.getShippingAddress();

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
        }
//        holder.getAllOrderDateOrdered().setText("Date Ordered: " + dateOrdered);
        holder.getOrderStatus().setText(orderStatus);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout productContainer;
        private ImageView allOrderProductImageView;
        private TextView allOrderProductName, allOrderProductPrice, orderStatus, allOrderDateOrdered;
        private CardView allOrderProductCardView;
        public ViewHolder(View view, AllOrdersAdapter.OnItemClickListener listener) {
            super(view);

            productContainer = view.findViewById(R.id.productContainer);
            allOrderProductImageView = view.findViewById(R.id.allOrderProductImageView);
            allOrderProductName = view.findViewById(R.id.allOrderProductName);
            allOrderProductPrice = view.findViewById(R.id.allOrderProductPrice);
            orderStatus = view.findViewById(R.id.orderStatus);
            allOrderProductCardView = view.findViewById(R.id.allOrderProductCardView);
//            allOrderDateOrdered = view.findViewById(R.id.allOrderDateOrdered);

            allOrderProductCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getAdapterPosition());
                }
            });
        }
        public TextView getAllOrderProductName() {
            return allOrderProductName;
        }
        public TextView getAllOrderProductPrice() {
            return allOrderProductPrice;
        }
        public ImageView getAllOrderProductImageView() {
            return allOrderProductImageView;
        }
        public TextView getOrderStatus() {
            return orderStatus;
        }

//        public TextView getAllOrderDateOrdered() {
//            return allOrderDateOrdered;
//        }
    }
}
