package com.qsz.game2048.view;

import com.qsz.game2048.config.GameConfig;
import com.qsz.game2048.model.GameBoard;
import com.qsz.game2048.model.GameState;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 游戏棋盘绘制面板 - 使用 paintComponent 自定义绘制
 *
 * 优势：
 * - 零组件创建/销毁，通过 repaint 驱动
 * - 图片缓存，避免重复 IO
 * - 纯色回退，无素材卡也能正常显示
 */
public class GameBoardPanel extends JPanel {

    private GameBoard board;
    private String backgroundPath = GameConfig.BACKGROUND_CLASSIC;

    // 图片缓存：路径 → Image
    private final Map<String, Image> imageCache = new HashMap<>();

    // 方块颜色映射（纯色回退用）
    private static final Map<Integer, Color> TILE_COLORS = new HashMap<>();

    static {
        TILE_COLORS.put(2,    new Color(0xEEE4DA));
        TILE_COLORS.put(4,    new Color(0xEDE0C8));
        TILE_COLORS.put(8,    new Color(0xF2B179));
        TILE_COLORS.put(16,   new Color(0xF59563));
        TILE_COLORS.put(32,   new Color(0xF67C5F));
        TILE_COLORS.put(64,   new Color(0xF65E3B));
        TILE_COLORS.put(128,  new Color(0xEDCF72));
        TILE_COLORS.put(256,  new Color(0xEDCC61));
        TILE_COLORS.put(512,  new Color(0xEDC850));
        TILE_COLORS.put(1024, new Color(0xEDC53F));
        TILE_COLORS.put(2048, new Color(0xEDC22E));
        // 超 2048 使用统一颜色
    }

    public GameBoardPanel(GameBoard board) {
        this.board = board;
        setLayout(null);
        setPreferredSize(new Dimension(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT));
    }

    public void setBoard(GameBoard board) {
        this.board = board;
    }

    // ==================== 换肤 ====================

    public void switchSkin(String skin) {
        switch (skin) {
            case "经典" -> backgroundPath = GameConfig.BACKGROUND_CLASSIC;
            case "霓虹" -> backgroundPath = GameConfig.BACKGROUND_NEON;
            case "糖果" -> backgroundPath = GameConfig.BACKGROUND_CANDY;
        }
        imageCache.clear();
        repaint();
    }

    // ==================== 绘制 ====================

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawBackground(g2d);
        drawTiles(g2d);
        drawScore(g2d);
        drawGameOver(g2d);
    }

    private void drawBackground(Graphics2D g) {
        Image bg = loadImage(backgroundPath);
        if (bg != null) {
            g.drawImage(bg, 40, 50, 420, 420, null);
        }
    }

    private void drawTiles(Graphics2D g) {
        int size = board.getSize();
        int[][] tiles = board.getTiles();

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                int value = tiles[r][c];
                int x = GameConfig.GRID_X + GameConfig.CELL_SIZE * c;
                int y = GameConfig.GRID_Y + GameConfig.CELL_SIZE * r;

                Image tileImg = loadImage(GameConfig.IMAGE_DIR + value + ".png");
                if (tileImg != null) {
                    g.drawImage(tileImg, x, y, GameConfig.TILE_SIZE, GameConfig.TILE_SIZE, null);
                } else if (value != 0) {
                    // 纯色回退
                    Color bg = TILE_COLORS.getOrDefault(value, Color.RED);
                    g.setColor(bg);
                    g.fillRoundRect(x, y, GameConfig.TILE_SIZE, GameConfig.TILE_SIZE, 10, 10);

                    g.setColor(value <= 4 ? Color.BLACK : Color.WHITE);
                    g.setFont(new Font("微软雅黑", Font.BOLD, 28));
                    String text = String.valueOf(value);
                    FontMetrics fm = g.getFontMetrics();
                    int textX = x + (GameConfig.TILE_SIZE - fm.stringWidth(text)) / 2;
                    int textY = y + (GameConfig.TILE_SIZE + fm.getAscent() - fm.getDescent()) / 2;
                    g.drawString(text, textX, textY);
                }
            }
        }
    }

    private void drawScore(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g.drawString("得分：" + board.getScore(),
                GameConfig.SCORE_X, GameConfig.SCORE_Y + GameConfig.SCORE_HEIGHT - 5);
    }

    private void drawGameOver(Graphics2D g) {
        if (board.getState() == GameState.GAME_OVER) {
            Image goImg = loadImage(GameConfig.IMAGE_GAME_OVER);
            if (goImg != null) {
                g.drawImage(goImg,
                        GameConfig.GAME_OVER_X, GameConfig.GAME_OVER_Y,
                        GameConfig.GAME_OVER_WIDTH, GameConfig.GAME_OVER_HEIGHT, null);
            } else {
                g.setColor(new Color(0, 0, 0, 180));
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(Color.WHITE);
                g.setFont(new Font("微软雅黑", Font.BOLD, 40));
                String text = "游戏结束";
                FontMetrics fm = g.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(text)) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g.drawString(text, x, y);
            }
        }
    }

    // ==================== 图片加载 ====================

    private Image loadImage(String path) {
        return imageCache.computeIfAbsent(path, p -> {
            ImageIcon icon = new ImageIcon(p);
            if (icon.getIconWidth() == -1) {
                return null; // 图片不存在
            }
            return icon.getImage();
        });
    }
}
