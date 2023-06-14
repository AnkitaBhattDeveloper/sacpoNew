package za.co.sacpo.grant.fragments;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeStudent.claims.SDownCFormA;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClaimFormFragment extends Fragment implements View.OnClickListener {
    private TextView tv_Claim_heading, tv_lbl_d_from, tv_lbl_u_from;
    private Button btn1_downloadFrom, btn1_Upload, btn2_downloadFrom, btn3_downloadFrom, btn2_Upload;
    ImageView imgViewImage;

    public ClaimFormFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_claim_form, container, false);

        tv_Claim_heading = (TextView) v.findViewById(R.id.tv_Claim_heading);
        tv_lbl_d_from = (TextView) v.findViewById(R.id.tv_lbl_d_from);
        tv_lbl_u_from = (TextView) v.findViewById(R.id.tv_lbl_u_from);

        btn1_downloadFrom = (Button) v.findViewById(R.id.btnDownloadCF);
        btn2_downloadFrom = (Button) v.findViewById(R.id.btn2_downlod);
        btn3_downloadFrom = (Button) v.findViewById(R.id.btn3_downlod);
        btn1_Upload = (Button) v.findViewById(R.id.btnUpload);
        btn2_Upload = (Button) v.findViewById(R.id.btn2_Upload);

        imgViewImage = (ImageView) v.findViewById(R.id.imgViewImage);


        implementlistener();
        initializelabels();

        return v;
    }

    private void implementlistener() {

        btn1_downloadFrom.setOnClickListener(this);
        btn2_downloadFrom.setOnClickListener(this);
        btn3_downloadFrom.setOnClickListener(this);
        btn1_Upload.setOnClickListener(this);
        btn2_Upload.setOnClickListener(this);
    }

    private void initializelabels() {
        tv_Claim_heading.setText(getString(R.string.l_126_101_fragment_ClaimForm));
        tv_lbl_d_from.setText(getString(R.string.l_126_102_fragment_ClaimForm));
        tv_lbl_u_from.setText(getString(R.string.l_126_103_fragment_ClaimForm));

        btn1_downloadFrom.setText(getString(R.string.lbtn_download));
        btn1_Upload.setText(getString(R.string.lbtn_upload));
        btn2_downloadFrom.setText(getString(R.string.lbtn_download));
        btn3_downloadFrom.setText(getString(R.string.lbtn_download));
        btn2_Upload.setText(getString(R.string.lbtn_upload));


    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnDownloadCF) {
            Toast.makeText(getActivity(), "downloading...", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(getActivity(), SDownCFormA.class);
            startActivity(i);
        }

    }
}
