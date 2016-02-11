package knowbag.async.rest.resource;

import javaslang.control.Try;
import knowbag.async.rest.dao.PersonDao;

import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.CompletableFuture.supplyAsync;

/**
 * Created by feliperojas on 2/4/16.
 */
@Path("async")
public class AsyncRestResource {

    private final PersonDao dao;
    private static AtomicInteger counter = new AtomicInteger(1);

    public AsyncRestResource(PersonDao dao) {
        this.dao = dao;
    }

    @GET
    @Path("hello-world")
    @Produces("application/json")
    public void sayHelloAsync(@Suspended AsyncResponse response, @QueryParam("time") int time) {
        supplyAsync(() -> Try.of(() -> {
            long start = System.currentTimeMillis();
            Thread.sleep(time);
            return start;
        }))
                .thenAccept(t -> response.resume(System.currentTimeMillis() - t.get()))
                .exceptionally(t -> {
                    response.resume(t.getMessage());
                    return null;
                });
    }

    @GET
    @Path("hello-worlds-sync")
    @Produces("application/json")
    public long sayHelloAsync(@QueryParam("time") long time) {
        long start = System.currentTimeMillis();
        Try.run(() -> Thread.sleep(time));
        return System.currentTimeMillis() - start;
    }
}
