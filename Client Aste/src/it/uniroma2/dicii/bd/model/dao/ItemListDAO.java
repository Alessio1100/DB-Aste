package it.uniroma2.dicii.bd.model.dao;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.domain.Item;
import it.uniroma2.dicii.bd.model.domain.ItemList;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemListDAO {
    private static ItemListDAO itemListInstance = null;

    private ItemListDAO(){}

    public static ItemListDAO getInstance() {
        if (itemListInstance == null) {
            itemListInstance = new ItemListDAO();
        }
        return itemListInstance;
    }

    public ItemList execute() throws DAOException {
        ItemList itemList = new ItemList();

        try {

            Connection conn = ConnectionFactory.getConnection();
            CallableStatement callableStatement = conn.prepareCall("{call show_open_auction()}");
            boolean flag = callableStatement.execute();

            if (flag) {

                ResultSet resultSet = callableStatement.getResultSet();
                while (resultSet.next()) {
                    Item item = new Item();
                    item.setId(resultSet.getString(1));                              //id;
                    item.setDescrizione(resultSet.getString(2));                     //descrizione;
                    item.setStato(resultSet.getString(3));                           //stato;
                    item.setBaseAsta(resultSet.getFloat(4));                         //baseAsta;
                    item.setDimensioni(resultSet.getString(5));                      //dimensioni;
                    item.setInizioAsta(resultSet.getTimestamp(6).toLocalDateTime()); //inizioAsta;
                    item.setTermineAsta(resultSet.getDate(7));                       //termineAsta;
                    item.setNumeroOfferte(resultSet.getInt(8));                      //numeroOfferte;
                    item.setValoreMaxOfferta(resultSet.getFloat(9));                 //valoreMaxOfferta;
                    item.setCategoria(resultSet.getString(10));                      //categoria;
                    itemList.addItem(item);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return itemList;
    }
}