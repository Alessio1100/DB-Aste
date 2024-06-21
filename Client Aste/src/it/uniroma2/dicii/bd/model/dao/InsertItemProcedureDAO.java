package it.uniroma2.dicii.bd.model.dao;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.domain.Item;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class InsertItemProcedureDAO implements GenericProcedureDAO<Boolean>{

    private static InsertItemProcedureDAO insertItemInstance = null;

    private InsertItemProcedureDAO(){}

    public static InsertItemProcedureDAO getInstance() {
        if (insertItemInstance == null){
            insertItemInstance = new InsertItemProcedureDAO();
        }
        return  insertItemInstance;
    }
    @Override
    public Boolean execute(Object... params) throws DAOException {
        Item item = (Item) params[0];

        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call insert_item_procedure(?,?,?,?,?,?)}");
            cs.setString(1,item.getDescrizione());
            cs.setString(2,item.getStato());
            cs.setFloat(3,item.getBaseAsta());
            cs.setString(4,item.getDimensioni());
            cs.setInt(5,item.getDurataAsta());
            cs.setString(6,item.getCategoria());
            cs.execute();

        }
        catch (SQLException e){
            throw new DAOException("Error inserting the new item : "+e.getMessage());
        }

        return true;
    }

}
