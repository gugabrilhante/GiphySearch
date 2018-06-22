package br.brilhante.gustavo.giphysearch.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.brilhante.gustavo.giphysearch.adapter.holder.DataRealmHolder;

/**
 * Created by Gustavo on 22/06/18.
 */

public class DataRealmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private List<String> dataList;

    DataRealmListener listener;

    public DataRealmAdapter(List<String> dataList) {
        this.dataList = dataList;
    }

    public void addData(List<String> dataList){
        this.dataList.addAll(dataList);
        this.notifyDataSetChanged();
    }

    public void setListener(DataRealmListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return DataRealmHolder.build(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {
        DataRealmHolder dataRealmHolder = (DataRealmHolder) holder;
        dataRealmHolder.bind(dataList.get(position));
        dataRealmHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onDataRealmClicked(dataList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


}
