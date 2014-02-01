/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hmperson1.apps.superdoughnuts.gui;

import java.lang.reflect.InvocationTargetException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import scala.Function0;
import scala.Function1;
import scala.Unit;
import scala.collection.immutable.Map;

/**
 * Manages our GUI'n'panels'n'frames'n'stuff.
 * <br/> <br/>
 * All methods in this class (except {@link doOnEdt()}) must be executed on the
 * Event Dispatch Thread.
 *
 * @author HMPerson1
 */
public class GuiManager {

    /**
     * The primary frame on which panels are displayed.
     */
    private static JFrame mainFrame;

    static {
        initFrame();
    }

    /**
     * Applies the given function on the Event Dispatch Thread.
     *
     * @param func the function to be executed
     */
    public static void doOnEdt(final Function0<Unit> func) {
        if (SwingUtilities.isEventDispatchThread()) {
            func.apply();
        }
        SwingUtilities.invokeLater(new Func2Runnable(func));
    }

    /**
     * Sets the title of our frame.
     *
     * @param name the new title of our frame
     */
    public static void renameFrame(final String name) {
        mainFrame.setTitle(name);
    }

    /**
     * Creates a {@link StartPanel}.
     *
     * @param startCallback called when the start button is pressed
     * @param exitCallback called when the exit button is pressed
     * @return a new StartPanel with the given functions
     */
    public static JPanel createStartPanel(final Function1<Map<String, String>, Unit> startCallback, final Function0<Unit> exitCallback) {
        return new StartPanel(startCallback, exitCallback);
    }

    /**
     * Creates a {@link GamePanel}.
     *
     * @return a new GamePanel
     */
    public static JPanel createGamePanel() {
        return new GamePanel();
    }

    /**
     * Displays the given panel on our frame. If the frame is not currently
     * visible, it will be made visible.
     *
     * @param panel the panel to display
     */
    public static void showPanel(JPanel panel) {
        mainFrame.setContentPane(panel);
        mainFrame.pack();
        mainFrame.setMinimumSize(panel.getPreferredSize());
        mainFrame.setVisible(true);
    }

    /**
     * Runs {@link FrameIniter} on the EDT.
     */
    private static void initFrame() {
        final FrameIniter frameIniter = new FrameIniter();
        if (!SwingUtilities.isEventDispatchThread()) {
            try {
                SwingUtilities.invokeAndWait(frameIniter);
            } catch (InterruptedException | InvocationTargetException ex) {
                // We should never be interruped
                // Runnable should never throw an exception
            }
        } else {
            frameIniter.run();
        }
        mainFrame = frameIniter.getFrame();
    }

    /**
     * Utility class -- private constructor
     */
    private GuiManager() {
    }

    //<editor-fold defaultstate="collapsed" desc="Utility Inner Classes">
    /**
     * Sets the {@link LookAndFeel} and creates a new {@link JFrame}. Should be
     * executed on the EDT.
     */
    private static class FrameIniter implements Runnable {

        /**
         * The frame that will be created upon execution.
         */
        private JFrame frame;

        @Override
        public void run() {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                // If it failed, oh well
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            }
            frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        /**
         * Retrieves the frame created in {@link run()}.
         *
         * @return the frame created
         */
        public JFrame getFrame() {
            return frame;
        }
    }

    /**
     * Wraps a {@link Function0} as a {@link Runnable}.
     */
    private static class Func2Runnable implements Runnable {

        /**
         * The function to be called.
         */
        private final Function0<Unit> func;

        /**
         * Creates a {@link Runnable} with the specified function.
         *
         * @param func the function to be wrapped
         */
        public Func2Runnable(Function0<Unit> func) {
            this.func = func;
        }

        @Override
        public void run() {
            func.apply();
        }
    }//</editor-fold>
}
