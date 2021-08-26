package ar.com.ada.api.noaa.entities;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "boya")
public class Boya {
    @Id
    @Column(name = "boya_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer boyaId;

    @Column(name = "color_luz_id")
    private Integer colorLuzId;

    @Column(name = "longitud")
    private Double longitudInstalacion;
    @Column(name = "latitud")
    private Double latitudInstalacion;

    @JsonIgnore
    @OneToMany(mappedBy = "boya", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Muestra> muestras = new ArrayList<>();

    public void agregarMuestra(Muestra muestra) {
        this.muestras.add(muestra);
        muestra.setBoya(this);
    }

    public Integer getBoyaId() {
        return boyaId;
    }

    public void setBoyaId(Integer boyaId) {
        this.boyaId = boyaId;
    }

    
    public Double getLongitudInstalacion() {
        return longitudInstalacion;
    }

    public void setLongitudInstalacion(Double longitudInstalacion) {
        this.longitudInstalacion = longitudInstalacion;
    }

    public Double getLatitudInstalacion() {
        return latitudInstalacion;
    }

    public void setLatitudInstalacion(Double latitudInstalacion) {
        this.latitudInstalacion = latitudInstalacion;
    }

    public List<Muestra> getMuestras() {
        return muestras;
    }

    public void setMuestras(List<Muestra> muestras) {
        this.muestras = muestras;
    }

    public ColorLuzEnum getColorLuzId() {
        return ColorLuzEnum.parse(colorLuzId);
    }

    public void setColorLuzId(ColorLuzEnum colorLuzId) {
        this.colorLuzId = colorLuzId.getValue();
    }

    public enum ColorLuzEnum {

        AZUL(1), VERDE(2), AMARILLO(3), ROJO(4);

        private final Integer value;

        private ColorLuzEnum(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public static ColorLuzEnum parse(Integer id) {
            ColorLuzEnum status = null; // Default
            for (ColorLuzEnum item : ColorLuzEnum.values()) {
                if (item.getValue().equals(id)) {
                    status = item;
                    break;
                }
            }
            return status;
        }
    }

}
