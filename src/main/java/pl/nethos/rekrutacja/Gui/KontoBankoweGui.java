package pl.nethos.rekrutacja.Gui;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.nethos.rekrutacja.Model.KontoBankowe;
import pl.nethos.rekrutacja.Repository.KontoBankoweRepository;

import java.util.ArrayList;
import java.util.List;

@Route(value = "konta")
public class KontoBankoweGui extends VerticalLayout implements HasUrlParameter<String> {

    private KontoBankoweRepository kontoBankoweRepository;
    private Long kontrahentId;
    private Grid<KontoBankowe> kontoBankoweGrid;
    private Label title;


    public KontoBankoweGui(@Autowired KontoBankoweRepository kontoBankoweRepository) {
        this.kontoBankoweGrid = new Grid<>();
        this.kontoBankoweRepository = kontoBankoweRepository;
        this.title = new Label();
        add(title);
    }

    private void prepareKontoBakoweGrid() {
        kontoBankoweGrid.setItems(fillGridWithKontaBankoweOnKontrahentId());
        kontoBankoweGrid.addColumn(KontoBankowe::getFormattedNumer).setHeader("Numer").setAutoWidth(true);
        kontoBankoweGrid.addColumn(KontoBankowe::getFormattedAktywne).setHeader("Aktywne").setAutoWidth(true);
        kontoBankoweGrid.addColumn(KontoBankowe::getFormattedDomyslne).setHeader("Domyślne").setAutoWidth(true);
        kontoBankoweGrid.addColumn(KontoBankowe::getFormattedWirtualne).setHeader("Wirtualne").setAutoWidth(true);
        kontoBankoweGrid.addColumn(KontoBankowe::getFormattedStanWeryfikacji).setHeader("Stan weryfikacji").setAutoWidth(true);
        kontoBankoweGrid.setSelectionMode(Grid.SelectionMode.NONE); //grid  rows are not selectable
    }

    private List<KontoBankowe> fillGridWithKontaBankoweOnKontrahentId() {
        List<KontoBankowe> listOfAllKontoBankoweWithKontrahentId = new ArrayList<>();
        kontoBankoweRepository.findAll().forEach(k -> {
            if (k.getKontrahent().getId() == kontrahentId) {
                title.setText("Konta bankowe kontrahenta: " +k.getKontrahent().getNazwa()); //definitly need improvment, assigning evert time we enter the loop, check with debugger 
                listOfAllKontoBankoweWithKontrahentId.add(k);
            }
        });
        return listOfAllKontoBankoweWithKontrahentId;
    }

    private void setKontrahentId(Long kontrahentId) {
        this.kontrahentId = kontrahentId;
    }


    @Override
    public void setParameter(BeforeEvent event,
                             String parameter) {
        setKontrahentId(Long.parseLong(parameter.substring(3)));
        prepareKontoBakoweGrid();
        add(kontoBankoweGrid);
    }


}
