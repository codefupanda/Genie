package com.codefupanda.genie.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.codefupanda.genie.R;
import com.codefupanda.genie.entity.Task;

/**
 * List Adapater for tasks
 * 
 * @author Shashank
 */
public class TasksListAdapater extends BaseAdapter {

	private List<Task> tasks;
	private Context context;
	
	public TasksListAdapater(Context context, List<Task> tasks) {
		this.context = context;
		this.tasks = tasks;
	}
	
	@Override
	public int getCount() {
		return tasks.size();
	}

	@Override
	public Task getItem(int position) {
		return tasks.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		if(view == null) {
			 LayoutInflater infalInflater = (LayoutInflater) this.context
	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			 view = infalInflater.inflate(R.layout.list_task, null);
		}
		TextView taskTextView = (TextView) view.findViewById(R.id.task_text_view);
		taskTextView.setText(tasks.get(position).getTitle());
		return view;
	}

}
