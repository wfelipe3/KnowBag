package knowbag.batch.restresources;

/**
 * Created by feliperojas on 6/10/15.
 */
public interface RestResourceBuilder {

    RestResource build();

    RestResourceBuilder withResource(RestResourceBuilder resource);

    String getName();
}
