package antclimb;

public class CreepingGame {
	Ant ants[];
	Stick stick;
	
	CreepingGame(int size, int len){
		this.ants = new Ant[size];
		this.stick = new Stick(len);
	}
	
	//判断游戏是否结束。（所有蚂蚁离开木杆）
	boolean isEnd() {
		for (int i = ants.length - 1; i >= 0; i--) {
			if (ants[i].isOut == false)
				return false;
		}
		return true;
	}
	
	//推进游戏
	void drivingGame(int size, int incTime) {
		for(int i = 0; i < size; i++) {
			if(ants[i].isOut)
				continue;
			ants[i].creeping(incTime);
			//判断蚂蚁是否掉下去
			if(ants[i].location >= stick.lenghth || ants[i].location <= 0)
				ants[i].isOut = true;
		}
		
		//位置相同的蚂蚁掉头
		for(int i = 0; i < size; i++) {
			for(int j = i + 1; j < size; j++) {
				if(ants[i].isMeet(ants[j])) {
					ants[i].changeDerection();
					ants[j].changeDerection();
				}
			}
		}
		
		//打印位置
		for(int i = 0; i < size; i++) {
			System.out.print(ants[i].location + " ");
		}
		System.out.println();
	}
	
	//执行一次游戏，返回执行时间。size为蚂蚁数量，incTime为间隔时间
	int playGame(int size, int incTime) {
		int time = 0;
		while(!isEnd()) {
			drivingGame(size, incTime);
			time += incTime;
		}
		return time;
	}
}
