package za.co.sacpo.grantportal.fragments;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import za.co.sacpo.grantportal.xCubeLib.adapter.old.EmployerAdapter;
import za.co.sacpo.grantportal.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmployerFragment extends Fragment implements View.OnClickListener {

    TextView tv_heading, tv_approved_amt, tv_approvedby, tv_date;
    RecyclerView mRecyclerView_employer;
    private LinearLayoutManager linearLayoutManager;


    public EmployerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_employer, container, false);

        tv_heading = (TextView) view.findViewById(R.id.tv_heading);
        tv_approved_amt = (TextView) view.findViewById(R.id.tv_approved_amt);
        tv_approvedby = (TextView) view.findViewById(R.id.tv_approvedby);
        tv_date = (TextView) view.findViewById(R.id.tv_date);

        mRecyclerView_employer = (RecyclerView) view.findViewById(R.id.rvEmployee);
        /*Recyclerview functionality*/
        linearLayoutManager  = new LinearLayoutManager(this.getActivity());
        mRecyclerView_employer.setLayoutManager(new GridLayoutManager(getActivity(),1));
        EmployerAdapter employerAdapter = new EmployerAdapter(getContext());
        mRecyclerView_employer.setAdapter(employerAdapter);

        initializelabels();

        return view;
    }

    private void initializelabels() {
        tv_heading.setText(getString(R.string.l_126_119_fragment_employee));
        tv_approved_amt.setText(getString(R.string.l_126_120_fragment_employee));
        tv_approvedby.setText(getString(R.string.l_126_121_fragment_employee));
        tv_date.setText(getString(R.string.l_126_122_fragment_employee));
    }

    private void initializeViews() {


    }

    @Override
    public void onClick(View v) {

    }
}
