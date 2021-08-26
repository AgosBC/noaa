package ar.com.ada.api.noaa.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.noaa.entities.Boya;
import ar.com.ada.api.noaa.entities.Muestra;
import ar.com.ada.api.noaa.entities.Boya.ColorLuzEnum;
import ar.com.ada.api.noaa.repos.BoyaRepository;

@Service
public class BoyaServece {

    @Autowired
    BoyaRepository repo;

    public Boya crearBoya(Double latitud, Double longitud){
        
        Boya boya = new Boya();
        boya.setLatitudInstalacion(latitud);
        boya.setLongitudInstalacion(longitud);
        boya.setColorLuzId(ColorLuzEnum.AZUL);

        return repo.save(boya);
    }

    public List<Boya> obtenerBoyas() {
        return repo.findAll();
    }

    public Boya buscarPorId(Integer id){

        return repo.findByBoyaId(id);
    }

    public void guardar(Boya boya){
        repo.save(boya);
    }

    

    
}
