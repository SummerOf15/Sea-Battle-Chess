package Ships;

public class Submarine extends AbstractShip {

    public Submarine(){
        super('S',NavireType.SUBMARINE,3,Orientation.EAST);
    }
    public Submarine(char nom, Orientation ori){
        super(nom,NavireType.SUBMARINE,3,ori);
    }
    public Submarine(char nom){
        super(nom,NavireType.SUBMARINE,3,Orientation.EAST);
    }

}
