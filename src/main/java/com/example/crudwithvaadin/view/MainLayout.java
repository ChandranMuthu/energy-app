package com.example.crudwithvaadin.view;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout {
    public MainLayout() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Energy App");
        logo.addClassName("logo");
        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo);
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
        listLink.setHighlightCondition(HighlightConditions.sameLocation());
        addToDrawer(new VerticalLayout(listLink, energy_reading_Link, new_user_Link, all_user_link));
    }
}