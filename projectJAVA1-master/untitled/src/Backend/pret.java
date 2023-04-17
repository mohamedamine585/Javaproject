package Backend;
import java.time.LocalDate;

public class pret {
    private int code_pret;
    private int id_lec;
    private int id_ouvrage;
    private LocalDate debut_pret;
    private LocalDate fin_pret;
    private int id_personnel;
    //constructeur avec para
    pret(int code_pret,int id_lec,int id_ouvrage,LocalDate debut_pret,
         LocalDate fin_pret,int id_personnel)
    {
        this.code_pret=code_pret;
        this.id_lec=id_lec;
        this.id_ouvrage=id_ouvrage;
        this.debut_pret=debut_pret;
        this.fin_pret=fin_pret;
        this.id_personnel=id_personnel;
    };

    int getId_lec(){
        return id_lec;
    }
    int getId_ouvrage(){
        return  id_ouvrage;

    }
    int getCode_pret(){
        return code_pret ;
    }
    int getId_personnel(){
        return  id_personnel;
    }

}
