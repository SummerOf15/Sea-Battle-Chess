import Ships.*;

public class TestBoard {
    public static void main(String [] args){
        Board board=new Board("A");
        Submarine submarine=new Submarine('S');
        Destroyer destroyer=new Destroyer('D');
        board.putShip(submarine,4,4);
        board.putShip(destroyer,1,3);
        board.setHit(true,4,4);
        board.print();
        System.out.println(board.hasShip(7,3));
    }
}
