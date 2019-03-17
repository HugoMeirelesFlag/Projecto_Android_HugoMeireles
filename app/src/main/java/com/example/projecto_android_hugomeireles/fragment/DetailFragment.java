package com.example.projecto_android_hugomeireles.fragment;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projecto_android_hugomeireles.R;
import com.example.projecto_android_hugomeireles.interfaceBook.GetItemsService;
import com.example.projecto_android_hugomeireles.model.Favorito;
import com.example.projecto_android_hugomeireles.model.Item;
import com.squareup.picasso.Picasso;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailFragment extends Fragment {
    private ImageView ivImage;
    private TextView tvNome;
    private TextView tvData;
    private TextView tvBuy;
    private Button btnAdcfavorito;

    public DetailFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        ivImage = (ImageView) view.findViewById(R.id.iv_image);
        tvNome   = (TextView) view.findViewById(R.id.tv_nome);
        tvData    = (TextView) view.findViewById(R.id.tv_data);
        tvBuy  = (TextView) view.findViewById(R.id.tv_buyLink);
        btnAdcfavorito = (Button) view.findViewById(R.id.btn_adcfavorito);
        Realm.init(getActivity());
        Bundle args = this.getArguments();


        getItemDetail(args.getString("ID"));
        return view;
    }
    public void prepareView(final Item item){
        tvNome.setText(item.getVolumeInfo().getTitle());
        tvData.setText(item.getVolumeInfo().getPublishedDate());
        if(item.getSaleInfo().getBuyLink()==null){
            tvBuy.setText("Nao existe Link!");
        }else{
            tvBuy.setText("Buy Book Here!");
        }
        if(item.getVolumeInfo().getImageLinks()!=null){
            Picasso.get().load(item.getVolumeInfo().getImageLinks().getThumbnail()).into(ivImage);
        }

        if(verificar(item) == true){
            btnAdcfavorito.setText("Adicionar");
        }else {
            btnAdcfavorito.setText("Retirar");
        }

        btnAdcfavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verificar(item)==true){
                    /*Favorito favorito = realm.createObject(Favorito.class);
                    favorito.setTvNome(item.getVolumeInfo().getTitle());
                    favorito.setTvData(item.getVolumeInfo().getPublishedDate());
                    favorito.setTvBuy(item.getSaleInfo().getBuyLink());
                    favorito.setId(item.getId());
                    if(item.getVolumeInfo().getImageLinks()!=null){
                        favorito.setIvImage(item.getVolumeInfo().getImageLinks().getSmallThumbnail());
                    }else {
                        favorito.setIvImage(null);
                    }
                    realm.commitTransaction();*/
                    adicionar(item);
                }else{
                    retirar(item);
                }

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(DetailFragment.this).attach(DetailFragment.this).commit();
            }
        });
        tvBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.getSaleInfo().getBuyLink()!=null){
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(item.getSaleInfo().getBuyLink()));
                    startActivity(i);
                }
            }
        });

    }

    private void adicionar(Item item){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Favorito favorito = realm.createObject(Favorito.class);
        favorito.setTvNome(item.getVolumeInfo().getTitle());
        favorito.setTvData(item.getVolumeInfo().getPublishedDate());
        favorito.setTvBuy(item.getSaleInfo().getBuyLink());
        favorito.setId(item.getId());
        if(item.getVolumeInfo().getImageLinks()!=null){
            favorito.setIvImage(item.getVolumeInfo().getImageLinks().getSmallThumbnail());
        }else {
            favorito.setIvImage(null);
        }
        realm.commitTransaction();
    }

    private void retirar(Item item){
        Realm realm =  Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<Favorito> user_2 = realm.where(Favorito.class).equalTo("id",item.getId()).findAll();
        user_2.deleteAllFromRealm();
        realm.commitTransaction();
    }

    private boolean verificar(Item item){
        Realm realm =  Realm.getDefaultInstance();
        RealmResults<Favorito> user_2 = realm.where(Favorito.class).equalTo("id",item.getId()).findAll();
        if(user_2.size()>0){
            return false;
        }
        return true;
    }

    private void getItemDetail(String id) {
        Retrofit retrofit = new Retrofit.
                Builder().
                baseUrl("https://www.googleapis.com/books/v1/").
                addConverterFactory(GsonConverterFactory.create()).build();

        GetItemsService service = retrofit.create(GetItemsService.class);

        Call<Item> ItemDetail = service.getItemDetail(String.valueOf(id));

        ItemDetail.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                Log.v("RETROFIT","OK");

                prepareView(response.body());
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                Log.v("RETROFIT","NOK");

            }
        });
    }



}
