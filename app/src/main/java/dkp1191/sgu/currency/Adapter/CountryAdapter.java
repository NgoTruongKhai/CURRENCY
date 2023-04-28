package dkp1191.sgu.currency.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jaredrummler.materialspinner.MaterialSpinnerAdapter;
import java.util.List;
import dkp1191.sgu.currency.Model.CountryModel;

public class CountryAdapter extends MaterialSpinnerAdapter<CountryModel> {
    public CountryAdapter(Context context, List<CountryModel> items) {
        super(context, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=LayoutInflater.from(parent.getContext()).inflate(com.jaredrummler.materialspinner.R.layout.ms__list_item,parent,false);
        TextView countryName=(TextView) convertView.findViewById(com.jaredrummler.materialspinner.R.id.tv_tinted_spinner);
        CountryModel countryModel=this.getItem(position);
        if(countryModel!=null){
            countryName.setText(countryModel.getID()+" - "+countryModel.getCountry());
        }
        return convertView;
    }


}
