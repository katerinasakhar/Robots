package gui.window;

import controler.GameController;
import locale.RobotsLocale;
import view.GameVisualizer;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class GameWindow extends JInternalFrame
{
    //public final GameController m_controler;
    public final GameVisualizer m_visualizer;
    public GameWindow() 
    {
        super(RobotsLocale.getINSTANCE().getString("frame.game"), true, true, true, true);
        //m_controler = new GameController();
        m_visualizer=new GameVisualizer();
        setSize(400, 300);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }
}
