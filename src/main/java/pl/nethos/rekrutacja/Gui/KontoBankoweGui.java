package pl.nethos.rekrutacja.Gui;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.nethos.rekrutacja.Model.KontoBankowe;
import pl.nethos.rekrutacja.Repository.KontoBankoweRepository;

import java.util.List;

@Route
public class KontoBankoweGui extends VerticalLayout {
    private KontoBankoweRepository kontoBankoweRepository;

    @Autowired
    public KontoBankoweGui(KontoBankoweRepository kontoBankoweRepository){
        this.kontoBankoweRepository = kontoBankoweRepository;

        List<KontoBankowe> test = kontoBankoweRepository.getAllKontoBankowe(); //size = 0 why?
        kontoBankoweRepository.getAllKontoBankowe().forEach(k -> add(new Label(k.getNumer())));
    }

}
