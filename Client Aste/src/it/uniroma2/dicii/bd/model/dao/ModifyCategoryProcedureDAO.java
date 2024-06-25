package it.uniroma2.dicii.bd.model.dao;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.domain.Category;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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
        List<Object> modifyList = (List<Object>) params[0];
        Category category = (Category) modifyList.get(0);
        String newName = (String) modifyList.get(1);

        try{

            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call modify_category_procedure(?,?)}");
            cs.setString(1, category.getNome());
            cs.setString(2, newName);
            cs.execute();

        } catch (SQLException e) {
            throw new DAOException("Error in editing Category: " + e.getMessage());
        }

        return true;
    }
}
