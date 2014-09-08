/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package life;

import java.util.*;

/**
 *
 * @author dsizzle
 */
public class EvolveTask extends TimerTask {

    /**The frame that the task controls*/
    private ColonyFrame frame;
    /**The colony that the task controls*/
    private Colony colony;
    /**The delay between evolutions*/
    private int delay;
    /**The timer used by this task*/
    private Timer timer;
    /**The number of updates before the timer will end*/
    private int numUpdates;

    /**Constructor for task
     * 
     * @param cf - the colony's frame
     * @param nu - the number of updates needed
     * @param t - the timer for the task
     */
    public EvolveTask(ColonyFrame cf, Colony c, int nu, Timer t) {
        frame = cf;
        timer = t;
        numUpdates = nu;
        colony = c;
    }

    /**Runs the timer and tells it when to stop*/
    @Override
    public void run() {
        colony.evolve();
        frame.repaint();
        numUpdates--;
        if (numUpdates == 0) {
            timer.cancel();
        }
    }

    /**Starts the timer and completes the number of updates
     * 
     * @param frame - the colony's frame
     * @param nu - the number of updates needed
     * @param d - the delay between updates
     * @return - the task of timing the evolutions
     */
    public static EvolveTask start(ColonyFrame frame, Colony c, int nu, int d) {
        Timer timer = new Timer();
        EvolveTask task = new EvolveTask(frame, c, nu, timer);
        timer.schedule(task, d, d);
        return task;
    }
}
