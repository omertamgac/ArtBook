package com.example.artbook;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.artbook.RoomDB.ArtDAO;
import com.example.artbook.RoomDB.ArtDatabase;
import com.example.artbook.RoomDB.ArtEntity;
import com.example.artbook.databinding.FragmentFirstBinding;
import com.example.artbook.databinding.FragmentSecondBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class FragmentFirst extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentFirst() {
        // Required empty public constructor
    }

    private FragmentFirstBinding bindingFirst;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    Adapter adapter;
    ArrayList<ArtEntity> artArrayList;
    ArtDAO artDAO;
    ArtDatabase db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       bindingFirst = FragmentFirstBinding.inflate(inflater, container, false);
        View view = bindingFirst.getRoot();
        artArrayList = new ArrayList<>();
        return view;
    }


    public static FragmentFirst newInstance(String param1, String param2) {
        FragmentFirst fragment = new FragmentFirst();
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
        db = Room.databaseBuilder(getContext(),ArtDatabase.class,"ArtDatabase").allowMainThreadQueries().build();
        artDAO = db.artDAO();

        compositeDisposable.add(artDAO.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(FragmentFirst.this::handleResponse)

        );
    }

    private void handleResponse(List<ArtEntity> artEntities) {
        bindingFirst.RecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        Adapter adapter1 = new Adapter(new ArrayList<>(artEntities));
        bindingFirst.RecyclerView.setAdapter(adapter1);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}