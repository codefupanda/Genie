/*
 * See the file "LICENSE" for the full license governing this code.
 */
package com.codefupanda.genie;

import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.codefupanda.genie.constant.Constants;
import com.codefupanda.genie.dao.TaskDao;
import com.codefupanda.genie.dao.impl.TaskDaoImpl;
import com.codefupanda.genie.entity.Task;
import com.codefupanda.genie.entity.Wish;
import com.codefupanda.genie.util.AndroiUiUtil;
import com.codefupanda.genie.util.Util;

/**
 * The tasks activity.
 * 
 * @author Shashank
 */
public class TasksActivity extends ActionBarActivity {

	private TaskDao taskDao;
	private int wishId;
	private List<Task> tasks;
	private ArrayAdapter<Task> tasksListAdapater;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroiUiUtil.customActionbar(getApplicationContext(), getSupportActionBar());
		setContentView(R.layout.activity_tasks);
		taskDao = new TaskDaoImpl(getApplicationContext());
	
		Bundle extras = getIntent().getExtras();
		wishId = extras.getInt(Constants.WISH_ID);
		
		ListView tasksListView = (ListView) findViewById(R.id.tasks);
		tasks = taskDao.getAllTasksForWish(wishId);
		tasksListAdapater = new ArrayAdapter<Task>(getApplicationContext(), R.layout.list_task, tasks);
		tasksListView.setAdapter(tasksListAdapater);
		
		tasksListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {

				final Dialog dialog = new Dialog(TasksActivity.this);
				View view = getLayoutInflater().inflate(R.layout.task_notes_dialog, null);
				dialog.setContentView(view);
				
				final Task task = (Task) tasksListAdapater.getItem(position);
				
				// Just a null check
				if(task == null) {
					return ;
				}
				
				dialog.setTitle(task.getTitle());
				
				final TextView notesTextView = (TextView) view.findViewById(R.id.notes);
				notesTextView.setText(task.getDescription());
				
				final TextView completionText = (TextView) view.findViewById(R.id.completion_text);
				completionText.setText(task.getCompletion() + "");
				
				final SeekBar completionBar = (SeekBar) view.findViewById(R.id.completion_seekbar);
				completionBar.setProgress(task.getCompletion());
				
				completionBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

					/** Show the changed progress as text. */
					@Override
					public void onProgressChanged(SeekBar completionBar, int arg1,
							boolean arg2) {
						completionText.setText(completionBar.getProgress() + "");
					}

					@Override
					public void onStartTrackingTouch(SeekBar arg0) {
					}

					/** Update value when progress is stopped. */
					@Override
					public void onStopTrackingTouch(SeekBar completionBar) {
						task.setCompletion(completionBar.getProgress());
						taskDao.update(task);
					}
					
				});
				Button okButton = (Button) view.findViewById(R.id.ok);
				okButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						dialog.dismiss();
					}
				});
				
				Button editButton = (Button) view.findViewById(R.id.edit);
				editButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						dialog.dismiss();
						// Going to Edit mode, isUpdate true
						newOrUpdateTaskDialog(task, true);
					}
				});
				dialog.show();
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_new:
				newOrUpdateTaskDialog(new Task(), false);
				return true;
	        case R.id.action_help:
	        	startActivity(new Intent(getApplicationContext(), HelpActivity.class));
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	/*
	 * Shows new or update layout, takes care of button clicks.
	 */
	private void newOrUpdateTaskDialog(final Task task, final boolean isUpdate) {
		final Dialog dialog = new Dialog(TasksActivity.this);
		View newTaskView = getLayoutInflater().inflate(
				R.layout.new_task_dialog, null);
		dialog.setContentView(newTaskView);

		Button cancel = (Button) newTaskView
				.findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				dialog.dismiss();
			}
		});

		final EditText taskTitleEditText = (EditText) newTaskView
				.findViewById(R.id.taskTitle);
		taskTitleEditText.setText(task.getTitle());
		
		final EditText taskNotesEditText = (EditText) newTaskView
				.findViewById(R.id.taskNotes);
		taskNotesEditText.setText(task.getDescription());

		Button create = (Button) newTaskView
				.findViewById(R.id.create);
		if(isUpdate){
			create.setText(getResources().getString(R.string.update));
		}
		
		create.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String taskTitle = taskTitleEditText.getText()
						.toString();
				String taskNotes = taskNotesEditText.getText().toString();
				if (!Util.isBlank(taskTitle)) {
					task.setWish(new Wish(wishId, null, null, null, null));
					task.setTitle(taskTitle);
					task.setDescription(taskNotes);
					if(isUpdate) {
						taskDao.update(task);
						AndroiUiUtil.toast(getBaseContext(),
								R.string.update_task_success);
					} else {
						taskDao.add(task);
						tasks.add(task);
						AndroiUiUtil.toast(getBaseContext(),
								R.string.add_task_success);
					}
					dialog.dismiss();
					tasksListAdapater.notifyDataSetChanged();
				}

			}
		});
		if (!isUpdate) {
			dialog.setTitle(getResources().getString(R.string.action_new));
		} else {
			dialog.setTitle(getResources().getString(R.string.update));
		}
		dialog.show();
	}
}
