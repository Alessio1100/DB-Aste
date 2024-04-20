package it.uniroma2.dicii.bd.model.domain;

import java.util.ArrayList;
import java.util.List;

public class CategoryList {
    List<Category> categoryList = new ArrayList<>();

    public int getSize(){ return categoryList.size();}

    public List<Category> getList(){ return this.categoryList;}

    public void addCategory(Category category){ this.categoryList.add(category); }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        for(Category category :categoryList){
            stringBuilder.append(category);
        }
        return stringBuilder.toString();
    }
}
