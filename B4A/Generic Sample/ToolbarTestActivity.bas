B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=7
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: False
	#Extends: android.support.v7.app.AppCompatActivity
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Private JSListView1 As JSListView
	Private ImageView1 As ImageView
	Private Label1 As Label
	Private Label2 As Label
	Dim recipes As List
	Private ACToolBarLight1 As ACToolBarLight
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("3")
	'Activity.Title = "Toolbar - Two Line Item and Bitmap"
	
	'ACToolBarLight1.SetAsActionBar
	ACToolBarLight1.Width = 100%x
	ACToolBarLight1.Title = "Toolbar - Two Line Item and Bitmap"
	ACToolBarLight1.NavigationIconBitmap = Application.Icon
	ACToolBarLight1.InitMenuListener
	
	Dim js As JSONParser
	js.Initialize(File.ReadString(File.DirAssets, "recipes.json"))
	Dim m As Map = js.NextObject
	recipes = m.Get("recipes")

	JSListView1.DataSource = recipes
	JSListView1.FastScrollEnabled = True
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

#If Java

public boolean _onCreateOptionsMenu(android.view.Menu menu) {
	if (processBA.subExists("activity_createmenu")) {
		processBA.raiseEvent2(null, true, "activity_createmenu", false, new de.amberhome.objects.appcompat.ACMenuWrapper(menu));
		return true;
	}
	else
		return false;
}
#End If

Sub JSListView1_OnGetView(position As Int, itemLayout As ItemViewLayout, forViewUpdate As Boolean)
	Dim iv As ItemViewLayout = itemLayout
	
	If forViewUpdate = False Then
		Dim p As Panel
		p.Initialize("")
		p.LoadLayout("TwoLineAndBitmap")
		iv.Container = p
		
		Label1.Tag = 1
		Label2.Tag = 2
		ImageView1.Tag = 3
		
		Label1.Width = 100%x - Label1.Left - 10dip
		Label2.Width = 100%x - Label2.Left - 10dip
		
		'
		Label1.Typeface = Typeface.LoadFromAssets("JosefinSans-Bold.ttf")
		Label2.Typeface = Typeface.LoadFromAssets("JosefinSans-SemiBoldItalic.ttf")
		Label1.Gravity = Gravity.TOP
		Label2.Gravity = Gravity.TOP
		
		'
		iv.Height = ImageView1.Top + ImageView1.Height + 10dip
		iv.Width = 100%x
						
		Label1.Height = 15dip
		Label2.Top = Label1.Top + Label1.Height
		Label2.Height = iv.Height - Label2.Top - 10dip

	End If

	Dim mapper As Map = recipes.Get(position)

	Label1 = iv.findViewWithTag(1)
	Label2 = iv.findViewWithTag(2)
	ImageView1 = iv.findViewWithTag(3)
			
	Label1.Text = mapper.Get("title")
	Label2.Text = mapper.Get("description")
	
	Dim picaso As Picasso
	picaso.Initialize
	picaso.LoadUrl(mapper.Get("image")).Resize(ImageView1.Width,ImageView1.Height).CenterCrop.IntoImageView(ImageView1)
	
End Sub

Sub JSListView1_OnItemClick(view As ItemViewLayout, position As Int)
	Log(GetType(view))
	ToastMessageShow($"JSListView1_OnItemClick @ position: ${position}"$, False)
End Sub

Sub JSListView1_OnItemLongClick(view As ItemViewLayout, position As Int)
	Log(GetType(view))
	ToastMessageShow($"JSListView1_OnItemLongClick @ position: ${position}"$, False)
End Sub

Sub ACToolBarLight1_NavigationItemClick
	
End Sub

Sub ACToolBarLight1_MenuItemClick (Item As ACMenuItem)
	
End Sub