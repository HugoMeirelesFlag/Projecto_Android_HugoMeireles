package com.example.projecto_android_hugomeireles.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projecto_android_hugomeireles.R;
import com.example.projecto_android_hugomeireles.adapter.ItemAdapter;
import com.example.projecto_android_hugomeireles.interfaceBook.AdapterCallback;
import com.example.projecto_android_hugomeireles.model.Favorito;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class FavoritoListFragment extends Fragment implements AdapterCallback {
    private RecyclerView favoritoRecyclerview;
    private ItemAdapter itemAdapter;
    private RecyclerView.LayoutManager layoutManager;
    GetFavoritoCallback getFavoritoCallback;

    public void connect(Activity activity){
        getFavoritoCallback = (GetFavoritoCallback) activity;
    }

    public FavoritoListFragment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorito_list, container, false);
        favoritoRecyclerview = (RecyclerView) view.findViewById(R.id.my_recycler2);
        favoritoRecyclerview.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        favoritoRecyclerview.setLayoutManager(layoutManager);
        Realm.init(getActivity());
        getFavoritos();
        return view;
    }

    private void getFavoritos(){
        Realm realm =  Realm.getDefaultInstance();
        RealmQuery<Favorito> query = realm.where(Favorito.class);
        RealmResults<Favorito> favoritos = query.findAll();
        itemAdapter = new ItemAdapter(null,favoritos,getActivity(), FavoritoListFragment.this);
        favoritoRecyclerview.setAdapter(itemAdapter);

    }

    public interface GetFavoritoCallback {
        void getItem(String id);
    }

    @Override
    public void selectItemOnclick(String posicao) {
        getFavoritoCallback.getItem(posicao);
    }
}
