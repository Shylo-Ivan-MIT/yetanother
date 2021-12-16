package com.example.demo;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Користувач
 */
import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Component
public class ItemsService {

    private List<Items> items;

    public ItemsService(List<Items> items) {
        this.items = items;
    }

    public List<Items> getItems() {
        return items;
    }

    public List<Items> createNewItem(String name, int itemPrice, LocalDate dateOfDelivery) throws Exception {
        validateItem(name, itemPrice, dateOfDelivery);
        Items item = new Items(name, itemPrice, dateOfDelivery);
        items.add(item);
        return items;
    }

    public void removeItem(String name) {
        items = items.stream().filter(it -> !it.name.equals(name)).collect(Collectors.toList());
    }

    public Items getItem(String name) throws CustomFieldException {
        for (Items item: items) {
            if(item.getName().equals(name)) {
                return item;
            }
        }
        throw new CustomFieldException("Item does not exist");
    }

    public boolean isDeliveryDay(Items item, LocalDate date) throws CustomFieldException {
        if (isNull(item) || isNull(item.dateOfDelivery)) {
            throw new CustomFieldException("User or date is null");
        }
        if (isNull(date)) {
            throw new CustomFieldException("Compare date must not be null");
        }
        return date.getDayOfMonth() == item.dateOfDelivery.getDayOfMonth() && date.getMonth().equals(item.dateOfDelivery.getMonth());
    }

    private void validateItem(String name, int itemPrice, LocalDate dateOfDelivery) throws Exception {
        if (isNull(name) || name.isBlank()) {
            throw new CustomFieldException("Name could not be empty or null");
        }
        if (isNull(itemPrice)) {
            throw new CustomFieldException("Price could not be null");
        }
        if (isNull(dateOfDelivery)) {
            throw new CustomFieldException("Date could not be null");
        }
    }
}
class Items {
    public String name;
    public int itemPrice;
    public LocalDate dateOfDelivery;

    public Items(String name, int itemPrice, LocalDate dateOfDelivery) {
        this.name = name;
        this.itemPrice = itemPrice;
        this.dateOfDelivery = dateOfDelivery;
    }

    public String getName() {
        return name;
    }
}


class CustomFieldException extends Exception {
    private String message;

    public CustomFieldException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}