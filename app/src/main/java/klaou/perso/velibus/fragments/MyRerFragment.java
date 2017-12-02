package klaou.perso.velibus.fragments;

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
import klaou.perso.velibus.customlayout.DataRowArrayAdapter;
import klaou.perso.velibus.data.RerData;
import klaou.perso.velibus.data.RerDataBundle;
import klaou.perso.velibus.data.TransportData;

/**
 * Created by Klaou on 27/12/2016.
 */
public class MyRerFragment extends Fragment{

    private RerDataBundle rerDataBundle;
    private View rootView;
    private SwipeRefreshLayout mySwipeRefreshLayout;

    private ArrayList<RerData> rerDataRows = new ArrayList<>();

    public MyRerFragment() { }

    /**
     * number.
     */
    public static MyRerFragment newInstance(int sectionNumber) {
        MyRerFragment fragment = new MyRerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context myContext = this.getContext();
        rootView = inflater.inflate(R.layout.fragment_rer_velibus, container, false);
        rerDataBundle = new RerDataBundle(myContext);
        mySwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_rer);

        final ArrayAdapter<TransportData> adapter = new DataRowArrayAdapter(myContext, 0, rerDataBundle.toList());
        //Find list view and bind it with the custom adapter
        ListView listView = (ListView) rootView.findViewById(R.id.custom_data_list_view);
        listView.setAdapter(adapter);

        new RerGetDataTask(adapter).execute();

        // Refresh Listeners
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        // This method performs the actual data-refresh operation.
                        if(rerDataBundle != null)
                        {
                            new RerGetDataTask(adapter).execute();
                        }
                    }
                }
        );


        FloatingActionButton fabSyncRer = (FloatingActionButton) rootView.findViewById(R.id.fab_sync_rer);
        fabSyncRer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // refresh data
                if(rerDataBundle != null)
                {
                    new RerGetDataTask(adapter).execute();
                }
            }
        });

        return rootView;
    }


    private class RerGetDataTask extends AsyncTask<Void, Void, Boolean> {

        private final static String ERROR_ASYNC_TASK =  "ERROR_ASYNC_TASK";
        private ArrayAdapter adapter;

        RerGetDataTask(ArrayAdapter pAdapter)
        {
            super();
            adapter = pAdapter;
        }

        protected void onPreExecute(){}

        protected Boolean doInBackground(Void... urls)
        {
            try {
                rerDataBundle.refresh();
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return false;
            }
            return true;
        }

        protected void onPostExecute(Boolean taskStatus)
        {

            TextView textView = (TextView) rootView.findViewById(R.id.text_view_rer_schedules);
            if(taskStatus)
            {
                rerDataRows.add(rerDataBundle.getRer_1());
                rerDataRows.add(rerDataBundle.getRer_2());
                /*
                rerDataRows.add(rerDataBundle.getRer_3());
                rerDataRows.add(rerDataBundle.getRer_4());
                */
                adapter.notifyDataSetChanged();
            }else
            {
                textView.setText(ERROR_ASYNC_TASK);
            }
            Toast.makeText(MyRerFragment.this.getContext(), "LET'S ROLL", Toast.LENGTH_SHORT).show();
            mySwipeRefreshLayout.setRefreshing(false);
        }
    }

}
