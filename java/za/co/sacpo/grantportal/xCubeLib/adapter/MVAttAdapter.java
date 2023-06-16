package za.co.sacpo.grantportal.xCubeLib.adapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseAPC;
import za.co.sacpo.grantportal.xCubeLib.dataObj.MVAttObj;
import za.co.sacpo.grantportal.xCubeLib.db.DbHelper;

import za.co.sacpo.grantportal.xCubeMentor.attendance.MAttMonthlyA;

/**
 * Created by xcube-06 on 7/8/18.
 */

public class MVAttAdapter extends RecyclerView.Adapter<MVAttAdapter.ViewHolder> {
    private List<MVAttObj.Item> aObjList;

    private String CAId;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    protected DbHelper dbSetaObj;
    String Labels;
    String remark;
    String approve,disapprove;
    private RadioButton lastCheckedRB = null;
    private RadioGroup lastCheckedRadioGroup = null;
    RecyclerView recyclerViewQ;

    int flag;
    private ArrayList<String> approveListId=new ArrayList<>();
    private ArrayList<String> remarksArrList=new ArrayList<>();
    private ArrayList<String> disApproveListId=new ArrayList<>();

    private Map<String, String> approvalStatusMap = new HashMap<String, String>();
    private Map<String, String> inputRemarkMap = new HashMap<String, String>();

    String typeId;


    MAttMonthlyA baseActivity;
    private double total;

    public MVAttAdapter(List<MVAttObj.Item> aObjList, Context baseActivityContext, AppCompatActivity activityInCall, MAttMonthlyA baseActivity) {
        this.aObjList = aObjList;
        this.baseActivityContext = baseActivityContext;
        this.activityInCall = activityInCall;
        this.baseActivity = baseActivity;
    }

    public String getLabelFromDb(String inputLabel, int resId) {
        String ValueLabel = baseActivityContext.getString(resId);
        dbSetaObj = DbHelper.getInstance(baseActivityContext);
        Cursor res = dbSetaObj.getDataValueByName(inputLabel);
        if (res.getCount() > 0) {
            try {

                while (res.moveToNext())

                {
                    ValueLabel = res.getString(0);
                }
            } finally {
                if (res != null && !res.isClosed()) {
                    res.close();
                    dbSetaObj.close();
                }
            }
        }

        return ValueLabel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_att_row, parent, false);
        return new ViewHolder(itemView);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem = aObjList.get(holder.getAdapterPosition());
        int BColor;


        if (position == 0) {
            BColor = res.getColor(R.color.row_head_1);

            Labels = this.getLabelFromDb("l_195_approve", R.string.l_195_approvee);
            holder.lblAction_Approve.setText(Labels);
            holder.lblAction_Approve.setTypeface(holder.lblAction_Approve.getTypeface(), Typeface.BOLD);
            holder.lblAction_Approve.setTextColor(res.getColor(R.color.white));
            holder.lblAction_Approve.setBackgroundColor(BColor);
            holder.lblAction_Approve.setVisibility(View.VISIBLE);
            holder.radioGroup.setVisibility(View.GONE);


            Labels = this.getLabelFromDb("l_195_remark", R.string.l_195_remark);
            holder.lblAction_Remark.setText(Labels);
            holder.lblAction_Remark.setTypeface(holder.lblAction_Remark.getTypeface(), Typeface.BOLD);
            holder.lblAction_Remark.setTextColor(res.getColor(R.color.white));
            holder.lblAction_Remark.setBackgroundColor(BColor);
            holder.lblAction_Remark.setVisibility(View.VISIBLE);
            holder.inputRemark.setVisibility(View.GONE);

            holder.lblTime.setTextColor(res.getColor(R.color.white));
            holder.lblTime.setTypeface(holder.lblTime.getTypeface(), Typeface.BOLD);


            holder.lblDate.setTextColor(res.getColor(R.color.white));
            holder.lblDate.setTypeface(holder.lblDate.getTypeface(), Typeface.BOLD);

            holder.lblDay.setTextColor(res.getColor(R.color.white));
            holder.lblDay.setTypeface(holder.lblDay.getTypeface(), Typeface.BOLD);

            holder.lblLoginTime.setTextColor(res.getColor(R.color.white));
            holder.lblLoginTime.setTypeface(holder.lblLoginTime.getTypeface(), Typeface.BOLD);

            holder.lblLogOutTime.setTextColor(res.getColor(R.color.white));
            holder.lblLogOutTime.setTypeface(holder.lblLogOutTime.getTypeface(), Typeface.BOLD);

            holder.lblCordDifference.setTextColor(res.getColor(R.color.white));
            holder.lblCordDifference.setTypeface(holder.lblCordDifference.getTypeface(), Typeface.BOLD);


        }

        else {
            if ((position % 2) == 0) {
                BColor = res.getColor(R.color.row_even);
            } else {
                BColor = res.getColor(R.color.row_odd);
            }





            if(aObjList.get(holder.getAdapterPosition()).approval_status10.equals("0")) {

            holder.lblAction_Approve.setVisibility(View.GONE);
            holder.radioGroup.setVisibility(View.VISIBLE);
            holder.radioGroup.setTag("radio_group_"+aObjList.get(holder.getAdapterPosition()).attt_id9);
            holder.inputRemark.setTag("remarks_"+aObjList.get(holder.getAdapterPosition()).attt_id9);
            holder.lblAction_Remark.setVisibility(View.GONE);
            holder.inputRemark.setVisibility(View.VISIBLE);


            }else   if(aObjList.get(holder.getAdapterPosition()).approval_status10.equals("1")) {

                holder.lblAction_Remark.setVisibility(View.GONE);
                holder.lblAction_Approve.setVisibility(View.GONE);
                holder.radioGroup.setVisibility(View.GONE);
                holder.inputRemark.setVisibility(View.GONE);
                holder.lblApprovalStatus.setVisibility(View.VISIBLE);
                holder.lbl_Remark.setVisibility(View.VISIBLE);
                Labels = this.getLabelFromDb("l_txt_Approve", R.string.l_txt_Approve);
                holder.lblApprovalStatus.setText(Labels);


            }else{

                holder.lblAction_Remark.setVisibility(View.GONE);
                holder.lblAction_Approve.setVisibility(View.GONE);
                holder.radioGroup.setVisibility(View.GONE);
                holder.inputRemark.setVisibility(View.GONE);
                holder.lblApprovalStatus.setVisibility(View.VISIBLE);
                holder.lbl_Remark.setVisibility(View.VISIBLE);
                Labels = this.getLabelFromDb("l_txt_DisApprove", R.string.l_txt_DisApprove);
                holder.lblApprovalStatus.setText(Labels);


            }

            holder.lbl_Remark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = aObjList.get(holder.getAdapterPosition()).remark11;
                    ((BaseAPC) activityInCall).showTooltip(holder.lbl_Remark, sToolTip, 4);
                }
            });


        }
     /*  holder.inputRemark.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // Do whatever you want here

                    return true;
                }
                return false;

            });
       }*/
/*  holder.inputRemark.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                EditText inputRemark=(EditText)view.findViewById(R.id.inputRemark) ;


                Object tag2 = view.getTag();


                if(inputRemark!=null){

                    inputRemarkMap.put(tag2.toString(),inputRemark.getText().toString());
                    System.out.println("INPUT REMARKS MAP"+inputRemarkMap);

                }


            }
        });
*/

        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbbutton=(RadioButton)group.findViewById(checkedId);

                Object tag = group.getTag();

                if(rbbutton.getText().toString().equalsIgnoreCase("App")){

                    approvalStatusMap.put(tag.toString(),"1");

                }else if(rbbutton.getText().toString().equalsIgnoreCase("Dis")){


                    approvalStatusMap.put(tag.toString(),"2");
                }

            }
        });


        //LAST ROW FOR SEND THE VALUES OF RADIO BUTTON AND REMARK
        if (aObjList.get(holder.getAdapterPosition()).mcord_diff8.equals("FOOTER@APPROVAL")||aObjList.get(holder.getAdapterPosition()).attt_id9.equals("attt_id9")) {
            int CColor;
            CColor = res.getColor(R.color.row_odd);
            holder.linearLAction.setVisibility(View.GONE);
            holder.linearLAction2.setVisibility(View.GONE);
            holder.linearLAction3.setVisibility(View.VISIBLE);
            Labels = this.getLabelFromDb("l_195_btn_submit", R.string.l_195_btn_submit);
            holder.BtnActionPost.setText(Labels);
            holder.lblDate.setBackgroundColor(CColor);
            holder.lblDay.setBackgroundColor(CColor);
            holder.lblTime.setBackgroundColor(CColor);
            holder.lblLoginTime.setBackgroundColor(CColor);
            holder.lblLogOutTime.setBackgroundColor(CColor);
            holder.lblCordDifference.setBackgroundColor(CColor);
            holder.linearLAction3.setBackgroundColor(CColor);
            // remark = holder.inputRemark.getText().toString();

            holder.BtnActionPost.setOnClickListener(new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.N)
                @Override
                public void onClick(View view) {

                    String APPStatus = "";


                    for (Map.Entry<String, String> entry : approvalStatusMap.entrySet())
                    {
                        String aKeyId=entry.getKey().replace("radio_group_","");


                        String aKey=entry.getKey().replace("radio_group_","remarks_");


                   //     APPStatus = APPStatus +aKeyId+"@@@"+ entry.getValue()+"@@@"+inputRemarkMap.get(aKey)+"***";
                        APPStatus = APPStatus +aKeyId+"@@@"+ entry.getValue()+"@@@"+inputRemarkMap.get(aKey)+"***";


                    }
                    ////baseActivity.setStatusRemarks(APPStatus);


                }
            });


        }

        else {

            holder.lblDate.setText(aObjList.get(holder.getAdapterPosition()).mVADate3);
            holder.lblDate.setBackgroundColor(BColor);

            holder.lblDay.setText(aObjList.get(holder.getAdapterPosition()).mVADay4);
            holder.lblDay.setBackgroundColor(BColor);

            holder.lblTime.setText(aObjList.get(holder.getAdapterPosition()).mVATime5);
            holder.lblTime.setBackgroundColor(BColor);

            holder.lblLoginTime.setText(aObjList.get(holder.getAdapterPosition()).lblLoginTime7);
            holder.lblLoginTime.setBackgroundColor(BColor);

            holder.lblLogOutTime.setText(aObjList.get(holder.getAdapterPosition()).lblLogoutTime6);
            holder.lblLogOutTime.setBackgroundColor(BColor);

            holder.lblCordDifference.setText(aObjList.get(holder.getAdapterPosition()).mcord_diff8);
            holder.lblCordDifference.setBackgroundColor(BColor);

           /* holder.lblApprovalStatus.setText(aObjList.get(holder.getAdapterPosition()).approval_status10);
            holder.lblApprovalStatus.setBackgroundColor(BColor);*/

            holder.lbl_Remark.setText(aObjList.get(holder.getAdapterPosition()).remark11);
            holder.lbl_Remark.setBackgroundColor(BColor);

            holder.linearLAction.setBackgroundColor(BColor);
            Labels = this.getLabelFromDb("l_rb_approve", R.string.l_rb_approve);
            holder.rb_approve.setText(Labels);

            holder.linearLAction2.setBackgroundColor(BColor);
            Labels = this.getLabelFromDb("l_rb_dis_approve", R.string.l_rb_dis_approve);
            holder.rb_disApprove.setText(Labels);

            holder.lblTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = aObjList.get(holder.getAdapterPosition()).mVATime5;
                    ((BaseAPC) activityInCall).showTooltip(holder.lblTime, sToolTip, 4);
                }
            });
            holder.lblDay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = aObjList.get(holder.getAdapterPosition()).mVADay4;
                    ((BaseAPC) activityInCall).showTooltip(holder.lblDay, sToolTip, 4);
                }
            });

            holder.lblDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = aObjList.get(holder.getAdapterPosition()).mVADate3;
                    ((BaseAPC) activityInCall).showTooltip(holder.lblDate, sToolTip, 4);
                }
            });
            holder.lblLoginTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = aObjList.get(holder.getAdapterPosition()).lblLoginTime7;
                    ((BaseAPC) activityInCall).showTooltip(holder.lblLoginTime, sToolTip, 4);
                }
            });
            holder.lblLogOutTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = aObjList.get(holder.getAdapterPosition()).lblLogoutTime6;
                    ((BaseAPC) activityInCall).showTooltip(holder.lblLogOutTime, sToolTip, 4);
                }
            }); holder.lblCordDifference.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = aObjList.get(holder.getAdapterPosition()).mcord_diff8;
                    ((BaseAPC) activityInCall).showTooltip(holder.lblCordDifference, sToolTip, 4);
                }
            });




        }
    }

    @Override
    public int getItemCount() {
        if (aObjList != null) {
            return aObjList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View hView;
        public MVAttObj.Item hItem;
        public final TableRow tRow;
        public final TextView lblTime, lblDay, lblDate, lblAction_Remark,lblAction_Approve,lblApprovalStatus,lbl_Remark;
        public final EditText inputRemark;
        public final Button BtnActionPost;

        public final RadioGroup radioGroup;
        public final RadioButton rb_approve;
        public final RadioButton rb_disApprove;
        public final RadioButton rb_custom;

        TextView lblLoginTime,lblLogOutTime,lblCordDifference;
        public final LinearLayout linearLAction2, linearLAction,linearLAction3;

        public ViewHolder(View itemView) {
            super(itemView);
            hView = itemView;
            tRow = (TableRow) itemView.findViewById(R.id.mViewAttList_Row);
            lblApprovalStatus = (TextView) itemView.findViewById(R.id.lblApprovalStatus);
            lbl_Remark = (TextView) itemView.findViewById(R.id.lbl_Remark);
            lblDate = (TextView) itemView.findViewById(R.id.lblDate);
            lblDay = (TextView) itemView.findViewById(R.id.lblDay);
            lblTime = (TextView) itemView.findViewById(R.id.lblTime);
            lblLoginTime = (TextView) itemView.findViewById(R.id.lblLoginTime);
            lblLogOutTime = (TextView) itemView.findViewById(R.id.lblLogOutTime);
            lblCordDifference = (TextView) itemView.findViewById(R.id.lblCordDifference);
            inputRemark = (EditText) itemView.findViewById(R.id.inputRemark);
            radioGroup = (RadioGroup) itemView.findViewById(R.id.radioGroup);
            rb_approve = (RadioButton) itemView.findViewById(R.id.rb_approve);
            rb_disApprove = (RadioButton) itemView.findViewById(R.id.rb_disApprove);
            rb_custom = (RadioButton) itemView.findViewById(R.id.rb_custom);
            linearLAction = (LinearLayout) itemView.findViewById(R.id.linearLAction);
            linearLAction2 = (LinearLayout) itemView.findViewById(R.id.linearLAction2);
            linearLAction3 = (LinearLayout) itemView.findViewById(R.id.linearLAction3);
            lblAction_Approve = (TextView) itemView.findViewById(R.id.lblAction_Approve);
            lblAction_Remark = (TextView) itemView.findViewById(R.id.lblAction_Remark);
            BtnActionPost = (Button) itemView.findViewById(R.id.BtnActionPost);
            inputRemark.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(inputRemark.getTag()!=null){
                     //   inputRemarkMap.put((int)inputRemark.getTag(),charSequence.toString());
                        inputRemarkMap.put((inputRemark.getTag().toString()),charSequence.toString());


                    }
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
    }
}
