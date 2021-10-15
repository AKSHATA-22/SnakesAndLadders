package domain;

import bootstrap.Driver;

import java.applet.Applet;
import java.io.Serializable;
import java.util.Random;

public class Move implements Serializable {
    private Player playing;

    public Player getPlaying() {
        return playing;
    }

    public void setPlaying(Player playing) {
        this.playing = playing;
    }

    public boolean makeAMove(Board gameBoard, Player playing){
        int roll = rollTheDice(2);
        setPlaying(playing);
        if (playing.getTile()+roll<gameBoard.getEND_OF_BOARD()){
            playing.setTile(playing.getTile()+roll);
            if (checkSnake(playing, gameBoard)){
                System.out.println("Snake!! You fell from "+playing.getTile()+" to "+gameBoard.getSnakes()[playing.getSnakePointer()].getTail());
                Driver.logger.info("Snake encountered");
                snakeBite(playing, gameBoard);
            }
            if (checkLadder(playing, gameBoard)){
                System.out.println("Ladder!! You climbed from "+playing.getTile()+" to "+gameBoard.getLadders()[playing.getLadderPointer()].getEnd());
                Driver.logger.info("Ladder encountered");
                ladderClimb(playing, gameBoard);
            }
        }
        else if (playing.getTile()+roll>=gameBoard.getEND_OF_BOARD()){
            return true;
        }
        return false;
    }
    public static void snakeBite(Player playing, Board gameBoard){
        playing.setTile(gameBoard.getSnakes()[playing.getSnakePointer()].getTail());
        calculatePointerSnakebite(playing,gameBoard);
    }
    public static void ladderClimb(Player playing, Board gameBoard){
        playing.setTile(gameBoard.getLadders()[playing.getLadderPointer()].getEnd());
        calculatePointerLadderClimb(playing,gameBoard);
    }
    public static void calculatePointerSnakebite(Player playing, Board gameBoard){
        while (playing.getTile()<gameBoard.getSnakes()[playing.getSnakePointer()].getHead()){
            if(playing.getSnakePointer()==0)
                return;
            playing.setSnakePointer(playing.getSnakePointer()-1);
        }
        if (playing.getLadderPointer()==-1){
            playing.setLadderPointer(gameBoard.getNUMBER_OF_LADDERS()-1);
        }
        while (playing.getTile()<gameBoard.getLadders()[playing.getLadderPointer()].getStart()) {
            if (playing.getLadderPointer()==0)
                return;
            playing.setLadderPointer(playing.getLadderPointer()-1);
        }
    }
    public static void calculatePointerLadderClimb(Player playing, Board gameBoard){
        while (playing.getTile()>gameBoard.getLadders()[playing.getLadderPointer()].getStart()){
            playing.setLadderPointer(playing.getLadderPointer()+1);
            if (playing.getTile()>=gameBoard.getLadders()[gameBoard.getNUMBER_OF_LADDERS()-1].getStart()) {
                playing.setLadderPointer(-1);
                break;
            }
        }
        while (playing.getTile()>gameBoard.getSnakes()[playing.getSnakePointer()].getHead()){
            playing.setSnakePointer(playing.getSnakePointer()+1);
        }
    }
    public static int rollTheDice(int noOfDice){
        Random random = new Random();
        int move = 0;
        for (int i=0;i<noOfDice;i++){
            int output = random.nextInt(5)+1;
            System.out.println("Number on the dice "+output);
            move+=output;
        }
        return move;
    }
    public static boolean checkSnake(Player playing, Board gameBoard){
        if (playing.getSnakePointer()==-1){
            return false;
        }
        if (gameBoard.getSnakes()[playing.getSnakePointer()].getHead()==playing.getTile()){
            return true;
        }
        else if (playing.getTile()>gameBoard.getSnakes()[playing.getSnakePointer()].getHead()){
            while (playing.getTile()>gameBoard.getSnakes()[playing.getSnakePointer()].getHead()){
                if (playing.getSnakePointer() == gameBoard.getNUMBER_OF_SNAKES()-1){
                    playing.setSnakePointer(-1);
                    break;
                }
                playing.setSnakePointer(playing.getSnakePointer()+1);
                if (playing.getSnakePointer()==-1){
                    return false;
                }
                if (gameBoard.getSnakes()[playing.getSnakePointer()].getHead()==playing.getTile()){
                    return true;
                }
            }

            return false;
        }
        return false;
    }
    public static boolean checkLadder(Player playing, Board gameBoard){
        if (playing.getLadderPointer()==-1){
            return false;
        }
        if (gameBoard.getLadders()[playing.getLadderPointer()].getStart()==playing.getTile()){
            return true;
        }
        else if (playing.getTile()>gameBoard.getLadders()[playing.getLadderPointer()].getStart()){
            while (playing.getTile()>gameBoard.getLadders()[playing.getLadderPointer()].getStart()){
                if (playing.getLadderPointer() == gameBoard.getNUMBER_OF_LADDERS()-1){
                    playing.setLadderPointer(-1);
                    break;
                }
                playing.setLadderPointer(playing.getLadderPointer()+1);
            }
            if (playing.getLadderPointer()==-1){
                return false;
            }
            if (gameBoard.getLadders()[playing.getLadderPointer()].getStart()==playing.getTile()){
                return true;
            }
            return false;
        }
        return false;
    }
}
