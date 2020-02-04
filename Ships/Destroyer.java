package Ships;

public class Destroyer extends AbstractShip {

    public Destroyer(char nom){
        super(nom,NavireType.DESTROYER,2,Orientation.EAST);
    }
}
