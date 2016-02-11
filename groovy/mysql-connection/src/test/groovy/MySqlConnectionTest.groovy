import de.gesellix.docker.client.DockerClientImpl
import groovy.sql.Sql
import org.junit.Test

/**
 * Created by feliperojas on 10/8/15.
 */
class MySqlConnectionTest {

    @Test
    void testMySqlConnection() {
        def sql = Sql.newInstance('jdbc:mysql://192.168.99.100/mysql', 'root', 'root', 'com.mysql.jdbc.Driver')
        sql.eachRow('show tables') { row ->
            println row[0]
        }
        sql.execute("insert into Person (id) values (1)")
        sql.eachRow('select * from Person') {
            println it
        }

        System.setProperty("docker.host", "192.168.99.100")
        System.setProperty("docker.cert.path", "/Users/${System.getProperty('user.name')}/.docker/machine/machines/default")

        def dockerClient = new DockerClientImpl(dockerHost: "tcp://192.168.99.100:2376")
        println dockerClient.images()
    }
}
