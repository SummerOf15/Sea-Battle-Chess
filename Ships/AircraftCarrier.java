package Ships;

public class AircraftCarrier extends AbstractShip {

    AircraftCarrier(char nom){
        super(nom, NavireType.AIRCRAFTCARRIER,5, Orientation.EAST);
    }
}
