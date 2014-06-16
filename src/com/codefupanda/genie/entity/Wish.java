/*
 * Copyright (C) Shashank Kulkarni - Shashank.physics AT gmail DOT com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.codefupanda.genie.entity;

import java.util.Date;

/**
 * The entity to hold wish data.
 * 
 * @author Shashank
 */
public class Wish {
	private int id;
	private Category category;
	private String title;	// find a btr name
	private String description;
	private Date endDate;
	
	/**
	 * Default constructor.
	 */
	public Wish() {
	}

	/**
	 * @param id
	 * @param category
	 * @param title
	 * @param description
	 */
	public Wish(int id, Category category, String title, String description, Date endDate) {
		this.id = id;
		this.category = category;
		this.title = title;
		this.description = description;
		this.endDate = endDate;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
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
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Wish [id=" + id + ", category=" + category + ", title=" + title
				+ ", description=" + description + ", endDate=" + endDate + "]";
	}
}
