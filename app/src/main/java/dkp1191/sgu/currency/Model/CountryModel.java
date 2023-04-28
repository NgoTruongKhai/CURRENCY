package dkp1191.sgu.currency.Model;

public class CountryModel {
    String country;
    String ID;

    public CountryModel(String ID, String country) {
        this.ID=ID;
        this.country=country;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
