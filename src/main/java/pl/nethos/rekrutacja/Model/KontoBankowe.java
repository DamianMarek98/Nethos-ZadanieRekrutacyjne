package pl.nethos.rekrutacja.Model;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "KONTO_BANKOWE")
public class KontoBankowe {

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

    public void setNumer(String numer) {
        this.numer = numer;
    }

    public Boolean getAktywne() {
        return aktywne;
    }

    public void setAktywne(Boolean aktywne) {
        this.aktywne = aktywne;
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

    public void setWirtualne(Boolean wirtualne) {
        this.wirtualne = wirtualne;
    }

    public Boolean getStan_weryfikacji() {
        return stan_weryfikacji;
    }

    public void setStan_weryfikacji(Boolean stan_weryfikacji) {
        this.stan_weryfikacji = stan_weryfikacji;
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
