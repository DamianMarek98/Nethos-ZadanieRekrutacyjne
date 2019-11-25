package pl.nethos.rekrutacja.Gui;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.springframework.beans.factory.annotation.Autowired;
import pl.nethos.rekrutacja.Model.Kontrahent;
import pl.nethos.rekrutacja.Repository.KontrahentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Route
@CssImport(value = "./styles/styles.css")
@PWA(name = "Lista kontrahentów", shortName = "Kontrahenci")
public class MainView extends VerticalLayout {
    private KontrahentRepository kontrahentRepository;
    private Grid<Kontrahent> kontrahentGrid;
    private Long selectedKontrahentId;
    private Label title;

    @Autowired
    public MainView(KontrahentRepository kontrahentRepository) {

        this.kontrahentRepository = kontrahentRepository;
        this.kontrahentGrid = new Grid<>();
        this.title = new Label("Lista Kontrahentów: ");
        this.title.setClassName("titleLabel");
        setSizeFull();
        prepareKontrahentGrid();
        addAllElementsToLyout();
    }

    private void addAllElementsToLyout() {
        add(title);
        add(kontrahentGrid);
    }

    private void prepareKontrahentGrid() {
        kontrahentGrid.setItems(fillGridWithKontrahent());
        kontrahentGrid.addColumn(Kontrahent::getNazwa).setHeader("Nazwa").setAutoWidth(true);
        kontrahentGrid.addColumn(Kontrahent::getNip).setHeader("Nip").setAutoWidth(true);
        kontrahentGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        kontrahentGrid.addSelectionListener(event -> {
            Optional<Kontrahent> selectedKontrahent = event.getFirstSelectedItem();
            selectedKontrahentId = selectedKontrahent.get().getId();
            getUI().ifPresent(ui -> ui.navigate("konta" + "/id=" + selectedKontrahentId));
        });
        kontrahentGrid.setHeightByRows(true);
    }

    private List<Kontrahent> fillGridWithKontrahent() {
        List<Kontrahent> listOfAllKontrahent = new ArrayList<>();
        kontrahentRepository.findAll().forEach(listOfAllKontrahent::add);
        return listOfAllKontrahent;
    }

}
