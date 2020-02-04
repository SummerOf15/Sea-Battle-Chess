import Ships.*;

public class TestBoard {
    public static void main(String [] args){
        Board board=new Board("A");
        Submarine submarine=new Submarine("ll");
        board.print();
    }
}
