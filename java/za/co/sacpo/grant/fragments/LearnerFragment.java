package za.co.sacpo.grant.fragments;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import za.co.sacpo.grant.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LearnerFragment extends Fragment implements View.OnClickListener{

    private TextView txClaimDate, txClaimMonth, txDaysworks, txAttendence, txClaimed, txHeading;
    RecyclerView learnerRecylerview;



    public LearnerFragment() {




    }

    private void initializelabel() {
        txHeading.setText(getString(R.string.l_126_109_fragment_learner));
        txClaimDate.setText(getString(R.string.l_126_110_fragment_learner));
        txClaimMonth.setText(getString(R.string.l_126_111_fragment_learner));
        txDaysworks.setText(getString(R.string.l_126_112_fragment_learner));
        txAttendence.setText(getString(R.string.l_126_113_fragment_learner));
        txClaimed.setText(getString(R.string.l_126_114_fragment_learner));


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_learner, container, false);
        txClaimDate = (TextView) view.findViewById(R.id.txClaimDate);
        txClaimMonth = (TextView) view.findViewById(R.id.txClaimMonth);
        txDaysworks = (TextView) view.findViewById(R.id.txDaysworks);
        txAttendence = (TextView) view.findViewById(R.id.txAttendence);
        txClaimed = (TextView) view.findViewById(R.id.txClaimed);
        txHeading = (TextView) view.findViewById(R.id.txHeading);

        learnerRecylerview = (RecyclerView) view.findViewById(R.id.learnerRecylerview);
        initializelabel();
        return view;

    }


    @Override
    public void onClick(View v) {
        Intent intent;
        if (v.getId() == R.id.txClaimDate) {
        }
    }
}


