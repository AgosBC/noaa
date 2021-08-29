package ar.com.ada.api.noaa.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.noaa.entities.Boya;
import ar.com.ada.api.noaa.entities.Muestra;
import ar.com.ada.api.noaa.entities.Boya.ColorLuzEnum;
import ar.com.ada.api.noaa.models.response.MuestraMinimaResponse;
import ar.com.ada.api.noaa.models.response.MuestraPorColorResponse;
import ar.com.ada.api.noaa.repos.BoyaRepository;
import ar.com.ada.api.noaa.repos.MuestraRepository;

@Service
public class MuestraService {

    @Autowired
    MuestraRepository repo;

    @Autowired
    BoyaServece boyaServece;

    public Muestra crearMuestra(Integer boyaId, Date horario, String matricula, Double latitud, Double longitud,
            Double alturaNivelDelMar) {

        Muestra muestra = new Muestra();

        muestra.setHorario(horario);
        muestra.setMatricula(matricula);
        muestra.setLatitud(latitud);
        muestra.setLongitud(longitud);
        muestra.setAlturaNivelDelMar(alturaNivelDelMar);

        Boya boya = boyaServece.buscarPorId(boyaId);

        if (alturaNivelDelMar < -100 || alturaNivelDelMar > 100) {
            boya.setColorLuzId(ColorLuzEnum.ROJO);
        } else if (alturaNivelDelMar < -50 || alturaNivelDelMar > 50) {
            boya.setColorLuzId(ColorLuzEnum.AMARILLO);
        } else {
            boya.setColorLuzId(ColorLuzEnum.VERDE);
        }

        boya.agregarMuestra(muestra);

        return repo.save(muestra);

    }

    public void borrar(Muestra muestra) {
        repo.delete(muestra);
    }

    public Muestra buscarPorId(Integer id) {
        return repo.findByMuestraId(id);
    }

    public List<Muestra> buscarMuestras(Integer idBoya) {

        Boya boya = boyaServece.buscarPorId(idBoya);

        return boya.getMuestras();

    }

    /*
     * public void buscarMuestraPorColor(ColorLuzEnum color) {
     * 
     * 
     * List <MuestraPorColorResponse> muestrasColor = new ArrayList<>(); //
     * MuestraPorColorResponse muestraAdd = new MuestraPorColorResponse();
     * 
     * for (Muestra muestras : repo.findAll()) { if(muestras.equals(color)){
     * 
     * 
     * muestrasColor.add(muestras); }
     * 
     * 
     * }
     * 
     * 
     * 
     * }
     */

    public Muestra buscarMuestraMinima(Integer boyaId) {

        Boya boya = boyaServece.buscarPorId(boyaId);

        List<Muestra> muestras = boya.getMuestras();

        Muestra muestraMinima = muestras.stream().min(Comparator.comparing(Muestra::getAlturaNivelDelMar))
                .orElseThrow(NoSuchElementException::new);

        return muestraMinima;

    }
    // https://www.baeldung.com/java-collection-min-max <--- Explicacion

}
