package com.pedrycz.phonedb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pedrycz.phonedb.entities.Phone;

import java.util.List;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.PhonesViewHolder> {

    private List<Phone> phones;
    private LayoutInflater layoutInflater;
    private static onItemClickListener onItemClickListener;

    public PhoneAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        this.phones = null;
    }

    public void setOnItemClickListener(PhoneAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public PhonesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.phone_row, parent, false);
        return new PhonesViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull PhonesViewHolder holder, int position) {
        holder.getBrand().setText(phones.get(position).getBrand());
        holder.getModel().setText(phones.get(position).getModel());
        holder.getVersion().setText(phones.get(position).getAndroid());
    }

    @Override
    public int getItemCount() {

        if (phones != null) return phones.size();
        return 0;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
        notifyDataSetChanged();
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public interface onItemClickListener {
        void onItemClickListener(int position);
    }

    class PhonesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView brand;
        private TextView model;
        private TextView version;

        public PhonesViewHolder(@NonNull View view) {
            super(view);
            brand = view.findViewById(R.id.brand);
            model = view.findViewById(R.id.model);
            version = view.findViewById(R.id.version);
            view.setOnClickListener((view1) -> {
                        com.pedrycz.phonedb.PhoneAdapter.onItemClickListener.onItemClickListener(getAdapterPosition());
                    }
            );
        }

        public TextView getBrand() {
            return brand;
        }

        public void setBrand(TextView brand) {
            this.brand = brand;
        }

        public TextView getModel() {
            return model;
        }

        public void setModel(TextView model) {
            this.model = model;
        }

        public TextView getVersion() {
            return version;
        }

        public void setVersion(TextView version) {
            this.version = version;
        }

        @Override
        public void onClick(View view) {

        }
    }
}

