package pl.nethos.rekrutacja.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.nethos.rekrutacja.Model.KontoBankowe;
import pl.nethos.rekrutacja.Repository.KontoBankoweRepository;

import java.util.List;
import java.util.Optional;

@Service
public class KontoBankoweService {

    private KontoBankoweRepository kontoBankoweRepository;

    @Autowired
    public KontoBankoweService(KontoBankoweRepository kontoBankoweRepository){
        this.kontoBankoweRepository = kontoBankoweRepository;
    }

    public Optional<KontoBankowe> findById(Long id){
        return kontoBankoweRepository.findById(id);
    }

    public Iterable<KontoBankowe> findAll(){
        return kontoBankoweRepository.findAll();
    }
}
