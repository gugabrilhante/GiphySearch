package br.brilhante.gustavo.giphysearch.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.brilhante.gustavo.giphysearch.R;
import br.brilhante.gustavo.giphysearch.adapter.GiffAdapterListener;
import br.brilhante.gustavo.giphysearch.component.GustavoRecyclerView;
import br.brilhante.gustavo.giphysearch.component.dialog.GustavoMessageDialog;
import br.brilhante.gustavo.giphysearch.model.Datum;
import br.brilhante.gustavo.giphysearch.model.data;
import br.brilhante.gustavo.giphysearch.rest.GiffRestClient;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TrendingFragment extends Fragment implements GiffAdapterListener, GustavoRecyclerView.RefreshListener{

    int currentOffset = 0;
    int limit = 10;

    GustavoRecyclerView gustavoRecyclerView;

    Unbinder unbinder;

    TrendingFragmentListener mListener;

    View view;

    public static TrendingFragment newInstance() {
        TrendingFragment fragment = new TrendingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_trending, container, false);
//        unbinder = ButterKnife.bind(TrendingFragment.this, view);
        gustavoRecyclerView = view.findViewById(R.id.gustavoRecyclerView);
        setCallbacks();
        makeRequest();
        return view;
    }

    private void setCallbacks(){
        gustavoRecyclerView.setListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof TrendingFragmentListener) {
            mListener = (TrendingFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    void makeRequest(){
        GiffRestClient.build().getTrendingGiffsObservable(limit,currentOffset,"g")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Datum>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        gustavoRecyclerView.startRefreshing();
                    }

                    @Override
                    public void onNext(Datum datum) {
                        if(datum.getData().isEmpty()){
                            GustavoMessageDialog
                                    .build(view.getContext())
                                    .simple()
                                    .message("No result found")
                                    .inflateDialog();
                        }else {
                            if (currentOffset == 0) {
                                gustavoRecyclerView.setRecyclerView(view.getContext(), datum.getData(), TrendingFragment.this);
                            } else {
                                gustavoRecyclerView.addData(datum.getData());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        gustavoRecyclerView.stopRefreshing();
                        GustavoMessageDialog
                                .build(view.getContext())
                                .simple()
                                .message(e.getMessage())
                                .inflateDialog();
                    }

                    @Override
                    public void onComplete() {
                        gustavoRecyclerView.stopRefreshing();
                    }
                });
    }

    @Override
    public void onGiffClicked(data data) {
        mListener.onTrendingGiffClicked(data);
    }

    @Override
    public void onRefresh() {
        currentOffset = 0;
        if(!gustavoRecyclerView.isRefreshing())makeRequest();

    }

    @Override
    public void onReachBottom() {
        currentOffset += limit;
        if(!gustavoRecyclerView.isRefreshing())makeRequest();
    }

    public interface TrendingFragmentListener {
        void onTrendingGiffClicked(data data);
    }
}
