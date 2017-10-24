package davidzapps.cryptoexchange.Model;

/**
 * Created by OBINNA on 10/21/2017.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import davidzapps.cryptoexchange.FixedValues;

public class GetRates {

    public ArrayList<MoneyRate> ratesArrayList = new ArrayList<>();

    public GetRates() {}

    //Populate the ratesArrayList
    public void add(String currency, Double btcRate, Double ethRate) {
        MoneyRate moneyRate = new MoneyRate(currency, btcRate, ethRate);
        ratesArrayList.add(moneyRate);
    }

    //Arrange the ratesArrayList alphabetically OR by the currency values
    public void orderList(final int mode) {
        Collections.sort(ratesArrayList, new Comparator<MoneyRate>() {
            @Override
            public int compare(MoneyRate lhs, MoneyRate rhs) {
                if(mode == FixedValues.ORDER_ALPHABETICAL)return lhs.getCurrency().compareTo(rhs.getCurrency());
                else return Double.compare(lhs.getBtcRate(), rhs.getBtcRate());
            }
        });
    }

    //Custom class carries name of currency and its BTC and ETH rates and methods to return these properties
     public class MoneyRate{
        private String currency;
        private double btcRate, ethRate;

        MoneyRate(String currency, double btcRate, double ethRate) {
            this.currency = currency;
            this.btcRate = btcRate;
            this.ethRate = ethRate;
        }

        public String getCurrency() {
            return currency;
        }

        public double getBtcRate() {
            return btcRate;
        }

        public double getEthRate() {
            return ethRate;
        }
    }

}
