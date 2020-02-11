package Ships;

public enum NavireType {
    DESTROYER(2, "Frégate"),
    SUBMARINE(3, "Sous-marin"),
    BATTLESHIP(4, "Croiseur"),
    AIRCRAFTCARRIER(5, "Porte-avion");
    /* ***
     * Attributs
     */
    private int value;
    private String label;

    /* ***
     * Constructeur
     */
    NavireType(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public int getTypeValue(){
        return value;
    }
}
