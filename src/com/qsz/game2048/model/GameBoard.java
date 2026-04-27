package com.qsz.game2048.model;

import com.qsz.game2048.config.GameConfig;

/**
 * 游戏棋盘数据模型
 * 封装棋盘数据和得分，只暴露必要的读写接口
 */
public class GameBoard {

    private final int size;
    private final int[][] tiles;
    private int score;
    private GameState state;

    public GameBoard() {
        this.size = GameConfig.BOARD_SIZE;
        this.tiles = new int[size][size];
        this.score = 0;
        this.state = GameState.RUNNING;
    }

    // ==================== 基础访问 ====================

    public int getSize() {
        return size;
    }

    public int getTile(int row, int col) {
        return tiles[row][col];
    }

    public void setTile(int row, int col, int value) {
        tiles[row][col] = value;
    }

    public int[][] getTiles() {
        return tiles;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        this.score += points;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public boolean isRunning() {
        return state == GameState.RUNNING;
    }

    public boolean isGameOver() {
        return state == GameState.GAME_OVER;
    }

    // ==================== 坐标工具 ====================

    /**
     * 记录单个棋盘位置
     */
    public static class Position {
        public final int row;
        public final int col;

        public Position(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    /**
     * 获取所有空位的坐标
     * @return 空位数组，无空位时返回长度为0的数组
     */
    public Position[] getEmptyPositions() {
        Position[] temp = new Position[size * size];
        int count = 0;
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (tiles[r][c] == 0) {
                    temp[count++] = new Position(r, c);
                }
            }
        }
        Position[] result = new Position[count];
        System.arraycopy(temp, 0, result, 0, count);
        return result;
    }

    /**
     * 坐标旋转：顺时针 90 度 * rotationSteps
     */
    public static int mapRow(int row, int col, int size, int rotationSteps) {
        return switch (rotationSteps % 4) {
            case 0 -> row;
            case 1 -> col;
            case 2 -> size - 1 - row;
            case 3 -> size - 1 - col;
            default -> row;
        };
    }

    public static int mapCol(int row, int col, int size, int rotationSteps) {
        return switch (rotationSteps % 4) {
            case 0 -> col;
            case 1 -> size - 1 - row;
            case 2 -> size - 1 - col;
            case 3 -> row;
            default -> col;
        };
    }

    /**
     * 重置棋盘（重新开始）
     */
    public void reset() {
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                tiles[r][c] = 0;
            }
        }
        score = 0;
        state = GameState.RUNNING;
    }
}
