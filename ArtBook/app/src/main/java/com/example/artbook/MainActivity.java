package com.example.artbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.artbook.RoomDB.ArtDAO;
import com.example.artbook.RoomDB.ArtDatabase;
import com.example.artbook.RoomDB.ArtEntity;
import com.example.artbook.databinding.ActivityMainBinding;
import com.example.artbook.databinding.FragmentSecondBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    ArtDatabase db;
    ArtDAO artDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        db = Room.databaseBuilder(getApplicationContext(),ArtDatabase.class,"ArtDatabase").build();
        artDAO = db.artDAO();

    }


    @Override //Bu kod bloğunda Options Menu'yü bağladım
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.art_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Fragment secondFragment = new FragmentSecond();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView, secondFragment); // fragment_container, ikinci fragment'ın gösterileceği layout olmalıdır.
        transaction.addToBackStack(null); // Geri tuşu ile geri dönüşü etkinleştirir
        transaction.commit();
        return true;
    }




}



