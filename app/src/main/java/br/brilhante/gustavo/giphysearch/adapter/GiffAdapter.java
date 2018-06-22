package br.brilhante.gustavo.giphysearch.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.brilhante.gustavo.giphysearch.adapter.holder.GiffHolder;
import br.brilhante.gustavo.giphysearch.model.data;

/**
 * Created by Gustavo on 20/06/18.
 */

public class GiffAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private List<data> dataList;

    GiffAdapterListener listener;

    public GiffAdapter(List<data> dataList) {
        this.dataList = dataList;
    }

    public void addData(List<data> dataList){
        this.dataList.addAll(dataList);
        this.notifyDataSetChanged();
    }

    public void setListener(GiffAdapterListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return GiffHolder.build(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {
        GiffHolder giffHolder = (GiffHolder) holder;
        giffHolder.bind(dataList.get(position));
        giffHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onGiffClicked(dataList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


}
