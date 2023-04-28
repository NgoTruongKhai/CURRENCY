package dkp1191.sgu.currency;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import dkp1191.sgu.currency.Adapter.HistoryAdapter;
import dkp1191.sgu.currency.Model.HistoryModel;

public class HistoryActivity extends AppCompatActivity {
    private ListView lsHistory;
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);
        Intent intent=getIntent();
        ArrayList<HistoryModel> test = intent.getParcelableArrayListExtra("history");
        lsHistory=(ListView) findViewById(R.id.lsHistory);
        HistoryAdapter adapter=new HistoryAdapter(this,R.layout.item_history,test);
        lsHistory.setAdapter(adapter);
        Log.e("abc", adapter.toString() );
    }

}
