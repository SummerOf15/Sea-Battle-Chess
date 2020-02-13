package Ships;

public class BattleShip extends AbstractShip {

    public BattleShip(){
        super('B', NavireType.BATTLESHIP,4, Orientation.EAST);
    }
    public BattleShip(char nom, Orientation ori){
        super(nom, NavireType.BATTLESHIP,4, ori);
    }
    public BattleShip(char nom){
        super(nom, NavireType.BATTLESHIP,4, Orientation.EAST);
    }
}
