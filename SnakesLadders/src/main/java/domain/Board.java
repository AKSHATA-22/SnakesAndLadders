package domain;
import bootstrap.*;
import java.io.*;
public class Board implements Serializable{
    private Ladder[] ladders;
    private Snake[] snakes;
    private Player[] players;
    private final int NUMBER_OF_SNAKES = 6;
    private final int NUMBER_OF_LADDERS = 6;
    private final int START_OF_BOARD = 1;
    private final int END_OF_BOARD = 100;


    @Override
    public String toString(){
        return "There are "+NUMBER_OF_SNAKES+" Snakes "+NUMBER_OF_LADDERS+
                " Ladders in the game. Game starts from "+START_OF_BOARD+
                " and ends at "+END_OF_BOARD;
    }

    public Player[] getPlayers() {
        return players;
    }

    public Player getPlayer(int numberOfThePlayer){
        return players[numberOfThePlayer];
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public int getNUMBER_OF_SNAKES() {
        return NUMBER_OF_SNAKES;
    }

    public int getNUMBER_OF_LADDERS() {
        return NUMBER_OF_LADDERS;
    }

    public int getSTART_OF_BOARD() {
        return START_OF_BOARD;
    }

    public int getEND_OF_BOARD() {
        return END_OF_BOARD;
    }

    public Ladder[] getLadders() {
        return ladders;
    }

    public void setLadders(Ladder[] ladders) {
        this.ladders = ladders;
    }

    public Snake[] getSnakes() {
        return snakes;
    }

    public void setSnakes(Snake[] snakes) {
        this.snakes = snakes;
    }
    public void setSnakes() {
        Snake[] snakesOnBoard = new Snake[NUMBER_OF_SNAKES];
        int[] heads = {39, 47, 72, 78, 93, 99};
        int[] tails = {3, 11, 66, 41, 56, 7};
        for (int i = 0; i<NUMBER_OF_SNAKES;i++){
            Snake snake = new Snake();
            snake.setHead(heads[i]);
            snake.setTail(tails[i]);
            snakesOnBoard[i]=snake;
        }
        setSnakes(snakesOnBoard);
    }
    public void setLadders() {
        Ladder[] laddersOnBoard = new Ladder[NUMBER_OF_LADDERS];
        int[] start = {7, 17, 21, 34, 52, 76};
        int[] end = {29, 38, 82, 55, 90, 95};
        for (int i = 0; i<NUMBER_OF_LADDERS;i++){
            Ladder ladder = new Ladder();
            ladder.setEnd(end[i]);
            ladder.setStart(start[i]);
            laddersOnBoard[i]=ladder;
        }
        setLadders(laddersOnBoard);
    }
    public void setPlayers(int noOfPlayers) {
        Player[] playersOfTheGame = new Player[noOfPlayers];
        for (int i = 0; i<noOfPlayers;i++){
            Player player = new Player();
            player.setPlayerNo(i+1);
            player.setTile(START_OF_BOARD);
            player.setLadderPointer(0);
            player.setSnakePointer(0);
            player.setNext(-1);
            playersOfTheGame[i] = player;
        }
        for (int j = 0; j<noOfPlayers;j++){
            if (j == noOfPlayers-1){
//                System.out.println("if");
                playersOfTheGame[j].setNext(0);
            }
            else {
//                System.out.println("else");
                playersOfTheGame[j].setNext(j + 1);
            }
        }
        setPlayers(playersOfTheGame);
    }
    public static void saveBoard(Board gameBoard) throws Exception {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File("SnakeAndLadders.dat")));
        objectOutputStream.writeObject(gameBoard);
        objectOutputStream.close();
    }
    public static Board loadBoard(int choiceOfGame) {
        if (choiceOfGame==1){
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("SnakeAndLadders.dat"));
                Board savedBoard = (Board) objectInputStream.readObject();
                Driver.logger.info("Old Game Restart");
                return savedBoard;
            } catch (Exception e) {
                Driver.logger.info("New game");
                return Driver.setNewGame();
            }
        }
        else {
            Driver.logger.info("New game");
            return Driver.setNewGame();
        }
    }

}
