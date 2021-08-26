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
                
                
                if(alturaNivelDelMar > -50 && alturaNivelDelMar < 50){

                    boya.setColorLuzId(ColorLuzEnum.AMARILLO);

                } if (alturaNivelDelMar > -100 && alturaNivelDelMar < 100){
                    boya.setColorLuzId(ColorLuzEnum.ROJO);
                }
                else {
                    boya.setColorLuzId(ColorLuzEnum.VERDE);
                }
            
                
                boya.agregarMuestra(muestra);

                repo.save(muestra);

            


                


    }


       
}
/**POST /muestras : que registre una muestra
RequestBody {
“boyaId”: 32,
“horario”: “2020-08-08T22:25:30”, //La separación de FECHA y HORA es por “T”
“matricula”: “99D9AAK”
“latitud”: -17.44681203,
“longitud”: -113.16478854,
“alturaNivelDelMar”: 50
}

Respuesta Esperada(JSON):
{
“id”: 25 //O cualquier número de muestra que devuelva.
“color”: “Un Color” //Este será el color que deberá cambiar la luz de la boya
}

Nota: Si cuando se carga una muestra nueva en una boya, la alturaNivelDelMar es de MENOS de
-50(menos 50) o de MAS de +50 (más 50), debera devolver “AMARILLO” en el color.
En el caso de que sea menor a -100 o mayor a +100 el color deberá ser ROJO.
Si no, el color deberá devolver es VERDE*/