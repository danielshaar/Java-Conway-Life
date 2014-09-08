/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package life;

/**
 *
 * @author dsizzle
 */
public class Colony {

    /**Value of a living cell*/
    public static final int CELL_ALIVE = 1;
    /**Value of a dead cell*/
    public static final int CELL_DEAD = 0;
    /**current generation number*/
    private int numGen = 0;
    /**turns wrap mode on or off*/
    private boolean wrap;
    /**Number of rows in colony*/
    private int numRows;
    /**Number of columns in colony*/
    private int numCols;
    /**Current Array*/
    private Array2D Array2D;
    /**Array that stores next generation cells*/
    private Array2D newArray2D;

    /**The Colony constructor
     * 
     * @param rows - the number of rows
     * @param cols - number of columns
     */
    public Colony(int rows, int cols) {
        Array2D = new Array2D(rows + 2, cols + 2);
        newArray2D = new Array2D(rows + 2, cols + 2);
        numRows = rows;
        numCols = cols;
    }

    /**Evolves the colony by one generation*/
    public void evolve() {
        int surround;
        int currentValue;
        for (int i = 1; i < numRows + 1; i++) {
            for (int j = 1; j < numCols + 1; j++) {
                surround = getNeighbors(i, j);
                currentValue = Array2D.getValue(i, j);
                newArray2D.setValue(i, j, rules(surround, currentValue));
            }
        }
        Array2D tempCells;
        tempCells = Array2D;
        Array2D = newArray2D;
        newArray2D = tempCells;
        numGen++;
    }

    /**Counts the number of living cells around a given cell
     * 
     * @param row - the row number
     * @param col - the column number
     * @return - the number of living cells around a given cell
     */
    private int getNeighbors(int row, int col) {
        if (getWrap()) {
            return wrapIndex(row + 1, col + 1)
                    + wrapIndex(row + 1, col)
                    + wrapIndex(row + 1, col - 1)
                    + wrapIndex(row, col + 1)
                    + wrapIndex(row, col - 1)
                    + wrapIndex(row - 1, col + 1)
                    + wrapIndex(row - 1, col)
                    + wrapIndex(row - 1, col - 1);
        }

        return Array2D.getValue(row + 1, col + 1)
                + Array2D.getValue(row + 1, col)
                + Array2D.getValue(row + 1, col - 1)
                + Array2D.getValue(row, col + 1)
                + Array2D.getValue(row, col - 1)
                + Array2D.getValue(row - 1, col + 1)
                + Array2D.getValue(row - 1, col)
                + Array2D.getValue(row - 1, col - 1);
    }

    /**Contains the rules for evolving
     * 
     * @param surround - the number of living surrounding cells
     * @param currentValue - the current value of the cell
     * @return - the status of the cell in the next generation
     */
    private int rules(int surround, int currentValue) {
        if (surround == 3) {
            return CELL_ALIVE;
        }
        if (surround == 2 && currentValue == CELL_ALIVE) {
            return CELL_ALIVE;
        }
        return CELL_DEAD;
    }

    /**Returns the number of a colony's rows
     * 
     * @return - the number of a colony's rows 
     */
    public int getColonySizeNumRows() {
        return numRows;
    }

    /**Returns the number of a colony's columns
     * 
     * @return - the number of a colony's columns 
     */
    public int getColonySizeNumCols() {
        return numCols;
    }

    /**Returns the generation number
     * 
     * @return - the current generation number 
     */
    public int getGenerationNumber() {
        return numGen;
    }

    /**Returns the number of living cells
     * 
     * @return - the number of living cells 
     */
    public int getNumberLivingCells() {
        int countAlive = 0;
        for (int i = 1; i < numRows + 1; i++) {
            for (int j = 1; j < numCols + 1; j++) {
                if (Array2D.getValue(i, j) == 1) {
                    countAlive++;
                }
            }
        }
        return countAlive;
    }

    /**Is a given cell alive?
     * 
     * @param row - the row number of the wanted cell
     * @param col - the column number of the wanted cell
     * @return - whether or not the wanted cell is alive
     */
    public boolean isCellAlive(int row, int col) {
        if (Array2D.getValue(row + 1, col + 1) == CELL_ALIVE) {
            return true;
        }
        return false;
    }

    /**Kills all cells*/
    public void resetColony() {
        for (int i = 1; i < numRows + 1; i++) {
            for (int j = 1; j < numCols + 1; j++) {
                Array2D.setValue(i, j, CELL_DEAD);
            }
        }
        numGen = 0;
    }

    /**Sets a cell to be alive given its row and col
     * 
     * @param row - the row number of the wanted cell
     * @param col - the column number of the wanted cell
     */
    public void setCellAlive(int row, int col) {
        Array2D.setValue(row + 1, col + 1, CELL_ALIVE);
    }

    /**Sets a cell to be dead given its row and col
     * 
     * @param row - the row number of the wanted cell
     * @param col - the column number of the wanted cell
     */
    public void setCellDead(int row, int col) {
        Array2D.setValue(row + 1, col + 1, CELL_DEAD);
    }

    /**Sets the wrapping mode on or off
     * 
     * @param setWrap - determines whether wrapping is on or off
     */
    public void setWrap(boolean setWrap) {
        wrap = setWrap;
    }

    /**Returns the current wrapping mode
     * 
     * @return - whether wrapping is on or off
     */
    public boolean getWrap() {
        return wrap;
    }

    /**Wraps the colony and returns the value of the current cell
     * 
     * @param row - row number of the current cell
     * @param col - column number of the current cell
     * @return - the value at the current cell
     */
    private int wrapIndex(int row, int col) {
        if (row < 1) {
            row = numRows + row;
        }
        if (row >= numRows + 1) {
            row = row - numRows;
        }
        if (col < 1) {
            col = numCols + col;
        }
        if (col >= numCols + 1) {
            col = col - numCols;
        }
        return Array2D.getValue(row, col);
    }

    /**toString method that prints out colony
     * 
     * @return - String representation of the colony 
     */
    @Override
    public String toString() {
        String output = "";
        for (int i = 1; i < numRows + 1; i++) {
            for (int j = 1; j < numCols + 1; j++) {
                if (Array2D.getValue(i, j) == CELL_ALIVE) {
                    output += "*";
                } else if (Array2D.getValue(i, j) == CELL_DEAD) {
                    output += " ";
                }
            }
            output += "\n";
        }
        return output;
    }
}
