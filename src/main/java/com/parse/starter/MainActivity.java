/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import java.security.Key;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

  TextView textView;
  Boolean signUpModeActive = true;
  Button button;
  EditText username;
  EditText password;
  String user;
  String pass;

  ImageView imageView;
  RelativeLayout relativeLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setTitle("Instagram");

    username = (EditText) findViewById(R.id.editText4);
    password = (EditText) findViewById(R.id.editText5);

    textView = (TextView) findViewById(R.id.textLoginOrSignUp);
    textView.setOnClickListener(this);

    password.setOnKeyListener(this);

    imageView = (ImageView) findViewById(R.id.imageView2);
    relativeLayout = (RelativeLayout) findViewById(R.id.layoutBackground);

    imageView.setOnClickListener(this);

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

  //Method for View.OnKeyListener
  //int i in this method is keycode
  //so when we click on "Enter" button on the keypad,
  //the value i is updated to 66 and then the signUp method gets called.
  //NOTE: keycode for "Enter" is 66
  @Override
  public boolean onKey(View view, int i, KeyEvent keyEvent) {
    //to handle keyboard events - when clicked on "->" or "enter" button on the keyboard, it should call the button action
    //hence calling the signUp() method here.
    Log.i("Value of i", Integer.toString(i));
    if(i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN){
      signUp(view);
    }
    return false;
  }

  //Method for View.OnClickListener
  @Override
  public void onClick(View view) {
    if (view.getId() == R.id.textLoginOrSignUp) {

      button = (Button) findViewById(R.id.buttonLogInOrSignUp);

      if (signUpModeActive) {
        //switch to Login mode
        signUpModeActive = false;
        button.setText("Login");
        textView.setText("or Sign Up");
      } else {
        //switch to Sign Up mode
        signUpModeActive = true;
        button.setText("Sign Up");
        textView.setText("or Login");
      }
    }
    else if(view.getId() == R.id.imageView2 || view.getId() == R.id.backgroundLayout){
      //to hide keyboard on clicking on image view
      InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
      inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
  }

  //calls User Activity class where list of items are displayed
  public void displayUserList(){
    Intent intent = new Intent(getApplicationContext(), UsersActivity.class);
    intent.putExtra("UserList", "UserList");
    startActivity(intent);
  }

  //button function for sign up/login
  public void signUp(View view) {

    user = username.getText().toString();
    pass = password.getText().toString();

    if (signUpModeActive) {
      ParseUser parseUser = new ParseUser();
      parseUser.setUsername(user);
      parseUser.setPassword(pass);

      parseUser.signUpInBackground(new SignUpCallback() {
        @Override
        public void done(ParseException e) {
          if (e == null) {
            Toast.makeText(getApplicationContext(), "User signed up successfully", Toast.LENGTH_SHORT).show();
            displayUserList();
          } else {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            //e.printStackTrace();
          }
        }
      });
    } else {
      ParseUser.logInInBackground(user, pass, new LogInCallback() {
        @Override
        public void done(ParseUser user, ParseException e) {
          if (e == null && user != null) {
            Toast.makeText(getApplicationContext(), "Login Successful!", Toast.LENGTH_SHORT).show();
            displayUserList();
//            Intent intent = new Intent(getApplicationContext(), UsersActivity.class);
//            intent.putExtra("UserList", "UserList");
//            startActivity(intent);
          } else {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
          }
        }
      });
    }
  }
}