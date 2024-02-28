package antclimb;

import javax.swing.JFrame;

public class PlayRoom {
	int incTime = 1;//推进游戏的时间间隔常量
	int Velocity = 5;//蚂蚁速度
	int size = 5;//蚂蚁数量
	int len = 300;//木杆长度
	int maxTime = -1, minTime = -1;
	CreepingGame creepingGame;
	
	//设定蚂蚁初始方向,num为第几次进行游戏
	void buildDirections(Ant ants[], int num) {
		for (int i = 0; i < size; i++) {
			ants[i].direction = (num >> i) & 1;
		}
	}
	
	//初始化5只蚂蚁
	void initAnts(int num) {
		creepingGame = new CreepingGame(size, len);
		creepingGame.ants[0] = new Ant(1, Velocity, 0, 30, false);
		creepingGame.ants[1] = new Ant(2, Velocity, 0, 80, false);
		creepingGame.ants[2] = new Ant(3, Velocity, 0, 110, false);
		creepingGame.ants[3] = new Ant(4, Velocity, 0, 160, false);
		creepingGame.ants[4] = new Ant(5, Velocity, 0, 250, false);
		buildDirections(creepingGame.ants, num);
	}
	
	//玩遍不同初始设定的游戏
	void playGames() {
		//进行游戏
		int max = -999999;
		int min = 999999;
		for (int i = 0; i < (1 << size); i++) {
			System.out.println("num:" + (i+1));
			initAnts(i);
			int tmp = creepingGame.playGame(size, incTime);
			if(tmp > max) max = tmp;
			if(tmp < min) min = tmp;
		}
		//输出最长，最短时间
		maxTime = max;
		minTime = min;
		System.out.println(max + " " + min);
	}
	

}
