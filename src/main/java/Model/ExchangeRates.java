package Model;

import Enumeration.Currency;
import Interfaces.FormatedInterface;
import com.google.gson.annotations.SerializedName;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ExchangeRates implements FormatedInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @SerializedName("ccy")
    private Currency currency;
    private Double buy;
    private Double sale;

    public ExchangeRates() {
    }

    public ExchangeRates(Currency currency, Double buy, Double sale) {
        this.currency = currency;
        this.buy = buy;
        this.sale = sale;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public double getBuy() {
        return buy;
    }

    public void setBuy(Double buy) {
        this.buy = buy;
    }

    public double getSale() {
        return sale;
    }

    public void setSale(Double sale) {
        this.sale = sale;
    }

    public String getFormattedObject() {
        return String.format("%-10d | %-10s | %-15.4f | %-15.4f",
                id, currency.name(), buy, sale);
    }

    public String getHeader() {
        return String.format("%-10s | %-10s | %-15s | %-15s",
                "id", "currency",  "buy", "sale");
    }

}