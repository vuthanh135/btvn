package com.example.day09reson;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class IteamAddapter extends RecyclerView.Adapter<IteamAddapter.ViewHoder> {

    List<Product> products = new ArrayList<>();

    public IteamAddapter(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.iteam,parent,false);
        ViewHoder viewHoder = new ViewHoder(view);

        return viewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        final Product product = products.get(position);
        holder.tvId.setText(product.getId());
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText(product.getPrice());

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        TextView tvId,tvName,tvPrice;


        public ViewHoder(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tvId);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}
