# B4A-JSListView
Listview Wrapper for B4A (Basic4Android)
Requires List or Cursor Adapter - [JSDataAdapter](https://github.com/salvadorjhai/B4A-JSDataAdapter)

Copy .jar, .xml to your additional library folder

The provided sample (source) (Generic Sample folder) requires additional library:
* AppCompat
* JSON
* Picasso
* SQLiteDatabase (private, use SQL library instead)

You can find the additional library in [B4A Forum](https://b4x.com/android/forum/#b4a-development-tool-for-native-android-apps.25)

### Sample
##### Using List Adapter 
```
'sample list from json file
Dim json As JSONParser
json.Initialize(File.ReadString(File.DirAssets, "drug_list.json"))
Dim dataSource As List = json.NextArray

'initialize adapter
adapter.Initialize("adapter", dataSource)

'Initialize JSListView2 and set the adapter
Dim LV As JSListView2
LV.Initialize("LV")
Activity.AddView(LV, 0dip, 0dip, 100%x, 100%y)
LV.Adapter = Adapter
```


### Important Notes
* click/ripple effect on the item will not work if your viewholder.container contains buttons.
* Click event will also not be triggered if your viewholder.container contains buttons.

### FAQ
* How to click items when your viewholder.container contains buttons? 
* How to handle the events of my views (buttons, label, imageview, etc) inside viewholder.container? - *on the `_onBindView(position As Int, viewHolder As JSViewHolder)` of your adapter, just remember the position and set it to tag property of your views - (checkout With Button Sample project) * 
```
btnMinus.Tag = position
```
```
Sub btnMinus_Click
	Dim btn As Button = Sender
	Dim position As Int = btn.Tag
	
	Dim mapData As Map = adapter.getItem(position)
	Dim qty As Int = mapData.Get("quantity")
	
	' modify data
	qty = qty - 1
	mapData.Put("quantity", qty)
	
	adapter.ItemUpdateAt(position, mapData)	
End Sub
```
