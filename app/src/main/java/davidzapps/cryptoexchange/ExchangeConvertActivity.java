package davidzapps.cryptoexchange;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class ExchangeConvertActivity extends Activity {

    String currencyCode;
    String currencyFullName;
    EditText btcValueEdit, ethValueEdit, flatValueEdit;
    ImageButton btcConvertButton, ethConvertButton, flatConvertButton, FCBtn;

    double btcRate;
    double ethRate;

    TextView moneyCodeView;
    TextView fullNameView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_convert);

        moneyCodeView = (TextView) findViewById(R.id.money_code_view);
        fullNameView = (TextView) findViewById(R.id.full_name_view);
        btcValueEdit = (EditText) findViewById(R.id.btc_value_edit);
        ethValueEdit = (EditText) findViewById(R.id.eth_value_edit);
        flatValueEdit = (EditText) findViewById(R.id.flat_value_edit);
        btcConvertButton = (ImageButton) findViewById(R.id.btc_convert_button);
        ethConvertButton = (ImageButton) findViewById(R.id.eth_convert_button);
        flatConvertButton = (ImageButton) findViewById(R.id.flat_convert_button);
        FCBtn = (ImageButton) findViewById(R.id.FCBtn);



        Intent intent = getIntent();
        currencyCode = intent.getStringExtra(FixedValues.EXTRA_CURRENCY);
        btcRate = intent.getDoubleExtra(FixedValues.EXTRA_BTC_RATE, 0);
        ethRate = intent.getDoubleExtra(FixedValues.EXTRA_ETH_RATE, 0);
        currencyFullName = getFullName(currencyCode);
        String conversionMessage = currencyFullName;

        moneyCodeView.setText(currencyCode);
        fullNameView.setText(conversionMessage);
        if(getActionBar() != null) getActionBar().setTitle(currencyFullName);

    }

    //Display currency names for each currency
    public String getFullName(String moneyCode) {
        switch (moneyCode) {

            case "AFN": return "Afghanistan Afghani Conversion Screen";
            case "DZD": return "Algerian Dinar Conversion Screen";
            case "NGN": return "Naira Conversion Screen";
            case "UGX": return "Ugandan Shilling Conversion Screen";
            case "ZAR": return "Rand Conversion Screen";
            case "XAF": return "CFA Franc BCEAO Conversion Screen";
            case "NZD": return "New Zealand Dollar Conversion Screen";
            case "MYR": return "Malaysian Ringgit Conversion Screen";
            case "BND": return "Brunei Dollar Conversion Screen";
            case "GEL": return "Georgian Lari Conversion Screen";
            case "EGP": return "Egyptian Pound Conversion Screen";
            case "RUB": return "Russian Ruble Conversion Screen";
            case "INR": return "Indian Rupee Conversion Screen";
            case "USD": return "US Dollar Conversion Screen";
            case "EUR": return "Euro Conversion Screen";
            case "JPY": return "Yen Conversion Screen";
            case "KES": return "Kenyan Shilling Conversion Screen";
            case "GHS": return "Cedi Conversion Screen";
            case "GBP": return "Pound Sterling Conversion Screen";
            case "AUD": return "Australian Dollar Conversion Screen";
            case "XOF": return "West African CFA Conversion Screen";
            case "CAD": return "Canadian Dollar Conversion Screen";
            case "CHF": return "Swiss Franc Conversion Screen";
            case "CNY": return "Yuan Conversion Screen";
            default: return "Currency Converter";

        }
    }

    @SuppressLint("DefaultLocale")
    //Conversion from one currency to the other two values and also calling of FAST CALCULATOR CLASS
    public void doConvert(View view) {
        //handle exceptions: if values is set and others are not empty, or just a dot (handle string in layout by using only numbers in text input
        if(view == btcConvertButton && btcValueEdit.getText().toString().trim().length() > 0 && !btcValueEdit.getText().toString().trim().equals(".")) {
            try {
                double btcAmount = Double.parseDouble(btcValueEdit.getText().toString());
                flatValueEdit.setText(String.format("%1$,.2f", (btcAmount * btcRate)));
                ethValueEdit.setText(String.format("%1$,.2f", (btcAmount * (ethRate / btcRate))));
            } catch (NumberFormatException e) {
                Snackbar.make(findViewById(R.id.main_scroll_view), FixedValues.INVALID_CONVERSION, Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }

        } else if(view == ethConvertButton && ethValueEdit.getText().toString().trim().length() > 0 && !ethValueEdit.getText().toString().trim().equals(".")){
            try {
                double ethAmount = Double.parseDouble(ethValueEdit.getText().toString());
                flatValueEdit.setText(String.format("%1$,.2f", (ethAmount * ethRate)));
                btcValueEdit.setText(String.format("%1$,.2f", (ethAmount * (btcRate / ethRate))));
            } catch (NumberFormatException e) {
                Snackbar.make(findViewById(R.id.main_scroll_view), FixedValues.INVALID_CONVERSION, Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }

        } else if(view == flatConvertButton && flatValueEdit.getText().toString().trim().length() > 0 && !flatValueEdit.getText().toString().trim().equals(".")) {
            try {
                double flatAmount = Double.parseDouble(flatValueEdit.getText().toString());
                btcValueEdit.setText(String.format("%1$,.2f", (flatAmount / btcRate)));
                ethValueEdit.setText(String.format("%1$,.2f", (flatAmount / ethRate)));
            } catch (NumberFormatException e) {
                Snackbar.make(findViewById(R.id.main_scroll_view), FixedValues.INVALID_CONVERSION, Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }

        } else if(view == FCBtn) {
            Intent fc = new Intent(ExchangeConvertActivity.this, FastCalculatorActivity.class);
            startActivity(fc);

        }
    }
}

