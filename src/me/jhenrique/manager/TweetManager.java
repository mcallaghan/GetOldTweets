package me.jhenrique.manager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.jhenrique.model.Tweet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import twitter4j.JSONObject;

/**
 * Class to getting tweets based on username and optional time constraints
 * 
 * @author Jefferson Henrique
 */
public class TweetManager {

	/**
	 * @param username A specific username (without @)
	 * @param since Lower bound date (yyyy-mm-dd)
	 * @param until Upper bound date (yyyy-mm-dd)
	 * @param scrollCursor (Parameter used by Twitter to do pagination of results)
	 * @return JSON response used by Twitter to build its results
	 * @throws Exception
	 */
	private static String getURLResponse(String username, String since, String until, String querySearch, String scrollCursor) throws Exception {
		String appendQuery = "";
		if (username != null) {
			appendQuery += "from:"+username;
		}
		if (since != null) {
			appendQuery += " since:"+since;
		}
		if (until != null) {
			appendQuery += " until:"+until;
		}
		if (querySearch != null) {
			appendQuery += " "+querySearch;
		}
		
		String url = String.format("https://twitter.com/i/search/timeline?f=realtime&q=%s&src=typd&max_position=%s", URLEncoder.encode(appendQuery, "UTF-8"), scrollCursor);
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		con.setRequestMethod("GET");
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		return response.toString();
	}
	
	/**
	 * @param criteria An object of the class {@link TwitterCriteria} to indicate how tweets must be searched
	 * 
	 * @return A list of all tweets found
	 */
	public static List<Tweet> getTweets(TwitterCriteria criteria) {
		List<Tweet> results = new ArrayList<Tweet>();
		
		try {
			String refreshCursor = null;
			outerLace: while (true) {
				JSONObject json = new JSONObject(getURLResponse(criteria.getUsername(), criteria.getSince(), criteria.getUntil(), criteria.getQuerySearch(), refreshCursor));
				refreshCursor = json.getString("min_position");
				Document doc = Jsoup.parse((String) json.get("items_html"));
				Elements tweets = doc.select("div.js-stream-tweet");
				
				if (tweets.size() == 0) {
					break;
				}
			
				for (Element tweet : tweets) {
					String usernameTweet = tweet.select("span.username.js-action-profile-name b").text();
					String txt = tweet.select("p.js-tweet-text").text().replaceAll("[^\\u0000-\\uFFFF]", "");
					String user_id = tweet.attr("data-user-id");
					String tweet_id = tweet.attr("data-tweet-id");
					int retweets = Integer.valueOf(tweet.select("span.ProfileTweet-action--retweet span.ProfileTweet-actionCount").attr("data-tweet-stat-count").replaceAll(",", ""));
					int favorites = Integer.valueOf(tweet.select("span.ProfileTweet-action--favorite span.ProfileTweet-actionCount").attr("data-tweet-stat-count").replaceAll(",", ""));
					long dateMs = Long.valueOf(tweet.select("small.time span.js-short-timestamp").attr("data-time-ms"));
					Date date = new Date(dateMs);
					
					Tweet t = new Tweet(tweet_id, usernameTweet, user_id, txt, date, retweets, favorites);
					results.add(t);

					//system.out.print(user_id);
					
					if (criteria.getMaxTweets() > 0 && results.size() >= criteria.getMaxTweets()) {
						break outerLace;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return results;
	}
	
}
