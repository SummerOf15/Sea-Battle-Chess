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
        // take into account the ship is sunk
        if(chessBoard[y][x]==null || chessBoard[y][x].getShip().isSunk())
            return false;
        else
            return true;
    }

    public void setHit(boolean hit, int x, int y){
        // if there is not ship
        if(chessBoard[y][x]==null){
            attack[y][x]=Hit.MISS;
        }
        else{
            // if there is a ship
            attack[y][x]=Hit.STRIKE;
            chessBoard[y][x].getShip().addStrike();
            // when the ship is totally destroyed
            if(chessBoard[y][x].getShip().isSunk()){
                attack[y][x]=Hit.fromInt(chessBoard[y][x].getShip().getNavireType().getTypeValue());
            }
        }
    }
    public Boolean getHit(int x, int y){
        return (attack[y][x]!=null);
    }

    public Hit sendHit(int x, int y){
        if(!getHit(x,y))
            setHit(true,x,y);
        return attack[y][x];
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
            System.out.print(Integer.toString(i));
            for(int j=0;j<chessBoard[0].length;j++){
                if(chessBoard[i][j]==null)
                    System.out.print(". ");
                else if(chessBoard[i][j].getShip().isSunk())
                    System.out.print(ColorFonts.ANSI_RED+chessBoard[i][j].getShip().getNavireNom()+" "+ColorFonts.ANSI_RESET);
                else
                    System.out.print(chessBoard[i][j].getShip().getNavireNom()+" ");
            }
            System.out.println();
        }
    }
}
