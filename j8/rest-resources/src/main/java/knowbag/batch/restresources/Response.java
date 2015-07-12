package knowbag.batch.restresources;

/**
 * Created by feliperojas on 6/10/15.
 */
public class Response {

    private int status;

    public Response(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Response response = (Response) o;

        if (status != response.status) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return status;
    }

    @Override
    public String toString() {
        return "Response{" +
                "status=" + status +
                '}';
    }
}

