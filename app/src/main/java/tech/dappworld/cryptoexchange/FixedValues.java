package tech.dappworld.cryptoexchange;

/**
 * Created by OBINNA on 10/21/2017.
 */

//DECLARING FIXED VALUES FOR CURRENCY AND COINS
public interface FixedValues {
    String EXTRA_CURRENCY = "extra currency";
    String EXTRA_BTC_RATE = "extra btc";
    String EXTRA_ETH_RATE = "extra eth";

    int ORDER_ALPHABETICAL = 1;
    int ORDER_BY_RATE = 2;

    String INVALID_CONVERSION = "Please insert valid values";
    String REFRESH_ERROR = "Refresh failed";
}
