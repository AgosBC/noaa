package ar.com.ada.api.noaa.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.noaa.entities.Boya;
import ar.com.ada.api.noaa.models.request.ColorBoyaRequest;
import ar.com.ada.api.noaa.models.response.GenericResponse;
import ar.com.ada.api.noaa.services.BoyaServece;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class BoyaController {

   
    @Autowired
    BoyaServece service;

    @PostMapping("/boyas")
    public ResponseEntity<?> crearBoya(@RequestBody Boya boya) {

        GenericResponse r = new GenericResponse();

        Boya nueva = service.crearBoya(boya.getLatitudInstalacion(), boya.getLongitudInstalacion());

        r.id = nueva.getBoyaId();
        r.isOk = true;
        r.message = "La boya ha sido creada con exito";

        return ResponseEntity.ok(r);

    }

   

    @GetMapping("/boyas")
    public ResponseEntity<List<Boya>> obtenerBoyas() {

        return ResponseEntity.ok(service.obtenerBoyas());
    }

    @GetMapping("/boyas/{id}")
    public ResponseEntity<Boya> buscarPorId(@PathVariable Integer id) {

        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping(value = "/boyas/{id}")
    public ResponseEntity<GenericResponse> actualizarColorBoya(@PathVariable Integer id,
            @RequestBody ColorBoyaRequest color) {

        GenericResponse r = new GenericResponse();

        Boya boya = service.buscarPorId(id);

        boya.setColorLuz(color.color);

        service.guardar(boya);

        r.isOk = true;
        r.message = "Color de boya actualizado";
        return ResponseEntity.ok(r);
    }

  
    

}
