package com.example.artbook;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.artbook.RoomDB.ArtEntity;
import com.example.artbook.databinding.FragmentRecyclerDetailBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link recycler_Detail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class recycler_Detail extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private FragmentRecyclerDetailBinding binding;

    ArtEntity entity;

    public recycler_Detail() {
        // Required empty public constructor
    }

    public static recycler_Detail newInstance(String param1, String param2) {
        return new recycler_Detail();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            entity = (ArtEntity) getArguments().getSerializable("Art");
        }
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRecyclerDetailBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        if (entity != null) {
            binding.DetailArtName.setText(entity.artName);
            binding.DetailName.setText(entity.name);
            binding.DetailArtYear.setText(entity.year);
            Uri imageUri = Uri.parse(entity.image.toString());
            binding.DetailimageView.setImageURI(imageUri);

        }

        Button button=view.findViewById(R.id.btnBack);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment FirstFragment = new FragmentFirst();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainerView, FirstFragment);
                transaction.commit();
            }
        });

        return view;
    }


}
