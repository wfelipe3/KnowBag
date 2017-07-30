package storage;

import com.microsoft.azure.storage.table.TableServiceEntity;

import java.util.UUID;

/**
 * Created by dev-williame on 4/3/17.
 */
public class Person extends TableServiceEntity {

    private String address;
    private Integer phone;

    public Person(String name, String address, Integer phone) {
        this.partitionKey = name;
        this.rowKey = UUID.randomUUID().toString();
        this.address = address;
        this.phone = phone;
    }

    public Person() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Person{" +
                "address='" + address + '\'' +
                ", phone=" + phone +
                '}';
    }
}
