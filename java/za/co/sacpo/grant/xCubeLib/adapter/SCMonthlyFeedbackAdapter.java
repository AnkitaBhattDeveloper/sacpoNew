package za.co.sacpo.grant.xCubeLib.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.dataObj.SCMonthlyFeedbackObj;
import za.co.sacpo.grant.xCubeLib.db.DbHelper;
import za.co.sacpo.grant.xCubeStudent.claims.SCMonthlyFeedbackA;

public class SCMonthlyFeedbackAdapter extends RecyclerView.Adapter<SCMonthlyFeedbackAdapter.ViewHolder> {
    private List<SCMonthlyFeedbackObj.Item> aObjList;
    private String CAId;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    protected DbHelper dbSetaObj;
    String Labels;
    private Map<String, String> optionStatus = new HashMap<String, String>();

    SCMonthlyFeedbackA baseActivity;

    public SCMonthlyFeedbackAdapter(List<SCMonthlyFeedbackObj.Item> aObjList, Context baseActivityContext, AppCompatActivity activityInCall, SCMonthlyFeedbackA baseActivity) {
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

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_sc_feedback_reports, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem = aObjList.get(holder.getAdapterPosition());
        int BColor;
        BColor = res.getColor(R.color.row_head_1);
        holder.txt_question.setTextColor(res.getColor(R.color.black_three));
        holder.txt_question.setTypeface(holder.txt_question.getTypeface(), Typeface.BOLD);

        holder.txt_question.setText(aObjList.get(holder.getAdapterPosition()).SFQues3);
        holder.rg_optn.setTag("radio_group_"+aObjList.get(holder.getAdapterPosition()).SFQ_Id4);
      //  holder.next();
        holder.rb_option1.setText(aObjList.get(holder.getAdapterPosition()).SANS_TYPE_17);
        holder.rb_option2.setText(aObjList.get(holder.getAdapterPosition()).SANS_TYPE_29);
        holder.rb_option3.setText(aObjList.get(holder.getAdapterPosition()).SANS_TYPE_311);
        holder.rb_option4.setText(aObjList.get(holder.getAdapterPosition()).SANS_TYPE_413);
        holder.rb_option5.setText(aObjList.get(holder.getAdapterPosition()).SANS_TYPE_515);
        holder.rb_option0.setText("");

        holder.rb_option1.setTag(aObjList.get(holder.getAdapterPosition()).SANS_TYPE_ID18);
        holder.rb_option2.setTag(aObjList.get(holder.getAdapterPosition()).SANS_TYPE_ID210);
        holder.rb_option3.setTag(aObjList.get(holder.getAdapterPosition()).SANS_TYPE_ID312);
        holder.rb_option4.setTag(aObjList.get(holder.getAdapterPosition()).SANS_TYPE_ID414);
        holder.rb_option5.setTag(aObjList.get(holder.getAdapterPosition()).SANS_TYPE_ID516);
        holder.rb_option0.setTag("");


        if(aObjList.get(holder.getAdapterPosition()).SANS_TYPE5.equals("")  || aObjList.get(holder.getAdapterPosition()).SANS_TYPE5.equals("null")){
            optionStatus.put("radio_group_"+aObjList.get(holder.getAdapterPosition()).SFQ_Id4,"");
            holder.rb_option0.setChecked(true);
            //Log.i("rb_option0",holder.rb_option0.getTag().toString());
        }
        else{
            if(aObjList.get(holder.getAdapterPosition()).SANS_TYPE5.equals(aObjList.get(holder.getAdapterPosition()).SANS_TYPE_ID18)){
                holder.rb_option1.setChecked(true);
                //Log.i("rb_option1",holder.rb_option1.getTag().toString());
                optionStatus.put("radio_group_"+aObjList.get(holder.getAdapterPosition()).SFQ_Id4,aObjList.get(holder.getAdapterPosition()).SANS_TYPE_ID18);
            }
            else if(aObjList.get(holder.getAdapterPosition()).SANS_TYPE5.equals(aObjList.get(holder.getAdapterPosition()).SANS_TYPE_ID210)){
                optionStatus.put("radio_group_"+aObjList.get(holder.getAdapterPosition()).SFQ_Id4,aObjList.get(holder.getAdapterPosition()).SANS_TYPE_ID210);
                holder.rb_option2.setChecked(true);
                //Log.i("rb_option2",holder.rb_option2.getTag().toString());
            }
            else if(aObjList.get(holder.getAdapterPosition()).SANS_TYPE5.equals(aObjList.get(holder.getAdapterPosition()).SANS_TYPE_ID312)){
                optionStatus.put("radio_group_"+aObjList.get(holder.getAdapterPosition()).SFQ_Id4,aObjList.get(holder.getAdapterPosition()).SANS_TYPE_ID312);
                holder.rb_option3.setChecked(true);
                //Log.i("rb_option3",holder.rb_option3.getTag().toString());
            }
            else if(aObjList.get(holder.getAdapterPosition()).SANS_TYPE5.equals(aObjList.get(holder.getAdapterPosition()).SANS_TYPE_ID414)){
                optionStatus.put("radio_group_"+aObjList.get(holder.getAdapterPosition()).SFQ_Id4,aObjList.get(holder.getAdapterPosition()).SANS_TYPE_ID414);
                holder.rb_option4.setChecked(true);
                //Log.i("rb_option4",holder.rb_option4.getTag().toString());
            }
            else if(aObjList.get(holder.getAdapterPosition()).SANS_TYPE5.equals(aObjList.get(holder.getAdapterPosition()).SANS_TYPE_ID516)){
                optionStatus.put("radio_group_"+aObjList.get(holder.getAdapterPosition()).SFQ_Id4,aObjList.get(holder.getAdapterPosition()).SANS_TYPE_ID516);
                holder.rb_option5.setChecked(true);
                //Log.i("rb_option5",holder.rb_option5.getTag().toString());
            }
        }
        holder.next();
        holder.rg_optn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbbutton=(RadioButton)group.findViewById(checkedId);

                Object tag = group.getTag();
                //optionStatus.replace(tag.toString(),rbbutton.getTag().toString());
                optionStatus.remove(tag.toString());
                //Log.i("ssss",tag.toString()+">>>"+rbbutton.getTag().toString());
                optionStatus.put(tag.toString(),rbbutton.getTag().toString());
                holder.next();
            }
        });

    }
    @Override
    public int getItemCount() {
        if (aObjList != null) {
            return aObjList.size();
        }
        return 0;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public final View hView;
        public SCMonthlyFeedbackObj.Item hItem;
        public TextView txt_question;
        public RadioGroup rg_optn;
        public RadioButton rb_option1,rb_option2,rb_option3,rb_option4,rb_option5,rb_option0;
        public Boolean setError = false;
        public ViewHolder(View itemView) {
            super(itemView);
            hView = itemView;
            txt_question = (TextView) itemView.findViewById(R.id.txt_question);
            rg_optn = (RadioGroup) itemView.findViewById(R.id.rg_optn);
            rb_option1 = (RadioButton) itemView.findViewById(R.id.rb_option1);
            rb_option2 = (RadioButton) itemView.findViewById(R.id.rb_option2);
            rb_option3 = (RadioButton) itemView.findViewById(R.id.rb_option3);
            rb_option4 = (RadioButton) itemView.findViewById(R.id.rb_option4);
            rb_option5 = (RadioButton) itemView.findViewById(R.id.rb_option5);
            rb_option0 = (RadioButton) itemView.findViewById(R.id.rb_option0);
        }
        public void next() {
            int countError = 0;
            String feedback_ratings = "";
            for (Map.Entry<String, String> entry : optionStatus.entrySet()) {
                String QKeyId = entry.getKey().replace("radio_group_","");
                String QKeyValue = entry.getValue().replace("radio_group_","");
                //Log.i("ssss",QKeyId+">>>"+QKeyValue);
                if(QKeyValue.isEmpty()){
                    countError=countError+1;
                }
                else{
                    feedback_ratings = feedback_ratings +QKeyId+"@*@"+QKeyValue+"~~~";
                }
                if(countError>0){
                    setError = true;
                }
                else {
                    setError = false;
                }
                //feedback_ratings = feedback_ratings +QKeyId+"@*@"+QKeyValue+"~~~";
            }

            if(setError==true){
                feedback_ratings="";
            }
            baseActivity.setStatusRemarks(feedback_ratings);
        }
    }
}