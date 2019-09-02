package b4a.example;


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

public class main extends Activity implements B4AActivity{
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
        mostCurrent = this;
		if (processBA == null) {
			processBA = new anywheresoftware.b4a.ShellBA(this.getApplicationContext(), null, null, "b4a.example", "b4a.example.main");
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
        processBA.setActivityPaused(true);
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(this, processBA, wl, false))
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
		activityBA = new BA(this, layout, processBA, "b4a.example", "b4a.example.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
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
			processBA.raiseEventFromUI(item.getTitle(), eventName + "_click");
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
        if (_activity == null)
            return;
        if (this != mostCurrent)
			return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        BA.LogInfo("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        if (mostCurrent != null)
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
            main mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (main) Resume **");
            if (mc != mostCurrent)
                return;
		    processBA.raiseEvent(mc._activity, "activity_resume", (Object[])null);
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



public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}
public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
return vis;}

private static BA killProgramHelper(BA ba) {
    if (ba == null)
        return null;
    anywheresoftware.b4a.BA.SharedProcessBA sharedProcessBA = ba.sharedProcessBA;
    if (sharedProcessBA == null || sharedProcessBA.activityBA == null)
        return null;
    return sharedProcessBA.activityBA.get();
}
public static void killProgram() {
     {
            Activity __a = null;
            if (main.previousOne != null) {
				__a = main.previousOne.get();
			}
            else {
                BA ba = killProgramHelper(main.mostCurrent == null ? null : main.mostCurrent.processBA);
                if (ba != null) __a = ba.activity;
            }
            if (__a != null)
				__a.finish();}

BA.applicationContext.stopService(new android.content.Intent(BA.applicationContext, starter.class));
}
public anywheresoftware.b4a.keywords.Common __c = null;
public static com.salvadorjhai.data.JSListAdapter _adapter = null;
public com.salvadorjhai.widgets.JSGridView _lv = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgavatar = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbldrugname = null;
public b4a.example.starter _starter = null;
public static String  _activity_create(boolean _firsttime) throws Exception{
RDebugUtils.currentModule="main";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_create", false))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "activity_create", new Object[] {_firsttime}));}
anywheresoftware.b4a.objects.collections.JSONParser _json = null;
anywheresoftware.b4a.objects.collections.List _datasource = null;
RDebugUtils.currentLine=131072;
 //BA.debugLineNum = 131072;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
RDebugUtils.currentLine=131075;
 //BA.debugLineNum = 131075;BA.debugLine="Dim json As JSONParser";
_json = new anywheresoftware.b4a.objects.collections.JSONParser();
RDebugUtils.currentLine=131076;
 //BA.debugLineNum = 131076;BA.debugLine="json.Initialize(File.ReadString(File.DirAssets, \"";
_json.Initialize(anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"drug_list.json"));
RDebugUtils.currentLine=131077;
 //BA.debugLineNum = 131077;BA.debugLine="Dim dataSource As List = json.NextArray";
_datasource = new anywheresoftware.b4a.objects.collections.List();
_datasource = _json.NextArray();
RDebugUtils.currentLine=131079;
 //BA.debugLineNum = 131079;BA.debugLine="adapter.Initialize(\"adapter\", dataSource)";
_adapter.Initialize(processBA,"adapter",_datasource);
RDebugUtils.currentLine=131082;
 //BA.debugLineNum = 131082;BA.debugLine="lv.Initialize(\"LV\")";
mostCurrent._lv.Initialize(mostCurrent.activityBA,"LV");
RDebugUtils.currentLine=131083;
 //BA.debugLineNum = 131083;BA.debugLine="Activity.AddView(lv, 0dip, 0dip, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._lv.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
RDebugUtils.currentLine=131086;
 //BA.debugLineNum = 131086;BA.debugLine="lv.Adapter = adapter";
mostCurrent._lv.setAdapter((Object)(_adapter));
RDebugUtils.currentLine=131087;
 //BA.debugLineNum = 131087;BA.debugLine="lv.setCacheColorHint(0)";
mostCurrent._lv.setCacheColorHint((int) (0));
RDebugUtils.currentLine=131088;
 //BA.debugLineNum = 131088;BA.debugLine="lv.setFastScrollEnabled(True)";
mostCurrent._lv.setFastScrollEnabled(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=131089;
 //BA.debugLineNum = 131089;BA.debugLine="lv.setNumColumns(3)";
mostCurrent._lv.setNumColumns((int) (3));
RDebugUtils.currentLine=131090;
 //BA.debugLineNum = 131090;BA.debugLine="lv.setVerticalSpacing(3dip)";
mostCurrent._lv.setVerticalSpacing(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (3)));
RDebugUtils.currentLine=131091;
 //BA.debugLineNum = 131091;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
RDebugUtils.currentModule="main";
RDebugUtils.currentLine=262144;
 //BA.debugLineNum = 262144;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
RDebugUtils.currentLine=262146;
 //BA.debugLineNum = 262146;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
RDebugUtils.currentModule="main";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_resume", false))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "activity_resume", null));}
RDebugUtils.currentLine=196608;
 //BA.debugLineNum = 196608;BA.debugLine="Sub Activity_Resume";
RDebugUtils.currentLine=196610;
 //BA.debugLineNum = 196610;BA.debugLine="End Sub";
return "";
}
public static String  _adapter_onbindview(int _position,com.salvadorjhai.data.JSViewHolder _viewholder) throws Exception{
RDebugUtils.currentModule="main";
if (Debug.shouldDelegate(mostCurrent.activityBA, "adapter_onbindview", false))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "adapter_onbindview", new Object[] {_position,_viewholder}));}
anywheresoftware.b4a.objects.PanelWrapper _p = null;
anywheresoftware.b4a.objects.collections.Map _mapdata = null;
uk.co.martinpearman.b4a.squareup.picasso.Picasso _picaso = null;
RDebugUtils.currentLine=917504;
 //BA.debugLineNum = 917504;BA.debugLine="Sub adapter_onBindView(position As Int, viewHolder";
RDebugUtils.currentLine=917505;
 //BA.debugLineNum = 917505;BA.debugLine="If viewHolder.Container = Null Then Return";
if (_viewholder.getContainer()== null) { 
if (true) return "";};
RDebugUtils.currentLine=917508;
 //BA.debugLineNum = 917508;BA.debugLine="Dim p As Panel = viewHolder.Container";
_p = new anywheresoftware.b4a.objects.PanelWrapper();
_p.setObject((android.view.ViewGroup)(_viewholder.getContainer()));
RDebugUtils.currentLine=917509;
 //BA.debugLineNum = 917509;BA.debugLine="If (position Mod 2) = 0 Then";
if ((_position%2)==0) { 
RDebugUtils.currentLine=917510;
 //BA.debugLineNum = 917510;BA.debugLine="p.Color = Colors.LightGray";
_p.setColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 }else {
RDebugUtils.currentLine=917512;
 //BA.debugLineNum = 917512;BA.debugLine="p.Color = Colors.White";
_p.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 };
RDebugUtils.currentLine=917515;
 //BA.debugLineNum = 917515;BA.debugLine="imgAvatar = viewHolder.findViewById(10)";
mostCurrent._imgavatar.setObject((android.widget.ImageView)(_viewholder.findViewById((int) (10))));
RDebugUtils.currentLine=917516;
 //BA.debugLineNum = 917516;BA.debugLine="lblDrugname = viewHolder.findViewById(20)";
mostCurrent._lbldrugname.setObject((android.widget.TextView)(_viewholder.findViewById((int) (20))));
RDebugUtils.currentLine=917519;
 //BA.debugLineNum = 917519;BA.debugLine="Dim mapData As Map = adapter.DataSource.Get(posit";
_mapdata = new anywheresoftware.b4a.objects.collections.Map();
_mapdata.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_adapter.getDataSource().Get(_position)));
RDebugUtils.currentLine=917520;
 //BA.debugLineNum = 917520;BA.debugLine="lblDrugname.Text = mapData.Get(\"drug_name\")";
mostCurrent._lbldrugname.setText(BA.ObjectToCharSequence(_mapdata.Get((Object)("drug_name"))));
RDebugUtils.currentLine=917522;
 //BA.debugLineNum = 917522;BA.debugLine="Dim picaso As Picasso";
_picaso = new uk.co.martinpearman.b4a.squareup.picasso.Picasso();
RDebugUtils.currentLine=917523;
 //BA.debugLineNum = 917523;BA.debugLine="picaso.Initialize";
_picaso.Initialize(processBA);
RDebugUtils.currentLine=917524;
 //BA.debugLineNum = 917524;BA.debugLine="picaso.LoadUrl(mapData.Get(\"image\")).Resize(imgAv";
_picaso.LoadUrl(BA.ObjectToString(_mapdata.Get((Object)("image")))).Resize(mostCurrent._imgavatar.getWidth(),mostCurrent._imgavatar.getHeight()).IntoImageView((android.widget.ImageView)(mostCurrent._imgavatar.getObject()));
RDebugUtils.currentLine=917526;
 //BA.debugLineNum = 917526;BA.debugLine="lblDrugname.Tag = position";
mostCurrent._lbldrugname.setTag((Object)(_position));
RDebugUtils.currentLine=917527;
 //BA.debugLineNum = 917527;BA.debugLine="End Sub";
return "";
}
public static String  _adapter_ongetview(int _position,com.salvadorjhai.data.JSViewHolder _viewholder) throws Exception{
RDebugUtils.currentModule="main";
if (Debug.shouldDelegate(mostCurrent.activityBA, "adapter_ongetview", false))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "adapter_ongetview", new Object[] {_position,_viewholder}));}
anywheresoftware.b4a.objects.PanelWrapper _p = null;
RDebugUtils.currentLine=720896;
 //BA.debugLineNum = 720896;BA.debugLine="Sub adapter_onGetView(position As Int, viewHolder";
RDebugUtils.currentLine=720898;
 //BA.debugLineNum = 720898;BA.debugLine="If viewHolder.Container	= Null Then";
if (_viewholder.getContainer()== null) { 
RDebugUtils.currentLine=720900;
 //BA.debugLineNum = 720900;BA.debugLine="Dim p As Panel";
_p = new anywheresoftware.b4a.objects.PanelWrapper();
RDebugUtils.currentLine=720901;
 //BA.debugLineNum = 720901;BA.debugLine="p.Initialize(\"\")";
_p.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=720902;
 //BA.debugLineNum = 720902;BA.debugLine="p.LoadLayout(\"layout\")";
_p.LoadLayout("layout",mostCurrent.activityBA);
RDebugUtils.currentLine=720903;
 //BA.debugLineNum = 720903;BA.debugLine="viewHolder.Initialize(p, 120dip, 100%x / 3)";
_viewholder.Initialize((android.view.ViewGroup)(_p.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (120)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)/(double)3));
RDebugUtils.currentLine=720906;
 //BA.debugLineNum = 720906;BA.debugLine="imgAvatar.SetLayout(0,0, viewHolder.Width, viewH";
mostCurrent._imgavatar.SetLayout((int) (0),(int) (0),_viewholder.getWidth(),_viewholder.getHeight());
RDebugUtils.currentLine=720907;
 //BA.debugLineNum = 720907;BA.debugLine="imgAvatar.Gravity = Gravity.FILL";
mostCurrent._imgavatar.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=720909;
 //BA.debugLineNum = 720909;BA.debugLine="lblDrugname.SetLayout(10dip, imgAvatar.Height-30";
mostCurrent._lbldrugname.SetLayout(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (mostCurrent._imgavatar.getHeight()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),(int) (_viewholder.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
RDebugUtils.currentLine=720910;
 //BA.debugLineNum = 720910;BA.debugLine="lblDrugname.Gravity = Bit.Or(Gravity.LEFT, Gravi";
mostCurrent._lbldrugname.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.LEFT,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
RDebugUtils.currentLine=720911;
 //BA.debugLineNum = 720911;BA.debugLine="lblDrugname.TextColor=Colors.Blue";
mostCurrent._lbldrugname.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Blue);
RDebugUtils.currentLine=720914;
 //BA.debugLineNum = 720914;BA.debugLine="viewHolder.setViewId(imgAvatar, 10)";
_viewholder.setViewId((android.view.View)(mostCurrent._imgavatar.getObject()),(int) (10));
RDebugUtils.currentLine=720915;
 //BA.debugLineNum = 720915;BA.debugLine="viewHolder.setViewId(lblDrugname, 20)";
_viewholder.setViewId((android.view.View)(mostCurrent._lbldrugname.getObject()),(int) (20));
 };
RDebugUtils.currentLine=720917;
 //BA.debugLineNum = 720917;BA.debugLine="End Sub";
return "";
}
public static String  _btnadd_click() throws Exception{
RDebugUtils.currentModule="main";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btnadd_click", false))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btnadd_click", null));}
anywheresoftware.b4a.objects.ButtonWrapper _btn = null;
int _position = 0;
anywheresoftware.b4a.objects.collections.Map _mapdata = null;
int _qty = 0;
RDebugUtils.currentLine=1048576;
 //BA.debugLineNum = 1048576;BA.debugLine="Sub btnAdd_Click";
RDebugUtils.currentLine=1048577;
 //BA.debugLineNum = 1048577;BA.debugLine="Dim btn As Button = Sender";
_btn = new anywheresoftware.b4a.objects.ButtonWrapper();
_btn.setObject((android.widget.Button)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
RDebugUtils.currentLine=1048578;
 //BA.debugLineNum = 1048578;BA.debugLine="Dim position As Int = btn.Tag";
_position = (int)(BA.ObjectToNumber(_btn.getTag()));
RDebugUtils.currentLine=1048580;
 //BA.debugLineNum = 1048580;BA.debugLine="Dim mapData As Map = adapter.getItem(position)";
_mapdata = new anywheresoftware.b4a.objects.collections.Map();
_mapdata.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_adapter.getItem(_position)));
RDebugUtils.currentLine=1048581;
 //BA.debugLineNum = 1048581;BA.debugLine="Dim qty As Int = mapData.Get(\"quantity\")";
_qty = (int)(BA.ObjectToNumber(_mapdata.Get((Object)("quantity"))));
RDebugUtils.currentLine=1048584;
 //BA.debugLineNum = 1048584;BA.debugLine="qty = qty + 1";
_qty = (int) (_qty+1);
RDebugUtils.currentLine=1048585;
 //BA.debugLineNum = 1048585;BA.debugLine="mapData.Put(\"quantity\", qty)";
_mapdata.Put((Object)("quantity"),(Object)(_qty));
RDebugUtils.currentLine=1048587;
 //BA.debugLineNum = 1048587;BA.debugLine="adapter.ItemUpdateAt(position, mapData)";
_adapter.ItemUpdateAt(_position,(Object)(_mapdata.getObject()));
RDebugUtils.currentLine=1048588;
 //BA.debugLineNum = 1048588;BA.debugLine="End Sub";
return "";
}
public static String  _btnminus_click() throws Exception{
RDebugUtils.currentModule="main";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btnminus_click", false))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btnminus_click", null));}
anywheresoftware.b4a.objects.ButtonWrapper _btn = null;
int _position = 0;
anywheresoftware.b4a.objects.collections.Map _mapdata = null;
int _qty = 0;
RDebugUtils.currentLine=983040;
 //BA.debugLineNum = 983040;BA.debugLine="Sub btnMinus_Click";
RDebugUtils.currentLine=983041;
 //BA.debugLineNum = 983041;BA.debugLine="Dim btn As Button = Sender";
_btn = new anywheresoftware.b4a.objects.ButtonWrapper();
_btn.setObject((android.widget.Button)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
RDebugUtils.currentLine=983042;
 //BA.debugLineNum = 983042;BA.debugLine="Dim position As Int = btn.Tag";
_position = (int)(BA.ObjectToNumber(_btn.getTag()));
RDebugUtils.currentLine=983044;
 //BA.debugLineNum = 983044;BA.debugLine="Dim mapData As Map = adapter.getItem(position)";
_mapdata = new anywheresoftware.b4a.objects.collections.Map();
_mapdata.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_adapter.getItem(_position)));
RDebugUtils.currentLine=983045;
 //BA.debugLineNum = 983045;BA.debugLine="Dim qty As Int = mapData.Get(\"quantity\")";
_qty = (int)(BA.ObjectToNumber(_mapdata.Get((Object)("quantity"))));
RDebugUtils.currentLine=983048;
 //BA.debugLineNum = 983048;BA.debugLine="qty = qty - 1";
_qty = (int) (_qty-1);
RDebugUtils.currentLine=983049;
 //BA.debugLineNum = 983049;BA.debugLine="mapData.Put(\"quantity\", qty)";
_mapdata.Put((Object)("quantity"),(Object)(_qty));
RDebugUtils.currentLine=983051;
 //BA.debugLineNum = 983051;BA.debugLine="adapter.ItemUpdateAt(position, mapData)";
_adapter.ItemUpdateAt(_position,(Object)(_mapdata.getObject()));
RDebugUtils.currentLine=983052;
 //BA.debugLineNum = 983052;BA.debugLine="End Sub";
return "";
}
public static String  _lbldrugname_click() throws Exception{
RDebugUtils.currentModule="main";
if (Debug.shouldDelegate(mostCurrent.activityBA, "lbldrugname_click", false))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "lbldrugname_click", null));}
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
int _position = 0;
RDebugUtils.currentLine=1114112;
 //BA.debugLineNum = 1114112;BA.debugLine="Sub lblDrugname_Click";
RDebugUtils.currentLine=1114113;
 //BA.debugLineNum = 1114113;BA.debugLine="Dim lbl As Label = Sender";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl.setObject((android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
RDebugUtils.currentLine=1114114;
 //BA.debugLineNum = 1114114;BA.debugLine="Dim position As Int = lbl.Tag";
_position = (int)(BA.ObjectToNumber(_lbl.getTag()));
RDebugUtils.currentLine=1114115;
 //BA.debugLineNum = 1114115;BA.debugLine="ToastMessageShow($\"You clicked on item index ${po";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("You clicked on item index "+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_position))+"")),anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=1114116;
 //BA.debugLineNum = 1114116;BA.debugLine="End Sub";
return "";
}
public static String  _lv_onitemclick(com.salvadorjhai.data.JSViewHolder _view,int _position) throws Exception{
RDebugUtils.currentModule="main";
if (Debug.shouldDelegate(mostCurrent.activityBA, "lv_onitemclick", false))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "lv_onitemclick", new Object[] {_view,_position}));}
RDebugUtils.currentLine=786432;
 //BA.debugLineNum = 786432;BA.debugLine="Sub LV_OnItemClick(view As JSViewHolder, position";
RDebugUtils.currentLine=786434;
 //BA.debugLineNum = 786434;BA.debugLine="ToastMessageShow($\"You clicked on item ${position";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("You clicked on item "+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_position))+"")),anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=786435;
 //BA.debugLineNum = 786435;BA.debugLine="End Sub";
return "";
}
public static String  _lv_onitemlongclick(com.salvadorjhai.data.JSViewHolder _view,int _position) throws Exception{
RDebugUtils.currentModule="main";
if (Debug.shouldDelegate(mostCurrent.activityBA, "lv_onitemlongclick", false))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "lv_onitemlongclick", new Object[] {_view,_position}));}
RDebugUtils.currentLine=851968;
 //BA.debugLineNum = 851968;BA.debugLine="Sub LV_OnItemLongClick(view As JSViewHolder, posit";
RDebugUtils.currentLine=851970;
 //BA.debugLineNum = 851970;BA.debugLine="ToastMessageShow($\"You long clicked on item ${pos";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("You long clicked on item "+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_position))+"")),anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=851971;
 //BA.debugLineNum = 851971;BA.debugLine="End Sub";
return "";
}
}