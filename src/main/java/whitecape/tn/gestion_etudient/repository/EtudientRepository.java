package whitecape.tn.gestion_etudient.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import whitecape.tn.gestion_etudient.entites.Etudient;

import java.util.Date;
import java.util.List;

public interface EtudientRepository extends JpaRepository<Etudient, Long> {
    public List<Etudient> findByNom(String n);
    public Page<Etudient> findByNom(String n, Pageable pageable);
    @Query("Select e from Etudient e where e.nom like :x")
    public Page<Etudient> chercherEtudient(@Param("x") String mc, Pageable pageable);
    @Query("Select e from Etudient e where e.dateNaissance >:x and e.dateNaissance<:y ")
    public List<Etudient> chercherDate(@Param("x") Date d1,@Param("y") Date d2);



}
