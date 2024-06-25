package it.uniroma2.dicii.bd.model.dao;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.domain.Offer;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class MakeOfferProcedureDAO implements GenericProcedureDAO<Boolean>{

    private static MakeOfferProcedureDAO makeOfferInstance = null;

    private MakeOfferProcedureDAO(){}

    public static MakeOfferProcedureDAO getInstance() {
        if (makeOfferInstance == null){
            makeOfferInstance = new MakeOfferProcedureDAO();
        }
        return makeOfferInstance;
    }

    @Override
    public Boolean execute(Object... params) throws DAOException {
        Boolean type;
        Offer offer = (Offer) params[0];

        if (offer.getValoreMassimoAutomatico() == null){
            type = makeOffer(offer);
        }
        else{
            type = makeAutomaticOffer(offer);
        }
        return type;
    }

    private Boolean makeAutomaticOffer(Offer offer) throws DAOException {
        try {
            Connection connection = ConnectionFactory.getConnection();
            CallableStatement cs = connection.prepareCall("{call make_automatic_offer(?,?,?,?)}");
            cs.setString(1,offer.getUtente());
            cs.setFloat(2,offer.getImporto());
            cs.setString(3,offer.getOggetto());
            cs.setFloat(4,offer.getValoreMassimoAutomatico());
            cs.execute();
        }
        catch (SQLException e){
            throw new DAOException("Offer automatic insertion error: "+e.getMessage());
        }
        return true;
    }

    private Boolean makeOffer(Offer offer) throws DAOException {
  
        try {
            Connection connection = ConnectionFactory.getConnection();
            CallableStatement cs = connection.prepareCall("{call make_offer(?,?,?)}");
            cs.setString(1,offer.getUtente());
            cs.setFloat(2,offer.getImporto());
            cs.setString(3,offer.getOggetto());
            cs.execute();
        }
        catch (SQLException e) {
            throw new DAOException("Offer insertion error: "+e.getMessage());
        }
        return true;
    }

}
