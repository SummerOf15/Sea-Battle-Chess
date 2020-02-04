package Ships;

public class Submarine extends AbstractShip {

    public Submarine(String nom){
        super(nom,NavireType.SUBMARINE,3,Orientation.EAST);
    }
}
