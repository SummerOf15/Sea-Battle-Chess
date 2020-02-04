public class Board {
    private String nom;
    private char[][] chessBoard;// les navires
    private boolean[][] attack;// les frappes

    Board(String nom){
        this.nom=nom;
        chessBoard=new char[10][10];
        attack=new boolean[10][10];
    }
    Board(String nom, int height, int width){
        this.nom=nom;
        chessBoard=new char[height][width];
        attack=new boolean[height][width];
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
                if(!attack[i][j])
                    System.out.print(". ");
                else
                    System.out.print("x ");
            }
            System.out.println();
        }
    }
}
