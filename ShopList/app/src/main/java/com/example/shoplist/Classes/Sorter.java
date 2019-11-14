package com.example.shoplist.Classes;

import java.util.ArrayList;

/**
 * Сортиировщик списка.
 */
public class Sorter {

    private Filter f;

    /**
     * Сортировка списка по заданному правилу.
     * @param arrayList фильтруемый список.
     * @return отфильтрованный список.
     */
    public ArrayList sort(ArrayList arrayList) {
        ArrayList list = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (f.filter(arrayList.get(i))) list.add(arrayList.get(i));
        }
        return list;
    }

    /**
     * Установка фильтра.
     * @param f фильтр.
     */
    public void setFilter(Filter f) {
        this.f = f;
    }
}
