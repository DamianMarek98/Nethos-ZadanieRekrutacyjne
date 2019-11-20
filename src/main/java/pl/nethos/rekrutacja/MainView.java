package pl.nethos.rekrutacja;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.springframework.beans.factory.annotation.Autowired;
import pl.nethos.rekrutacja.Repository.KontrahentRepository;

@Route
@PWA(name = "Nethos - Zadanie rekrutacyjne na stanowisko programisty", shortName = "Nethos - Rekrutacja")
public class MainView extends VerticalLayout {

    private KontrahentRepository kontrahentRepository;


    public MainView(@Autowired KontrahentRepository kontrahentRepository) {
        this.kontrahentRepository = kontrahentRepository;
        setSizeFull();
        //wyswietl();
    }

    private void wyswietl(){
        kontrahentRepository.findAll().forEach(k -> add(new Label(k.toString())));
    }
}

