package Ships;

public abstract class AbstractShip {
    protected NavireType navireType;
    protected String navireNom;
    protected int navireLength;
    protected Orientation navireOri;

    AbstractShip(String nom, NavireType type, int length, Orientation ori){
        navireNom=nom;
        navireType=type;
        navireLength=length;
        navireOri=ori;
    }

}
