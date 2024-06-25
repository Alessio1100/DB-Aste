package it.uniroma2.dicii.bd.model.dao;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.domain.Category;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class ModifyParentCategoryProcedureDAO implements GenericProcedureDAO<Boolean>{

    private static ModifyParentCategoryProcedureDAO modifyParentCategoryInstance = null;

    private ModifyParentCategoryProcedureDAO(){}

    public static ModifyParentCategoryProcedureDAO getInstance() {
        if (modifyParentCategoryInstance == null){
            modifyParentCategoryInstance = new ModifyParentCategoryProcedureDAO();
        }
        return modifyParentCategoryInstance;
    }

    public Boolean execute(Object... params) throws DAOException {
        Category category = (Category) params[0];

        try{

            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call modify_parent_category_procedure(?,?)}");
            cs.setString(1, category.getNome());
            cs.setString(2, category.getCategoriaGenitore());
            cs.execute();

        } catch (SQLException e) {
            throw new DAOException("Error in editing Parent Category: " + e.getMessage());
        }

        return true;
    }
}
