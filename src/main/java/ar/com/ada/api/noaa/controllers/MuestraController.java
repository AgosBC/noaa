package ar.com.ada.api.noaa.controllers;

import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.noaa.entities.Muestra;
import ar.com.ada.api.noaa.models.request.MuestraRequest;
import ar.com.ada.api.noaa.models.response.GenericResponse;
import ar.com.ada.api.noaa.models.response.MuestraMinimaResponse;
import ar.com.ada.api.noaa.models.response.MuestraPorColorResponse;
import ar.com.ada.api.noaa.models.response.MuestraResponse;
import ar.com.ada.api.noaa.services.BoyaServece;
import ar.com.ada.api.noaa.services.MuestraService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class MuestraController {

    @Autowired
    MuestraService service;

    @PostMapping(value = "/muestras")
    public ResponseEntity<MuestraResponse> postMuestra(@RequestBody MuestraRequest request) {

        MuestraResponse r = new MuestraResponse();

        Muestra muestra = service.crearMuestra(request.boyaId, request.horario, request.matricula, request.latitud,
                request.longitud, request.alturaNivelDelMar);

        r.id = muestra.getMuestraId();
        r.color = muestra.getBoya().getColorLuz();

        return ResponseEntity.ok(r);

    }

    @GetMapping("/muestras/boyas/{idBoya}")
    public ResponseEntity<List<Muestra>> getMuestraDeBoya(@PathVariable Integer idBoya) {

        return ResponseEntity.ok(service.buscarMuestras(idBoya));

    }

    @DeleteMapping("/muestra/{id}")
    public ResponseEntity<GenericResponse> borrarMuestra(@PathVariable Integer id) {

        GenericResponse r = new GenericResponse();

        Muestra muestra = service.buscarPorId(id);
        service.setColorAzul(muestra);

        r.isOk = true;
        r.message = "Muestra borrada";
        return ResponseEntity.ok(r);

    }

    @GetMapping("/muestras/colores/{color}")
    public ResponseEntity<List<MuestraPorColorResponse>> traerMuestrasPorColor(@PathVariable String color){
        return ResponseEntity.ok(service.buscarMuestrasPorColor(color));
    }



    @GetMapping("/muestras/minima/{idBoya}")
    public ResponseEntity<?> getMuestraMinBoya(@PathVariable Integer idBoya) {

        MuestraMinimaResponse response = new MuestraMinimaResponse();

        Muestra muestra = service.buscarMuestraMinima(idBoya);

        response.color = muestra.getBoya().getColorLuz();
        response.alturaNivelDelMarMinima = muestra.getAlturaNivelDelMar();
        response.horario = muestra.getHorario();

        return ResponseEntity.ok(response);

    }

    /**
     * GET /muestras/colores/{color} : que devuelva la lista de muestras de un color
     * en el siguiente formato JSON Array: [{ “boyaId”: 1232, “horario”:
     * “2020-08-05T20:20:10”, “alturaNivelDelMar”: 29 }, { “boyaId”: 124, “horario”:
     * “2020-08-01T20:22:10”, “alturaNivelDelMar”: 55 }]
     */

    /**
     * PASOS LO QUE PIDE ES QUE SE DEVUELVA UNA LISTA DE MUESTRAS CON DETERMINADA
     * INFO BOYAID HORARIO ALTURANIVELDELMAR
     */

    /*
     * @GetMapping("/muestras/colores/{color}") public ResponseEntity<List<Muestra>>
     * getListColor(@PathVariable ColorLuzEnum color){
     * 
     * service.buscarMuestraPorColor(color); }
     * 
     * @GetMapping(value="/muestras/color/{color}") public
     * ResponseEntity<List<MuestraPorColorResponse>>
     * getMuestrasPorColor(@PathVariable ColorLuzEnum color) {
     * 
     * service.buscarMuestraPorColor(color);
     * 
     * 
     * return ResponseEntity.ok(); }
     */

}
