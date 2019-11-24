package pl.nethos.rekrutacja.Model;


import lombok.Data;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Entity
@Table(name = "KONTO_BANKOWE")
public class KontoBankowe  {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    @ManyToOne
    @JoinColumn(name="id_kontrahent")
    Kontrahent kontrahent;
    String numer;
    Boolean aktywne;
    Boolean domyslne;
    Boolean wirtualne;
    Boolean stan_weryfikacji;
    Date data_weryfikacji;

    public KontoBankowe(){};

    public KontoBankowe(Kontrahent kontrahent, String numer, Boolean aktywne, Boolean domyslne, Boolean wirtualne, Boolean stan_weryfikacji, Date data_weryfikacji) {
        this.kontrahent = kontrahent;
        this.numer = numer;
        this.aktywne = aktywne;
        this.domyslne = domyslne;
        this.wirtualne = wirtualne;
        this.stan_weryfikacji = stan_weryfikacji;
        this.data_weryfikacji = data_weryfikacji;
    }

    public Kontrahent getKontrahent() {
        return kontrahent;
    }

    public void setKontrahent(Kontrahent kontrahent) {
        this.kontrahent = kontrahent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getNumer() {
        return numer;
    }

    public String getFormattedNumer(){
        String numer = getNumer();
        StringBuilder formattedNumber = new StringBuilder();
        for(int i=0; i<numer.length(); i++){
            if(i==2 || i%4 == 0)
                formattedNumber.append(" ");
            formattedNumber.append(numer.charAt(i));
        }
        return formattedNumber.toString();
    }

    public void setNumer(String numer) {
        this.numer = numer;
    }

    public Boolean getAktywne() {
        return aktywne;
    }

    public String getFormattedAktywne(){
        if(getAktywne())
            return "tak";
        return "nie";
    }

    public void setAktywne(Boolean aktywne) {
        this.aktywne = aktywne;
    }

    public String getFormattedDomyslne(){
        if(getDomyslne())
            return "tak";
        return "nie";
    }

    public Boolean getDomyslne() {
        return domyslne;
    }

    public void setDomyslne(Boolean domyslne) {
        this.domyslne = domyslne;
    }

    public Boolean getWirtualne() {
        return wirtualne;
    }

    public String getFormattedWirtualne(){
        if(getWirtualne())
            return "tak";
        return "nie";
    }

    public void setWirtualne(Boolean wirtualne) {
        this.wirtualne = wirtualne;
    }

    public Boolean getStan_weryfikacji() {
        return stan_weryfikacji;
    }

    public String getFormattedStanWeryfikacji(){
        if(stan_weryfikacji == null)
            return "nieokreślony";
        if(getStan_weryfikacji())
            return "zweryfikowny";
        return "błędne konto"; //to check if this string fits meaning
    }

    public void setStan_weryfikacji(Boolean stan_weryfikacji) {
        this.stan_weryfikacji = stan_weryfikacji;
    }

    public String getDataWeryfikacjiAsString(){
        if(data_weryfikacji == null){
            return "nie weryfikowano";
        }
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yy HH:mm");
        return "Zweryfikowno: "+DATE_FORMAT.format(data_weryfikacji);
    }

    public Date getData_weryfikacji() {
        return data_weryfikacji;
    }

    public void setData_weryfikacji(Date data_weryfikacji) {
        this.data_weryfikacji = data_weryfikacji;
    }

    @Override
    public String toString(){
        return "Konto bankowe: id-"+id+", kontrahent-"+ kontrahent.nazwa +", numer-"+ numer;
    }
}
