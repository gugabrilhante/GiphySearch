package br.brilhante.gustavo.giphysearch.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.brilhante.gustavo.giphysearch.adapter.holder.TextHolder;
import br.brilhante.gustavo.giphysearch.model.SearchRealm;

/**
 * Created by Gustavo on 22/06/18.
 */

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<SearchRealm> dataList;

    SearchAdapterListener listener;

    public SearchAdapter(List<SearchRealm> dataList) {
        this.dataList = dataList;
    }

    public void setData(List<SearchRealm> dataList){
        this.dataList = dataList;
        this.notifyDataSetChanged();
    }

    public void setListener(SearchAdapterListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return TextHolder.build(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {
        TextHolder textHolder = (TextHolder) holder;
        textHolder.bind(dataList.get(position));
        textHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onSearchClicked(dataList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
