import Ships.AbstractShip;
import Ships.Orientation;

import java.io.Serializable;

public class Board implements IBoard, Serializable {
    private String nom;
    private ShipState[][] chessBoard;// les navires
    private Hit[][] attack;// les frappes
    private int length;
    public boolean setDone;

    Board(String nom){
        setDone=false;
        this.nom=nom;
        length=2;
        chessBoard=new ShipState[length][length];
        attack=new Hit[length][length];
    }
    Board(String nom, int len){
        setDone=false;
        this.nom=nom;
        length=len;
        chessBoard=new ShipState[len][len];
        attack=new Hit[len][len];
    }

    public int getSize(){
        return length;
    }

    public void putShip(AbstractShip ship, int x, int y){
        setDone=false;
        int l=ship.getNavireLength();
        Orientation ori=ship.getNavireOri();
        try{
            switch (ori){
                case EAST:{
                    for(int i=l-1;i>=0;i--){
                        if(hasShip(x+i,y))
                        {
                            throw new Exception("already has battles");
                        }
                    }
                    for(int i=l-1;i>=0;i--){
                        chessBoard[y][x+i]=new ShipState();
                        chessBoard[y][x+i].setShip(ship);
                    }
                    setDone=true;
                    break;
                }
                case WEST:{
                    for(int i=l-1;i>=0;i--)
                        if(hasShip(x-i,y))
                        {
                            throw new Exception("already has battles");
                        }
                    for(int i=l-1;i>=0;i--){
                        chessBoard[y][x-i]=new ShipState();
                        chessBoard[y][x-i].setShip(ship);
                    }
                    setDone=true;
                    break;
                }
                case NORTH:{
                    for(int i=l-1;i>=0;i--)
                        if(hasShip(x,y-i))
                        {
                            throw new Exception("already has battles");
                        }
                    for(int i=l-1;i>=0;i--){
                        chessBoard[y-i][x]=new ShipState();
                        chessBoard[y-i][x].setShip(ship);
                    }
                    setDone=true;
                    break;
                }
                case SOUTH:{
                    for(int i=l-1;i>=0;i--)
                        if(hasShip(x,y+i))
                        {
                            throw new Exception("already has battles");
                        }
                    for(int i=l-1;i>=0;i--)
                    {
                        chessBoard[y+i][x]=new ShipState();
                        chessBoard[y+i][x].setShip(ship);
                    }
                    setDone=true;
                    break;
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e+",put ship "+ship.getNavireNom()+" failed");
            setDone=false;
        }

    }

    public boolean hasShip(int x, int y){
        // take into account the ship is sunk
        if(chessBoard[y][x]==null || chessBoard[y][x].getShip().isSunk())
            return false;
        else
            return true;
    }

    public void setHit(boolean hit, int x, int y){
        // set where I have attacked
        if(hit){
            attack[y][x]=Hit.STRIKE;
        }
        else{
            attack[y][x]=Hit.MISS;
        }
    }
    public Boolean getHit(int x, int y){
        return (attack[y][x]!=null);
    }

    public Hit sendHit(int x, int y){
        Hit hit;

        if(chessBoard[y][x]==null){
            hit=Hit.MISS;
        }
        else{
            // if there is a ship
            hit=Hit.STRIKE;
            chessBoard[y][x].getShip().addStrike();
//            System.out.print("strike:"+chessBoard[y][x].getShip().getStrike());
            // when the ship is totally destroyed
            if(chessBoard[y][x].getShip().isSunk()){
                hit=Hit.fromInt(chessBoard[y][x].getShip().getNavireType().getTypeValue());
            }
        }

        return hit;
    }

    public void print(){
        char c0='A';
        System.out.println(nom+":->");
        System.out.println("Frappes:");
        for(int k=0;k<attack[0].length;k++)
            System.out.print(" "+(char)((int)c0+k));
        System.out.println();
        for(int i=0;i<attack.length;i++){
            System.out.print(Integer.toString(i+1));
            for(Hit b:attack[i]){
                if(b==null)
                    System.out.print(". ");
                else if(b==Hit.MISS)
                    System.out.print("x ");
                else
                    System.out.print(ColorFonts.ANSI_RED+"x "+ColorFonts.ANSI_RESET);
            }
            System.out.println();
        }
        System.out.println("\nNavires:");
        for(int k=0;k<attack[0].length;k++)
            System.out.print(" "+(char)((int)c0+k));
        System.out.println();
        for(int i=0;i<chessBoard.length;i++){
            System.out.print(Integer.toString(i+1));
            for(int j=0;j<chessBoard[0].length;j++){
                if(chessBoard[i][j]==null)
                    System.out.print(". ");
//                else if(chessBoard[i][j].getShip().isSunk())
//                    System.out.print(ColorFonts.ANSI_RED+chessBoard[i][j].getShip().getNavireNom()+" "+ColorFonts.ANSI_RESET);
                else
                    System.out.print(chessBoard[i][j].getShip().getNavireNom()+" ");
            }
            System.out.println();
        }
    }
}
