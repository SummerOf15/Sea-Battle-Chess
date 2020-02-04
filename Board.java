import Ships.AbstractShip;
import Ships.Orientation;

public class Board implements IBoard{
    private String nom;
    private char[][] chessBoard;// les navires
    private boolean[][] attack;// les frappes
    private int length;

    Board(String nom){
        this.nom=nom;
        chessBoard=new char[10][10];
        attack=new boolean[10][10];
    }
    Board(String nom, int len){
        this.nom=nom;
        length=len;
        chessBoard=new char[len][len];
        attack=new boolean[len][len];
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
                            chessBoard[y][x+i]=ship.getNavireNom();
                        else
                            throw new Exception("already has battles");
                    break;
                }
                case WEST:{
                    for(int i=l-1;i>=0;i--)
                        if(!hasShip(x-i,y))
                            chessBoard[y][x-i]=ship.getNavireNom();
                        else
                            throw new Exception("already has battles");
                    break;
                }
                case NORTH:{
                    for(int i=l-1;i>=0;i--)
                        if(!hasShip(x,y-i))
                            chessBoard[y-i][x]=ship.getNavireNom();
                        else
                            throw new Exception("already has battles");
                    break;
                }
                case SOUTH:{
                    for(int i=l-1;i>=0;i--)
                        if(!hasShip(x,y+i))
                            chessBoard[y+i][x]=ship.getNavireNom();
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
        if(chessBoard[y][x]==(char)(0))
            return false;
        else
            return true;
    }
    public void setHit(boolean hit, int x, int y){
        attack[y][x]=hit;
    }
    public Boolean getHit(int x, int y){
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
            for(boolean b:attack[i]){
                if(!b)
                    System.out.print(". ");
                else
                    System.out.print("x ");
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
                if(chessBoard[i][j]==(char)(0))
                    System.out.print(". ");
                else
                    System.out.print(chessBoard[i][j]+" ");
            }
            System.out.println();
        }
    }
}
