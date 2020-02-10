import Ships.AbstractShip;
import Ships.Orientation;
import sun.plugin2.util.ColorUtil;

public class Board implements IBoard{
    private String nom;
    private ShipState[][] chessBoard;// les navires
    private Hit[][] attack;// les frappes
    private int length;

    Board(String nom){
        this.nom=nom;
        chessBoard=new ShipState[10][10];
        attack=new Hit[10][10];
    }
    Board(String nom, int len){
        this.nom=nom;
        length=len;
        chessBoard=new ShipState[len][len];
        attack=new Hit[len][len];
    }

    public int getSize(){
        return length;
    }

    public void putShip(AbstractShip ship, int x, int y){
        int l=ship.getNavireLength();
        Orientation ori=ship.getNavireOri();
        try{
            switch (ori){
                case EAST:{
                    for(int i=l-1;i>=0;i--)
                        if(!hasShip(x+i,y))
                        {
                            chessBoard[y][x+i]=new ShipState();
                            chessBoard[y][x+i].setShip(ship);
                        }
                        else
                            throw new Exception("already has battles");
                    break;
                }
                case WEST:{
                    for(int i=l-1;i>=0;i--)
                        if(!hasShip(x-i,y))
                        {
                            chessBoard[y][x-i]=new ShipState();
                            chessBoard[y][x-i].setShip(ship);
                        }
                        else
                            throw new Exception("already has battles");
                    break;
                }
                case NORTH:{
                    for(int i=l-1;i>=0;i--)
                        if(!hasShip(x,y-i))
                        {
                            chessBoard[y-i][x]=new ShipState();
                            chessBoard[y-i][x].setShip(ship);
                        }
                        else
                            throw new Exception("already has battles");
                    break;
                }
                case SOUTH:{
                    for(int i=l-1;i>=0;i--)
                        if(!hasShip(x,y+i))
                        {
                            chessBoard[y+i][x]=new ShipState();
                            chessBoard[y+i][x].setShip(ship);
                        }
                        else
                            throw new Exception("already has battles");
                    break;
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public boolean hasShip(int x, int y){
        if(chessBoard[y][x]==null)
            return false;
        else
            return true;
    }

    public void setHit(boolean hit, int x, int y){
        if(chessBoard[y][x]==null){
            attack[y][x]=Hit.MISS;
        }
        else{
            attack[y][x]=Hit.STRIKE;
            chessBoard[y][x].getShip().addStrike();
        }
    }
    public Boolean getHit(int x, int y){
        if(chessBoard[y][x]==null)
            return false;
        else
            return chessBoard[y][x].isStruck();
    }

    public void print(){
        char c0='A';
        System.out.println("Frappes:");
        for(int k=0;k<attack[0].length;k++)
            System.out.print(" "+(char)((int)c0+k));
        System.out.println();
        for(int i=0;i<attack.length;i++){
            System.out.print(Integer.toString(i));
            for(Hit b:attack[i]){
                if(b==null)
                    System.out.print(". ");
                else if(b==Hit.MISS)
                    System.out.print("x ");
                else if(b==Hit.STRIKE)
                    System.out.print(ColorFonts.ANSI_RED+"x "+ColorFonts.ANSI_RESET);
            }
            System.out.println();
        }
        System.out.println("\nNavires:");
        for(int k=0;k<attack[0].length;k++)
            System.out.print(" "+(char)((int)c0+k));
        System.out.println();
        for(int i=0;i<chessBoard.length;i++){
            System.out.print(Integer.toString(i));
            for(int j=0;j<chessBoard[0].length;j++){
                if(chessBoard[i][j]==null)
                    System.out.print(". ");
                else
                    System.out.print(chessBoard[i][j].getShip().getNavireNom()+" ");
            }
            System.out.println();
        }
    }
}
