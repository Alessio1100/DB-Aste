package it.uniroma2.dicii.bd.view;

import it.uniroma2.dicii.bd.model.domain.Category;
import it.uniroma2.dicii.bd.model.domain.CategoryList;
import it.uniroma2.dicii.bd.model.domain.Item;
import it.uniroma2.dicii.bd.model.domain.ItemList;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminView {
    public static int showMenu() throws IOException {
        System.out.println(" ");
        System.out.println("\u001B[34m"+"*********************************"+"\u001B[0m");
        System.out.println("\u001B[34m"+"****     ADMIN DASHBOARD     ****"+"\u001B[0m");
        System.out.println("\u001B[34m"+"*********************************\n"+"\u001B[0m");
        System.out.println("*** What should I do for you? ***\n");
        System.out.println("1) Insert a new item in auction");
        System.out.println("2) Insert a new Category");
        System.out.println("3) Modify a Category ");
        System.out.println("4) Delete a Category");
        System.out.println("5) Modify a Parent Category ");
        System.out.println("6) Delete a Parent Category");
        System.out.println("7) Display all auctions");
        System.out.println("8) Display all Categories");
        System.out.println("9) Quit");
        System.out.println("");


        Scanner input = new Scanner(System.in);
        int choice = 0;
        while (true) {
            System.out.print("Please enter your choice: ");
            System.out.println("");
            choice = input.nextInt();
            if (choice >= 1 && choice <= 9) {
                break;
            }
            System.out.println("Invalid option");
        }

        return choice;
    }

    public static void showItemsInAuctionView(ItemList itemList) {

        List<String> fieldsToPrint = new ArrayList<>();
        fieldsToPrint.add("id");
        fieldsToPrint.add("descrizione");
        fieldsToPrint.add( "stato");
        fieldsToPrint.add("baseAsta");
        fieldsToPrint.add("dimensioni");
        fieldsToPrint.add("inizioAsta");
        fieldsToPrint.add("termineAsta");
        fieldsToPrint.add("numeroOfferte");
        fieldsToPrint.add("valoreMaxOfferta");
        fieldsToPrint.add("categoria");

        ApplicationView.printObjectTable(itemList.getList(), System.out, fieldsToPrint);
    }

    public static void showCategoriesView(CategoryList categoryList) {

        List<String> fieldsToPrint = new ArrayList<>();
        fieldsToPrint.add("nome");
        fieldsToPrint.add(" categoriaGenitore");

        ApplicationView.printObjectTable(categoryList.getList(), System.out, fieldsToPrint);
    }

    public static Category deleteParentCategoryView() throws IOException {
        Category category = new Category();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter the name of the category whose parent category you want to delete: ");
        category.setNome(reader.readLine());

        return category;
    }

    public static Category deleteCategoryView() throws IOException {

        Category category = new Category();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter the name of the category to delete: ");
        category.setNome(reader.readLine());

        return category;
    }

    public static Category modifyParentCategoryView() throws IOException {

        Category category = new Category();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter the name of the category to change the parent category: ");
        category.setNome(reader.readLine());
        System.out.println("Please enter the parent category to modify: ");
        category.setCategoriaGenitore(reader.readLine());

        return category;
    }

    public static List<Object> modifyCategoryView() throws IOException {

        Category category = new Category();
        List<Object> list = new ArrayList<>();
        String newName;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter the name of the category you want to modify: ");
        category.setNome(reader.readLine());
        System.out.println("Please enter the new name of the category: ");
        newName = reader.readLine();
        list.add(category);
        list.add(newName);

        return list;
    }

    public static Category insertCategoryView() throws IOException {

        Category category = new Category();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Scanner input = new Scanner(System.in);
        boolean flag = false;
        int choice;

        System.out.println("Please enter the name of the category to insert: ");
        category.setNome(reader.readLine());
        System.out.println("The category has a Parent Category?");
        System.out.println("1) Yes");
        System.out.println("2) No");

        do {
            System.out.println("Please enter your choice: ");
            choice = input.nextInt();
            if (choice<1 || choice>2){
                flag=true;
                System.out.println("Invalid Option");
            }
            else{
                flag = false;
            }
        } while (flag);

        if (choice == 1){
            System.out.println("Please enter the Parent Category name: ");
            category.setCategoriaGenitore(reader.readLine());
        }
        return category;
    }

    public static Item insertItemInAuctionView() throws IOException {

        Scanner input = new Scanner(System.in);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Item item = new Item();

        System.out.println("Enter the description of the object to be auctioned: ");
        item.setDescrizione(reader.readLine());
        System.out.println("Enter the status of the object to be auctioned: (in good condition, ...)");
        item.setStato(reader.readLine());
        System.out.println("Enter the basic auction price: ");
        item.setBaseAsta(input.nextFloat());
        System.out.println("Enter a description of the size of the object to be auctioned [DxDxD]: ");
        item.setDimensioni(reader.readLine());
        System.out.println("Enter the duration of the auction: (the duration indicates the days that the auction should last, max 7 days)");
        item.setDurataAsta(input.nextInt());
        System.out.println("Enter the category of the object to be auctioned: ");
        item.setCategoria(reader.readLine());

        return item;
    }

}

