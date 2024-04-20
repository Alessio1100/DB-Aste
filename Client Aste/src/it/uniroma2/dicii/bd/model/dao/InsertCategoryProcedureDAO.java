package it.uniroma2.dicii.bd.model.dao;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.domain.Category;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class InsertCategoryProcedureDAO implements GenericProcedureDAO<Boolean>{

    private static InsertCategoryProcedureDAO insertCategoryinstance = null;

    private InsertCategoryProcedureDAO(){}

    public static InsertCategoryProcedureDAO getInstance() {
        if (insertCategoryinstance == null){
            insertCategoryinstance = new InsertCategoryProcedureDAO();
        }
        return insertCategoryinstance;
    }

    public Boolean execute(Object... params) throws DAOException {
        Boolean bool;
        Category category = (Category) params[0];

        if(category.getCategoriaGenitore() == null){
            bool = insertCategory(category);
        }
        else{
            bool = insertParentCategory(category);
        }
        return bool;
    }

    private Boolean insertParentCategory(Category category) throws DAOException {
        try{

            Connection connection = ConnectionFactory.getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call insert_parent_category(?,?)}");
            callableStatement.setString(1, category.getNome());
            callableStatement.setString(2, category.getCategoriaGenitore());
            callableStatement.execute();

        }
        catch (SQLException e) {
            throw new DAOException("Error entering category with parent: " + e.getMessage());
        }
        return true;

    }

    private Boolean insertCategory(Category category) throws DAOException {
        try{

            Connection connection = ConnectionFactory.getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call insert_category(?)}");
            callableStatement.setString(1, category.getNome());
            callableStatement.execute();

        }
        catch (SQLException e) {
            throw new DAOException("Error entering category without parent: " + e.getMessage());
        }
        return true;

    }
}
