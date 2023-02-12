package Service;

import Enumeration.Currency;
import Model.Accounts;
import Model.Transactions;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;


public class TransactionService extends AdditionalFunctions {

    public static void makeReplenishmentTransaction(Accounts account, Currency currency, BigDecimal amount){
        performTransaction(() -> {
            makeTransaction(account, currency, amount);
            em.refresh(account);
            Transactions transaction = new Transactions(Calendar.getInstance(), amount, currency,
                    "Replenishment of the account");
            account.addTransaction(transaction);
            em.persist(transaction);
            return null;
        });
    }

    public static void makeTransaction(Accounts account, Currency currency, BigDecimal amount) {
        String queryString = """
                UPDATE Accounts a 
                SET a.amount = (SELECT a.amount + :amount * s.buy / b.sale 
                FROM ExchangeRates s, ExchangeRates b 
                WHERE (a.id = :id AND s.currency = :currency AND b.currency = a.currency)) 
                WHERE a.id = :id
                """;
        Query query = em.createQuery(queryString);

        query.setParameter("id", account.getId());
        query.setParameter("amount", amount.doubleValue());
        query.setParameter("currency", currency);
        query.executeUpdate();
    }

    public static void makeTransfer() {
        Accounts accountFrom = findPosition("Enter the id of the bank account" +
                " from which the transfer will be made: ", Accounts.class);
        if (accountFrom == null) return;
        Accounts accountTo = findPosition("Enter the id of the bank account" +
                " to which the transfer will be made: ", Accounts.class);
        if (accountTo == null) return;
        System.out.print("Enter amount of the transfer (" + accountFrom.getCurrency().name() + "): ");
        BigDecimal amount = new BigDecimal(sc.nextLine());
        if (accountFrom.getAmount().compareTo(amount) < 0) {
            System.out.println("Not enough money");
            return;
        }
        makeTransferTransaction(accountFrom, accountTo, amount);
    }
    private static void makeTransferTransaction(Accounts accountFrom, Accounts accountTo, BigDecimal amount){
        performTransaction(() -> {
            makeTransaction(accountFrom, accountFrom.getCurrency(), amount.negate());
            em.refresh(accountFrom);
            Transactions transaction = new Transactions(Calendar.getInstance(), amount.negate(),
                    accountFrom.getCurrency(), "Transfer to account_id:   " + accountTo.getId());
            accountFrom.addTransaction(transaction);
            em.persist(transaction);

            makeTransaction(accountTo, accountFrom.getCurrency(), amount);
            em.refresh(accountTo);
            transaction = new Transactions(Calendar.getInstance(), amount, accountFrom.getCurrency(),
                    "Transfer from account_id: " + accountFrom.getId());
            accountTo.addTransaction(transaction);
            em.persist(transaction);
            return null;
        });
    }

}
