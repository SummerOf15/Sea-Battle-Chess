package Ships;

import javafx.scene.paint.Color;

import java.io.Serializable;

public abstract class AbstractShip implements Serializable {
    private NavireType navireType;
    private char navireNom;
    private int navireLength;
    private Orientation navireOri;
    private int strikeCount;
    private int navireColor;

    AbstractShip(char nom, NavireType type, int length, Orientation ori){
        navireNom=nom;
        navireType=type;
        navireLength=length;
        navireOri=ori;
        strikeCount=0;
    }
    AbstractShip(char nom, NavireType type, int length, Orientation ori, int color){
        navireNom=nom;
        navireType=type;
        navireLength=length;
        navireOri=ori;
        strikeCount=0;
        navireColor=color;
    }
    public void setNavireOri(Orientation ori){
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

    public int getStrike(){
        return strikeCount;
    }

    public boolean isSunk(){
        return (strikeCount>=navireLength);
    }

    public int getNavireColor(){
        return navireColor;
    }
}
