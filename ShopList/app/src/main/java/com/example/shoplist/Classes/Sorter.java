package com.example.shoplist.Classes;

import java.util.ArrayList;

/**
 * Сортиировщик списка.
 */
public class Sorter {

    private ArrayList startList;
    private Filter f;

    public Sorter(ArrayList arrayList) {
        this.startList = arrayList;
    }

    /**
     * Сортировка списка по заданному правилу.
     * @param arrayList фильтруемый список.
     * @return отфильтрованный список.
     */
    public ArrayList sort(ArrayList arrayList) {
        arrayList.clear();
        for (int i = 0; i < startList.size(); i++) {
            if (f.filter(startList.get(i))) arrayList.add(startList.get(i));
        }
        return arrayList;
    }

    /**
     * Установка фильтра.
     * @param f фильтр.
     */
    public void setFilter(Filter f) {
        this.f = f;
    }
}
