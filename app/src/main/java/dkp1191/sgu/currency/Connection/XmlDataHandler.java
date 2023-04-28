package dkp1191.sgu.currency.Connection;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import dkp1191.sgu.currency.Model.CountryModel;
import dkp1191.sgu.currency.helper.helper;

public class XmlDataHandler {

    public XmlDataHandler() {
    }

    public List<CountryModel> getAllCountry(String urlLink) throws IOException {
        List<CountryModel> countryModels = new ArrayList<>();
        InputStream inputStream = null;
        boolean isItem = false;
        String title = null;
        try {
            URL url = new URL(urlLink);
            inputStream = url.openConnection().getInputStream();
            XmlPullParser xmlPullParser = Xml.newPullParser();
            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            xmlPullParser.setInput(inputStream, null);
            while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
                int eventType = xmlPullParser.getEventType();
                String name = xmlPullParser.getName();
                if (name == null)
                    continue;
                if (eventType == XmlPullParser.END_TAG) {
                    if (name.equalsIgnoreCase("item")) {
                        isItem = false;
                    }
                    continue;
                }
                if (eventType == XmlPullParser.START_TAG) {
                    if (name.equalsIgnoreCase("item")) {
                        isItem = true;
                        continue;
                    }
                }
                Log.d("MyXmlParser", "Parsing name ==> " + name);
                String result = "";
                if (xmlPullParser.next() == XmlPullParser.TEXT) {
                    result = xmlPullParser.getText();
                    xmlPullParser.nextTag();
                }
                if (name.equalsIgnoreCase("title")) {
                    title = result;
                }
                if (title != null) {
                    if (isItem) {
                        helper helper = new helper();
                        String ID = helper.getID(title);
                        String country = helper.getCountry(title);
                        CountryModel item = new CountryModel(ID, country);
                        countryModels.add(item);
                    }
                    title = null;
                    isItem = false;
                }
            }
            return countryModels;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
        }
        return null;
    }

    public String getCurrency(String urlLink) throws IOException {
        boolean isItem = false;
        String description = null;
        try {
            URL url = new URL(urlLink);
            InputStream inputStream = url.openConnection().getInputStream();
            XmlPullParser xmlPullParser = Xml.newPullParser();
            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            xmlPullParser.setInput(inputStream, null);
            while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
                int eventType = xmlPullParser.getEventType();
                String name = xmlPullParser.getName();
                if (name == null)
                    continue;
                if (eventType == XmlPullParser.END_TAG) {
                    if (name.equalsIgnoreCase("item")) {
                        isItem = false;
                    }
                    continue;
                }
                if (eventType == XmlPullParser.START_TAG) {
                    if (name.equalsIgnoreCase("item")) {
                        isItem = true;
                        continue;
                    }
                }
                Log.d("MyXmlParser", "Parsing name ==> " + name);
                String result = "";
                if (xmlPullParser.next() == XmlPullParser.TEXT) {
                    result = xmlPullParser.getText();
                    xmlPullParser.nextTag();
                }
                if (name.equalsIgnoreCase("description")) {
                    description = result;
                }
                if (description != null) {
                    if (isItem) {
                        return new helper().getScale(description);
                    }
                    description = null;
                    isItem = false;
                }
            }
            return null;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
