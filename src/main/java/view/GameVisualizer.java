package view;

import RobotsGame.BaseRobotsGameObject;
import RobotsGame.RobotsGame;
import gui.Game;
import locale.RobotsLocale;
import log.Logger;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import RobotsGame.Robot;
import RobotsGame.Target;


import javax.swing.JPanel;

public class GameVisualizer extends JPanel implements Observer
{
    private LinkedList<BaseRobotsGameObject> ent = new LinkedList<>();

    private void drawEntity(Graphics2D g, BaseRobotsGameObject object) {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(object.getColor());
        fillOval(g, (int)object.getX(), (int)object.getY(), object.getSize(), object.getSize());
        g.setColor(Color.BLACK);
        drawOval(g, (int)object.getX(), (int)object.getY(), object.getSize(), object.getSize());
    }


//
//    public GameVisualizer()
//    {
//        addMouseListener(new MouseAdapter()
//        {
//            @Override
//            public void mouseClicked(MouseEvent e)
//            {
//                setTargetPosition(e.getPoint());
//            }
//        });
//        setDoubleBuffered(true);
//    }
//
//    protected void setTargetPosition(Point p)
//    {
//        Game.INSTANCE.getTarget().setPosition(p.x,p.y);
//        Logger.debug(
//                MessageFormat.format(
//                        RobotsLocale.getINSTANCE().getString("frame.log.msg.target.pos"),
//                        p.x,
//                        p.y
//                )
//        );
//    }
//
    public void onRedrawEvent()
    {
        EventQueue.invokeLater(this::repaint);
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        synchronized (ent) {
            if(ent.isEmpty()) return;
            for (BaseRobotsGameObject el : ent) {
                drawEntity(g2d, el);
            }
        }
    }


//    @Override
//    protected void paintComponent(Graphics g){
//        super.paintComponent(g);
//        for (BaseRobotsGameObject el:ent){
//            g.setColor(el.getColor());
//            g.fillOval((int)el.getX(), (int)el.getY(), 5, 5);
//        }
//
//    }

    private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2)
    {
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2)
    {
        g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof RobotsGame) {
            synchronized (ent) {
                ent = (LinkedList<BaseRobotsGameObject>) arg;
            }
        }
        onRedrawEvent();
    }
//
//    private void drawRobot(Graphics2D g, int x, int y, double direction)
//    {
//        int robotCenterX = round(Game.INSTANCE.getRobot().getPosition_X());
//        int robotCenterY = round(Game.INSTANCE.getRobot().getPosition_Y());
//        AffineTransform t = AffineTransform.getRotateInstance(direction, robotCenterX, robotCenterY);
//        g.setTransform(t);
//        g.setColor(Color.MAGENTA);
//        fillOval(g, robotCenterX, robotCenterY, 30, 10);
//        g.setColor(Color.BLACK);
//        drawOval(g, robotCenterX, robotCenterY, 30, 10);
//        g.setColor(Color.WHITE);
//        fillOval(g, robotCenterX  + 10, robotCenterY, 5, 5);
//        g.setColor(Color.BLACK);
//        drawOval(g, robotCenterX  + 10, robotCenterY, 5, 5);
//    }
//




}
