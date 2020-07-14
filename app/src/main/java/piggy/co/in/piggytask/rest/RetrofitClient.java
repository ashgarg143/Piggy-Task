package piggy.co.in.piggytask.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL ="https://api.piggy.co.in/";

    private Retrofit retrofit;

    private static RetrofitClient retrofitClient;

    public static synchronized RetrofitClient getRetrofitClient() {
        if (retrofitClient==null){
            retrofitClient = new RetrofitClient();
        }
        return retrofitClient;
    }

    private RetrofitClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public APIInterface connectUser(){
        return retrofit.create(APIInterface.class);
    }
}
