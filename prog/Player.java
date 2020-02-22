package prog;

import java.io.Serializable;
import java.util.List;
import Ships.AbstractShip;
import Ships.Orientation;

public class Player implements Serializable{
    /* **
     * Attributs
     */
    protected Board board;
    protected Board opponentBoard;
    protected int destroyedCount;
    protected AbstractShip[] ships;
    protected boolean lose;
    /* **
     * Constructeur
     */
    public Player(Board board, Board opponentBoard, List<AbstractShip> ships) {
        this.board = board;
        this.ships = ships.toArray(new AbstractShip[0]);
        this.opponentBoard = opponentBoard;
    }

    /* **
     * Méthodes
     */

    /**
     * Read keyboard input to get ships coordinates. Place ships on given coodrinates.
     */
    public void putShips() {
        boolean done = false;
        int i = 0;

        do {
            AbstractShip s = ships[i];
            String msg = String.format("placer %d : %s(%d)", i + 1, s.getNavireNom(), s.getNavireLength());
            System.out.println(msg);
            InputHelper.ShipInput res = InputHelper.readShipInput();
            s.setNavireOri(res.orientation);
            board.putShip(s,res.x,res.y);
            if(board.setDone)
            {
                ++i;
                done = i == ships.length;

                board.print();
            }
        } while (!done);
    }

    /**
     * send hit to opponent board
     * @param coords must have the length of 2, store the x and y
     * @return the last touched hit
     */
    public Hit sendHit(int[] coords) {
        boolean done=false;
        Hit hit = null;

        if (coords == null || coords.length < 2) {
            throw new IllegalArgumentException("must provide an initialized array of size 2");
        }

        do {
            System.out.println("où frapper?");
            InputHelper.CoordInput hitInput = InputHelper.readCoordInput();
            // TODO call sendHit on this.opponentBoard
            // all user input is untrustworthy
            if(hitInput.x<0 || hitInput.x>board.getSize()-1 || hitInput.y<0 || hitInput.y>board.getSize()-1)
                continue;

            hit=opponentBoard.sendHit(hitInput.x,hitInput.y);
            board.setHit(hit!=Hit.MISS,hitInput.x,hitInput.y);
            // TODO : Game expects sendHit to return BOTH hit result & hit coords.
            // return hit is obvious. But how to return coords at the same time ?
//            if(hit!=Hit.MISS){
            coords[0]=hitInput.x;
            coords[1]=hitInput.y;
            done=true;
//            }
        } while (!done);
        return hit;
    }

    public Hit attack(int x, int y) {
        Hit hit = null;
        if(x<0 || x>board.getSize()-1 || y<0 || y>board.getSize()-1)
            return hit;
        hit=opponentBoard.sendHit(x,y);
        board.setHit(hit!=Hit.MISS,x,y);
        return hit;
    }
    public boolean putship(AbstractShip ship, int x, int y){
        board.putShip(ship,x,y);
        return board.setDone;
    }
    public AbstractShip[] getShips() {
        return ships;
    }

    public void setShips(AbstractShip[] ships) {
        this.ships = ships;
    }
    public void setDestroyedCount(int count){
        destroyedCount=count;
    }
    public void setLose(boolean lose){
        this.lose=lose;
    }
    public boolean getLoss(){
        return lose;
    }
    public Board getBoard(){
        return board;
    }
}
