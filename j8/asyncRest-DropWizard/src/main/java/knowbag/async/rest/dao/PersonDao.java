package knowbag.async.rest.dao;

import io.dropwizard.hibernate.AbstractDAO;
import knowbag.async.rest.model.Person;
import org.hibernate.SessionFactory;

import java.util.List;

public class PersonDao extends AbstractDAO<Person> {

    public PersonDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Person findById(Long id) {
        return get(id);
    }

    public long create(Person person) {
        return persist(person).getId();
    }

    public List<Person> findAll() {
        return list(namedQuery("findAll"));
    }
}
