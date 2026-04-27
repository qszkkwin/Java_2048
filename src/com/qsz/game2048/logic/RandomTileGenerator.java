package com.qsz.game2048.logic;

import com.qsz.game2048.config.GameConfig;
import com.qsz.game2048.model.GameBoard;

import java.util.Random;

/**
 * 随机方块生成器：在空位随机生成 2（90%）或 4（10%）
 */
public class RandomTileGenerator implements TileGenerator {

    private final Random random = new Random();

    @Override
    public boolean generate(GameBoard board) {
        GameBoard.Position[] emptyPositions = board.getEmptyPositions();
        if (emptyPositions.length == 0) {
            return false;
        }

        GameBoard.Position target = emptyPositions[random.nextInt(emptyPositions.length)];
        int value = random.nextDouble() < GameConfig.PROBABILITY_OF_4 ? 4 : 2;
        board.setTile(target.row, target.col, value);
        return true;
    }
}
