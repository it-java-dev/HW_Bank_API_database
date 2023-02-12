package Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class AdditionalFunctions {
    protected static Scanner sc = new Scanner(System.in);
    protected static EntityManagerFactory emf = Persistence.createEntityManagerFactory("Bank");
    protected static EntityManager em = emf.createEntityManager();
    protected static  <T> T performTransaction(Callable<T> action) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            T result = action.call();
            transaction.commit();

            return result;
        } catch (Exception ex) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            ex.printStackTrace();
            return null;
        }
    }

    protected static <T> T findPosition(String message, Class<T> clazz) {
        System.out.print(message);
        String name = clazz.getSimpleName();
        long id = Long.parseLong(sc.nextLine());
        T position = em.find(clazz, id);
        if (position == null) {
            System.out.println(name.substring(0, name.length() - 1) + " not found!");
        }
        return position;
    }
}
