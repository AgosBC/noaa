package ar.com.ada.api.noaa.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.noaa.entities.Boya;
import ar.com.ada.api.noaa.entities.Muestra;
import ar.com.ada.api.noaa.entities.Boya.ColorLuzEnum;
import ar.com.ada.api.noaa.repos.BoyaRepository;
import ar.com.ada.api.noaa.repos.MuestraRepository;

@Service
public class MuestraService {

    @Autowired
    MuestraRepository repo;

    @Autowired
    BoyaRepository boyaRepository;

    @Autowired
    BoyaServece boyaServece;

    public void crearMuestra(Integer boyaId, Date horario, String matricula, Double longitud, Double latitud,
            Double alturaNivelDelMar) {

        Muestra muestra = new Muestra();

        muestra.setHorario(horario);
        muestra.setMatricula(matricula);
        muestra.setLongitud(longitud);
        muestra.setLatitud(latitud);
        muestra.setAlturaNivelDelMar(alturaNivelDelMar);

        Boya boya = boyaServece.buscarPorId(boyaId);

        if (alturaNivelDelMar > -50 && alturaNivelDelMar < 50) {

            boya.setColorLuzId(ColorLuzEnum.AMARILLO);

        }
        if (alturaNivelDelMar > -100 && alturaNivelDelMar < 100) {
            boya.setColorLuzId(ColorLuzEnum.ROJO);
        } else {
            boya.setColorLuzId(ColorLuzEnum.VERDE);
        }

        boya.agregarMuestra(muestra);

        repo.save(muestra);

    }

    public void borrar(Muestra muestra){
        repo.delete(muestra);
    }

    public Muestra buscarPorId(Integer id) {
        return null;
    }

   



}
