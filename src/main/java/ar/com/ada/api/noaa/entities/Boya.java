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

    @Column(name = "color_luz")
    private String colorLuz;

    @Column(name = "longitud_instalacion")
    private Double longitudInstalacion;
    @Column(name = "latitud_instalacion")
    private Double latitudInstalacion;

    @JsonIgnore
    @OneToMany(mappedBy = "boya", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Muestra> muestras = new ArrayList<>();

    // @Column(name = "alerta_id")
    // private Integer alertaId;

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

    public String getColorLuz() {
        return colorLuz;
    }

    public void setColorLuz(String colorLuz) {
        this.colorLuz = colorLuz;
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

    /*
     * public AlertaEnum getAlertaId() { return AlertaEnum.parse(alertaId); }
     * 
     * public void setAlertaId(ColorLuzEnum alertaId) { this.alertaId =
     * alertaId.getValue(); }
     */

    public enum AlertaEnum {

        ALERTA_DE_KAIJU(1), ALERTA_DE_IMPACTO(2);

        private final Integer value;

        private AlertaEnum(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public static AlertaEnum parse(Integer id) {
            AlertaEnum status = null;
            for (AlertaEnum item : AlertaEnum.values()) {
                if (item.getValue().equals(id)) {
                    status = item;
                    break;
                }
            }
            return status;
        }
    }

}
