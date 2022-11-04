package za.co.sacpo.grant.xCubeLib.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.component.TimeEditText;
import za.co.sacpo.grant.xCubeLib.dataObj.MCurrentAttObj;
import za.co.sacpo.grant.xCubeLib.db.DbHelper;
import za.co.sacpo.grant.xCubeLib.db.DbSchema;
import za.co.sacpo.grant.xCubeLib.spinners.SpinnerSet;
import za.co.sacpo.grant.xCubeLib.spinners.SpinnerSetAdapter;
import za.co.sacpo.grant.xCubeMentor.attendance.MCurrentAttA;

public class MCurrentAttAdapter extends RecyclerView.Adapter<MCurrentAttAdapter.AttHolder> {
    private List<MCurrentAttObj.Item> aObjList;
    private String CAId;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    protected DbHelper dbSetaObj;
    String Labels;
    private SpinnerSetAdapter adapterAttType;
    private SpinnerSet[] attType;
    MCurrentAttA baseActivity;
    int selected_attendance_type;
    private Map<String, String> attendanceIds = new HashMap<String, String>();
    private Map<String, String> attendanceStatus = new HashMap<String, String>();
    private Map<String, String> signINStatus = new HashMap<String, String>();
    private Map<String, String> signOutStatus = new HashMap<String, String>();
    public MCurrentAttAdapter(List<MCurrentAttObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall, MCurrentAttA baseActivity) {
        this.aObjList = aObjList;
        this.baseActivityContext = baseActivityContext;
        this.activityInCall = activityInCall;
        this.baseActivity = baseActivity;
    }

    public String getLabelFromDb(String inputLabel,int resId){
        String ValueLabel =baseActivityContext.getString(resId);
        dbSetaObj = DbHelper.getInstance(baseActivityContext);
        Cursor res = dbSetaObj.getDataValueByName(inputLabel);
        if(res.getCount() > 0) {
            try {
                while (res.moveToNext()) {
                    ValueLabel = res.getString(0);
                }
            }
            finally {
                if (res != null && !res.isClosed()) {
                    res.close();
                    dbSetaObj.close();
                }
            }
        }
        return ValueLabel;
    }
    /*public int getSelectedPosition(Spinner spnr, String value,String type) {
        int pos=0;
        SpinnerAdapter adapter = (SpinnerAdapter) spnr.getAdapter();
        for (int position = 0; position < adapter.getCount(); position++) {
            if(adapter.getItem(position).getId().equals(value)){
                pos = position;
            }
        }
        return pos;
    }*/
    public int getSelectedPositionSetAdapter(Spinner spnr, String value) {
        Long val = Long.parseLong(value);
        int pos=0;
        SpinnerSetAdapter adapter = (SpinnerSetAdapter) spnr.getAdapter();
        for (int position = 0; position < adapter.getCount(); position++) {
            if(adapter.getItemId(position) == val) {
                pos = position;
            }
        }
        return pos;
    }

    public void initializeSpinner(Spinner attendanceSpinner){
        dbSetaObj = DbHelper.getInstance(baseActivityContext);
        Cursor resUTM = dbSetaObj.getAllByType(DbSchema.TABLE_MASTER_ARRAYS,DbSchema.COLUMN_M_A_TYPE,11);
        int total_leave_type = resUTM.getCount();
        attType = new SpinnerSet[total_leave_type];
        if(total_leave_type > 0) {
            int i=0;
            try {
                while (resUTM.moveToNext()) {
                    attType[i] = new SpinnerSet();
                    attType[i].setId(resUTM.getInt(resUTM.getColumnIndex(DbSchema.COLUMN_M_A_SERVER_ID)));
                    attType[i].setName(resUTM.getString(resUTM.getColumnIndex(DbSchema.COLUMN_M_A_NAME)));
                    i++;
                }
            } finally {
                if (resUTM != null && !resUTM.isClosed()) {
                    resUTM.close();
                }
            }
        }
        adapterAttType = new SpinnerSetAdapter(baseActivity,android.R.layout.simple_spinner_item,attType);
        attendanceSpinner.setAdapter(adapterAttType);
    }
    @Override
    public AttHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.m_att_out_of_range_row, parent, false);
        return new AttHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AttHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem=aObjList.get(holder.getAdapterPosition());
        int BColor;
        if(holder.getAdapterPosition()==0){
            BColor = res.getColor(R.color.row_head_1);
            holder.lblLoginDate.setTypeface(holder.lblLoginDate.getTypeface(), Typeface.BOLD);
            holder.lblDay.setTypeface(holder.lblDay.getTypeface(), Typeface.BOLD);
            holder.lblEditAttendance.setTypeface(holder.lblEditAttendance.getTypeface(), Typeface.BOLD);
            holder.lblSignInTime.setTypeface(holder.lblSignInTime.getTypeface(), Typeface.BOLD);
            holder.lblSignOutTime.setTypeface(holder.lblSignOutTime.getTypeface(), Typeface.BOLD);
            holder.lblHoursWorked.setTypeface(holder.lblHoursWorked.getTypeface(), Typeface.BOLD);
            holder.lblDistanceFrmWorkstation.setTypeface(holder.lblDistanceFrmWorkstation.getTypeface(), Typeface.BOLD);
            holder.lblLearnerComment.setTypeface(holder.lblLearnerComment.getTypeface(), Typeface.BOLD);
            holder.attendanceSpinner.setVisibility(View.GONE);
            holder.editTextSignInTime.setVisibility(View.GONE);
            holder.editTextSignOutTime.setVisibility(View.GONE);
            holder.lblLoginDate.setTextColor(res.getColor(R.color.white));
            holder.lblDay.setTextColor(res.getColor(R.color.white));
            holder.lblEditAttendance.setTextColor(res.getColor(R.color.white));
            holder.lblSignInTime.setTextColor(res.getColor(R.color.white));
            holder.lblSignOutTime.setTextColor(res.getColor(R.color.white));
            holder.lblHoursWorked.setTextColor(res.getColor(R.color.white));
            holder.lblDistanceFrmWorkstation.setTextColor(res.getColor(R.color.white));
            holder.lblLearnerComment.setTextColor(res.getColor(R.color.white));

            holder.lblLoginDate.setText(aObjList.get(holder.getAdapterPosition()).aDate3);
            holder.lblDay.setText(aObjList.get(holder.getAdapterPosition()).aDay4);
            holder.lblEditAttendance.setText(aObjList.get(holder.getAdapterPosition()).aDisCord5);
            holder.lblSignInTime.setText(aObjList.get(holder.getAdapterPosition()).aLearnerStatus6);
            holder.lblSignOutTime.setText(aObjList.get(holder.getAdapterPosition()).aLearnerStatusId7);
            holder.lblHoursWorked.setText(aObjList.get(holder.getAdapterPosition()).aSignInHours8);
            holder.lblDistanceFrmWorkstation.setText(aObjList.get(holder.getAdapterPosition()).aSignInMin9);
            holder.lblLearnerComment.setText(aObjList.get(holder.getAdapterPosition()).aSignInSec10);

        }
        else{
            holder.tRow.setTag(aObjList.get(holder.getAdapterPosition()).aId2);
            Object tag = holder.tRow.getTag();
            attendanceIds.put(tag.toString(),aObjList.get(holder.getAdapterPosition()).aId2);

            if((holder.getAdapterPosition()%2)==0){
                //BColor=res.getColor(R.color.row_even);
                BColor=res.getColor(R.color.row_even);
            }
            else{
                BColor=res.getColor(R.color.row_odd);
            }
            holder.lblLoginDate.setText(aObjList.get(holder.getAdapterPosition()).aDate3);
            holder.lblDay.setText(aObjList.get(holder.getAdapterPosition()).aDay4);
            holder.lblHoursWorked.setText(aObjList.get(holder.getAdapterPosition()).aHoursWorked16);
            holder.lblDistanceFrmWorkstation.setText(aObjList.get(holder.getAdapterPosition()).aDisCord5);
            holder.lblLearnerComment.setText(aObjList.get(holder.getAdapterPosition()).aLearnerComment17);
            holder.lblEditAttendance.setVisibility(View.GONE);

            initializeSpinner(holder.attendanceSpinner);
            holder.attendanceSpinner.setTag("attendance_"+aObjList.get(holder.getAdapterPosition()).aId2);
            holder.attendanceSpinner.setVisibility(View.VISIBLE);
            holder.attendanceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view,int position, long id) {
                    int item =position;
                    selected_attendance_type = attType[item].getId();
                    if(selected_attendance_type==1){
                        holder.editTextSignInTime.setVisibility(View.VISIBLE);
                        holder.editTextSignOutTime.setVisibility(View.VISIBLE);
                        holder.editTextSignInTime.setText(aObjList.get(holder.getAdapterPosition()).aSignInTime14);
                        holder.editTextSignOutTime.setText(aObjList.get(holder.getAdapterPosition()).aSignOutTime15);
                        holder.lblSignInTime.setVisibility(View.GONE);
                        holder.lblSignOutTime.setVisibility(View.GONE);
                    }
                    else{
                        holder.lblSignInTime.setVisibility(View.VISIBLE);
                        holder.lblSignOutTime.setVisibility(View.VISIBLE);
                        holder.lblSignInTime.setText("-");
                        holder.lblSignOutTime.setText("-");
                        holder.editTextSignInTime.setVisibility(View.GONE);
                        holder.editTextSignOutTime.setVisibility(View.GONE);
                    }
                    if(selected_attendance_type==0){
                        selected_attendance_type =2;
                    }
                    Object tag = holder.tRow.getTag();
                    attendanceStatus.remove(tag.toString());
                    attendanceStatus.put(tag.toString(),String.valueOf(selected_attendance_type));
                    holder.next();

                }
                @Override
                public void onNothingSelected(AdapterView<?> adapter) {selected_attendance_type=2;}
            });
            holder.editTextSignInTime.setTag("in_time_"+aObjList.get(holder.getAdapterPosition()).aId2);
            holder.editTextSignOutTime.setTag("out_time_"+aObjList.get(holder.getAdapterPosition()).aId2);
            String selectedAS = aObjList.get(holder.getAdapterPosition()).aLearnerStatusId7;
            int selectedAttendanceStatusPos1=getSelectedPositionSetAdapter(holder.attendanceSpinner,selectedAS);
            holder.attendanceSpinner.setSelection(selectedAttendanceStatusPos1);
            if(selectedAS.equals("1")){
                holder.lblSignInTime.setVisibility(View.GONE);
                holder.lblSignOutTime.setVisibility(View.GONE);
                holder.editTextSignInTime.setVisibility(View.VISIBLE);
                holder.editTextSignOutTime.setVisibility(View.VISIBLE);
                holder.editTextSignInTime.setText(aObjList.get(holder.getAdapterPosition()).aSignInTime14);
                holder.editTextSignOutTime.setText(aObjList.get(holder.getAdapterPosition()).aSignOutTime15);
            }
            else{
                holder.lblSignInTime.setVisibility(View.VISIBLE);
                holder.lblSignOutTime.setVisibility(View.VISIBLE);
                holder.editTextSignInTime.setVisibility(View.GONE);
                holder.editTextSignOutTime.setVisibility(View.GONE);
            }
            holder.editTextSignInTime.addTextChangedListener(new TextWatcher() {
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() > 0) {
                        Object tag = holder.tRow.getTag();
                        signINStatus.remove(tag.toString());
                        signINStatus.put(tag.toString(),String.valueOf(holder.editTextSignInTime.getText()));
                        holder.next();
                    }
                    else{
                        Object tag = holder.tRow.getTag();
                        signINStatus.remove(tag.toString());
                        signINStatus.put(tag.toString(),String.valueOf("00:00"));
                        holder.next();
                    }
                }
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                public void afterTextChanged(Editable s) {

                }
            });
            holder.editTextSignOutTime.addTextChangedListener(new TextWatcher() {
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() > 0) {
                        Object tag = holder.tRow.getTag();
                        signOutStatus.remove(tag.toString());
                        signOutStatus.put(tag.toString(),String.valueOf(holder.editTextSignOutTime.getText()));
                        holder.next();
                    }
                    else{
                        Object tag = holder.tRow.getTag();
                        signOutStatus.remove(tag.toString());
                        signOutStatus.put(tag.toString(),String.valueOf("00:00"));
                        holder.next();
                    }
                }
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                public void afterTextChanged(Editable s) {

                }
            });

            holder.lblLoginDate.setBackgroundColor(BColor);
            holder.lblDay.setBackgroundColor(BColor);
            holder.lblHoursWorked.setBackgroundColor(BColor);
            holder.lblDistanceFrmWorkstation.setBackgroundColor(BColor);
            holder.lblLearnerComment.setBackgroundColor(BColor);
            holder.lblEditAttendance.setBackgroundColor(BColor);
            holder.lblSignInTime.setBackgroundColor(BColor);
            holder.lblSignOutTime.setBackgroundColor(BColor);
            holder.editTextSignInTime.setBackgroundColor(BColor);
            holder.editTextSignOutTime.setBackgroundColor(BColor);

            holder.next();
        }
    }

    @Override
    public int getItemCount() {
        if(aObjList!=null) {
            return aObjList.size();
        }
        return 0;
    }

    public class AttHolder extends RecyclerView.ViewHolder {
        public final View hView;
        public MCurrentAttObj.Item hItem;
        public final TableRow tRow;
        public final TextView lblLoginDate,lblDay,lblEditAttendance,lblSignInTime,lblSignOutTime,lblHoursWorked,lblDistanceFrmWorkstation,lblLearnerComment;
        public final Spinner attendanceSpinner;
        public final LinearLayout LLAttendance,LLSignIn,LLSignOut;
        public final TimeEditText editTextSignInTime;
        public final TimeEditText editTextSignOutTime;
        public AttHolder(View itemView) {
            super(itemView);
            hView = itemView;
            tRow= (TableRow) itemView.findViewById(R.id.tRow);
            lblLoginDate= itemView.findViewById(R.id.lblLoginDate);
            lblDay= itemView.findViewById(R.id.lblDay);
            lblEditAttendance= itemView.findViewById(R.id.lblEditAttendance);
            lblSignInTime= itemView.findViewById(R.id.lblSignInTime);
            lblSignOutTime= itemView.findViewById(R.id.lblSignOutTime);
            lblHoursWorked= itemView.findViewById(R.id.lblHoursWorked);
            lblDistanceFrmWorkstation= itemView.findViewById(R.id.lblDistanceFrmWorkstation);
            lblLearnerComment= itemView.findViewById(R.id.lblLearnerComment);
            attendanceSpinner= itemView.findViewById(R.id.attendanceSpinner);
            LLAttendance= itemView.findViewById(R.id.LLAttendance);
            LLSignIn= itemView.findViewById(R.id.LLSignIn);
            LLSignOut= itemView.findViewById(R.id.LLSignOut);
            editTextSignInTime= itemView.findViewById(R.id.editTextSignInTime);
            editTextSignOutTime= itemView.findViewById(R.id.editTextSignOutTime);
        }
        public void next() {
            int countError = 0;
            String attendance_str = "";
            for (Map.Entry<String, String> entry : attendanceIds.entrySet()) {
                String QKeyId = entry.getKey();
                String ans =attendanceStatus.get(QKeyId);
                String sin =signINStatus.get(QKeyId);
                String sout =signOutStatus.get(QKeyId);
                attendance_str = attendance_str +QKeyId+"@*@"+ans+"@*@"+sin+"@*@"+sout+"~~~";

            }
            baseActivity.setStatusRemarks(attendance_str);
        }
    }
}
