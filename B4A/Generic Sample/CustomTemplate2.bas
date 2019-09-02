B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=7
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: True
	#Extends: android.support.v7.app.AppCompatActivity
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Private Adapter As JSListAdapter
	Private JSListView1 As JSListView2
	Private ImageView1 As ImageView
	Private Label1 As Label
	Private Label2 As Label	
	Private Label3 As Label
	Dim data As List 
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("4")
	Activity.Title = "Custom Listview 2"		
	
	Dim js As JSONParser
	js.Initialize(File.ReadString(File.DirAssets, "MOCK_DATA.json"))
	Dim data As List = js.NextArray
	Adapter.Initialize("Adapter", data)

	' Create gradient divider
	Dim cd As GradientDrawable
	Dim c() As Int = Array As Int(Colors.rgb(252, 237, 242), 0xFFE91E63, Colors.rgb(252, 237, 242))
	cd.Initialize("TR_BL", c)
	
	JSListView1.Divider = cd
	JSListView1.DividerHeight = 3dip
	
	' set custom overscroll: which is currently not working...
	Dim cd2 As ColorDrawable
	cd2.Initialize(0xFFE91E63, 0)
	JSListView1.OverscrollHeader = cd
	JSListView1.OverscrollFooter = cd
'	Dim ccc As Cursor
'	Dim cn As SQL
'	cn.Initialize(File.DirInternalCache, "tmp.db", True)
'	ccc = cn.ExecQuery("SELECT CHANGES()")
	JSListView1.Adapter = Adapter
	JSListView1.FastScrollEnabled = True	
	
	Activity.Title = "Custom Listview 2 - Total: " & data.Size	
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub Adapter_onGetView(position As Int, viewHolder As JSViewHolder)
	If viewHolder.Container = Null Then
		Dim p As Panel
		p.Initialize("")
		p.LoadLayout("CustomLayout2")
		'
		Label1.Tag = 1
		Label2.Tag = 2
		Label3.Tag = 3
		ImageView1.Tag = 4

		Label1.Width = 100%x - Label1.Left - 10dip
		Label2.Width = 100%x - Label2.Left - 10dip
		Label3.Width = 100%x - Label3.Left - 10dip
		
		Label1.Typeface = Typeface.LoadFromAssets("JosefinSans-Bold.ttf")
		Label1.TextColor = Colors.RGB(3, 169, 244)
		Label1.Gravity = Gravity.TOP
		
		Label2.Typeface = Typeface.LoadFromAssets("JosefinSans-Bold.ttf")
		Label2.Gravity = Gravity.TOP
		
		Label3.Typeface = Typeface.LoadFromAssets("JosefinSans-SemiBoldItalic.ttf")
		Label3.Gravity = Gravity.TOP
		'
		viewHolder.Initialize(p, ImageView1.Top + ImageView1.Height + 10dip, 100%x)
	End If
End Sub

Sub Adapter_onBindView(position As Int, viewHolder As JSViewHolder)
	Dim mapper As Map = data.Get(position)

	Label1 = viewHolder.findViewWithTag(1)
	Label2 = viewHolder.findViewWithTag(2)
	Label3 = viewHolder.findViewWithTag(3)
	ImageView1 = viewHolder.findViewWithTag(4)

	Label1.Text = $"(${mapper.Get("id")}) ${mapper.Get("fullname")} - ${mapper.Get("job_title")}"$
	Label2.Text = mapper.Get("company")
	Label3.Text = mapper.Get("slogan")
	
	Dim job_title As String = mapper.GetDefault("job_title", "")
	Label1.TextColor = Colors.RGB(3, 169, 244) 'reset color
	If job_title.Contains("Manager") Then Label1.TextColor = Colors.rgb(241,101,28)
	If job_title.Contains("Engineer") Then Label1.TextColor = Colors.rgb(47,163,194)
	If job_title.Contains("Secretary") Then Label1.TextColor = Colors.rgb(158,93,215)

	Dim p As Panel = viewHolder.Container
	If position Mod 2 = 0 Then
		p.Color = Colors.Transparent
	Else
		p.Color = Colors.ARGB(20, 100, 100, 100)
	End If
	
	Dim picaso As Picasso
	picaso.Initialize
	picaso.LoadUrl(mapper.Get("avatar")).Resize(ImageView1.Width,ImageView1.Height).CenterCrop.IntoImageView(ImageView1)
	
End Sub

Sub JSListView1_OnItemClick(view As JSViewHolder, position As Int)
	ToastMessageShow($"JSListView1_OnItemClick @ position: ${position}"$, False)
End Sub
Sub JSListView1_OnItemLongClick(view As JSViewHolder, position As Int)
	ToastMessageShow($"JSListView1_OnItemLongClick @ position: ${position}"$, False)
End Sub
