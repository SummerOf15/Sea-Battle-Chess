package prog;

import Ships.*;

import java.io.*;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Game {

    /*
     * *** Constante
     */
    public static final File SAVE_FILE = new File("savegame.dat");

    /*
     * *** Attributs
     */
    private Player player1;
    private Player player2;
    private Scanner sin;
    private int len=10;
    /*
     * *** Constructeurs
     */
    public Game() {
        sin=new Scanner(System.in);
    }

    public Game init() {
        if (!loadSave()) {
            // init attributes
            System.out.println("entre ton nom:");

            // TODO use a scanner to read player name
            String name="p1"; //default name
            if(sin.hasNextLine())
                name=sin.nextLine();
            System.out.println("entre la taille:");
            if(sin.hasNextLine())
                len=Integer.valueOf(sin.nextLine());
            if (len<2){
                System.out.println("invalid size, use default 10");
                len=10;
            }
            Board b1, b2;
            b1=new Board(name,len);// people
            b2=new Board("p2",len);// ai
            // TODO init this.player1 & this.player2
            List<AbstractShip> ships1=createDefaultShips();
            List<AbstractShip> ships2=createDefaultShips();
            player1=new Player(b1,b2,ships1);
            player2=new AIPlayer(b2,b1,ships2);
            b1.print();
            // place player ships
            player1.putShips();
            player2.putShips();
        }
        return this;
    }

    /*
     * *** Méthodes
     */
    public void run() {
        int[] coords = new int[2];
        Board b1 = player1.board;
        Hit hit;

        // main loop
        b1.print();
        boolean done;
        do {
            // TODO player1 send a hit
            hit=player1.sendHit(coords);
            boolean strike = hit != Hit.MISS; // TODO set this hit on his board (b1)
            b1.setHit(strike,coords[0],coords[1]);

            done = updateScore();
            b1.print();
            System.out.println(makeHitMessage(false /* outgoing hit */, coords, hit));

            save();

            if (!done) {

                hit=player2.sendHit(coords);
                strike = hit != Hit.MISS;
//                    player2.board.setHit(strike,coords[0],coords[1]);

                if (strike) {
                    b1.print();
                }

                System.out.println(makeHitMessage(true /* incoming hit */, coords, hit));
                done = updateScore();

                if (!done) {
                    save();
                }
            }

        } while (!done);

        System.out.println("delete "+SAVE_FILE.delete());
        System.out.println(String.format("joueur %d gagne", player1.lose ? 2 : 1));
        sin.close();
    }

    private void save() {
        try {
            // TODO bonus 2 : uncomment
//             if (!SAVE_FILE.exists()) {
//             SAVE_FILE.getAbsoluteFile().getParentFile().mkdirs();
//             }
            FileOutputStream fileOutputStream
                    = new FileOutputStream(SAVE_FILE);
            ObjectOutputStream objectOutputStream
                    = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(player1);
            objectOutputStream.writeObject(player2);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();
            // TODO bonus 2 : serialize players

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean loadSave() {
        if (SAVE_FILE.exists()) {
            try {
                // TODO bonus 2 : deserialize players
                ObjectInputStream objectInputStream=new ObjectInputStream(new FileInputStream(SAVE_FILE));
                player1=(Player)objectInputStream.readObject();
                player2=(Player)objectInputStream.readObject();
                objectInputStream.close();
                return true;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private boolean updateScore() {
        for (Player player : new Player[] { player1, player2 }) {
            int destroyed = 0;
            for (AbstractShip ship : player.getShips()) {
                if (ship.isSunk()) {
                    destroyed++;
                }
            }

            player.destroyedCount = destroyed;
            player.lose = destroyed == player.getShips().length;
            if (player.lose) {
                return true;
            }
        }
        return false;
    }

    private String makeHitMessage(boolean incoming, int[] coords, Hit hit) {
        String msg;
        String color=ColorFonts.ANSI_RESET;
        switch (hit) {
        case MISS:
            msg = hit.toString();
            break;
        case STRIKE:
            msg = hit.toString();
            color = ColorFonts.ANSI_RED;
            break;
        default:
            msg = hit.toString() + " coulé";
            color = ColorFonts.ANSI_RED;
        }
        msg = String.format("%s Frappe en %c%d : %s", incoming ? "<=" : "=>", ((char) ('A' + coords[0])),
                (coords[1] + 1), msg);
        return color+msg+ColorFonts.ANSI_RESET;
    }

    private static List<AbstractShip> createDefaultShips() {
        return Arrays.asList(new AbstractShip[] { new Destroyer(), new Submarine(), new BattleShip(), new AircraftCarrier() });
//        return Arrays.asList(new AbstractShip[] { new Destroyer() });
    }
}
