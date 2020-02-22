package sample;

import Ships.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import prog.*;

import java.util.Arrays;
import java.util.List;


public class Controller {

    @FXML
    private Canvas canvas1;
    @FXML
    private Canvas canvas2;
    @FXML
    private MenuItem onePlayer;
    @FXML
    private MenuItem mAbout;

    private final int len=10;
    private Color colorChessboard = Color.valueOf("#FBE39B");
    private Color colorLine = Color.valueOf("#884B09");
    private Color colorMark = Color.valueOf("#FF7F27");
    private String[] markX = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U"};
    private String[] markY = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21"};

    private GraphicsContext gc1,gc2;
    private double gapX, gapY;
    private double broadPadding = 20;
    private Player player1;
    private Player player2;
    private List<AbstractShip> ships1;
    private int indShip=0;// the index of ship to be put
    /*
     * (non-Javadoc)
     *
     * @see javafx.fxml.Initializable#initialize(java.net.URL,
     * java.util.ResourceBundle)
     */
    @FXML
    private void initialize() {
        indShip=0;
        initGameArea();
        bindOnePlayerGame();
        handleMenuItem();
    }
    /**
     * bind event handler for the one player menu item
     */
    @FXML
    private void bindOnePlayerGame(){
        onePlayer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("one player");
                Board b1, b2;
                b1=new Board("p1",len);// people
                b2=new Board("p2",len);// ai
                // TODO init this.player1 & this.player2
                ships1=createDefaultShips();
                List<AbstractShip> ships2=createDefaultShips();
                player1=new Player(b1,b2,ships1);
                player2=new AIPlayer(b2,b1,ships2);
                player2.putShips();
                System.out.println("one player");
                handleCanvas1();
            }
        });
    }

    /**
     * initialize the game area
     */
    private void initGameArea(){
        gc1 = canvas1.getGraphicsContext2D();
        gapX = (canvas1.getWidth() - broadPadding * 2) / len;
        gapY = (canvas1.getWidth() - broadPadding * 2) / len;
        cleanChessBoard(gc1,canvas1);
        gc2=canvas2.getGraphicsContext2D();
        cleanChessBoard(gc2,canvas2);
    }
    private void cleanChessBoard(GraphicsContext gc,Canvas canvas) {
        gc.setFill(colorChessboard);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setStroke(colorLine);
        for (int i = 0; i <= len; i++) {
            gc.strokeLine(i * gapX + broadPadding, broadPadding, i * gapX + broadPadding, canvas.getHeight() - broadPadding);
            gc.strokeLine(broadPadding, i * gapY + broadPadding, canvas.getWidth() - broadPadding, i * gapY + broadPadding);
        }

        gc.setFill(colorMark);
        gc.setFont(Font.font(broadPadding / 2));
        for (int i = 0; i < len; i++) {
            gc.fillText(markX[i], i * gapX + broadPadding+10, broadPadding - 5);
            gc.fillText(markX[i], i * gapX + broadPadding+10, canvas.getHeight() - 5);
            gc.fillText(markY[i], 5, gapY * i + broadPadding+10);
//            gc.fillText(markY[i], canvas.getWidth() - broadPadding + 5, gapY * i + broadPadding + 5);
        }
    }

    @FXML
    private void handleCanvas1(){
        Orientation[] oris = {Orientation.NORTH, Orientation.SOUTH, Orientation.EAST, Orientation.WEST}; // North, South, East, West
        canvas1.setOnMouseClicked(new EventHandler<MouseEvent>(){
            int o=0;// click number
            @Override
            public void handle(MouseEvent event) {
                int x=(int)((event.getX() - broadPadding)/gapX)+1;
                int y=(int)((event.getY()-broadPadding)/gapY)+1;
                if(event.getButton()== MouseButton.PRIMARY){
//                    System.out.println("click");
                    if(indShip<ships1.size()){
                        drawShip(ships1.get(indShip),x,y);
                        if(player1.putship(ships1.get(indShip),x-1,y-1))
                            indShip++;
                    }
                    else{
                        handleCanvas2();
                    }
                }
                else{
                    if(indShip<ships1.size()){
                        cleanShip(ships1.get(indShip),x,y);
                        do{
                            ships1.get(indShip).setNavireOri(oris[o%4]);
                            o++;
                        }while(!player1.getBoard().hasShip(x-1,y-1)&&!drawShip(ships1.get(indShip),x,y));

                    }
                    System.out.println(event.getX()+","+event.getY());
                }
            }
        });
    }
    @FXML
    private void handleCanvas2(){
        canvas2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int x=(int)((event.getX() - broadPadding)/gapX)+1;
                int y=(int)((event.getY()-broadPadding)/gapY)+1;
                int[] coords = new int[2];
                Hit hit=player1.attack(x-1,y-1);
                drawAttack(gc2,hit,x,y);
                boolean done=updateScore();
                if(!done){
                    hit=player2.sendHit(coords);
                    drawAttack(gc1,hit,coords[0]+1,coords[1]+1);
                    done = updateScore();
                }
                if(done)
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText(String.format("joueur %d gagne", player1.getLoss() ? 2 : 1));
                    alert.showAndWait();

                }

            }
        });
    }

    private boolean drawShip(AbstractShip ship, int x, int y){
        boolean isDrawn=true;
        Orientation ori=ship.getNavireOri();
        int l=ship.getNavireLength();
        gc1.setFill(ship.getNavireColor());
        int margin=-3;
        double width=gapX-5;
        double height=gapY-5;
        try{
            switch (ori){
                case EAST:
                    for(int i=l-1;i>=0;i--){
                        if(player1.getBoard().hasShip(x-1+i,y-1))
                            throw new Exception("invalide position");
                    }
                    for(int i=l-1;i>=0;i--){
                        gc1.fillRect((x+i)*gapX+margin,y*gapY+margin,width,height);
                    }
                    break;
                case WEST:
                    for(int i=l-1;i>=0;i--){
                        if(player1.getBoard().hasShip(x-1-i,y-1))
                            throw new Exception("invalide position");
                    }
                    for(int i=l-1;i>=0;i--){
                        gc1.fillRect((x-i)*gapX+margin,y*gapY+margin,width,height);
                    }
                    break;
                case SOUTH:
                    for(int i=l-1;i>=0;i--){
                        if(player1.getBoard().hasShip(x-1,y-1+i))
                            throw new Exception("invalide position");
                    }
                    for(int i=l-1;i>=0;i--){
                        gc1.fillRect(x*gapX+margin,(y+i)*gapY+margin,width,height);
                    }
                    break;
                case NORTH:
                    for(int i=l-1;i>=0;i--){
                        if(player1.getBoard().hasShip(x-1,y-1-i))
                            throw new Exception("invalide position");
                    }
                    for(int i=l-1;i>=0;i--){
                        gc1.fillRect(x*gapX+margin,(y-i)*gapY+margin,width,height);
                    }
                    break;
            }
        }
        catch (Exception e){
            System.out.println(e);
            isDrawn=false;
        }
        return isDrawn;
    }
    private boolean cleanShip(AbstractShip ship, int x, int y){
        boolean isDrawn=true;
        Orientation ori=ship.getNavireOri();
        int l=ship.getNavireLength();
        int margin=-3;
        double width=gapX-5;
        double height=gapY-5;

        gc1.setFill(colorChessboard);
        try{
            switch (ori){
                case EAST:
                    for(int i=l-1;i>=0;i--){
                        if(player1.getBoard().hasShip(x-1+i,y-1))
                            throw new Exception("invalide position");
                    }
                    for(int i=l-1;i>=0;i--){
                        gc1.fillRect((x+i)*gapX+margin,y*gapY+margin,width,height);
                    }
                    break;
                case WEST:
                    for(int i=l-1;i>=0;i--){
                        if(player1.getBoard().hasShip(x-1-i,y-1))
                            throw new Exception("invalide position");
                    }
                    for(int i=l-1;i>=0;i--){
                        gc1.fillRect((x-i)*gapX+margin,y*gapY+margin,width,height);
                    }
                    break;
                case SOUTH:
                    for(int i=l-1;i>=0;i--){
                        if(player1.getBoard().hasShip(x-1,y-1+i))
                            throw new Exception("invalide position");
                    }
                    for(int i=l-1;i>=0;i--){
                        gc1.fillRect(x*gapX+margin,(y+i)*gapY+margin,width,height);
                    }
                    break;
                case NORTH:
                    for(int i=l-1;i>=0;i--){
                        if(player1.getBoard().hasShip(x-1,y-1-i))
                            throw new Exception("invalide position");
                    }
                    for(int i=l-1;i>=0;i--){
                        gc1.fillRect(x*gapX+margin,(y-i)*gapY+margin,width,height);
                    }
                    break;
            }
        }
        catch (Exception e){
            System.out.println(e);
            isDrawn=false;
        }
        return isDrawn;
    }

    private void drawAttack(GraphicsContext gc,Hit hit, int x, int y){
        if(hit==Hit.MISS){
            gc.setFill(Color.BLACK);
        }
        else{
            gc.setFill(Color.RED);
        }
        int margin=-3;
        double width=gapX-5;
        double height=gapY-5;
        gc.fillOval(x*gapX+margin,y*gapY+margin,width,height);
    }
    private static List<AbstractShip> createDefaultShips() {
        return Arrays.asList(new AbstractShip[] { new Destroyer(), new Submarine(), new BattleShip(), new AircraftCarrier() });
//        return Arrays.asList(new AbstractShip[] { new Destroyer() });
    }

    private boolean updateScore() {
        for (Player player : new Player[] { player1, player2 }) {
            int destroyed = 0;
            for (AbstractShip ship : player.getShips()) {
                if (ship.isSunk()) {
                    destroyed++;
                }
            }

            player.setDestroyedCount(destroyed);
            player.setLose(destroyed == player.getShips().length);
            if (player.getLoss()) {
                return true;
            }
        }
        return false;
    }
    @FXML
    private void handleMenuItem(){
        mAbout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("This is a game about battle sea.\n Have fun with it!");
                alert.showAndWait();
            }
        });
    }
}
