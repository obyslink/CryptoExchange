package tech.dappworld.cryptoexchange;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import com.android.volley.toolbox.Volley;

import tech.dappworld.cryptoexchange.Model.GetRates;

import static tech.dappworld.cryptoexchange.R.id.items_listView;

/**
 * Created by OBINNA on 10/21/2017.
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    LinearLayout mainView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ListView ratesListView;
    public GetRates ratesLedger;
    RequestQueue rQueue;
    String requestUrl;

    SharedPreferences settings;
    int orderMode;

    private ProgressBar mProgressBar;
    private TextView mLoadingText;
    private int mProgressStatus = 0;
    private Handler mHandler = new Handler();
    private boolean isRecieverRegistered = false,
            isNetDialogShowing = false, isDataRecieved = false;
    private AlertDialog internetDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mainView = (LinearLayout) findViewById(R.id.main_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.layout_swipe_refresh);
        ratesListView = (ListView) findViewById(items_listView);
        ratesListView.setDivider(null); //to remove dividers from the list view

        ratesLedger = new GetRates();

        rQueue = Volley.newRequestQueue(getApplicationContext());

        settings = getSharedPreferences("mSettings", MODE_PRIVATE);
        orderMode = settings.getInt("orderMode", FixedValues.ORDER_ALPHABETICAL);


        requestUrl = "https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC,ETH&tsyms=" +
                "AFN,DZD,NGN,USD,EUR,INR,GBP,EGP,JPY,GBP,AUD,CAD,CHF,XOF,CNY,KES,GHS,UGX,ZAR,XAF,NZD,MYR,RUB,BND,GEL";


                downloadRates();

                mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        downloadRates();
                    }
                });


    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(internetConnectionReciever, new IntentFilter(
                "android.net.conn.CONNECTIVITY_CHANGE"));
        if (Util.isNetworkAvailable(this)) {
            if (!isDataRecieved) {
                isDataRecieved = true;
                downloadRates();
            }
        }
    }

    public BroadcastReceiver internetConnectionReciever = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetInfo = connectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo activeWIFIInfo = connectivityManager
                    .getNetworkInfo(connectivityManager.TYPE_WIFI);

            if (activeWIFIInfo.isConnected() || activeNetInfo.isConnected()) {
                removeInternetDialog();
            } else {
                if (isNetDialogShowing) {
                    return;
                }
                showInternetDialog();
            }
        }
    };

    private void removeInternetDialog() {
        if (internetDialog != null && internetDialog.isShowing()) {
            internetDialog.dismiss();
            isNetDialogShowing = false;
            internetDialog = null;

        }
    }

    private void showInternetDialog() {
        Util.removeCustomProgressDialog();
        isNetDialogShowing = true;
        AlertDialog.Builder internetBuilder = new AlertDialog.Builder(
                MainActivity.this);
        internetBuilder.setCancelable(false);
        internetBuilder
                .setTitle(getString(R.string.dialog_no_internet))
                .setMessage(getString(R.string.dialog_no_inter_message))
                .setPositiveButton(getString(R.string.dialog_enable_3g),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // continue with delete
                                Intent intent = new Intent(
                                        android.provider.Settings.ACTION_SETTINGS);
                                startActivity(intent);
                                removeInternetDialog();
                            }
                        })
                .setNeutralButton(getString(R.string.dialog_enable_wifi),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // User pressed Cancel button. Write
                                // Logic Here
                                startActivity(new Intent(
                                        Settings.ACTION_WIFI_SETTINGS));
                                removeInternetDialog();
                            }
                        })
                .setNegativeButton(getString(R.string.dialog_skip),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // do nothing
                                removeInternetDialog();
                                proceedToDummyMainActivity();
                                //finish();
                            }
                        });
        internetDialog = internetBuilder.create();
        internetDialog.show();
    }

    private void proceedToDummyMainActivity() {
        Intent intent = new Intent(MainActivity.this, DummyMainActivity.class);
        startActivity(intent);
    }


    private static class ViewHolder {
        private TextView currencyTextView;
        private TextView btcTextView;
        private TextView ethTextView;
    }


    private class MyAdapter extends BaseAdapter {
        ArrayList<GetRates.MoneyRate> rates;

        private MyAdapter(ArrayList<GetRates.MoneyRate> ratesInstance) {
            rates = ratesInstance;
        }

        public int getCount() {
            return rates.size();
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("DefaultLocale")
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_view_header_exchange_rate, parent, false);
                holder = new ViewHolder();
                holder.currencyTextView = (TextView) convertView.findViewById(R.id.currency_TextViewList);
                holder.btcTextView = (TextView) convertView.findViewById(R.id.btc_getrateTextViewList);
                holder.ethTextView = (TextView) convertView.findViewById(R.id.eth_getrateTextViewList);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final GetRates.MoneyRate rateRow = rates.get(position);

            final String crossCurrency = rateRow.getCurrency();
            final double crossBtc = rateRow.getBtcRate();
            final double crossEth = rateRow.getEthRate();

            holder.currencyTextView.setText(crossCurrency);
            holder.btcTextView.setText(String.format("%1$,.2f", crossBtc));
            holder.ethTextView.setText(String.format("%1$,.2f", crossEth));

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, ExchangeConvertActivity.class);
                    intent.putExtra(FixedValues.EXTRA_CURRENCY, crossCurrency);
                    intent.putExtra(FixedValues.EXTRA_BTC_RATE, crossBtc);
                    intent.putExtra(FixedValues.EXTRA_ETH_RATE, crossEth);
                    startActivity(intent);
                }
            });

            return convertView;

        }
    }

    //Get JSON objects from API and handle progress bar
    public void downloadRates() {

        //handling the progress bar with ProgressBar and Handler
        mProgressBar = (ProgressBar) findViewById(R.id.exchangerate_progressbar);
        mLoadingText = (TextView) findViewById(R.id.LoadingCompleteTextView);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (mProgressStatus < 100) {
                            mProgressStatus++;
                            android.os.SystemClock.sleep(50);
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(mProgressStatus);
                                }
                            });
                        }
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mLoadingText.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                }).start();



            //calling rates to objects through JSON from API
            ratesLedger = new GetRates();

            JsonObjectRequest requestNameAvatar = new JsonObjectRequest(Request.Method.GET, requestUrl, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONObject btc_rates = response.getJSONObject("BTC".trim());
                                JSONObject eth_rates = response.getJSONObject("ETH".trim());

                                Iterator<?> keysBTC = btc_rates.keys();
                                Iterator<?> keysETH = eth_rates.keys();

                                while (keysBTC.hasNext() && keysETH.hasNext()) {
                                    String keyBTC = (String) keysBTC.next();
                                    String keyETH = (String) keysETH.next();

                                    ratesLedger.add(keyBTC, btc_rates.getDouble(keyBTC), eth_rates.getDouble(keyETH));
                                }

                                mSwipeRefreshLayout.setRefreshing(false); //to remove the progress bar for refresh
                                ratesLedger.orderList(orderMode);
                                ratesListView.setAdapter(new MyAdapter(ratesLedger.ratesArrayList));

                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Network Error! Refresh", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {


                @Override
                public void onErrorResponse(VolleyError error) {
                    Snackbar.make(mainView, FixedValues.REFRESH_ERROR, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    mSwipeRefreshLayout.setRefreshing(false);
                }


            });

            rQueue.add(requestNameAvatar);

    }



    @Override
    protected void onStop() {
        super.onStop();
        rQueue.cancelAll(this);
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //Menu options for refresh, order and about
        if (id == R.id.action_refresh) {
            mSwipeRefreshLayout.setRefreshing(true); // progress bar for refresh
            downloadRates();
            return true;
        } else if (id == R.id.action_order) {
            if (orderMode == FixedValues.ORDER_ALPHABETICAL) orderMode = FixedValues.ORDER_BY_RATE;
            else orderMode = FixedValues.ORDER_ALPHABETICAL;
            ratesLedger.orderList(orderMode);
            ratesListView.setAdapter(new MyAdapter(ratesLedger.ratesArrayList));
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("orderMode", orderMode);
            editor.apply();
        }else if (id == R.id.action_exit) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        //Navigation bar menu
        int id = item.getItemId();

        if (id == R.id.quick_convertMenu) {
            Intent intent = new Intent(MainActivity.this, FastCalculatorNavActivity.class);
            startActivity(intent);
        } else if (id == R.id.quick_aboutMenu) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        }else if (id == R.id.offlineCalculator) {
            Intent intent = new Intent(MainActivity.this, OfflineCalculatorActivity.class);
            startActivity(intent);
        }else if (id == R.id.visit_repo) {
            Intent intent = new Intent(MainActivity.this, RepositoryWebViewActivity.class);
            startActivity(intent);
        }else if (id == R.id.share) {
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

