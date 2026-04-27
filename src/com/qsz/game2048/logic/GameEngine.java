package com.qsz.game2048.logic;

import com.qsz.game2048.config.GameConfig;
import com.qsz.game2048.model.Direction;
import com.qsz.game2048.model.GameBoard;
import com.qsz.game2048.model.GameState;

/**
 * 游戏核心引擎 - 负责移动、合并、游戏结束检测
 *
 * 设计要点：
 * - 所有方向通过坐标旋转统一为「向左移动」，避免 4 份重复代码
 * - 游戏结束检测使用 O(n²) 单次遍历，不拷贝数组
 * - 移动入口返回 boolean 表示棋盘是否发生变化
 */
public class GameEngine {

    private final GameBoard board;
    private final TileGenerator tileGenerator;
    private final int size;

    public GameEngine(GameBoard board, TileGenerator tileGenerator) {
        this.board = board;
        this.tileGenerator = tileGenerator;
        this.size = board.getSize();
    }

    /**
     * 执行移动 + 生成方块 + 检查游戏结束，返回本次移动得分
     */
    public int executeMove(Direction direction) {
        if (!board.isRunning()) {
            return 0;
        }

        boolean moved = moveTiles(direction);
        if (!moved) {
            return 0;
        }

        tileGenerator.generate(board);

        if (isGameOver()) {
            board.setState(GameState.GAME_OVER);
        }

        return board.getScore();
    }

    /**
     * 朝指定方向移动/合并所有方块
     * @return 棋盘是否发生变化
     */
    private boolean moveTiles(Direction direction) {
        int rotationSteps = switch (direction) {
            case LEFT -> 0;
            case DOWN -> 1;
            case RIGHT -> 2;
            case UP -> 3;
        };

        boolean changed = false;
        // 逐「列」处理：旋转后用坐标映射函数读取每一列的原始数据
        for (int col = 0; col < size; col++) {
            // 提取这一列（旋转后等价为左移的行）
            int[] line = new int[size];
            for (int row = 0; row < size; row++) {
                int srcRow = GameBoard.mapRow(row, col, size, rotationSteps);
                int srcCol = GameBoard.mapCol(row, col, size, rotationSteps);
                line[row] = board.getTile(srcRow, srcCol);
            }

            int[] result = mergeLine(line);
            if (result == null) {
                continue; // 无变化
            }
            changed = true;

            // 写回
            for (int row = 0; row < size; row++) {
                int srcRow = GameBoard.mapRow(row, col, size, rotationSteps);
                int srcCol = GameBoard.mapCol(row, col, size, rotationSteps);
                board.setTile(srcRow, srcCol, result[row]);
            }
        }
        return changed;
    }

    /**
     * 对一行执行紧左 + 合并操作
     * @return 合并后的行，无变化时返回 null
     */
    private int[] mergeLine(int[] line) {
        // 紧左：去零
        int[] compacted = new int[size];
        int pos = 0;
        for (int i = 0; i < size; i++) {
            if (line[i] != 0) {
                compacted[pos++] = line[i];
            }
        }

        // 合并相邻相等元素
        boolean merged = false;
        for (int i = 0; i < pos - 1; i++) {
            if (compacted[i] == compacted[i + 1]) {
                compacted[i] *= 2;
                board.addScore(compacted[i]);
                // 后续元素前移
                for (int j = i + 1; j < pos - 1; j++) {
                    compacted[j] = compacted[j + 1];
                }
                compacted[--pos] = 0;
                merged = true;
            }
        }

        // 判断是否有变化
        boolean changed = merged;
        for (int i = 0; i < size && !changed; i++) {
            if (compacted[i] != line[i]) {
                changed = true;
            }
        }

        return changed ? compacted : null;
    }

    /**
     * 检测游戏是否结束：相邻位置无相同数字且无空位
     */
    private boolean isGameOver() {
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (board.getTile(r, c) == 0) {
                    return false;
                }
                if (c < size - 1 && board.getTile(r, c) == board.getTile(r, c + 1)) {
                    return false;
                }
                if (r < size - 1 && board.getTile(r, c) == board.getTile(r + 1, c)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 重新开始游戏
     */
    public void restart() {
        board.reset();
        tileGenerator.generate(board);
        tileGenerator.generate(board);
    }

    public GameBoard getBoard() {
        return board;
    }
}
