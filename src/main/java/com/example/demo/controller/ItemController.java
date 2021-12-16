package com.example.demo.controller;

import com.example.demo.ItemsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
@RequiredArgsConstructor
public class ItemController {
    @Autowired
    ItemsService service;


    @GetMapping("/")
    public String getIndex(Model model){
        model.addAttribute("items", service.getItems());

        return "index";
    }
    @PostMapping("/add")
    public String addItem(@RequestParam(name ="name")  String name, @RequestParam(name ="price")  String price,
                          @RequestParam(name ="date")  String date, Model model) {

        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");

        //String date = "16-Aug-2016";
        //

        try {
            LocalDate localDate = LocalDate.parse(date);
            service.createNewItem(name,Integer.parseInt(price),localDate);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        model.addAttribute("items", service.getItems());

        return "index";
    }
    @PostMapping("/is")
    public String isDelivery(@RequestParam(name ="name")  String name,
                     @RequestParam(name ="date")  String date, Model model) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");

        //String date = "16-Aug-2016";

        try {
            LocalDate localDate = LocalDate.parse(date, formatter);
            if (service.isDeliveryDay(service.getItem(name) ,localDate)){
                model.addAttribute("error", name+ " is Delivery day");
            } else {
                model.addAttribute("error", name+ " is not Delivery day");
            }
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        model.addAttribute("items", service.getItems());



        return "index";
    }

    @PostMapping("/delete")
    public String addItem(@RequestParam(name ="name")  String name) {
        service.removeItem(name);
        return "redirect:/";
    }
}
