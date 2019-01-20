package edu.kvcc.cis298.beveragetracker;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BeverageListActivity extends SingleFragmentActivity {

    // Since this activity inherits from SingleFragmentActivity, the only work we have to do
    // is override this createFragment method and return a new BeverageListFragment
    @Override
    protected Fragment createFragment() {
        return new BeverageListFragment();
    }
}
