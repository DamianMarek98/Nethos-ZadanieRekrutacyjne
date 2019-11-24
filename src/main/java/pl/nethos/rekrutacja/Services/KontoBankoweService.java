package pl.nethos.rekrutacja.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.nethos.rekrutacja.Model.KontoBankowe;
import pl.nethos.rekrutacja.Repository.KontoBankoweRepository;

import java.util.Optional;

@Service
public class KontoBankoweService {
    private KontoBankoweRepository kontoBankoweRepository;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public KontoBankoweService(KontoBankoweRepository kontoBankoweRepository, JdbcTemplate jdbcTemplate) {
        this.kontoBankoweRepository = kontoBankoweRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<KontoBankowe> findById(Long id) {
        return kontoBankoweRepository.findById(id);
    }

    public Iterable<KontoBankowe> findAll() {
        return kontoBankoweRepository.findAll();
    }

    public void update(KontoBankowe kontoBankowe) { //set all parameters
        jdbcTemplate.update(
                "Update KONTO_BANKOWE  SET stan_weryfikacji=?, numer=?,aktywne=?,domyslne=?," +
                        "wirtualne=?, data_weryfikacji=?" +
                        " WHERE id=?",
                kontoBankowe.getStan_weryfikacji(), kontoBankowe.getNumer(), kontoBankowe.getAktywne(),kontoBankowe.getDomyslne(),
                kontoBankowe.getWirtualne(), kontoBankowe.getData_weryfikacji(),
                kontoBankowe.getId());
    }
}
