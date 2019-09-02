package b4a.example;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.pc.*;

public class main_subs_0 {


public static RemoteObject  _activity_create(RemoteObject _firsttime) throws Exception{
try {
		Debug.PushSubsStack("Activity_Create (main) ","main",0,main.mostCurrent.activityBA,main.mostCurrent,30);
if (RapidSub.canDelegate("activity_create")) { return b4a.example.main.remoteMe.runUserSub(false, "main","activity_create", _firsttime);}
RemoteObject _json = RemoteObject.declareNull("anywheresoftware.b4a.objects.collections.JSONParser");
RemoteObject _datasource = RemoteObject.declareNull("anywheresoftware.b4a.objects.collections.List");
Debug.locals.put("FirstTime", _firsttime);
 BA.debugLineNum = 30;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
Debug.ShouldStop(536870912);
 BA.debugLineNum = 33;BA.debugLine="Dim json As JSONParser";
Debug.ShouldStop(1);
_json = RemoteObject.createNew ("anywheresoftware.b4a.objects.collections.JSONParser");Debug.locals.put("json", _json);
 BA.debugLineNum = 34;BA.debugLine="json.Initialize(File.ReadString(File.DirAssets, \"";
Debug.ShouldStop(2);
_json.runVoidMethod ("Initialize",(Object)(main.mostCurrent.__c.getField(false,"File").runMethod(true,"ReadString",(Object)(main.mostCurrent.__c.getField(false,"File").runMethod(true,"getDirAssets")),(Object)(RemoteObject.createImmutable("drug_list.json")))));
 BA.debugLineNum = 35;BA.debugLine="Dim dataSource As List = json.NextArray";
Debug.ShouldStop(4);
_datasource = RemoteObject.createNew ("anywheresoftware.b4a.objects.collections.List");
_datasource = _json.runMethod(false,"NextArray");Debug.locals.put("dataSource", _datasource);Debug.locals.put("dataSource", _datasource);
 BA.debugLineNum = 37;BA.debugLine="adapter.Initialize(\"adapter\", dataSource)";
Debug.ShouldStop(16);
main._adapter.runVoidMethod ("Initialize",main.processBA,(Object)(BA.ObjectToString("adapter")),(Object)(_datasource));
 BA.debugLineNum = 40;BA.debugLine="lv.Initialize(\"LV\")";
Debug.ShouldStop(128);
main.mostCurrent._lv.runVoidMethod ("Initialize",main.mostCurrent.activityBA,(Object)(RemoteObject.createImmutable("LV")));
 BA.debugLineNum = 41;BA.debugLine="Activity.AddView(lv, 0dip, 0dip, 100%x, 100%y)";
Debug.ShouldStop(256);
main.mostCurrent._activity.runVoidMethod ("AddView",(Object)((main.mostCurrent._lv.getObject())),(Object)(main.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 0)))),(Object)(main.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 0)))),(Object)(main.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 100)),main.mostCurrent.activityBA)),(Object)(main.mostCurrent.__c.runMethod(true,"PerYToCurrent",(Object)(BA.numberCast(float.class, 100)),main.mostCurrent.activityBA)));
 BA.debugLineNum = 44;BA.debugLine="lv.Adapter = adapter";
Debug.ShouldStop(2048);
main.mostCurrent._lv.runMethod(false,"setAdapter",(main._adapter));
 BA.debugLineNum = 45;BA.debugLine="lv.setCacheColorHint(0)";
Debug.ShouldStop(4096);
main.mostCurrent._lv.runVoidMethod ("setCacheColorHint",(Object)(BA.numberCast(int.class, 0)));
 BA.debugLineNum = 46;BA.debugLine="lv.setFastScrollEnabled(True)";
Debug.ShouldStop(8192);
main.mostCurrent._lv.runVoidMethod ("setFastScrollEnabled",(Object)(main.mostCurrent.__c.getField(true,"True")));
 BA.debugLineNum = 47;BA.debugLine="lv.setNumColumns(3)";
Debug.ShouldStop(16384);
main.mostCurrent._lv.runVoidMethod ("setNumColumns",(Object)(BA.numberCast(int.class, 3)));
 BA.debugLineNum = 48;BA.debugLine="lv.setVerticalSpacing(3dip)";
Debug.ShouldStop(32768);
main.mostCurrent._lv.runVoidMethod ("setVerticalSpacing",(Object)(main.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 3)))));
 BA.debugLineNum = 49;BA.debugLine="End Sub";
Debug.ShouldStop(65536);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _activity_pause(RemoteObject _userclosed) throws Exception{
try {
		Debug.PushSubsStack("Activity_Pause (main) ","main",0,main.mostCurrent.activityBA,main.mostCurrent,55);
if (RapidSub.canDelegate("activity_pause")) { return b4a.example.main.remoteMe.runUserSub(false, "main","activity_pause", _userclosed);}
Debug.locals.put("UserClosed", _userclosed);
 BA.debugLineNum = 55;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
Debug.ShouldStop(4194304);
 BA.debugLineNum = 57;BA.debugLine="End Sub";
Debug.ShouldStop(16777216);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _activity_resume() throws Exception{
try {
		Debug.PushSubsStack("Activity_Resume (main) ","main",0,main.mostCurrent.activityBA,main.mostCurrent,51);
if (RapidSub.canDelegate("activity_resume")) { return b4a.example.main.remoteMe.runUserSub(false, "main","activity_resume");}
 BA.debugLineNum = 51;BA.debugLine="Sub Activity_Resume";
Debug.ShouldStop(262144);
 BA.debugLineNum = 53;BA.debugLine="End Sub";
Debug.ShouldStop(1048576);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _adapter_onbindview(RemoteObject _position,RemoteObject _viewholder) throws Exception{
try {
		Debug.PushSubsStack("adapter_onBindView (main) ","main",0,main.mostCurrent.activityBA,main.mostCurrent,92);
if (RapidSub.canDelegate("adapter_onbindview")) { return b4a.example.main.remoteMe.runUserSub(false, "main","adapter_onbindview", _position, _viewholder);}
RemoteObject _p = RemoteObject.declareNull("anywheresoftware.b4a.objects.PanelWrapper");
RemoteObject _mapdata = RemoteObject.declareNull("anywheresoftware.b4a.objects.collections.Map");
RemoteObject _picaso = RemoteObject.declareNull("uk.co.martinpearman.b4a.squareup.picasso.Picasso");
Debug.locals.put("position", _position);
Debug.locals.put("viewHolder", _viewholder);
 BA.debugLineNum = 92;BA.debugLine="Sub adapter_onBindView(position As Int, viewHolder";
Debug.ShouldStop(134217728);
 BA.debugLineNum = 93;BA.debugLine="If viewHolder.Container = Null Then Return";
Debug.ShouldStop(268435456);
if (RemoteObject.solveBoolean("n",_viewholder.runMethod(false,"getContainer"))) { 
if (true) return RemoteObject.createImmutable("");};
 BA.debugLineNum = 96;BA.debugLine="Dim p As Panel = viewHolder.Container";
Debug.ShouldStop(-2147483648);
_p = RemoteObject.createNew ("anywheresoftware.b4a.objects.PanelWrapper");
_p.setObject(_viewholder.runMethod(false,"getContainer"));Debug.locals.put("p", _p);
 BA.debugLineNum = 97;BA.debugLine="If (position Mod 2) = 0 Then";
Debug.ShouldStop(1);
if (RemoteObject.solveBoolean("=",(RemoteObject.solve(new RemoteObject[] {_position,RemoteObject.createImmutable(2)}, "%",0, 1)),BA.numberCast(double.class, 0))) { 
 BA.debugLineNum = 98;BA.debugLine="p.Color = Colors.LightGray";
Debug.ShouldStop(2);
_p.runVoidMethod ("setColor",main.mostCurrent.__c.getField(false,"Colors").getField(true,"LightGray"));
 }else {
 BA.debugLineNum = 100;BA.debugLine="p.Color = Colors.White";
Debug.ShouldStop(8);
_p.runVoidMethod ("setColor",main.mostCurrent.__c.getField(false,"Colors").getField(true,"White"));
 };
 BA.debugLineNum = 103;BA.debugLine="imgAvatar = viewHolder.findViewById(10)";
Debug.ShouldStop(64);
main.mostCurrent._imgavatar.setObject(_viewholder.runMethod(false,"findViewById",(Object)(BA.numberCast(int.class, 10))));
 BA.debugLineNum = 104;BA.debugLine="lblDrugname = viewHolder.findViewById(20)";
Debug.ShouldStop(128);
main.mostCurrent._lbldrugname.setObject(_viewholder.runMethod(false,"findViewById",(Object)(BA.numberCast(int.class, 20))));
 BA.debugLineNum = 107;BA.debugLine="Dim mapData As Map = adapter.DataSource.Get(posit";
Debug.ShouldStop(1024);
_mapdata = RemoteObject.createNew ("anywheresoftware.b4a.objects.collections.Map");
_mapdata.setObject(main._adapter.runMethod(false,"getDataSource").runMethod(false,"Get",(Object)(_position)));Debug.locals.put("mapData", _mapdata);
 BA.debugLineNum = 108;BA.debugLine="lblDrugname.Text = mapData.Get(\"drug_name\")";
Debug.ShouldStop(2048);
main.mostCurrent._lbldrugname.runMethod(true,"setText",BA.ObjectToCharSequence(_mapdata.runMethod(false,"Get",(Object)((RemoteObject.createImmutable("drug_name"))))));
 BA.debugLineNum = 110;BA.debugLine="Dim picaso As Picasso";
Debug.ShouldStop(8192);
_picaso = RemoteObject.createNew ("uk.co.martinpearman.b4a.squareup.picasso.Picasso");Debug.locals.put("picaso", _picaso);
 BA.debugLineNum = 111;BA.debugLine="picaso.Initialize";
Debug.ShouldStop(16384);
_picaso.runVoidMethod ("Initialize",main.processBA);
 BA.debugLineNum = 112;BA.debugLine="picaso.LoadUrl(mapData.Get(\"image\")).Resize(imgAv";
Debug.ShouldStop(32768);
_picaso.runMethod(false,"LoadUrl",(Object)(BA.ObjectToString(_mapdata.runMethod(false,"Get",(Object)((RemoteObject.createImmutable("image"))))))).runMethod(false,"Resize",(Object)(main.mostCurrent._imgavatar.runMethod(true,"getWidth")),(Object)(main.mostCurrent._imgavatar.runMethod(true,"getHeight"))).runVoidMethod ("IntoImageView",(Object)((main.mostCurrent._imgavatar.getObject())));
 BA.debugLineNum = 114;BA.debugLine="lblDrugname.Tag = position";
Debug.ShouldStop(131072);
main.mostCurrent._lbldrugname.runMethod(false,"setTag",(_position));
 BA.debugLineNum = 115;BA.debugLine="End Sub";
Debug.ShouldStop(262144);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _adapter_ongetview(RemoteObject _position,RemoteObject _viewholder) throws Exception{
try {
		Debug.PushSubsStack("adapter_onGetView (main) ","main",0,main.mostCurrent.activityBA,main.mostCurrent,59);
if (RapidSub.canDelegate("adapter_ongetview")) { return b4a.example.main.remoteMe.runUserSub(false, "main","adapter_ongetview", _position, _viewholder);}
RemoteObject _p = RemoteObject.declareNull("anywheresoftware.b4a.objects.PanelWrapper");
Debug.locals.put("position", _position);
Debug.locals.put("viewHolder", _viewholder);
 BA.debugLineNum = 59;BA.debugLine="Sub adapter_onGetView(position As Int, viewHolder";
Debug.ShouldStop(67108864);
 BA.debugLineNum = 61;BA.debugLine="If viewHolder.Container	= Null Then";
Debug.ShouldStop(268435456);
if (RemoteObject.solveBoolean("n",_viewholder.runMethod(false,"getContainer"))) { 
 BA.debugLineNum = 63;BA.debugLine="Dim p As Panel";
Debug.ShouldStop(1073741824);
_p = RemoteObject.createNew ("anywheresoftware.b4a.objects.PanelWrapper");Debug.locals.put("p", _p);
 BA.debugLineNum = 64;BA.debugLine="p.Initialize(\"\")";
Debug.ShouldStop(-2147483648);
_p.runVoidMethod ("Initialize",main.mostCurrent.activityBA,(Object)(RemoteObject.createImmutable("")));
 BA.debugLineNum = 65;BA.debugLine="p.LoadLayout(\"layout\")";
Debug.ShouldStop(1);
_p.runMethodAndSync(false,"LoadLayout",(Object)(RemoteObject.createImmutable("layout")),main.mostCurrent.activityBA);
 BA.debugLineNum = 66;BA.debugLine="viewHolder.Initialize(p, 120dip, 100%x / 3)";
Debug.ShouldStop(2);
_viewholder.runVoidMethod ("Initialize",(Object)((_p.getObject())),(Object)(main.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 120)))),(Object)(BA.numberCast(int.class, RemoteObject.solve(new RemoteObject[] {main.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 100)),main.mostCurrent.activityBA),RemoteObject.createImmutable(3)}, "/",0, 0))));
 BA.debugLineNum = 69;BA.debugLine="imgAvatar.SetLayout(0,0, viewHolder.Width, viewH";
Debug.ShouldStop(16);
main.mostCurrent._imgavatar.runVoidMethod ("SetLayout",(Object)(BA.numberCast(int.class, 0)),(Object)(BA.numberCast(int.class, 0)),(Object)(_viewholder.runMethod(true,"getWidth")),(Object)(_viewholder.runMethod(true,"getHeight")));
 BA.debugLineNum = 70;BA.debugLine="imgAvatar.Gravity = Gravity.FILL";
Debug.ShouldStop(32);
main.mostCurrent._imgavatar.runMethod(true,"setGravity",main.mostCurrent.__c.getField(false,"Gravity").getField(true,"FILL"));
 BA.debugLineNum = 72;BA.debugLine="lblDrugname.SetLayout(10dip, imgAvatar.Height-30";
Debug.ShouldStop(128);
main.mostCurrent._lbldrugname.runVoidMethod ("SetLayout",(Object)(main.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 10)))),(Object)(RemoteObject.solve(new RemoteObject[] {main.mostCurrent._imgavatar.runMethod(true,"getHeight"),main.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 30)))}, "-",1, 1)),(Object)(RemoteObject.solve(new RemoteObject[] {_viewholder.runMethod(true,"getWidth"),main.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 20)))}, "-",1, 1)),(Object)(main.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 30)))));
 BA.debugLineNum = 73;BA.debugLine="lblDrugname.Gravity = Bit.Or(Gravity.LEFT, Gravi";
Debug.ShouldStop(256);
main.mostCurrent._lbldrugname.runMethod(true,"setGravity",main.mostCurrent.__c.getField(false,"Bit").runMethod(true,"Or",(Object)(main.mostCurrent.__c.getField(false,"Gravity").getField(true,"LEFT")),(Object)(main.mostCurrent.__c.getField(false,"Gravity").getField(true,"CENTER_HORIZONTAL"))));
 BA.debugLineNum = 74;BA.debugLine="lblDrugname.TextColor=Colors.Blue";
Debug.ShouldStop(512);
main.mostCurrent._lbldrugname.runMethod(true,"setTextColor",main.mostCurrent.__c.getField(false,"Colors").getField(true,"Blue"));
 BA.debugLineNum = 77;BA.debugLine="viewHolder.setViewId(imgAvatar, 10)";
Debug.ShouldStop(4096);
_viewholder.runVoidMethod ("setViewId",(Object)((main.mostCurrent._imgavatar.getObject())),(Object)(BA.numberCast(int.class, 10)));
 BA.debugLineNum = 78;BA.debugLine="viewHolder.setViewId(lblDrugname, 20)";
Debug.ShouldStop(8192);
_viewholder.runVoidMethod ("setViewId",(Object)((main.mostCurrent._lbldrugname.getObject())),(Object)(BA.numberCast(int.class, 20)));
 };
 BA.debugLineNum = 80;BA.debugLine="End Sub";
Debug.ShouldStop(32768);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _btnadd_click() throws Exception{
try {
		Debug.PushSubsStack("btnAdd_Click (main) ","main",0,main.mostCurrent.activityBA,main.mostCurrent,132);
if (RapidSub.canDelegate("btnadd_click")) { return b4a.example.main.remoteMe.runUserSub(false, "main","btnadd_click");}
RemoteObject _btn = RemoteObject.declareNull("anywheresoftware.b4a.objects.ButtonWrapper");
RemoteObject _position = RemoteObject.createImmutable(0);
RemoteObject _mapdata = RemoteObject.declareNull("anywheresoftware.b4a.objects.collections.Map");
RemoteObject _qty = RemoteObject.createImmutable(0);
 BA.debugLineNum = 132;BA.debugLine="Sub btnAdd_Click";
Debug.ShouldStop(8);
 BA.debugLineNum = 133;BA.debugLine="Dim btn As Button = Sender";
Debug.ShouldStop(16);
_btn = RemoteObject.createNew ("anywheresoftware.b4a.objects.ButtonWrapper");
_btn.setObject(main.mostCurrent.__c.runMethod(false,"Sender",main.mostCurrent.activityBA));Debug.locals.put("btn", _btn);
 BA.debugLineNum = 134;BA.debugLine="Dim position As Int = btn.Tag";
Debug.ShouldStop(32);
_position = BA.numberCast(int.class, _btn.runMethod(false,"getTag"));Debug.locals.put("position", _position);Debug.locals.put("position", _position);
 BA.debugLineNum = 136;BA.debugLine="Dim mapData As Map = adapter.getItem(position)";
Debug.ShouldStop(128);
_mapdata = RemoteObject.createNew ("anywheresoftware.b4a.objects.collections.Map");
_mapdata.setObject(main._adapter.runMethod(false,"getItem",(Object)(_position)));Debug.locals.put("mapData", _mapdata);
 BA.debugLineNum = 137;BA.debugLine="Dim qty As Int = mapData.Get(\"quantity\")";
Debug.ShouldStop(256);
_qty = BA.numberCast(int.class, _mapdata.runMethod(false,"Get",(Object)((RemoteObject.createImmutable("quantity")))));Debug.locals.put("qty", _qty);Debug.locals.put("qty", _qty);
 BA.debugLineNum = 140;BA.debugLine="qty = qty + 1";
Debug.ShouldStop(2048);
_qty = RemoteObject.solve(new RemoteObject[] {_qty,RemoteObject.createImmutable(1)}, "+",1, 1);Debug.locals.put("qty", _qty);
 BA.debugLineNum = 141;BA.debugLine="mapData.Put(\"quantity\", qty)";
Debug.ShouldStop(4096);
_mapdata.runVoidMethod ("Put",(Object)(RemoteObject.createImmutable(("quantity"))),(Object)((_qty)));
 BA.debugLineNum = 143;BA.debugLine="adapter.ItemUpdateAt(position, mapData)";
Debug.ShouldStop(16384);
main._adapter.runVoidMethod ("ItemUpdateAt",(Object)(_position),(Object)((_mapdata.getObject())));
 BA.debugLineNum = 144;BA.debugLine="End Sub";
Debug.ShouldStop(32768);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _btnminus_click() throws Exception{
try {
		Debug.PushSubsStack("btnMinus_Click (main) ","main",0,main.mostCurrent.activityBA,main.mostCurrent,118);
if (RapidSub.canDelegate("btnminus_click")) { return b4a.example.main.remoteMe.runUserSub(false, "main","btnminus_click");}
RemoteObject _btn = RemoteObject.declareNull("anywheresoftware.b4a.objects.ButtonWrapper");
RemoteObject _position = RemoteObject.createImmutable(0);
RemoteObject _mapdata = RemoteObject.declareNull("anywheresoftware.b4a.objects.collections.Map");
RemoteObject _qty = RemoteObject.createImmutable(0);
 BA.debugLineNum = 118;BA.debugLine="Sub btnMinus_Click";
Debug.ShouldStop(2097152);
 BA.debugLineNum = 119;BA.debugLine="Dim btn As Button = Sender";
Debug.ShouldStop(4194304);
_btn = RemoteObject.createNew ("anywheresoftware.b4a.objects.ButtonWrapper");
_btn.setObject(main.mostCurrent.__c.runMethod(false,"Sender",main.mostCurrent.activityBA));Debug.locals.put("btn", _btn);
 BA.debugLineNum = 120;BA.debugLine="Dim position As Int = btn.Tag";
Debug.ShouldStop(8388608);
_position = BA.numberCast(int.class, _btn.runMethod(false,"getTag"));Debug.locals.put("position", _position);Debug.locals.put("position", _position);
 BA.debugLineNum = 122;BA.debugLine="Dim mapData As Map = adapter.getItem(position)";
Debug.ShouldStop(33554432);
_mapdata = RemoteObject.createNew ("anywheresoftware.b4a.objects.collections.Map");
_mapdata.setObject(main._adapter.runMethod(false,"getItem",(Object)(_position)));Debug.locals.put("mapData", _mapdata);
 BA.debugLineNum = 123;BA.debugLine="Dim qty As Int = mapData.Get(\"quantity\")";
Debug.ShouldStop(67108864);
_qty = BA.numberCast(int.class, _mapdata.runMethod(false,"Get",(Object)((RemoteObject.createImmutable("quantity")))));Debug.locals.put("qty", _qty);Debug.locals.put("qty", _qty);
 BA.debugLineNum = 126;BA.debugLine="qty = qty - 1";
Debug.ShouldStop(536870912);
_qty = RemoteObject.solve(new RemoteObject[] {_qty,RemoteObject.createImmutable(1)}, "-",1, 1);Debug.locals.put("qty", _qty);
 BA.debugLineNum = 127;BA.debugLine="mapData.Put(\"quantity\", qty)";
Debug.ShouldStop(1073741824);
_mapdata.runVoidMethod ("Put",(Object)(RemoteObject.createImmutable(("quantity"))),(Object)((_qty)));
 BA.debugLineNum = 129;BA.debugLine="adapter.ItemUpdateAt(position, mapData)";
Debug.ShouldStop(1);
main._adapter.runVoidMethod ("ItemUpdateAt",(Object)(_position),(Object)((_mapdata.getObject())));
 BA.debugLineNum = 130;BA.debugLine="End Sub";
Debug.ShouldStop(2);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _globals() throws Exception{
 //BA.debugLineNum = 21;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 24;BA.debugLine="Dim lv As JSGridView";
main.mostCurrent._lv = RemoteObject.createNew ("com.salvadorjhai.widgets.JSGridView");
 //BA.debugLineNum = 26;BA.debugLine="Private imgAvatar As ImageView";
main.mostCurrent._imgavatar = RemoteObject.createNew ("anywheresoftware.b4a.objects.ImageViewWrapper");
 //BA.debugLineNum = 27;BA.debugLine="Private lblDrugname As Label";
main.mostCurrent._lbldrugname = RemoteObject.createNew ("anywheresoftware.b4a.objects.LabelWrapper");
 //BA.debugLineNum = 28;BA.debugLine="End Sub";
return RemoteObject.createImmutable("");
}
public static RemoteObject  _lbldrugname_click() throws Exception{
try {
		Debug.PushSubsStack("lblDrugname_Click (main) ","main",0,main.mostCurrent.activityBA,main.mostCurrent,146);
if (RapidSub.canDelegate("lbldrugname_click")) { return b4a.example.main.remoteMe.runUserSub(false, "main","lbldrugname_click");}
RemoteObject _lbl = RemoteObject.declareNull("anywheresoftware.b4a.objects.LabelWrapper");
RemoteObject _position = RemoteObject.createImmutable(0);
 BA.debugLineNum = 146;BA.debugLine="Sub lblDrugname_Click";
Debug.ShouldStop(131072);
 BA.debugLineNum = 147;BA.debugLine="Dim lbl As Label = Sender";
Debug.ShouldStop(262144);
_lbl = RemoteObject.createNew ("anywheresoftware.b4a.objects.LabelWrapper");
_lbl.setObject(main.mostCurrent.__c.runMethod(false,"Sender",main.mostCurrent.activityBA));Debug.locals.put("lbl", _lbl);
 BA.debugLineNum = 148;BA.debugLine="Dim position As Int = lbl.Tag";
Debug.ShouldStop(524288);
_position = BA.numberCast(int.class, _lbl.runMethod(false,"getTag"));Debug.locals.put("position", _position);Debug.locals.put("position", _position);
 BA.debugLineNum = 149;BA.debugLine="ToastMessageShow($\"You clicked on item index ${po";
Debug.ShouldStop(1048576);
main.mostCurrent.__c.runVoidMethod ("ToastMessageShow",(Object)(BA.ObjectToCharSequence((RemoteObject.concat(RemoteObject.createImmutable("You clicked on item index "),main.mostCurrent.__c.runMethod(true,"SmartStringFormatter",(Object)(BA.ObjectToString("")),(Object)((_position))),RemoteObject.createImmutable(""))))),(Object)(main.mostCurrent.__c.getField(true,"False")));
 BA.debugLineNum = 150;BA.debugLine="End Sub";
Debug.ShouldStop(2097152);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _lv_onitemclick(RemoteObject _view,RemoteObject _position) throws Exception{
try {
		Debug.PushSubsStack("LV_OnItemClick (main) ","main",0,main.mostCurrent.activityBA,main.mostCurrent,82);
if (RapidSub.canDelegate("lv_onitemclick")) { return b4a.example.main.remoteMe.runUserSub(false, "main","lv_onitemclick", _view, _position);}
Debug.locals.put("view", _view);
Debug.locals.put("position", _position);
 BA.debugLineNum = 82;BA.debugLine="Sub LV_OnItemClick(view As JSViewHolder, position";
Debug.ShouldStop(131072);
 BA.debugLineNum = 84;BA.debugLine="ToastMessageShow($\"You clicked on item ${position";
Debug.ShouldStop(524288);
main.mostCurrent.__c.runVoidMethod ("ToastMessageShow",(Object)(BA.ObjectToCharSequence((RemoteObject.concat(RemoteObject.createImmutable("You clicked on item "),main.mostCurrent.__c.runMethod(true,"SmartStringFormatter",(Object)(BA.ObjectToString("")),(Object)((_position))),RemoteObject.createImmutable(""))))),(Object)(main.mostCurrent.__c.getField(true,"False")));
 BA.debugLineNum = 85;BA.debugLine="End Sub";
Debug.ShouldStop(1048576);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _lv_onitemlongclick(RemoteObject _view,RemoteObject _position) throws Exception{
try {
		Debug.PushSubsStack("LV_OnItemLongClick (main) ","main",0,main.mostCurrent.activityBA,main.mostCurrent,87);
if (RapidSub.canDelegate("lv_onitemlongclick")) { return b4a.example.main.remoteMe.runUserSub(false, "main","lv_onitemlongclick", _view, _position);}
Debug.locals.put("view", _view);
Debug.locals.put("position", _position);
 BA.debugLineNum = 87;BA.debugLine="Sub LV_OnItemLongClick(view As JSViewHolder, posit";
Debug.ShouldStop(4194304);
 BA.debugLineNum = 89;BA.debugLine="ToastMessageShow($\"You long clicked on item ${pos";
Debug.ShouldStop(16777216);
main.mostCurrent.__c.runVoidMethod ("ToastMessageShow",(Object)(BA.ObjectToCharSequence((RemoteObject.concat(RemoteObject.createImmutable("You long clicked on item "),main.mostCurrent.__c.runMethod(true,"SmartStringFormatter",(Object)(BA.ObjectToString("")),(Object)((_position))),RemoteObject.createImmutable(""))))),(Object)(main.mostCurrent.__c.getField(true,"False")));
 BA.debugLineNum = 90;BA.debugLine="End Sub";
Debug.ShouldStop(33554432);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        main_subs_0._process_globals();
starter_subs_0._process_globals();
main.myClass = BA.getDeviceClass ("b4a.example.main");
starter.myClass = BA.getDeviceClass ("b4a.example.starter");
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static RemoteObject  _process_globals() throws Exception{
 //BA.debugLineNum = 15;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 18;BA.debugLine="Dim adapter As JSListAdapter";
main._adapter = RemoteObject.createNew ("com.salvadorjhai.data.JSListAdapter");
 //BA.debugLineNum = 19;BA.debugLine="End Sub";
return RemoteObject.createImmutable("");
}
}