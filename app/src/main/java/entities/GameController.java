package entities;


public class GameController {
    private static final int NO_OF_SHIPS = 5;
    private static final int HARD_BOARD_SIZE = 20;
    private static final int MEDIUM_BOARD_SIZE = 20;
    private static final int EASY_BOARD_SIZE = 20;
    private int shipsLeft;
    private Ship[] ships;
    int boardSize;

    public GameController() {
        ships = new Ship[NO_OF_SHIPS];
    }

    public void createUserBoard() {
        setShipsLeft(NO_OF_SHIPS);
        

    }

    public void createCompBoard() {
        setShipsLeft(NO_OF_SHIPS);


    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public Ship[] getShips() {
        return ships;
    }

    public void setShips() {

    }

    public void checkCell(int row, int col) {
    }

    public void setShipsLeft(int shipsLeft) {
        this.shipsLeft = shipsLeft;

    }

    public void setLevel(String level) {
        switch (level) {
            case "EASY":
                setBoardSize(EASY_BOARD_SIZE);
                break;
            case "MEDIUM":
                setBoardSize(MEDIUM_BOARD_SIZE);
                break;
            case "HARD":
                setBoardSize(HARD_BOARD_SIZE);
                break;


        }
    }

}
