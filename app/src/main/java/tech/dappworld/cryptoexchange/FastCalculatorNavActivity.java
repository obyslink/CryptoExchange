package tech.dappworld.cryptoexchange;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.dappworld.cryptoexchange.Model.Coin;
import tech.dappworld.cryptoexchange.Remote.CoinService;

public class FastCalculatorNavActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferences settings;
    int orderMode;
    CoinService mService;

    RadioButton coin2coin,money2coin,coin2money;
    MaterialSpinner fromSpinner,toSpinner;
    RadioGroup radioGroup;

    /*Button btnConvert;*/
    ImageButton btnConvert;
    TextView clickToConvert;

    ImageView coinImage;
    TextView toTextView;

    String[] money = {"NGN","USD","EUR","GBP","INR","JPY","GBP","AUD","CAD","CHF","CNY","KES","GHS","UGX","ZAR","XAF","NZD","MYR","BND","GEL","RUB"};
    String[] coin = {"BTC","ETH","ETC","XRP","LTC","XMR","DASH","MAID","AUR","XEM"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_calculator_nav);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //RELATING BUTTONS AND ACTIONS TO VARIABLES
        settings = getSharedPreferences("mSettings", MODE_PRIVATE);
        orderMode = settings.getInt("orderMode", FixedValues.ORDER_ALPHABETICAL);

        mService = Common.getCoinService();

        fromSpinner = (MaterialSpinner)findViewById(R.id.fromSpinner);
        toSpinner = (MaterialSpinner)findViewById(R.id.toSpinner);

        toTextView = (TextView)findViewById(R.id.toTextView);

        btnConvert = (ImageButton)findViewById(R.id.btnConvert);
        clickToConvert = (TextView)findViewById(R.id.clickToConvert);

        btnConvert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                calculateValue();
            }
        });
        clickToConvert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                calculateValue();
            }
        });
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i){
                if(i == R.id.coin2coin)
                    setCoin2CoinSource();
                else if(i == R.id.money2coin)
                    setMoney2CoinSource();
                else if(i == R.id.coin2money)
                    setCoin2MoneySource();
                else
                    Toast.makeText(FastCalculatorNavActivity.this, "Please select an option", Toast.LENGTH_SHORT).show();

            }
        });
        coin2coin = (RadioButton) findViewById(R.id.coin2coin);
        money2coin = (RadioButton) findViewById(R.id.money2coin);
        coin2money = (RadioButton) findViewById(R.id.coin2money);

        coinImage = (ImageView)findViewById(R.id.coinImage);
        loadCoinList();

    }
    // GETTING SOURCE FOR THE THREE DIFFERENT SELECTION TYPES
    private void loadCoinList() {
        if(coin2money.isSelected())
            setCoin2MoneySource();
        else if(coin2coin.isSelected())
            setCoin2CoinSource();
        else if(money2coin.isSelected())
            setMoney2CoinSource();
        else
            Toast.makeText(FastCalculatorNavActivity.this, "No option has been selected yet", Toast.LENGTH_SHORT).show();
            //auto set first choice to avoid app crash
            setCoin2MoneySource();
    }

    private void setCoin2MoneySource() {
        fromSpinner.setItems(coin);
        toSpinner.setItems(money);
    }
    private void setMoney2CoinSource() {
        fromSpinner.setItems(money);
        toSpinner.setItems(coin);
    }

    private void setCoin2CoinSource() {
        fromSpinner.setItems(coin);
        toSpinner.setItems(coin);
    }

    //ACTION TO CALCULATE THE CURRENTLY SELECTED MODE
    private void calculateValue() {

        final ProgressDialog mDialog = new ProgressDialog(FastCalculatorNavActivity.this);
        mDialog.setMessage("Please wait...");
        mDialog.show();

        final String coinName = toSpinner.getItems().get(toSpinner.getSelectedIndex()).toString();

        String fromCoin = fromSpinner.getItems().get(fromSpinner.getSelectedIndex()).toString();

        mService.calculateValue(fromCoin,coinName).enqueue(new Callback<Coin>() {
            @Override
            public void onResponse(Call<Coin> call, Response<Coin> response) {
                mDialog.dismiss();

                //selection for coins
                if(coinName.equals("BTC"))
                    showData(response.body().getBTC());
                else if(coinName.equals("ETC"))
                    showData(response.body().getETC());
                else if(coinName.equals("ETH"))
                    showData(response.body().getETH());
                else if(coinName.equals("AUR"))
                    showData(response.body().getAUR());
                else if(coinName.equals("DASH"))
                    showData(response.body().getDASH());
                else if(coinName.equals("MAID"))
                    showData(response.body().getMAID());
                else if(coinName.equals("LTC"))
                    showData(response.body().getLTC());
                else if(coinName.equals("XMR"))
                    showData(response.body().getXMR());
                else if(coinName.equals("XEM"))
                    showData(response.body().getXEM());

                    //selection for currencies
                else if(coinName.equals("XAF"))
                    showData(response.body().getXAF());
                else if(coinName.equals("NGN"))
                    showData(response.body().getNGN());
                else if(coinName.equals("USD"))
                    showData(response.body().getUSD());
                else if(coinName.equals("EUR"))
                    showData(response.body().getEUR());
                else if(coinName.equals("JPY"))
                    showData(response.body().getJPY());
                else if(coinName.equals("GBP"))
                    showData(response.body().getGBP());
                else if(coinName.equals("AUD"))
                    showData(response.body().getAUD());
                else if(coinName.equals("INR"))
                    showData(response.body().getINR());
                else if(coinName.equals("RUB"))
                    showData(response.body().getRUB());
                else if(coinName.equals("GEL"))
                    showData(response.body().getGEL());
                else if(coinName.equals("BND"))
                    showData(response.body().getBND());
                else if(coinName.equals("MYR"))
                    showData(response.body().getMYR());
                else if(coinName.equals("NZD"))
                    showData(response.body().getNZD());
                else if(coinName.equals("ZAF"))
                    showData(response.body().getZAF());
                else if(coinName.equals("ZAR"))
                    showData(response.body().getZAR());
                else if(coinName.equals("UGX"))
                    showData(response.body().getUGX());
                else if(coinName.equals("GHX"))
                    showData(response.body().getGHS());
                else if(coinName.equals("KES"))
                    showData(response.body().getKES());
                else if(coinName.equals("CNY"))
                    showData(response.body().getCNY());
                else if(coinName.equals("CHF"))
                    showData(response.body().getCHF());
                else if(coinName.equals("CAD"))
                    showData(response.body().getCAD());

                    //handle exception by showing BTC value
                else showData(response.body().getBTC());


            }

            @Override
            public void onFailure(Call<Coin> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
                mDialog.dismiss();

            }
        });
    }

    //DISPLAY IMAGE LOGO + RESULT FROM API SERVICE FOR CURRENTLY CALCULATED VALUES
    private void showData(String value) {
        if (!Util.isNetworkAvailable(this)) {
            Util.showToast(getResources().getString(R.string.no_internet),
                    this);
            return;
        }

        //COIN LISTS
        if(toSpinner.getItems().get(toSpinner.getSelectedIndex()).toString().equals("BTC")){
            Picasso.with(FastCalculatorNavActivity.this).load(Common.BTC_IMAGE)
                    .into(coinImage);
            toTextView.setText(value);
        }
        else if(toSpinner.getItems().get(toSpinner.getSelectedIndex()).toString().equals("ETC")){
            Picasso.with(FastCalculatorNavActivity.this).load(Common.ETC_IMAGE)
                    .into(coinImage);
            toTextView.setText(value);
        }
        else if(toSpinner.getItems().get(toSpinner.getSelectedIndex()).toString().equals("ETH")){
            Picasso.with(FastCalculatorNavActivity.this).load(Common.ETH_IMAGE)
                    .into(coinImage);
            toTextView.setText(value);
        }
        else if(toSpinner.getItems().get(toSpinner.getSelectedIndex()).toString().equals("LTC")){
            Picasso.with(FastCalculatorNavActivity.this).load(Common.LTC_IMAGE)
                    .into(coinImage);
            toTextView.setText(value);
        }
        else if(toSpinner.getItems().get(toSpinner.getSelectedIndex()).toString().equals("AUR")){
            Picasso.with(FastCalculatorNavActivity.this).load(Common.AUR_IMAGE)
                    .into(coinImage);
            toTextView.setText(value);
        }
        else if(toSpinner.getItems().get(toSpinner.getSelectedIndex()).toString().equals("DASH")){
            Picasso.with(FastCalculatorNavActivity.this).load(Common.DASH_IMAGE)
                    .into(coinImage);
            toTextView.setText(value);
        }
        else if(toSpinner.getItems().get(toSpinner.getSelectedIndex()).toString().equals("MAID")){
            Picasso.with(FastCalculatorNavActivity.this).load(Common.MAID_IMAGE)
                    .into(coinImage);
            toTextView.setText(value);
        }
        else if(toSpinner.getItems().get(toSpinner.getSelectedIndex()).toString().equals("XRP")){
            Picasso.with(FastCalculatorNavActivity.this).load(Common.XRP_IMAGE)
                    .into(coinImage);
            toTextView.setText(value);
        }
        else if(toSpinner.getItems().get(toSpinner.getSelectedIndex()).toString().equals("XMR")){
            Picasso.with(FastCalculatorNavActivity.this).load(Common.XMR_IMAGE)
                    .into(coinImage);
            toTextView.setText(value);
        }
        else if(toSpinner.getItems().get(toSpinner.getSelectedIndex()).toString().equals("XEM")){
            Picasso.with(FastCalculatorNavActivity.this).load(Common.XEM_IMAGE)
                    .into(coinImage);
            toTextView.setText(value);
        }
        // CURRENCY LIST
        else if(toSpinner.getItems().get(toSpinner.getSelectedIndex()).toString().equals("NGN")){

            toTextView.setText("₦" +value);
        }
        else if(toSpinner.getItems().get(toSpinner.getSelectedIndex()).toString().equals("USD")){

            toTextView.setText("$" +value);
        }
        else if(toSpinner.getItems().get(toSpinner.getSelectedIndex()).toString().equals("GBP")){

            toTextView.setText("£" +value);
        }
        else if(toSpinner.getItems().get(toSpinner.getSelectedIndex()).toString().equals("EUR")){

            toTextView.setText("€" +value);
        }
        else if(toSpinner.getItems().get(toSpinner.getSelectedIndex()).toString().equals("INR")){

            toTextView.setText("₨" +value);
        }
        else if(toSpinner.getItems().get(toSpinner.getSelectedIndex()).toString().equals("JPY")){

            toTextView.setText("¥" +value);
        }
        else if(toSpinner.getItems().get(toSpinner.getSelectedIndex()).toString().equals("AUD")){

            toTextView.setText("$" +value);
        }
        else if(toSpinner.getItems().get(toSpinner.getSelectedIndex()).toString().equals("CAD")){

            toTextView.setText("$" +value);
        }
        else if(toSpinner.getItems().get(toSpinner.getSelectedIndex()).toString().equals("CHF")){

            toTextView.setText("₣" +value);
        }
        else if(toSpinner.getItems().get(toSpinner.getSelectedIndex()).toString().equals("CNY")){

            toTextView.setText("¥" +value);
        }
        else if(toSpinner.getItems().get(toSpinner.getSelectedIndex()).toString().equals("KES")){

            toTextView.setText("€" +value);
        }
        else if(toSpinner.getItems().get(toSpinner.getSelectedIndex()).toString().equals("GHS")){

            toTextView.setText("Sh" +value);
        }
        else if(toSpinner.getItems().get(toSpinner.getSelectedIndex()).toString().equals("UGX")){

            toTextView.setText("Sh" +value);
        }
        else if(toSpinner.getItems().get(toSpinner.getSelectedIndex()).toString().equals("ZAR")){

            toTextView.setText("R" +value);
        }
        else if(toSpinner.getItems().get(toSpinner.getSelectedIndex()).toString().equals("XAF")){

            toTextView.setText("₣" +value);
        }
        else if(toSpinner.getItems().get(toSpinner.getSelectedIndex()).toString().equals("NZD")){

            toTextView.setText("$" +value);
        }
        else if(toSpinner.getItems().get(toSpinner.getSelectedIndex()).toString().equals("MYR")){

            toTextView.setText("RM" +value);
        }
        else if(toSpinner.getItems().get(toSpinner.getSelectedIndex()).toString().equals("BND")){

            toTextView.setText("$" +value);
        }
        else if(toSpinner.getItems().get(toSpinner.getSelectedIndex()).toString().equals("GEL")){

            toTextView.setText("ლ" +value);
        }
        else if(toSpinner.getItems().get(toSpinner.getSelectedIndex()).toString().equals("RUB")){

            toTextView.setText("р." +value);
        }


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.general_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //Menu options for repository, about and exit
        if (id == R.id.action_exit) {
            finish();
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        //Navigation bar menu
        int id = item.getItemId();

        if (id == R.id.quick_convertMenu) {
            Intent intent = new Intent(FastCalculatorNavActivity.this, FastCalculatorNavActivity.class);
            startActivity(intent);
        } else if (id == R.id.quick_aboutMenu) {
            Intent intent = new Intent(FastCalculatorNavActivity.this, AboutActivity.class);
            startActivity(intent);
        } else if (id == R.id.homeExchangeRate) {
            Intent intent = new Intent(FastCalculatorNavActivity.this, MainActivity.class);
            startActivity(intent);
        }else if (id == R.id.offlineCalculator) {
            Intent intent = new Intent(FastCalculatorNavActivity.this, OfflineCalculatorActivity.class);
            startActivity(intent);
        } else if (id == R.id.visit_repo) {
            Intent intent = new Intent(FastCalculatorNavActivity.this, RepositoryWebViewActivity.class);
            startActivity(intent);
        } else if (id == R.id.share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent
                    .putExtra(
                            Intent.EXTRA_TEXT,
                            "I am using "
                                    + getString(R.string.app_name)
                                    + " App ! Why don't you try it out...\nInstall "
                                    + getString(R.string.app_name)
                                    + " now !\nhttps://play.google.com/store/apps/details?id="
                                    + getPackageName());
            sendIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                    getString(R.string.app_name) + " App !");
            sendIntent.setType("text/plain");

            startActivity(Intent.createChooser(sendIntent,
                    getString(R.string.text_share_app)));
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
