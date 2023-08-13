package app.entities.medic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface MedicRepository extends JpaRepository<Medic,Long> {
    
    @Transactional
    @Modifying
    @Query("UPDATE Medic c SET c.fullName = :#{#medic.fullName}, c.idParafa = :#{#medic.idParafa}, c.specializare = :#{#medic.specializare} WHERE c.id= :#{#medic.id}")
    public void update(@Param("medic") Medic medic);

    @Transactional
    @Modifying
    @Query("UPDATE Medic c SET c.fullName = :#{#medic.fullName}, c.idParafa = :#{#medic.idParafa}, c.specializare = :#{#medic.specializare}, c.password =:password WHERE c.id= :#{#medic.id}")
    public void updateWithPassword(@Param("medic") Medic medic,@Param("password")String password);

    
}
