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

public class customtemplate2 extends android.support.v7.app.AppCompatActivity implements B4AActivity{
	public static customtemplate2 mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example.jslistview", "b4a.example.jslistview.customtemplate2");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (customtemplate2).");
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
		activityBA = new BA(this, layout, processBA, "b4a.example.jslistview", "b4a.example.jslistview.customtemplate2");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.jslistview.customtemplate2", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (customtemplate2) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (customtemplate2) Resume **");
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
		return customtemplate2.class;
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
        BA.LogInfo("** Activity (customtemplate2) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (customtemplate2) Resume **");
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
public com.salvadorjhai.data.JSListAdapter _vvv1 = null;
public com.salvadorjhai.widgets.JSListView2 _jslistview1 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label3 = null;
public anywheresoftware.b4a.objects.collections.List _v5 = null;
public b4a.example.jslistview.main _vvv2 = null;
public b4a.example.jslistview.twolineandbitmap _vv2 = null;
public b4a.example.jslistview.customtwolineandbitmap _vv3 = null;
public b4a.example.jslistview.customtemplate4 _vv6 = null;
public b4a.example.jslistview.singlelineactivity _v7 = null;
public b4a.example.jslistview.customtemplate3 _vv5 = null;
public b4a.example.jslistview.toolbartestactivity _vv7 = null;
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
anywheresoftware.b4a.objects.drawable.GradientDrawable _cd = null;
int[] _c = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd2 = null;
 //BA.debugLineNum = 25;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 27;BA.debugLine="Activity.LoadLayout(\"4\")";
mostCurrent._activity.LoadLayout("4",mostCurrent.activityBA);
 //BA.debugLineNum = 28;BA.debugLine="Activity.Title = \"Custom Listview 2\"";
mostCurrent._activity.setTitle(BA.ObjectToCharSequence("Custom Listview 2"));
 //BA.debugLineNum = 30;BA.debugLine="Dim js As JSONParser";
_js = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 31;BA.debugLine="js.Initialize(File.ReadString(File.DirAssets, \"MO";
_js.Initialize(anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"MOCK_DATA.json"));
 //BA.debugLineNum = 32;BA.debugLine="Dim data As List = js.NextArray";
mostCurrent._v5 = new anywheresoftware.b4a.objects.collections.List();
mostCurrent._v5 = _js.NextArray();
 //BA.debugLineNum = 33;BA.debugLine="Adapter.Initialize(\"Adapter\", data)";
mostCurrent._vvv1.Initialize(processBA,"Adapter",mostCurrent._v5);
 //BA.debugLineNum = 36;BA.debugLine="Dim cd As GradientDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.GradientDrawable();
 //BA.debugLineNum = 37;BA.debugLine="Dim c() As Int = Array As Int(Colors.rgb(252, 237";
_c = new int[]{anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (252),(int) (237),(int) (242)),(int) (0xffe91e63),anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (252),(int) (237),(int) (242))};
 //BA.debugLineNum = 38;BA.debugLine="cd.Initialize(\"TR_BL\", c)";
_cd.Initialize(BA.getEnumFromString(android.graphics.drawable.GradientDrawable.Orientation.class,"TR_BL"),_c);
 //BA.debugLineNum = 40;BA.debugLine="JSListView1.Divider = cd";
mostCurrent._jslistview1.setDivider((android.graphics.drawable.Drawable)(_cd.getObject()));
 //BA.debugLineNum = 41;BA.debugLine="JSListView1.DividerHeight = 3dip";
mostCurrent._jslistview1.setDividerHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (3)));
 //BA.debugLineNum = 44;BA.debugLine="Dim cd2 As ColorDrawable";
_cd2 = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 45;BA.debugLine="cd2.Initialize(0xFFE91E63, 0)";
_cd2.Initialize((int) (0xffe91e63),(int) (0));
 //BA.debugLineNum = 46;BA.debugLine="JSListView1.OverscrollHeader = cd";
mostCurrent._jslistview1.setOverscrollHeader((android.graphics.drawable.Drawable)(_cd.getObject()));
 //BA.debugLineNum = 47;BA.debugLine="JSListView1.OverscrollFooter = cd";
mostCurrent._jslistview1.setOverscrollFooter((android.graphics.drawable.Drawable)(_cd.getObject()));
 //BA.debugLineNum = 52;BA.debugLine="JSListView1.Adapter = Adapter";
mostCurrent._jslistview1.setAdapter((Object)(mostCurrent._vvv1));
 //BA.debugLineNum = 53;BA.debugLine="JSListView1.FastScrollEnabled = True";
mostCurrent._jslistview1.setFastScrollEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 55;BA.debugLine="Activity.Title = \"Custom Listview 2 - Total: \" &";
mostCurrent._activity.setTitle(BA.ObjectToCharSequence("Custom Listview 2 - Total: "+BA.NumberToString(mostCurrent._v5.getSize())));
 //BA.debugLineNum = 56;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 62;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 64;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 58;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 60;BA.debugLine="End Sub";
return "";
}
public static String  _adapter_onbindview(int _position,com.salvadorjhai.data.JSViewHolder _viewholder) throws Exception{
anywheresoftware.b4a.objects.collections.Map _mapper = null;
String _job_title = "";
anywheresoftware.b4a.objects.PanelWrapper _p = null;
uk.co.martinpearman.b4a.squareup.picasso.Picasso _picaso = null;
 //BA.debugLineNum = 95;BA.debugLine="Sub Adapter_onBindView(position As Int, viewHolder";
 //BA.debugLineNum = 96;BA.debugLine="Dim mapper As Map = data.Get(position)";
_mapper = new anywheresoftware.b4a.objects.collections.Map();
_mapper.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(mostCurrent._v5.Get(_position)));
 //BA.debugLineNum = 98;BA.debugLine="Label1 = viewHolder.findViewWithTag(1)";
mostCurrent._label1.setObject((android.widget.TextView)(_viewholder.findViewWithTag((Object)(1))));
 //BA.debugLineNum = 99;BA.debugLine="Label2 = viewHolder.findViewWithTag(2)";
mostCurrent._label2.setObject((android.widget.TextView)(_viewholder.findViewWithTag((Object)(2))));
 //BA.debugLineNum = 100;BA.debugLine="Label3 = viewHolder.findViewWithTag(3)";
mostCurrent._label3.setObject((android.widget.TextView)(_viewholder.findViewWithTag((Object)(3))));
 //BA.debugLineNum = 101;BA.debugLine="ImageView1 = viewHolder.findViewWithTag(4)";
mostCurrent._imageview1.setObject((android.widget.ImageView)(_viewholder.findViewWithTag((Object)(4))));
 //BA.debugLineNum = 103;BA.debugLine="Label1.Text = $\"(${mapper.Get(\"id\")}) ${mapper.Ge";
mostCurrent._label1.setText(BA.ObjectToCharSequence(("("+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",_mapper.Get((Object)("id")))+") "+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",_mapper.Get((Object)("fullname")))+" - "+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",_mapper.Get((Object)("job_title")))+"")));
 //BA.debugLineNum = 104;BA.debugLine="Label2.Text = mapper.Get(\"company\")";
mostCurrent._label2.setText(BA.ObjectToCharSequence(_mapper.Get((Object)("company"))));
 //BA.debugLineNum = 105;BA.debugLine="Label3.Text = mapper.Get(\"slogan\")";
mostCurrent._label3.setText(BA.ObjectToCharSequence(_mapper.Get((Object)("slogan"))));
 //BA.debugLineNum = 107;BA.debugLine="Dim job_title As String = mapper.GetDefault(\"job_";
_job_title = BA.ObjectToString(_mapper.GetDefault((Object)("job_title"),(Object)("")));
 //BA.debugLineNum = 108;BA.debugLine="Label1.TextColor = Colors.RGB(3, 169, 244) 'reset";
mostCurrent._label1.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (3),(int) (169),(int) (244)));
 //BA.debugLineNum = 109;BA.debugLine="If job_title.Contains(\"Manager\") Then Label1.Text";
if (_job_title.contains("Manager")) { 
mostCurrent._label1.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (241),(int) (101),(int) (28)));};
 //BA.debugLineNum = 110;BA.debugLine="If job_title.Contains(\"Engineer\") Then Label1.Tex";
if (_job_title.contains("Engineer")) { 
mostCurrent._label1.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (47),(int) (163),(int) (194)));};
 //BA.debugLineNum = 111;BA.debugLine="If job_title.Contains(\"Secretary\") Then Label1.Te";
if (_job_title.contains("Secretary")) { 
mostCurrent._label1.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (158),(int) (93),(int) (215)));};
 //BA.debugLineNum = 113;BA.debugLine="Dim p As Panel = viewHolder.Container";
_p = new anywheresoftware.b4a.objects.PanelWrapper();
_p.setObject((android.view.ViewGroup)(_viewholder.getContainer()));
 //BA.debugLineNum = 114;BA.debugLine="If position Mod 2 = 0 Then";
if (_position%2==0) { 
 //BA.debugLineNum = 115;BA.debugLine="p.Color = Colors.Transparent";
_p.setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 }else {
 //BA.debugLineNum = 117;BA.debugLine="p.Color = Colors.ARGB(20, 100, 100, 100)";
_p.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (20),(int) (100),(int) (100),(int) (100)));
 };
 //BA.debugLineNum = 120;BA.debugLine="Dim picaso As Picasso";
_picaso = new uk.co.martinpearman.b4a.squareup.picasso.Picasso();
 //BA.debugLineNum = 121;BA.debugLine="picaso.Initialize";
_picaso.Initialize(processBA);
 //BA.debugLineNum = 122;BA.debugLine="picaso.LoadUrl(mapper.Get(\"avatar\")).Resize(Image";
_picaso.LoadUrl(BA.ObjectToString(_mapper.Get((Object)("avatar")))).Resize(mostCurrent._imageview1.getWidth(),mostCurrent._imageview1.getHeight()).CenterCrop().IntoImageView((android.widget.ImageView)(mostCurrent._imageview1.getObject()));
 //BA.debugLineNum = 124;BA.debugLine="End Sub";
return "";
}
public static String  _adapter_ongetview(int _position,com.salvadorjhai.data.JSViewHolder _viewholder) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _p = null;
 //BA.debugLineNum = 66;BA.debugLine="Sub Adapter_onGetView(position As Int, viewHolder";
 //BA.debugLineNum = 67;BA.debugLine="If viewHolder.Container = Null Then";
if (_viewholder.getContainer()== null) { 
 //BA.debugLineNum = 68;BA.debugLine="Dim p As Panel";
_p = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 69;BA.debugLine="p.Initialize(\"\")";
_p.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 70;BA.debugLine="p.LoadLayout(\"CustomLayout2\")";
_p.LoadLayout("CustomLayout2",mostCurrent.activityBA);
 //BA.debugLineNum = 72;BA.debugLine="Label1.Tag = 1";
mostCurrent._label1.setTag((Object)(1));
 //BA.debugLineNum = 73;BA.debugLine="Label2.Tag = 2";
mostCurrent._label2.setTag((Object)(2));
 //BA.debugLineNum = 74;BA.debugLine="Label3.Tag = 3";
mostCurrent._label3.setTag((Object)(3));
 //BA.debugLineNum = 75;BA.debugLine="ImageView1.Tag = 4";
mostCurrent._imageview1.setTag((Object)(4));
 //BA.debugLineNum = 77;BA.debugLine="Label1.Width = 100%x - Label1.Left - 10dip";
mostCurrent._label1.setWidth((int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._label1.getLeft()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 78;BA.debugLine="Label2.Width = 100%x - Label2.Left - 10dip";
mostCurrent._label2.setWidth((int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._label2.getLeft()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 79;BA.debugLine="Label3.Width = 100%x - Label3.Left - 10dip";
mostCurrent._label3.setWidth((int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._label3.getLeft()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 81;BA.debugLine="Label1.Typeface = Typeface.LoadFromAssets(\"Josef";
mostCurrent._label1.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("JosefinSans-Bold.ttf"));
 //BA.debugLineNum = 82;BA.debugLine="Label1.TextColor = Colors.RGB(3, 169, 244)";
mostCurrent._label1.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (3),(int) (169),(int) (244)));
 //BA.debugLineNum = 83;BA.debugLine="Label1.Gravity = Gravity.TOP";
mostCurrent._label1.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.TOP);
 //BA.debugLineNum = 85;BA.debugLine="Label2.Typeface = Typeface.LoadFromAssets(\"Josef";
mostCurrent._label2.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("JosefinSans-Bold.ttf"));
 //BA.debugLineNum = 86;BA.debugLine="Label2.Gravity = Gravity.TOP";
mostCurrent._label2.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.TOP);
 //BA.debugLineNum = 88;BA.debugLine="Label3.Typeface = Typeface.LoadFromAssets(\"Josef";
mostCurrent._label3.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("JosefinSans-SemiBoldItalic.ttf"));
 //BA.debugLineNum = 89;BA.debugLine="Label3.Gravity = Gravity.TOP";
mostCurrent._label3.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.TOP);
 //BA.debugLineNum = 91;BA.debugLine="viewHolder.Initialize(p, ImageView1.Top + ImageV";
_viewholder.Initialize((android.view.ViewGroup)(_p.getObject()),(int) (mostCurrent._imageview1.getTop()+mostCurrent._imageview1.getHeight()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA));
 };
 //BA.debugLineNum = 93;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 13;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 16;BA.debugLine="Private Adapter As JSListAdapter";
mostCurrent._vvv1 = new com.salvadorjhai.data.JSListAdapter();
 //BA.debugLineNum = 17;BA.debugLine="Private JSListView1 As JSListView2";
mostCurrent._jslistview1 = new com.salvadorjhai.widgets.JSListView2();
 //BA.debugLineNum = 18;BA.debugLine="Private ImageView1 As ImageView";
mostCurrent._imageview1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private Label1 As Label";
mostCurrent._label1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private Label2 As Label";
mostCurrent._label2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private Label3 As Label";
mostCurrent._label3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Dim data As List";
mostCurrent._v5 = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 23;BA.debugLine="End Sub";
return "";
}
public static String  _jslistview1_onitemclick(com.salvadorjhai.data.JSViewHolder _view,int _position) throws Exception{
 //BA.debugLineNum = 126;BA.debugLine="Sub JSListView1_OnItemClick(view As JSViewHolder,";
 //BA.debugLineNum = 127;BA.debugLine="ToastMessageShow($\"JSListView1_OnItemClick @ posi";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("JSListView1_OnItemClick @ position: "+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_position))+"")),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 128;BA.debugLine="End Sub";
return "";
}
public static String  _jslistview1_onitemlongclick(com.salvadorjhai.data.JSViewHolder _view,int _position) throws Exception{
 //BA.debugLineNum = 129;BA.debugLine="Sub JSListView1_OnItemLongClick(view As JSViewHold";
 //BA.debugLineNum = 130;BA.debugLine="ToastMessageShow($\"JSListView1_OnItemLongClick @";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("JSListView1_OnItemLongClick @ position: "+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_position))+"")),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 131;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 7;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 11;BA.debugLine="End Sub";
return "";
}
}
