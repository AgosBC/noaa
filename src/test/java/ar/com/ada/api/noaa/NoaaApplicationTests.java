package ar.com.ada.api.noaa;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import ar.com.ada.api.noaa.entities.Muestra;

@SpringBootTest
class NoaaApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void muestraMinima(){

		Muestra muestra1 = new Muestra();
		muestra1.setAlturaNivelDelMar(12.10);
		muestra1.setLatitud(12.15);

		Muestra muestra2 = new Muestra();
		muestra2.setAlturaNivelDelMar(151.10);
		muestra2.setLatitud(25.15);

		Muestra muestra3 = new Muestra();
		muestra3.setAlturaNivelDelMar(-14.10);
		muestra3.setLatitud(13.15);

		List<Muestra> muestras = new ArrayList<>();
		muestras.add(muestra1);
		muestras.add(muestra2);
		muestras.add(muestra3);

        
		Muestra minimoNivel = muestras.stream().min(Comparator.comparing(Muestra::getAlturaNivelDelMar)).orElseThrow(NoSuchElementException::new);

		assertEquals(muestra3, minimoNivel);
        
        
	}

}
