package com.space.game;

import java.util.HashMap;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.util.Log;
import android.view.Menu;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.space.wars.Constants;

public class InitialView extends Activity

{
	public static final String TAG = "StarWars->InitialView";

	public static final String MM_APID = "58057";
	public static final String MM_INTER_APID = "58057";
	public static final String MM_TEST_APID = "28911";
	public static final String MM_TEST_INTERID = "28911";

	private static final int NUM_META_TAGS = 9;

	public static MediaPlayer backgroundMp;

	public static Boolean backgroundMpErrored = false;
	public static Boolean initialViewCreated = false;

	public static RelativeLayout layoutView;
	public static int levelCount;
	public static InitialView mainInstance;
	public static boolean menuHasBeenJustLoaded = false;
	public static boolean[] metas;

	public static final Random rand = new Random();
	public static Activity shownActivity;
	public final int shipSizeX = 50;
	public final int shipSizeY = 70;
	TextView bg;
	int counter;
	public final int delay = 30;
	public boolean gamePaused = false;
	ImageView initialShip;
	int initialShipAngle;
	boolean initialShipControl;
	int initialShipDir;
	Bitmap[] initialShipFrames;
	public boolean isRunning;
	private Context mContext;

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);

		SharedPreferences.Editor mEditor;

		initialViewCreated = true;
		mainInstance = this;

		setContentView(2130903041);
		setRequestedOrientation(1);
		setVolumeControlStream(3);
		overridePendingTransition(0, 0);

		SharedPreferences mSharedPreferences = getSharedPreferences("AppData",	0);
		mEditor = mSharedPreferences.edit();
		mEditor.putInt("State", 0);
		mEditor.putBoolean("ShouldContinue", false);
		mEditor.putBoolean("GamePaused", false);
		mEditor.commit();

		mContext = getApplicationContext();
		createBackgroundMusic(mContext);
		if (!(mSharedPreferences.contains("Vibration"))) {
			mEditor.putBoolean("Vibration", true);
			mEditor.commit();
		}
		levelCount = 1;
		layoutView = (RelativeLayout) findViewById(2131230724);
		layoutView.removeAllViews();
		CookieSyncManager.createInstance(this.mContext);
		CookieManager.getInstance().removeAllCookie();
		mEditor.putInt("ShowMoreApps", 2);
		mEditor.commit();
		Intent localIntent = new Intent(this.mContext, Menu.class);
		overridePendingTransition(2130968576, 2130968577);
		startActivityForResult(localIntent, 1);
		prepareABTesting();
		update();

	}

	public static boolean canPlayMoreGames(Context paramContext) {
		SharedPreferences localSharedPreferences1 = paramContext
				.getSharedPreferences("AppData", 0);
		localSharedPreferences1.edit();
		SharedPreferences localSharedPreferences2 = paramContext
				.getSharedPreferences("ShipSmasherShop", 0);
		int i = localSharedPreferences1
				.getInt("NumberOfPlayedGamesSinceV29", 0);
		int j = 0;
		if (localSharedPreferences2.getBoolean("KidsMode", false))
			j = 0 + 1;
		if (localSharedPreferences2.getBoolean("FunMode", false))
			++j;
		if (localSharedPreferences2.getBoolean("BabyMode", false))
			++j;
		if (localSharedPreferences2.getBoolean("NoAds", false))
			++j;
		if (localSharedPreferences2.getBoolean("Protection", false))
			++j;
		if (localSharedPreferences2.getInt("MaxLifes", 3) > 3)
			++j;
		Log.i("AS", "Plays: " + i + ", Products: " + j);
		if ((i >= 15) && (j != 0))
			;
		return true;
	}

	public static void createBackgroundMusic(Context paramContext) {
		boolean bool = paramContext.getSharedPreferences("AppData", 0)
				.getBoolean("Sound", true);
		if (backgroundMp == null)
			if (bool) {
				backgroundMpErrored = false;
				backgroundMp = MediaPlayer.create(paramContext, 2131034122);
				if (backgroundMp != null) {
					backgroundMp.setAudioStreamType(3);
					backgroundMp.setLooping(true);
					backgroundMp.start();
					backgroundMp
							.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
								public void onCompletion(
										MediaPlayer paramMediaPlayer) {
									if (paramMediaPlayer != null) {
										if ((!(InitialView.backgroundMpErrored
												.booleanValue()))
												&& (paramMediaPlayer
														.isPlaying()))
											paramMediaPlayer.stop();
										paramMediaPlayer.release();
									}
								}
							});

				}
			}

		backgroundMp.release();
		backgroundMp = null;
		createBackgroundMusic(paramContext);

	}

	static void deliverTapJoyProduct(Activity paramActivity) {
		SharedPreferences localSharedPreferences = paramActivity
				.getSharedPreferences("ShipSmasherShop", 0);
		SharedPreferences.Editor localEditor = localSharedPreferences.edit();
		short s = 1;
		if (Util.isDebug)
			s = 100;
		int i = localSharedPreferences.getInt("TapJoyProduct", 0);
		int j = 0;
		switch (i) {
		default:
			if (paramActivity instanceof StoreActivity) {
				int k = Integer.decode(
						((StoreActivity) paramActivity).coinText.getText()
								.toString()).intValue()
						- j;
				((StoreActivity) paramActivity).coinText.setText(k);
			}
			localEditor.putInt("TapJoySpentPoints",
					j + localSharedPreferences.getInt("TapJoySpentPoints", 0));
			localEditor.commit();
			playButtonSound("Out", paramActivity);
			return;
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		}
		try {
			do {
				HashMap localHashMap9 = new HashMap();
				localHashMap9.put("FromTapJoy", "Kids");
				localHashMap9.put("All", "Kids_Purchased");
				GoogleAnalyticsTracker.getInstance().trackEvent("Store",
						"Store_Item", "Store_Item_Kids_FromTapJoy", -1);
				j = 1000 / s;
				localEditor.putBoolean("KidsMode", true);
				localEditor.commit();
			} while (!(paramActivity instanceof StoreActivity));
			label522: paramActivity.findViewById(2131230807).setVisibility(4);
		} catch (Exception localException9) {
			while (true)
				localException9.printStackTrace();
			try {
				do {
					HashMap localHashMap8 = new HashMap();
					localHashMap8.put("FromTapJoy", "Fun");
					localHashMap8.put("All", "Fun_Purchased");
					GoogleAnalyticsTracker.getInstance().trackEvent("Store",
							"Store_Item", "Store_Item_Fun_FromTapJoy", -1);
					j = 500 / s;
					localEditor.putBoolean("FunMode", true);
					localEditor.commit();
				} while (!(paramActivity instanceof StoreActivity));
				paramActivity.findViewById(2131230811).setVisibility(4);
			} catch (Exception localException8) {
				byte b2;
				while (true)
					localException8.printStackTrace();
				try {
					do {
						HashMap localHashMap7 = new HashMap();
						localHashMap7.put("FromTapJoy", "NoAds");
						localHashMap7.put("All", "NoAds_Purchased");
						GoogleAnalyticsTracker.getInstance().trackEvent(
								"Store", "Store_Item",
								"Store_Item_NoAds_FromTapJoy", -1);
						localEditor.putBoolean("NoAds", true);
						localEditor.commit();
						if (ad != null) {
							b2 = 0;
							if (b2 < 5)
								break label522;
						}
					} while (!(paramActivity instanceof StoreActivity));
					paramActivity.findViewById(2131230814).setVisibility(4);
				} catch (Exception localException7) {
					while (true) {
						while (true)
							localException7.printStackTrace();
						ad[b2].setVisibility(4);
						++b2;
					}
					try {
						do {
							HashMap localHashMap6 = new HashMap();
							localHashMap6.put("FromTapJoy", "Lives3To4");
							localHashMap6.put("All", "Lives3To4_Purchased");
							GoogleAnalyticsTracker.getInstance().trackEvent(
									"Store", "Store_Item",
									"Store_Item_Lives3To4_FromTapJoy", -1);
							j = 300 / s;
							localEditor.putInt("MaxLifes", 4);
							localEditor.commit();
						} while (!(paramActivity instanceof StoreActivity));
						paramActivity.findViewById(2131230816).setVisibility(4);
						paramActivity.findViewById(2131230817).setVisibility(4);
						paramActivity.findViewById(2131230818).setVisibility(0);
					} catch (Exception localException6) {
						while (true)
							localException6.printStackTrace();
						try {
							do {
								HashMap localHashMap5 = new HashMap();
								localHashMap5.put("FromTapJoy", "Lives3To5");
								localHashMap5.put("All", "Lives3To5_Purchased");
								GoogleAnalyticsTracker
										.getInstance()
										.trackEvent(
												"Store",
												"Store_Item",
												"Store_Item_Lives3To5_FromTapJoy",
												-1);
								j = 600 / s;
								localEditor.putInt("MaxLifes", 5);
								localEditor.commit();
							} while (!(paramActivity instanceof StoreActivity));
							paramActivity.findViewById(2131230816)
									.setVisibility(4);
							paramActivity.findViewById(2131230817)
									.setVisibility(4);
							paramActivity.findViewById(2131230818)
									.setVisibility(4);
						} catch (Exception localException5) {
							while (true)
								localException5.printStackTrace();
							try {
								do {
									HashMap localHashMap4 = new HashMap();
									localHashMap4.put("FromTapJoy",
											"GameOverProtection");
									localHashMap4.put("All",
											"GameOverProtection_Purchased");
									GoogleAnalyticsTracker
											.getInstance()
											.trackEvent(
													"Store",
													"Store_Item",
													"Store_Item_GameOverProtection_FromTapJoy",
													-1);
									j = 500 / s;
									localEditor.putBoolean("Protection", true);
									localEditor.putBoolean("ProtectionEnabled",
											true);
									localEditor.commit();
								} while (!(paramActivity instanceof StoreActivity));
								paramActivity.findViewById(2131230819)
										.setVisibility(4);
							} catch (Exception localException4) {
								while (true)
									localException4.printStackTrace();
								try {
									do {
										HashMap localHashMap3 = new HashMap();
										localHashMap3.put("FromTapJoy",
												"Lives4To5");
										localHashMap3.put("All",
												"Lives4To5_Purchased");
										GoogleAnalyticsTracker
												.getInstance()
												.trackEvent(
														"Store",
														"Store_Item",
														"Store_Item_Lives4To5_FromTapJoy",
														-1);
										j = 300 / s;
										localEditor.putInt("MaxLifes", 5);
										localEditor.commit();
									} while (!(paramActivity instanceof StoreActivity));
									paramActivity.findViewById(2131230816)
											.setVisibility(4);
									paramActivity.findViewById(2131230817)
											.setVisibility(4);
									paramActivity.findViewById(2131230818)
											.setVisibility(4);
								} catch (Exception localException3) {
									while (true)
										localException3.printStackTrace();
									try {
										do {
											HashMap localHashMap2 = new HashMap();
											localHashMap2.put("FromTapJoy",
													"Baby");
											localHashMap2.put("All",
													"Baby_Purchased");
											GoogleAnalyticsTracker
													.getInstance()
													.trackEvent(
															"Store",
															"Store_Item",
															"Store_Item_Baby_FromTapJoy",
															-1);
											j = 1000 / s;
											localEditor.putBoolean("BabyMode",
													true);
											localEditor.commit();
										} while (!(paramActivity instanceof StoreActivity));
										paramActivity.findViewById(2131230809)
												.setVisibility(4);
									} catch (Exception localException2) {
										while (true)
											localException2.printStackTrace();
										try {
											byte b1;
											do {
												do {
													do {
														HashMap localHashMap1 = new HashMap();
														localHashMap1.put(
																"FromTapJoy",
																"DeluxePack");
														localHashMap1
																.put("All",
																		"DeluxePack_Purchased");
														GoogleAnalyticsTracker
																.getInstance()
																.trackEvent(
																		"Store",
																		"Store_Item",
																		"Store_Item_DeluxePack_FromTapJoy",
																		-1);
														j = 2500 / s;
														localEditor.putInt(
																"MaxLifes", 5);
														localEditor.putBoolean(
																"BabyMode",
																true);
														localEditor
																.putBoolean(
																		"FunMode",
																		true);
														localEditor.putBoolean(
																"KidsMode",
																true);
														localEditor.putBoolean(
																"Protection",
																true);
														localEditor
																.putBoolean(
																		"ProtectionEnabled",
																		true);
														localEditor.putBoolean(
																"NoAds", true);
														localEditor.putBoolean(
																"DeluxePack",
																true);
														localEditor.commit();
													} while (!(paramActivity instanceof StoreActivity));
													paramActivity.findViewById(
															2131230816)
															.setVisibility(4);
													paramActivity.findViewById(
															2131230817)
															.setVisibility(4);
													paramActivity.findViewById(
															2131230818)
															.setVisibility(4);
													paramActivity.findViewById(
															2131230809)
															.setVisibility(4);
													paramActivity.findViewById(
															2131230807)
															.setVisibility(4);
													paramActivity.findViewById(
															2131230811)
															.setVisibility(4);
													paramActivity.findViewById(
															2131230819)
															.setVisibility(4);
													paramActivity.findViewById(
															2131230814)
															.setVisibility(4);
													paramActivity.findViewById(
															2131230803)
															.setVisibility(4);
												} while (ad == null);
												b1 = 0;
											} while (b1 >= 5);
											ad[b1].setVisibility(4);
											++b1;
										} catch (Exception localException1) {
											while (true)
												localException1
														.printStackTrace();
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public static void playButtonSound(String paramString, Context paramContext) {
		int i;
		if (paramString.equals("In"))
			i = 2131034124;
		while (true) {
			if (paramContext.getSharedPreferences("AppData", 0).getBoolean(
					"Sound", true)) {
				MediaPlayer localMediaPlayer = MediaPlayer.create(paramContext,
						i);
				if (localMediaPlayer != null) {
					localMediaPlayer.setAudioStreamType(3);
					localMediaPlayer.start();
					localMediaPlayer
							.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
								public void onCompletion(
										MediaPlayer paramMediaPlayer) {
									if ((paramMediaPlayer != null)
											&& (paramMediaPlayer.isPlaying()))
										paramMediaPlayer.stop();
								}
							});
				}
			}
			return;
			i = 2131034125;
		}
	}

	protected void finalize()
  {
    try
    {
      finalize();
      label4: return;
    }
    catch (Throwable localThrowable)
    {
      break label4:
    }
  }

	public void onDestroy() {
		Log.w(TAG, "ONDESTROY");
		super.onDestroy();
		Process.killProcess(Process.myPid());
	}

	public void onResume() {
		super.onResume();
	}

	public void onWindowFocusChanged(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      SharedPreferences.Editor localEditor = getSharedPreferences("AppData", 0).edit();
      localEditor.putInt("State", 0);
      localEditor.putBoolean("ShouldContinue", false);
      localEditor.commit();
      overridePendingTransition(2130968576, 2130968577);
      finishActivity(1);
    }
    try
    {
      finalize();
      label65: finish();
      return;
    }
    catch (Throwable localThrowable)
    {
      break label65:
    }
  }

	public void prepareABTesting() {
		SharedPreferences localSharedPreferences = getSharedPreferences(
				"AppData", 0);
		SharedPreferences.Editor localEditor = localSharedPreferences.edit();
		if (!(localSharedPreferences.getFloat(
				Constants.key.getABDifficultyLabel(this), 0F) < 0F)) {
			localEditor
					.putFloat(Constants.key.getABDifficultyLabel(this), 3.5F);
			localEditor.commit();
		}
	}

	public void receivedYesOnMeta(int paramInt) {
		Log.i("AS", "Received Meta" + paramInt);
		metas[(paramInt - 1)] = true;
	}

	public void update()
  {
    Handler localHandler = new Handler();
    localHandler.postDelayed(new Runnable(this, localHandler)
    {
      public void finalize()
      {
        try
        {
          super.finalize();
          return;
        }
        catch (Throwable localThrowable)
        {
          while (true)
            localThrowable.printStackTrace();
        }
      }

      public void run()
      {
        ViewParent localViewParent;
        RelativeLayout.LayoutParams localLayoutParams;
        SharedPreferences localSharedPreferences = this.this$0.getSharedPreferences("AppData", 0);
        SharedPreferences.Editor localEditor = localSharedPreferences.edit();
        boolean bool1 = localSharedPreferences.getBoolean("GamePaused", false);
        boolean bool2 = this.this$0.getSharedPreferences("ShipSmasherShop", 0).getBoolean("NoAds", false);
        if ((!(bool1)) && (!(bool2)))
        {
          int i = InitialView.adCounter;
          InitialView.adCounter = i + 1;
          if ((i % 150 == 0) && (localSharedPreferences.getInt("GameMode", 0) != 3))
          {
            localViewParent = null;
            localLayoutParams = null;
          }
        }
        try
        {
          byte b;
          if ((InitialView.shownActivity != null) && (InitialView.access$0().getParent() != null) && (InitialView.access$0().getParent() instanceof RelativeLayout))
          {
            localViewParent = InitialView.access$0().getParent();
            localLayoutParams = (RelativeLayout.LayoutParams)InitialView.access$0().getLayoutParams();
            b = 0;
            if ((!(InitialView.ad[(InitialView.adCounter / 150 % 5)].isReady())) && (b < 5))
              break label353;
            if (!(InitialView.access$0().isReady()))
              InitialView.access$0().loadAd(new AdRequest());
            ((RelativeLayout)localViewParent).removeView(InitialView.access$0());
          }
          label204: InitialView.access$1(InitialView.ad[(InitialView.adCounter / 150 % 5)]);
          if (localViewParent != null)
          {
            InitialView.access$0().setLayoutParams(localLayoutParams);
            ((RelativeLayout)localViewParent).addView(InitialView.access$0());
            if (localSharedPreferences.getInt("GameMode", 0) != 3)
              break label392;
            InitialView.access$0().setClickable(false);
          }
          switch (localSharedPreferences.getInt("State", 0))
          {
          case 13:
          default:
          case 3:
          case 4:
          case 15:
          case 5:
          case 12:
          case 6:
          case 7:
          case 8:
          case 9:
          case 10:
          case 11:
            while (true)
            {
              while (true)
              {
                while (true)
                {
                  while (true)
                  {
                    while (true)
                    {
                      while (true)
                      {
                        while (true)
                        {
                          while (true)
                          {
                            while (true)
                            {
                              while (true)
                              {
                                while (true)
                                {
                                  while (true)
                                  {
                                    while (true)
                                    {
                                      this.val$handler.postDelayed(this, 30);
                                      return;
                                      label353: InitialView.ad[(InitialView.adCounter / 150 % 5)].loadAd(new AdRequest());
                                      InitialView.adCounter = 150 + InitialView.adCounter;
                                      ++b;
                                    }
                                    label392: InitialView.access$0().setClickable(true);
                                  }
                                  this.this$0.finishActivity(1);
                                  InitialView.levelCount = 0;
                                  localEditor.putInt("State", 0);
                                  localEditor.putBoolean("ShouldContinue", false);
                                  localEditor.commit();
                                  Intent localIntent12 = new Intent(InitialView.access$2(this.this$0), GameOver.class);
                                  this.this$0.overridePendingTransition(2130968576, 2130968577);
                                  localIntent12.addFlags(536870912);
                                  this.this$0.startActivityForResult(localIntent12, 1);
                                  System.gc();
                                  localEditor.putBoolean("GamePaused", false);
                                  localEditor.commit();
                                }
                                this.this$0.finishActivity(1);
                                localEditor.putLong("StartGameDate", new Date().getTime());
                                localEditor.putLong("PlayTimeDiscount", 0L);
                                localEditor.putInt("Points", 0);
                                localEditor.putInt("Lifes", 2);
                                localEditor.putInt("State", 1);
                                localEditor.putBoolean("ShouldContinue", true);
                                localEditor.putBoolean("Bonus", false);
                                localEditor.putBoolean("Bonus-Life", false);
                                localEditor.putBoolean("Paused", false);
                                localEditor.putBoolean("FirstLevel", true);
                                localEditor.putFloat("Acceleration", 0F);
                                localEditor.putInt("SurfaceAction", 0);
                                localEditor.putBoolean("Prot", this.this$0.getSharedPreferences("ShipSmasherShop", 0).getBoolean("ProtectionEnabled", false));
                                localEditor.commit();
                                InitialView.levelCount = 1;
                                Intent localIntent11 = new Intent(InitialView.access$2(this.this$0), GameActivity.class);
                                this.this$0.overridePendingTransition(2130968576, 2130968577);
                                this.this$0.startActivityForResult(localIntent11, 1);
                                System.gc();
                                localEditor.putBoolean("GamePaused", false);
                                localEditor.commit();
                              }
                              this.this$0.finishActivity(1);
                              Intent localIntent10 = new Intent(InitialView.access$2(this.this$0), TutorialActivity.class);
                              this.this$0.overridePendingTransition(2130968576, 2130968577);
                              this.this$0.startActivityForResult(localIntent10, 1);
                              localEditor.putInt("State", 0);
                              System.gc();
                              localEditor.commit();
                            }
                            this.this$0.overridePendingTransition(2130968576, 2130968577);
                            this.this$0.finishActivity(1);
                            InitialView.levelCount = 0;
                            localEditor.putInt("State", 0);
                            localEditor.putBoolean("ShouldContinue", false);
                            localEditor.commit();
                            Intent localIntent9 = new Intent(InitialView.access$2(this.this$0), Menu.class);
                            this.this$0.startActivityForResult(localIntent9, 1);
                            System.gc();
                            localEditor.putBoolean("GamePaused", false);
                            localEditor.putInt("State", 0);
                            localEditor.commit();
                          }
                          this.this$0.overridePendingTransition(2130968576, 2130968577);
                          this.this$0.finishActivity(1);
                          localEditor.putInt("State", 0);
                          localEditor.putBoolean("ShouldContinue", false);
                          localEditor.commit();
                          Intent localIntent8 = new Intent(InitialView.access$2(this.this$0), PlayMenu.class);
                          this.this$0.startActivityForResult(localIntent8, 1);
                          System.gc();
                          localEditor.putBoolean("GamePaused", false);
                          localEditor.commit();
                        }
                        this.this$0.overridePendingTransition(2130968576, 2130968577);
                        this.this$0.finishActivity(1);
                        localEditor.putInt("State", 0);
                        localEditor.putBoolean("ShouldContinue", false);
                        localEditor.commit();
                        Intent localIntent7 = new Intent(InitialView.access$2(this.this$0), Twitter.class);
                        this.this$0.overridePendingTransition(2130968576, 2130968577);
                        this.this$0.startActivityForResult(localIntent7, 1);
                        System.gc();
                        localEditor.putBoolean("GamePaused", false);
                        localEditor.commit();
                      }
                      this.this$0.overridePendingTransition(2130968576, 2130968577);
                      this.this$0.finishActivity(1);
                      localEditor.putInt("State", 0);
                      localEditor.putBoolean("ShouldContinue", false);
                      localEditor.commit();
                      Intent localIntent6 = new Intent(InitialView.access$2(this.this$0), GameOver.class);
                      this.this$0.startActivityForResult(localIntent6, 1);
                      System.gc();
                      localEditor.putBoolean("GamePaused", false);
                      localEditor.commit();
                    }
                    this.this$0.overridePendingTransition(2130968576, 2130968577);
                    this.this$0.finishActivity(1);
                    localEditor.putInt("State", 0);
                    localEditor.putBoolean("ShouldContinue", false);
                    localEditor.commit();
                    Intent localIntent5 = new Intent(InitialView.access$2(this.this$0), HighScores.class);
                    this.this$0.startActivityForResult(localIntent5, 1);
                    System.gc();
                    localEditor.putBoolean("GamePaused", false);
                    localEditor.commit();
                  }
                  this.this$0.overridePendingTransition(2130968576, 2130968577);
                  this.this$0.finishActivity(1);
                  localEditor.putInt("State", 0);
                  localEditor.putBoolean("ShouldContinue", false);
                  localEditor.commit();
                  Intent localIntent4 = new Intent(InitialView.access$2(this.this$0), Options.class);
                  this.this$0.startActivityForResult(localIntent4, 1);
                  System.gc();
                  localEditor.putBoolean("GamePaused", false);
                  localEditor.commit();
                }
                this.this$0.overridePendingTransition(2130968576, 2130968577);
                this.this$0.finishActivity(1);
                localEditor.putInt("State", 0);
                localEditor.putBoolean("ShouldContinue", false);
                localEditor.commit();
                Intent localIntent3 = new Intent(InitialView.access$2(this.this$0), FBCustom.class);
                this.this$0.startActivityForResult(localIntent3, 1);
                System.gc();
                localEditor.putBoolean("GamePaused", false);
                localEditor.commit();
              }
              this.this$0.overridePendingTransition(2130968576, 2130968577);
              this.this$0.finishActivity(1);
              localEditor.putInt("State", 0);
              localEditor.putBoolean("ShouldContinue", false);
              localEditor.commit();
              Intent localIntent2 = new Intent(InitialView.access$2(this.this$0), StoreActivity.class);
              this.this$0.startActivityForResult(localIntent2, 1);
              System.gc();
              localEditor.putBoolean("GamePaused", false);
              localEditor.commit();
            }
          case 14:
          }
          this.this$0.overridePendingTransition(2130968576, 2130968577);
          this.this$0.finishActivity(1);
          localEditor.putInt("State", 0);
          localEditor.putBoolean("ShouldContinue", false);
          localEditor.commit();
          Intent localIntent1 = new Intent(InitialView.access$2(this.this$0), MoreGames.class);
          this.this$0.startActivityForResult(localIntent1, 1);
          System.gc();
          localEditor.putBoolean("GamePaused", false);
          localEditor.commit();
        }
        catch (Exception localException)
        {
          break label204:
        }
      }
    }
    , 30);
  }
}