package com.example.waterreminder2.ui.main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.waterreminder2.IMainActivity;
import com.example.waterreminder2.R;
import com.example.waterreminder2.adapter.WaterRecyclerAdapter;
import com.example.waterreminder2.models.Water;
import com.example.waterreminder2.persistence.WaterRepository;
import com.example.waterreminder2.util.VerticalSpacingItemDecorator;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WaterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WaterFragment extends Fragment implements View.OnClickListener, IMainActivity, WaterRecyclerAdapter.OnWaterListener {
    private static final String TAG = "WaterFragment";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView currentAmountOfWaterTextView;
    private TextView goalOfWater;
    private ImageButton addWaterButton;

    private int currentAmountOfWater = 0;

    private IMainActivity mIMainActivity;

    //UI Component
    private RecyclerView mRecyclerView;

    //vars
    private ArrayList<Water> mWater = new ArrayList<>();
    private ArrayList<Water> mAmountOfWater = new ArrayList<>();
    private WaterRecyclerAdapter mWaterRecyclerAdapter;
    final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
    private String time;
    private WaterRepository mWaterRepository;
    private Water water;
    private int sumOfCurrentAmountOfWater;


    public WaterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WaterFragment.
     */


    //TODO Kann sein das ich das hier nicht brauche, da ich das Löschen über einen Swipe lösen will und nicht durch das anklicken des Objects
    @Override
    public void onWaterClick(int position) {

    }


    @Override
    public void sendInput(String input, String addingTime) {
        Log.d(TAG, "sendInput: found incoming input: " + input);

        try {
            currentAmountOfWater = Integer.parseInt(input);
            System.out.println(currentAmountOfWater);
            water = new Water(currentAmountOfWater, addingTime);
            saveWaters();
            //currentAmountOfWaterTextView.setText(Integer.toString(currentAmountOfWater));

        } catch (NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
    }

    // TODO: Rename and change types and number of parameters
    public static WaterFragment newInstance(String param1, String param2) {
        WaterFragment fragment = new WaterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        retrieveAmountOfWater();

        //TODO Im Tutorial, da keine Fragments, anders -> Schauen ob geht

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(WaterFragment.this).attach(WaterFragment.this).commit();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        retrieveAmountOfWater();
        View view = inflater.inflate(R.layout.fragment_water, container, false);
        currentAmountOfWaterTextView = (TextView) view.findViewById(R.id.currentWater);
        goalOfWater = (TextView) view.findViewById((R.id.goalWater));


        addWaterButton = view.findViewById(R.id.addWater);
        addWaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: opening dialog");

                editwaterFragment editwaterFragmentDialog = new editwaterFragment();
                editwaterFragmentDialog.setTargetFragment(WaterFragment.this, 1);
                editwaterFragmentDialog.show(getFragmentManager(), "EditWaterFragment");
            }
        });

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        initRecyclerView(recyclerView);
        //insertFakesWaterS();

        retrieveWaters();
        retrieveAmountOfWater();

        //Updating the TextView with the new Database Entries, so that t
       /* System.out.println("AmountList in onCreateView: " + mAmountOfWater);
        for (Water waters : mAmountOfWater) {
            sumOfCurrentAmountOfWater += waters.getAmount();
        }*/

       currentAmountOfWaterTextView.setText("" +sumOfCurrentAmountOfWater);

        /*FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(WaterFragment.this).attach(WaterFragment.this).commit();*/
        return view;
    }



    private void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        recyclerView.addItemDecoration(itemDecorator);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        mWaterRecyclerAdapter = new WaterRecyclerAdapter(mWater);
        System.out.println("\n\n\n\n" + mWater);
        recyclerView.setAdapter(mWaterRecyclerAdapter);

    }

    private void insertFakesWaterS() {
        for (int i = 0; i < 1000; i++) {
            Water water = new Water();
            water.setAmount(i);
            time = dateFormat.format(new Date());
            water.setCurrentTimeStamp(time);
            mWater.add(water);
        }
        //without this method no WaterObject will be shown in the List
        mWaterRecyclerAdapter.notifyDataSetChanged();
    }

    private void deleteWater(Water water) {
        mWater.remove(water);
        mWaterRecyclerAdapter.notifyDataSetChanged();
    }

    private ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int adapterPosition = viewHolder.getAdapterPosition();
            if (direction == ItemTouchHelper.RIGHT) {
                Toast.makeText(getContext(), "Right", Toast.LENGTH_SHORT).show();
            } else if (direction == ItemTouchHelper.LEFT) {
                Toast.makeText(getContext(), "Left", Toast.LENGTH_SHORT).show();
            }


        }
    };

    private void retrieveWaters() {
        //TODO Im Video war bei observe(this) statt getViewLifecycleOwner -> schauen ob geht
        mWaterRepository.retrieveWaterTask().observe(getViewLifecycleOwner(), new Observer<List<Water>>() {
            @Override
            public void onChanged(List<Water> waters) {
                if (mWater.size() > 0) {
                    mWater.clear();
                }
                if (waters != null) {
                    mWater.addAll(waters);
                }
                mWaterRecyclerAdapter.notifyDataSetChanged();

            }
        });
    }

    /*private void retrieveAmountOfWater() {
        mWaterRepository.retrieveAmountOfWaterTask().observe(getViewLifecycleOwner(), new Observer<List<Water>>() {
            @Override
            public void onChanged(List<Water> waters) {
                System.out.println("Anzahl an Einträgen in DB: " + waters.size());
                for (Water water : waters) {
                    if (sumOfCurrentAmountOfWater != water.getAmount())
                        sumOfCurrentAmountOfWater += water.getAmount();
                }
            }
        });
    }*/

    private void retrieveAmountOfWater(){
        for (int i = 0; i < mWaterRepository.retrieveAmountOfWaterTask().size(); i++) {
            sumOfCurrentAmountOfWater = mWaterRepository.retrieveAmountOfWaterTask().get(i).getAmount();
        }
    }
    private void saveWaters() {
        if (water != null) {
            mWaterRepository.inserWaterTask(water);
        } else {
            Log.d(TAG, "saveWaters: nothing to save");
            ;
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("items", mWater);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mWaterRepository = new WaterRepository(context);
    }

    @Override
    public void onClick(View v) {

    }
}



