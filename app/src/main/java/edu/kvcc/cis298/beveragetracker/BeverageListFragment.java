package edu.kvcc.cis298.beveragetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;

public class BeverageListFragment extends Fragment {

    // Private variables for the recycler view and the required adapter
    private RecyclerView mBeverageRecyclerView;
    private BeverageAdapter mBeverageAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Use the inflater to create a new view
        View view = inflater.inflate(R.layout.fragment_beverage_list, container, false);

        // Get a handle for the recycler view
        mBeverageRecyclerView = (RecyclerView) view.findViewById(R.id.beverage_recycler_view);

        // Set the layout manager, which is a required piece of the view
        mBeverageRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Call the updateUI method to do any remaining setup and get the view displayed
        updateUI();

        // Return the view
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // If this fragment is resumed from returning from the detail view, update the UI
        updateUI();
    }

    // Method to setup the view with an adapter if it doesn't already have one.
    // and update changes if it does.
    private void updateUI() {
        // Get the collection of data.
        BeverageCollection beverageCollection = BeverageCollection.get();
        // If the beverageCollection is empty, load the beverages to populate the list
        if (beverageCollection.isEmpty() && !beverageCollection.isDataLoadedOnce()) {
            beverageCollection.loadBeverageList(getResources().openRawResource(R.raw.beverage_list));
        }

        // Fetch the list of data from the collection
        List<Beverage> beverages = beverageCollection.getBeverages();

        // If there is no adapter, make a new one and send it in the list of beverages
        if (mBeverageAdapter == null) {
            mBeverageAdapter = new BeverageAdapter(beverages);
            // Set the adapter for the recyclerview to the newly created adapter
            mBeverageRecyclerView.setAdapter(mBeverageAdapter);
        } else {
            // Adapter already exists, so just call the notify data set changed method to update
            mBeverageAdapter.notifyDataSetChanged();
        }
    }

    // Private class that is required to get a recyclerview working
    private class BeverageHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Private widget vars
        private TextView mNameTextView;
        private TextView mPriceTextView;
        private TextView mIdTextView;

        // Private var for a single beverage
        private Beverage mBeverage;

        // Constructor that takes in a view
        BeverageHolder(View itemView) {
            super(itemView);
            // Set an onclick listener for the view holder
            itemView.setOnClickListener(this);

            // Wire up the vars to the layout widgets
            mNameTextView = (TextView) itemView.findViewById(R.id.list_item_name);
            mIdTextView = (TextView) itemView.findViewById(R.id.list_item_id);
            mPriceTextView = (TextView) itemView.findViewById(R.id.list_item_price);
        }

        // Implement the bindBeverage method that takes in a single beverage
        void bindBeverage(Beverage beverage) {
            // Set the beverage to the class level one
            mBeverage = beverage;

            // Set the name and id from the model
            mNameTextView.setText(mBeverage.getName());
            mIdTextView.setText(mBeverage.getId());

            // Create and use a number formatter that will display the price as currency
            NumberFormat moneyFormat = NumberFormat.getCurrencyInstance();
            mPriceTextView.setText(moneyFormat.format(mBeverage.getPrice()));
        }

        // Override the onClick method so clicking on a viewHolder starts up a new activity
        @Override
        public void onClick(View v) {
            // Get a properly formatted intent from the BeveragePagerActivity
            Intent intent = BeveragePagerActivity.newIntent(getActivity(), mBeverage.getId());
            // Start the new activity
            startActivity(intent);
        }
    }

    // Private class for the adapter that the recyclerview needs
    private class BeverageAdapter extends RecyclerView.Adapter<BeverageHolder> {
        // Private list of the beverages
        private List<Beverage> mBeverages;

        // Constructor that takes in a list of beverages and then assigns it to the class level version
        BeverageAdapter(List<Beverage> beverages) {
            mBeverages = beverages;
        }

        // Method to get the create the view holder
        @NonNull
        @Override
        public BeverageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // get an inflater
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            // inflate the view
            View view = layoutInflater.inflate(R.layout.list_item_beverage, parent, false);
            // Return a new holder with the view
            return new BeverageHolder(view);
        }

        // Method to bind a view holder to a beverage
        @Override
        public void onBindViewHolder(@NonNull BeverageHolder beverageHolder, int position) {
            // Get the beverage from the list based on the position being searched for
            Beverage beverage = mBeverages.get(position);
            // Call the bind beverage method with the found beverage
            beverageHolder.bindBeverage(beverage);
        }

        // Method to get the size of the list of beverages.
        @Override
        public int getItemCount() {
            return mBeverages.size();
        }
    }
}
