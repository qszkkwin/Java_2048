package com.qsz.demo1;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 author：漆胜之
 operating_system: qsz
 time:2026/2/23 16:41
 descriptive:
 初步整合所有功能性代码,模块化的第一步。
 */

public class MainFrame extends JFrame implements KeyListener, ActionListener {
    int[][] data = new int[4][4];

    // 是否展示失败的图片的开关。
    int loseFlag = 1;

    // 得分。
    int score = 0;

    // 当前使用的背景皮肤（默认为background.png）
    String backgroundPath = "assets/images/background1.png";

    // 将item对象提取到成员变量的位置，是为了扩大作用域，使得在鼠标监听的方法中可以调用。
    JMenuItem item1 = new JMenuItem("经典");
    JMenuItem item2 = new JMenuItem("霓虹");
    JMenuItem item3 = new JMenuItem("糖果");

    JMenuItem item4 = new JMenuItem("喜欢");
    JMenuItem item5 = new JMenuItem("很喜欢");
    JMenuItem item6 = new JMenuItem("十分喜欢");

    /**
     构造方法*/
    public MainFrame() {
        // 初始化窗体
        initFrame();
        // 初始化菜单
        initMenu();
        // 初始化数据
        initData();
        // 绘制图片
        paintView();
        // 为窗体添加键盘监听
        addKeyListener(this);//  this的位置就是填写addKeyListener的实现类
        // 设置窗体课可见
        setVisible(true);
    }

    /**
     初始化数据——data数组初始化。*/
    public void initData(){
        // 随机两个位置产生数字2
        generatorNum();
        generatorNum();
    }

    /**
     此方法是添加菜单*/
    public void initMenu() {
        // 1，创建JMenuBar（木棍）
        JMenuBar menuBar = new JMenuBar();

        // 2，创建栏目对象
        JMenu menu1 = new JMenu("换肤");
        JMenu menu2 = new JMenu("关于我们");
        JMenu menu3 = new JMenu("喜欢我们的游戏吗！");

        // 木棍先挂上两个
        menuBar.add(menu1);
        menuBar.add(menu2);
        menuBar.add(menu3);

        // 第一个栏目挂上三个选项
        menu1.add(item1);
        menu1.add(item2);
        menu1.add(item3);

        menu2.add("头号玩家,祝你玩的开心！");

        // 第三个栏目上挂上剩下三个选项
        menu3.add(item4);
        menu3.add(item5);
        menu3.add(item6);

        // 为每一个小窗口添加监听。
        item1.addActionListener(this);
        item2.addActionListener(this);
        item3.addActionListener(this);
        item4.addActionListener(this);
        item5.addActionListener(this);
        item6.addActionListener(this);

        // 给窗体对象设置菜单。（将这个自定义好的棍子 放在窗体中）
        setJMenuBar(menuBar);
    }

    /**
     此方法用于初始化窗体，所有窗体的相关设置都在这个方法中完成。
     */
    public void initFrame() {
        // 窗口标题；
        setTitle("2048小游戏(作者：漆胜之)");
        //窗口大小
        setSize(514, 538);
        // 设置窗体居中。
        setLocationRelativeTo(null);
        // 窗体置顶。
        setAlwaysOnTop(true);
        // 设置关闭模式。点击叉号直接结束程序。
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 取消默认布局，不然无法设置自定义。
        setLayout(null);
    }

    /**
     此方法是从空白位置随机产生数字2
     */
    public void generatorNum() {
        // 1,创建两个数组，准备记录空格处的i的值，和j的值。静态初始化（动态初始化会初始每一个元素值为0）
        int[] arrayJ = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        int[] arrayI = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};

        int w = 0;
        // 2, 遍历data，判断每一个方块是不是空白格式，是的话就拿出来。
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] == 0) {
                    // 3，存入相应的j和i的值，分别在两个数组之中。
                    arrayJ[w] = j;
                    arrayI[w] = i;
                    w++;
                }
            }
        }
        // 4，如果，w的记录量不是0，代表数组中还有空白的位置，就可以产生新的数字方块。
        if (w != 0){
            Random r = new Random();
            int index = r.nextInt(w);
            // 就是这里可能会搞混x，y的对应的位置——i行数就是高度，j就是列数就是x宽度。但是你一一对应就无需思考。
            int y = arrayJ[index];
            int x = arrayI[index];

            // 上面的所有步骤都是为了随机x，y的值，有了xy的随机值，直接对应位置data值为2即可。
            data[x][y] = 2;
        }
    }

    /**
     此方法是用于插入游戏素材图片
     */
    public void paintView() {

        // 移除掉，界面的所有内容。
        getContentPane().removeAll();

        // 判断是否失败，失败则绘画失败图片。
        if (loseFlag == 2) {

            JLabel loseLabel = new JLabel(new ImageIcon("assets/images/gameOver.png"));
            loseLabel.setBounds(90, 200, 300, 40);
            getContentPane().add(loseLabel);
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                JLabel jl1 = new JLabel(new ImageIcon("assets/images/" + data[j][i] + ".png")); // 字符串的拼接，插入素材地址。
                jl1.setBounds(50 + 100 * i, 60 + 100 * j, 95, 95); // 这个宽和高类似为裁剪插入的图片。
                getContentPane().add(jl1);// 甚至super都可以不写，因为子类没有重写父类的方法，所以可以直接调用父类方法。
            }
        }
        // 背景图片
        // 定义背景图片路径变量，方便后续通过菜单切换皮肤时修改此变量即可更换背景
        JLabel jl17 = new JLabel(new ImageIcon(backgroundPath));
        jl17.setBounds(40, 50, 420, 420);
        getContentPane().add(jl17); // 省略了super的写法，有点像python中的直接调用函数方法了。

        // 得分小窗口。
        JLabel scoreLabel = new JLabel("得分：" + score);
        scoreLabel.setBounds(50, 20, 100, 20);
        getContentPane().add(scoreLabel);

        // 刷新界面的方法：
        getContentPane().repaint();
    }

    /**
     * 此方法用于处理键入字符的事件。
     * 在2048游戏中，我们主要关注方向键的按下事件（keyPressed），
     * 因此该方法通常不需要执行任何操作，但作为KeyListener接口的实现必须保留。
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // 空实现，无需处理字符键入事件
    }

    /**
     按键监听：此方法是键盘按下时，所触发的方法，在这个方法中区分出上下左右的按键。
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();// 将抽象的按键操作接收为编码数字。

        if (keyCode == 37) {
            moveToleft(1);
            System.out.println("执行向左移动");
            generatorNum();

        } else if (keyCode == 38) {
            moveToUp(1);
            System.out.println("执行向上移动");
            generatorNum();

        } else if (keyCode == 39) {
            moveToRight(1);
            System.out.println("执行向右移动");
            generatorNum();

        } else if (keyCode == 40) {
            moveToDown(1);
            System.out.println("执行向下移动");
            generatorNum();
        }
        // 检查游戏状态；合格就继续画paintView()
        check();
        // 每次执行完按键操作，窗口上的图片位置都会改变，所以每次执行完都要重新绘制。
        paintView();
    }

    /**
     此方法是用于左移动。
     */
    public void moveToleft(int flag) {
        for (int i = 0; i < data.length; i++) {
            // 1，将每一行的非零数字移动到最左边
            int[] newArr = new int[4];
            int index = 0;
            for (int x = 0; x < data[i].length; x++) {
                if (data[i][x] != 0) {
                    newArr[index] = data[i][x];
                    index++;
                }
            }
            // 2，再将原数组的指针 指向 这个排序好的数组地址 即完成非零元素的前移。
            data[i] = newArr;

            // 3, 合并操作：合并元素（相邻相同数字相加）
            for (int x = 0; x < 3; x++) {   // 注意限制条件，
                if (data[i][x] == data[i][x + 1]) {
                    data[i][x] *= 2;

                    if (flag == 1) {
                        // 计算真正操作得分：
                        score += data[i][x];
                    }

            // 4，后续元素前移，并且在末尾补上0；
                    for (int j = x + 1; j < 3; j++) data[i][j] = data[i][j + 1];
                    data[i][3] = 0; // 注意末尾元素必须是零（因为只要有元素合并，就会少一个，少一个默认就是零。
                }
            }
        }
    }

    /**
     此方法用于向右移动。
     */
    public void moveToRight(int flag) {
        /*
         * 向右移动，利用了向左移动的镜像：在镜子里，向左移，就是向右移，所以只要颠倒顺序，就是向右移
         */
        // 1，第一步是反转二维数组。
        horizontalSwap();
        // 2, 向左边移动
        moveToleft(flag);
        // 3，再次反转数组
        horizontalSwap();

    }

    /**
     此方法执行向上移动的操作
     */
    public void moveToUp(int flag) {
        // 1,先执行逆时针90
        Rotate90CounterClockwise();
        // 2, 向左边移动
        moveToleft(flag);
        // 3，顺时针转回来
        RotateArray90();
    }

    /**
     此方法执行向下移动的操作
     */
    public void moveToDown(int flag) {
        // 1，二维数组顺时针旋转90°
        RotateArray90();
        // 2，进行向左移动模块。
        moveToleft(flag);
        // 3，再逆时针90°旋转数组。恢复原数组位置。
        Rotate90CounterClockwise();

        // 综上，需要写函数：1：转置，2：顺时针旋转九十度，3：逆时针旋转九十度.
    }

    /**
     此方法用于一维数组的反转
     */
    public void reverseArray(int[] arr) {
        for (int start = 0, end = arr.length - 1; start < end; start++, end--) {
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
        }
    }

    /**
     逆时针旋转九十度
     */
    public void Rotate90CounterClockwise() {
        // （逆时针旋转一种思路：数组先转置，镜像，在调用一维数组的反转，即逆转置，再镜像（调用二维数组的反转），即逆旋转90°）
        MatrixTranspose();
        horizontalSwap();
        reverse2DRowsInPlace();// reverseArray(arr3);
        // 前三步 完成了逆转置
        horizontalSwap();
    }

    /**
     再补充一种逆时针旋转90°：位置映射
     */
    public void Rotate90CounterClockwise2() {
        int[][] result = new int[4][4];
        // 利用在ij互换的基础上，能发现i的顺序是：3 - i的关系。但是实操下来有个bug，向上移动会有两种操作。
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[j][3 - i] = data[i][j];
            }
        }
        data = result;
    }

    /**
     顺时针旋转九十度
     */
    public void RotateArray90() {
        // （顺时针思路：转置，镜像 即：顺时针九十度。）
        MatrixTranspose();
        horizontalSwap();

    }

    /**
     矩阵的转置
     */
    public void MatrixTranspose() {
        // 矩阵转置（行变列，列变行）
        int[][] transposed = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                transposed[i][j] = data[j][i];
            }
        }
        data = transposed;
    }

    /**
     镜像操作：此方法用于二维数组的反转，
     */
    public void horizontalSwap() {
        for (int i = 0; i < data.length; i++) {
            // 调用一维数组的反转。
            reverseArray(data[i]);
        }
    }

    /**
     原地逆序二维数组的行（直接修改原数组）
     */
    public void reverse2DRowsInPlace() {
        int start = 0;
        int end = data.length - 1;

        while (start < end) {
            // 交换 start 和 end 位置的行（直接交换引用）
            int[] tempRow = data[start];
            data[start] = data[end];
            data[end] = tempRow;

            // 移动指针
            start++;
            end--;
        }
    }

    /**
     此方法是用于整合判断四个方向移动的失败情况
     */
    public void check() {
        // 这里有一个点：函数即使在判断语句中提到，也是算调用。
        if (checkDown() == false && checkLeft() == false && checkRight() == false && checkUp() == false) {
            System.out.println("游戏失败");
            loseFlag = 2;
        }
    }

    /**
     此方法用于二维数组的复制。
     @param src：原数组
     @param dest：目标数组
     */
    public void copyArray(int[][] src, int[][] dest) {
        for (int i = 0; i < src.length; i++) {
            for (int j = 0; j < src[i].length; j++) {
                dest[i][j] = src[i][j];
            }
        }
    }

    /**
     此方法判断 是否可以左移动
     */
    public boolean checkLeft() {
        // 1,创建一个新的数组
        int[][] newArr = new int[4][4];
        // 2, 将原数组的数据拷贝到新数组中。
        copyArray(data, newArr);
        // 3,直接调用左移动的方法。
        moveToleft(2);
        // 4,使用移动后的数据，和备份的数据进行比较，并且用flag变量进行记录。
        boolean flag = false;
        lo:
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] != newArr[i][j]) {
                    flag = true;
                    break lo;
                }
            }
        }
        // 设计思路；
        // true：可以移动。
        // false: 不可以移动

        // 5，确定信息后，恢复原数组的数据，拷贝备份数据。
        copyArray(newArr, data);
        // 6,返回结果信息。
        return flag;
    }

    /**
     此方法判断是否可以上移动
     */
    public boolean checkUp() {
        int[][] newArr = new int[4][4];
        copyArray(data, newArr);
        moveToUp(2);
        boolean flag = false;
        lo:
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] != newArr[i][j]) {
                    flag = true;
                    break lo;
                }
            }
        }
        copyArray(newArr, data);
        return flag;
    }

    /**
     此方法判断是否可以右移动
     */
    public boolean checkRight() {
        int[][] newArr = new int[4][4];
        copyArray(data, newArr);
        moveToRight(2);
        boolean flag = false;
        lo:
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] != newArr[i][j]) {
                    flag = true;
                    break lo;
                }
            }
        }
        copyArray(newArr, data);
        return flag;
    }

    /**
     此方法判断是否可以下移动
     */
    public boolean checkDown() {
        int[][] newArr = new int[4][4];
        copyArray(data, newArr);
        moveToDown(2);
        boolean flag = false;
        lo:
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] != newArr[i][j]) {
                    flag = true;
                    break lo;
                }
            }
        }
        copyArray(newArr, data);
        return flag;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    /**
     鼠标监听，为点击创建执行对象，执行更换皮肤的操作
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // 也就是形象化的理解为鼠标点击区域的不同而触发不同的效果。对比键盘监听
        if (e.getSource() == item1){ // 理解为点击到item1的区域时：
            System.out.println("换肤为经典配色");
            backgroundPath = "assets/images/background1.png";
            paintView();

        }else if (e.getSource() == item2){
            System.out.println("换肤为霓虹配色");
            backgroundPath = "assets/images/background3.png";
            paintView();

        }else if (e.getSource() == item3){
            System.out.println("换肤为糖果配色");
            backgroundPath = "assets/images/background2.png";
            paintView();
        }
    }
}
