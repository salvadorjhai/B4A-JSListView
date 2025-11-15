/* r10	- added gridview
 * 		- updated to fluent syntax
 * r9	- added stack and transcript mode
 * r8	- update on getView
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

import com.salvadorjhai.data.JSViewHolder;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BA.ActivityObject;
import anywheresoftware.b4a.BA.Author;
import anywheresoftware.b4a.BA.Events;
import anywheresoftware.b4a.BA.Hide;
import anywheresoftware.b4a.BA.ShortName;
import anywheresoftware.b4a.BA.Version;
import anywheresoftware.b4a.keywords.Common.DesignerCustomView;
import anywheresoftware.b4a.objects.CustomViewWrapper;
import anywheresoftware.b4a.objects.LabelWrapper;
import anywheresoftware.b4a.objects.PanelWrapper;
import anywheresoftware.b4a.objects.ViewWrapper;
import anywheresoftware.b4a.objects.collections.Map;

// TODO: Auto-generated Javadoc
/**
 * JSListView2 base on Android Listview. You can use designer to create your
 * item layout, just inflate the item layout using panel LoadLayout. Use this
 * JSListView2 together with JSListAdapter or JSCursorAdapter
 * 
 * @author salvadorjhai
 * 
 */

@Version(1.0f)
@Author("salvadorjhai")
@ShortName("JSListView2")
@Events(values = { "OnItemClick(view As JSViewHolder, position As int)",
		"OnItemLongClick(view As JSViewHolder, position As int)" })
@ActivityObject
public class JSListView2 extends ViewWrapper<ListView> implements DesignerCustomView {

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

	/** The over scroll always. */
	public final int OVER_SCROLL_ALWAYS = 0;

	/** The over scroll if content scrolls. */
	public final int OVER_SCROLL_IF_CONTENT_SCROLLS = 1;

	/** The over scroll never. */
	public final int OVER_SCROLL_NEVER = 2;

	/**
	 * The list will automatically scroll to the bottom, no matter what items
	 * are currently visible.
	 **/
	public final int TRANSCRIPT_MODE_DISABLED = 0;
	/** Disables transcript mode. This is the default value. **/
	public final int TRANSCRIPT_MODE_NORMAL = 1;
	/**
	 * The list will automatically scroll to the bottom when a data set change
	 * notification is received and only if the last item is already visible on
	 * screen.
	 **/
	public final int TRANSCRIPT_MODE_ALWAYSSCROLL = 2;

	/** The adapter. */
	private ListAdapter adapter = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * anywheresoftware.b4a.objects.ViewWrapper#Initialize(anywheresoftware.b4a.
	 * BA, java.lang.String)
	 */
	@Override
	public void Initialize(BA ba, String eventName) {
		_initialize(ba, null, eventName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see anywheresoftware.b4a.keywords.Common.DesignerCustomView#_initialize(
	 * anywheresoftware.b4a.BA, java.lang.Object, java.lang.String)
	 */
	@Override
	@Hide
	public void _initialize(BA ba, Object paramObject, String eventName) {
		innerInitialize(ba, eventName, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see anywheresoftware.b4a.keywords.Common.DesignerCustomView#
	 * DesignerCreateView(anywheresoftware.b4a.objects.PanelWrapper,
	 * anywheresoftware.b4a.objects.LabelWrapper,
	 * anywheresoftware.b4a.objects.collections.Map)
	 */

	@Override
	@Hide
	public void innerInitialize(BA ba, String eventName, boolean keepOldObject) {
		this.mBA = ba;
		this.mEventName = eventName;

		try {
			// set object and props
			if (!keepOldObject) {
				final ListView lv = new ListView(ba.context);
				this.setObject(lv);
			}

			// Inner Initialize
			super.innerInitialize(ba, eventName, true);

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see anywheresoftware.b4a.keywords.Common.DesignerCustomView#
	 * DesignerCreateView(anywheresoftware.b4a.objects.PanelWrapper,
	 * anywheresoftware.b4a.objects.LabelWrapper,
	 * anywheresoftware.b4a.objects.collections.Map)
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
	 * 
	 * @return true if fast scroll is enabled, false otherwise
	 */
	public boolean IsFastScrollEnabled() {
		return this.getObjectOrNull().isFastScrollEnabled();
	}

	/**
	 * Specifies whether fast scrolling is enabled or disabled.
	 * 
	 * @param enabled
	 *            boolean: true to enable fast scrolling, false otherwise
	 */
	public JSListView2 setFastScrollEnabled(boolean enabled) {
		this.getObjectOrNull().setFastScrollEnabled(enabled);
		return this;
	}

	/**
	 * Set whether or not the fast scroller should always be shown in place of
	 * the standard scroll bars.
	 * 
	 * @param alwaysShow
	 *            boolean: true if the fast scroller should always be displayed,
	 *            false otherwise
	 */

	public JSListView2 setFastScrollAlwaysVisible(boolean alwaysShow) {
		this.getObjectOrNull().setFastScrollAlwaysVisible(alwaysShow);
		return this;
	}

	/**
	 * Returns true if the fast scroller is set to always show on this view.
	 * 
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
	 * @param position
	 *            the new selection
	 */
	public JSListView2 setSelection(int position) {
		this.getObjectOrNull().setSelection(position);
		return this;
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
	 * @param divider
	 *            the new divider
	 */
	public JSListView2 setDivider(Drawable divider) {
		this.getObjectOrNull().setDivider(divider);
		return this;
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
	 * @param height
	 *            the new divider height
	 */
	public JSListView2 setDividerHeight(int height) {
		this.getObjectOrNull().setDividerHeight(height);
		return this;
	}

	/**
	 * Sets the header dividers enabled.
	 *
	 * @param headerDividersEnabled
	 *            the new header dividers enabled
	 */
	public JSListView2 setHeaderDividersEnabled(boolean headerDividersEnabled) {
		this.getObjectOrNull().setHeaderDividersEnabled(headerDividersEnabled);
		return this;
	}

	/**
	 * Sets the footer dividers enabled.
	 *
	 * @param footerDividersEnabled
	 *            the new footer dividers enabled
	 */
	public JSListView2 setFooterDividersEnabled(boolean footerDividersEnabled) {
		this.getObjectOrNull().setFooterDividersEnabled(footerDividersEnabled);
		return this;
	}

	/**
	 * Sets the overscroll header.
	 *
	 * @param header
	 *            the new overscroll header
	 */
	public JSListView2 setOverscrollHeader(Drawable header) {
		this.getObjectOrNull().setOverscrollHeader(header);
		return this;
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
	 * @param footer
	 *            the new overscroll footer
	 */
	public JSListView2 setOverscrollFooter(Drawable footer) {
		this.getObjectOrNull().setOverscrollFooter(footer);
		return this;
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
	 * Set the over-scroll mode for this view. Valid over-scroll modes are
	 * OVER_SCROLL_ALWAYS (default), OVER_SCROLL_IF_CONTENT_SCROLLS (allow
	 * over-scrolling only if the view content is larger than the container), or
	 * OVER_SCROLL_NEVER. Setting the over-scroll mode of a view will have an
	 * effect only if the view is capable of scrolling.
	 * 
	 * @param mode
	 *            OVER_SCROLL_ALWAYS, OVER_SCROLL_IF_CONTENT_SCROLLS,
	 *            OVER_SCROLL_NEVER
	 */
	public JSListView2 setOverScrollMode(int mode) {
		this.getObjectOrNull().setOverScrollMode(mode);
		return this;
	}

	/**
	 * Returns the over-scroll mode for this view. The result will be one of
	 * OVER_SCROLL_ALWAYS (default), OVER_SCROLL_IF_CONTENT_SCROLLS (allow
	 * over-scrolling only if the view content is larger than the container), or
	 * OVER_SCROLL_NEVER.
	 * 
	 * @return This view's over-scroll mode.
	 */
	public int getOverScrollMode() {
		return this.getObjectOrNull().getOverScrollMode();
	}

	/**
	 * Sets the selector.
	 *
	 * @param sel
	 *            the new selector
	 */
	public JSListView2 setSelector(Drawable sel) {
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
	 * Add a fixed view to appear at the top of the list. If addHeaderView is
	 * called more than once, the views will appear in the order they were
	 * added. Views added using this call can take focus if they want.
	 * 
	 * NOTE: Call this before calling setAdapter. This is so ListView can wrap
	 * the supplied cursor with one that will also account for header and footer
	 * views.
	 *
	 * @param v
	 *            The view to add.
	 * @param data
	 *            Data to associate with this view
	 * @param isSelectable
	 *            whether the item is selectable
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
	 * @param v
	 *            The view to add.
	 * @param data
	 *            Data to associate with this view
	 * @param isSelectable
	 *            true if the footer view can be selected
	 */
	public void addFooterView(View v, Object data, boolean isSelectable) {
		this.getObjectOrNull().addFooterView(v, data, isSelectable);
	}

	/**
	 * Sets the view to show if the adapter is empty.
	 *
	 * @param emptyView
	 *            the new empty view
	 */
	public JSListView2 setEmptyView(View emptyView) {
		this.getObjectOrNull().setEmptyView(emptyView);
		return this;
	}

	/**
	 * When the current adapter is empty, the AdapterView can display a special
	 * view call the empty view. The empty view is used to provide feedback to
	 * the user that no data is available in this AdapterView.
	 *
	 * @return The view to show if the adapter is empty.
	 */
	public View getEmptyView() {
		return this.getObjectOrNull().getEmptyView();
	}

	/**
	 * When set to a non-zero value, the cache color hint indicates that this
	 * list is always drawn on top of a solid, single-color, opaque background.
	 * Zero means that what's behind this object is translucent (non solid) or
	 * is not made of a single color.
	 * 
	 * @param color
	 *            Color
	 */
	public JSListView2 setCacheColorHint(int color) {
		this.getObjectOrNull().setCacheColorHint(color);
		return this;
	}

	/**
	 * Puts the list or grid into transcript mode. In this mode the list or grid
	 * will always scroll to the bottom to show new items.
	 *
	 * @param transcriptMode
	 *            - 0 - disabled, 1 - normal, 2 - alwaysScroll
	 */
	public JSListView2 setTranscriptMode(int transcriptMode) {
		this.getObjectOrNull().setTranscriptMode(transcriptMode);
		return this;
	}

	/**
	 * Returns the current transcript mode.
	 *
	 * @return 0 - disabled, 1 - normal, 2 - alwaysScroll
	 */
	public int getTranscriptMode() {
		return this.getObjectOrNull().getTranscriptMode();
	}

	/**
	 * When stack from bottom is set to true, the list fills its content
	 * starting from the bottom of the view.
	 *
	 * @param stackFromBottom
	 *            true to pin the view's content to the bottom edge, false to
	 *            pin the view's content to the top edge
	 */
	public JSListView2 setStackFromBottom(boolean stackFromBottom) {
		this.getObjectOrNull().setStackFromBottom(stackFromBottom);
		return this;
	}

	/**
	 * Indicates whether the content of this view is pinned to, or stacked from,
	 * the bottom edge.
	 *
	 * @return true if the content is stacked from the bottom edge, false
	 *         otherwise
	 */
	public boolean isStackFromBottom() {
		return this.getObjectOrNull().isStackFromBottom();
	}

	/**
	 * Controls whether the selection highlight drawable should be drawn on top
	 * of the item or behind it.
	 *
	 * @param onTop
	 *            If true, the selector will be drawn on the item it is
	 *            highlighting. The default is false.
	 *
	 */
	public JSListView2 setDrawSelectorOnTop(boolean onTop) {
		this.getObjectOrNull().setDrawSelectorOnTop(onTop);
		return this;
	}

	// // TODO: IMPLEMENT CLICKABLE on Choice Mode
	// /**
	// * Get/Defines the choice behavior for the List.
	// * @param choiceMode int: One of CHOICE_MODE_NONE, CHOICE_MODE_SINGLE, or
	// CHOICE_MODE_MULTIPLE
	// */
	// public void setChoiceMode(int choiceMode) {
	// this.getObjectOrNull().setChoiceMode(choiceMode);
	// }
	//
	// /**
	// * Get the current choice behavior for the List.
	// * @return The current choice mode
	// */
	// public int getChoiceMode() {
	// return this.getObjectOrNull().getChoiceMode();
	// }

}
