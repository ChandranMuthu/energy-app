package com.example.crudwithvaadin.view;

import com.example.crudwithvaadin.model.EnergyOutput;
import com.example.crudwithvaadin.model.User;
import com.example.crudwithvaadin.repository.UserRepository;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "users-view", layout = MainLayout.class)
public class UsersView extends VerticalLayout {
    private final UserRepository userRepository;
    final Grid<User> grid;
    final List<User> userList;

    public UsersView(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.grid = new Grid<>(User.class);
        this.userList = userRepository.findAll();
        grid.setItems(this.userList);
        add(grid);
    }
}
