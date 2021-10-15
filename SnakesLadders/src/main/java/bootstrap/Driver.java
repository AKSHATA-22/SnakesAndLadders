package bootstrap;
import domain.*;

import java.io.*;
import java.util.Scanner;
import java.util.Random;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Priority;
import org.apache.log4j.*;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


public class Driver {
    static Scanner scanner = new Scanner(System.in);
    public static Logger logger = LoggerFactory.getLogger(Driver.class);

    public static void main(String[] args) {
        configureLogging("SnakeAndLaddersLog.log","INFO");
        System.out.println("Lets begin with the game!\n" +
                "1.\tLoad old game\n" +
                "2.\tPlay new game\n");
        int choiceOfGame = scanner.nextInt();
        Board gameBoard = Board.loadBoard(choiceOfGame);
        logger.info("Snake and Ladders Started");
        Player winner = gameInProcess(gameBoard);
        if (winner!=null){
            System.out.println("The winner for the game is "+winner.getPlayerNo());
            logger.info("The winner for the game is "+winner.getPlayerNo());
        }
        scanner.close();
    }
    public static Board setNewGame(){
        int noOfPlayers;
        System.out.println("Enter the number of players");
        noOfPlayers = scanner.nextInt();
        Board gameBoard = new Board();
        gameBoard.setSnakes();
        gameBoard.setLadders();
        gameBoard.setPlayers(noOfPlayers);
        return gameBoard;
    }
    public static Player gameInProcess(Board gameBoard){
        Player playing;
        playing = gameBoard.getPlayers()[0];
        int choice;
        while (true){
            System.out.println("Player : "+playing.getPlayerNo());
            System.out.println("Options\n" +
                    "1.\tShow Game State\n" +
                    "2.\tShow players position\n" +
                    "3.\tContinue Playing\n" +
                    "4.\tExit");
            choice = scanner.nextInt();
            if (choice==1){
                System.out.println(gameBoard);
            }
            if (choice==2){
                for (int i= 0; i<gameBoard.getPlayers().length;i++){
                    System.out.println("Player no. "+i+1+" : "+gameBoard.getPlayers()[i].getTile());
                }
            }
            else if (choice==4){
                if (playing.getPlayerNo()==gameBoard.getPlayers().length){
                    System.out.println("1.\tSave and Exit\n" +
                            "2.\tExit");
                    int exitChoice = scanner.nextInt();
                    if (exitChoice==1){
                        try {
                            Board.saveBoard(gameBoard);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                    else
                        return null;
                }
                else
                    System.out.println("Complete all the turns until the last player and try again");

            }
            else {
                Move currentMove = new Move();
                logger.info("Player "+playing.getPlayerNo()+" is playing");
                boolean game = currentMove.makeAMove(gameBoard, playing);
                if (game) {
                    return playing;
                }
                System.out.println("Player " + playing.getPlayerNo() + " reached at " + playing.getTile());
                logger.info("Player " + playing.getPlayerNo() + " reached at " + playing.getTile());
                int next = playing.getNext();
                playing = gameBoard.getPlayers()[next];
            }
        }
    }
    public static String configureLogging(String logFile, String logLevel){
        DailyRollingFileAppender dailyRollingFileAppender = new DailyRollingFileAppender();
        String logFileName="";
        switch (logLevel){
            case "DEBUG":{
                dailyRollingFileAppender.setThreshold(Level.toLevel(Priority.DEBUG_INT));
            }
            case "WARN":{
                dailyRollingFileAppender.setThreshold(Level.toLevel(Priority.WARN_INT));
            }
            case "ERROR":{
                dailyRollingFileAppender.setThreshold(Level.toLevel((Priority.ERROR_INT)));
            }
            default:{
                dailyRollingFileAppender.setThreshold(Level.toLevel(Priority.INFO_INT));
            }
            break;
        }

        System.out.println("Log files written out at "+logFile);
        dailyRollingFileAppender.setFile(logFile);
        dailyRollingFileAppender.setLayout(new EnhancedPatternLayout("%d [%t] %-5p %c - %m%n"));

        dailyRollingFileAppender.activateOptions();
        org.apache.log4j.Logger.getRootLogger().addAppender(dailyRollingFileAppender);
        return dailyRollingFileAppender.getFile();

    }

}
