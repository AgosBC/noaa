package ar.com.ada.api.noaa.controllers;

import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.noaa.entities.Muestra;
import ar.com.ada.api.noaa.models.response.GenericResponse;
import ar.com.ada.api.noaa.models.response.MuestraResponse;
import ar.com.ada.api.noaa.services.BoyaServece;
import ar.com.ada.api.noaa.services.MuestraService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class MuestraController {
    

@Autowired
MuestraService service;



@PostMapping(value="/muestras")
public ResponseEntity<MuestraResponse> postMuestra(@RequestBody Muestra muestra) {
    
    MuestraResponse r = new MuestraResponse();

      

    service.crearMuestra(muestra.getBoyaId().getBoyaId(), muestra.getHorario(),muestra.getMatricula(),muestra.getLongitud(), muestra.getLatitud(), muestra.getAlturaNivelDelMar());

    r.id = muestra.getMuestraId();
    r.color = muestra.getBoyaId().getColorLuzId();

    

    return ResponseEntity.ok(r);

    
} 

@DeleteMapping("/muestra7{id}")
public ResponseEntity<GenericResponse> borrarMuestra(@PathVariable Integer id) {

    
    GenericResponse r = new GenericResponse();

    Muestra muestra = service.buscarPorId(id);
    service.borrar(muestra);

    r.isOk = true;
    r.message = "muestra borrada";
    return  ResponseEntity.ok(r);




}
}
