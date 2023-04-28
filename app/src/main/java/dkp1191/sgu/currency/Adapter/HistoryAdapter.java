package dkp1191.sgu.currency.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import dkp1191.sgu.currency.Model.HistoryModel;
import dkp1191.sgu.currency.R;

public class HistoryAdapter extends ArrayAdapter<HistoryModel> {
    ArrayList<HistoryModel> historyModelList;
    Context mcontext;

    public HistoryAdapter(@NonNull Context context, int resource,ArrayList<HistoryModel> historyModelList) {
        super(context, resource,historyModelList);
        this.historyModelList=historyModelList;
        this.mcontext=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history,parent,false);
        TextView from=(TextView) convertView.findViewById(R.id.txtFrom);
        TextView to=(TextView) convertView.findViewById(R.id.txtTo);
        TextView amount=(TextView) convertView.findViewById(R.id.txtAmount);
        TextView result=(TextView) convertView.findViewById(R.id.txtResult);
        HistoryModel historyModel=this.getItem(position);
        if(historyModel!=null){
            from.setText("From: "+historyModel.getCurrencyFrom());
            to.setText("To: "+historyModel.getCurrencyTo());
            amount.setText("Amount: "+historyModel.getAmount());
            result.setText("Result: "+historyModel.getResult());
        }
        return convertView;
    }
}
