package Ships;

public class BattleShip extends AbstractShip {

    BattleShip(char nom){
        super(nom, NavireType.BATTLESHIP,4, Orientation.EAST);
    }
}
