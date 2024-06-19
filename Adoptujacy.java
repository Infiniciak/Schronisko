
import javax.persistence.*;

@Entity
@Table(name = "adoptujacy")
public class Adoptujacy {
    @Id
    @Column(name = "idosoby")
    private Long idosoby;

    @Column(name = "imie")
    private String imie;

    @Column(name = "nazwisko")
    private String nazwisko;

    @Column(name = "idzwierzaka")
    private Long idzwierzaka;

    @Column(name = "dataadopcji")
    private String dataadopcji;

    public Long getIdosoby() {
        return this.idosoby;
    }

    public void setIdosoby(Long idosoby) {
        this.idosoby = idosoby;
    }

    public String getImie() {
        return this.imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return this.nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public Long getIdzwierzaka() {
        return this.idzwierzaka;
    }

    public void setIdzwierzaka(Long idzwierzaka) {
        this.idzwierzaka = idzwierzaka;
    }

    public String getDataadopcji() {
        return this.dataadopcji;
    }

    public void setDataadopcji(String dataadopcji) {
        this.dataadopcji = dataadopcji;
    }
}
