package ar.com.ada.api.noaa.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.ada.api.noaa.entities.Boya;
import ar.com.ada.api.noaa.entities.Boya.ColorLuzEnum;

@Repository
public interface BoyaRepository extends JpaRepository<Boya, Integer> {

    Boya findByBoyaId(Integer id);
    List<Boya> findByColorLuzId(ColorLuzEnum colorLuzId);
    
}
