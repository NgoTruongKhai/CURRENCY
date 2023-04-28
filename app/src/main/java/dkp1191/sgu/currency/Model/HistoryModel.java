package dkp1191.sgu.currency.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class HistoryModel implements Parcelable {
    String currencyFrom;
    String currencyTo;
    String amount;
    String result;

    public HistoryModel(String currencyFrom, String currencyTo, String amount, String result) {
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.amount = amount;
        this.result = result;
    }

    protected HistoryModel(Parcel in) {
        currencyFrom = in.readString();
        currencyTo = in.readString();
        amount = in.readString();
        result = in.readString();
    }

    public static final Creator<HistoryModel> CREATOR = new Creator<HistoryModel>() {
        @Override
        public HistoryModel createFromParcel(Parcel in) {
            return new HistoryModel(in);
        }

        @Override
        public HistoryModel[] newArray(int size) {
            return new HistoryModel[size];
        }
    };

    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(String currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    public String getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(String currencyTo) {
        this.currencyTo = currencyTo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(currencyFrom);
        parcel.writeString(currencyTo);
        parcel.writeString(amount);
        parcel.writeString(result);
    }
}
