package pl.nethos.rekrutacja.Gui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.events.ClickEvent;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.selection.SelectionListener;
import com.vaadin.flow.data.selection.SingleSelectionEvent;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import pl.nethos.rekrutacja.Model.Kontrahent;
import pl.nethos.rekrutacja.Repository.KontoBankoweRepository;
import pl.nethos.rekrutacja.Repository.KontrahentRepository;

import javax.swing.*;
import java.util.*;

@Route
public class KontrahentGui extends VerticalLayout {
    private KontrahentRepository kontrahentRepository;
    private Grid<Kontrahent> kontrahentGrid;
    private Long selectedKontrahentId;
    private Label title;

    @Autowired
    public KontrahentGui(KontrahentRepository kontrahentRepository){

        this.kontrahentRepository = kontrahentRepository;
        this.kontrahentGrid  = new Grid<>();
        this.title = new Label("Lista Kontrahentów: ");

        prepareKontrahentGrid();
        addAllElementsToLyout();
    }
    private void addAllElementsToLyout(){
        add(title);
        add(kontrahentGrid);
    }

    private void prepareKontrahentGrid(){
        kontrahentGrid.setItems(fillGridWithKontrahent());
        kontrahentGrid.addColumn(Kontrahent::getNazwa).setHeader("Nazwa");
        kontrahentGrid.addColumn(Kontrahent::getNip).setHeader("Nip");
        kontrahentGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        kontrahentGrid.addSelectionListener(event -> {
            Optional<Kontrahent> selectedKontrahent = event.getFirstSelectedItem();
            selectedKontrahentId = selectedKontrahent.get().getId();
            getUI().ifPresent(ui -> ui.navigate("konta"+"/id="+selectedKontrahentId));
        });
    }

    private List<Kontrahent> fillGridWithKontrahent(){
        List<Kontrahent> listOfAllKontrahent = new ArrayList<>();
        kontrahentRepository.findAll().forEach(listOfAllKontrahent::add);
        return listOfAllKontrahent;
    }

}
