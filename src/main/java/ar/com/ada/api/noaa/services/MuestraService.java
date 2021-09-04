package ar.com.ada.api.noaa.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.noaa.entities.Boya;
import ar.com.ada.api.noaa.entities.Muestra;
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
            boya.setColorLuz("rojo");
        } else if (alturaNivelDelMar < -50 || alturaNivelDelMar > 50) {
            boya.setColorLuz("amarillo");
        } else {
            boya.setColorLuz("verde");
        }

        boya.agregarMuestra(muestra);

        return repo.save(muestra);

    }

    

    public void setColorAzul(Muestra muestra){
        muestra.getBoya().setColorLuz("azul");
        repo.save(muestra);
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

    public String colorMuestra(Double alturaNivelDelMar){
       
        if (alturaNivelDelMar < -100 || alturaNivelDelMar > 100) {
            return ("rojo");
        } else if (alturaNivelDelMar < -50 || alturaNivelDelMar > 50) {
            return ("amarillo");
        } else {
            return ("verde");
        }
    }

    public List<MuestraPorColorResponse> buscarMuestrasPorColor(String color) {

        List<MuestraPorColorResponse> muestrasPorColor = new ArrayList<>();
        MuestraPorColorResponse muestraPorColor = new MuestraPorColorResponse();

        
        //List<Boya> boyasColor = boyaServece.buscarPorColor(color);

        for (Muestra muestra : repo.findAll()){

            if (colorMuestra(muestra.getAlturaNivelDelMar()).equals(color)){

                muestraPorColor.boyaId = muestra.getBoya().getBoyaId();
                muestraPorColor.horario = muestra.getHorario();
                muestraPorColor.alturaNivelDelMar = muestra.getAlturaNivelDelMar();                

                muestrasPorColor.add(muestraPorColor);//add(muestraPorColor);
                
            }
            
        }
        return muestrasPorColor;
        
    }
    

    public Muestra buscarMuestraMinima(Integer boyaId) {

        Boya boya = boyaServece.buscarPorId(boyaId);

        List<Muestra> muestras = boya.getMuestras();

        Muestra muestraMinima = muestras.stream().min(Comparator.comparing(Muestra::getAlturaNivelDelMar))
                .orElseThrow(NoSuchElementException::new); // usar en el punto de buscar lista de boyas por color, que la variable sea de tipo lista? 

        return muestraMinima;

    }
    // https://www.baeldung.com/java-collection-min-max <--- Explicacion

}
