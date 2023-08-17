package com.example.molosecondassignment.Adapters;

import android.content.Context;
import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.molosecondassignment.CartActivity;
import com.example.molosecondassignment.Classes.Baskets;
import com.example.molosecondassignment.Classes.DbClass;
import com.example.molosecondassignment.Classes.Products;
import com.example.molosecondassignment.Classes.SharedPreferenceManager;
import com.example.molosecondassignment.R;

import java.util.ArrayList;

public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.ViewHolder> {
    Context context;
    ArrayList<Baskets> baskets;
    DbClass dbClass;
    SharedPreferenceManager sharedPreferenceManager;
    private OnItemClickListener listener;
    public interface OnItemClickListener{
        void onItemClick(int position);
        void onAddItemClick(int position);
        void onSubtractClick(int position);
        void onDeleteClick(int position);
    }



    public void setOnItemClickListener(CartProductAdapter.OnItemClickListener clickListener) {
        listener = clickListener;
    }

    public CartProductAdapter(Context context, ArrayList<Baskets> baskets) {
        this.context = context;
        this.baskets = baskets;
        dbClass = new DbClass(context);
    }


    @NonNull
    @Override
    public CartProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_product_item, parent, false);
        return new CartProductAdapter.ViewHolder(view, listener, dbClass, baskets);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Baskets baskets1 = baskets.get(position);

        sharedPreferenceManager = SharedPreferenceManager.getInstance(context);
        int userId = sharedPreferenceManager.getUserId();

        if(baskets1.getUserId() == userId) {
            int productId = baskets1.getProductId();
            Products product = dbClass.getProductById(productId);

            holder.getProductNameTextView().setText(product.getProductName());
            holder.getProductPriceTextView().setText("Price: " + product.getProductPrice());
            holder.getProductImageView().setImageBitmap(product.getProductImage());

            int productQuantity = Integer.parseInt(baskets1.getBasketQuantity());
            holder.getQuantityTextView().setText(String.valueOf(productQuantity));
        } else {
            holder.itemView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return baskets.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView productNameTextView, productPriceTextView, quantityTextView;
        private ImageView productImageView;
        private Button subtractButton, addButton, deleteButton, checkOutButton;

        private DbClass dbClass1;
        private ArrayList<Baskets> baskets;
        Context context;

        public ViewHolder(View view, CartProductAdapter.OnItemClickListener listener, DbClass dbClass, ArrayList<Baskets> baskets) {
            super(view);

            this.dbClass1 = dbClass;
            this.baskets = baskets;

            productNameTextView = view.findViewById(R.id.productNameTextView);
            productPriceTextView = view.findViewById(R.id.productPriceTextView);
            quantityTextView = view.findViewById(R.id.quantityTextView);
            productImageView = view.findViewById(R.id.productImageView);
            subtractButton = view.findViewById(R.id.subtractButton);
            addButton = view.findViewById(R.id.addButton);
            deleteButton = view.findViewById(R.id.deleteButton);
            checkOutButton = view.findViewById(R.id.checkOutButton);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDeleteClick(getAdapterPosition());
                }
            });

            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Baskets baskets1 = baskets.get(position);

                        // Getting the products price from the product table
                        String productId = String.valueOf(baskets1.getProductId());
                        Products products = dbClass1.getProductById(Integer.parseInt(productId));
                        String productPriceString = products.getProductPrice();
                        String productPriceWithoutCurrency = productPriceString.replace("£", "");
                        double productPrice = Double.parseDouble(productPriceWithoutCurrency);

                        // Updating the quantity of the product in the basket
                        int newBasketQuantity = Integer.parseInt(baskets1.getBasketQuantity()) + 1;
                        baskets1.setBasketQuantity(String.valueOf(newBasketQuantity));
                        dbClass1.updateBasketQuantity(baskets1);

                        // Updating the price of the product in the basket
                        double newPrice = productPrice * newBasketQuantity;
                        baskets1.setBasketPrice(String.valueOf(newPrice));
                        dbClass1.updateBasketPrice(baskets1);

//                        Update the quantityTextView
                        quantityTextView.setText(String.valueOf(newBasketQuantity));

                        if (context instanceof CartActivity) {
                            CartActivity cartActivity = (CartActivity) context;
                            cartActivity.updateTotalPrice();
                        }
                    }
                }
            });

            subtractButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Baskets baskets1 = baskets.get(position);

                        // Getting the products price from the product table
                        String productId = String.valueOf(baskets1.getProductId());
                        Products products = dbClass1.getProductById(Integer.parseInt(productId));
                        String productPriceString = products.getProductPrice();
                        String productPriceWithoutCurrency = productPriceString.replace("£", "");
                        double productPrice = Double.parseDouble(productPriceWithoutCurrency);

                        // Updating the quantity of the product in the basket
                        int newBasketQuantity = Integer.parseInt(baskets1.getBasketQuantity()) - 1;
                        baskets1.setBasketQuantity(String.valueOf(newBasketQuantity));
                        dbClass1.updateBasketQuantity(baskets1);

                        // Updating the price of the product in the basket
                        double newPrice = productPrice * newBasketQuantity;
                        baskets1.setBasketPrice(String.valueOf(newPrice));
                        dbClass1.updateBasketPrice(baskets1);

                        // Update the quantityTextView
                        quantityTextView.setText(String.valueOf(newBasketQuantity));

                        if (context instanceof CartActivity) {
                            CartActivity cartActivity = (CartActivity) context;
                            cartActivity.updateTotalPrice();
                        }
                    }
                }
            });
        }
        public TextView getProductNameTextView() {
            return productNameTextView;
        }
        public ImageView getProductImageView() {
            return productImageView;
        }
        public TextView getProductPriceTextView(){return productPriceTextView; }
        public TextView getQuantityTextView() {
            return quantityTextView;
        }

        private double calculateTotalPrice(ArrayList<Baskets> baskets) {
            double totalPrice = 0.0;

            for(Baskets baskets1: baskets) {
                if(baskets1.getUserId() == getCurrentId()) {
                    int productId = baskets1.getProductId();
                    Products products = dbClass1.getProductById(productId);

                    String priceString = products.getProductPrice().replaceAll("[^\\d.]", "");
                    double productPrice = Double.parseDouble(priceString);

                    totalPrice += productPrice;
                }
            }
            return totalPrice;
        }

        private int getCurrentId() {
            SharedPreferenceManager sharedPreferenceManager = SharedPreferenceManager.getInstance(context);
            int userId = sharedPreferenceManager.getUserId();

            try {
                return userId;
            } catch(NumberFormatException e) {
                e.printStackTrace();
            }

            return -1;
        }
    }
}
