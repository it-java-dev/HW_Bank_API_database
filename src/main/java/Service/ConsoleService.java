package Service;

import Model.Accounts;
import Model.ExchangeRates;
import Model.Transactions;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class ConsoleService {

    static EntityManager em;
    static ExchangeRatesService ex;
    static AccountService as;
    static ClientService cs;
    static TransactionService ts;
    static ViewService vs;
    static Scanner sc = new Scanner(System.in);

    public ConsoleService() {
    }



    public void mainMenu() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Bank");
        em = emf.createEntityManager();
        try {
            ExchangeRatesService.loadExchangeRates("https://api.privatbank.ua/p24api/pubinfo?exchange&json&coursid=11");
            try {
                while (true) {
                    System.out.println("1: add client");
                    System.out.println("2: add bank account");
                    System.out.println("3: replenish bank account");
                    System.out.println("4: make transaction");
                    System.out.println("5: show the client's total balance");
                    System.out.println("6: show clients");
                    System.out.println("7: show accounts");
                    System.out.println("8: show transactions");
                    System.out.println("9: show exchange rates");
                    System.out.print("-> ");

                    String s = sc.nextLine();
                    switch (s) {
                        case "1" -> ClientService.addClient();
                        case "2" -> AccountService.addAccount();
                        case "3" -> AccountService.replenishAccount();
                        case "4" -> TransactionService.makeTransfer();
                        case "5" -> ClientService.showBalance();
                        case "6" -> ClientService.showClients();
                        case "7" -> ViewService.viewTable(Accounts.class);
                        case "8" -> ViewService.viewTable(Transactions.class);
                        case "9" -> ViewService.viewTable(ExchangeRates.class);
                        default -> {
                            return;
                        }
                    }
                }
            } finally {
                sc.close();
                em.close();
                emf.close();
            }
        } catch (Exception ex) {
            System.out.println("Incorrect entered data, try again");
            ex.printStackTrace();
        }

    }
}