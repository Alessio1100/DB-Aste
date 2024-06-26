package it.uniroma2.dicii.bd.model.dao;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.domain.Admin;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class AdminRegistrationProcedureDAO implements GenericProcedureDAO<Boolean>{

    private static AdminRegistrationProcedureDAO adminRegisterDAO = null;

    private AdminRegistrationProcedureDAO(){}

    public static AdminRegistrationProcedureDAO getInstance() {
        if (adminRegisterDAO == null) {
            adminRegisterDAO = new AdminRegistrationProcedureDAO();
        }
        return adminRegisterDAO;
    }

    public Boolean execute(Object... params) throws DAOException {
        Admin admin = (Admin) params[0];

        try{
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{ call admin_registration_procedure(?,?)}");
            cs.setString(1, admin.getUsername());
            cs.setString(2, admin.getPassword());
            cs.executeQuery();
        }
        catch (SQLException e){
            throw new DAOException("Admin registration error: "+ e.getMessage());
        }

        return true;
    }
}
