package aua.DAO.impl;

import aua.DAO.DBManager;
import aua.DAO.ItemDAO;
import aua.model.Item;

import javax.ejb.Remote;
import javax.ejb.Stateful;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Stateful
@Remote(ItemDAO.class)
public class ItemDAOJDBC implements ItemDAO {

    @Override
    public void add(Item object) {
        PreparedStatement preparedStmt = null;  //for SQL stmt
        ResultSet rs = null;
        try {
            String query = "insert into items(name, description, price, picture_url) values (?, ?, ?, ?)";
            preparedStmt = DBManager.getInstance().getConnection().prepareStatement(query);
            preparedStmt.setString(1, object.getName());
            preparedStmt.setString(2, object.getDescription());
            preparedStmt.setDouble(3, object.getPrice());
            preparedStmt.setString(4, object.getPicture_url());
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UnknownError();
        } finally {
            DBManager.getInstance().closeConn(preparedStmt, null);
        }
    }

    @Override
    public boolean edit(Item object) {
        return false;
    }

    @Override
    public ItemDAO read(String id) {
        return null;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }
}
