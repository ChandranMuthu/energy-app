package com.example.crudwithvaadin.view;

import com.example.crudwithvaadin.security.SecurityUtils;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import org.springframework.security.core.context.SecurityContextHolder;

@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout {
    public MainLayout() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Energy App");
        logo.addClassName("logo");
        //H6 userName = new H6("Welcome, " + SecurityContextHolder.getContext().getAuthentication().getName());
        Span span = new Span("Welcome, " + SecurityContextHolder.getContext().getAuthentication().getName());
        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, span);
        header.expand(logo);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.addClassName("header");
        addToNavbar(header);
    }

    private void createDrawer() {
        RouterLink listLink = new RouterLink("Dashboard", DashboardView.class);
        RouterLink energy_reading_Link = new RouterLink("Energy List", EnergyReadingListView.class);

        RouterLink new_user_Link = new RouterLink("Add New User", SignupView.class);
        RouterLink all_user_link = new RouterLink("All Users", UsersView.class);
        Anchor logout = new Anchor("logout", "Log out");
        listLink.setHighlightCondition(HighlightConditions.sameLocation());
        if(SecurityUtils.isUserHasAdminRole())
        {
            addToDrawer(new VerticalLayout(listLink, energy_reading_Link, new_user_Link, all_user_link, logout));
        }
        else
        {
            addToDrawer(new VerticalLayout(listLink, energy_reading_Link, logout));
        }

    }
}