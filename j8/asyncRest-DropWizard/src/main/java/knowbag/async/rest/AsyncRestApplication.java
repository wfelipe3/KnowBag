package knowbag.async.rest;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import knowbag.async.rest.dao.PersonDao;
import knowbag.async.rest.model.Person;
import knowbag.async.rest.resource.AsyncRestResource;

/**
 * Created by feliperojas on 2/4/16.
 */
public class AsyncRestApplication extends Application<AsyncRestConfiguration> {

    private final HibernateBundle<AsyncRestConfiguration> hibernate = new HibernateBundle<AsyncRestConfiguration>(Person.class) {
        public DataSourceFactory getDataSourceFactory(AsyncRestConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    public static void main(String[] args) throws Exception {
        new AsyncRestApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<AsyncRestConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate);
    }

    @Override
    public void run(AsyncRestConfiguration configuration, Environment environment) throws Exception {
        final PersonDao dao = new PersonDao(hibernate.getSessionFactory());
        environment.jersey().register(new AsyncRestResource(dao));
    }

    @Override
    public String getName() {
        return "async-rest";
    }
}
