package it.uniroma2.dicii.bd.model.dao;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.domain.Category;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class ModifyCategoryProcedureDAO implements GenericProcedureDAO<Boolean>{

    private static ModifyCategoryProcedureDAO modifyCategoryInstance = null;

    private ModifyCategoryProcedureDAO(){}

    public static ModifyCategoryProcedureDAO getInstance() {
        if (modifyCategoryInstance == null){
            modifyCategoryInstance = new ModifyCategoryProcedureDAO();
        }
        return modifyCategoryInstance;
    }

    public Boolean execute(Object... params) throws DAOException {
        Category category = (Category) params[0];

        try{

            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call modify_category(?,?)}");
            cs.setString(1, category.getNome());
            cs.setString(2, category.getCategoriaGenitore());
            cs.execute();

        } catch (SQLException e) {
            throw new DAOException("Error in editing Category: " + e.getMessage());
        }

        return true;
    }
}
