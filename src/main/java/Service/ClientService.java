package Service;

import Model.Clients;

import javax.persistence.TypedQuery;
import java.util.List;

public class ClientService extends AdditionalFunctions {
    public static void addClient() {
        System.out.print("Enter client's name: ");
        String name = sc.nextLine();
        System.out.print("Enter client's surname: ");
        String surname = sc.nextLine();
        System.out.print("Enter client's phone: ");
        String phone = sc.nextLine();
        System.out.print("Enter client's email: ");
        String email = sc.nextLine();
        System.out.print("Enter client's address: ");
        String address = sc.nextLine();
        Clients client = new Clients(name, surname, phone, email, address);
        performTransaction(() -> {
            em.persist(client);
            return null;
        });
        System.out.println("Client added");
        System.out.println(client.getId());
    }

    public static void showClients() {
        TypedQuery<Clients> query = em.createQuery("SELECT c FROM Clients c", Clients.class);
        List<Clients> list = query.getResultList();

        if (list.isEmpty()) {
            System.out.println("No client has been created yet");
        } else {
            String border = "-".repeat(70);
            System.out.println(border+"Clients"+border);

            for (Clients c : list) {
                System.out.println(c);
            }
            System.out.println("Count of clients = " + list.size());
            System.out.println(border+border);
        }
    }


    public static void showBalance() {
        Clients client = findPosition("Enter the id of the client: ", Clients.class);
        if (client == null) {
            return;
        }
        String queryString = """
                SELECT SUM(a.amount * ex.buy) 
                FROM Accounts a, ExchangeRates ex 
                WHERE a.client.id = :id AND a.currency=ex.currency
                """;
        TypedQuery<Double> queryBalance = em.createQuery(queryString, Double.class);
        queryBalance.setParameter("id", client.getId());

        System.out.println(String.format("Total balance: %.2f UAH",
                queryBalance.getSingleResult()));
    }


}


