package Ships;

public class BattleShip extends AbstractShip {

    BattleShip(char nom){
        super(nom, NavireType.BATTALESHIP,4, Orientation.EAST);
    }
}
