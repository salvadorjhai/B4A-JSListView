/* r8	- update on getView
 * r7	- implemented setViewId
 * r6	- add: itemAddAt 
 * 		- add: ItemClearAll
 * 		- add: HeaderView, FooterView
 * 		- add: EmptyView
 * 		- add: overscrollmodes
 * 		- add: setCacheColorHint
 * 		- removed: choice mode
 * r5	- public release
 */
package com.salvadorjhai.widgets;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BA.ActivityObject;
import anywheresoftware.b4a.BA.Events;
import anywheresoftware.b4a.BA.Hide;
import anywheresoftware.b4a.BA.ShortName;
import anywheresoftware.b4a.keywords.Common.DesignerCustomView;
import anywheresoftware.b4a.objects.CustomViewWrapper;
import anywheresoftware.b4a.objects.LabelWrapper;
import anywheresoftware.b4a.objects.PanelWrapper;
import anywheresoftware.b4a.objects.ViewWrapper;
import anywheresoftware.b4a.objects.collections.List;
import anywheresoftware.b4a.objects.collections.Map;


/**
 * JSListView base on Android Listview.
 * You can use designer to create your item layout, just inflate this layout using panel LoadLayout.
 * Use OnGetView event to handle and display item layout.
 * IMPORTANT: THIS JSLISTVIEW WILL NO LONGER BE SUPPORTED, USE JSLISTVIEW2 INSTEAD.
 */

@ShortName("JSListView")

@Events(values = { "OnGetView(position As int, itemLayout As ItemViewLayout, forViewUpdate As boolean)",
		"OnItemClick(view As ItemViewLayout, position As int)", 
		"OnItemLongClick(view As ItemViewLayout, position As int)"})

@ActivityObject
public class JSListView extends ViewWrapper<ListView> implements DesignerCustomView {
	
	/** The m data. */
	private List mData = new List();
	
	/** The m BA. */
	private BA mBA = null;
	
	/** The m event name. */
	private String mEventName = "";

	/** The choice mode none. */
	private final int CHOICE_MODE_NONE = 0;
	
	/** The choice mode single. */
	private final int CHOICE_MODE_SINGLE = 1;
	
	/** The choice mode multiple. */
	private final int CHOICE_MODE_MULTIPLE = 2;
	
	public final int OVER_SCROLL_ALWAYS = 0;
	public final int OVER_SCROLL_IF_CONTENT_SCROLLS = 1;
	public final int OVER_SCROLL_NEVER = 2;

	/** The adapter. */
	private SimpleListAdapter adapter = null;
	
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
	@Hide
	public void _initialize(BA ba, Object paramObject, String eventName) {
		innerInitialize(ba, eventName, false);
	}
	
	/* (non-Javadoc)
	 * @see anywheresoftware.b4a.keywords.Common.DesignerCustomView#DesignerCreateView(anywheresoftware.b4a.objects.PanelWrapper, anywheresoftware.b4a.objects.LabelWrapper, anywheresoftware.b4a.objects.collections.Map)
	 */
	
	@Override @Hide
	public void innerInitialize(BA ba, String eventName, boolean keepOldObject) {
		this.mBA = ba;
		this.mEventName = eventName;

		try {
			// set object and props
			if (!keepOldObject) {			
				final ListView lv = new ListView(ba.context);
				this.setObject(lv);
				
				this.mData = new List();
				this.mData.Initialize();

			}

			// Inner Initialize
		    super.innerInitialize(ba, eventName, true);
			
			if (eventName.length() != 0) {
				// onItemClick
				this.getObjectOrNull().setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						String event = mEventName + "_OnItemClick";		
						ItemViewLayout iv = (ItemViewLayout)view.getTag();
						mBA.raiseEvent(this, event.toLowerCase(BA.cul), new Object[] {iv, position});												
					}			
				});
				
				// onItemLongClick
				this.getObjectOrNull().setOnItemLongClickListener(new OnItemLongClickListener() {
					@Override
					public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
						String event = mEventName + "_OnItemLongClick";								
						ItemViewLayout iv = (ItemViewLayout)view.getTag();
						mBA.raiseEvent(this, event.toLowerCase(BA.cul), new Object[] {iv, position});
						return false;
					}					
				});

			}
			
		} catch (Exception e) {
			BA.LogError(e.getMessage());
		}
	}
	
	@Override
	public void DesignerCreateView(PanelWrapper paramPanelWrapper, LabelWrapper paramLabelWrapper, Map paramMap) {
		CustomViewWrapper.replaceBaseWithView2(paramPanelWrapper, this.getObjectOrNull());
	}
	
	/**
	 * Set data source, it can be a List, array, cursor.
	 *
	 * @param data - List, Array, Cursor
	 */
	public void setDataSource(List data) {		
		this.mData = data;
		this.adapter = new SimpleListAdapter();
		this.getObjectOrNull().setAdapter(this.adapter);
	}
	
	/**
	 * Get data source.
	 *
	 * @return list of data
	 */
	public List getDataSource() {
		return this.mData;
	}
	
	/**
	 * Item add
	 *
	 * @param data the data
	 */
	public void ItemAdd(Object data) {	
		try {
			this.mData.Add(data);
			this.adapter.notifyDataSetChanged();
		} catch (Exception e) {
			BA.LogError(e.getMessage());
		}	
	}
	
	/**
	 * Add item at specific position
	 * @param Index position
	 * @param data data
	 */
	public void ItemAddAt(int Index, Object data) {	
		try {
			this.mData.InsertAt(Index, data);
			this.adapter.notifyDataSetChanged();
		} catch (Exception e) {
			BA.LogError(e.getMessage());
		}	
	}
	
	/**
	 * Item remove at.
	 *
	 * @param position the position
	 */
	public void ItemRemoveAt(int position) {
		try {
			this.mData.RemoveAt(position);
			this.adapter.notifyDataSetChanged();				
		} catch (Exception e) {
			BA.LogError(e.getMessage());
		}	
	}
	
	/**
	 * Item update at.
	 *
	 * @param position the position
	 * @param data the new data
	 */
	public void ItemUpdateAt(int position, Object data) {
		try {
			this.mData.Set(position, data);
			this.adapter.notifyDataSetChanged();				
		} catch (Exception e) {
			BA.LogError(e.getMessage());
		}	
	}
	
	/**
	 * Clear all item on the listview
	 */
	public void ItemClearAll() {
		try {
			this.mData.Clear();
			this.adapter.notifyDataSetChanged();				
		} catch (Exception e) {
			BA.LogError(e.getMessage());
		}	
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
	public void setFastScrollEnabled(boolean enabled) {
		this.getObjectOrNull().setFastScrollEnabled(enabled);
		
		
	}
	/**
	 * Set whether or not the fast scroller should always be shown in place of the standard scroll bars. 
	 * @param alwaysShow boolean: true if the fast scroller should always be displayed, false otherwise
	 */
	
	public void setFastScrollAlwaysVisible(boolean alwaysShow) {
		this.getObjectOrNull().setFastScrollAlwaysVisible(alwaysShow);
		
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
	 * @param position the position
	 */
	public void smoothScrollToPosition(int position) {
		this.getObjectOrNull().smoothScrollToPosition(position);
	}

	/**
	 * Smooth scroll by offset.
	 *
	 * @param offset the offset
	 */
	public void smoothScrollByOffset(int offset) {
		this.getObjectOrNull().smoothScrollByOffset(offset);
	}

	/**
	 * Sets the selection.
	 *
	 * @param position the new selection
	 */
	public void setSelection(int position) {
		this.getObjectOrNull().setSelection(position);
	}

	/**
	 * Gets the items can focus.
	 *
	 * @return the items can focus
	 */
	public boolean getItemsCanFocus() {
		return this.getObjectOrNull().getItemsCanFocus();
	}

	/**
	 * Gets the divider.
	 *
	 * @return the divider
	 */
	public Drawable getDivider() {
		return this.getObjectOrNull().getDivider();
	}

	/**
	 * Sets the divider.
	 *
	 * @param divider the new divider
	 */
	public void setDivider(Drawable divider) {
		this.getObjectOrNull().setDivider(divider);
	}

	/**
	 * Gets the divider height.
	 *
	 * @return the divider height
	 */
	public int getDividerHeight() {
		return this.getObjectOrNull().getDividerHeight();
	}

	/**
	 * Sets the divider height.
	 *
	 * @param height the new divider height
	 */
	public void setDividerHeight(int height) {
		this.getObjectOrNull().setDividerHeight(height);
	}

	/**
	 * Sets the header dividers enabled.
	 *
	 * @param headerDividersEnabled the new header dividers enabled
	 */
	public void setHeaderDividersEnabled(boolean headerDividersEnabled) {
		this.getObjectOrNull().setHeaderDividersEnabled(headerDividersEnabled);
	}

	/**
	 * Sets the footer dividers enabled.
	 *
	 * @param footerDividersEnabled the new footer dividers enabled
	 */
	public void setFooterDividersEnabled(boolean footerDividersEnabled) {
		this.getObjectOrNull().setFooterDividersEnabled(footerDividersEnabled);
	}

	/**
	 * Sets the overscroll header.
	 *
	 * @param header the new overscroll header
	 */
	public void setOverscrollHeader(Drawable header) {
		this.getObjectOrNull().setOverscrollHeader(header);
	}

	/**
	 * Gets the overscroll header.
	 *
	 * @return the overscroll header
	 */
	public Drawable getOverscrollHeader() {
		return this.getObjectOrNull().getOverscrollHeader();
	}

	/**
	 * Sets the overscroll footer.
	 *
	 * @param footer the new overscroll footer
	 */
	public void setOverscrollFooter(Drawable footer) {		
		this.getObjectOrNull().setOverscrollFooter(footer);
	}

	/**
	 * Gets the overscroll footer.
	 *
	 * @return the overscroll footer
	 */
	public Drawable getOverscrollFooter() {
		return this.getObjectOrNull().getOverscrollFooter();
	}
	
	/**
	 * Set the over-scroll mode for this view. Valid over-scroll modes are OVER_SCROLL_ALWAYS (default), OVER_SCROLL_IF_CONTENT_SCROLLS (allow over-scrolling only if the view content is larger than the container), or OVER_SCROLL_NEVER. Setting the over-scroll mode of a view will have an effect only if the view is capable of scrolling.
	 * @param mode OVER_SCROLL_ALWAYS, OVER_SCROLL_IF_CONTENT_SCROLLS, OVER_SCROLL_NEVER
	 */
	public void setOverScrollMode(int mode) {
		this.getObjectOrNull().setOverScrollMode(mode);
		
	}
	
	/**
	 * Returns the over-scroll mode for this view. The result will be one of OVER_SCROLL_ALWAYS (default), OVER_SCROLL_IF_CONTENT_SCROLLS (allow over-scrolling only if the view content is larger than the container), or OVER_SCROLL_NEVER.
	 * @return This view's over-scroll mode.
	 */
	public int getOverScrollMode() {
		return this.getObjectOrNull().getOverScrollMode();
	}
	

	/**
	 * Sets the selector.
	 *
	 * @param sel the new selector
	 */
	public void setSelector(Drawable sel) {
		this.getObjectOrNull().setSelector(sel);
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
     * Add a fixed view to appear at the top of the list. If addHeaderView is
     * called more than once, the views will appear in the order they were
     * added. Views added using this call can take focus if they want.
     * 
     * NOTE: Call this before calling setAdapter. This is so ListView can wrap
     * the supplied cursor with one that will also account for header and footer
     * views.
     *
     * @param v The view to add.
     * @param data Data to associate with this view
     * @param isSelectable whether the item is selectable
     */
	public void addHeaderView(View v, Object data, boolean isSelectable) {
		this.getObjectOrNull().addHeaderView(v, data, isSelectable);
	}
	
	/**
     * Add a fixed view to appear at the bottom of the list. If addFooterView is
     * called more than once, the views will appear in the order they were
     * added. Views added using this call can take focus if they want.
     * 
     * NOTE: Call this before calling setAdapter. This is so ListView can wrap
     * the supplied cursor with one that will also account for header and footer
     * views.
     *
     * @param v The view to add.
     * @param data Data to associate with this view
     * @param isSelectable true if the footer view can be selected
     */
	public void addFooterView(View v, Object data, boolean isSelectable) {
		this.getObjectOrNull().addFooterView(v, data, isSelectable);
	}
	
	/**
     * Sets the view to show if the adapter is empty
     */
	public void setEmptyView(View emptyView) {
		this.getObjectOrNull().setEmptyView(emptyView);
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
	public void setCacheColorHint(int color) {
		this.getObjectOrNull().setCacheColorHint(color);
	}
	
//	// TODO: IMPLEMENT CLICKABLE on Choice Mode
//	/**
//	 * Get/Defines the choice behavior for the List. 
//	 * @param choiceMode int: One of CHOICE_MODE_NONE, CHOICE_MODE_SINGLE, or CHOICE_MODE_MULTIPLE
//	 */
//	public void setChoiceMode(int choiceMode) {
//		this.getObjectOrNull().setChoiceMode(choiceMode);
//	}
//	
//	/**
//	 * Get the current choice behavior for the List. 
//	 * @return The current choice mode 
//	 */
//	public int getChoiceMode() {
//		return this.getObjectOrNull().getChoiceMode();
//	}

	/**
	 * The Class SimpleListAdapter.
	 */
	@ShortName("SimpleListAdapter")
	@Hide
	public class SimpleListAdapter extends BaseAdapter implements ListAdapter {

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getCount()
		 */
		@Override
		public int getCount() {
			return mData.getSize();
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getItem(int)
		 */
		@Override
		public Object getItem(int position) {
			return mData.Get(position);
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getItemId(int)
		 */
		@Override
		public long getItemId(int position) {
			return position;
		}
		
		/* (non-Javadoc)
		 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
//			ItemViewLayout iv = new ItemViewLayout();
//			
//			BALayout bl = new BALayout(mBA.context);
//			BALayout.LayoutParams p = new BALayout.LayoutParams(0, 0, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//			bl.setLayoutParams(p);
//			iv.container = bl;
//			
//			String event = mEventName + "_OnGetView";								
//			mBA.raiseEvent(this, event.toLowerCase(BA.cul), new Object[] {position, iv, false});					
//
//			iv.container.setLayoutParams(new AbsListView.LayoutParams(iv.width, iv.height));
//
//			convertView = iv.container;
//			convertView.setTag(iv);
//			return row;
			
			View row = convertView;								
			if (row == null) {
				if (mEventName.length() != 0) {
					ItemViewLayout iv = new ItemViewLayout();
					String event = mEventName + "_OnGetView";								
					mBA.raiseEvent(this, event.toLowerCase(BA.cul), new Object[] {position, iv, false});					

					iv.container.setLayoutParams(new AbsListView.LayoutParams(iv.width, iv.height));
		
					row = iv.container;
					row.setTag((ItemViewLayout)iv);					
				}
			} else {
				// request for update
				if (mEventName.length() != 0) {
					ItemViewLayout iv = (ItemViewLayout)row.getTag();
					
					String event = mEventName + "_OnGetView";						
					mBA.raiseEvent(this, event.toLowerCase(BA.cul), new Object[] {position, iv, true});
					
					iv.container.setLayoutParams(new AbsListView.LayoutParams(iv.width, iv.height));

					row = iv.container;
					row.setTag((ItemViewLayout)iv);
				}
			}
			
			return row;
		}

		
	}
	
	/**
	 * The Class ItemViewLayout.
	 */
	@ShortName("ItemViewLayout")
	public static class ItemViewLayout {
		
		/** The container. */
		private ViewGroup container = null;
		
		/** The height. */
		private int height;
		
		/** The width. */
		private int width;
				
		/**
		 * Gets the container.
		 *
		 * @return the container
		 */
		public ViewGroup getContainer() {
			return container;
		}

		/**
		 * Sets the container.
		 *
		 * @param container the new container
		 */
		public void setContainer(ViewGroup container) {
			this.container = container;
		}
		
		/**
		 * Find view with tag.
		 *
		 * @param tag the tag
		 * @return the view
		 */
		public View findViewWithTag(Object tag) {
			return (View)container.findViewWithTag(tag);		
		}
		
		/**
		 * Find view by id.
		 *
		 * @param id the id
		 * @return the view
		 */
		public View findViewById(int id) {
			return (View)container.findViewById(id);
		}
		
		/**
		 * Set id to view
		 * @param v View
		 * @param id
		 */
		public void setViewId(View v, int id) {
			v.setId(id);
		}

		/**
		 * Gets the height.
		 *
		 * @return the height
		 */
		public int getHeight() {
			return height;
		}

		/**
		 * Sets the height.
		 *
		 * @param height the new height
		 */
		public void setHeight(int height) {
			this.height = height;
		}

		/**
		 * Gets the width.
		 *
		 * @return the width
		 */
		public int getWidth() {
			return width;
		}

		/**
		 * Sets the width.
		 *
		 * @param width the new width
		 */
		public void setWidth(int width) {
			this.width = width;
		}
		
		
		
	}
	
}
