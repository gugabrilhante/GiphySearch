package br.brilhante.gustavo.giphysearch.adapter.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.brilhante.gustavo.giphysearch.R;
import br.brilhante.gustavo.giphysearch.model.SearchRealm;

/**
 * Created by Gustavo on 22/06/18.
 */

public class TextHolder  extends RecyclerView.ViewHolder {

    TextView searchTextView, dateTextView;
    Context context;


    public TextHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        searchTextView = itemView.findViewById(R.id.searchTextView);
        dateTextView = itemView.findViewById(R.id.timeTextView);

    }

    public void bind(SearchRealm searchRealm) {
        searchTextView.setText(searchRealm.getSearchTerm());
        dateTextView.setText(searchRealm.getTime());

    }

    public static TextHolder build(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_search, parent, false);
        TextHolder holder = new TextHolder(view, parent.getContext());
        return holder;
    }
}
