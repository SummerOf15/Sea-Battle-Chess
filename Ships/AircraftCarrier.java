package Ships;

public class AircraftCarrier extends AbstractShip {

    AircraftCarrier(String nom){
        super(nom, NavireType.AIRCRAFTCARRIER,5, Orientation.EAST);
    }
}
