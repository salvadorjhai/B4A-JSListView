package b4a.example.jslistview;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class toolbartestactivity extends android.support.v7.app.AppCompatActivity implements B4AActivity{
	public static toolbartestactivity mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isFirst) {
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example.jslistview", "b4a.example.jslistview.toolbartestactivity");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (toolbartestactivity).");
				p.finish();
			}
		}
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		mostCurrent = this;
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(processBA, wl, false))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "b4a.example.jslistview", "b4a.example.jslistview.toolbartestactivity");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.jslistview.toolbartestactivity", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (toolbartestactivity) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (toolbartestactivity) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEventFromUI(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return toolbartestactivity.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeydown", this, new Object[] {keyCode, event}))
            return true;
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeyup", this, new Object[] {keyCode, event}))
            return true;
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null) //workaround for emulator bug (Issue 2423)
            return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        BA.LogInfo("** Activity (toolbartestactivity) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
			if (mostCurrent == null || mostCurrent != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (toolbartestactivity) Resume **");
		    processBA.raiseEvent(mostCurrent._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        for (int i = 0;i < permissions.length;i++) {
            Object[] o = new Object[] {permissions[i], grantResults[i] == 0};
            processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
        }
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public com.salvadorjhai.widgets.JSListView _jslistview1 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label2 = null;
public anywheresoftware.b4a.objects.collections.List _vvv3 = null;
public de.amberhome.objects.appcompat.ACToolbarLightWrapper _actoolbarlight1 = null;
public b4a.example.jslistview.main _vvv2 = null;
public b4a.example.jslistview.customtemplate2 _vv4 = null;
public b4a.example.jslistview.twolineandbitmap _vv2 = null;
public b4a.example.jslistview.customtwolineandbitmap _vv3 = null;
public b4a.example.jslistview.customtemplate4 _vv6 = null;
public b4a.example.jslistview.singlelineactivity _v7 = null;
public b4a.example.jslistview.customtemplate3 _vv5 = null;
public b4a.example.jslistview.singlelineandbitmap _v0 = null;
public b4a.example.jslistview.starter _vv0 = null;
public b4a.example.jslistview.twolineactivity _vv1 = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
anywheresoftware.b4a.objects.collections.JSONParser _js = null;
anywheresoftware.b4a.objects.collections.Map _m = null;
 //BA.debugLineNum = 24;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 26;BA.debugLine="Activity.LoadLayout(\"3\")";
mostCurrent._activity.LoadLayout("3",mostCurrent.activityBA);
 //BA.debugLineNum = 30;BA.debugLine="ACToolBarLight1.Width = 100%x";
mostCurrent._actoolbarlight1.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 31;BA.debugLine="ACToolBarLight1.Title = \"Toolbar - Two Line Item";
mostCurrent._actoolbarlight1.setTitle(BA.ObjectToCharSequence("Toolbar - Two Line Item and Bitmap"));
 //BA.debugLineNum = 32;BA.debugLine="ACToolBarLight1.NavigationIconBitmap = Applicatio";
mostCurrent._actoolbarlight1.setNavigationIconBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Application.getIcon().getObject()));
 //BA.debugLineNum = 33;BA.debugLine="ACToolBarLight1.InitMenuListener";
mostCurrent._actoolbarlight1.InitMenuListener();
 //BA.debugLineNum = 35;BA.debugLine="Dim js As JSONParser";
_js = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 36;BA.debugLine="js.Initialize(File.ReadString(File.DirAssets, \"re";
_js.Initialize(anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"recipes.json"));
 //BA.debugLineNum = 37;BA.debugLine="Dim m As Map = js.NextObject";
_m = new anywheresoftware.b4a.objects.collections.Map();
_m = _js.NextObject();
 //BA.debugLineNum = 38;BA.debugLine="recipes = m.Get(\"recipes\")";
mostCurrent._vvv3.setObject((java.util.List)(_m.Get((Object)("recipes"))));
 //BA.debugLineNum = 40;BA.debugLine="JSListView1.DataSource = recipes";
mostCurrent._jslistview1.setDataSource(mostCurrent._vvv3);
 //BA.debugLineNum = 41;BA.debugLine="JSListView1.FastScrollEnabled = True";
mostCurrent._jslistview1.setFastScrollEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 42;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 48;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 50;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 44;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 46;BA.debugLine="End Sub";
return "";
}
public static String  _actoolbarlight1_menuitemclick(de.amberhome.objects.appcompat.ACMenuItemWrapper _item) throws Exception{
 //BA.debugLineNum = 125;BA.debugLine="Sub ACToolBarLight1_MenuItemClick (Item As ACMenuI";
 //BA.debugLineNum = 127;BA.debugLine="End Sub";
return "";
}
public static String  _actoolbarlight1_navigationitemclick() throws Exception{
 //BA.debugLineNum = 121;BA.debugLine="Sub ACToolBarLight1_NavigationItemClick";
 //BA.debugLineNum = 123;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 13;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 16;BA.debugLine="Private JSListView1 As JSListView";
mostCurrent._jslistview1 = new com.salvadorjhai.widgets.JSListView();
 //BA.debugLineNum = 17;BA.debugLine="Private ImageView1 As ImageView";
mostCurrent._imageview1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private Label1 As Label";
mostCurrent._label1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private Label2 As Label";
mostCurrent._label2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Dim recipes As List";
mostCurrent._vvv3 = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 21;BA.debugLine="Private ACToolBarLight1 As ACToolBarLight";
mostCurrent._actoolbarlight1 = new de.amberhome.objects.appcompat.ACToolbarLightWrapper();
 //BA.debugLineNum = 22;BA.debugLine="End Sub";
return "";
}
public static String  _jslistview1_ongetview(int _position,com.salvadorjhai.widgets.JSListView.ItemViewLayout _itemlayout,boolean _forviewupdate) throws Exception{
com.salvadorjhai.widgets.JSListView.ItemViewLayout _iv = null;
anywheresoftware.b4a.objects.PanelWrapper _p = null;
anywheresoftware.b4a.objects.collections.Map _mapper = null;
uk.co.martinpearman.b4a.squareup.picasso.Picasso _picaso = null;
 //BA.debugLineNum = 64;BA.debugLine="Sub JSListView1_OnGetView(position As Int, itemLay";
 //BA.debugLineNum = 65;BA.debugLine="Dim iv As ItemViewLayout = itemLayout";
_iv = _itemlayout;
 //BA.debugLineNum = 67;BA.debugLine="If forViewUpdate = False Then";
if (_forviewupdate==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 68;BA.debugLine="Dim p As Panel";
_p = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 69;BA.debugLine="p.Initialize(\"\")";
_p.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 70;BA.debugLine="p.LoadLayout(\"TwoLineAndBitmap\")";
_p.LoadLayout("TwoLineAndBitmap",mostCurrent.activityBA);
 //BA.debugLineNum = 71;BA.debugLine="iv.Container = p";
_iv.setContainer((android.view.ViewGroup)(_p.getObject()));
 //BA.debugLineNum = 73;BA.debugLine="Label1.Tag = 1";
mostCurrent._label1.setTag((Object)(1));
 //BA.debugLineNum = 74;BA.debugLine="Label2.Tag = 2";
mostCurrent._label2.setTag((Object)(2));
 //BA.debugLineNum = 75;BA.debugLine="ImageView1.Tag = 3";
mostCurrent._imageview1.setTag((Object)(3));
 //BA.debugLineNum = 77;BA.debugLine="Label1.Width = 100%x - Label1.Left - 10dip";
mostCurrent._label1.setWidth((int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._label1.getLeft()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 78;BA.debugLine="Label2.Width = 100%x - Label2.Left - 10dip";
mostCurrent._label2.setWidth((int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._label2.getLeft()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 81;BA.debugLine="Label1.Typeface = Typeface.LoadFromAssets(\"Josef";
mostCurrent._label1.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("JosefinSans-Bold.ttf"));
 //BA.debugLineNum = 82;BA.debugLine="Label2.Typeface = Typeface.LoadFromAssets(\"Josef";
mostCurrent._label2.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("JosefinSans-SemiBoldItalic.ttf"));
 //BA.debugLineNum = 83;BA.debugLine="Label1.Gravity = Gravity.TOP";
mostCurrent._label1.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.TOP);
 //BA.debugLineNum = 84;BA.debugLine="Label2.Gravity = Gravity.TOP";
mostCurrent._label2.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.TOP);
 //BA.debugLineNum = 87;BA.debugLine="iv.Height = ImageView1.Top + ImageView1.Height +";
_iv.setHeight((int) (mostCurrent._imageview1.getTop()+mostCurrent._imageview1.getHeight()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 88;BA.debugLine="iv.Width = 100%x";
_iv.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 90;BA.debugLine="Label1.Height = 15dip";
mostCurrent._label1.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15)));
 //BA.debugLineNum = 91;BA.debugLine="Label2.Top = Label1.Top + Label1.Height";
mostCurrent._label2.setTop((int) (mostCurrent._label1.getTop()+mostCurrent._label1.getHeight()));
 //BA.debugLineNum = 92;BA.debugLine="Label2.Height = iv.Height - Label2.Top - 10dip";
mostCurrent._label2.setHeight((int) (_iv.getHeight()-mostCurrent._label2.getTop()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 };
 //BA.debugLineNum = 96;BA.debugLine="Dim mapper As Map = recipes.Get(position)";
_mapper = new anywheresoftware.b4a.objects.collections.Map();
_mapper.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(mostCurrent._vvv3.Get(_position)));
 //BA.debugLineNum = 98;BA.debugLine="Label1 = iv.findViewWithTag(1)";
mostCurrent._label1.setObject((android.widget.TextView)(_iv.findViewWithTag((Object)(1))));
 //BA.debugLineNum = 99;BA.debugLine="Label2 = iv.findViewWithTag(2)";
mostCurrent._label2.setObject((android.widget.TextView)(_iv.findViewWithTag((Object)(2))));
 //BA.debugLineNum = 100;BA.debugLine="ImageView1 = iv.findViewWithTag(3)";
mostCurrent._imageview1.setObject((android.widget.ImageView)(_iv.findViewWithTag((Object)(3))));
 //BA.debugLineNum = 102;BA.debugLine="Label1.Text = mapper.Get(\"title\")";
mostCurrent._label1.setText(BA.ObjectToCharSequence(_mapper.Get((Object)("title"))));
 //BA.debugLineNum = 103;BA.debugLine="Label2.Text = mapper.Get(\"description\")";
mostCurrent._label2.setText(BA.ObjectToCharSequence(_mapper.Get((Object)("description"))));
 //BA.debugLineNum = 105;BA.debugLine="Dim picaso As Picasso";
_picaso = new uk.co.martinpearman.b4a.squareup.picasso.Picasso();
 //BA.debugLineNum = 106;BA.debugLine="picaso.Initialize";
_picaso.Initialize(processBA);
 //BA.debugLineNum = 107;BA.debugLine="picaso.LoadUrl(mapper.Get(\"image\")).Resize(ImageV";
_picaso.LoadUrl(BA.ObjectToString(_mapper.Get((Object)("image")))).Resize(mostCurrent._imageview1.getWidth(),mostCurrent._imageview1.getHeight()).CenterCrop().IntoImageView((android.widget.ImageView)(mostCurrent._imageview1.getObject()));
 //BA.debugLineNum = 109;BA.debugLine="End Sub";
return "";
}
public static String  _jslistview1_onitemclick(com.salvadorjhai.widgets.JSListView.ItemViewLayout _view,int _position) throws Exception{
 //BA.debugLineNum = 111;BA.debugLine="Sub JSListView1_OnItemClick(view As ItemViewLayout";
 //BA.debugLineNum = 112;BA.debugLine="Log(GetType(view))";
anywheresoftware.b4a.keywords.Common.Log(anywheresoftware.b4a.keywords.Common.GetType((Object)(_view)));
 //BA.debugLineNum = 113;BA.debugLine="ToastMessageShow($\"JSListView1_OnItemClick @ posi";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("JSListView1_OnItemClick @ position: "+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_position))+"")),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 114;BA.debugLine="End Sub";
return "";
}
public static String  _jslistview1_onitemlongclick(com.salvadorjhai.widgets.JSListView.ItemViewLayout _view,int _position) throws Exception{
 //BA.debugLineNum = 116;BA.debugLine="Sub JSListView1_OnItemLongClick(view As ItemViewLa";
 //BA.debugLineNum = 117;BA.debugLine="Log(GetType(view))";
anywheresoftware.b4a.keywords.Common.Log(anywheresoftware.b4a.keywords.Common.GetType((Object)(_view)));
 //BA.debugLineNum = 118;BA.debugLine="ToastMessageShow($\"JSListView1_OnItemLongClick @";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("JSListView1_OnItemLongClick @ position: "+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_position))+"")),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 119;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 7;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 11;BA.debugLine="End Sub";
return "";
}

public boolean _onCreateOptionsMenu(android.view.Menu menu) {
	if (processBA.subExists("activity_createmenu")) {
		processBA.raiseEvent2(null, true, "activity_createmenu", false, new de.amberhome.objects.appcompat.ACMenuWrapper(menu));
		return true;
	}
	else
		return false;
}
}
