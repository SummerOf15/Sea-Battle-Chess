package Ships;

public class AircraftCarrier extends AbstractShip {

    public AircraftCarrier(){super('A', NavireType.AIRCRAFTCARRIER, 5, Orientation.EAST);}
    public AircraftCarrier(char nom, Orientation ori){
        super(nom, NavireType.AIRCRAFTCARRIER,5, ori);
    }
    public AircraftCarrier(char nom) {
        super(nom, NavireType.AIRCRAFTCARRIER, 5, Orientation.EAST);
    }
}
