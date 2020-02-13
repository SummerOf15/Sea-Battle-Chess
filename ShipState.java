import Ships.AbstractShip;

import java.io.Serializable;

public class ShipState implements Serializable {
    private AbstractShip ship;
    private boolean struck;

    ShipState(){
        ship=null;
        struck=false;
    }
    public boolean isStruck(){
        return struck;
    }
    public void setStruck(boolean hit){
        struck=hit;
    }

    public void setShip(AbstractShip s){
        ship=s;
    }
    public AbstractShip getShip(){
        return ship;
    }

    public String toString(){
        String s;
        if(struck)
            s=ColorFonts.ANSI_RED+ship.getNavireType().toString()+ColorFonts.ANSI_RESET;
        else
            s=ship.getNavireType().toString();
        return s;
    }

}
