package pl.nethos.rekrutacja.Gui;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.nethos.rekrutacja.Repository.KontoBankoweRepository;
import pl.nethos.rekrutacja.Repository.KontrahentRepository;

@Route
public class KontrahentGui extends VerticalLayout {

    private KontrahentRepository kontrahentRepository;

    @Autowired
    public KontrahentGui(KontrahentRepository kontrahentRepository){
        this.kontrahentRepository = kontrahentRepository;

        kontrahentRepository.getAllKontrahent().forEach(k -> add(new Label(k.getNazwa()))); //works



    }


}
