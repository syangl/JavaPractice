package Chess;

import java.util.LinkedList;

class Message{
	public int row;
	public int col;
	public int color;
	public Message(int row, int col, int color) {
		this.row = row;
		this.col = col;
		this.color = color;
	}
}

public class ChessModel  {
	public static final int BLACK = 1;
	public static final int WHITE = -1;
	public static final int SPACE = 0;
	public static final int WIDTH = 19;
		
	public LinkedList<Message> datalist = new LinkedList<>();
	public LinkedList<Message> helplist = new LinkedList<>();
	public LinkedList<Message> reshowlist = new LinkedList<>();
	private int[][] data = new int[WIDTH][WIDTH];
	private int lastRow,lastCol,lastColor;
	private int lastRow2,lastCol2,lastColor2;
	
	public int[][] getData() {
		return data;
	}
	
	public int getBlack(){
		return BLACK;
	}
	
	public int getWhite(){
		return WHITE;
	}
	
	//单例模式
	private ChessModel() {};
	private static ChessModel instance;
	public static ChessModel getInstance(){
		if(instance == null) {
			instance = new ChessModel();
		}
		return instance;
	}
	
	//操作代码
	//下棋putChess
	public boolean putChess(int row, int col, int color) {
		// TODO Auto-generated method stub
		if(row>=0&&row<WIDTH&&col>=0&&col<WIDTH&&(color==BLACK||color==WHITE)) {
			if(data[row][col]==SPACE) {
				data[row][col] = color;
				lastRow2 = lastRow;
				lastCol2 = lastCol;
				lastColor2 = lastColor;
				lastRow = row;
				lastCol = col;
				lastColor = color;
				return true;
			}
		}
		return false;
	}
	
	public int whoWin(int row, int col, int color) {
		if (color==BLACK) {
			//first
			int leftb = 1, rightb = 1, flagleftb = 0, flagrightb = 0;
			for (int i = 0; i < 4; i++){
				if((col - leftb)>=0) {
					if (data[row ][col- leftb] == BLACK) {
						flagleftb++;
						leftb++;
					}else if(data[row][col - leftb]==WHITE ||data[row ][col- leftb]==SPACE){
						break;
					}
				}else {
					break;
				}
			}
			for (int i = 0; i < 4; i++){
				if((col + rightb)<WIDTH) {
					if (data[row ][col+ rightb] == BLACK) {
						flagrightb++;
						rightb++;
					}else if(data[row][col+ rightb]==WHITE ||data[row ][col+ rightb]==SPACE){
						break;
					}
				}else {
					break;
				}
			}
			if (flagleftb + flagrightb >= 4) {
				return BLACK;
			}
			
			//second
			int upb = 1, downb = 1, flagupb = 0, flagdownb = 0;
			for (int i = 0; i < 4; i++){
				if((row - upb)>=0) {
					if (data[row - upb][col] == BLACK) {
						flagupb++;
						upb++;
					}else if(data[row - upb][col]==WHITE ||data[row - upb][col]==SPACE){
						break;
					}
				}else {
					break;
				}
			}
			for (int i = 0; i < 4; i++){
				if((row + downb)<WIDTH) {
					if (data[row + downb][col] == BLACK) {
						flagdownb++;
						downb++;
					}else if(data[row + downb][col]==WHITE ||data[row + downb][col]==SPACE){
						break;
					}
				}else {
					break;
				}
			}
			if (flagupb + flagdownb >= 4) {
				return BLACK;
			}
			
			//third
			int maindiaupb = 1, maindiadownb = 1, flagupMdiab = 0, flagdownMdiab = 0;
			for (int i = 0; i < 4; i++){
				if((row - maindiaupb)>=0&&(col-maindiaupb)>=0) {
					if (data[row - maindiaupb][col-maindiaupb] == BLACK) {
						flagupMdiab++;
						maindiaupb++;
					}else if(data[row - maindiaupb][col-maindiaupb]==WHITE ||data[row - maindiaupb][col-maindiaupb]==SPACE){
						break;
					}
				}else {
					break;
				}
			}
			for (int i = 0; i < 4; i++){
				if((row + maindiadownb)<WIDTH&&(col+maindiadownb)<WIDTH) {
					if (data[row + maindiadownb][col+maindiadownb] == BLACK) {
						flagdownMdiab++;
						maindiadownb++;
					}else if(data[row + maindiadownb][col+maindiadownb]==WHITE ||data[row + maindiadownb][col+maindiadownb]==SPACE){
						break;
					}
				}else {
					break;
				}
			}
			if (flagupMdiab + flagdownMdiab >= 4) {
				return BLACK;
			}
			
			//forth
			int subdiaupb = 1, subdiadownb = 1, flagupSdiab = 0, flagdownSdiab = 0;
			for (int i = 0; i < 4; i++){
				if((row - subdiaupb)>=0&&(col+subdiaupb)<WIDTH) {
					if (data[row - subdiaupb][col+subdiaupb] == BLACK) {
						flagupSdiab++;
						subdiaupb++;
					}else if(data[row - subdiaupb][col+subdiaupb]==WHITE ||data[row - subdiaupb][col+subdiaupb]==SPACE){
						break;
					}
				}else {
					break;
				}
			}
			for (int i = 0; i < 4; i++){
				if((row + subdiadownb)<WIDTH&&(col-subdiadownb)>=0) {
					if (data[row + subdiadownb][col-subdiadownb] == BLACK) {
						flagdownSdiab++;
						subdiadownb++;
					}else if(data[row + subdiadownb][col-subdiadownb]==WHITE ||data[row + subdiadownb][col-subdiadownb]==SPACE){
						break;
					}
				}else {
					break;
				}
			}
			if (flagupSdiab + flagdownSdiab >= 4) {
				return BLACK;
			}
		}
			
			
			else if (color==WHITE) {
				//first
				int leftw = 1, rightw = 1, flagleftw = 0, flagrightw = 0;
				for (int i = 0; i < 4; i++){
					if((col - leftw)>=0) {
						if (data[row ][col- leftw] == WHITE) {
							flagleftw++;
							leftw++;
						}else if(data[row][col - leftw]==BLACK ||data[row ][col- leftw]==SPACE){
							break;
						}
					}else {
						break;
					}
				}
				for (int i = 0; i < 4; i++){
					if((col + rightw)<WIDTH) {
						if (data[row ][col+ rightw] == WHITE) {
							flagrightw++;
							rightw++;
						}else if(data[row][col+ rightw]==BLACK ||data[row ][col+ rightw]==SPACE){
							break;
						}
					}else {
						break;
					}
				}
				if (flagleftw + flagrightw >= 4) {
					return WHITE;
				}
				
				//second
				int upw = 1, downw = 1, flagupw = 0, flagdownw = 0;
				for (int i = 0; i < 4; i++){
					if((row - upw)>=0) {
						if (data[row - upw][col] == WHITE) {
							flagupw++;
							upw++;
						}else if(data[row - upw][col]==BLACK ||data[row - upw][col]==SPACE){
							break;
						}
					}else {
						break;
					}
				}
				for (int i = 0; i < 4; i++){
					if((row + downw)<WIDTH) {
						if (data[row + downw][col] == WHITE) {
							flagdownw++;
							downw++;
						}else if(data[row + downw][col]==BLACK ||data[row + downw][col]==SPACE){
							break;
						}
					}else {
						break;
					}
				}
				if (flagupw + flagdownw >= 4) {
					return WHITE;
				}
				
				//third
				int maindiaupw = 1, maindiadownw = 1, flagupMdiaw = 0, flagdownMdiaw = 0;
				for (int i = 0; i < 4; i++){
					if((row - maindiaupw)>=0&&(col-maindiaupw)>=0) {
						if (data[row - maindiaupw][col-maindiaupw] == WHITE) {
							flagupMdiaw++;
							maindiaupw++;
						}else if(data[row - maindiaupw][col-maindiaupw]==BLACK ||data[row - maindiaupw][col-maindiaupw]==SPACE){
							break;
						}
					}else {
						break;
					}
				}
				for (int i = 0; i < 4; i++){
					if((row + maindiadownw)<WIDTH&&(col+maindiadownw)<WIDTH) {
						if (data[row + maindiadownw][col+maindiadownw] == WHITE) {
							flagdownMdiaw++;
							maindiadownw++;
						}else if(data[row + maindiadownw][col+maindiadownw]==BLACK ||data[row + maindiadownw][col+maindiadownw]==SPACE){
							break;
						}
					}else {
						break;
					}
				}
				if (flagupMdiaw + flagdownMdiaw >= 4) {
					return WHITE;
				}
				
				//forth
				int subdiaupw = 1, subdiadownw = 1, flagupSdiaw = 0, flagdownSdiaw = 0;
				for (int i = 0; i < 4; i++){
					if((row - subdiaupw)>=0&&(col+subdiaupw)<WIDTH) {
						if (data[row - subdiaupw][col+subdiaupw] == WHITE) {
							flagupSdiaw++;
							subdiaupw++;
						}else if(data[row - subdiaupw][col+subdiaupw]==BLACK ||data[row - subdiaupw][col+subdiaupw]==SPACE){
							break;
						}
					}else {
						break;
					}
				}
				for (int i = 0; i < 4; i++){
					if((row + subdiadownw)<WIDTH&&(col-subdiadownw)>=0) {
						if (data[row + subdiadownw][col-subdiadownw] == WHITE) {
							flagdownSdiaw++;
							subdiadownw++;
						}else if(data[row + subdiadownw][col-subdiadownw]==BLACK ||data[row + subdiadownw][col-subdiadownw]==SPACE){
							break;
						}
					}else {
						break;
					}
				}
				if (flagupSdiaw + flagdownSdiaw >= 4) {
					return WHITE;
				}
		}
		
		return SPACE;
	}

	public boolean Withdraw() {
		// 悔棋，队列和数组清除两步
		Message tmp1 = new Message(0, 0, 0); 
		tmp1 = ChessModel.getInstance().datalist.pop();
		Message tmp2 = new Message(0, 0, 0);
		tmp2 = ChessModel.getInstance().datalist.pop();
		ChessModel.getInstance().getData()[tmp1.row][tmp1.col]=ChessModel.getInstance().SPACE;
		ChessModel.getInstance().getData()[tmp2.row][tmp2.col]=ChessModel.getInstance().SPACE;
		ChessView.getInstance().repaint();
		
		return false;
	}
	
	public void reset(){
		while(!datalist.isEmpty()) {
			datalist.pop();
		}
		while(!helplist.isEmpty()) {
			helplist.pop();
		}
		while(!helplist.isEmpty()) {
			reshowlist.pop();
		}
		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < WIDTH; j++) {
				data[i][j]=SPACE;
			}
		}
		lastRow=0;lastCol=0;lastColor=0;
		lastRow2=0;lastCol2=0;lastColor2=0;
		ChessView.getInstance().repaint();
		EastPanel.getInstance().record.setText("");
		NorthPanel.getInstance().linkBar.setText("联网模式请按'连接'开始游戏,或开始本地游戏");
	}
	
	//复盘，结束后弹出LocalEndDialog
	public void reshow() {
		if(reshowlist.isEmpty()) {
			if(StartPanel.getInstance().whetherlocal==true) {
				LocalEndDialog.getInstance().show();
			}else {
				WebEndDialog.getInstance().show();
			}
		}else {
			Message tmp = new Message(0, 0, 0);
			tmp = reshowlist.poll();
			datalist.push(tmp);
			ChessView.getInstance().repaint();
		}
	}
	
}
