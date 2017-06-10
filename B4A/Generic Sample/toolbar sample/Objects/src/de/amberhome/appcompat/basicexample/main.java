package de.amberhome.appcompat.basicexample;


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

public class main extends android.support.v7.app.AppCompatActivity implements B4AActivity{
	public static main mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = true;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isFirst) {
			processBA = new BA(this.getApplicationContext(), null, null, "de.amberhome.appcompat.basicexample", "de.amberhome.appcompat.basicexample.main");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (main).");
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
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(processBA, wl, true))
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
		activityBA = new BA(this, layout, processBA, "de.amberhome.appcompat.basicexample", "de.amberhome.appcompat.basicexample.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "de.amberhome.appcompat.basicexample.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (main) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (main) Resume **");
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
			processBA.raiseEvent(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return main.class;
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
        BA.LogInfo("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (main) Resume **");
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
        Object[] o;
        if (permissions.length > 0)
            o = new Object[] {permissions[0], grantResults[0] == 0};
        else
            o = new Object[] {"", false};
        processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public de.amberhome.objects.appcompat.ACSearchViewWrapper _v7 = null;
public de.amberhome.objects.appcompat.ACMenuItemWrapper _v0 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label2 = null;
public anywheresoftware.b4a.objects.collections.List _v6 = null;
public anywheresoftware.b4a.objects.collections.List _vv2 = null;
public com.salvadorjhai.widgets.JSListView _jslistview1 = null;
public de.amberhome.objects.appcompat.ACToolbarDarkWrapper _actoolbardark1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnviewdetails = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnremovedetails = null;
public de.amberhome.appcompat.basicexample.detailedview _vv1 = null;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
vis = vis | (detailedview.mostCurrent != null);
return vis;}
public static String  _activity_create(boolean _firsttime) throws Exception{
anywheresoftware.b4a.objects.collections.JSONParser _js = null;
 //BA.debugLineNum = 52;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 54;BA.debugLine="Activity.LoadLayout(\"layout2\")";
mostCurrent._activity.LoadLayout("layout2",mostCurrent.activityBA);
 //BA.debugLineNum = 56;BA.debugLine="Dim js As JSONParser";
_js = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 57;BA.debugLine="js.Initialize(File.ReadString(File.DirAssets, \"MO";
_js.Initialize(anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"MOCK_DATA.json"));
 //BA.debugLineNum = 58;BA.debugLine="recipes = js.NextArray";
mostCurrent._v6 = _js.NextArray();
 //BA.debugLineNum = 60;BA.debugLine="JSListView1.DataSource = recipes";
mostCurrent._jslistview1.setDataSource(mostCurrent._v6);
 //BA.debugLineNum = 61;BA.debugLine="JSListView1.FastScrollEnabled = True";
mostCurrent._jslistview1.setFastScrollEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 63;BA.debugLine="End Sub";
return "";
}
public static String  _activity_createmenu(de.amberhome.objects.appcompat.ACMenuWrapper _menu) throws Exception{
 //BA.debugLineNum = 74;BA.debugLine="Sub Activity_CreateMenu(Menu As ACMenu)";
 //BA.debugLineNum = 75;BA.debugLine="sv.Initialize2(\"Search\", sv.THEME_DARK)";
mostCurrent._v7.Initialize2(mostCurrent.activityBA,"Search",mostCurrent._v7.THEME_DARK);
 //BA.debugLineNum = 76;BA.debugLine="sv.IconifiedByDefault = True";
mostCurrent._v7.setIconifiedByDefault(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 79;BA.debugLine="Menu.Clear";
_menu.Clear();
 //BA.debugLineNum = 82;BA.debugLine="si = Menu.Add2(1, 1, \"Search\", Null)";
mostCurrent._v0 = _menu.Add2((int) (1),(int) (1),"Search",(android.graphics.drawable.Drawable)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 83;BA.debugLine="si.SearchView = sv";
mostCurrent._v0.setSearchView(mostCurrent._v7);
 //BA.debugLineNum = 87;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 69;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 71;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 65;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 67;BA.debugLine="End Sub";
return "";
}
public static String  _actoolbardark1_menuitemclick(de.amberhome.objects.appcompat.ACMenuItemWrapper _item) throws Exception{
 //BA.debugLineNum = 210;BA.debugLine="Sub ACToolBarDark1_MenuItemClick (Item As ACMenuIt";
 //BA.debugLineNum = 212;BA.debugLine="End Sub";
return "";
}
public static String  _actoolbardark1_navigationitemclick() throws Exception{
 //BA.debugLineNum = 206;BA.debugLine="Sub ACToolBarDark1_NavigationItemClick";
 //BA.debugLineNum = 208;BA.debugLine="End Sub";
return "";
}
public static String  _btnremovedetails_click() throws Exception{
anywheresoftware.b4a.objects.ButtonWrapper _btn = null;
long _index = 0L;
 //BA.debugLineNum = 220;BA.debugLine="Sub btnRemoveDetails_Click";
 //BA.debugLineNum = 221;BA.debugLine="Dim btn As Button = Sender";
_btn = new anywheresoftware.b4a.objects.ButtonWrapper();
_btn.setObject((android.widget.Button)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 222;BA.debugLine="Dim index As Long = btn.Tag";
_index = BA.ObjectToLongNumber(_btn.getTag());
 //BA.debugLineNum = 223;BA.debugLine="JSListView1.ItemRemoveAt(index)";
mostCurrent._jslistview1.ItemRemoveAt((int) (_index));
 //BA.debugLineNum = 224;BA.debugLine="End Sub";
return "";
}
public static String  _btnviewdetails_click() throws Exception{
anywheresoftware.b4a.objects.ButtonWrapper _btn = null;
 //BA.debugLineNum = 214;BA.debugLine="Sub btnViewDetails_Click";
 //BA.debugLineNum = 215;BA.debugLine="Dim btn As Button = Sender";
_btn = new anywheresoftware.b4a.objects.ButtonWrapper();
_btn.setObject((android.widget.Button)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 216;BA.debugLine="DetailedView.mapper = btn.Tag";
mostCurrent._vv1._v5.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_btn.getTag()));
 //BA.debugLineNum = 217;BA.debugLine="StartActivity(DetailedView)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._vv1.getObject()));
 //BA.debugLineNum = 218;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 23;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 26;BA.debugLine="Private sv As ACSearchView";
mostCurrent._v7 = new de.amberhome.objects.appcompat.ACSearchViewWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private si As ACMenuItem";
mostCurrent._v0 = new de.amberhome.objects.appcompat.ACMenuItemWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private ImageView1 As ImageView";
mostCurrent._imageview1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private Label1 As Label";
mostCurrent._label1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private Label2 As Label";
mostCurrent._label2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Dim recipes As List";
mostCurrent._v6 = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 33;BA.debugLine="Dim filtered As List";
mostCurrent._vv2 = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 34;BA.debugLine="Private JSListView1 As JSListView";
mostCurrent._jslistview1 = new com.salvadorjhai.widgets.JSListView();
 //BA.debugLineNum = 35;BA.debugLine="Private ACToolBarDark1 As ACToolBarDark";
mostCurrent._actoolbardark1 = new de.amberhome.objects.appcompat.ACToolbarDarkWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private btnViewDetails As Button";
mostCurrent._btnviewdetails = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private btnRemoveDetails As Button";
mostCurrent._btnremovedetails = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 38;BA.debugLine="End Sub";
return "";
}
public static String  _imageview1_click() throws Exception{
anywheresoftware.b4a.objects.ImageViewWrapper _btn = null;
 //BA.debugLineNum = 226;BA.debugLine="Sub ImageView1_Click";
 //BA.debugLineNum = 227;BA.debugLine="Dim btn As ImageView = Sender";
_btn = new anywheresoftware.b4a.objects.ImageViewWrapper();
_btn.setObject((android.widget.ImageView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 228;BA.debugLine="DetailedView.mapper = btn.Tag";
mostCurrent._vv1._v5.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_btn.getTag()));
 //BA.debugLineNum = 229;BA.debugLine="StartActivity(DetailedView)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._vv1.getObject()));
 //BA.debugLineNum = 230;BA.debugLine="End Sub";
return "";
}
public static String  _jslistview1_ongetview(int _position,com.salvadorjhai.widgets.JSListView.ItemViewLayout _itemlayout,boolean _forviewupdate) throws Exception{
com.salvadorjhai.widgets.JSListView.ItemViewLayout _iv = null;
anywheresoftware.b4a.objects.PanelWrapper _p = null;
anywheresoftware.b4a.objects.collections.Map _mapper = null;
uk.co.martinpearman.b4a.squareup.picasso.Picasso _picaso = null;
 //BA.debugLineNum = 120;BA.debugLine="Sub JSListView1_OnGetView(position As Int, itemLay";
 //BA.debugLineNum = 121;BA.debugLine="Try";
try { //BA.debugLineNum = 122;BA.debugLine="Dim iv As ItemViewLayout = itemLayout";
_iv = _itemlayout;
 //BA.debugLineNum = 124;BA.debugLine="Dim p As Panel";
_p = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 125;BA.debugLine="If forViewUpdate = False Then";
if (_forviewupdate==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 126;BA.debugLine="p.Initialize(\"\")";
_p.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 127;BA.debugLine="p.LoadLayout(\"twolineandbitmapbuttons\")";
_p.LoadLayout("twolineandbitmapbuttons",mostCurrent.activityBA);
 //BA.debugLineNum = 128;BA.debugLine="iv.Container = p";
_iv.setContainer((android.view.ViewGroup)(_p.getObject()));
 //BA.debugLineNum = 131;BA.debugLine="iv.setViewId(Label1, 1)";
_iv.setViewId((android.view.View)(mostCurrent._label1.getObject()),(int) (1));
 //BA.debugLineNum = 132;BA.debugLine="iv.setViewId(Label2, 2)";
_iv.setViewId((android.view.View)(mostCurrent._label2.getObject()),(int) (2));
 //BA.debugLineNum = 133;BA.debugLine="iv.setViewId(ImageView1, 3)";
_iv.setViewId((android.view.View)(mostCurrent._imageview1.getObject()),(int) (3));
 //BA.debugLineNum = 134;BA.debugLine="iv.setViewId(btnViewDetails, 4)";
_iv.setViewId((android.view.View)(mostCurrent._btnviewdetails.getObject()),(int) (4));
 //BA.debugLineNum = 135;BA.debugLine="iv.setViewId(btnRemoveDetails, 5)";
_iv.setViewId((android.view.View)(mostCurrent._btnremovedetails.getObject()),(int) (5));
 //BA.debugLineNum = 137;BA.debugLine="Label1.Width = 100%x - Label1.Left - 10dip";
mostCurrent._label1.setWidth((int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._label1.getLeft()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 138;BA.debugLine="Label2.Width = 100%x - Label2.Left - 10dip";
mostCurrent._label2.setWidth((int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._label2.getLeft()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 140;BA.debugLine="Label1.Gravity = Gravity.TOP";
mostCurrent._label1.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.TOP);
 //BA.debugLineNum = 141;BA.debugLine="Label2.Gravity = Gravity.TOP";
mostCurrent._label2.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.TOP);
 //BA.debugLineNum = 144;BA.debugLine="iv.Height = ImageView1.Top + ImageView1.Height";
_iv.setHeight((int) (mostCurrent._imageview1.getTop()+mostCurrent._imageview1.getHeight()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 145;BA.debugLine="iv.Width = 100%x";
_iv.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 147;BA.debugLine="Label1.Height = 15dip";
mostCurrent._label1.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15)));
 //BA.debugLineNum = 148;BA.debugLine="Label2.Top = Label1.Top + Label1.Height";
mostCurrent._label2.setTop((int) (mostCurrent._label1.getTop()+mostCurrent._label1.getHeight()));
 //BA.debugLineNum = 149;BA.debugLine="Label2.Height = iv.Height - Label2.Top - 10dip";
mostCurrent._label2.setHeight((int) (_iv.getHeight()-mostCurrent._label2.getTop()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 152;BA.debugLine="iv.Height = btnRemoveDetails.Top + btnRemoveDet";
_iv.setHeight((int) (mostCurrent._btnremovedetails.getTop()+mostCurrent._btnremovedetails.getHeight()));
 };
 //BA.debugLineNum = 156;BA.debugLine="Dim mapper As Map = JSListView1.DataSource.Get(p";
_mapper = new anywheresoftware.b4a.objects.collections.Map();
_mapper.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(mostCurrent._jslistview1.getDataSource().Get(_position)));
 //BA.debugLineNum = 159;BA.debugLine="Label1 = iv.findViewById(1)";
mostCurrent._label1.setObject((android.widget.TextView)(_iv.findViewById((int) (1))));
 //BA.debugLineNum = 160;BA.debugLine="Label2 = iv.findViewById(2)";
mostCurrent._label2.setObject((android.widget.TextView)(_iv.findViewById((int) (2))));
 //BA.debugLineNum = 161;BA.debugLine="ImageView1 = iv.findViewById(3)";
mostCurrent._imageview1.setObject((android.widget.ImageView)(_iv.findViewById((int) (3))));
 //BA.debugLineNum = 162;BA.debugLine="btnViewDetails = iv.findViewById(4)";
mostCurrent._btnviewdetails.setObject((android.widget.Button)(_iv.findViewById((int) (4))));
 //BA.debugLineNum = 163;BA.debugLine="btnRemoveDetails = iv.findViewById(5)";
mostCurrent._btnremovedetails.setObject((android.widget.Button)(_iv.findViewById((int) (5))));
 //BA.debugLineNum = 166;BA.debugLine="Label1.Text =  $\"(${mapper.Get(\"id\")}) ${mapper.";
mostCurrent._label1.setText((Object)(("("+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",_mapper.Get((Object)("id")))+") "+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",_mapper.Get((Object)("fullname")))+" - "+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",_mapper.Get((Object)("job_title")))+"")));
 //BA.debugLineNum = 167;BA.debugLine="Label2.Text = mapper.Get(\"slogan\")";
mostCurrent._label2.setText(_mapper.Get((Object)("slogan")));
 //BA.debugLineNum = 170;BA.debugLine="ImageView1.Tag = mapper";
mostCurrent._imageview1.setTag((Object)(_mapper.getObject()));
 //BA.debugLineNum = 171;BA.debugLine="btnViewDetails.Tag = mapper";
mostCurrent._btnviewdetails.setTag((Object)(_mapper.getObject()));
 //BA.debugLineNum = 172;BA.debugLine="btnRemoveDetails.Tag = position";
mostCurrent._btnremovedetails.setTag((Object)(_position));
 //BA.debugLineNum = 180;BA.debugLine="Dim picaso As Picasso";
_picaso = new uk.co.martinpearman.b4a.squareup.picasso.Picasso();
 //BA.debugLineNum = 181;BA.debugLine="picaso.Initialize";
_picaso.Initialize(processBA);
 //BA.debugLineNum = 182;BA.debugLine="picaso.LoadUrl(mapper.Get(\"avatar\")).Resize(Imag";
_picaso.LoadUrl(BA.ObjectToString(_mapper.Get((Object)("avatar")))).Resize(mostCurrent._imageview1.getWidth(),mostCurrent._imageview1.getHeight()).CenterCrop().IntoImageView((android.widget.ImageView)(mostCurrent._imageview1.getObject()));
 } 
       catch (Exception e39) {
			processBA.setLastException(e39); //BA.debugLineNum = 186;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)));
 };
 //BA.debugLineNum = 188;BA.debugLine="End Sub";
return "";
}
public static String  _jslistview1_onitemclick(com.salvadorjhai.widgets.JSListView.ItemViewLayout _view,int _position) throws Exception{
 //BA.debugLineNum = 190;BA.debugLine="Sub JSListView1_OnItemClick(view As ItemViewLayout";
 //BA.debugLineNum = 191;BA.debugLine="Log(GetType(view))";
anywheresoftware.b4a.keywords.Common.Log(anywheresoftware.b4a.keywords.Common.GetType((Object)(_view)));
 //BA.debugLineNum = 193;BA.debugLine="ToastMessageShow($\"JSListView1_OnItemClick @ posi";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(("JSListView1_OnItemClick @ position: "+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_position))+""),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 198;BA.debugLine="End Sub";
return "";
}
public static String  _jslistview1_onitemlongclick(com.salvadorjhai.widgets.JSListView.ItemViewLayout _view,int _position) throws Exception{
 //BA.debugLineNum = 200;BA.debugLine="Sub JSListView1_OnItemLongClick(view As ItemViewLa";
 //BA.debugLineNum = 201;BA.debugLine="Log(GetType(view))";
anywheresoftware.b4a.keywords.Common.Log(anywheresoftware.b4a.keywords.Common.GetType((Object)(_view)));
 //BA.debugLineNum = 202;BA.debugLine="ToastMessageShow($\"JSListView1_OnItemLongClick @";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(("JSListView1_OnItemLongClick @ position: "+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_position))+""),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 203;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        main._process_globals();
detailedview._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 17;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 21;BA.debugLine="End Sub";
return "";
}
public static String  _search_closed() throws Exception{
 //BA.debugLineNum = 112;BA.debugLine="Sub Search_Closed";
 //BA.debugLineNum = 113;BA.debugLine="Log(\"SearchView closed\")";
anywheresoftware.b4a.keywords.Common.Log("SearchView closed");
 //BA.debugLineNum = 114;BA.debugLine="End Sub";
return "";
}
public static String  _search_querychanged(String _query) throws Exception{
 //BA.debugLineNum = 116;BA.debugLine="Sub Search_QueryChanged (Query As String)";
 //BA.debugLineNum = 117;BA.debugLine="Log(\"Query changed: \" & Query)";
anywheresoftware.b4a.keywords.Common.Log("Query changed: "+_query);
 //BA.debugLineNum = 118;BA.debugLine="End Sub";
return "";
}
public static String  _search_querysubmitted(String _query) throws Exception{
int _i = 0;
anywheresoftware.b4a.objects.collections.Map _mapper = null;
String _value = "";
 //BA.debugLineNum = 90;BA.debugLine="Sub Search_QuerySubmitted (Query As String)";
 //BA.debugLineNum = 91;BA.debugLine="filtered.Initialize";
mostCurrent._vv2.Initialize();
 //BA.debugLineNum = 93;BA.debugLine="For i = 0 To recipes.Size - 1";
{
final int step2 = 1;
final int limit2 = (int) (mostCurrent._v6.getSize()-1);
for (_i = (int) (0) ; (step2 > 0 && _i <= limit2) || (step2 < 0 && _i >= limit2); _i = ((int)(0 + _i + step2)) ) {
 //BA.debugLineNum = 94;BA.debugLine="Dim mapper As Map = recipes.Get(i)";
_mapper = new anywheresoftware.b4a.objects.collections.Map();
_mapper.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(mostCurrent._v6.Get(_i)));
 //BA.debugLineNum = 95;BA.debugLine="Dim value As String = mapper.Get(\"fullname\")";
_value = BA.ObjectToString(_mapper.Get((Object)("fullname")));
 //BA.debugLineNum = 96;BA.debugLine="If value.ToLowerCase.Contains(Query.ToLowerCase)";
if (_value.toLowerCase().contains(_query.toLowerCase())) { 
 //BA.debugLineNum = 97;BA.debugLine="filtered.Add(mapper)";
mostCurrent._vv2.Add((Object)(_mapper.getObject()));
 };
 }
};
 //BA.debugLineNum = 100;BA.debugLine="If filtered.Size <> 0 Then";
if (mostCurrent._vv2.getSize()!=0) { 
 //BA.debugLineNum = 101;BA.debugLine="JSListView1.DataSource = filtered";
mostCurrent._jslistview1.setDataSource(mostCurrent._vv2);
 }else {
 //BA.debugLineNum = 103;BA.debugLine="JSListView1.DataSource = recipes";
mostCurrent._jslistview1.setDataSource(mostCurrent._v6);
 };
 //BA.debugLineNum = 106;BA.debugLine="sv.Iconfied = True";
mostCurrent._v7.setIconfied(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 107;BA.debugLine="si.ItemCollapsed = True";
mostCurrent._v0.setItemCollapsed(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 109;BA.debugLine="Log(\"Search for '\" & Query & \"'\")";
anywheresoftware.b4a.keywords.Common.Log("Search for '"+_query+"'");
 //BA.debugLineNum = 110;BA.debugLine="End Sub";
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
