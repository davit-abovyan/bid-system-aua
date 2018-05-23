package aua.entity;

import aua.DAO.DBManager;
import aua.model.Item;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.lang.reflect.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@ManagedBean(name = "items")
@SessionScoped
public class ItemsBean {

    private List<Item> items;

    public List<Item> itemsList() {
        PreparedStatement prepared = null;
        ResultSet rs = null;
        List<Item> items = new LinkedList<>();
        try {
            String query = "select * from item";
            prepared = DBManager.getInstance().getConnection().prepareStatement(query);
            rs = prepared.executeQuery();
            while (rs.next()) {
                items.add(new Item(
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getString("url"))
                );

            }
            this.items = items;
            return items;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UnknownError();
        } finally {
            DBManager.getInstance().closeConn(prepared, null);
        }
    }

}
