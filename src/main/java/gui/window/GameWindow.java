package gui.window;

import controler.GameController;
import locale.RobotsLocale;
import view.GameVisualizer;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class GameWindow extends JInternalFrame
{
    //public final GameVisualizer m_visualizer;
    public final GameController m_controler;
    public GameWindow() 
    {
        super(RobotsLocale.getINSTANCE().getString("frame.game"), true, true, true, true);
        //m_visualizer = new GameVisualizer();
        m_controler = new GameController();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_controler.gameVisualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }
}
