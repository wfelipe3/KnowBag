package knowbag.batch.restresources;

/**
 * Created by feliperojas on 6/10/15.
 */
public class EmptyRestResource implements RestResource {

    @Override
    public <T> T execute(RestPath path, Request request) {
        return (T) new Response(404);
    }

}
