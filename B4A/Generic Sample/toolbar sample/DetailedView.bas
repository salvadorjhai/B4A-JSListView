Type=Activity
Version=6.5
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: True
	#Extends: android.support.v7.app.AppCompatActivity
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
	Dim mapper As Map
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	
	Private ACToolBarDark1 As ACToolBarDark
	Private ImageView1 As ImageView
	Private Label1 As Label
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("DetailedView")
	
	If mapper.IsInitialized Then
		ACToolBarDark1.Title = mapper.Get("company")
		Label1.Text = $"(${mapper.Get("id")}) ${mapper.Get("fullname")} - ${mapper.Get("job_title")}"$
		Label1.TextColor = Colors.Magenta
		Label1.TextSize = 16
		Dim picaso As Picasso
		picaso.Initialize
		picaso.LoadUrl(mapper.Get("avatar")).Resize(ImageView1.Width,ImageView1.Height).CenterCrop.IntoImageView(ImageView1)
	End If
	
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub
