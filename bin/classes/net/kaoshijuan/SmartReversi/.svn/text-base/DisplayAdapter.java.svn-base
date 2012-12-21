package net.kaoshijuan.SmartReversi;


import java.util.ArrayList;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.layer.ILayer;
import org.anddev.andengine.entity.primitive.Line;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.shape.modifier.MoveYModifier;
import org.anddev.andengine.entity.shape.modifier.SequenceShapeModifier;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.graphics.Typeface;


enum  ChessColor{
	NONE,
	BLACK,
	WHITE
};

public class DisplayAdapter implements DisplayAdapterInterface{
	private ReversiLogic mReversiLogicInterface;
	
	private SmartReversi mSmartReversi;
	private Scene mScene;
	private Texture mTexture;
	private Texture mChessboardTexture;
	private Texture mWhiteTexture;
	private Texture mBlackTexture;	
	private Texture mBlackToWhiteTexture;
	private Texture mWhiteToBlackTexture;
	private Texture mBottomTextTexture;
	private Texture mScoreTexture;
	private Texture mSplashTexture;
	private TextureRegion mChessboardTextureRegion;
	private TextureRegion mBlackTextureRegion;
	private TextureRegion mWhiteTextureRegion;
	private TextureRegion mAvailableTextureRegion;
	private TextureRegion mSplashTextureRegion;
	private TextureRegion mRightnowTextureRegion;
	
	private TiledTextureRegion mBlackToWhiteTextureRegion;
	private TiledTextureRegion mWhiteToBlackTextureRegion;
	
	private Font mBottomFont;
	private ChangeableText mBottomText;
	private Font mScoreFont;
	private ChangeableText mScoreText;
	private Engine mEngine;
	
	private IEntity [] mSpriteVector = new IEntity[64];
	private ArrayList<Sprite> mAvailableSpriteArray = new ArrayList<Sprite>();
	
	private Sprite mRightNowSprite = null;
	
	private final float fStartX = 0.0f;
	private final float fEndX = fStartX + 310.0f;
	private final float fStartY = 35.0f;
	private final float fEndY = fStartY + 310.0f;
	private final float fLongPerPiece = 38.75f;
	private final float fDisOfPiece = 3.375f;
	
	private static final int CAMERA_WIDTH = 320;
	private static final int CAMERA_HEIGHT = 480;
	private static final int DEMO_VELOCITY = 100;
	
	private static class Ball extends Sprite {
		
		public Ball(final float pX, final float pY, final TextureRegion pTextureRegion) {
			super(pX, pY, pTextureRegion);
		}
		@Override
		protected void onManagedUpdate(final float pSecondsElapsed) {
			if(this.mX < 0) {
				this.setVelocityX(DEMO_VELOCITY);
			} else if(this.mX - this.getWidth() > CAMERA_WIDTH) {
				this.mX = - this.getWidth();
			}

			super.onManagedUpdate(pSecondsElapsed);
		}
	}
	
	public DisplayAdapter()
	{
		
	}
	
	
	public Engine CreateEngine()
	{
		Camera mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		mEngine = new Engine(new EngineOptions(true, ScreenOrientation.PORTRAIT, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera));
		return mEngine;
	}
	
	public Scene CreateScene()
	{
		this.mEngine.registerUpdateHandler(new FPSLogger());
		final Scene scene = new Scene(1);
		//scene.setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f));
		scene.setBackground(new ColorBackground(0.0f,0.0f,0.0f));
		mBottomText = new ChangeableText(fStartX+2.0f, fEndY+15.0f, this.mBottomFont, 
				"",
				256);
		
		mScoreText = new ChangeableText(fStartX+2.0f,fStartY-20.0f,this.mScoreFont,"",256);
		
		mScene = scene;
		scene.setOnSceneTouchListener(mSmartReversi);

		final Sprite splash = new Sprite(0,0,mSplashTextureRegion);
		final Ball loading_ball = new Ball(fEndX/2,fEndY*0.75f,mBlackTextureRegion);
		loading_ball.setVelocity(DEMO_VELOCITY ,0);
		mScene.getTopLayer().addEntity(splash);
		mScene.getTopLayer().addEntity(loading_ball);		
		return scene;
	}
	
	public void CreateTextureRegion()
	{
		mTexture = new Texture(512,512,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
/*		mChessboardTexture = new Texture(512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mWhiteTexture = new Texture(32, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mBlackTexture = new Texture(32, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mBlackToWhiteTexture = new Texture(128,32,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mWhiteToBlackTexture = new Texture(128,32,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
*/		
		mSplashTexture = new Texture(512,512,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mSplashTextureRegion = TextureRegionFactory.createFromAsset(this.mSplashTexture, mSmartReversi, "splash.png", 0, 0);
		mChessboardTexture = mTexture;
		mWhiteTexture = mTexture;
		mBlackTexture = mTexture;
		mBlackToWhiteTexture = mTexture;
		mWhiteToBlackTexture = mTexture;
		this.mBlackTextureRegion = TextureRegionFactory.createFromAsset(this.mBlackTexture, mSmartReversi, "black.png", 0, 0);
		this.mWhiteTextureRegion = TextureRegionFactory.createFromAsset(this.mWhiteTexture, mSmartReversi, "white.png",0,32);
		this.mBlackToWhiteTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mBlackToWhiteTexture,mSmartReversi,"blacktowhite.png", 0,64,4, 1);
		this.mWhiteToBlackTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mWhiteToBlackTexture,mSmartReversi,"whitetoblack.png", 0,96,4, 1);
		this.mAvailableTextureRegion = TextureRegionFactory.createFromAsset(this.mWhiteTexture, mSmartReversi, "available.png",0,128);
		this.mChessboardTextureRegion = TextureRegionFactory.createFromAsset(this.mChessboardTexture, mSmartReversi, "chess_board.png", 0, 160);		
		this.mRightnowTextureRegion = TextureRegionFactory.createFromAsset(this.mTexture, mSmartReversi, "rightnow.png",0,470);
		
		//this.mEngine.getTextureManager().loadTexture(this.mChessboardTexture);
		//this.mEngine.getTextureManager().loadTexture(this.mBlackTexture);
		//this.mEngine.getTextureManager().loadTexture(this.mWhiteTexture);
		//this.mEngine.getTextureManager().loadTexture(this.mBlackToWhiteTexture);
		//this.mEngine.getTextureManager().loadTexture(this.mWhiteToBlackTexture);
		this.mEngine.getTextureManager().loadTexture(this.mTexture);
		this.mEngine.getTextureManager().loadTexture(this.mSplashTexture);
		
		mBottomTextTexture = new Texture(256,256,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mBottomFont = new Font(mBottomTextTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 12, true, android.graphics.Color.WHITE);
		mScoreTexture = new Texture(256,256,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mScoreFont = new Font(mScoreTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 12, true, android.graphics.Color.WHITE);
		this.mEngine.getTextureManager().loadTexture(this.mBottomTextTexture);
		this.mEngine.getFontManager().loadFont(this.mBottomFont);
		this.mEngine.getTextureManager().loadTexture(this.mScoreTexture);
		this.mEngine.getFontManager().loadFont(this.mScoreFont);
	}
	
	public void SetContext(SmartReversi smartReservi)
	{
		mSmartReversi = smartReservi;
	}
	
	public void NewGame()
	{// redraw all the screen
		
		final ILayer topLayer = mScene.getTopLayer();
		topLayer.clear();
		topLayer.addEntity(new Sprite(fStartX , fStartY, this.mChessboardTextureRegion));

		//lines for check position
		/*for (int i = 0 ; i < 8 ; ++i)
		{
			final Line line = new Line(fStartX+i*fLongPerPiece,fStartY,fStartX+i*fLongPerPiece,fEndY);
			mScene.getTopLayer().addEntity(line);
		}
		for (int j = 0 ; j < 8 ; ++j)
		{
			final Line line = new Line(fStartX,fStartY+j*fLongPerPiece,fEndX,fStartY+j*fLongPerPiece);
			mScene.getTopLayer().addEntity(line);			
		}
		*/
		topLayer.addEntity(mBottomText);
		topLayer.addEntity(mScoreText);
		SetScore(0,0);
		String str = mSmartReversi.getString(R.string.your_turn);
		SetBottomText(str);
		mReversiLogicInterface.NewGame();
	}
	
	@Override
	public boolean Initialize()
	{
		
		mReversiLogicInterface = new ReversiLogicSingle();
		mReversiLogicInterface.Initialize(this,mSmartReversi);
		NewGame();
		return true;
	}
	
	@Override
	public boolean OnClickPosition(float x, float y)
	{
		if(x<fStartX || x>fEndX) return false;
		if(y<fStartY || y>fEndY) return false;
		
		float dis = (x - fStartX)/fLongPerPiece;
		long xIndex = (long) Math.floor((x + dis -fStartX)/fLongPerPiece);
		long yIndex = (long) Math.floor((y-fStartY)/fLongPerPiece);
		
		if(xIndex<0 || xIndex >7) return false;
		if(yIndex<0 || yIndex >7) return false;
			
	
		return OnClick(xIndex,yIndex);
	}
	
	private boolean OnClick(long xIndex, long yIndex)
	{

		this.mReversiLogicInterface.OnClick(xIndex, yIndex);
		return true;
	}
	
	@Override
	public boolean AddPiece(ChessColor color, long xIndex, long yIndex)
	{
		boolean bAddRightNowTip = true;
		return AddPiece(color,xIndex,yIndex,bAddRightNowTip);
	}
	
	private boolean AddPiece(ChessColor color, long xIndex, long yIndex,boolean bAddRightNowTip)
	{
		float x = xIndex * fLongPerPiece + fStartX + fDisOfPiece;
		float y = yIndex * fLongPerPiece + fStartY + fDisOfPiece;

		long iArrayIndex = yIndex*8+xIndex;
		
		if(iArrayIndex <0 || iArrayIndex > 63) return false;
			
		TextureRegion pTextureRegion = (color == ChessColor.BLACK )? mBlackTextureRegion : mWhiteTextureRegion;
		Sprite pSprite = new Sprite(x,y,pTextureRegion);
		
		IEntity pOld = mSpriteVector[(int) iArrayIndex];
		mSpriteVector[(int) iArrayIndex] = pSprite;
		
		if(pOld != null)
			mScene.getTopLayer().removeEntity(pOld);
		
		mScene.getTopLayer().addEntity(pSprite);
		
		if(bAddRightNowTip == true)
		{
			if(mRightNowSprite != null)
			{
				mScene.getTopLayer().removeEntity(mRightNowSprite);
			}
			mRightNowSprite = new Sprite(x,y,mRightnowTextureRegion);
			mScene.getTopLayer().addEntity(mRightNowSprite);
		}
		return true;
	}

	@Override
	public boolean OnGameEnd(int iResult) {
		// TODO Auto-generated method stub
		
		/* iResult: 0, black win 1, white win 2, draw
		 * 
		 */
		
		
		
		return false;
	}

	@Override
	public boolean ChangePiece(final ChessColor color, final long xIndex, final long yIndex) {
		// TODO Auto-generated method stub
		
		final float x = xIndex * fLongPerPiece + fStartX + fDisOfPiece;
		final float y = yIndex * fLongPerPiece + fStartY + fDisOfPiece;

		final long iArrayIndex = yIndex*8+xIndex;
		
		if(iArrayIndex <0 || iArrayIndex > 63) return false;
		
		TiledTextureRegion pTextureRegion = (color == ChessColor.BLACK )? mWhiteToBlackTextureRegion : mBlackToWhiteTextureRegion;
		
		final AnimatedSprite play = new AnimatedSprite(x,y,pTextureRegion.clone());
		play.animate(100,false,new IAnimationListener()
		{
			@Override
			public void onAnimationEnd(final AnimatedSprite pAnimatedSprite)
			{
				mSmartReversi.runOnUpdateThread(new Runnable() {
					@Override
					public void run() {
						play.setVisible(false);
						AddPiece(color,xIndex,yIndex,false);
					}
				});
			}
		} );

		IEntity pOld = mSpriteVector[(int) iArrayIndex];
		mSpriteVector[(int) iArrayIndex] = play;
		
		if(pOld != null)
			mScene.getTopLayer().removeEntity(pOld);
		
		mScene.getTopLayer().addEntity(play);
		
		return true;
	}

	@Override
	public void SetBottomText(String sText) {
		// TODO Auto-generated method stub
		mBottomText.setText(sText);
	}

	@Override
	public void OnBlankAvailable(long xIndex, long yIndex) {
		// TODO Auto-generated method stub
		final float x = xIndex * fLongPerPiece + fStartX + fDisOfPiece;
		final float y = yIndex * fLongPerPiece + fStartY + fDisOfPiece;

		Sprite pSprite = new Sprite(x,y,mAvailableTextureRegion);
		mScene.getTopLayer().addEntity(pSprite);
		mAvailableSpriteArray.add(pSprite);
	}

	@Override
	public void ClearAvailableTips() {
		// TODO Auto-generated method stub
		int iCount = mAvailableSpriteArray.size();
		for (int i = 0 ; i < iCount ; ++i)
		{
			Sprite pSprite = mAvailableSpriteArray.get(i);
			mScene.getTopLayer().removeEntity(pSprite);
		}
		
		mAvailableSpriteArray.clear();
	}


	@Override
	public void SetScore(int iBlack, int iWhite) {
		// TODO Auto-generated method stub
		String strBlack = mSmartReversi.getString(R.string.black_name);
		String strWhite = mSmartReversi.getString(R.string.white_name);
		String str = String.format("%s:%02d           %s:%02d",strBlack,iBlack,strWhite,iWhite);
		mScoreText.setText(str);
	}
	
}
