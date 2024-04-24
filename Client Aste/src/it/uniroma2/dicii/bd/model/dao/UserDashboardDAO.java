package it.uniroma2.dicii.bd.model.dao;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.domain.User;

import java.sql.*;

public class UserDashboardDAO {
    private static UserDashboardDAO userDashboardInstance = null;

    private UserDashboardDAO(){}

    public static UserDashboardDAO getInstance() {
        if(userDashboardInstance == null){
            userDashboardInstance = new UserDashboardDAO();
        }
        return userDashboardInstance;
    }

    public User execute(Object... params) throws SQLException, DAOException {
        String username = (String) params[0];
        User user = null;

        try{
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call get_user_procedure(?)}");
            cs.setString(1,username);
            if(cs.execute()) {
                ResultSet rs = cs.getResultSet();

                if (rs.next()) {
                    user = new User();
                    user.setCf(rs.getString(1));
                    user.setNome(rs.getString(2));
                    user.setCognome(rs.getString(3));
                    user.setDataDiNascita(rs.getDate(4));
                    user.setCittaDiNascita(rs.getString(5));
                    user.setVia(rs.getString(6));
                    user.setCivico(rs.getString(7));
                    user.setComune(rs.getString(8));
                    user.setCap(rs.getString(9));
                    user.setCartaDiCredito(rs.getString(10));
                }
            }
        }
        catch (SQLException e){
            throw new DAOException("Error obtainig user : "+ e.getMessage());
        }

        return user;
    }
}
