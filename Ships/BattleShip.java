package Ships;

public class BattleShip extends AbstractShip {

    public BattleShip(char nom){
        super(nom, NavireType.BATTLESHIP,4, Orientation.EAST);
    }
}
