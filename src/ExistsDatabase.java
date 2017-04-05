import net.xqj.exist.ExistXQDataSource;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;

import javax.xml.xquery.*;
import java.io.File;

/**
 * Created by 47989768s on 03/04/17.
 */
public class ExistsDatabase {

    private String IP;
    private String user;
    private String pass;
    private String driver;
    private String port;
    private String URI;

    public ExistsDatabase() {}

    public ExistsDatabase(String _ip, String _user, String _pass, String _driver, String _port) {

        IP = _ip;
        user = _user;
        pass = _pass;
        driver = _driver;
        port = _port;
        URI = "xmldb:exist://"+ IP +":" + port + "/exist/xmlrpc/db";

    }

    public void createCollection(String collectionName) throws ClassNotFoundException, IllegalAccessException, InstantiationException, XMLDBException {

        Class cl = Class.forName(driver);

        Database database = (Database) cl.newInstance();
        database.setProperty("create-database", "true");

        DatabaseManager.registerDatabase(database);

        Collection col = DatabaseManager.getCollection(URI, user, pass);
        CollectionManagementService cms = (CollectionManagementService) col.getService("CollectionManagementService", "1.0");
        cms.createCollection("/" + collectionName);

        System.out.println("Collection " + collectionName + " created.");

    }

    public void addResource(String collectionName, String filename, String resourceName) throws ClassNotFoundException, XMLDBException, IllegalAccessException, InstantiationException, XQException {

        File f = new File(filename);

        Collection col = DatabaseManager.getCollection(URI + "/" + collectionName, user, pass);

        Resource res = col.createResource(resourceName, "XMLResource");
        res.setContent(f);

        col.storeResource(res);

        System.out.println("Resource " + resourceName + " added to collection " + collectionName + ".");

    }

    public void executeQuery(String query) throws XQException {

        XQDataSource source = new ExistXQDataSource();
        source.setProperty("serverName", IP);
        source.setProperty("port", port);
        XQConnection connection = source.getConnection();

        XQPreparedExpression expression = connection.prepareExpression(query);
        XQResultSequence result = expression.executeQuery();

        String res;
        while(result.next()) {

            res = result.getItemAsString(null);
            res = res.split("\"")[1];
            System.out.println("\t\t\t - " + res);

        }

        connection.close();

        System.out.println("Query correctly executed.");
    }

    public void deleteResource(String collectionName, String resourceName) throws XMLDBException {

        Collection col = DatabaseManager.getCollection(URI + "/" + collectionName, user, pass);

        Resource resource = col.getResource(resourceName);

        col.removeResource(resource);

        System.out.println("Resource " + resourceName + " removed.");

    }

    public void deleteCollection(String collectionName) throws XQException, ClassNotFoundException, XMLDBException, IllegalAccessException, InstantiationException {

        Class cl = Class.forName(driver);

        Database database = (Database) cl.newInstance();
        database.setProperty("create-database", "true");

        DatabaseManager.registerDatabase(database);

        Collection col = DatabaseManager.getCollection(URI, user, pass);
        CollectionManagementService cms = (CollectionManagementService) col.getService("CollectionManagementService", "1.0");
        cms.removeCollection(collectionName);

        System.out.println("Collection " + collectionName + " removed.");

    }


    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
