package algorithmbasic.questionsets;

import javax.swing.*; // 导入Swing库，用于图形界面
import java.awt.*; // 导入AWT库，用于绘图和事件
import java.awt.event.ActionEvent; // 导入用于处理事件的类
import java.awt.event.ActionListener; // 导入用于监听按钮点击的接口
import java.awt.geom.Path2D; // 导入用于绘制复杂图形路径的类

// 主窗口类，继承自JFrame
public class SierpinskiTriangle extends JFrame {
    private SierpinskiTrianglePanel trianglePanel = new SierpinskiTrianglePanel(); // 创建绘图面板

    // 构造函数
    public SierpinskiTriangle() {
        super("Sierpinski Triangle Generator"); // 调用父类构造函数，设置窗口标题
        setLayout(new BorderLayout()); // 设置布局管理器为边界布局
        add(trianglePanel, BorderLayout.CENTER); // 将绘图面板添加到窗口中央
        JButton nextButton = new JButton("Next Step"); // 创建“下一步”按钮
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                trianglePanel.nextStep(); // 当按钮被点击时，调用面板的nextStep方法
            }
        });
        add(nextButton, BorderLayout.SOUTH); // 将按钮添加到窗口南部（底部）
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置默认关闭操作
        setSize(600, 620); // 设置窗口大小
        setLocationRelativeTo(null); // 设置窗口居中显示
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() { // 安全地启动GUI
            @Override
            public void run() {
                new SierpinskiTriangle().setVisible(true); // 创建并显示主窗口
            }
        });
    }
}


// 绘图面板类，继承自JPanel
class SierpinskiTrianglePanel extends JPanel {
    private int currentOrder = 0; // 用于跟踪当前迭代次序的变量

    // 用于增加迭代次序并重绘面板的方法
    public void nextStep() {
        currentOrder++;
        repaint(); // 调用repaint方法，它将间接调用paintComponent
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // 调用父类的paintComponent方法以确保面板正确绘制
        Graphics2D g2d = (Graphics2D) g; // 将Graphics对象转换为Graphics2D以使用更高级的绘图功能
        Point p1 = new Point(getWidth() / 2, 10); // 计算顶点P1的位置
        Point p2 = new Point(10, getHeight() - 10); // 计算顶点P2的位置
        Point p3 = new Point(getWidth() - 10, getHeight() - 10); // 计算顶点P3的位置
        drawSierpinski(g2d, currentOrder, p1, p2, p3); // 调用递归方法开始绘制三角形
    }

    // 递归方法用于绘制三角形
    private void drawSierpinski(Graphics2D g2d, int order, Point p1, Point p2, Point p3) {
        if (order == 0) {
            drawTriangle(g2d, p1, p2, p3); // 如果迭代次序为0，直接绘制三角形
        } else {
            Point p12 = midpoint(p1, p2); // 计算点P1和P2的中点
            Point p23 = midpoint(p2, p3); // 计算点P2和P3的中点
            Point p31 = midpoint(p3, p1); // 计算点P3和P1的中点

            // 递归调用方法，绘制三个较小的三角形
            drawSierpinski(g2d, order - 1, p1, p12, p31);
            drawSierpinski(g2d, order - 1, p12, p2, p23);
            drawSierpinski(g2d, order - 1, p31, p23, p3);
        }
    }

    // 辅助方法用于绘制一个填充的三角形
    private void drawTriangle(Graphics2D g2d, Point p1, Point p2, Point p3) {
        Path2D path = new Path2D.Double(); // 创建一个新的路径
        path.moveTo(p1.x, p1.y); // 移动到第一个顶点
        path.lineTo(p2.x, p2.y); // 绘制到第二个顶点
        path.lineTo(p3.x, p3.y); // 绘制到第三个顶点
        path.closePath(); // 关闭路径形成三角形
        g2d.fill(path); // 填充路径绘制的三角形 t
    }

    // 辅助方法用于计算两点的中点
    private Point midpoint(Point p1, Point p2) {
        return new Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2); // 返回新点
    }
}


