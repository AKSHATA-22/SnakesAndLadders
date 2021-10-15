package domain;

import java.io.Serializable;

public class Player implements Serializable {
    private int tile;
    private int playerNo;
    private int snakePointer;
    private int ladderPointer;
    private int next;

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public int getSnakePointer() {
        return snakePointer;
    }

    public void setSnakePointer(int snakePointer) {
        this.snakePointer = snakePointer;
    }

    public int getLadderPointer() {
        return ladderPointer;
    }

    public void setLadderPointer(int ladderPointer) {
        this.ladderPointer = ladderPointer;
    }

    public int getTile() {
        return tile;
    }

    public void setTile(int tile) {
        this.tile = tile;
    }

    public int getPlayerNo() {
        return playerNo;
    }

    public void setPlayerNo(int playerNo) {
        this.playerNo = playerNo;
    }
}
