package com.example.molosecondassignment.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.molosecondassignment.Classes.Users;
import com.example.molosecondassignment.R;

import java.util.ArrayList;

public class AdminUserAdapter extends RecyclerView.Adapter<AdminUserAdapter.ViewHolder> {
    Context context;
    ArrayList<Users> users;
    private AdminUserAdapter.OnItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_users_item, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users users1 = users.get(position);

        holder.getUserIdTextView().setText(users1.getId());
        holder.getFullNameTextView().setText(users1.getFullName());
        holder.getEmailTextView().setText(users1.getEmail());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onEditItemClick(int position);
    }

    public void setOnItemClickListener(AdminUserAdapter.OnItemClickListener clickListener) {
        listener = clickListener;
    }

    public AdminUserAdapter(Context context, ArrayList<Users> users) {
        this.context = context;
        this.users = users;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView usersFullName, subTextView, userIdTextView;
        private Button editUserButton, deleteUserButton;

        public ViewHolder(View view, AdminUserAdapter.OnItemClickListener listener) {
            super(view);

            usersFullName = view.findViewById(R.id.usersFullName);
            subTextView = view.findViewById(R.id.subTextView);
            editUserButton = view.findViewById(R.id.editUserButton);
            deleteUserButton = view.findViewById(R.id.deleteUserButton);
            userIdTextView = view.findViewById(R.id.userIdTextView);

            deleteUserButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getAdapterPosition());
                }
            });

            editUserButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onEditItemClick(getAdapterPosition());
                }
            });
        }
        public TextView getFullNameTextView() {
            return usersFullName;
        }
        public TextView getEmailTextView() {
            return subTextView;
        }
        public TextView getUserIdTextView() {
            return userIdTextView;
        }
    }
}
