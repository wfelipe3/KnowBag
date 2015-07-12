package knowbag.batch.restresources;

import org.junit.Test;

import static knowbag.batch.restresources.HttpMethod.GET;
import static knowbag.batch.restresources.RestResourceBuilders.newResource;
import static knowbag.batch.restresources.RestResourceBuilders.newRoot;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by feliperojas on 6/10/15.
 */
public class RestResourceTest {

    @Test
    public void givenEmptyRestResource_WhenGetIsCall_ThenReturnNotFoundCommand() {
        RestResource resource = newRoot("api").build();
        Response response = resource.execute(RestPath.newRestPath("api/products", GET), new Request());
        assertThat(response, is(new Response(404)));
    }

    @Test
    public void givenRootResourceWithMultipleNames_WhenExecuteIsCall_ThenReturnNotFoundResponse() {
        RestResource resource = newRoot("api", "rest").build();
        Response response = resource.execute(RestPath.newRestPath("rest/products", GET), new Request());
        assertThat(response, is(new Response(404)));
    }

    @Test
    public void givenRootWithSubResource_WhenNotFoundRootResourceIsExecuted_ThenReturnNotFound() {
        RestResource resource = newRoot("api", "rest").withResource(newResource("products")).build();
        Response response = resource.execute(RestPath.newRestPath("other", GET), new Request());
        assertThat(response, is(new Response(404)));
    }

    @Test
    public void givenRootWithSubResource_WhenSubResourceDoesNotHaveMethods_ThenReturnMethodNotAllowed() {
        RestResource resource = newRoot("api", "rest").withResource(newResource("products")).build();
        Response response = resource.execute(RestPath.newRestPath("rest/products", GET), new Request());
        assertThat(response, is(new Response(405)));
    }
}
