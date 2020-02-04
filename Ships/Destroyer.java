package Ships;

public class Destroyer extends AbstractShip {

    public Destroyer(String nom){
        super(nom,NavireType.DESTROYER,2,Orientation.EAST);
    }
}
