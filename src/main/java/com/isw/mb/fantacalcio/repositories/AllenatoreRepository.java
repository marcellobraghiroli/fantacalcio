package com.isw.mb.fantacalcio.repositories;

import com.isw.mb.fantacalcio.models.Allenatore;
import com.isw.mb.fantacalcio.models.Lega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AllenatoreRepository extends JpaRepository<Allenatore, Integer> {
    Allenatore findByUsernameAndPasswordAndDeleted(String username, String password, Character deleted);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByTelefono(String telefono);

    @Query("SELECT a FROM Lega l JOIN Squadra s ON l.id = s.lega.id JOIN Allenatore a ON s.allenatore.id = a.id WHERE l.id = :idLega AND a.deleted = 'N' AND s.deleted = 'N' AND a.id != :idAllLogged")
    List<Allenatore> findByLegaAndNotLogged(Integer idLega, Integer idAllLogged);

    @Query("SELECT a.email FROM Allenatore a WHERE a.username = :recUsername AND a.deleted = 'N'")
    String getEmailByUsername(String recUsername);

    Allenatore findByIdAndDeleted(Integer recId, char n);

    @Query("SELECT a FROM Lega l JOIN Squadra s ON l.id = s.lega.id JOIN Allenatore a ON s.allenatore.id = a.id WHERE l.id = :idLega AND a.deleted = 'N' AND s.deleted = 'N'")
    List<Allenatore> findByLega(Integer idLega);
}