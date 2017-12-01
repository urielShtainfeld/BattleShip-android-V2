package entities;


public class GameController {
        private static final int EASY_NO_OF_SHIPS = 5;
        private static final int MEDIUM_NO_OF_SHIPS = 4;
        private static final int HARD_NO_OF_SHIPS = 3;
        private static final int BOARD_SIZE = 20;
        private int shipsLeft;
        private Ship[] ships;


        public void createBoard(){

            setShipsLeft(EASY_NO_OF_SHIPS);

        }
        public Ship[] getShips() {
            return ships;
        }

        public void setShips(){

        }

        public void checkCell(int row,int col){}

        public void setShipsLeft(int shipsLeft) {
            this.shipsLeft = shipsLeft;

        }
        public void setLevel(String level) {


        }

}
