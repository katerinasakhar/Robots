package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import RobotsGame.RobotsGame;
import gui.window.CoordinatesWindow;
import gui.window.GameParamWindow;
import gui.window.GameWindow;
import gui.window.LogWindow;
import locale.RobotsLocale;
import log.Logger;

/**
 * Что требуется сделать:
 * 1. Метод создания меню перегружен функционалом и трудно читается. 
 * Следует разделить его на серию более простых методов (или вообще выделить отдельный класс).
 *
 */
public class MainApplicationFrame extends JFrame
{
    private final JDesktopPane desktopPane = new JDesktopPane();
    
    public MainApplicationFrame() {


        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
            screenSize.width  - inset*2,
            screenSize.height - inset*2);

        setContentPane(desktopPane);
        
        
//        LogWindow logWindow = createLogWindow();
//        addWindow(logWindow);

//        GameWindow gameWindow = new GameWindow();
//        gameWindow.setSize(400,  400);
//        addWindow(gameWindow);
//        CoordinatesWindow coordinatesWindow=new CoordinatesWindow(gameWindow.m_controler);
//        coordinatesWindow.setSize(300,300);
//        addWindow(coordinatesWindow);

        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    protected LogWindow createLogWindow()
    {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10,10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug(RobotsLocale.getINSTANCE().getString("frame.log.msg.start"));
        return logWindow;
    }
    
    protected void addWindow(JInternalFrame frame)
    {
        desktopPane.add(frame);
        frame.setVisible(true);
    }
    
//    protected JMenuBar createMenuBar() {
//        JMenuBar menuBar = new JMenuBar();
// 
//        //Set up the lone menu.
//        JMenu menu = new JMenu("Document");
//        menu.setMnemonic(KeyEvent.VK_D);
//        menuBar.add(menu);
// 
//        //Set up the first menu item.
//        JMenuItem menuItem = new JMenuItem("New");
//        menuItem.setMnemonic(KeyEvent.VK_N);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_N, ActionEvent.ALT_MASK));
//        menuItem.setActionCommand("new");
////        menuItem.addActionListener(this);
//        menu.add(menuItem);
// 
//        //Set up the second menu item.
//        menuItem = new JMenuItem("Quit");
//        menuItem.setMnemonic(KeyEvent.VK_Q);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_Q, ActionEvent.ALT_MASK));
//        menuItem.setActionCommand("quit");
////        menuItem.addActionListener(this);
//        menu.add(menuItem);
// 
//        return menuBar;
//    }
    
    private JMenuBar generateMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu lookAndFeelMenu = new JMenu(RobotsLocale.getINSTANCE().getString("menu.look"));
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
                "Управление режимом отображения приложения");
        
        {
            JMenuItem systemLookAndFeel = new JMenuItem(RobotsLocale.getINSTANCE().getString("menu.look.system"), KeyEvent.VK_S);
            systemLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                this.invalidate();
            });
            lookAndFeelMenu.add(systemLookAndFeel);
        }

        {
            JMenuItem crossplatformLookAndFeel = new JMenuItem(RobotsLocale.getINSTANCE().getString("menu.look.universal"), KeyEvent.VK_S);
            crossplatformLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                this.invalidate();
            });
            lookAndFeelMenu.add(crossplatformLookAndFeel);
        }

        JMenu testMenu = new JMenu(RobotsLocale.getINSTANCE().getString("menu.test"));
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(
                "Тестовые команды");
        
        {
            JMenuItem addLogMessageItem = new JMenuItem(RobotsLocale.getINSTANCE().getString("menu.test.msg.simple"), KeyEvent.VK_S);
            addLogMessageItem.addActionListener((event) -> {
                Logger.debug(RobotsLocale.getINSTANCE().getString("frame.log.msg.simple"));
            });
            testMenu.add(addLogMessageItem);
        }

        JMenu languageMenu = new JMenu(RobotsLocale.getINSTANCE().getString("menu.language"));
        testMenu.setMnemonic(KeyEvent.VK_T);
        {
            JMenuItem ruLocale = new JMenuItem(RobotsLocale.getINSTANCE().getString("menu.language.ru"), KeyEvent.VK_S);
            ruLocale.addActionListener((event) -> {
                RobotsLocale.setLang(new Locale("ru", "RU"));
            });
            JMenuItem enLocale = new JMenuItem(RobotsLocale.getINSTANCE().getString("menu.language.en"), KeyEvent.VK_S);
            enLocale.addActionListener((event) -> {
                RobotsLocale.setLang(new Locale("en", "US"));
            });
            languageMenu.add(ruLocale);
            languageMenu.add(enLocale);
        }

        JMenu gameMenu = new JMenu(RobotsLocale.getINSTANCE().getString("menu.game"));
        gameMenu.setMnemonic(KeyEvent.VK_G);
        gameMenu.getAccessibleContext().setAccessibleDescription(
                "Запуск игры");
        {
            JMenuItem robot = new JMenuItem(RobotsLocale.getINSTANCE().getString("menu.game.robot"), KeyEvent.VK_R);
            robot.addActionListener((event) -> {
                GameParamWindow gameParamWindow=new GameParamWindow();
                addWindow(gameParamWindow);

            });
            gameMenu.add(robot);
        }

        menuBar.add(lookAndFeelMenu);
        menuBar.add(testMenu);
        menuBar.add(languageMenu);
        menuBar.add(gameMenu);

        return menuBar;
    }
    
    private void setLookAndFeel(String className)
    {
        try
        {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        }
        catch (ClassNotFoundException | InstantiationException
            | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            // just ignore
        }
    }


}
