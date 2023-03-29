package View;

public class Board {
    private int boardSize;
    private int originalX = 50;
    private int originalY = 50;
    private int cellSide = 60;

    public Board(int boardSize){
        this.boardSize = boardSize;

    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public int getOriginalX() {
        return originalX;
    }

    public void setOriginalX(int originalX) {
        this.originalX = originalX;
    }

    public int getOriginalY() {
        return originalY;
    }

    public void setOriginalY(int originalY) {
        this.originalY = originalY;
    }

    public int getCellSide() {
        return cellSide;
    }

    public void setCellSide(int cellSide) {
        this.cellSide = cellSide;
    }


}
