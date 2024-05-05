package gui.window;


import locale.RobotsLocale;
import view.GameVisualizer;
import java.awt.BorderLayout;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import RobotsGame.RobotsGame;

public class GameWindow extends JInternalFrame
{
    //public final GameController m_controler;
    public final GameVisualizer m_visualizer;
    public GameWindow(int robotsNum, int targetsNum)
    {
        super(RobotsLocale.getINSTANCE().getString("frame.game"), true, true, true, true);
        //m_controler = new GameController();
        setSize(1000, 1000);
        RobotsGame game = new RobotsGame(robotsNum,targetsNum, 400, 300);
        m_visualizer = new GameVisualizer();
        game.addObserver(m_visualizer);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        panel.setSize(4000, 3000);
        getContentPane().add(panel);
        pack();
    }
}
