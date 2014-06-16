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

/**
 * @author Shashank
 *
 */
public class Category {
	private int id;
	private String name;
	private boolean userCreated;
	
	public Category() {
	}

	/**
	 * @param id
	 * @param name
	 * @param userCreated
	 */
	public Category(int id, String name, boolean userCreated) {
		this.id = id;
		this.name = name;
		this.userCreated = userCreated;
	}

	/**
	 * 
	 * @param id
	 */
	public Category(int id) {
		this.id = id;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the userCreated
	 */
	public boolean isUserCreated() {
		return userCreated;
	}

	/**
	 * @param userCreated the userCreated to set
	 */
	public void setUserCreated(boolean userCreated) {
		this.userCreated = userCreated;
	}
	
}
