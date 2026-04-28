package com.qsz.game2048.view;

import com.qsz.game2048.config.GameConfig;
import com.qsz.game2048.logic.GameEngine;
import com.qsz.game2048.model.Direction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * 游戏主窗口 - 负责窗口初始化、键盘/菜单事件转发给引擎和面板
 *
 * 设计原则：只做"胶水层"，不包含任何游戏逻辑
 */
public class GameFrame extends JFrame implements ActionListener {

    private final GameEngine engine;
    private final GameBoardPanel panel;

    // 菜单项
    private final JMenuItem classicItem = new JMenuItem("经典");
    private final JMenuItem neonItem = new JMenuItem("霓虹");
    private final JMenuItem candyItem = new JMenuItem("糖果");
    private final JMenuItem restartItem = new JMenuItem("重新开始");

    public GameFrame(GameEngine engine) {
        this.engine = engine;
        this.panel = new GameBoardPanel(engine.getBoard());

        initWindow();
        initMenu();
        bindKeyboard();
        engine.restart(); // 初始生成 2 个方块

        add(panel);
        setVisible(true);
    }

    // ==================== 窗口初始化 ====================

    private void initWindow() {
        setTitle(GameConfig.WINDOW_TITLE);
        setSize(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    // ==================== 菜单 ====================

    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu skinMenu = new JMenu("换肤");
        JMenu controlMenu = new JMenu("控制");

        skinMenu.add(classicItem);
        skinMenu.add(neonItem);
        skinMenu.add(candyItem);
        controlMenu.add(restartItem);

        menuBar.add(skinMenu);
        menuBar.add(controlMenu);

        classicItem.addActionListener(this);
        neonItem.addActionListener(this);
        candyItem.addActionListener(this);
        restartItem.addActionListener(this);

        setJMenuBar(menuBar);
    }

    // ==================== 键盘绑定 ====================

    private void bindKeyboard() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                Direction dir;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_A:
                        dir = Direction.LEFT;
                        break;
                    case KeyEvent.VK_RIGHT:
                    case KeyEvent.VK_D:
                        dir = Direction.RIGHT;
                        break;
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_W:
                        dir = Direction.UP;
                        break;
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_S:
                        dir = Direction.DOWN;
                        break;
                    default:
                        dir = null;
                }

                if (dir != null) {
                    engine.executeMove(dir);
                    panel.repaint();
                }
            }
        });
    }

    // ==================== 菜单事件 ====================

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == classicItem || source == neonItem || source == candyItem) {
            JMenuItem item = (JMenuItem) source;
            panel.switchSkin(item.getText());
        } else if (source == restartItem) {
            engine.restart();
            panel.repaint();
        }
    }
}
