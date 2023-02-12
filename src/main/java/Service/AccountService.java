package Service;

import Enumeration.Currency;
import Model.Accounts;
import Model.Clients;

import java.math.BigDecimal;

public class AccountService extends AdditionalFunctions {

    static TransactionService ts;

    public static void addAccount() {
        Clients client = findPosition("Enter the id of the client: ", Clients.class);
        if (client == null) {
            System.out.println("No client with this id");
            return;
        }
        try {
            System.out.print("Enter currency of account(USD, EUR, UAH): ");
            Currency currency = Currency.valueOf(sc.nextLine().toUpperCase());
            System.out.print("Enter amount (" + currency.name() + "): ");
            BigDecimal amount = new BigDecimal(sc.nextLine());
            Accounts account = new Accounts(currency, amount);
            client.addAccount(account);
            performTransaction(() -> {
                em.persist(account);
                return null;
            });
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }
    public static void replenishAccount() {
        Accounts account = findPosition("Enter the id of the bank account: ", Accounts.class);
        if (account == null) {
            System.out.println("No bank account with this id");
            return;
        }
        System.out.print("Enter the name of the currency (UAH, USD, EUR) you are depositing: ");
        Currency currency = Currency.valueOf(sc.nextLine().toUpperCase());
        System.out.print(" Enter amount (" + currency.name() + "): ");
        BigDecimal amount = new BigDecimal(sc.nextLine());
        ts.makeReplenishmentTransaction(account, currency, amount);
        System.out.print("Done");
    }
}
