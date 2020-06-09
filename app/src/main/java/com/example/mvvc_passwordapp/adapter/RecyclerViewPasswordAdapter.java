package com.example.mvvc_passwordapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvc_passwordapp.R;
import com.example.mvvc_passwordapp.mvvm.entity.PasswordEntity;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewPasswordAdapter extends RecyclerView.Adapter<RecyclerViewPasswordAdapter.PasswordHolder> {

    List<PasswordEntity> passwords = new ArrayList<>();
    OnItemClickListener listener;

    @NonNull
    @Override
    public PasswordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.password_item, parent, false);

        return new PasswordHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PasswordHolder holder, int position) {

        PasswordEntity password = passwords.get(position);

        holder.tvPassword.setText(password.getPassword());
        holder.tvUsername.setText(password.getUsername());
        holder.tvWebsite.setText(password.getWebsite());

    }

    @Override
    public int getItemCount() {
        return passwords.size();
    }

    public void setPasswords(List<PasswordEntity> passwords) {
        this.passwords = passwords;
        notifyDataSetChanged();
    }

    public PasswordEntity getPasswordAt(int position) {
        return passwords.get(position);
    }

    class PasswordHolder extends RecyclerView.ViewHolder {

        private TextView tvPassword;
        private TextView tvWebsite;
        private TextView tvUsername;


        public PasswordHolder(@NonNull View itemView) {
            super(itemView);
            tvPassword = itemView.findViewById(R.id.tvPassword);
            tvWebsite = itemView.findViewById(R.id.tvWebsite);
            tvUsername = itemView.findViewById(R.id.tvUsername);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if ((position == RecyclerView.NO_POSITION) || (listener == null)) return;

                    listener.onItemClick(passwords.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(PasswordEntity passwordEntity);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


}
