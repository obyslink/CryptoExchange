package davidzapps.cryptoexchange.Remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by OBINNA on 10/18/2017.
 */

public class RetrofitClient {
    private static Retrofit retrofit= null;
    public static Retrofit getClient(String baseURL){
        if(retrofit == null)
        {
            retrofit = new Retrofit.Builder ()
                    .baseUrl (baseURL)
                    .addConverterFactory (GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
