package pl.nethos.rekrutacja.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.nethos.rekrutacja.Model.Kontrahent;
import pl.nethos.rekrutacja.Repository.KontrahentRepository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Service
public class KontrahentService {
    private EntityManager entityManager;
    private KontrahentRepository kontrahentRepository;

    @Autowired
    public KontrahentService(KontrahentRepository kontrahentRepository) {
        this.kontrahentRepository = kontrahentRepository;
    }

    public Optional<Kontrahent> findById(Long id) {
        return kontrahentRepository.findById(id);
    }

    public Iterable<Kontrahent> findAll() {
        return kontrahentRepository.findAll();
    }

    public boolean checkIfUserOfGivenIdExists(Long id) {
        Optional<Kontrahent> k = findById(id);
        return k.isPresent();
    }
}
