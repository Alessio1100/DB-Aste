package it.uniroma2.dicii.bd.model.dao;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.domain.CreditCard;
import it.uniroma2.dicii.bd.model.domain.User;
import java.sql.*;

public class UserRegistrationProcedureDAO implements GenericProcedureDAO<Boolean> {

    private static UserRegistrationProcedureDAO adminRegisterDAO = null;

    private UserRegistrationProcedureDAO(){};

    public static UserRegistrationProcedureDAO getInstance() {
        if (adminRegisterDAO == null) {
            adminRegisterDAO = new UserRegistrationProcedureDAO();
        }
        return adminRegisterDAO;

    }

    @Override
    public Boolean execute(Object... params) throws DAOException {
        User user = (User) params[0];
        CreditCard creditCard = (CreditCard) params[1];


        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call user_registration_procedure(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            cs.setString(1, user.getCf());
            cs.setString(2, user.getNome());
            cs.setString(3, user.getCognome());
            cs.setDate(4, (Date) user.getDataDiNascita());
            cs.setString(5, user.getCittaDiNascita());
            cs.setString(6, user.getVia());
            cs.setString(7, user.getCivico());
            cs.setString(8, user.getComune());
            cs.setString(9, user.getCap());
            cs.setString(10, user.getCartaDiCredito());
            cs.setString(11, creditCard.getNomeIntestatario());
            cs.setString(12, creditCard.getCognomeIntestatario());
            cs.setString(13, creditCard.getDataDiScadenza());
            cs.setString(14, creditCard.getCvv());
            cs.executeQuery();
        } catch (SQLException e) {
            throw new DAOException("User registration error: " + e.getMessage());
        }

        return true;
    }
}
