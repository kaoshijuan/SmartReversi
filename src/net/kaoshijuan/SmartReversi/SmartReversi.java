package net.kaoshijuan.SmartReversi;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.ui.activity.LayoutGameActivity;


import com.mobclix.android.sdk.MobclixAdView;
import com.mobclix.android.sdk.MobclixAdViewListener;
import com.mobclix.android.sdk.MobclixMMABannerXLAdView;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;



public class SmartReversi extends LayoutGameActivity implements MobclixAdViewListener, IOnSceneTouchListener{
    /** Called when the activity is first created. */
	
	private DisplayAdapter mDisplayAdapter;
	private MobclixMMABannerXLAdView adview_banner;

	//private PowerManager.WakeLock mWakeLock = null;
	
	@Override 
	protected void onCreate(final Bundle pSavedInstanceState)
	{

		
		mDisplayAdapter = new DisplayAdapter();
		mDisplayAdapter.SetContext(this);			
		super.onCreate(pSavedInstanceState);
/*		AdManager.setTestDevices(new String[]{AdManager.TEST_EMULATOR,});
		AdView adView = (AdView)findViewById(R.id.ad);
		adView.requestFreshAd();
	*/
		/*if(mWakeLock == null)
		{
			mWakeLock = ((PowerManager)getSystemService(POWER_SERVICE)).
			newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK , 
					"SmartReversi");
			mWakeLock.acquire();
		}*/
		
        adview_banner = (MobclixMMABannerXLAdView) findViewById(R.id.advertising_banner_view);
        adview_banner.addMobclixAdViewListener(this);
        adview_banner.getAd();
        
        CreateButton();
	}
	
	@Override
	public Engine onLoadEngine() {
	
		return mDisplayAdapter.CreateEngine();
	}

	@Override
	public void onLoadResources() {
		mDisplayAdapter.CreateTextureRegion();
	}

	@Override
	public Scene onLoadScene() {
		Scene scene = mDisplayAdapter.CreateScene();
		
/*		this.runOnUpdateThread(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//这个放在update线程里面做是因为太耗时了，启动时UI创建不起来
				CreateFile("book");
				CreateFile("coeffs2");
				mDisplayAdapter.Initialize();
			}
		});
		*/
		return scene;
	}

	@Override
	public void onLoadComplete() {
		
		final SmartReversi pTemp = this;
		
		final Timer pTimer = new Timer();
		pTimer.schedule(new TimerTask(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				pTimer.cancel();
				//这个放在Timer线程里面做是因为太耗时了，启动时UI创建不起来
				CreateFile("book");
				CreateFile("coeffs2");
				mDisplayAdapter.Initialize();
			}
		}, 10);
	}
	
	@Override
	public boolean onSceneTouchEvent(final Scene pScene, final TouchEvent pSceneTouchEvent) {

		final float x = pSceneTouchEvent.getX();
		final float y = pSceneTouchEvent.getY();
		
		this.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				mDisplayAdapter.OnClickPosition(x, y);
			}
		});
		
		return false;
	}

	@Override
	protected int getLayoutID() {
		// TODO Auto-generated method stub
		return R.layout.main;
	}

	@Override
	protected int getRenderSurfaceViewID() {
		// TODO Auto-generated method stub
		return R.id.SmartReversi_rendersurfaceview;
	}
/*
	@Override
	protected void onResume()
	{
		if(mWakeLock != null)
			mWakeLock.acquire();
	}
	
	@Override
	protected void onPause()
	{
		if(mWakeLock != null)
			mWakeLock.release();
	}*/
	
	public String keywords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onAdClick(MobclixAdView arg0) {
		// TODO Auto-generated method stub
		Log.v("SmartReversi","OnAdClick");
	}

	@Override
	public void onCustomAdTouchThrough(MobclixAdView arg0, String arg1) {
		// TODO Auto-generated method stub
		Log.v("SmartReversi",arg1+"onCustomAdTouchThrough");
	}

	@Override
	public void onFailedLoad(MobclixAdView arg0, int arg1) {
		// TODO Auto-generated method stub
		Log.v("SmartReversi","OnFailedLoad,result:"+arg1);
	}

	@Override
	public boolean onOpenAllocationLoad(MobclixAdView arg0, int arg1) {
		// TODO Auto-generated method stub
		Log.v("SmartReversi","onOpenAllocationLoad");
		return false;
	}

	@Override
	public void onSuccessfulLoad(MobclixAdView arg0) {
		// TODO Auto-generated method stub
		arg0.setVisibility(View.VISIBLE);
	}

	@Override
	public String query() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if(keyCode == KeyEvent.KEYCODE_BACK)
		{
			OnExit();
			return true;
		}
		
		return super.onKeyDown(keyCode, event);
	}
	
	private void OnExit()
	{
		Builder d = new AlertDialog.Builder(this);
		d.setTitle(R.string.exit_title);
		d.setMessage(R.string.exit_prompt);
		d.setPositiveButton(R.string.exit_yes, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				finish();
			}
		}); 
		d.setNegativeButton(R.string.exit_no,null);
		d.show();
	}
	
	private void CreateButton()
	{
        Button new_game = (Button) findViewById(R.id.new_game);
        new_game.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				runOnUpdateThread(new Runnable() {
					@Override
					public void run() {
						mDisplayAdapter.NewGame();
					}
				});
			}
        });
        
        Button buy = (Button) findViewById(R.id.buy);
        buy.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				//Toast.makeText(v.getContext(), getString(R.string.buy_prom), Toast.LENGTH_SHORT).show();
				Uri uri = Uri.parse(getString(R.string.buy_url)); 
				Intent it = new Intent(Intent.ACTION_VIEW, uri); 
				startActivity(it); 
			}
        });
        
        Button help = (Button) findViewById(R.id.help);
        help.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Uri uri = Uri.parse(getString(R.string.help_url)); 
				Intent it = new Intent(Intent.ACTION_VIEW, uri); 
				startActivity(it);
			}
        	
        });
        
        Button exit_button = (Button) findViewById(R.id.exit);
        exit_button.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				OnExit();
			}        	
        });		
	}
	
	private void CreateFile(String sFileName)
	{
        // 打包了两个文件，需要解压缩到sdcard上面
        File fFile = new File("/sdcard/"+sFileName+".bin");
        
        FileInputStream filein = null;
        
        boolean file_not_found = false;
        try {
			filein = new FileInputStream(fFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			Log.v("SmartReversi",fFile.getAbsolutePath()+" not found");
			file_not_found = true;
		}

		if(file_not_found)
		{
			if(sFileName == "book")
			{
				int []id = {R.raw.bookaa,
						R.raw.bookab,R.raw.bookac,R.raw.bookad,
						R.raw.bookae,R.raw.bookaf,R.raw.bookag,
						R.raw.bookah,R.raw.bookai};
				for(int i = 0;i < 9; ++i)
				{
					Log.v("SmartReversi","Now process "+i+" for "+sFileName);
					ReadAndWrite(id[i],sFileName);
				}
			}else
			{
				int []id = {R.raw.coeffs2aa,R.raw.coeffs2ab};
				for(int i = 0;i < 2; ++i)
				{
					Log.v("SmartReversi","Now process "+i+" for "+sFileName);
					ReadAndWrite(id[i],sFileName);
				}
			}
		}//if(file_not_found)
		
		return;
	}
	
	private void ReadAndWrite(int id,String sFileName)
	{
		
		File fFile = new File("/sdcard/"+sFileName+".bin");
		InputStream in = getResources().openRawResource(id);
		int len;
		try {
			len = in.available();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.v("SmartReversi", "open raw "+sFileName+" failed");
			return;
		}
		byte [] buffer = new byte[len];

		try {
			in.read(buffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.v("SmartReversi", "read raw "+sFileName+" failed:"+e.getLocalizedMessage());
			return;
		}
		
		FileOutputStream sFileStream = null;
		try {
			sFileStream = new FileOutputStream(fFile,true);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.v("SmartReversi","open sdcard for writing "+fFile.getAbsolutePath()+" failed:"+e.getLocalizedMessage());
			return;
		}
		
		try {
			sFileStream.write(buffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.v("SmartReversi","write sdcard for "+fFile.getAbsolutePath()+" failed");
		}
		
		try {
			sFileStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.v("SmartReversi","close "+sFileName+" failed");
			return;
		}		
	}
}
