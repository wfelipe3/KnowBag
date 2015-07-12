package knowbag.batch.restresources;

/**
 * Created by feliperojas on 6/10/15.
 */
public class RegularRestResource implements RestResource {

    public RegularRestResource(String name) {

    }

    @Override
    public <T> T execute(RestPath path, Request request) {
        return null;
    }
}
