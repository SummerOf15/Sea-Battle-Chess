package Ships;

public class BattleShip extends AbstractShip {

    BattleShip(String nom){
        super(nom, NavireType.BATTALESHIP,4, Orientation.EAST);
    }
}
