package antclimb;

import java.awt.*; 
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import javax.swing.*;

public class UserPanel extends JPanel implements ActionListener {

	private JLabel lenLabel, velLabel, maxLabel, minLabel; //标签，用于显示提示信息和最长时间和最短时间
	private JTextField lenField, velField; //文本框，用于输入参数
	private JButton startButton, finishButton; //按钮，用于开始游戏和快速完成游戏
	private PlayRoom playRoom; //游戏对象，用于调用playGames方法
	private Timer timer; //定时器，用于控制动画的速度和更新
	private boolean isPlaying; //标志，用于判断是否正在进行游戏
	private int num = 0;//所有情况的次数编号(0~1<<size)
	private int maxTime, minTime, time; //最长时间和最短时间

	public UserPanel() {
		//初始化组件
		lenLabel = new JLabel("请输入木杆长度（单位：厘米）：");
		velLabel = new JLabel("请输入蚂蚁速度（单位：厘米/秒）：");
		maxLabel = new JLabel("最长时间（单位：秒）：0");
		minLabel = new JLabel("最短时间（单位：秒）：0");
		lenField = new JTextField(10);
		velField = new JTextField(10);
		startButton = new JButton("开始游戏");
		finishButton = new JButton("快速完成"); //创建一个快速完成按钮
		playRoom = new PlayRoom();
		playRoom.initAnts(0);
		timer = new Timer(100, this); //创建一个定时器，每隔100毫秒触发一次事件
		isPlaying = false;
		maxTime = -1; //初始化最长时间为-1
		minTime = -1; //初始化最短时间为-1
		time = 0;
		
		//设置布局
		this.setLayout(new BorderLayout());
		
		//创建一个面板，用于放置输入组件
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); //使用流式布局，水平间距为20像素，垂直间距为20像素
		
		//设置组件的大小变化属性
		lenLabel.putClientProperty("JComponent.sizeVariant", "small"); //设置标签为小号字体
		lenField.putClientProperty("JComponent.sizeVariant", "large"); //设置文本框为大号字体
		velLabel.putClientProperty("JComponent.sizeVariant", "small"); //设置标签为小号字体
		velField.putClientProperty("JComponent.sizeVariant", "large"); //设置文本框为大号字体
		startButton.putClientProperty("JComponent.sizeVariant", "large"); //设置按钮为大号字体
		finishButton.putClientProperty("JComponent.sizeVariant", "large"); //设置按钮为大号字体
		maxLabel.putClientProperty("JComponent.sizeVariant", "small"); //设置标签为小号字体
		minLabel.putClientProperty("JComponent.sizeVariant", "small"); //设置标签为小号字体
		
		SwingUtilities.updateComponentTreeUI(inputPanel); //更新面板上的组件大小
		
		//添加组件到输入面板
		inputPanel.add(lenLabel);
		inputPanel.add(lenField);
		inputPanel.add(velLabel);
		inputPanel.add(velField);
		
		//添加输入面板到用户面板的北部
		this.add(inputPanel, BorderLayout.NORTH);
		
		//创建一个面板，用于放置按钮组件
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); //使用流式布局，水平间距为20像素，垂直间距为20像素
		
		//添加组件到按钮面板
		buttonPanel.add(startButton);
		buttonPanel.add(finishButton); //添加快速完成按钮到按钮面板
		buttonPanel.add(maxLabel);
		buttonPanel.add(minLabel);
		
		//添加按钮面板到用户面板的南部
		this.add(buttonPanel, BorderLayout.SOUTH);
		
		//添加事件监听器
		startButton.addActionListener(this);
		finishButton.addActionListener(this); //添加事件监听器到快速完成按钮
		
	}

	public void actionPerformed(ActionEvent e) {
	    if (e.getSource() == finishButton) { //如果点击了快速完成按钮
	        if (!isPlaying) { //如果还没有开始游戏
	            try {
	                int len = Integer.parseInt(lenField.getText()); //获取木杆长度
	                int vel = Integer.parseInt(velField.getText()); //获取蚂蚁速度
	                if (len <= 0 || vel <= 0) { //检查参数是否合法
	                    JOptionPane.showMessageDialog(this, "请输入正数！", "错误", JOptionPane.ERROR_MESSAGE);
	                } else {
	                    playRoom.len = len; //设置木杆长度
	                    playRoom.Velocity = vel; //设置蚂蚁速度
	                    playRoom.playGames(); //调用playGames方法，输出所有情况的最长和最短时间
	                    maxLabel.setText("最长时间（单位：秒）：" + playRoom.maxTime); //更新最长时间标签
	                    minLabel.setText("最短时间（单位：秒）：" + playRoom.minTime); //更新最短时间标签
	                    JOptionPane.showMessageDialog(this, "快速完成成功！", "提示", JOptionPane.INFORMATION_MESSAGE); //显示一个消息对话框，提示用户快速完成成功
	                }
	            } catch (NumberFormatException ex) { //捕获异常，提示用户输入数字
	                JOptionPane.showMessageDialog(this, "请输入数字！", "错误", JOptionPane.ERROR_MESSAGE);
	            }
	        } else { //如果已经开始游戏
	        	playRoom.playGames(); //调用playGames方法，输出所有情况的最长和最短时间
                maxLabel.setText("最长时间（单位：秒）：" + playRoom.maxTime); //更新最长时间标签
                minLabel.setText("最短时间（单位：秒）：" + playRoom.minTime); //更新最短时间标签
	            //JOptionPane.showMessageDialog(this, "请先重启！", "错误", JOptionPane.ERROR_MESSAGE); //显示一个错误对话框，提示用户先暂停游戏
	        }
	    } else if (e.getSource() == startButton) { //如果点击了开始游戏按钮
			if (!isPlaying) { //如果还没有开始游戏
				try {
					int len = Integer.parseInt(lenField.getText()); //获取木杆长度
					int vel = Integer.parseInt(velField.getText()); //获取蚂蚁速度
					if (len <= 0 || vel <= 0) { //检查参数是否合法
						JOptionPane.showMessageDialog(this, "请输入正数！", "错误", JOptionPane.ERROR_MESSAGE);
					} else {
						playRoom.len = len; //设置木杆长度
						playRoom.Velocity = vel; //设置蚂蚁速度
						playRoom.initAnts(num);
						timer.start(); //启动定时器
						isPlaying = true; //设置标志为正在进行游戏
						startButton.setText("暂停游戏"); //修改按钮文本为暂停游戏
					}
				} catch (NumberFormatException ex) { //捕获异常，提示用户输入数字
					JOptionPane.showMessageDialog(this, "请输入数字！", "错误", JOptionPane.ERROR_MESSAGE);
				}
			} else { //如果已经开始游戏
				if (timer.isRunning()) { //如果定时器正在运行
					timer.stop(); //停止定时器
					startButton.setText("继续游戏"); //修改按钮文本为继续游戏
				} else { //如果定时器已经停止
					timer.restart(); //重新启动定时器
					startButton.setText("暂停游戏"); //修改按钮文本为暂停游戏
				}
			}
			
		} else if (e.getSource() == timer) { //如果触发了定时器事件
			if (!playRoom.creepingGame.isEnd()) { //如果游戏还没有结束
				playRoom.creepingGame.drivingGame(playRoom.size, playRoom.incTime); //推进游戏一步
				time += playRoom.incTime;
				repaint(); //重绘面板，更新动画效果
			} else { //如果游戏已经结束
				playRoom.initAnts(++num);
				if (num >= (1 << playRoom.size)) {
					timer.stop(); //停止定时器
					isPlaying = false; //设置标志为没有进行游戏
					startButton.setText("开始游戏"); //修改按钮文本为开始游戏
					JOptionPane.showMessageDialog(this, "游戏结束！", "提示", JOptionPane.INFORMATION_MESSAGE);
					num = 0;
				}
				else {
					if (maxTime == -1 || time > maxTime) { //如果最长时间还没有初始化或者当前时间大于最长时间
						maxTime = time; //更新最长时间
						maxLabel.setText("最长时间（单位：秒）：" + maxTime); //更新最长时间标签
					}
					if (minTime == -1 || time < minTime) { //如果最短时间还没有初始化或者当前时间小于最短时间
						minTime = time; //更新最短时间
						minLabel.setText("最短时间（单位：秒）：" + minTime); //更新最短时间标签
					}
					time = 0;
				}
			}
		}
		
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g); //调用父类的方法，清除面板
		Graphics2D g2 = (Graphics2D) g; //将Graphics对象转换为Graphics2D对象
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //设置抗锯齿效果
		g2.setColor(Color.BLACK); //设置颜色为黑色
		g2.setStroke(new BasicStroke(5)); //设置线条粗细为5像素
		g2.drawLine(50, 100, 50 + playRoom.len, 100); //绘制木杆，起点坐标为(50, 100)，长度为playRoom.len
		for (int i = 0; i < playRoom.size; i++) { //遍历每只蚂蚁
			if (!playRoom.creepingGame.ants[i].isOut) { //如果蚂蚁还没有掉下去
				g2.setColor(Color.WHITE); //设置颜色为红色
				g2.fill(new Ellipse2D.Double(50 + playRoom.creepingGame.ants[i].location - 5, 95, 10, 10)); //绘制蚂蚁的身体，半径为5像素，位置为playRoom.ants[i].location
				g2.setColor(Color.RED);
				g2.drawString(String.valueOf(playRoom.creepingGame.ants[i].id), 50 + playRoom.creepingGame.ants[i].location - 3, 100); //绘制蚂蚁的id，位置为playRoom.ants[i].location
				g2.setColor(Color.BLACK); //设置颜色为黑色
				if (playRoom.creepingGame.ants[i].direction == 0) { //如果蚂蚁朝左
					g2.draw(new Line2D.Double(50 + playRoom.creepingGame.ants[i].location - 5, 100, 50 + playRoom.creepingGame.ants[i].location - 10, 90)); //绘制蚂蚁的触角，长度为5像素，角度为45度
					g2.draw(new Line2D.Double(50 + playRoom.creepingGame.ants[i].location - 5, 100, 50 + playRoom.creepingGame.ants[i].location - 10, 110)); //绘制蚂蚁的触角，长度为5像素，角度为-45度
				} else { //如果蚂蚁朝右
					g2.draw(new Line2D.Double(50 + playRoom.creepingGame.ants[i].location + 5, 100, 50 + playRoom.creepingGame.ants[i].location + 10, 90)); //绘制蚂蚁的触角，长度为5像素，角度为45度
					g2.draw(new Line2D.Double(50 + playRoom.creepingGame.ants[i].location + 5, 100, 50 + playRoom.creepingGame.ants[i].location + 10, 110)); //绘制蚂蚁的触角，长度为5像素，角度为-45度
				}
			}
		}
		
	}


	
	public static void main(String[] args) { 
		JFrame frame = new JFrame("蚂蚁爬杆游戏"); //创建一个窗口，标题为"蚂蚁爬杆游戏"
		frame.setSize(1000, 400); //设置窗口大小
		frame.setLocationRelativeTo(null); //设置窗口居中
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //设置窗口关闭时退出程序
		UserPanel panel = new UserPanel(); //创建一个UserPanel对象 
		frame.add(panel); //把UserPanel对象添加到窗口中 
		frame.setVisible(true); //设置窗口可见
	}

}