import Ships.*;

public class TestBoard {
    public static void main(String [] args){
        Board board=new Board("A");
        Submarine submarine=new Submarine('S');
        board.putShip(submarine,2,3);
        board.setHit(true,4,4);
        board.print();
        System.out.println(board.hasShip(7,3));
    }
}
