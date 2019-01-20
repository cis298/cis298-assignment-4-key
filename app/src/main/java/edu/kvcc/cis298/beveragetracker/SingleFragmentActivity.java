package edu.kvcc.cis298.beveragetracker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public abstract class SingleFragmentActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content to the activity_fragment layout
        setContentView(R.layout.activity_fragment);

        // Create a new fragment manager, which is required to attach a new fragment
        FragmentManager fm = getSupportFragmentManager();

        // Find the fragment that is already in the container. Might be null.
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        // If the fragment is null, we can create a new one and add it to the container
        if (fragment == null) {
            // Create the fragment
            fragment = createFragment();
            // Start a transaction to put it in the container
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit(); // Must commit the transaction to make it complete.
        }
    }

    // Define an abstract method that must be overridden in child classes to return a fragment to use.
    protected abstract Fragment createFragment();
}
