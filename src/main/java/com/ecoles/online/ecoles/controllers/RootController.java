package com.ecoles.online.ecoles.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping(value="")
public class RootController {
    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        return "index";
    }   
}
