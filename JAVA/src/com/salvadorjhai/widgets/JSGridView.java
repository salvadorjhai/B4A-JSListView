package com.salvadorjhai.widgets;

import com.salvadorjhai.data.JSViewHolder;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BA.ActivityObject;
import anywheresoftware.b4a.BA.Author;
import anywheresoftware.b4a.BA.Events;
import anywheresoftware.b4a.BA.ShortName;
import anywheresoftware.b4a.BA.Version;
import anywheresoftware.b4a.keywords.Common.DesignerCustomView;
import anywheresoftware.b4a.objects.CustomViewWrapper;
import anywheresoftware.b4a.objects.LabelWrapper;
import anywheresoftware.b4a.objects.PanelWrapper;
import anywheresoftware.b4a.objects.ViewWrapper;
import anywheresoftware.b4a.objects.collections.Map;

/**
 * JSGridView base on Android GridView. You can use designer to create your
 * item layout, just inflate the item layout using panel LoadLayout. Use this
 * JSGridView together with JSListAdapter or JSCursorAdapter
 * 
 * @author salvadorjhai
 * 
 */

@ShortName("JSGridView")
@Events(values = { "OnItemClick(view As JSViewHolder, position As int)",
		"OnItemLongClick(view As JSViewHolder, position As int)" })
@ActivityObject
public class JSGridView extends ViewWrapper<GridView> implements DesignerCustomView {
	/**
     * Disables stretching.
     */
    public static final int STRETCH_MODE_NO_STRETCH = 0;
    /**
     * Stretches the spacing between columns.
     */
    public static final int STRETCH_MODE_SPACING = 1;
    /**
     * Stretches columns.
     */
    public static final int STRETCH_MODE_COLUMN_WIDTH = 2;
    /**
     * Stretches the spacing between columns. The spacing is uniform.
     */
    public static final int STRETCH_MODE_SPACING_UNIFORM = 3;
    /**
     * Creates as many columns as can fit on screen.
     */
    public static final int NUM_COLUMNS_AUTO_FIT = -1;
    
	/** The m BA. */
	private BA mBA = null;
	
	/** The m event name. */
	private String mEventName = "";

	/* (non-Javadoc)
	 * @see anywheresoftware.b4a.objects.ViewWrapper#Initialize(anywheresoftware.b4a.BA, java.lang.String)
	 */
	@Override
	public void Initialize(BA ba, String eventName) {
		_initialize(ba, null, eventName);
	}

	/* (non-Javadoc)
	 * @see anywheresoftware.b4a.keywords.Common.DesignerCustomView#_initialize(anywheresoftware.b4a.BA, java.lang.Object, java.lang.String)
	 */
	@Override
	@BA.Hide
	public void _initialize(BA ba, Object paramObject, String eventName) {
		innerInitialize(ba, eventName, false);
	}

	/* (non-Javadoc)
	 * @see anywheresoftware.b4a.objects.ViewWrapper#innerInitialize(anywheresoftware.b4a.BA, java.lang.String, boolean)
	 */
	@Override
	@BA.Hide
	public void innerInitialize(final BA ba, final String eventName, boolean keepOldObject) {
		this.mBA = ba;
		this.mEventName = eventName;

		try {
			// set object and props
			if (!keepOldObject) {
				this.setObject(new GridView(ba.context));
			}

			// Inner Initialize
			super.innerInitialize(ba, eventName, true);

			// Set event Listeners
			if (eventName.length() != 0) {

				// onItemClick
				this.getObjectOrNull().setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						String event = mEventName + "_OnItemClick";
						JSViewHolder iv = (JSViewHolder) view.getTag();
						mBA.raiseEvent(this, event.toLowerCase(BA.cul), new Object[] { iv, position });
					}
				});

				// onItemLongClick
				this.getObjectOrNull().setOnItemLongClickListener(new OnItemLongClickListener() {
					@Override
					public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
						String event = mEventName + "_OnItemLongClick";
						JSViewHolder iv = (JSViewHolder) view.getTag();
						mBA.raiseEvent(this, event.toLowerCase(BA.cul), new Object[] { iv, position });
						return false;
					}
				});

			}

		} catch (Exception e) {
			BA.LogError(e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see anywheresoftware.b4a.keywords.Common.DesignerCustomView#DesignerCreateView(anywheresoftware.b4a.objects.PanelWrapper, anywheresoftware.b4a.objects.LabelWrapper, anywheresoftware.b4a.objects.collections.Map)
	 */
	@Override
	public void DesignerCreateView(PanelWrapper paramPanelWrapper, LabelWrapper paramLabelWrapper, Map paramMap) {
		CustomViewWrapper.replaceBaseWithView2(paramPanelWrapper, this.getObjectOrNull());
	}

	/**
	 * Set Adapter (JSCursorAdapter, JSListAdapter).
	 *
	 * @param adapter
	 *            the new adapter
	 */
	public void setAdapter(Object adapter) {
		this.getObjectOrNull().setAdapter((ListAdapter) adapter);
	}

	/**
	 * Get JSCursorAdapter or JSListAdapter.
	 *
	 * @return getAdapter
	 */
	public Object getAdapter() {
		return this.getObjectOrNull().getAdapter();
	}
	
	/**
	 * Returns true if the fast scroller is enabled.
	 * @return true if fast scroll is enabled, false otherwise 
	 */
	public boolean IsFastScrollEnabled() {
		return this.getObjectOrNull().isFastScrollEnabled();
	}
	
	/**
	 * Specifies whether fast scrolling is enabled or disabled. 
	 * @param enabled boolean: true to enable fast scrolling, false otherwise 
	 */
	public JSGridView setFastScrollEnabled(boolean enabled) {
		this.getObjectOrNull().setFastScrollEnabled(enabled);
		return this;
	}
	/**
	 * Set whether or not the fast scroller should always be shown in place of the standard scroll bars. 
	 * @param alwaysShow boolean: true if the fast scroller should always be displayed, false otherwise
	 */
	
	public JSGridView setFastScrollAlwaysVisible(boolean alwaysShow) {
		this.getObjectOrNull().setFastScrollAlwaysVisible(alwaysShow);
		return this;
	}
	/**
	 * Returns true if the fast scroller is set to always show on this view. 
	 * @return true if the fast scroller will always show
	 */
	public boolean IsFastScrollAlwaysVisible() {
		return this.getObjectOrNull().isFastScrollAlwaysVisible();
	}
	
	/**
	 * Smooth scroll to position.
	 *
	 * @param position
	 *            the position
	 */
	public void smoothScrollToPosition(int position) {
		this.getObjectOrNull().smoothScrollToPosition(position);
	}

	/**
	 * Smooth scroll by offset.
	 *
	 * @param offset
	 *            the offset
	 */
	public void smoothScrollByOffset(int offset) {
		this.getObjectOrNull().smoothScrollByOffset(offset);
	}

	/**
	 * Sets the selection.
	 *
	 * @param position            the new selection
	 * @return the JS grid view
	 */
	public JSGridView setSelection(int position) {
		this.getObjectOrNull().setSelection(position);
		return this;
	}

	/**
	 * Sets the selector.
	 *
	 * @param sel the new selector
	 */
	public JSGridView setSelector(Drawable sel) {
		this.getObjectOrNull().setSelector(sel);
		return this;
	}

	/**
	 * Gets the selector.
	 *
	 * @return the selector
	 */
	public Drawable getSelector() {
		return this.getObjectOrNull().getSelector();
	}
	
	/**
	 * Sets the view to show if the adapter is empty.
	 *
	 * @param emptyView the new empty view
	 */
	public JSGridView setEmptyView(View emptyView) {
		this.getObjectOrNull().setEmptyView(emptyView);
		return this;
	}
	
	/**
     * When the current adapter is empty, the AdapterView can display a special view
     * call the empty view. The empty view is used to provide feedback to the user
     * that no data is available in this AdapterView.
     *
     * @return The view to show if the adapter is empty.
     */
	public View getEmptyView() {
		return this.getObjectOrNull().getEmptyView();
	}
	
	
	/**
	 * When set to a non-zero value, the cache color hint indicates that this list is always drawn on top of a solid, single-color, opaque background. Zero means that what's behind this object is translucent (non solid) or is not made of a single color. 
	 * @param color Color
	 */
	public JSGridView setCacheColorHint(int color) {
		this.getObjectOrNull().setCacheColorHint(color);
		return this;
	}	
	
    /**
     * Controls whether the selection highlight drawable should be drawn on top of the item or
     * behind it.
     *
     * @param onTop If true, the selector will be drawn on the item it is highlighting. The default
     *        is false.
     *
     */
    public JSGridView setDrawSelectorOnTop(boolean onTop) {
        this.getObjectOrNull().setDrawSelectorOnTop(onTop);
        return this;
    }
	
	/**
	 * Describes how the child views are horizontally aligned. Defaults to Gravity.LEFT
	 *
	 * @param gravity the gravity
	 * @return the JS grid view
	 */
	public JSGridView setGravity(int gravity) {
		this.getObjectOrNull().setGravity(gravity);
		return this;
	}

	/**
	 * Set the amount of horizontal (x) spacing to place between each item
     * in the grid.
	 *
	 * @param horizontalSpacing the horizontal spacing
	 * @return the JS grid view
	 */
	public JSGridView setHorizontalSpacing(int horizontalSpacing) {
		this.getObjectOrNull().setHorizontalSpacing(horizontalSpacing);
		return this;
	}

	/**
	 * Set the amount of vertical (y) spacing to place between each item
     * in the grid.
	 *
	 * @param verticalSpacing the vertical spacing
	 * @return the JS grid view
	 */
	public JSGridView setVerticalSpacing(int verticalSpacing) {
		this.getObjectOrNull().setVerticalSpacing(verticalSpacing);
		return this;
	}

	/**
	 * Control how items are stretched to fill their space.
	 *
	 * @param stretchMode the stretch mode
	 * @return the JS grid view
	 */
	public JSGridView setStretchMode(int stretchMode) {
		this.getObjectOrNull().setStretchMode(stretchMode);
		return this;
	}

	/**
	 * Gets the stretch mode.
	 *
	 * @return the stretch mode
	 */
	public int getStretchMode() {
		return this.getObjectOrNull().getStretchMode();
	}

	/**
	 * Sets the column width.
	 *
	 * @param columnWidth the column width
	 * @return the JS grid view
	 */
	public JSGridView setColumnWidth(int columnWidth) {
		this.getObjectOrNull().setColumnWidth(columnWidth);
		return this;
	}

	/**
	 * Sets the num columns.
	 *
	 * @param numColumns the num columns
	 * @return the JS grid view
	 */
	public JSGridView setNumColumns(int numColumns) {
		this.getObjectOrNull().setNumColumns(numColumns);
		return this;
	}

	/**
	 * Gets the num columns.
	 *
	 * @return the num columns
	 */
	public int getNumColumns() {
		return this.getObjectOrNull().getNumColumns();
	}

}
