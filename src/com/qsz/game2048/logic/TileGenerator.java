package com.qsz.game2048.logic;

import com.qsz.game2048.model.GameBoard;

/**
 * 方块生成器接口 - 可替换不同策略
 */
public interface TileGenerator {
    /**
     * 在棋盘空位生成一个新方块
     * @param board 游戏棋盘
     * @return 是否成功生成（有可用空位）
     */
    boolean generate(GameBoard board);
}
