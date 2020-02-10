package Ships;

public abstract class AbstractShip {
    private NavireType navireType;
    private char navireNom;
    private int navireLength;
    private Orientation navireOri;
    private int strikeCount;

    AbstractShip(char nom, NavireType type, int length, Orientation ori){
        navireNom=nom;
        navireType=type;
        navireLength=length;
        navireOri=ori;
    }

    public int getNavireLength(){
        return navireLength;
    }

    public NavireType getNavireType() {
        return navireType;
    }

    public Orientation getNavireOri() {
        return navireOri;
    }

    public char getNavireNom() { return navireNom; }

    public void addStrike(){
        strikeCount++;
    }

    public boolean isSunk(){
        return (strikeCount>=navireLength);
    }
}
