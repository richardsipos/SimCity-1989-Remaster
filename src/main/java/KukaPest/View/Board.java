package KukaPest.View;

public class Board {
    private int boardX;
    private int boardY;
    private int cellSide = 25;


    /**
     *Constructor for the Board class
     * @param boardX map width
     * @param boardY map height
     */
    public Board(int boardX, int boardY){
        this.boardX = boardX;
        this.boardY = boardY;
    }

    /**
     *Getter for the boardX
     */
    public int getBoardX() {
        return boardX;
    }

    /**
     *Getter for the boardX
     */
    public int getBoardY() {
        return boardY;
    }

    /**
     *Getter for the cellSide
     */
    public int getCellSide() {
        return cellSide;
    }

    /**
     *Setter for the cellSide
     */
    public void setCellSide(int cellSide) {
        this.cellSide = cellSide;
    }


}
