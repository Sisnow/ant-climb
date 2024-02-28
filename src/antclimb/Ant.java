package antclimb;

public class Ant {
	int id;
	int velocity;
	int direction;//0代表向左，1代表向右
	int location;
	boolean isOut = false;
	Ant(int id, int v, int d, int l, boolean i){
		this.id = id;
		this.velocity = v;
		this.direction = d;
		this.location = l;
		this.isOut = i;
	}
	
	//判断蚂蚁是否在相同位置
	public boolean isMeet(Ant a) {
		if (this.location == a.location)
			return true;
		return false;
	}
	
	//改变蚂蚁方向
	public void changeDerection() {
		if (this.direction == 0)
			this.direction = 1;
		else
			this.direction = 0;
	}
	
	//爬行
	public void creeping(int incTime) {
		if(this.direction == 0)//方向朝左
			this.location = this.location - this.velocity * incTime;
		else//朝右
			this.location = this.location + this.velocity * incTime;
	}
}
