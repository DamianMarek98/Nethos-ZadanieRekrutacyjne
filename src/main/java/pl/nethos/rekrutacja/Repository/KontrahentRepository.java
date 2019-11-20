package pl.nethos.rekrutacja.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.nethos.rekrutacja.Model.Kontrahent;

import java.util.List;


@Repository
public interface KontrahentRepository extends CrudRepository<Kontrahent, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM KONTRAHENT")
    List<Kontrahent> getAllKontrahent();
}
