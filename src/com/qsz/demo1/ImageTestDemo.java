package com.qsz.demo1;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * 图片显示测试Demo
 * 用于测试2048游戏中所有素材图片的显示效果
 */
public class ImageTestDemo extends JFrame {

    public ImageTestDemo() {
        initFrame();
        createImagePanel();
        setVisible(true);
    }

    private void initFrame() {
        setTitle("2048游戏图片素材测试");
        setSize(900, 1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void createImagePanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        String[] imageNames = {
                "2", "4", "8", "16", "32", "64",
                "128", "256", "512", "1024", "2048",
                "4096", "8192", "16384", "32768", "65536", "131072",
                "background1", "background2", "background3", "scorebg", "gameOver", "restart"
        };

        System.out.println("当前工作目录: " + System.getProperty("user.dir"));
        System.out.println("开始加载图片...\n");

        int successCount = 0;
        int failCount = 0;

        for (String imageName : imageNames) {
            JPanel imagePanel = new JPanel();
            imagePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
            imagePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
            imagePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            imagePanel.setBackground(Color.WHITE);
            imagePanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

            String imagePath = "assets/images/" + imageName + ".png";
            File imageFile = new File(imagePath);

            try {
                System.out.println("尝试加载: " + imagePath);
                System.out.println("  文件存在: " + imageFile.exists());
                System.out.println("  绝对路径: " + imageFile.getAbsolutePath());

                ImageIcon icon = new ImageIcon(imagePath);

                if (icon.getIconWidth() == -1) {
                    JLabel errorLabel = new JLabel("❌ 图片加载失败: " + imageName + ".png");
                    errorLabel.setForeground(Color.RED);
                    errorLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
                    imagePanel.add(errorLabel);
                    failCount++;
                    System.out.println("  结果: 加载失败 ❌\n");
                } else {
                    JLabel nameLabel = new JLabel(imageName + ".png");
                    nameLabel.setFont(new Font("微软雅黑", Font.BOLD, 12));
                    nameLabel.setForeground(new Color(50, 50, 50));

                    JLabel sizeLabel = new JLabel("(" + icon.getIconWidth() + "x" + icon.getIconHeight() + ")");
                    sizeLabel.setFont(new Font("微软雅黑", Font.PLAIN, 11));
                    sizeLabel.setForeground(Color.GRAY);

                    JLabel imageLabel = new JLabel(icon);
                    imageLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

                    JPanel infoPanel = new JPanel();
                    infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
                    infoPanel.setBackground(Color.WHITE);
                    infoPanel.add(nameLabel);
                    infoPanel.add(sizeLabel);

                    imagePanel.add(infoPanel);
                    imagePanel.add(imageLabel);
                    successCount++;
                    System.out.println("  结果: 加载成功 ✅\n");
                }
            } catch (Exception e) {
                JLabel errorLabel = new JLabel("❌ 异常: " + imageName + ".png");
                errorLabel.setForeground(Color.RED);
                errorLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
                imagePanel.add(errorLabel);
                failCount++;
                System.out.println("  结果: 异常 - " + e.getMessage() + "\n");
            }

            mainPanel.add(imagePanel);
            mainPanel.add(Box.createVerticalStrut(5));
        }

        System.out.println("===========================");
        System.out.println("加载完成！成功: " + successCount + ", 失败: " + failCount);
        System.out.println("===========================\n");

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBackground(Color.WHITE);

        add(scrollPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ImageTestDemo());
    }
}
