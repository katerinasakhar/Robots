package gui.window;

import RobotsGame.RobotsGame;
import locale.RobotsLocale;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import java.text.NumberFormat;

public class GameParamWindow extends JInternalFrame  {
    public GameParamWindow(){
        super(RobotsLocale.getINSTANCE().getString("frame.gameparam"),true, true, true, true);
        NumberFormat numberFormat = NumberFormat.getIntegerInstance();
        NumberFormatter formatter = new NumberFormatter(numberFormat);
        formatter.setValueClass(Integer.class);
        formatter.setAllowsInvalid(false);
        JFormattedTextField numOfRobots=new JFormattedTextField(formatter);
        numOfRobots.setColumns(3);
        JFormattedTextField numOfTargets=new JFormattedTextField(formatter);
        numOfTargets.setColumns(3);
        JPanel panel=new JPanel();
        panel.setPreferredSize(new Dimension(167, 131));
        panel.add(new JLabel(RobotsLocale.getINSTANCE().getString("frame.gameparam.label.settings")), SwingConstants.CENTER);
        panel.add(new JLabel(RobotsLocale.getINSTANCE().getString("frame.gameparam.label.number.robot")));
        panel.add(numOfRobots);
        panel.add(new JLabel(RobotsLocale.getINSTANCE().getString("frame.gameparam.label.number.target") ));
        panel.add(numOfTargets);
        JButton button = new JButton(RobotsLocale.getINSTANCE().getString("frame.gameparam.label.start"));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer robotsNum=Integer.parseInt(numOfRobots.getText());
                Integer targetsNum=Integer.parseInt(numOfTargets.getText());
                RobotsGame.INSTANCE.StartGame(robotsNum,targetsNum);
                RobotsGame.INSTANCE.notifyObservers();
                openGameWindow();



            }
        });

        panel.add(button);
        getContentPane().add(panel);
        setResizable(false);
        pack();
    }

    private void openGameWindow() {
        GameWindow gameWindow = new GameWindow();
        gameWindow.setSize(200,200);
        JDesktopPane desktopPane = getDesktopPane();
        if (desktopPane != null) {
            desktopPane.add(gameWindow);
            gameWindow.setVisible(true);
        }
    }
}
