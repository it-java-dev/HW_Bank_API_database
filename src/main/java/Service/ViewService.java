package Service;

import Interfaces.FormatedInterface;

import javax.persistence.TypedQuery;
import java.util.List;

public class ViewService extends AdditionalFunctions {

    public static void viewTable(Class<?> clazz) {
        String name = clazz.getSimpleName();
        String whereParameter = "";
        if (name.equals("Accounts")) {
            System.out.print("Enter id of the client or empty to show all accounts:");
            if (!(whereParameter = sc.nextLine()).isEmpty()) {
                whereParameter = "WHERE o.client.id=" + whereParameter;
            }
        } else if (name.equals("Transactions")) {
            System.out.print("Enter id of the account or empty to show all accounts:");
            if (!(whereParameter = sc.nextLine()).isEmpty()) {
                whereParameter = "WHERE o.account.id=" + whereParameter;
            }
        }
        TypedQuery<FormatedInterface> query = em.createQuery("SELECT o FROM " + name + " o " + whereParameter,
                FormatedInterface.class);
        viewTable(query);
    }

    private static void viewTable(TypedQuery<FormatedInterface> query) {
        List<FormatedInterface> list = query.getResultList();
        if (list.size() == 0) {
            System.out.println("There are no such data in the database");
        } else {
            String border = "-".repeat(150);
            System.out.println(border);
            System.out.println(list.get(0).getHeader());
            System.out.println(border);
            for (var a : list) {
                System.out.println(a.getFormattedObject());
            }
            System.out.println(border);
        }
    }
}
