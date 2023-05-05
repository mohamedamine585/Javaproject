package Backend;

public class Avertissement {
    private int id_avertis;
    private int id_lec;
    private int code_prets;
    //constructeur avec para
    Avertissement(int id_avertis, int id_lecteur, int code_prets)
    {
        this.id_avertis=id_avertis;
        this.id_lec=id_lec;
        this.code_prets=code_prets;
    };

}
