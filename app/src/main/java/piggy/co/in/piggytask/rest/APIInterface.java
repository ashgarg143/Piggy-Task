package piggy.co.in.piggytask.rest;


import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface APIInterface {


    @Headers({
            "Cache-Control: no-cache",
            "Authorization: Token a41d2b39e3b47412504509bb5a1b66498fb1f43a",
            "Content-Type: application/json"
    })
    @POST("v2/mf/search/")
    Call<ResponseBody> searchMutualFund(@Body JsonObject object);

    @Headers({
            "Cache-Control: no-cache",
            "Authorization: Token a41d2b39e3b47412504509bb5a1b66498fb1f43a",
            "Content-Type: application/json"
    })
    @GET("v1/mf/")
    Call<ResponseBody> getMutualFund(@Query("key") String key);


}
