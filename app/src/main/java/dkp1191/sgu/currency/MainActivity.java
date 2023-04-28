package dkp1191.sgu.currency;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.jaredrummler.materialspinner.MaterialSpinner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import dkp1191.sgu.currency.Adapter.CountryAdapter;
import dkp1191.sgu.currency.Connection.XmlDataHandler;
import dkp1191.sgu.currency.Model.CountryModel;
import dkp1191.sgu.currency.Model.HistoryModel;
import dkp1191.sgu.currency.helper.helper;

public class MainActivity extends AppCompatActivity {
    private Button btnConvert;
    private EditText etFirstCurrency;
    private EditText etSecondCurrency;
    private TextView txtFirstCurrencyName;
    private TextView txtSecondCurrencyName;
    MaterialSpinner spnFirstCountry;
    MaterialSpinner spnSecondCountry;
    List<CountryModel> CountryModelList = null;
    int from = 0;
    int to = 0;
    ArrayList<HistoryModel> historyModelArrayList=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnConvert = (Button) findViewById(R.id.btnConvert);
        etFirstCurrency = (EditText) findViewById(R.id.etFirstCurrency);
        etSecondCurrency = (EditText) findViewById(R.id.etSecondCurrency);
        spnFirstCountry = (MaterialSpinner) findViewById(R.id.spnFirstCountry);
        spnSecondCountry = (MaterialSpinner) findViewById(R.id.spnSecondCountry);
        txtFirstCurrencyName=(TextView) findViewById(R.id.txtFirstCurrencyName);
        txtSecondCurrencyName=(TextView) findViewById(R.id.txtSecondCurrencyName);
        //Check internet connection
        Timer timer = new Timer();
        Message msg = new Message();
        Handler handler = new Handler(new Handler.Callback() {

            @Override
            public boolean handleMessage(Message msg) {
                if(msg.arg1==1)
                {
                    Toast.makeText(MainActivity.this,"Network is not connected !",Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });
        timer.schedule(new TimerTask() {
            boolean check=false;
            @Override
            public void run() {
                if (checkInternetConnection()) {
                    if(CountryModelList==null){
//                        Toast.makeText(MainActivity.this,"Network is connected !",Toast.LENGTH_LONG).show();
                        new getAllCountry().execute((Void) null);
                        check=false;
                    }
                }else{
                    if (check==false){
                        msg.arg1=1;
                        handler.sendMessage(msg);
                        check=true;
                    }
                }
            }
        }, 0, 5000);

        //Select Currency countruy
        spnFirstCountry.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                from = position;
                CountryModel countryModel = CountryModelList.get(position);
                spnFirstCountry.setText(countryModel.getID() + " - " + countryModel.getCountry());
                txtFirstCurrencyName.setText(countryModel.getID());
            }
        });

        spnSecondCountry.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                to = position;
                CountryModel countryModel = CountryModelList.get(position);
                spnSecondCountry.setText(countryModel.getID() + " - " + countryModel.getCountry());
                txtSecondCurrencyName.setText(countryModel.getID());
            }
        });

        //Convert currency
        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String countryFromID = CountryModelList.get(from).getID();
                String countryToID = CountryModelList.get(to).getID();
                String amount = String.valueOf(etFirstCurrency.getText());
                if (!amount.equals("")) {
                    if (countryFromID.equals(countryToID)) {
                        etSecondCurrency.setText(amount);
                    } else {
                        if (checkInternetConnection()) {
                            new CurrencyCoutry(countryFromID, countryToID, amount).execute((Void) null);
                        } else {
                            Toast.makeText(getApplicationContext(), "Network is not connected !", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Enter amount !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    TextView txtHistory=(TextView) findViewById(R.id.txtHistory);
    txtHistory.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(MainActivity.this,HistoryActivity.class);
            if (historyModelArrayList==null){
                Toast.makeText(MainActivity.this,"Not have a convert history",Toast.LENGTH_SHORT).show();
            }else {
                intent.putParcelableArrayListExtra("history",historyModelArrayList);
                startActivity(intent);
            }

        }
    });
    }

    //Load data
    private class getAllCountry extends AsyncTask<Void, Void, Boolean> {
        private String urlLink;
        @Override
        protected void onPreExecute() {
            urlLink = "https://usd.fxexchangerate.com/rss.xml";
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            if (TextUtils.isEmpty(urlLink))
                return false;
            try {
                if (!urlLink.startsWith("http://") && !urlLink.startsWith("https://"))
                    urlLink = "http://" + urlLink;
                CountryModelList = new ArrayList<>();
                CountryModelList = new XmlDataHandler().getAllCountry(urlLink);
                return true;
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                spnFirstCountry.setAdapter(new CountryAdapter(getApplicationContext(), CountryModelList));
                spnSecondCountry.setAdapter(new CountryAdapter(getApplicationContext(), CountryModelList));
                spnFirstCountry.setText(CountryModelList.get(0).getID() + " - " + CountryModelList.get(0).getCountry());
                spnSecondCountry.setText(CountryModelList.get(0).getID() + " - " + CountryModelList.get(0).getCountry());
            } else {
                Toast.makeText(MainActivity.this, "Enter a valid Rss feed url", Toast.LENGTH_LONG).show();
            }
        }
    }


    private class CurrencyCoutry extends AsyncTask<Void, Void, Boolean> {
        private String from, to;
        String urlLink;
        String result;
        String amount;

        public CurrencyCoutry(String from, String to, String amount) {
            this.from = from;
            this.to = to;
            this.amount = amount;
        }

        @Override
        protected void onPreExecute() {
            urlLink = "https://" + from.toLowerCase() + ".fxexchangerate.com/" + to.toLowerCase() + ".xml";
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            if (TextUtils.isEmpty(urlLink))
                return false;
            try {
                if (!urlLink.startsWith("http://") && !urlLink.startsWith("https://"))
                    urlLink = "http://" + urlLink;
                result = new XmlDataHandler().getCurrency(urlLink);
                result = count(Double.valueOf(amount), Double.valueOf(result));
                return true;
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                etSecondCurrency.setText(result);
                if(historyModelArrayList==null){
                    historyModelArrayList=new ArrayList<HistoryModel>();
                }
                HistoryModel model=new HistoryModel(from,to,amount,result);
                historyModelArrayList.add(model);
            } else {
                Toast.makeText(MainActivity.this, "Enter a valid Rss feed url", Toast.LENGTH_LONG).show();
            }
        }
    }

    private String count(double amount, double scale) {
        return new helper().Format(amount * scale);
    }

    private boolean checkInternetConnection() {
        ConnectivityManager connManager =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        if (networkInfo == null) {
//            Toast.makeText(this, "No default network is currently active", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!networkInfo.isConnected()) {
//            Toast.makeText(this, "Network is not connected", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!networkInfo.isAvailable()) {
//            Toast.makeText(this, "Network not available", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}