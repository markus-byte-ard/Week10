package com.example.week10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    WebView web;
    EditText search;
    Button searchButton, refreshButton, shoutButton, forwardButton, backButton;
    List<String> previousSites, nextSites;
    String currentAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        web = (WebView) findViewById(R.id.WebView);
        search = (EditText) findViewById(R.id.SearchBar);
        searchButton = (Button) findViewById(R.id.SearchButton);
        refreshButton = (Button) findViewById(R.id.refreshButton);
        shoutButton = (Button) findViewById(R.id.shoutOutButton);
        forwardButton = (Button) findViewById(R.id.forwardButton);
        backButton = (Button) findViewById(R.id.BackButton);
        //Alustan listat
        previousSites = new LinkedList<String>();
        nextSites = new LinkedList<String>();

        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    searchFunction(search);
                    handled = true;
                }
                return handled;
            }
        });

        web.setWebViewClient(new WebViewClient());
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl("http://www.Google.fi");
        currentAddress = web.getUrl();

    }

    public void searchFunction (View v) {
        if (search.getText().toString().equals("index.html")) {
            currentAddress = "file:///android_asset/index.html";
        }
        else {
            currentAddress = "http://" + search.getText().toString();
        }
        previousSites.add(web.getUrl());
        nextSites.clear();
        if (previousSites.size() > 11) {
            previousSites.remove(0);
        }
        web.loadUrl(currentAddress);
    }

    public void refreshFunction (View v) {
        currentAddress = web.getUrl();
        web.loadUrl(currentAddress);
    }

    public void goBack (View v) {
        if (previousSites.size() == 0) {
        }
        else {
            nextSites.add(web.getUrl());
            web.loadUrl(previousSites.get(previousSites.size()-1));
            previousSites.remove(previousSites.size()-1);
            currentAddress = web.getUrl();
        }
    }
    public void goForward (View v) {
        if (nextSites.size() == 0) {
        }
        else {
            previousSites.add(web.getUrl());
            web.loadUrl(nextSites.get(nextSites.size()-1));
            nextSites.remove(nextSites.size()-1);
            currentAddress = web.getUrl();
        }
    }

    public void executeShout (View v) {
        web.evaluateJavascript("javascript:shoutOut()", null);
    }
    public void executeIni (View v) {
        web.evaluateJavascript("javascript:initialize()", null);
    }
}