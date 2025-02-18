package com.example.projecto_android_hugomeireles.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.projecto_android_hugomeireles.R;
import com.example.projecto_android_hugomeireles.fragment.DetailFragment;
import com.example.projecto_android_hugomeireles.fragment.FavoritoListFragment;
import com.example.projecto_android_hugomeireles.fragment.ItemListFragment;
import com.example.projecto_android_hugomeireles.interfaceBook.AdapterCallback;

public class MainActivity extends AppCompatActivity implements ItemListFragment.GetItemCallback , FavoritoListFragment.GetFavoritoCallback {

    private Button btn_favorito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ItemListFragment itemListFragment = new ItemListFragment();

        itemListFragment.connect(MainActivity.this);

        fragmentTransaction.add(R.id.container, itemListFragment,"list");
        fragmentTransaction.commit();
    }

    @Override
    public void getItem(String id) {
        DetailFragment detailFragment = new DetailFragment();

        Bundle args = new Bundle();
        args.putString("ID",id);

        detailFragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.container,detailFragment,"DETAIL");
        fragmentTransaction.addToBackStack("list");
        fragmentTransaction.commit();
    }
}
