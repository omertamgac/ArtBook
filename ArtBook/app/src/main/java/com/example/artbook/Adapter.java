package com.example.artbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.AbstractListDetailFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.artbook.RoomDB.ArtEntity;
import com.example.artbook.databinding.ListDetailBinding;
import com.example.artbook.databinding.RecyclerlistBinding;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    ArrayList<ArtEntity>ArrayArt;
    public Adapter(ArrayList<ArtEntity> arrayArt) {
        ArrayArt = arrayArt;
    }


    @NonNull
    @Override //Burada sıralanacak olan görüntüyü bağlıyoruz yani recyclerlist xml dosyasını eşleştirdim.
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       RecyclerlistBinding recyclerlistBinding = RecyclerlistBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
       return new ViewHolder(recyclerlistBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.binding.textViewItem.setText(ArrayArt.get(position).name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = ((AppCompatActivity) holder.itemView.getContext()).getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                recycler_Detail fragment = new recycler_Detail();
                Bundle bundle = new Bundle();
                bundle.putSerializable("Art", ArrayArt.get(holder.getAdapterPosition()));
                fragment.setArguments(bundle);
                transaction.replace(R.id.fragmentContainerView, fragment);
               transaction.addToBackStack(null); // Geri dönüşü desteklemek için
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return ArrayArt.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        //Burada görüntülenecek olan itemları eşleştiriyorum
        private RecyclerlistBinding binding;

        public ViewHolder(RecyclerlistBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }

}
