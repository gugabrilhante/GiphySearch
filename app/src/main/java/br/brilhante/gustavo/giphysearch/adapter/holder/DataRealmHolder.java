package br.brilhante.gustavo.giphysearch.adapter.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import br.brilhante.gustavo.giphysearch.R;

/**
 * Created by Gustavo on 22/06/18.
 */

public class DataRealmHolder extends RecyclerView.ViewHolder  {

    ImageView listIcon;

    Context context;

    public DataRealmHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;

        listIcon          = (ImageView) itemView.findViewById(R.id.listIcon);
    }

    public void bind(String url){

        Glide.with(context)
                .asGif()
                .load(url)
                .into(listIcon);
        

    }

    public static DataRealmHolder build(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_data_realm, parent, false);
        DataRealmHolder holder = new DataRealmHolder(view, parent.getContext());
        return holder;
    }
}
