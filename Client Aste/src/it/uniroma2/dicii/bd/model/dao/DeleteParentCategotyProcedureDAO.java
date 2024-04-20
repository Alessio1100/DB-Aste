package it.uniroma2.dicii.bd.model.dao;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.domain.Category;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class DeleteParentCategotyProcedureDAO implements GenericProcedureDAO<Boolean>{

    private static DeleteParentCategotyProcedureDAO deleteParentCategotyInstance = null;

    private DeleteParentCategotyProcedureDAO(){}

    public static DeleteParentCategotyProcedureDAO getInstance() {
        if (deleteParentCategotyInstance == null){
            deleteParentCategotyInstance = new DeleteParentCategotyProcedureDAO();
        }
        return deleteParentCategotyInstance;
    }

    @Override
    public Boolean execute(Object... params) throws DAOException {
        Category category = (Category) params[0];

        try {
            Connection connection = ConnectionFactory.getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call delete_parent_category(?)}");
            callableStatement.setString(1, category.getNome());
            callableStatement.execute();
        }
        catch (SQLException e){
            throw new DAOException("Error deleting parent category "+ e.getMessage());
        }
        return true;
    }
}
