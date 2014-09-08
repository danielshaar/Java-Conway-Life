/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package life;

import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author dsizzle
 */
public class ColonyFrame extends Frame {

    /**Color of the living cells*/
    private Color aliveCellColor = Color.BLACK;
    /**Color of the cell border*/
    private Color cellBorder = Color.GREEN;
    /**Color of the background squares*/
    private Color deadCellColor = Color.WHITE;
    /**Turns grid on and off*/
    private boolean gridMode = false;
    /**The frame number*/
    private static int count = 0;
    /**The frame's colony to display*/
    private Colony clny;

    /**Constructor for frame
     * 
     * @param colony - the colony assigned to this window
     */
    public ColonyFrame(Colony colony) {
        super();
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                setVisible(false);
                dispose();
            }
        });

        clny = colony;
        int width = clny.getColonySizeNumCols() * 10 + 20;
        int height = clny.getColonySizeNumRows() * 10 + 40;
        setTitle("Colony: " + count);
        setSize(width, height);
        setVisible(true);
        count++;
    }

    /**Paints the colony
     * 
     * @param g - the graphics for the window
     */
    @Override
    public void paint(Graphics g) {
        for (int i = 0; i < clny.getColonySizeNumRows(); i++) {
            for (int j = 0; j < clny.getColonySizeNumCols(); j++) {
                if (clny.isCellAlive(i, j)) {
                    g.setColor(aliveCellColor);
                    g.fillRect(j * 10 + 10, i * 10 + 30, 10, 10);
                } else {
                    g.setColor(deadCellColor);
                    g.fillRect(j * 10 + 10, i * 10 + 30, 10, 10);
                }
                if (gridMode) {
                    g.setColor(cellBorder);
                    g.drawRect(j * 10 + 10, i * 10 + 30, 10, 10);
                }
            }
        }
    }

    /**Sets the color for the living cells to be
     * 
     * @param color - color to set 
     */
    public void setAliveCellColor(Color color) {
        aliveCellColor = color;
    }

    /**Sets the color for the dead cells to be
     * 
     * @param color - color to set
     */
    public void setDeadCellColor(Color color) {
        deadCellColor = color;
    }

    /**Sets the color for the border of the cells to be
     * 
     * @param color - color to set
     */
    public void setBorderCellColor(Color color) {
        cellBorder = color;
    }

    /**Sets the grid mode on and off
     * 
     * @param grid - tells whether the grid should be on or off 
     */
    public void setGrid(boolean grid) {
        gridMode = grid;
    }
}
