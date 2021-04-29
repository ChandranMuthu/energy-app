package com.example.crudwithvaadin.view;

import com.example.crudwithvaadin.model.EnergyOutput;
import com.example.crudwithvaadin.repository.EnergyOutputRepository;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.util.StringUtils;

import java.util.List;

@Route(value = "energy-list", layout = MainLayout.class)
public class EnergyReadingListView extends VerticalLayout {

    private final EnergyOutputRepository repo;
    final Grid<EnergyOutput> grid;
    final TextField filter;
    final List<EnergyOutput> energyOutputList;

    public EnergyReadingListView(EnergyOutputRepository repo) {
        this.repo = repo;
        this.grid = new Grid<>(EnergyOutput.class);;
        this.filter = new TextField();;

        this.energyOutputList = repo.findAll();
        HorizontalLayout actions = new HorizontalLayout(filter);
        filter.setPlaceholder("Filter by transactionId");
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> listCustomers(e.getValue()));
        //"createdBy", "updatedBy", "transactionResponseCode", "updatedAt", "deviceIndex"
        grid.setColumns("id", "transactionId", "deviceType", "deviceDateTime", "inputReading", "transactionConfig", "dataLength", "calculatedValue","createdAt");
        this.grid.getColumnByKey("id").setWidth("400px");
        add(actions, this.grid);
        listCustomers(null);
    }

    void listCustomers(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(repo.findAll());
        } else {
            grid.setItems(repo.findByTransactionId(filterText));
        }
    }
}
