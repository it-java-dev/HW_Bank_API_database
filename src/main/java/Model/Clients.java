package Model;

import Interfaces.FormatedInterface;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Clients implements FormatedInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String surname;
    private String phone;
    private String email;
    private String address;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Accounts> accounts = new ArrayList<>();

    public Clients() {
    }

    public Clients(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public Clients(String name, String surname, String phone, String email, String address) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Accounts> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Accounts> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Clients{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", surname='").append(surname).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", accounts_amount=").append(accounts.size());
        sb.append('}');
        return sb.toString();
    }

    public void addAccount(Accounts account) {
        if (!accounts.contains(account)) {
            accounts.add(account);
            account.setClient(this);
        }


    }

    @Override
    public String getFormattedObject() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < accounts.size(); i++) {
            sb.append("#").append(accounts.get(i).getId());
            sb.append("(").append(accounts.get(i).getCurrency().name()).append(")");
            if (i != accounts.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return String.format("%-10d | %-25s | %-25s | %-15s | %-25s | %-25s | %-25s | %-25s",
                id, name, surname, phone, email, address, accounts, sb);
    }

    @Override
    public String getHeader() {
        return String.format("%-10d | %-25s | %-25s | %-15s | %-25s | %-25s",
                "id", "name", "surname", "phone", "email", "address");
    }
}
