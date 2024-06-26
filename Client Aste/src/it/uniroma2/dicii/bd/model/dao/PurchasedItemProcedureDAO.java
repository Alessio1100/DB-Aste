package it.uniroma2.dicii.bd.model.dao;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.domain.Item;
import it.uniroma2.dicii.bd.model.domain.ItemList;
import it.uniroma2.dicii.bd.model.domain.User;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PurchasedItemProcedureDAO implements GenericProcedureDAO<ItemList> {

    private static PurchasedItemProcedureDAO purchasedItemInstance = null;

    private PurchasedItemProcedureDAO(){}

    public static PurchasedItemProcedureDAO getInstance() {
        if (purchasedItemInstance == null){
            purchasedItemInstance = new PurchasedItemProcedureDAO();
        }
        return purchasedItemInstance;
    }

    @Override
    public ItemList execute(Object... params) throws DAOException {
        ItemList itemList = new ItemList();
        User user = (User) params[0];

        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call get_purchased_items(?)}");
            cs.setString(1, user.getCf());
            boolean bool = cs.execute();

            if (bool){

                ResultSet rs = cs.getResultSet();
                while (rs.next()){

                    Item item = new Item();
                    item.setDescrizione(rs.getString(1));
                    item.setStato(rs.getString(2));
                    item.setDimensioni(rs.getString(3));
                    item.setTermineAsta(rs.getDate(4));
                    item.setPrezzoDiVendita(rs.getFloat(5));
                    item.setCategoria(rs.getString(6));


                    itemList.addItem(item);
                }
            }

        }
        catch (SQLException e){
            throw new DAOException("Error obtaining items you purchased: "+e.getMessage());
        }
        return itemList;
    }


}
