/*
 * See the file "LICENSE" for the full license governing this code.
 */
package com.codefupanda.genie.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.codefupanda.genie.R;
import com.codefupanda.genie.TasksActivity;
import com.codefupanda.genie.constant.Constants;
import com.codefupanda.genie.entity.Category;
import com.codefupanda.genie.entity.Wish;

/**
 * 
 * @author Shashank
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
 
    private Context context;
    private List<Category> categories;
    private Map<Category, List<Wish>> categoryWiseWishes;
 
    public ExpandableListAdapter(Context context, List<Category> listDataHeader,
    		Map<Category, List<Wish>> listChildData) {
        this.context = context;
        this.categories = listDataHeader;
        this.categoryWiseWishes = listChildData;
    }
 
    @Override
    public Wish getChild(int categoryIndex, int wishIndex) {
    	Wish wish = null;
    	List<Wish> list = categoryWiseWishes.get(this.categories.get(categoryIndex));
    	if(list != null) {
    		wish = list.get(wishIndex);
    	}
    	return wish;
    }
 
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
 
    @Override
    public View getChildView(int groupPosition, final int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {
 
        final Wish wish = (Wish) getChild(groupPosition, childPosition);
 
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_wish, null);
        }
        TextView wishIdTextView = (TextView) convertView
                .findViewById(R.id.wishId);
        wishIdTextView.setText(String.valueOf(wish.getId()));

        TextView wishTextView = (TextView) convertView
                .findViewById(R.id.wishTextView);
        wishTextView.setText(wish.getTitle());

        // On click of tasks icon, redirect to TasksActivity
        TextView tasksTextView = (TextView) convertView
        		.findViewById(R.id.tasks);
        tasksTextView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View tasksView) {
				Intent intent = new Intent(context, TasksActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra(Constants.WISH_ID, wish.getId());
				context.startActivity(intent);
			}
		});

        return convertView;
    }
 
    @Override
    public int getChildrenCount(int groupPosition) {
    	int size = 0;
    	
    	Category category = this.categories.get(groupPosition);
        List<Wish> wishes = categoryWiseWishes.get(category);
        if(wishes != null) {
        	size = wishes.size();
        }
        return size;
    }
 
    @Override
    public Category getGroup(int groupPosition) {
        return this.categories.get(groupPosition);
    }
 
    @Override
    public int getGroupCount() {
        return this.categories.size();
    }
 
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
 
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
    	Category category = (Category) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_category, null);
        }
 
        TextView categoryTextView = (TextView) convertView
                .findViewById(R.id.category_text_view);
        categoryTextView.setTypeface(null, Typeface.BOLD);
        categoryTextView.setText(category.getName());
        TextView wishCount = (TextView) convertView.findViewById(R.id.wishCount);
		wishCount.setText("(" + getChildrenCount(groupPosition) + ")");
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
 
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

	/**
	 * @return the categories
	 */
	public List<Category> getCategories() {
		return categories;
	}

	/**
	 * @param categories the categories to set
	 */
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	/**
	 * @return the categoryWiseWishes
	 */
	public Map<Category, List<Wish>> getCategoryWiseWishes() {
		return categoryWiseWishes;
	}

	/**
	 * @param categoryWiseWishes the categoryWiseWishes to set
	 */
	public void setCategoryWiseWishes(Map<Category, List<Wish>> categoryWiseWishes) {
		this.categoryWiseWishes = categoryWiseWishes;
	}
}
