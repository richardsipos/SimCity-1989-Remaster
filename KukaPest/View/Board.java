package View;

public class Board {
    private int boardX;
    private int boardY;
    private int originalX = 50;
    private int originalY = 50;
    private int cellSide = 20;


    public Board(int boardX, int boardY){
        this.boardX = boardX;
        this.boardY = boardY;
    }

    public int getBoardX() {
        return boardX;
    }
    public int getBoardY() {
        return boardY;
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
