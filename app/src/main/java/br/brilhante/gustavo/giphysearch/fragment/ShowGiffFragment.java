package br.brilhante.gustavo.giphysearch.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import br.brilhante.gustavo.giphysearch.R;
import br.brilhante.gustavo.giphysearch.adapter.DataRealmListener;
import br.brilhante.gustavo.giphysearch.component.GustavoRecyclerView;
import br.brilhante.gustavo.giphysearch.database.UrlDataBaseManager;

public class ShowGiffFragment extends Fragment implements DataRealmListener, GustavoRecyclerView.RefreshListener {
    private static final String GIFF_URL = "GIFF_URL";

    String url;
    View view;

    ImageView displayImageView;

    GustavoRecyclerView gustavoRecyclerView;

    UrlDataBaseManager manager;

    int currentOffset = 0;
    int limit = 10;


    public static ShowGiffFragment newInstance(String url) {
        ShowGiffFragment fragment = new ShowGiffFragment();
        Bundle args = new Bundle();
        args.putString(GIFF_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString(GIFF_URL);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_show_giff, container, false);

        displayImageView  = (ImageView) view.findViewById(R.id.displayImageView);

        gustavoRecyclerView = view.findViewById(R.id.gustavoRecyclerView);
        gustavoRecyclerView.disableRefresh();


        manager = new UrlDataBaseManager();

        downloadGiff();

        getFromDatabase();

        return view;
    }

    void getFromDatabase(){
        gustavoRecyclerView.setGridRecyclerView(getContext(), manager.getPartList(limit, currentOffset), this);
        gustavoRecyclerView.setListener(this);
    }


    private void downloadGiff(){

        Glide.with(ShowGiffFragment.this)
                .asGif()
                .load(url)
                .into(displayImageView);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDataRealmClicked(String url) {
        Glide.with(ShowGiffFragment.this)
                .asGif()
                .load(url)
                .into(displayImageView);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onReachBottom() {
        currentOffset += limit;
        gustavoRecyclerView.addDataRealm(manager.getPartList(limit, currentOffset));

    }
}
