package it.uniroma2.dicii.bd.view;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Scanner;

public class ApplicationView {
    public static int showMenu(){
        System.out.println("\u001B[34m"+"**********************************"+"\u001B[0m");
        System.out.println("\u001B[34m"+"***  AUCTION SYSTEM DASHBOARD  ***"+"\u001B[0m");
        System.out.println("\u001B[34m"+"**********************************\n"+"\u001B[0m");
        System.out.println("****  What do you want to do? ****\n");
        System.out.println("1) Register");
        System.out.println("2) Login");
        System.out.println("3) Quit");
        System.out.println("");


        Scanner input = new Scanner(System.in);
        int choice = 0;
        while (true) {
            System.out.print("Please enter your choice: ");
            System.out.println("");
            choice = input.nextInt();
            if (choice >= 1 && choice <= 3) {
                break;
            }
            System.out.println("Invalid option");
        }

        return choice;
    }

    public static <T> void printObjectTable(List<T> list, OutputStream output, List<String> fieldsToPrint,String ifempty) {

        PrintWriter out = new PrintWriter(output);

        if (list.isEmpty()) {
            out.println("\u001B[35m"+ifempty+"\u001B[30m");
            out.flush();
            return;
        }

        int numcols = fieldsToPrint.size(); // how many columns
        String[] labels = new String[numcols]; // the column labels
        int[] colwidths = new int[numcols]; // the width of each
        int[] colpos = new int[numcols]; // start position of each
        int linewidth; // total width of table

        linewidth = 1; // for the initial '|'.
        for (int i = 0; i < numcols; i++) { // for each column
            colpos[i] = linewidth; // save its position
            labels[i] = fieldsToPrint.get(i); // get its label
            int size = 30; // Assume a column width of 30 for simplicity
            int labelsize = labels[i].length();
            if (labelsize > size)
                size = labelsize;
            colwidths[i] = size + 1; // save the column the size
            linewidth += colwidths[i] + 2; // increment total size
        }

        StringBuffer divider = new StringBuffer(linewidth);
        StringBuffer blankline = new StringBuffer(linewidth);
        for (int i = 0; i < linewidth; i++) {
            divider.insert(i, '-');
            blankline.insert(i, " ");
        }

        for (int i = 0; i < numcols; i++)
            divider.setCharAt(colpos[i] - 1, '+');
        divider.setCharAt(linewidth - 1, '+');

        out.println(divider);

        StringBuffer line = new StringBuffer(blankline.toString());
        line.setCharAt(0, '|');
        for (int i = 0; i < numcols; i++) {
            int pos = colpos[i] + 1 + (colwidths[i] - labels[i].length()) / 2;
            overwrite(line, pos, labels[i]);
            overwrite(line, colpos[i] + colwidths[i], " |");
        }

        out.println(line);
        out.println(divider);

        for (T item : list) {
            line = new StringBuffer(blankline.toString());
            line.setCharAt(0, '|');
            for (int i = 0; i < numcols; i++) {
                String fieldName = fieldsToPrint.get(i);
                String value = null;
                try {
                    Field field = item.getClass().getDeclaredField(fieldName);
                    field.setAccessible(true);
                    Object fieldValue = field.get(item);
                    if (fieldValue != null) {
                        value = fieldValue.toString();
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (value != null) {
                    overwrite(line, colpos[i] + 1, value);
                    overwrite(line, colpos[i] + colwidths[i], " |");
                }
            }
            out.println(line);
        }

        out.println(divider);
        out.flush();
    }

    static void overwrite(StringBuffer b, int pos, String s) {
        int slen = s.length(); // length of string
        int blen = b.length(); // length of buffer
        if (pos + slen > blen)
            slen = blen - pos; // does it fit?
        for (int i = 0; i < slen; i++)
            b.setCharAt(pos + i, s.charAt(i)); // copy characters
    }


}
