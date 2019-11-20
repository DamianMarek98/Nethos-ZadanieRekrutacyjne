package pl.nethos.rekrutacja.Model;

import javax.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity
@Table(name = "KONTRAHENT")
public class Kontrahent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    String nazwa;
    String nip;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name="kontrahent_kontoBankowe_mapping", joinColumns = @JoinColumn(name="id_kontrahent"),inverseJoinColumns = @JoinColumn(name = "id_kontoBankowe"))
    Collection<KontoBankowe> kontaBankowe=new ArrayList<>();

    public Kontrahent(){};

    public Kontrahent(String nazwa, String nip) {
        this.nazwa = nazwa;
        this.nip = nip;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public Collection<KontoBankowe> getKontaBankowe() {
        return kontaBankowe;
    }

    public void setKontaBankowe(Collection<KontoBankowe> kontaBankowe) {
        this.kontaBankowe = kontaBankowe;
    }

    @Override
    public String toString(){
        return "Kontrahent{" + "id: " + id + ", nazwa='" + nazwa +'\''+", nip=" +nip+'\''+'}';
    }
}
