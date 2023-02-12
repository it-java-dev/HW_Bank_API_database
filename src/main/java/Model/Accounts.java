package Model;

import Enumeration.Currency;
import Interfaces.FormatedInterface;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Accounts implements FormatedInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Currency currency; // currency of account
    private BigDecimal amount; // amount of account
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Clients client;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Transactions> transactions = new ArrayList<>();


    public Accounts() {
    }

    public Accounts(Currency currency, BigDecimal amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public void addTransaction(Transactions transaction) {
        if (!transactions.contains(transaction)) {
            transactions.add(transaction);
            transaction.setAccount(this);
        }
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Clients getClient() {
        return client;
    }

    public void setClient(Clients client) {
        this.client = client;
    }

    public List<Transactions> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transactions> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String getFormattedObject() {
        return String.format("%-10d | %-10d | %-10s | %-15.2f | %-10d",
                id, client.getId(), currency, amount, transactions.size());
    }

    @Override
    public String getHeader() {
        return String.format("%-10s | %-10s | %-10s | %-15s | %-10s",
                "account_id", "client_id", "currency", "amount", "transactions");
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Accounts{");
        sb.append("id=").append(id);
        sb.append(", currency=").append(currency);
        sb.append(", amount=").append(amount);
        sb.append(", client=").append(client);
        sb.append(", transactions=").append(transactions);
        sb.append('}');
        return sb.toString();
    }
}

