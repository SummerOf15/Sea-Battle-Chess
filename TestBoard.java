import Ships.*;

public class TestBoard {
    public static void main(String [] args){
        Board board=new Board("A");
        Submarine submarine=new Submarine('S');
        Submarine submarine1=new Submarine('F');
        Destroyer destroyer=new Destroyer('D');
        board.putShip(submarine,4,4);
        board.putShip(submarine1,4,5);
        board.putShip(destroyer,1,3);
        board.setHit(true,4,4);
        board.setHit(true,5,4);
        board.setHit(true,6,4);
        board.setHit(true,6,5);
        board.print();
        System.out.println("sunk:"+submarine.isSunk());
        System.out.println("sunk1:"+submarine1.isSunk());
        System.out.println(board.hasShip(7,3));
    }
}
