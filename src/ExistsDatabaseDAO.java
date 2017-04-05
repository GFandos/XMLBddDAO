/**
 * Created by 47989768s on 03/04/17.
 */
public interface ExistsDatabaseDAO {
    /**
     * Constructor without params.
     */
    void XmlDatabase();

    /**
     *
     * Constructor with params.
     *
     * @param _ip -> ip of the database.
     * @param _user -> user for login.
     * @param _pass -> password for login.
     * @param _driver -> driver used to implement the database.
     * @param _port -> port where we will connect to the database.
     */
    void XmlDatabase(String _ip, String _user, String _pass, String _driver, String _port);

    /**
     *
     * Creates a collection from a given collectionName.
     *
     * @param collectionName -> Name for the new collection.
     */
    void createCollection(String collectionName);

    /**
     *
     * Adds a resource to a given collection.
     *
     * @param collectionName -> Name of the collection where we want to add the resource.
     * @param filename -> Name of the file with the resource's information.
     * @param resourceName -> Name of the new resource.
     */
    void addResource(String collectionName, String filename, String resourceName);

    /**
     *
     * Executes a given Xpath or Xquery query.
     *
     * @param query -> Query to execute.
     */
    void executeQuery(String query);

    /**
     *
     * Deletes a resource from a given collection.
     *
     * @param collectionName -> Name of the collection where we want to delete the resource.
     * @param resourceName -> Name of the resource we are going to remove from the collection.
     */
    void deleteResource(String collectionName, String resourceName);

    /**
     *
     * Deletes a given collection from the database
     *
     * @param collectionName -> Name of the collection we are going to drop.
     */
    void deleteCollection(String collectionName);

}
