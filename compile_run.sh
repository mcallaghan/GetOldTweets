#!/bin/bash
#cd ~/Documents/hertie/datascience/GetOldTweets
sudo javac -d bin -sourcepath src -cp libs/jsoup-1.8.1.jar:libs/twitter4j-core-4.0.2.jar src/me/jhenrique/main/Main.java src/me/jhenrique/manager/TweetManager.java src/me/jhenrique/model/Tweet.java
sudo java -cp bin:libs/jsoup-1.8.1.jar:libs/twitter4j-core-4.0.2.jar me.jhenrique.main.Main $1

