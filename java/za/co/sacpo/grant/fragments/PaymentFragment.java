package za.co.sacpo.grant.fragments;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import za.co.sacpo.grant.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends Fragment {

    private TextView tv_P_heading, tv_P_lbl_title1, tv_P_lbl_title2, txProcessedBy,txPaymentDate, txAmountPAid,
            txtname1, verifieddate, dateAmt, text_no1, text_no2, text_no3;


    public PaymentFragment() {

        // Required empty public constructor  //

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment, container, false);


        tv_P_heading = (TextView) view.findViewById(R.id.tv_P_heading);
        tv_P_lbl_title1 = (TextView) view.findViewById(R.id.tv_P_lbl_title1);
        tv_P_lbl_title2 = (TextView) view.findViewById(R.id.tv_P_lbl_title2);
        txProcessedBy = (TextView) view.findViewById(R.id.txProcessedBy);
        txPaymentDate = (TextView) view.findViewById(R.id.txPaymentDate);
        txAmountPAid = (TextView) view.findViewById(R.id.txAmountPAid);
        txtname1 = (TextView) view.findViewById(R.id.txtname1);
        verifieddate = (TextView) view.findViewById(R.id.verifieddate);
        dateAmt = (TextView) view.findViewById(R.id.dateAmt);
        text_no1 = (TextView) view.findViewById(R.id.text_no1);
        text_no2 = (TextView) view.findViewById(R.id.text_no2);
        text_no3 = (TextView) view.findViewById(R.id.text_no3);


        initializelabels();

        return view;
    }

    private void initializelabels() {

        tv_P_heading.setText(getString(R.string.l_126_123_fragment_payment));
        tv_P_lbl_title1.setText(getString(R.string.l_126_124_fragment_payment));
        tv_P_lbl_title2.setText(getString(R.string.l_126_125_fragment_payment));
        txProcessedBy.setText(getString(R.string.l_126_126_fragment_payment));
        txPaymentDate.setText(getString(R.string.l_126_127_fragment_payment));
        txAmountPAid.setText(getString(R.string.l_126_128_fragment_payment));
        txtname1.setText(getString(R.string.l_126_129_fragment_payment));
        verifieddate.setText(getString(R.string.l_126_130_fragment_payment));
        dateAmt.setText(getString(R.string.l_126_131_fragment_payment));
        text_no1.setText(getString(R.string.l_126_132_fragment_payment));
        text_no2.setText(getString(R.string.l_126_133_fragment_payment));
        text_no3.setText(getString(R.string.l_126_134_fragment_payment));




    }

}
