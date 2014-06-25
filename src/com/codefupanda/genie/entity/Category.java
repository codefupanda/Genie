/*
 * See the file "LICENSE" for the full license governing this code.
 */
package com.codefupanda.genie.entity;

import java.io.Serializable;

/**
 * @author Shashank
 *
 */
public class Category implements Serializable {
	
	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String whWord;
	private boolean userCreated;
	
	public Category() {
	}

	/**
	 * @param id
	 * @param name
	 * @param userCreated
	 */
	public Category(int id, String name, String whWord, boolean userCreated) {
		this.id = id;
		this.name = name;
		this.whWord = whWord;
		this.userCreated = userCreated;
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
	 * @return the whWord
	 */
	public String getWhWord() {
		return whWord;
	}

	/**
	 * @param whWord the whWord to set
	 */
	public void setWhWord(String whWord) {
		this.whWord = whWord;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		if (id != other.id)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Category [id=" + id + "]";
	}
}
