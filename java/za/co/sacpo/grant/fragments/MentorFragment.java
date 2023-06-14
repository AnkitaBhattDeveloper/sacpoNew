package za.co.sacpo.grant.fragments;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import za.co.sacpo.grant.xCubeLib.adapter.old.MentorAdapter;
import za.co.sacpo.grant.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MentorFragment extends Fragment implements View.OnClickListener {

    TextView txMentorApprovedBy,txVerified,txMenDate, heading;
    RecyclerView recyclerView_mMentor;
    private LinearLayoutManager linearLayoutManager;


    public MentorFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mentor, container, false);
        txMentorApprovedBy= (TextView) view.findViewById(R.id.txMentorApprovedBy);
        txVerified= (TextView) view.findViewById(R.id.txVerified);
        txMenDate= (TextView) view.findViewById(R.id.txMenDate);
        heading= (TextView) view.findViewById(R.id.heading);
        recyclerView_mMentor= (RecyclerView) view.findViewById(R.id.rvMenterRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView_mMentor.setLayoutManager(new GridLayoutManager(getActivity(),1));
        MentorAdapter mentorAdapter = new MentorAdapter(getContext());
        recyclerView_mMentor.setAdapter(mentorAdapter);


        initializelabel();

        return view;
    }

    private void initializelabel() {
        heading.setText(getString(R.string.l_126_115_fragment_mentor));
        txMentorApprovedBy.setText(getString(R.string.l_126_116_fragment_mentor));
        txVerified.setText(getString(R.string.l_126_117_fragment_mentor));
        txMenDate.setText(getString(R.string.l_126_118_fragment_mentor));
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        if (v.getId() == R.id.txMentorApprovedBy) {
        }
    }
}
