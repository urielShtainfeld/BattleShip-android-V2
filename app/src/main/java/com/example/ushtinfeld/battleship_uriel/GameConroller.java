package com.example.ushtinfeld.battleship_uriel;

/**
 * Created by ushtinfeld on 01/12/2017.
 */

public class GameConroller {
        private static final int NO_OF_SHIPS = 5;
        private static final int BOARD_SIZE = 20;
        private int shipsLeft;
        private Ship[] ships;
        // private enum level{EASY,MEDIUM,HARD};


        public void createBoard(){

            setShipsLeft(NO_OF_SHIPS);

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
