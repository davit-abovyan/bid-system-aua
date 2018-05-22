
package aua.DAO;

public interface Item {
    /**
     * add auxion item
     * @param object
     */
    void add(Item object);
    boolean edit(Item object);
    Item read(String id);
    boolean delete(String id);
}
