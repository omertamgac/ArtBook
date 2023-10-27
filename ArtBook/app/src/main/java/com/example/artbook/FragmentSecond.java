package com.example.artbook;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.artbook.RoomDB.ArtDAO;
import com.example.artbook.RoomDB.ArtDatabase;
import com.example.artbook.RoomDB.ArtEntity;
import com.example.artbook.databinding.FragmentSecondBinding;

import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FragmentSecond extends Fragment {

    public FragmentSecondBinding bindingSecond;
    ArtDatabase db;
    ArtDAO artDAO;
    public static final int GALLERY_REQUEST_CODE = 1001;
    String selectedImage;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentSecond() {
        // Required empty public constructor
    }


    public static FragmentSecond newInstance(String param1, String param2) {
        FragmentSecond fragment = new FragmentSecond();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
        db = Room.databaseBuilder(requireContext(),ArtDatabase.class,"ArtDatabase").allowMainThreadQueries().build();
        artDAO = db.artDAO();
       
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bindingSecond = FragmentSecondBinding.inflate(inflater, container, false);
        View view = bindingSecond.getRoot();
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView imageView=bindingSecond.imageViewResim;
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Galeriye erişmek için bir intent oluşturulur.
                // ACTION_PICK, bir medya seçme işlemi yapmamıza izin veren bir işlem türüdür.
                // external content uri, galerideki resimlerin bulunduğu konumu temsil eder.
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                // startActivityForResult yöntemi, intent'i başlatarak galeriden bir resim seçmek için kullanılır.
                startActivityForResult(intent, GALLERY_REQUEST_CODE);
            }
        });

       Button button = view.findViewById(R.id.btnSave);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (bindingSecond != null) {
                    String name = bindingSecond.editTextName.getText().toString();
                    String artName = bindingSecond.editTextArtName.getText().toString();
                    String year = bindingSecond.editTextYear.getText().toString();

                    // ArtEntity sınıfının bir örneğini oluşturun ve veritabanına eklemek için kullanabilirsiniz
                    ArtEntity artEntity = new ArtEntity(name, artName, year, selectedImage);
                  //  artDAO.insert(artEntity);


                    compositeDisposable.add(artDAO.insert(artEntity)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(FragmentSecond.this::handleResponse));
                            
                    
                    // Veritabanına kaydetme işlemi burad1a yapılmalıdır (artDAO kullanılarak veya uygun şekilde)
                } else {
                    // Eğer ss null ise, bu hatayı ele alın
                    // Hatayı kullanıcıya bildirebilir veya başka bir işlem yapabilirsiniz
                }

            }
        });

    }

    private void handleResponse() {
        //İlk sayfaya dönüş işlemleri
        Fragment FirstFragment = new FragmentFirst();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView, FirstFragment);
        transaction.commit();
    }
    Uri selectedImageUri;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imageUri);
                // Resmi bir Bitmap olarak aldık.
                // Resmi veritabanına kaydetmeden önce imageUri'yi selectedImage değişkenine atayın.
                selectedImage = imageUri.toString();
                // Buradan ileriye işlem yapabilirsiniz.
                selectedImageUri = imageUri;
                ImageView imageView = bindingSecond.imageViewResim;
                imageView.setImageURI(imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

}