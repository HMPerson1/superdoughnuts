/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hmperson1.apps.superdoughnuts.gui;

import java.lang.reflect.InvocationTargetException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import scala.Function0;
import scala.Function1;
import scala.Unit;
import scala.collection.immutable.Map;

/**
 * Manages our GUI'n'panels'n'frames'n'stuff. <br/>
 * All methods in this class (except {@link doOnEdt()}) must be executed on the
 * Event Dispatch Thread.
 *
 * @author HMPerson1
 */
public class GuiManager {

    private static JFrame mainFrame;

    static {
        initFrame();
    }

    public static void doOnEdt(final Function0<Unit> func) {
        if (SwingUtilities.isEventDispatchThread()) {
            func.apply();
        }
        SwingUtilities.invokeLater(new Func2Runnable(func));
    }

    public static void renameFrame(final String name) {
        mainFrame.setTitle(name);
    }

    public static JPanel createStartPanel(final Function1<Map<String, String>, Unit> startCallback, final Function0<Unit> exitCallback) {
        return new StartPanel(startCallback, exitCallback);
    }

    public static JPanel createGamePanel() {
        return new GamePanel();
    }

    public static void showPanel(JPanel panel) {
        mainFrame.setContentPane(panel);
        mainFrame.pack();
        mainFrame.setMinimumSize(panel.getPreferredSize());
        mainFrame.setVisible(true);
    }

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

    private GuiManager() {
    }

    //<editor-fold defaultstate="collapsed" desc="Utility Inner Classes">
    private static class FrameIniter implements Runnable {

        public FrameIniter() {
        }
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

        public JFrame getFrame() {
            return frame;
        }
    }

    private static class Func2Runnable implements Runnable {

        private final Function0<Unit> func;

        public Func2Runnable(Function0<Unit> func) {
            this.func = func;
        }

        @Override
        public void run() {
            func.apply();
        }
    }//</editor-fold>
}
