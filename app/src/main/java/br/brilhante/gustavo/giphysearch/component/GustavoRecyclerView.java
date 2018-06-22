package br.brilhante.gustavo.giphysearch.component;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import java.util.List;

import br.brilhante.gustavo.giphysearch.R;
import br.brilhante.gustavo.giphysearch.adapter.DataRealmAdapter;
import br.brilhante.gustavo.giphysearch.adapter.DataRealmListener;
import br.brilhante.gustavo.giphysearch.adapter.GiffAdapter;
import br.brilhante.gustavo.giphysearch.adapter.GiffAdapterListener;
import br.brilhante.gustavo.giphysearch.adapter.SearchAdapter;
import br.brilhante.gustavo.giphysearch.adapter.SearchAdapterListener;
import br.brilhante.gustavo.giphysearch.model.SearchRealm;
import br.brilhante.gustavo.giphysearch.model.data;

/**
 * Created by Gustavo on 21/06/18.
 */

public class GustavoRecyclerView extends RelativeLayout {

    RecyclerView recyclerView;

    SwipeRefreshLayout swipeRefreshLayout;

    RefreshListener listener;

    GiffAdapter giffAdapter;

    DataRealmAdapter dataRealmAdapter;

    SearchAdapter searchAdapter;

    LayoutInflater inflater = null;

    public GustavoRecyclerView(Context context) {
        super(context);
        initViews();
    }

    public GustavoRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public GustavoRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    void initViews() {

        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.gustavo_recycler_view, this, true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        setCallbacks();
    }

    public boolean isRefreshing(){
        return swipeRefreshLayout.isRefreshing();
    }

    public void stopRefreshing(){
        swipeRefreshLayout.setRefreshing(false);
    }

    public void startRefreshing(){
        swipeRefreshLayout.setRefreshing(true);
    }

    void setCallbacks(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(listener!=null){
                    listener.onRefresh();
                }
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    if(listener!=null){
                        listener.onReachBottom();
                    }
                }
            }
        });
    }

    public void setRecyclerView(Context context, List<data> dataList, GiffAdapterListener listener){
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        giffAdapter = new GiffAdapter(dataList);
        giffAdapter.setListener(listener);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(giffAdapter);

    }

    public void setGridRecyclerView(Context context, List<String> dataList, DataRealmListener listener){
        GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
        dataRealmAdapter = new DataRealmAdapter(dataList);
        dataRealmAdapter.setListener(listener);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(dataRealmAdapter);

    }


    public void setTextRecyclerView(Context context, List<SearchRealm> dataList, SearchAdapterListener listener){
        disableRefresh();
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        searchAdapter = new SearchAdapter(dataList);
        searchAdapter.setListener(listener);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(searchAdapter);

    }

    public void addData(List<data> dataList){
        this.giffAdapter.addData(dataList);
        recyclerView.smoothScrollBy(0, 200);
    }

    public void addDataRealm(List<String> dataList){
        if(dataList==null)return;
        this.dataRealmAdapter.addData(dataList);
        recyclerView.smoothScrollBy(0, 200);
    }

    public void disableRefresh(){
        swipeRefreshLayout.setEnabled(false);
    }

    public void setListener(RefreshListener listener) {
        this.listener = listener;
    }

    public interface RefreshListener {
        void onRefresh();
        void onReachBottom();
    }


}
