package klaou.perso.velibus.fragments;

/**
 * Created by Klaou on 04/11/2016.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import klaou.perso.velibus.R;
import klaou.perso.velibus.data.TransportData;
import klaou.perso.velibus.customlayout.DataRowArrayAdapter;
import klaou.perso.velibus.data.BusData;
import klaou.perso.velibus.data.BusDataBundle;

/**
 * A placeholder fragment containing a simple view.
 */
public class MyBusFragment extends Fragment {

    private BusDataBundle busDataBundle;
    private View rootView;
    private SwipeRefreshLayout mySwipeRefreshLayout;

    private ArrayList<BusData> busDataRows = new ArrayList<>();

    public MyBusFragment() { }

    /**
     * number.
     */
    public static MyBusFragment newInstance(int sectionNumber) {
        MyBusFragment fragment = new MyBusFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context myContext = this.getContext();
        rootView = inflater.inflate(R.layout.fragment_bus_velibus, container, false);
        busDataBundle = new BusDataBundle(myContext);
        mySwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_bus);

        final ArrayAdapter<TransportData> adapter = new DataRowArrayAdapter(myContext, 0, busDataBundle.toList());
        //Find list view and bind it with the custom adapter
        ListView listView = (ListView) rootView.findViewById(R.id.custom_data_list_view);
        listView.setAdapter(adapter);

         new BusGetDataTask(adapter).execute();

        // Refresh Listeners
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        // This method performs the actual data-refresh operation.
                        if(busDataBundle != null)
                        {
                            new BusGetDataTask(adapter).execute();
                        }
                    }
                }
        );


        FloatingActionButton fabSyncBus = (FloatingActionButton) rootView.findViewById(R.id.fab_sync_bus);
        fabSyncBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // refresh data
                if(busDataBundle != null)
                {
                    new BusGetDataTask(adapter).execute();
                }
            }
        });

        return rootView;
    }


    private class BusGetDataTask extends AsyncTask<Void, Void, Boolean> {

        private final static String ERROR_ASYNC_TASK =  "ERROR_ASYNC_TASK";
        private ArrayAdapter adapter;

        BusGetDataTask(ArrayAdapter pAdapter)
        {
            super();
            adapter = pAdapter;
        }

        protected void onPreExecute(){}

        protected Boolean doInBackground(Void... urls)
        {
            try {
                busDataBundle.refresh();
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return false;
            }
            return true;
        }

        protected void onPostExecute(Boolean taskStatus)
        {

            TextView textView = (TextView) rootView.findViewById(R.id.text_view_bus_schedules);
            if(taskStatus)
            {
                busDataRows.add(busDataBundle.getBus_1());
                busDataRows.add(busDataBundle.getBus_2());
                busDataRows.add(busDataBundle.getBus_3());
                busDataRows.add(busDataBundle.getBus_4());

                adapter.notifyDataSetChanged();
            }else
            {
                textView.setText(ERROR_ASYNC_TASK);
            }
            Toast.makeText(MyBusFragment.this.getContext(), "LET'S ROLL", Toast.LENGTH_SHORT).show();
            mySwipeRefreshLayout.setRefreshing(false);
        }
    }

}

