package br.brilhante.gustavo.giphysearch.rest;

import br.brilhante.gustavo.giphysearch.model.Datum;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Gustavo on 19/06/18.
 */

public class GiffRestClient {

    static String API_KEY = "T5Cu3ot3A47CH5p78jIBh59eQIanytHs";
    static String FORMAT = "json";
    static String LANG = "en";
    static String RATING = "G";

    private GiffRestClienteApi giffClientApi;

    public GiffRestClient(){
        this.giffClientApi = new RestClient().createRestClient(GiffRestClienteApi.class);
    }

    public static GiffRestClient build(){
        return new GiffRestClient();
    }

    public Observable<Datum> getTrendingGiffsObservable(int limit, int offset, String rating){
//        return this.giffClientApi.getTrendingGiffs(API_KEY, limit, offset, rating, FORMAT);
        return this.giffClientApi.getSimpleTrendingGiffs(API_KEY, limit);
    }

    public Observable<Datum> getSearchGiffsObservable(String q, int limit, int offset){
        return this.giffClientApi.getSearchGiffs(API_KEY, q, limit, offset, RATING, LANG);
    }

    public interface GiffRestClienteApi{
        @GET("/v1/gifs/trending")
        Observable<Datum> getTrendingGiffs(@Query("api_key") String key,
                                           @Query("limit") int limit, @Query("offset") int offset,
                                           @Query("rating") String rating, @Query("fmt") String format);

        @GET("/v1/gifs/trending")
        Observable<Datum> getSimpleTrendingGiffs(@Query("api_key") String key,
                                           @Query("limit") int limit);


        @GET("/v1/gifs/search")
        Observable<Datum> getSearchGiffs(@Query("api_key") String key, @Query("q") String q,
                                           @Query("limit") int limit, @Query("offset") int offset,
                                           @Query("rating") String rating, @Query("lang") String lang);
    }
}
