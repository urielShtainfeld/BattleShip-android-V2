package com.example.ushtinfeld.battleship_uriel;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import com.example.ushtinfeld.battleship_uriel.data.DataHandler;
import com.example.ushtinfeld.battleship_uriel.data.Record;
import com.example.ushtinfeld.battleship_uriel.data.ScoreTable;

public class highScoresFragment extends android.support.v4.app.Fragment {

    ArrayList<String> recordList;
    private OnListFragmentInteractionListener mListener;
    DataHandler dh = new DataHandler();
    public highScoresFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_highscores_list, container, false);
        ScoreTable table;
        try{
            table = dh.getData(getContext());
        }
        catch(Exception e){
            table = new ScoreTable();
        }
        recordList = new ArrayList<>();
        ArrayList<Record> records;
        records = table.getScoreTable();
        for (Record ro : records){
            try{
                recordList.add(ro.toString());
            }
            catch (Exception e){};

        }
        ListView listView = (ListView) view.findViewById(R.id.listViewScores);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,
                recordList);
        listView.setAdapter(arrayAdapter);
        return view;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public static highScoresFragment newInstance() {
        highScoresFragment fragment = new highScoresFragment();
        return fragment;
    }

    public interface OnListFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
