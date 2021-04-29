package com.example.crudwithvaadin.view;

import com.example.crudwithvaadin.model.UserDetails;
import com.example.crudwithvaadin.repository.UserRepository;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "users-view", layout = MainLayout.class)
public class UsersView extends VerticalLayout {
    private final UserRepository userRepository;
    final Grid<UserDetails> grid;
    final List<UserDetails> userDetailsList;

    public UsersView(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.grid = new Grid<>(UserDetails.class);
        this.userDetailsList = userRepository.findAll();
        grid.setItems(this.userDetailsList);
        add(grid);
    }
}
