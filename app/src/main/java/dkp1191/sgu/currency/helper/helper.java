package dkp1191.sgu.currency.helper;
import android.util.Log;

import org.apache.commons.lang3.StringUtils;

import java.text.NumberFormat;
import java.util.Locale;

public class helper {
    public String getID(String title){
        String tmp=StringUtils.substringAfter(title,"/");
        return  StringUtils.substringBetween(tmp,"(",")");
    }
    public String getCountry(String title){
        String tmp=StringUtils.substringAfter(title,"/");
        return StringUtils.substringBefore(tmp,"(");
    }
    public String getScale(String description){
        description=StringUtils.substringBetween(description,"=","<br/>").trim();
        description=StringUtils.substringBefore(description," ");
        return description;
    }
    public String Format(double X){
        Locale localeEN = new Locale("en", "EN");
        NumberFormat en = NumberFormat.getInstance(localeEN);
        return en.format(X);
    }
}
