package it.uniroma2.dicii.bd.model.dao;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.domain.Category;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class DeleteCategotyProcedureDAO {

    private static DeleteCategotyProcedureDAO deleteCategotyInstance = null;

    private DeleteCategotyProcedureDAO(){}

    public static DeleteCategotyProcedureDAO getInstance() {
        if (deleteCategotyInstance == null){
            deleteCategotyInstance = new DeleteCategotyProcedureDAO();
        }
        return deleteCategotyInstance;
    }

    public boolean execute(Object... params) throws DAOException {
        Category category = (Category) params[0];

        try {
            Connection connection = ConnectionFactory.getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call delete_category(?)}");
            callableStatement.setString(1, category.getNome());
            callableStatement.execute();
        }
        catch (SQLException e){
            throw new DAOException("Error deleting category: "+ e.getMessage());
        }
        return true;
    }
}
