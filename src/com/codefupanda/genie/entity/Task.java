/*
 * See the file "LICENSE" for the full license governing this code.
 */
package com.codefupanda.genie.entity;


/**
 * The entity to hold task data.
 * 
 * @author Shashank
 */
public class Task {
	private int id;
	private Wish wish;
	private String title;
	private String description;
	private int completion;

	/**
	 * Default constructor.
	 */
	public Task() {
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the wish
	 */
	public Wish getWish() {
		return wish;
	}

	/**
	 * @param wish
	 *            the wish to set
	 */
	public void setWish(Wish wish) {
		this.wish = wish;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the completion
	 */
	public int getCompletion() {
		return completion;
	}

	/**
	 * @param completion
	 *            the completion to set
	 */
	public void setCompletion(int completion) {
		this.completion = completion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return title;
	}

}