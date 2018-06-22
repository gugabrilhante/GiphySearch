package br.brilhante.gustavo.giphysearch.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.brilhante.gustavo.giphysearch.R;
import br.brilhante.gustavo.giphysearch.activity.MainActivity;
import br.brilhante.gustavo.giphysearch.adapter.GiffAdapterListener;
import br.brilhante.gustavo.giphysearch.adapter.SearchAdapterListener;
import br.brilhante.gustavo.giphysearch.component.GustavoRecyclerView;
import br.brilhante.gustavo.giphysearch.component.GustavoSearchFloatingBar;
import br.brilhante.gustavo.giphysearch.component.dialog.GustavoMessageDialog;
import br.brilhante.gustavo.giphysearch.database.SearchDataBaseManager;
import br.brilhante.gustavo.giphysearch.database.UrlDataBaseManager;
import br.brilhante.gustavo.giphysearch.model.Datum;
import br.brilhante.gustavo.giphysearch.model.SearchRealm;
import br.brilhante.gustavo.giphysearch.model.data;
import br.brilhante.gustavo.giphysearch.rest.GiffRestClient;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchFragment extends Fragment implements GiffAdapterListener, SearchAdapterListener, GustavoRecyclerView.RefreshListener{

    int currentOffset = 0;
    int limit = 10;
    String word = "";

    GustavoRecyclerView gustavoRecyclerView, suggestTextRecyclerView;

    GustavoSearchFloatingBar searchFloatingBar;

    View view;

    SearchFragmentListener mListener;

    UrlDataBaseManager urlManager;
    SearchDataBaseManager searchManager;


    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
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
        view = inflater.inflate(R.layout.fragment_search, container, false);
        gustavoRecyclerView = view.findViewById(R.id.gustavoRecyclerView);
        suggestTextRecyclerView = view.findViewById(R.id.suggestTextRecyclerView);
        searchFloatingBar = view.findViewById(R.id.searchBar);
        setCallBacks();

        urlManager = new UrlDataBaseManager();

        searchManager = new SearchDataBaseManager();

        return view;
    }

    void setCallBacks(){
        searchFloatingBar.setSearchButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeSearch();
            }
        });
        searchFloatingBar.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    suggestTextRecyclerView.setVisibility(View.VISIBLE);
                }
                else{
                    suggestTextRecyclerView.setVisibility(View.GONE);
                }
                suggestTextRecyclerView.setTextRecyclerView(getContext(), searchManager.getFilteredList(s.toString()), SearchFragment.this );
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        gustavoRecyclerView.setListener(this);
    }

    void makeSearch(){
        word = searchFloatingBar.getText();
        currentOffset = 0;
        searchManager.saveSearchTerm(word);
        searchManager.updatedList();
        suggestTextRecyclerView.setVisibility(View.GONE);
        makeRequest(word);
        MainActivity.hideSoftKeyboard(getActivity());
    }

    void makeRequest(String search){
        GiffRestClient.build().getSearchGiffsObservable(search,limit, currentOffset)
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
                                gustavoRecyclerView.setRecyclerView(view.getContext(), datum.getData(), SearchFragment.this);
                                urlManager.storeData(datum.getData());
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
                        suggestTextRecyclerView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onComplete() {
                        gustavoRecyclerView.stopRefreshing();
                        suggestTextRecyclerView.setVisibility(View.GONE);
                    }
                });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SearchFragmentListener) {
            mListener = (SearchFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onGiffClicked(data data) {
        mListener.onSearchGiffClicked(data);
    }

    @Override
    public void onRefresh() {
        currentOffset = 0;
        if(!gustavoRecyclerView.isRefreshing())makeRequest(word);

    }

    @Override
    public void onReachBottom() {
        currentOffset += limit;
        if(!gustavoRecyclerView.isRefreshing())makeRequest(word);
    }

    @Override
    public void onSearchClicked(SearchRealm searchRealm) {
        searchFloatingBar.setText(searchRealm.getSearchTerm());
        suggestTextRecyclerView.setVisibility(View.GONE);
        makeSearch();
    }


    public interface SearchFragmentListener {
        void onSearchGiffClicked(data data);
    }
}
