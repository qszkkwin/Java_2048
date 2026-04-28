package com.qsz.game2048;

import com.qsz.game2048.logic.GameEngine;
import com.qsz.game2048.logic.RandomTileGenerator;
import com.qsz.game2048.model.GameBoard;
import com.qsz.game2048.view.GameFrame;

import javax.swing.*;

/**
 * 2048 游戏入口
 */
public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            GameBoard board = new GameBoard();

            GameEngine engine = new GameEngine(board, new RandomTileGenerator());

            new GameFrame(engine);
        });
    }
}
