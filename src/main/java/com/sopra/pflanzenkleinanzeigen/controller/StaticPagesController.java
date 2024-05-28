package com.sopra.pflanzenkleinanzeigen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Controller for all requests that only render static HTML content and do not use ModelAttribute.
 */
@Controller
public class StaticPagesController implements WebMvcConfigurer {

    /**
     * This method adds view controllers to the registry.
     * Currently, it adds a view controller for the "/login" endpoint, which displays the "login" view.
     * Additional view controllers for other endpoints that do not require ModelAttributes can be added here.
     * @param registry The registry to which view controllers are added.
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        // Hier können Sie weitere Requests definieren, die keine ModelAttribute erfordern bspw für das Impressum:
        // registry.addViewController("/imprint").setViewName("imprint");
    }
}
