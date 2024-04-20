package it.uniroma2.dicii.bd.controller;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.dao.*;
import it.uniroma2.dicii.bd.model.domain.*;
import it.uniroma2.dicii.bd.view.AdminView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminController implements Controller {

    @Override
    public void start() {
        try{
            ConnectionFactory.changeRole(Role.AMMINISTRATORE);
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        adminChoice();
    }

    public void adminChoice() {

        while(true) {
            int choice;
            try {
                choice = AdminView.showMenu();
            }
            catch(IOException e) {
                throw new RuntimeException(e);
            }

            switch(choice) {
                case 1 -> insertItemInAuction();         // A1
                case 2 -> insertCategory();       // A2
                case 3 -> modifyCategory();          // A3
                case 4 -> deleteCategory();           // A4
                case 5 -> modifyParentCategory();  // A3bis
                case 6 -> deleteParentCategory();   // A4bis
                case 7 -> showItemsInAuction();             // aggiunta funzionalita
                case 8 -> showCategories();        // aggiunta funzionalita
                case 9 -> System.exit(0);
                default -> throw new RuntimeException("Invalid choice");
            }
        }
    }

    private void showCategories() {
        CategoryList categoryList = null;
        try{
            CategoryListDAO categoryListDAO = CategoryListDAO.getInstance();
            categoryList = categoryListDAO.execute();
        }
        catch (DAOException e){
            System.out.println(e.getMessage());
        }

        AdminView.showCategoriesView(categoryList);
    }
    private void showItemsInAuction() {
        ItemList itemList = null;
        try{
            ItemListDAO itemListDAO = ItemListDAO.getInstance();
            itemList = itemListDAO.execute();
        }
        catch (DAOException e){
            System.out.println(e.getMessage());
        }

        AdminView.showItemsInAuctionView(itemList);
    }
    private void deleteParentCategory() {
        Category category = new Category();
        boolean bool = false;

        try{
            category = AdminView.deleteParentCategoryView();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try{
            DeleteParentCategotyProcedureDAO deleteParentCategotyProcedureDAO = DeleteParentCategotyProcedureDAO.getInstance();
            bool = deleteParentCategotyProcedureDAO.execute(category);

        }
        catch (DAOException e){
            System.out.println(e.getMessage());
        }

        if(bool){
            System.out.println("Parent Category successfully deleted!");
        }
    }
    private void deleteCategory() {
        Category category = new Category();
        boolean bool = false;

        try{
            category = AdminView.deleteCategoryView();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try{
            DeleteCategotyProcedureDAO deleteCategotyProcedureDAO = DeleteCategotyProcedureDAO.getInstance();
            bool = deleteCategotyProcedureDAO.execute(category);

        }
        catch (DAOException e){
            System.out.println(e.getMessage());
        }

        if(bool){
            System.out.println("Category successfully deleted!");
        }
    }
    private void modifyParentCategory() {
        Category category = new Category();
        boolean flag = false;

        try {
            category = AdminView.modifyParentCategoryView();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            ModifyParentCategoryProcedureDAO modifyParentCategoryProcedureDAO = ModifyParentCategoryProcedureDAO.getInstance();
            flag = modifyParentCategoryProcedureDAO.execute(category);
        }
        catch (DAOException e){
            System.out.println(e.getMessage());
        }

        if(flag){
            System.out.println("Parent Category successfully modified!");
        }
    }
    private void modifyCategory() {
        List<Object> modify = new ArrayList<>();
        boolean flag = false;

        try {
            modify = AdminView.modifyCategoryView();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            ModifyCategoryProcedureDAO modifyCategoryProcedureDAO = ModifyCategoryProcedureDAO.getInstance();
            flag = modifyCategoryProcedureDAO.execute(modify);
        }
        catch (DAOException e){
            System.out.println(e.getMessage());
        }

        if(flag){
            System.out.println("Category successfully modified!");
        }
    }
    private void insertCategory() {
        Category category = new Category();
        boolean bool = false;

        try{

            category = AdminView.insertCategoryView();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {

            InsertCategoryProcedureDAO insertCategoryProcedureDAO = InsertCategoryProcedureDAO.getInstance();
            bool = insertCategoryProcedureDAO.execute(category);

        }catch (DAOException e){
           System.out.println(e.getMessage());
        }

        if(bool){
            System.out.println("Category successfully entered!");
        }


    }
    private void insertItemInAuction() {
        Item item =new Item();
        boolean bool = false;
        try {
            item = AdminView.insertItemInAuctionView();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        try {
            InsertItemProcedureDAO insertItemProcedureDAO = InsertItemProcedureDAO.getInstance();
            bool = insertItemProcedureDAO.execute(item);
        }
        catch (DAOException e){
            System.out.println(e.getMessage());
        }
        if (bool){
            System.out.println("Item entered successfully");
        }

    }

}
