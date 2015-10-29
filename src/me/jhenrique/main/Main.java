package me.jhenrique.main;

import me.jhenrique.manager.TweetManager;
import me.jhenrique.manager.TwitterCriteria;
import me.jhenrique.model.Tweet;

import java.util.List;


import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
	
	public static void main(String[] args) {
		/**
		 * Reusable objects
		 */
		TwitterCriteria criteria = null;
		List<Tweet> tweets = null;
		/* Tweet t = null;
		*/

		String q = args[0];

		String query = "homeromanteion";
		String since = "2015-07-02";
		String until = "2015-11-03";
		String filename = "output/"+query+"_"+since+"_"+until+".txt";	

		String[] starts = {"2010-04-15","2012-02-07","2015-07-01"};
		String[] ends = {"2010-05-15","2012-03-31","2015-08-25"};
		int i = 0;


		/*"~/Documents/hertie/datascience/col_res_proj/GetOldTweets/input.txt"*/
		File r = new  File(args[0]);
		try (BufferedReader br = new BufferedReader(new FileReader(r))) {
    		String line;
    		while ((line = br.readLine()) != null) {

				try {

				query = line;

				for (i = 0; i < 3; i++) {

					since = starts[i];
					until = ends[i];
					filename = "output/"+query+"_"+since+"_"+until+".txt";	

					criteria = TwitterCriteria.create()
							.setQuerySearch(query)
							.setSince(since)
							.setUntil(until);
					tweets = TweetManager.getTweets(criteria);

					File f = new File(filename);

					if (!f.exists()) {
						f.createNewFile();
					}

					FileWriter fw = new FileWriter(f.getAbsoluteFile());
					BufferedWriter bw = new BufferedWriter(fw);

					int listCount = tweets.size();

					//Tweet t = t.get(0);
					String dateString = null;
					SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			

					for(int j = 0; j < listCount; j = j+1) {
						Tweet t = tweets.get(j);

						dateString = sdf.format(t.getDate());

						bw.write(t.getText());
						bw.write("\t");
						bw.write(t.getUsername());
						bw.write("\t");
						bw.write(dateString);
						bw.write("\n");
					}

					bw.close();

					/*Runtime.getRuntime().exec("chown max:max " + PATH + filename);
					*/

					}
				} catch (IOException e) {
					e.printStackTrace();
				}		
      		 
   		}
		} 	catch (IOException e) {
			e.printStackTrace();
		}	

	
		
	}
	
}
