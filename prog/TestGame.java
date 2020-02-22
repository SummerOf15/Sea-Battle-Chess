package prog;

import Ships.*;

public class TestGame {
    public static void main(String args[]){
        Game game=new Game();
        game.init();
        game.run();
    }

    public void example1(){
        Board board=new Board("p1");
        Board board1=new Board("p2");
        AbstractShip ships1[]=new AbstractShip[3];
        ships1[0]=new Destroyer('D');
        ships1[1]=new BattleShip('B');
        ships1[2]=new Submarine('S');
        AbstractShip ships2[]=new AbstractShip[3];
        ships2[0]=new Destroyer('D');
        ships2[1]=new BattleShip('B');
        ships2[2]=new Submarine('S');
        BattleShipsAI ai1=new BattleShipsAI(board,board1);
        BattleShipsAI ai2=new BattleShipsAI(board1,board);
        ai1.seed=2;
        ai2.seed=3;
        ai1.putShips(ships1);
        ai2.putShips(ships2);
        board.print();
        board1.print();

        int numDestroy1=0;
        int numDestroy2=0;
        while(numDestroy1<ships1.length && numDestroy2<ships2.length){
            int []coords=new int[2];
            Hit h1=ai1.sendHit(coords);
            if(h1.getValue()>0){
                System.out.println("ai1 destroy->"+h1.toString()+":"+coords[0]+","+coords[1]);
                numDestroy1++;
            }
            if(h1.getValue()==-2){
                System.out.println("ai1->"+h1.toString()+":"+coords[0]+","+coords[1]);
            }
            Hit h2=ai2.sendHit(coords);
            if(h2.getValue()>0)
            {
                System.out.println("ai2 destroy->"+h2.toString()+":"+coords[0]+","+coords[1]);
                numDestroy2++;
            }
            if(h2.getValue()==-2){
                System.out.println("ai2->"+h2.toString()+":"+coords[0]+","+coords[1]);
            }
        }
        board.print();
        board1.print();
    }
}
