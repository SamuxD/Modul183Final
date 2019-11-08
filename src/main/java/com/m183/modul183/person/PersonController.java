package main.java.com.m183.modul183.person;/*package com.m183.modul183.person

import com.m183.modul183.person.Person;
import com.m183.modul183.dao.PersonDAO;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.lwawt.macosx.CSystemTray;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class PersonController {
    PersonDAO pdao = new PersonDAO();

@RequestMapping("/authenticate")
    public List<Person> authenticate(@RequestHeader Map<String, String> header) {
        for (Map.Entry<String, String> entry: header.entrySet())
        {
            System.out.println(entry.getKey() + " / " + entry.getValue());
        }

        List<Person> list = new ArrayList<>();
        return pdao.selectAllPersons();
    }
}*/
