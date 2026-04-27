# 2048 小游戏

Java Swing 实现的 2048 游戏，采用 MVC 架构，支持换肤、纯色回退。

## 运行

```bash
# 编译
javac -encoding UTF-8 -sourcepath src -d out src/com/qsz/game2048/App.java

# 运行
java -cp out com.qsz.game2048.App
```

## 项目结构

```
src/com/qsz/
├── game2048/                    # 优化版（MVC 结构）
│   ├── App.java                 # 启动入口
│   ├── config/GameConfig.java   # 常量管理
│   ├── model/
│   │   ├── Direction.java       # 方向枚举
│   │   ├── GameState.java       # 游戏状态枚举
│   │   └── GameBoard.java       # 棋盘数据模型
│   ├── logic/
│   │   ├── TileGenerator.java           # 方块生成策略接口
│   │   ├── RandomTileGenerator.java     # 随机生成（90%出2，10%出4）
│   │   └── GameEngine.java             # 移动/合并/游戏结束检测
│   └── view/
│       ├── GameBoardPanel.java  # 自定义双缓冲绘制
│       └── GameFrame.java       # 主窗口 + 事件转发
├── demo1/                       # 原版（保留对比）
│   ├── MainFrame.java           # 562 行上帝类
│   └── testMainFrame.java       # 入口
└── demo2/                       # 算法练习
    ├── Demo1.java               # 零元素后置
    ├── Demo2.java               # 合并相邻元素
    └── Demo3.java               # 二维数组左移
```

## 重构优化点

| 维度 | 原版 (demo1) | 优化版 (game2048) |
|------|-------------|-------------------|
| 架构 | 1 个类 562 行 | MVC 三层 7 个类 |
| 绘制 | removeAll + new JLabel × 17 | paintComponent + 图片缓存 |
| 方向处理 | 四套独立方法 + 矩阵旋转 | 坐标映射公式，统一为左移 |
| 游戏结束检测 | 拷贝-移动-恢复 × 4 | O(n²) 单次遍历 |
| 键盘码 | 硬编码 37/38/39/40 | KeyEvent 语义常量 |
| 游戏状态 | int 标志位 1/2 | GameState 枚举 |
| 无素材回退 | 不支持 | 纯色方块自动降级 |

## 功能

- 键盘操作：方向键 / WASD
- 换肤：菜单栏支持经典 / 霓虹 / 糖果
- 重新开始：菜单栏 → 控制 → 重新开始
