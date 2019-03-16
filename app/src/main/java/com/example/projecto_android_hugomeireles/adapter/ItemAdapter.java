package com.example.projecto_android_hugomeireles.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projecto_android_hugomeireles.R;
import com.example.projecto_android_hugomeireles.interfaceBook.AdapterCallback;
import com.example.projecto_android_hugomeireles.model.Favorito;
import com.example.projecto_android_hugomeireles.model.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemAdapterViewHolder> {
    private List<Item> items;
    private List<Favorito> favoritos;
    private Context context;
    private AdapterCallback adapterCallback;

    public ItemAdapter(List<Item> items, List<Favorito> favoritos, Context context, AdapterCallback adapterCallback) {
        this.items = items;
        this.favoritos = favoritos;
        this.context = context;
        this.adapterCallback = adapterCallback;
    }


    @Override
    public ItemAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list,viewGroup,false);
        ItemAdapterViewHolder itemAdapterViewHolder = new ItemAdapterViewHolder(view);
        return itemAdapterViewHolder;
    }

    @Override
    public void onBindViewHolder( ItemAdapterViewHolder itemAdapterViewHolder, final int i) {
        if(items != null){
            Item item = items.get(i);
            itemAdapterViewHolder.tvNome.setText(item.getVolumeInfo().getTitle());
            itemAdapterViewHolder.tvData.setText(item.getVolumeInfo().getPublishedDate());
            if(item.getVolumeInfo().getImageLinks()!=null){
                Picasso.get()
                        .load(item.getVolumeInfo().getImageLinks().getSmallThumbnail())
                        .into(itemAdapterViewHolder.ivItem);
            }
            itemAdapterViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapterCallback.selectItemOnclick(items.get(i).getId());
                }
            });
        }else {
            final Favorito favorito = favoritos.get(i);
            itemAdapterViewHolder.tvNome.setText(favorito.getTvNome());
            itemAdapterViewHolder.tvData.setText(favorito.getTvData());
            if(favorito.getIvImage()!=null){
                Picasso.get()
                        .load(favorito.getIvImage())
                        .into(itemAdapterViewHolder.ivItem);
            }
            itemAdapterViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapterCallback.selectItemOnclick(favorito.getId());
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if(items==null){
            return favoritos.size();
        }else{
            return items.size();
        }

    }


    public static class ItemAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNome;
        private TextView tvData;
        private ImageView ivItem;


        public ItemAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNome  = (TextView) itemView.findViewById(R.id.tv_nome);
            ivItem = (ImageView) itemView.findViewById(R.id.iv_item);
            tvData = (TextView) itemView.findViewById(R.id.tv_data);
        }
    }
}
