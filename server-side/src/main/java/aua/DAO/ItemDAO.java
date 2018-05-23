package aua.DAO;

import aua.model.Item;

public interface ItemDAO {
    /**
     * add auction item
     * @param object
     */
    void add(Item object);

    /**
     * edit auction item
     * @param object
     * @return
     */
    boolean edit(Item object);

    /**
     * get auctioon item
     * @param id
     * @return
     */
    ItemDAO read(String id);

    /**
     * remove auction item
     * @param id
     * @return
     */
    boolean delete(String id);
}
