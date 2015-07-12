package knowbag.batch.restresources;

/**
 * Created by feliperojas on 6/10/15.
 */
public interface RestResource {

    <T>T execute(RestPath path, Request request);
}
