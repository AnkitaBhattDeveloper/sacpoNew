package za.co.sacpo.grantportal.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import za.co.sacpo.grantportal.R;

/*fragment_dinamis*/
public class DynamicViewFragment extends  Fragment {
    private TextView tvDetailFragment;
    private String detail;
    public Button btnsubmit;
    TextView  txtQuesName,txtAnswerName,txtQuizeNo;

    CheckBox checkbox1,checkbox2,checkbox3,checkbox4;

    TextView txtDescription1,txtDescription2,txtDescription3,txtDescription4;

    private TextView lblView;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dinamis, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        txtQuizeNo = (TextView) view.findViewById(R.id.txtQuizeNo);
        txtQuizeNo.setText(getDetail());

        txtAnswerName = (TextView) view.findViewById(R.id.txtAnswerName);
        txtQuesName = (TextView)view. findViewById(R.id.txtQuesName);
        txtQuizeNo = (TextView) view.findViewById(R.id.txtQuizeNo);

        txtDescription1 = (TextView)view. findViewById(R.id.txtDescription1);
        txtDescription2 = (TextView)view. findViewById(R.id.txtDescription2);
        txtDescription3 = (TextView)view. findViewById(R.id.txtDescription3);
        txtDescription4 = (TextView)view. findViewById(R.id.txtDescription4);

        checkbox1 = (CheckBox)view. findViewById(R.id.checkbox1);
        checkbox2 = (CheckBox)view. findViewById(R.id.checkbox2);
        checkbox3 = (CheckBox)view. findViewById(R.id.checkbox3);
        checkbox4 = (CheckBox)view. findViewById(R.id.checkbox4);

        txtQuesName = (TextView)view. findViewById(R.id.txtQuesName);
        btnsubmit = (Button) view. findViewById(R.id.btnsubmit);

    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
