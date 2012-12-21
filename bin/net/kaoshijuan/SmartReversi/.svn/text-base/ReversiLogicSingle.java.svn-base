package net.kaoshijuan.SmartReversi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;

public class ReversiLogicSingle implements ReversiLogic {

	private DisplayAdapterInterface mDisplayInterface;
	private ChessColor [] mChessBoard = new ChessColor[64];
	private ChessColor mColor = ChessColor.BLACK;
	private boolean mCanClick = false;
	private final Zebra mZebra = new Zebra(); 
	private String mHistory = new String();
	private Context mContext;
	@Override
	public boolean Initialize(DisplayAdapterInterface displayInterface,Context context) {
		// TODO Auto-generated method stub
		mDisplayInterface = displayInterface;
		mContext = context;
		mZebra.Init();
		//NewGame();
		return true;
	}

	@Override
	public void NewGame() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 64; ++i)
		{
			mChessBoard[i] = ChessColor.NONE;
		}
		SetBoardColor(3,3,ChessColor.WHITE);
		SetBoardColor(4,3,ChessColor.BLACK);
		SetBoardColor(4,4,ChessColor.WHITE);
		SetBoardColor(3,4,ChessColor.BLACK);

		//for test
		
/*		int i = 0;
		for(i=0;i < 8; ++i)SetBoardColor(i,0,ChessColor.WHITE);

		for(i=0;i < 4; ++i)SetBoardColor(i,1,ChessColor.WHITE);
		SetBoardColor(4,1,ChessColor.BLACK);SetBoardColor(5,1,ChessColor.BLACK);
		SetBoardColor(6,1,ChessColor.WHITE);SetBoardColor(7,1,ChessColor.WHITE);

		for(i=0;i < 6; ++i)SetBoardColor(i,2,ChessColor.WHITE);
		SetBoardColor(7,2,ChessColor.WHITE);
		
		SetBoardColor(0,3,ChessColor.WHITE);SetBoardColor(1,3,ChessColor.WHITE);
		SetBoardColor(2,3,ChessColor.BLACK);SetBoardColor(3,3,ChessColor.WHITE);
		SetBoardColor(4,3,ChessColor.WHITE);SetBoardColor(5,3,ChessColor.BLACK);
		
		SetBoardColor(0,4,ChessColor.WHITE);SetBoardColor(1,4,ChessColor.WHITE);
		SetBoardColor(2,4,ChessColor.BLACK);SetBoardColor(3,4,ChessColor.WHITE);
		SetBoardColor(4,4,ChessColor.BLACK);SetBoardColor(5,4,ChessColor.BLACK);		
		SetBoardColor(7,4,ChessColor.BLACK);
		
		for(i=0;i<5;++i)SetBoardColor(i,5,ChessColor.WHITE);
		SetBoardColor(5,5,ChessColor.BLACK);SetBoardColor(7,5,ChessColor.BLACK);
		
		for(i=0;i<7;++i)SetBoardColor(i,6,ChessColor.WHITE);
		SetBoardColor(7,6,ChessColor.BLACK);
		
		for(i=0;i<7;++i)SetBoardColor(i,7,ChessColor.WHITE);
		SetBoardColor(7,7,ChessColor.BLACK);
*/		
		long lBlackCount = GetChessCount(ChessColor.BLACK);
		long lWhiteCount = GetChessCount(ChessColor.WHITE);
		
		mDisplayInterface.SetScore((int)lBlackCount, (int)lWhiteCount);
		String str = mContext.getString(R.string.your_turn);
		mDisplayInterface.SetBottomText(str);
		mHistory = "";
		//mHistory = "d3c5c6e3c4c2d2c3b2a1f3d6f2b3d7f4e6f5f6b1c1b4a2b5b6d1e1e2f1c7b8a3a4a5a6a7b7e8e7d8c8f8f7a8g8g7h7h8h6h5g1g2h1h2h3";
		FindAvailable(ChessColor.BLACK);
		mCanClick = true;		
	}
	
	@Override
	public boolean OnClick(long x, long y) {
		// TODO Auto-generated method stub
		if(mCanClick == false) return false;
		
		ChessColor computerColor = ChessColor.WHITE;
		if(mColor == ChessColor.WHITE)
		{
			computerColor = ChessColor.BLACK;
		}
		
		if(GetBoardColor(x,y) == ChessColor.NONE)
		{
			if(ChangeBoard(x,y,ChessColor.BLACK) == true)
			{
				//点击完了立刻就不能再点
				mHistory += String.format("%c%d", (char)x+'a',y+1);
				mCanClick = false;
				mDisplayInterface.ClearAvailableTips();
				SetBoardColor(x,y,ChessColor.BLACK);
				//ComputerSide(ChessColor.WHITE);
				final Timer pTimer = new Timer();
				pTimer.schedule(new TimerTask(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						pTimer.cancel();
						//mScene.getTopLayer().removeEntity(play);
						OnChessChangeOver(ChessColor.BLACK);
					}	
				}, 2000);
				long lBlackCount = GetChessCount(ChessColor.BLACK);
				long lWhiteCount = GetChessCount(ChessColor.WHITE);
				
				
				mDisplayInterface.SetScore((int)lBlackCount, (int)lWhiteCount);
				String str = mContext.getString(R.string.computer_turn);
				mDisplayInterface.SetBottomText(str);
			}
			
		}
		return false;
	}
 
	private boolean ChangeBoard(long x, long y, ChessColor color)
	{
		if(x <0  || x>7) return false;
		if(y <0  || y>7) return false;
		if(color != ChessColor.BLACK && color != ChessColor.WHITE) return false;
		
		boolean bChanged = false;
		// check left 
		long tempX = x-1;
		while(tempX >=0)
		{
			ChessColor tempColor = GetBoardColor(tempX,y);
			if(tempColor == ChessColor.NONE)
			{//往一个方向遍历，发现无颜色就退出
				break;
			}
			
			if(tempColor == color)
			{
				//中间一定是反色
				for (long i = tempX +1 ; i < x; ++i)
				{
					SetBoardColor(i,y,color);
					bChanged = true;
				}
				break;
			}	
			tempX = tempX - 1;
		}//while
		
		//check right
		tempX = x+1;
		while(tempX <= 7)
		{
			ChessColor tempColor = GetBoardColor(tempX,y);
			if(tempColor == ChessColor.NONE)
			{//往一个方向遍历，发现无颜色就退出
				break;
			}
			
			if(tempColor == color)
			{
				//中间一定是反色
				for (long i = tempX -1 ; i > x; --i)
				{
					SetBoardColor(i,y,color);
					bChanged = true;
				}
				
				break;
			}	
			tempX = tempX + 1;
		}//while
		
		//check up
		long tempY = y - 1;
		while(tempY >=0)
		{
			ChessColor tempColor = GetBoardColor(x,tempY);
			if(tempColor == ChessColor.NONE)
			{//往一个方向遍历，发现无颜色就退出
				break;
			}
			
			if(tempColor == color)
			{
				//中间一定是反色
				for (long j = tempY +1 ; j < y; ++j)
				{
					SetBoardColor(x,j,color);
					bChanged = true;
				}
				
				break;
			}	
			tempY = tempY - 1;
		}//while		
		
		//check down
		tempY = y + 1;
		while(tempY <=7)
		{
			ChessColor tempColor = GetBoardColor(x,tempY);
			if(tempColor == ChessColor.NONE)
			{//往一个方向遍历，发现无颜色就退出
				break;
			}
			
			if(tempColor == color)
			{
				//中间一定是反色
				for (long j = tempY -1 ; j > y; --j)
				{
					SetBoardColor(x,j,color);
					bChanged = true;
				}
				
				break;
			}	
			tempY = tempY + 1;
		}//while	
		
		//check up and left
		tempX = x - 1;
		tempY = y - 1;
		while(tempX>=0 && tempY >=0)
		{
			ChessColor tempColor = GetBoardColor(tempX,tempY);
			if(tempColor == ChessColor.NONE)
			{//往一个方向遍历，发现无颜色就退出
				break;
			}
			
			if(tempColor == color)
			{
				//中间一定是反色
				for (long i = tempX +1, j = tempY +1 ; i < x && j < y; ++i,++j)
				{
					SetBoardColor(i,j,color);
					bChanged = true;
				}
				
				break;
			}	
			tempX = tempX - 1;
			tempY = tempY - 1;
		}//while		
		
		//chek right and down
		tempX = x + 1;
		tempY = y + 1;
		while(tempX<=7 && tempY <=7)
		{
			ChessColor tempColor = GetBoardColor(tempX,tempY);
			if(tempColor == ChessColor.NONE)
			{//往一个方向遍历，发现无颜色就退出
				break;
			}
			
			if(tempColor == color)
			{
				//中间一定是反色
				for (long i = tempX -1, j = tempY -1 ; i > x && j > y; --i,--j)
				{
					SetBoardColor(i,j,color);
					bChanged = true;
				}
				
				break;
			}	
			tempX = tempX + 1;
			tempY = tempY + 1;
		}//while
		
		
		//check right and up
		tempX = x + 1;
		tempY = y - 1;
		while(tempX<=7 && tempY >=0)
		{
			ChessColor tempColor = GetBoardColor(tempX,tempY);
			if(tempColor == ChessColor.NONE)
			{//往一个方向遍历，发现无颜色就退出
				break;
			}
			
			if(tempColor == color)
			{
				//中间一定是反色
				for (long i = tempX -1, j = tempY +1 ; i > x && j < y; --i,++j)
				{
					SetBoardColor(i,j,color);
					bChanged = true;
				}
				
				break;
			}	
			tempX = tempX + 1;
			tempY = tempY - 1;
		}//while
		
		
		//check left and down
		tempX = x - 1;
		tempY = y + 1;
		while(tempX>=0 && tempY <=7)
		{
			ChessColor tempColor = GetBoardColor(tempX,tempY);
			if(tempColor == ChessColor.NONE)
			{//往一个方向遍历，发现无颜色就退出
				break;
			}
			
			if(tempColor == color)
			{
				//中间一定是反色
				for (long i = tempX +1, j = tempY -1 ; i < x && j > y; ++i,--j)
				{
					SetBoardColor(i,j,color);
					bChanged = true;
				}
				
				break;
			}	
			tempX = tempX - 1;
			tempY = tempY + 1;
		}//while
		return bChanged;
	}
	
	private long FindAvailableCountOnXY(long x, long y,ChessColor color)
	{
		long lCount = 0;
		
		if(x <0  || x>7) return 0;
		if(y <0  || y>7) return 0;
		if(color != ChessColor.BLACK && color != ChessColor.WHITE) return 0;
		
		// check left 
		long tempX = x-1;
		while(tempX >=0)
		{
			ChessColor tempColor = GetBoardColor(tempX,y);
			if(tempColor == ChessColor.NONE)
			{//往一个方向遍历，发现无颜色就退出
				break;
			}
			
			if(tempColor == color)
			{
				//中间一定是反色
				for (long i = tempX +1 ; i < x; ++i)
				{
					lCount++;
				}
				break;
			}	
			tempX = tempX - 1;
		}//while
		
		//check right
		tempX = x+1;
		while(tempX <= 7)
		{
			ChessColor tempColor = GetBoardColor(tempX,y);
			if(tempColor == ChessColor.NONE)
			{//往一个方向遍历，发现无颜色就退出
				break;
			}
			
			if(tempColor == color)
			{
				//中间一定是反色
				for (long i = tempX -1 ; i > x; --i)
				{
					lCount++;
				}
				
				break;
			}	
			tempX = tempX + 1;
		}//while
		
		//check up
		long tempY = y - 1;
		while(tempY >=0)
		{
			ChessColor tempColor = GetBoardColor(x,tempY);
			if(tempColor == ChessColor.NONE)
			{//往一个方向遍历，发现无颜色就退出
				break;
			}
			
			if(tempColor == color)
			{
				//中间一定是反色
				for (long j = tempY +1 ; j < y; ++j)
				{
					lCount++;
				}
				
				break;
			}	
			tempY = tempY - 1;
		}//while		
		
		//check down
		tempY = y + 1;
		while(tempY <=7)
		{
			ChessColor tempColor = GetBoardColor(x,tempY);
			if(tempColor == ChessColor.NONE)
			{//往一个方向遍历，发现无颜色就退出
				break;
			}
			
			if(tempColor == color)
			{
				//中间一定是反色
				for (long j = tempY -1 ; j > y; --j)
				{
					lCount++;
				}
				
				break;
			}	
			tempY = tempY + 1;
		}//while	
		
		//check up and left
		tempX = x - 1;
		tempY = y - 1;
		while(tempX>=0 && tempY >=0)
		{
			ChessColor tempColor = GetBoardColor(tempX,tempY);
			if(tempColor == ChessColor.NONE)
			{//往一个方向遍历，发现无颜色就退出
				break;
			}
			
			if(tempColor == color)
			{
				//中间一定是反色
				for (long i = tempX +1, j = tempY +1 ; i < x && j < y; ++i,++j)
				{
					lCount++;
				}
				
				break;
			}	
			tempX = tempX - 1;
			tempY = tempY - 1;
		}//while		
		
		//chek right and down
		tempX = x + 1;
		tempY = y + 1;
		while(tempX<=7 && tempY <=7)
		{
			ChessColor tempColor = GetBoardColor(tempX,tempY);
			if(tempColor == ChessColor.NONE)
			{//往一个方向遍历，发现无颜色就退出
				break;
			}
			
			if(tempColor == color)
			{
				//中间一定是反色
				for (long i = tempX -1, j = tempY -1 ; i > x && j > y; --i,--j)
				{
					lCount++;
				}
				
				break;
			}	
			tempX = tempX + 1;
			tempY = tempY + 1;
		}//while
		
		
		//check right and up
		tempX = x + 1;
		tempY = y - 1;
		while(tempX<=7 && tempY >=0)
		{
			ChessColor tempColor = GetBoardColor(tempX,tempY);
			if(tempColor == ChessColor.NONE)
			{//往一个方向遍历，发现无颜色就退出
				break;
			}
			
			if(tempColor == color)
			{
				//中间一定是反色
				for (long i = tempX -1, j = tempY +1 ; i > x && j < y; --i,++j)
				{
					lCount++;
				}
				
				break;
			}	
			tempX = tempX + 1;
			tempY = tempY - 1;
		}//while
		
		
		//check left and down
		tempX = x - 1;
		tempY = y + 1;
		while(tempX>=0 && tempY <=7)
		{
			ChessColor tempColor = GetBoardColor(tempX,tempY);
			if(tempColor == ChessColor.NONE)
			{//往一个方向遍历，发现无颜色就退出
				break;
			}
			
			if(tempColor == color)
			{
				//中间一定是反色
				for (long i = tempX +1, j = tempY -1 ; i < x && j > y; ++i,--j)
				{
					lCount++;
				}
				
				break;
			}	
			tempX = tempX - 1;
			tempY = tempY + 1;
		}//while		
		return lCount;
	}
	
	private boolean FindAvailable(ChessColor color)
	{
		boolean bDraw = true;
		return FindAvailable(color,bDraw);
	}
	
	private boolean FindAvailable(ChessColor color,boolean bDraw)
	{
		boolean bResult = false;
		for(int i = 0; i < 8 ;++i)
		{
			for(int j = 0; j < 8; ++j)
			{
				if(mChessBoard[j*8+i] != ChessColor.NONE)
				{
					continue;
				}
				
				long lCount = FindAvailableCountOnXY(i,j,color);
				if(lCount>0)
				{
					bResult = true;
					if(bDraw == true)
					{
						mDisplayInterface.OnBlankAvailable(i,j);
					}
				}
			}
			
		}
		
		return bResult;
	}
	
	private void ComputerSide(ChessColor color)
	{
		if(FindAvailable(ChessColor.WHITE,false) == false)
		{
			return;
		}
		
		int result = mZebra.OnPlayGame(mHistory);
		
		int tx = result % 10 - 1;
		int ty = result / 10 - 1;
	
	/*	int tx=0,ty=0;
		if(mHistory.equals("d3c5c6e3c4c2d2c3b2a1f3d6f2b3d7f4e6f5f6b1c1b4a2b5b6d1e1e2f1c7b8a3a4a5a6a7b7e8e7d8c8f8f7a8g8g7h7h8h6h5g1g2h1h2h3g4"))
		{
			tx=6;ty=2;
		}else if(mHistory.equals("d3c5c6e3c4c2d2c3b2a1f3d6f2b3d7f4e6f5f6b1c1b4a2b5b6d1e1e2f1c7b8a3a4a5a6a7b7e8e7d8c8f8f7a8g8g7h7h8h6h5g1g2h1h2h3g4g3"))
		{
			tx=7;ty=3;
		}else if(mHistory.equals("d3c5c6e3c4c2d2c3b2a1f3d6f2b3d7f4e6f5f6b1c1b4a2b5b6d1e1e2f1c7b8a3a4a5a6a7b7e8e7d8c8f8f7a8g8g7h7h8h6h5g1g2h1h2h3g4g3h4"))
		{
			tx=6;ty=4;
		}
		*/
		boolean ret = ChangeBoard(tx,ty,ChessColor.WHITE);
		
		if(ret == false)return;
		
		SetBoardColor(tx,ty,ChessColor.WHITE);
		
		mHistory += String.format("%c%d", (char)tx+'a',ty+1);
		
		//下完棋了，需要设置一个定时器来触发下棋完毕的异步事件
		final Timer pTimer = new Timer();
		pTimer.schedule(new TimerTask(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				pTimer.cancel();
				OnChessChangeOver(ChessColor.WHITE);
			}
		}, 500);		
	}
	
	private void CheckResult()
	{
		boolean bNoMove = false;
		CheckResult(bNoMove);
	}
	
	private void CheckResult(boolean bNoMove)
	{
		/*三种情况游戏结束
		 * 1. 没有空白了
		 * 2. 黑方没有子了
		 * 3. 白方没有子了
		*/
		
		
		long lBlackCount = GetChessCount(ChessColor.BLACK);
		long lWhiteCount = GetChessCount(ChessColor.WHITE);
		

		
		if(lWhiteCount == 0 || lBlackCount == 0 || (lWhiteCount+lBlackCount) == 64 || bNoMove == true)
		{
			// Game Over
			if(lWhiteCount > lBlackCount)
			{
				// White side win
				mDisplayInterface.SetScore((int)lBlackCount, (int)lWhiteCount);
				String str = mContext.getString(R.string.computer_win);
				mDisplayInterface.SetBottomText(str);
				
			}else  if(lBlackCount > lWhiteCount)
			{
				//Black side win
				mDisplayInterface.SetScore((int)lBlackCount, (int)lWhiteCount);
				String str = mContext.getString(R.string.you_win);
				mDisplayInterface.SetBottomText(str);
				
			}else if(lBlackCount == lWhiteCount)
			{
				//draw ...
				mDisplayInterface.SetScore((int)lBlackCount, (int)lWhiteCount);
				String str = mContext.getString(R.string.you_win);
				mDisplayInterface.SetBottomText(str);				
			}
		}
		
	}
	
	private ChessColor GetBoardColor(long x, long y)
	{
		if(x <0  || x>7) return ChessColor.NONE;
		if(y <0  || y>7) return ChessColor.NONE;
		
		return mChessBoard[(int) (y*8+x)];
	}
	
	private void SetBoardColor(long x, long y, ChessColor color)
	{
		if(x <0  || x>7) return;
		if(y <0  || y>7) return;
		
		boolean bChange = true;
		if(mChessBoard[(int)(y*8+x)] == ChessColor.NONE)
		{
			bChange =  false;
		}
		mChessBoard[(int) (y*8+x)] = color;
		if(bChange == true)
		{
			mDisplayInterface.ChangePiece(color, x, y);
		}else{
			mDisplayInterface.AddPiece(color, x, y);
		}
	}

	private long GetChessCount(ChessColor color)
	{
		long lCount = 0;
		for(int i = 0; i < 64; ++i)
		{
			if(mChessBoard[i] == color)
			{
				lCount++;
			}
		}
		
		return lCount;
	}
	
	@Override
	public boolean OnChessChangeOver(ChessColor color) {
		// TODO Auto-generated method stub
		/*
		 * 翻转棋子完成，先让电脑下，再判断胜负
		 */
		if(color == ChessColor.BLACK)
		{	
			ComputerSide(ChessColor.WHITE);
			long lBlackCount = GetChessCount(ChessColor.BLACK);
			long lWhiteCount = GetChessCount(ChessColor.WHITE);
			
			String str = mContext.getString(R.string.your_turn);
			mDisplayInterface.SetBottomText(str);
			mDisplayInterface.SetScore((int)lBlackCount, (int)lWhiteCount);
			
		}else if(color == ChessColor.WHITE)
		{//刚刚是电脑下完，要看看人是否有地方可下
			boolean bResult = FindAvailable(ChessColor.BLACK);
			if(bResult == false)
			{//没有地方下，设个定时器让电脑再下一次
				if(FindAvailable(ChessColor.WHITE,false) == false)
				{//白棋也没辙，那么就检查比赛结果
					CheckResult(true);
				}else{
					final Timer pTimer = new Timer();
					pTimer.schedule(new TimerTask(){
						@Override
						public void run() {
							// TODO Auto-generated method stub
							pTimer.cancel();
							OnChessChangeOver(ChessColor.BLACK); //假装黑棋下完了，让白棋下						
						}	
					}, 2000);
					long lBlackCount = GetChessCount(ChessColor.BLACK);
					long lWhiteCount = GetChessCount(ChessColor.WHITE);
					mDisplayInterface.SetScore((int)lBlackCount, (int)lWhiteCount);
					String str=mContext.getString(R.string.you_passed);
					mDisplayInterface.SetBottomText(str);					
				}

			}else{
				mCanClick = true;
			}
		}
		
		File fFile = new File("/sdcard/SmartReversi.log");
		FileOutputStream sFileStream = null;
		try {
			sFileStream = new FileOutputStream(fFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			sFileStream.write(mHistory.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			sFileStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		CheckResult();
		return false;
	}

}
