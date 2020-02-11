package Ships;

public class Destroyer extends AbstractShip {

    public Destroyer(char nom, Orientation ori){
        super(nom,NavireType.DESTROYER,2,ori);
    }
    public Destroyer(char nom){
        super(nom,NavireType.DESTROYER,2,Orientation.EAST);
    }
}
