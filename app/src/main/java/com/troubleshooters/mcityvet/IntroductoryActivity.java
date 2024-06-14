package com.troubleshooters.mcityvet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

public class IntroductoryActivity extends AppCompatActivity {

    ImageView intro, logo, name_logo;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);

        // Initialize the views
        intro = findViewById(R.id.intro);
        logo = findViewById(R.id.logo);
        name_logo = findViewById(R.id.name_logo);
        lottieAnimationView = findViewById(R.id.lottie);

        if (!NetworkUtils.isNetworkAvailable(this)) {
            showNoInternetDialog();
        } else {
            // Proceed with your normal workflow
            animateViews();
        }
    }

    private void showNoInternetDialog() {
        new AlertDialog.Builder(this)
                .setTitle("No Internet Connection")
                .setMessage("This application requires an internet connection to work. Please connect to the internet and try again.")
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish(); // Close the application
                    }
                })
                .setCancelable(false)
                .show();
    }

    private void animateViews() {
        intro.animate().translationY(-1600).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        name_logo.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(1600).setDuration(1000).setStartDelay(4000);

        // Call navigateToLoginActivity after animation
        intro.postDelayed(new Runnable() {
            @Override
            public void run() {
                navigateToLoginActivity();
            }
        }, 5000); // Adjust delay according to your animation duration
    }

    private void navigateToLoginActivity() {
        startActivity(new Intent(IntroductoryActivity.this, LoginActivity.class));
        finish();
    }
}
