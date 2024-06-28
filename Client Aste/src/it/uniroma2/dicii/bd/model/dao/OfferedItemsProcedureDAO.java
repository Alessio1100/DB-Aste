package it.uniroma2.dicii.bd.model.dao;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.domain.Item;
import it.uniroma2.dicii.bd.model.domain.ItemList;
import it.uniroma2.dicii.bd.model.domain.User;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OfferedItemsProcedureDAO implements GenericProcedureDAO<ItemList>{

    private static OfferedItemsProcedureDAO offeredItemsInstance = null;

    private OfferedItemsProcedureDAO(){}

    public static OfferedItemsProcedureDAO getInstance() {
        if (offeredItemsInstance == null){
            offeredItemsInstance = new OfferedItemsProcedureDAO();
        }
        return offeredItemsInstance;
    }

    @Override
    public ItemList execute(Object... params) throws DAOException {
        ItemList itemList = new ItemList();
        User user = (User) params[0];

        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call get_offered_items(?)}");
            cs.setString(1, user.getCf());
            boolean bool = cs.execute();

            if (bool){

                ResultSet rs = cs.getResultSet();
                while (rs.next()){

                    Item item = new Item();
                    item.setId(rs.getString(1));
                    item.setDescrizione(rs.getString(2));
                    item.setStato(rs.getString(3));
                    item.setBaseAsta(rs.getFloat(4));
                    item.setDimensioni(rs.getString(5));
                    item.setInizioAsta(rs.getTimestamp(6).toLocalDateTime());
                    item.setTermineAsta(rs.getDate(7));
                    item.setNumeroOfferte(rs.getInt(8));
                    item.setValoreMaxOfferta(rs.getFloat(9));
                    item.setCategoria(rs.getString(10));

                    itemList.addItem(item);
                }
            }

        }
        catch (SQLException e){
            throw new DAOException("Error obtaining items you offered to: "+e.getMessage());
        }
        return itemList;
    }
}
