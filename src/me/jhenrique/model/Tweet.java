package me.jhenrique.model;

import java.util.Date;

/**
 * Model class to helps users getting info about an specific tweet
 * 
 * @author Jefferson
 */
public class Tweet {
	
	private String username;
	
	private String text;
	
	private Date date;
	
	private int retweets;

	private int favorites;

	private String user_id;

	private String tweet_id;
	
	public Tweet() {
	}

	public Tweet(String tweet_id, String username, String user_id, String text, Date date, int retweets,
			int favorites) {
		this.tweet_id = tweet_id;
		this.username = username;
		this.user_id = user_id;
		this.text = text;
		this.date = date;
		this.retweets = retweets;
		this.favorites = favorites;
	}

	public String getTweet_id() {
		return tweet_id;
	}

	public void setTweet_id(String tweet_id) {
		this.tweet_id = tweet_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getRetweets() {
		return retweets;
	}

	public void setRetweets(int retweets) {
		this.retweets = retweets;
	}

	public int getFavorites() {
		return favorites;
	}

	public void setFavorites(int favorites) {
		this.favorites = favorites;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

}
