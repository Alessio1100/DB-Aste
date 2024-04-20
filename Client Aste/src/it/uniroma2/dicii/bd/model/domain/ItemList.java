package it.uniroma2.dicii.bd.model.domain;

import java.util.ArrayList;
import java.util.List;

public class ItemList {

    List<Item> itemList = new ArrayList<>();

    public int getSize(){ return itemList.size();}

    public List<Item> getList(){ return this.itemList;}

    public void addItem(Item item){ this.itemList.add(item); }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        for(Item item : itemList){
            stringBuilder.append(item);
        }
        return stringBuilder.toString();
    }
}
