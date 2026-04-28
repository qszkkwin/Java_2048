package com.qsz.game2048.config;

/**
 * 游戏全局常量配置，一些游戏参数，一旦修改，全局生效，便于后期集中统一维护。
 */
public final class GameConfig {

    private GameConfig() {}

    // ========== 棋盘 ==========
    public static final int BOARD_SIZE = 4;
    public static final int TARGET_VALUE = 2048;

    // ========== 窗口 ==========
    public static final String WINDOW_TITLE = "2048";
    public static final int WINDOW_WIDTH = 514;
    public static final int WINDOW_HEIGHT = 538;

    // ========== 网格布局 ==========
    public static final int GRID_X = 50;
    public static final int GRID_Y = 60;
    public static final int CELL_SIZE = 100;
    public static final int TILE_SIZE = 95;

    // ========== 得分区域 ==========
    public static final int SCORE_X = 50;
    public static final int SCORE_Y = 20;
    public static final int SCORE_WIDTH = 100;
    public static final int SCORE_HEIGHT = 20;

    // ========== 游戏结束 ==========
    public static final int GAME_OVER_X = 90;
    public static final int GAME_OVER_Y = 200;
    public static final int GAME_OVER_WIDTH = 300;
    public static final int GAME_OVER_HEIGHT = 40;

    // ========== 图片路径（classpath 资源路径，兼容 JAR 内运行） ==========
    public static final String IMAGE_DIR = "/assets/images/";
    public static final String BACKGROUND_CLASSIC = IMAGE_DIR + "background1.png";
    public static final String BACKGROUND_NEON = IMAGE_DIR + "background3.png";
    public static final String BACKGROUND_CANDY = IMAGE_DIR + "background2.png";
    public static final String IMAGE_GAME_OVER = IMAGE_DIR + "gameOver.png";
    public static final String IMAGE_RESTART = IMAGE_DIR + "restart.png";
    public static final String IMAGE_SCORE_BG = IMAGE_DIR + "scorebg.png";

    // ========== 随机生成 ==========
    public static final double PROBABILITY_OF_4 = 0.1;
}
