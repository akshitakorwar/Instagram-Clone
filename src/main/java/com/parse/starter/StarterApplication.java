/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;


public class StarterApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    // Enable Local Datastore.
    Parse.enableLocalDatastore(this);

    // Add your initialization code here
    Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
            .applicationId("952ffce7720668c62f5ce351abcc807714f17786")
            .clientKey("594f7136e1b4f6373983b680c8a94b6cac2dc568")
            .server("http://18.222.54.255:80/parse/")
            .build()
    );

    //Saving Objects
    final ParseObject score = new ParseObject("GameScore");
    score.put("playerName", "Liza");
    score.put("totalScore", 54633);

    score.saveInBackground(new SaveCallback() {
      @Override
      public void done(ParseException e) {
        if(e == null){
          Log.i("Message", "Success!");
        }
        else{
          e.printStackTrace();
        }
      }
    });

    //Retrieving Objects - Single Object
    final ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("GameScore");
    // Retrieve the object by id
    query.getInBackground("CdTTOm2U8r", new GetCallback<ParseObject>() {
      @Override
      public void done(ParseObject object, ParseException e) {
        if(e == null && object!=null){

            //Updating Objects
            object.put("totalScore", 18483);
            object.saveInBackground();

            Log.i("Player Name", object.getString("playerName"));
            Log.i("Score", Integer.toString(object.getInt("totalScore")));
        }
        else{
         e.printStackTrace();
        }
      }
    });

    //Retrieving group of objects
    /*ParseQuery<ParseObject> groupQuery = new ParseQuery<ParseObject>("GameScore");

    //querying based on score here, so the result returns only players whose score is greater than certain number
    //Note: comment the below line if you want to see all the players.
    groupQuery.whereGreaterThan("totalScore", 20000);

    groupQuery.findInBackground(new FindCallback<ParseObject>() {
      @Override
      public void done(List<ParseObject> objects, ParseException e) {
        if(objects.size() > 0){
          for(ParseObject object : objects){

            //we are updating the score of players by 10000 points
            //Note: this only updates users whose score is greater than 20000 as queried above ;)
            object.put("totalScore", object.getInt("totalScore")+10000);
            object.saveInBackground();

            Log.i("Player Name", object.getString("playerName"));
            Log.i("Total Score", Integer.toString(object.getInt("totalScore")));
          }
        }
      }
    });*/

    //Parse Users
    //Signing Up
    /*ParseUser user = new ParseUser();
    user.setUsername("Georgina");
    user.setPassword("password");
    user.setEmail("georgie@foo.com");

    user.signUpInBackground(new SignUpCallback() {
      @Override
      public void done(ParseException e) {
        if(e == null){
          Log.i("Message", "Successfully signed up!");
        }
        else{
          e.printStackTrace();
        }
      }
    });*/

    //Logging In
    /*ParseUser.logInInBackground("George", "password", new LogInCallback() {
      @Override
      public void done(ParseUser user, ParseException e) {
        if(user!=null && e==null){
          Log.i("Message", "Log in successful!");
        }
        else{
          e.printStackTrace();
        }
      }
    });*/


    //Current User
    /*ParseUser current = ParseUser.getCurrentUser();
    if(current != null){
      Log.i("Message", "User is already logged in");
    }
    else{
      Log.i("Message", "Something wrong.. User not logged in");
    }*/

   /* ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
    parseQuery.findInBackground(new FindCallback<ParseUser>() {
      @Override
      public void done(List<ParseUser> objects, ParseException e) {
        for(ParseObject object : objects){
          if(e==null && objects.size()>0){
            Log.i("Username", String.valueOf(object.getString("username")));
          }
        }
      }
    });*/
    ParseUser.enableAutomaticUser();

    ParseACL defaultACL = new ParseACL();
    defaultACL.setPublicReadAccess(true);
    defaultACL.setPublicWriteAccess(true);
    ParseACL.setDefaultACL(defaultACL, true);

  }
}
