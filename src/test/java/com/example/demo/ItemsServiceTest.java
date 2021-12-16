package com.example.demo;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Користувач
 */

import com.example.demo.CustomFieldException;
import com.example.demo.Items;
import com.example.demo.ItemsService;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


public class ItemsServiceTest {

    private ItemsService itemsService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void globalSetUp() {
        System.out.println("Initial setup...");
        System.out.println("Code executes only once");
    }

    @Before
    public void setUp() {
        System.out.println("Code executes before each test method");
        Items item1 = new Items("John", 1900, LocalDate.of(1994, 3, 17));
        Items item2 = new Items("Alice", 12,LocalDate.of(1970, 4, 17));
        Items item3 = new Items("Melinda", 33,LocalDate.of(1997, 6, 23));
        List<Items> itemsList = new ArrayList<>();
        itemsList.add(item1);
        itemsList.add(item2);
        itemsList.add(item3);
        itemsService = new ItemsService(itemsList);
    }

    @Test
    public void whenCreateNewUserThenReturnListWithNewUser() throws Exception {
        assertThat(itemsService.getItems().size(), is(3));
        itemsService.createNewItem("New Item", 111,LocalDate.of(1990, 2, 1));
        assertThat(itemsService.getItems().size(), is(4));
    }

    @Test
    public void whenRemoveUserWhenRemoveUserByName(){
        itemsService.removeItem("Melinda");
        List<Items> itemsList = itemsService.getItems();
        assertThat(itemsList.size(), is(2));
    }

    @Test
    public void whenCreateNewUserWithoutNameThenThrowCustomFieldException() throws Exception {
        thrown.expect(CustomFieldException.class);
        thrown.expectMessage("Name could not be empty or null");
        itemsService.createNewItem(null, 233,LocalDate.of(1990, 2, 1));
    }

    @Test
    public void whenCreateNewItemWithoutDateOfDeliveryThenThrowCustomFieldException() throws Exception {
        thrown.expect(CustomFieldException.class);
        thrown.expectMessage("Date could not be null");
        itemsService.createNewItem("Dave", 333, null);
    }

    @Test
    public void whenIsDeliveryDayWhenDeliveryThenReturnTrue() throws CustomFieldException {
        boolean isItDeliveryDay = itemsService.isDeliveryDay(itemsService.getItems().get(0), LocalDate.of(1990, 2, 1));
        assertFalse(isItDeliveryDay);
    }

    @Test
    public void whenIsDeliveryDayWhenNotDeliveryThenReturnFalse() throws CustomFieldException {
        boolean isItDeliveryDay = itemsService.isDeliveryDay(itemsService.getItems().get(0), LocalDate.of(1990, 3, 17));
        assertTrue(isItDeliveryDay);
    }

    @AfterClass
    public static void tearDown() {
        System.out.println("Tests finished");
    }

    @After
    public void afterMethod() {
        System.out.println("Code executes after each test method");
    }
}