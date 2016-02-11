package knowbag.async.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

/**
 * Created by feliperojas on 2/4/16.
 */
@Entity(name = "Person")
@Table(name = "Person")
@NamedQuery(name = "findAll", query = "select p from Person p")
public class Person {

    public Person(long id) {
        this.id = id;
    }

    public Person() {
    }

    @Id
    @Column(name = "id")
    private long id;

    @JsonProperty
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
