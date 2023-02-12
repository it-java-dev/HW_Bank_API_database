package Service;

import Enumeration.Currency;
import Model.ExchangeRates;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;


public class ExchangeRatesService extends AdditionalFunctions {
    public static void loadExchangeRates(String urlAddress) {
        try {
            URL url = new URL(urlAddress);
            Gson gson = new Gson();
            JsonReader jsonReader = new JsonReader(new InputStreamReader(url.openStream()));
            ExchangeRates[] exchangeRates = gson.fromJson(jsonReader, ExchangeRates[].class);
            performTransaction(() -> {
                em.persist(new ExchangeRates(Currency.UAH, 1.0, 1.0));
                for (var i : exchangeRates) {
                    em.persist(i);
                }
                return null;
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}


