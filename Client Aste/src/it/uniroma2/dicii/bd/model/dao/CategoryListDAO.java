package it.uniroma2.dicii.bd.model.dao;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.domain.Category;
import it.uniroma2.dicii.bd.model.domain.CategoryList;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryListDAO implements GenericProcedureDAO<CategoryList>{

    private static CategoryListDAO categoryListInstance = null;

    private CategoryListDAO(){}

    public static CategoryListDAO getInstance(){
        if (categoryListInstance == null){
            categoryListInstance = new CategoryListDAO();
        }
        return categoryListInstance;
    }

    @Override
    public CategoryList execute(Object... params) throws DAOException {
        CategoryList categoryList = new CategoryList();

        try {
            Connection connection = ConnectionFactory.getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call show_categories()}");
            boolean flag = callableStatement.execute();

            if (flag){
                ResultSet rs = callableStatement.getResultSet();
                while (rs.next()){

                    Category category = new Category();
                    category.setNome(rs.getString(1));
                    category.setCategoriaGenitore(rs.getString(2));
                    if (category.getCategoriaGenitore()==null){
                        category.setCategoriaGenitore("No parent Category avaliable");
                    }
                    categoryList.addCategory(category);
                }

            }
        } catch (SQLException e) {
            throw new DAOException("Error in Category list : "+ e.getMessage());
        }
        return categoryList;
    }
}
