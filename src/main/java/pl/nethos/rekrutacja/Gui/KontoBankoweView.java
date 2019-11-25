package pl.nethos.rekrutacja.Gui;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import pl.nethos.rekrutacja.Model.KontoBankowe;
import pl.nethos.rekrutacja.Repository.KontoBankoweRepository;
import pl.nethos.rekrutacja.Services.KontoBankoweService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Route(value = "konta")
@CssImport(value = "./styles/styles.css")
public class KontoBankoweView extends VerticalLayout implements HasUrlParameter<String> {

    private KontoBankoweRepository kontoBankoweRepository;
    private KontoBankoweService kontoBankoweService;
    private Long selectedKontrahentId;
    private Grid<KontoBankowe> kontoBankoweGrid;
    private Label title;
    private Label error;


    public KontoBankoweView(@Autowired KontoBankoweRepository kontoBankoweRepository, @Autowired KontoBankoweService kontoBankoweService) {
        this.kontoBankoweGrid = new Grid<>();
        this.kontoBankoweRepository = kontoBankoweRepository;
        this.kontoBankoweService = kontoBankoweService;
        this.title = new Label("");
        this.title.setClassName("titleLabel");
        this.error = new Label("");
        this.error.setClassName("errorLabel");
        add(title);
        setSizeFull();
    }

    private void prepareKontoBakoweGrid() {
        kontoBankoweGrid.setClassName("kontoBankoweGrid");
        kontoBankoweGrid.setItems(fillGridWithKontaBankoweOnselectedKontrahentId());
        kontoBankoweGrid.addColumn(KontoBankowe::getFormattedNumer).setHeader("Numer konta").setAutoWidth(true);
        kontoBankoweGrid.addColumn(KontoBankowe::getFormattedAktywne).setHeader("Aktywne").setAutoWidth(true);
        kontoBankoweGrid.addColumn(KontoBankowe::getFormattedDomyslne).setHeader("Domyślne").setAutoWidth(true);
        kontoBankoweGrid.addColumn(KontoBankowe::getFormattedWirtualne).setHeader("Wirtualne").setAutoWidth(true);
        kontoBankoweGrid.addColumn(TemplateRenderer.<KontoBankowe>of("<button title='[[item.date]]' on-click='verify'>[[item.state]]</button>")
                .withProperty("state", KontoBankowe::getFormattedStanWeryfikacji)
                .withProperty("date", KontoBankowe::getDataWeryfikacjiAsString)
                .withEventHandler("verify", kontoBankowe -> {
                    checkStanWeryfikacji(kontoBankowe);
                    kontoBankoweService.update(kontoBankowe);
                    kontoBankoweGrid.getDataProvider().refreshAll();
                }))
                .setHeader("Stan weryfikacji").setAutoWidth(true);
        kontoBankoweGrid.setSelectionMode(Grid.SelectionMode.NONE); //grid  rows are not selectable
        kontoBankoweGrid.setHeightByRows(true);
    }

    private List<KontoBankowe> fillGridWithKontaBankoweOnselectedKontrahentId() {
        List<KontoBankowe> listOfAllKontoBankoweWithselectedKontrahentId = new ArrayList<>();
        kontoBankoweRepository.findAll().forEach(k -> {
            if (k.getKontrahent().getId().equals(selectedKontrahentId)) {
                if (title.getText().equals(""))
                    title.setText("Konta bankowe kontrahenta: " + k.getKontrahent().getNazwa()); //definitly need improvment, assigning evert time we enter the loop, check with debugger
                listOfAllKontoBankoweWithselectedKontrahentId.add(k);
            }
        });
        return listOfAllKontoBankoweWithselectedKontrahentId;
    }

    private void setselectedKontrahentId(Long selectedKontrahentId) {
        this.selectedKontrahentId = selectedKontrahentId;
    }


    @Override
    public void setParameter(BeforeEvent event,
                             String parameter) {
        setselectedKontrahentId(Long.parseLong(parameter.substring(3)));
        prepareKontoBakoweGrid();
        add(kontoBankoweGrid);
    }

    private void checkStanWeryfikacji(KontoBankowe kontoBankowe) {
        error.setText("");
        String nip = kontoBankowe.getKontrahent().getNip();
        String numer = kontoBankowe.getNumer();

        String url = "https://wl-api.mf.gov.pl/api/check/nip/" + nip + "/bank-account/" + numer;
        String answear = callExternalAPItoGetElementAssigment(url);

        Date now = new Date(System.currentTimeMillis());

        if (answear.equals("TAK")) {
            kontoBankowe.setStan_weryfikacji(true);
            kontoBankowe.setData_weryfikacji(now);
        } else if (answear.equals("NIE")) {
            kontoBankowe.setStan_weryfikacji(false);
            kontoBankowe.setData_weryfikacji(now);
        }
    }

    private String callExternalAPItoGetElementAssigment(String address) {
        try {
            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;

            while ((output = br.readLine()) != null) {
                JSONObject jsonObject = new JSONObject(output);
                try {
                    return jsonObject.getJSONObject("result").getString("accountAssigned"); //checking if json result have information about account assignment
                } catch (JSONException e) { //if there is no information then error handling
                    add(error);
                    error.setText("not valid data"); //display error at the bottom of the screen
                }
            }

            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
        return "error";
    }
}

