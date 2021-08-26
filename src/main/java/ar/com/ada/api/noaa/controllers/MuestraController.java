package ar.com.ada.api.noaa.controllers;

import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.noaa.entities.Muestra;
import ar.com.ada.api.noaa.models.request.MuestraRequest;
import ar.com.ada.api.noaa.models.response.GenericResponse;
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
        r.color = muestra.getBoya().getColorLuzId();

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
        service.borrar(muestra);

        r.isOk = true;
        r.message = "muestra borrada";
        return ResponseEntity.ok(r);

    }
}
