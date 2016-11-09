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
import klaou.perso.velibus.data.VelibData;
import klaou.perso.velibus.data.VelibDataBundle;

/**
 * A placeholder fragment containing a simple view.
 */
public class MyVelibFragment extends Fragment {

    private VelibDataBundle velibDataBundle;
    private View rootView;
    private SwipeRefreshLayout mySwipeRefreshLayout;

    private ArrayList<VelibData> velibDataRows = new ArrayList<>();


    public MyVelibFragment() { }

    /**
     * Returns a new instance of this fragment
     */
    public static MyVelibFragment newInstance(int sectionNumber) {
        MyVelibFragment fragment = new MyVelibFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context myContext = this.getContext();
        rootView = inflater.inflate(R.layout.fragment_velib_velibus, container, false);

        velibDataBundle = new VelibDataBundle(myContext);
        mySwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_velib);

        final ArrayAdapter<TransportData> adapter = new DataRowArrayAdapter(myContext, 0, velibDataBundle.toList());
        //Find list view and bind it with the custom adapter
        ListView listView = (ListView) rootView.findViewById(R.id.custom_data_list_view);
        listView.setAdapter(adapter);

        new VelibGetDataTask(adapter).execute();

        // Refresh Listeners
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        // This method performs the actual data-refresh operation.
                        if(velibDataBundle != null)
                        {
                            new VelibGetDataTask(adapter).execute();
                        }
                    }
                }
        );
        FloatingActionButton fabSyncVelib = (FloatingActionButton) rootView.findViewById(R.id.fab_sync_velib);
        fabSyncVelib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                // refresh data
                if(velibDataBundle != null)
                {
                    new VelibGetDataTask(adapter).execute();
                }
            }
        });

        return rootView;
    }

    private class VelibGetDataTask extends AsyncTask<Void, Void, Boolean> {

        private final static String ERROR_ASYNC_TASK =  "ERROR_ASYNC_TASK";
        private ArrayAdapter adapter;

        VelibGetDataTask(ArrayAdapter pAdapter)
        {
            super();
            adapter = pAdapter;
        }

        protected void onPreExecute(){}

        protected Boolean doInBackground(Void... urls)
        {
            try {
                velibDataBundle.refresh();
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return false;
            }
            return true;
        }

        protected void onPostExecute(Boolean taskStatus)
        {
            TextView textView = (TextView) rootView.findViewById(R.id.text_view_velib_schedules);
            if(taskStatus)
            {
                //display velibDataBundle refreshed
                velibDataRows.add( velibDataBundle.getVelib1());
                velibDataRows.add( velibDataBundle.getVelib3());
                velibDataRows.add( velibDataBundle.getVelib2());

                adapter.notifyDataSetChanged();

            }else
            {
                textView.setText(ERROR_ASYNC_TASK);
            }
            Toast.makeText(MyVelibFragment.this.getContext(), "LET'S ROLL", Toast.LENGTH_SHORT).show();
            mySwipeRefreshLayout.setRefreshing(false);
        }
    }

}

