package gui.window;


import locale.RobotsLocale;
import view.GameVisualizer;
import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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
        setSize(400, 300);
        RobotsGame game = new RobotsGame(robotsNum,targetsNum, 400, 300);
        m_visualizer = new GameVisualizer();
        game.addObserver(m_visualizer);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        GameWindow gameWindow = this;
        addComponentListener(
                new ComponentAdapter() {
                    @Override
                    public void componentResized(ComponentEvent e) {
                        super.componentResized(e);
                        game.setWidth((int)gameWindow.getSize().getHeight());
                        game.setLength((int)gameWindow.getSize().getWidth());
                    }
                }
        );
    }
}
