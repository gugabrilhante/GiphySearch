package br.brilhante.gustavo.giphysearch.adapter.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import br.brilhante.gustavo.giphysearch.R;
import br.brilhante.gustavo.giphysearch.model.data;

/**
 * Created by Gustavo on 20/06/18.
 */

public class GiffHolder extends RecyclerView.ViewHolder  {

    TextView tituloTextView, dataTextView, descricaoTextView;

    ImageView listIcon;

    Context context;

    public GiffHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        tituloTextView    = (TextView) itemView.findViewById(R.id.tituloTextView);
        dataTextView      = (TextView) itemView.findViewById(R.id.dataTextView);
        descricaoTextView = (TextView) itemView.findViewById(R.id.descricaoTextView);

        listIcon          = (ImageView) itemView.findViewById(R.id.listIcon);
    }

    public void bind(data data){

        tituloTextView.setText(data.getTitle());
        descricaoTextView.setText(data.getType());
        dataTextView.setText(data.getImportDatetime());

        Glide.with(context)
                .asGif()
                .load(data.getImages().getDownsizedStill().getUrl())
                .into(listIcon);

    }

    public static GiffHolder build(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_menu, parent, false);
        GiffHolder holder = new GiffHolder(view, parent.getContext());
        return holder;
    }
}
