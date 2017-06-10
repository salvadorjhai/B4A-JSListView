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
		if (isFirst) {
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example", "b4a.example.main");
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
public static com.salvadorjhai.data.JSListAdapter _v5 = null;
public com.salvadorjhai.widgets.JSListView2 _v6 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnadd = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnminus = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgavatar = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbldrugname = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblquantity = null;
public b4a.example.starter _v7 = null;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
return vis;}
public static String  _activity_create(boolean _firsttime) throws Exception{
anywheresoftware.b4a.objects.collections.JSONParser _json = null;
anywheresoftware.b4a.objects.collections.List _datasource = null;
 //BA.debugLineNum = 33;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 36;BA.debugLine="Dim json As JSONParser";
_json = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 37;BA.debugLine="json.Initialize(File.ReadString(File.DirAssets, \"";
_json.Initialize(anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"drug_list.json"));
 //BA.debugLineNum = 38;BA.debugLine="Dim dataSource As List = json.NextArray";
_datasource = new anywheresoftware.b4a.objects.collections.List();
_datasource = _json.NextArray();
 //BA.debugLineNum = 40;BA.debugLine="adapter.Initialize(\"adapter\", dataSource)";
_v5.Initialize(processBA,"adapter",_datasource);
 //BA.debugLineNum = 43;BA.debugLine="lv.Initialize(\"LV\")";
mostCurrent._v6.Initialize(mostCurrent.activityBA,"LV");
 //BA.debugLineNum = 44;BA.debugLine="Activity.AddView(lv, 0dip, 0dip, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._v6.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 47;BA.debugLine="lv.Adapter = adapter";
mostCurrent._v6.setAdapter((Object)(_v5));
 //BA.debugLineNum = 48;BA.debugLine="lv.CacheColorHint = 0";
mostCurrent._v6.setCacheColorHint((int) (0));
 //BA.debugLineNum = 49;BA.debugLine="lv.FastScrollEnabled = True";
mostCurrent._v6.setFastScrollEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 50;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 56;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 58;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 52;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 54;BA.debugLine="End Sub";
return "";
}
public static String  _adapter_onbindview(int _position,com.salvadorjhai.data.JSViewHolder _viewholder) throws Exception{
anywheresoftware.b4a.objects.collections.Map _mapdata = null;
 //BA.debugLineNum = 93;BA.debugLine="Sub adapter_onBindView(position As Int, viewHolder";
 //BA.debugLineNum = 94;BA.debugLine="If viewHolder.Container = Null Then Return";
if (_viewholder.getContainer()== null) { 
if (true) return "";};
 //BA.debugLineNum = 97;BA.debugLine="imgAvatar = viewHolder.findViewById(10)";
mostCurrent._imgavatar.setObject((android.widget.ImageView)(_viewholder.findViewById((int) (10))));
 //BA.debugLineNum = 98;BA.debugLine="lblDrugname = viewHolder.findViewById(20)";
mostCurrent._lbldrugname.setObject((android.widget.TextView)(_viewholder.findViewById((int) (20))));
 //BA.debugLineNum = 99;BA.debugLine="lblQuantity = viewHolder.findViewById(30)";
mostCurrent._lblquantity.setObject((android.widget.TextView)(_viewholder.findViewById((int) (30))));
 //BA.debugLineNum = 100;BA.debugLine="btnAdd = viewHolder.findViewById(40)";
mostCurrent._btnadd.setObject((android.widget.Button)(_viewholder.findViewById((int) (40))));
 //BA.debugLineNum = 101;BA.debugLine="btnMinus = viewHolder.findViewById(50)";
mostCurrent._btnminus.setObject((android.widget.Button)(_viewholder.findViewById((int) (50))));
 //BA.debugLineNum = 104;BA.debugLine="Dim mapData As Map = adapter.DataSource.Get(posit";
_mapdata = new anywheresoftware.b4a.objects.collections.Map();
_mapdata.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_v5.getDataSource().Get(_position)));
 //BA.debugLineNum = 105;BA.debugLine="lblDrugname.Text = mapData.Get(\"drug_name\")";
mostCurrent._lbldrugname.setText(_mapdata.Get((Object)("drug_name")));
 //BA.debugLineNum = 106;BA.debugLine="lblQuantity.Text = mapData.Get(\"quantity\")";
mostCurrent._lblquantity.setText(_mapdata.Get((Object)("quantity")));
 //BA.debugLineNum = 107;BA.debugLine="btnAdd.Tag = position";
mostCurrent._btnadd.setTag((Object)(_position));
 //BA.debugLineNum = 108;BA.debugLine="btnMinus.Tag = position";
mostCurrent._btnminus.setTag((Object)(_position));
 //BA.debugLineNum = 109;BA.debugLine="lblDrugname.Tag = position";
mostCurrent._lbldrugname.setTag((Object)(_position));
 //BA.debugLineNum = 110;BA.debugLine="End Sub";
return "";
}
public static String  _adapter_ongetview(int _position,com.salvadorjhai.data.JSViewHolder _viewholder) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _p = null;
 //BA.debugLineNum = 60;BA.debugLine="Sub adapter_onGetView(position As Int, viewHolder";
 //BA.debugLineNum = 62;BA.debugLine="If viewHolder.Container	= Null Then";
if (_viewholder.getContainer()== null) { 
 //BA.debugLineNum = 64;BA.debugLine="Dim p As Panel";
_p = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 65;BA.debugLine="p.Initialize(\"\")";
_p.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 66;BA.debugLine="p.LoadLayout(\"layout\")";
_p.LoadLayout("layout",mostCurrent.activityBA);
 //BA.debugLineNum = 67;BA.debugLine="viewHolder.Initialize(p, 110dip, 100%x)";
_viewholder.Initialize((android.view.ViewGroup)(_p.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (110)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 70;BA.debugLine="lblDrugname.Width = 100%x - lblDrugname.Left - 1";
mostCurrent._lbldrugname.setWidth((int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._lbldrugname.getLeft()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 71;BA.debugLine="lblDrugname.Gravity = Bit.Or(Gravity.LEFT, Gravi";
mostCurrent._lbldrugname.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.LEFT,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 72;BA.debugLine="lblQuantity.Gravity = Gravity.CENTER";
mostCurrent._lblquantity.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 75;BA.debugLine="viewHolder.setViewId(imgAvatar, 10)";
_viewholder.setViewId((android.view.View)(mostCurrent._imgavatar.getObject()),(int) (10));
 //BA.debugLineNum = 76;BA.debugLine="viewHolder.setViewId(lblDrugname, 20)";
_viewholder.setViewId((android.view.View)(mostCurrent._lbldrugname.getObject()),(int) (20));
 //BA.debugLineNum = 77;BA.debugLine="viewHolder.setViewId(lblQuantity, 30)";
_viewholder.setViewId((android.view.View)(mostCurrent._lblquantity.getObject()),(int) (30));
 //BA.debugLineNum = 78;BA.debugLine="viewHolder.setViewId(btnAdd, 40)";
_viewholder.setViewId((android.view.View)(mostCurrent._btnadd.getObject()),(int) (40));
 //BA.debugLineNum = 79;BA.debugLine="viewHolder.setViewId(btnMinus, 50)";
_viewholder.setViewId((android.view.View)(mostCurrent._btnminus.getObject()),(int) (50));
 };
 //BA.debugLineNum = 81;BA.debugLine="End Sub";
return "";
}
public static String  _btnadd_click() throws Exception{
anywheresoftware.b4a.objects.ButtonWrapper _btn = null;
int _position = 0;
anywheresoftware.b4a.objects.collections.Map _mapdata = null;
int _qty = 0;
 //BA.debugLineNum = 127;BA.debugLine="Sub btnAdd_Click";
 //BA.debugLineNum = 128;BA.debugLine="Dim btn As Button = Sender";
_btn = new anywheresoftware.b4a.objects.ButtonWrapper();
_btn.setObject((android.widget.Button)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 129;BA.debugLine="Dim position As Int = btn.Tag";
_position = (int)(BA.ObjectToNumber(_btn.getTag()));
 //BA.debugLineNum = 131;BA.debugLine="Dim mapData As Map = adapter.getItem(position)";
_mapdata = new anywheresoftware.b4a.objects.collections.Map();
_mapdata.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_v5.getItem(_position)));
 //BA.debugLineNum = 132;BA.debugLine="Dim qty As Int = mapData.Get(\"quantity\")";
_qty = (int)(BA.ObjectToNumber(_mapdata.Get((Object)("quantity"))));
 //BA.debugLineNum = 135;BA.debugLine="qty = qty + 1";
_qty = (int) (_qty+1);
 //BA.debugLineNum = 136;BA.debugLine="mapData.Put(\"quantity\", qty)";
_mapdata.Put((Object)("quantity"),(Object)(_qty));
 //BA.debugLineNum = 138;BA.debugLine="adapter.ItemUpdateAt(position, mapData)";
_v5.ItemUpdateAt(_position,(Object)(_mapdata.getObject()));
 //BA.debugLineNum = 139;BA.debugLine="End Sub";
return "";
}
public static String  _btnminus_click() throws Exception{
anywheresoftware.b4a.objects.ButtonWrapper _btn = null;
int _position = 0;
anywheresoftware.b4a.objects.collections.Map _mapdata = null;
int _qty = 0;
 //BA.debugLineNum = 113;BA.debugLine="Sub btnMinus_Click";
 //BA.debugLineNum = 114;BA.debugLine="Dim btn As Button = Sender";
_btn = new anywheresoftware.b4a.objects.ButtonWrapper();
_btn.setObject((android.widget.Button)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 115;BA.debugLine="Dim position As Int = btn.Tag";
_position = (int)(BA.ObjectToNumber(_btn.getTag()));
 //BA.debugLineNum = 117;BA.debugLine="Dim mapData As Map = adapter.getItem(position)";
_mapdata = new anywheresoftware.b4a.objects.collections.Map();
_mapdata.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_v5.getItem(_position)));
 //BA.debugLineNum = 118;BA.debugLine="Dim qty As Int = mapData.Get(\"quantity\")";
_qty = (int)(BA.ObjectToNumber(_mapdata.Get((Object)("quantity"))));
 //BA.debugLineNum = 121;BA.debugLine="qty = qty - 1";
_qty = (int) (_qty-1);
 //BA.debugLineNum = 122;BA.debugLine="mapData.Put(\"quantity\", qty)";
_mapdata.Put((Object)("quantity"),(Object)(_qty));
 //BA.debugLineNum = 124;BA.debugLine="adapter.ItemUpdateAt(position, mapData)";
_v5.ItemUpdateAt(_position,(Object)(_mapdata.getObject()));
 //BA.debugLineNum = 125;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 21;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 24;BA.debugLine="Dim lv As JSListView2";
mostCurrent._v6 = new com.salvadorjhai.widgets.JSListView2();
 //BA.debugLineNum = 26;BA.debugLine="Private btnAdd As Button";
mostCurrent._btnadd = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private btnMinus As Button";
mostCurrent._btnminus = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private imgAvatar As ImageView";
mostCurrent._imgavatar = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private lblDrugname As Label";
mostCurrent._lbldrugname = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private lblQuantity As Label";
mostCurrent._lblquantity = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 31;BA.debugLine="End Sub";
return "";
}
public static String  _lbldrugname_click() throws Exception{
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
int _position = 0;
 //BA.debugLineNum = 141;BA.debugLine="Sub lblDrugname_Click";
 //BA.debugLineNum = 142;BA.debugLine="Dim lbl As Label = Sender";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl.setObject((android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 143;BA.debugLine="Dim position As Int = lbl.Tag";
_position = (int)(BA.ObjectToNumber(_lbl.getTag()));
 //BA.debugLineNum = 144;BA.debugLine="ToastMessageShow($\"You clicked on item index ${po";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(("You clicked on item index "+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_position))+""),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 145;BA.debugLine="End Sub";
return "";
}
public static String  _lv_onitemclick(com.salvadorjhai.data.JSViewHolder _view,int _position) throws Exception{
 //BA.debugLineNum = 83;BA.debugLine="Sub LV_OnItemClick(view As JSViewHolder, position";
 //BA.debugLineNum = 85;BA.debugLine="ToastMessageShow($\"You clicked on item ${position";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(("You clicked on item "+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_position))+""),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 86;BA.debugLine="End Sub";
return "";
}
public static String  _lv_onitemlongclick(com.salvadorjhai.data.JSViewHolder _view,int _position) throws Exception{
 //BA.debugLineNum = 88;BA.debugLine="Sub LV_OnItemLongClick(view As JSViewHolder, posit";
 //BA.debugLineNum = 90;BA.debugLine="ToastMessageShow($\"You long clicked on item ${pos";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(("You long clicked on item "+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_position))+""),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 91;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        main._process_globals();
starter._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 15;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 18;BA.debugLine="Dim adapter As JSListAdapter";
_v5 = new com.salvadorjhai.data.JSListAdapter();
 //BA.debugLineNum = 19;BA.debugLine="End Sub";
return "";
}
}
