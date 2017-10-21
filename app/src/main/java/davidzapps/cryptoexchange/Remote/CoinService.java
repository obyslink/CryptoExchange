package davidzapps.cryptoexchange.Remote;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import davidzapps.cryptoexchange.Model.Coin;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by OBINNA on 10/18/2017.
 */

public interface CoinService {

    @GET("data/price")
    Call<Coin> calculateValue (@Query("fsym") String from, @Query("tsyms") String to);
}

