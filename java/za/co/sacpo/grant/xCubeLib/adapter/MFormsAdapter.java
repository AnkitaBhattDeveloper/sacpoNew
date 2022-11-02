package za.co.sacpo.grant.xCubeLib.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPC;
import za.co.sacpo.grant.xCubeLib.dataObj.MFormsObj;
import za.co.sacpo.grant.xCubeLib.db.DbHelper;
import za.co.sacpo.grant.xCubeMentor.forms.MDownloadSignedFormA;
import za.co.sacpo.grant.xCubeMentor.forms.MDownloadUnSignedFormA;
import za.co.sacpo.grant.xCubeMentor.forms.MUploadSignedFormA;

/**
 * Created by xcube-06 on 7/8/18.
 */

public class MFormsAdapter extends RecyclerView.Adapter<MFormsAdapter.FormsHolder> {
    private List<MFormsObj.Item> aObjList;
    private Context baseActivityContext;
    protected DbHelper dbSetaObj;
    String Labels;
    private AppCompatActivity activityInCall;

    public MFormsAdapter(List<MFormsObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall) {
        this.aObjList = aObjList;
        this.baseActivityContext = baseActivityContext;
        this.activityInCall = activityInCall;
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
    @Override
    public FormsHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mform_row, parent, false);
        return new FormsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FormsHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem=aObjList.get(holder.getAdapterPosition());
        int BColor;
        if(holder.getAdapterPosition()==0){
            BColor = res.getColor(R.color.row_head);
            holder.lblFormName.setTextColor(res.getColor(R.color.white));
            holder.lblFormName.setTypeface(holder.lblFormName.getTypeface(), Typeface.BOLD);



            holder.lblAction.setText("ACTION");

            Labels =this.getLabelFromDb("l_612_Form_Action",R.string.l_612_Form_Action);
            holder.lblAction.setText(Labels);

            holder.lblAction.setTextColor(res.getColor(R.color.white));
            holder.lblAction.setVisibility(View.VISIBLE);



/*
            holder.lblAction2.setText(Labels);
            holder.lblAction2.setTextColor(res.getColor(R.color.white));
            holder.lblAction2.setVisibility(View.GONE);
            holder.btnUploadSignedForm.setVisibility(View.GONE);

            holder.lblAction3.setText(Labels);
            holder.lblAction3.setTextColor(res.getColor(R.color.white));
            holder.lblAction3.setVisibility(View.GONE);
            holder.btnDownloadSignedForm.setVisibility(View.GONE);*/



            holder.lblAction.setTypeface(holder.lblAction.getTypeface(), Typeface.BOLD);

            holder.lblFormName.setTextColor(res.getColor(R.color.white));
            holder.lblFormName.setTypeface(holder.lblFormName.getTypeface(), Typeface.BOLD);
            holder.lblFormId.setTextColor(res.getColor(R.color.white));
            holder.lblFormId.setTypeface(holder.lblFormId.getTypeface(), Typeface.BOLD);
            holder.lblFormLearner.setTextColor(res.getColor(R.color.white));
            holder.lblFormLearner.setTypeface(holder.lblFormLearner.getTypeface(), Typeface.BOLD);
            holder.lblFormHostEmp.setTextColor(res.getColor(R.color.white));
            holder.lblFormHostEmp.setTypeface(holder.lblFormHostEmp.getTypeface(), Typeface.BOLD);
            holder.lblFormLeadEmp.setTextColor(res.getColor(R.color.white));
            holder.lblFormLeadEmp.setTypeface(holder.lblFormLeadEmp.getTypeface(), Typeface.BOLD);
            holder.lblFormCompletion.setTextColor(res.getColor(R.color.white));
            holder.lblFormCompletion.setTypeface(holder.lblFormCompletion.getTypeface(), Typeface.BOLD);


        }
        else{

            holder.lblAction.setVisibility(View.GONE);
            holder.linearAction.setVisibility(View.VISIBLE);
            holder.btnDownlode.setVisibility(View.VISIBLE);

            if((holder.getAdapterPosition()%2)==0){
                BColor=res.getColor(R.color.row_even);
            }
            else{
                BColor=res.getColor(R.color.row_odd);
            }
        }



        //doubt position
        holder.lblFormName.setText(aObjList.get(holder.getAdapterPosition()).mFname3);
        holder.lblFormName.setBackgroundColor(BColor);

       /* holder.btnDownlode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle inputUri = new Bundle();
                inputUri.putInt("form_id", Integer.parseInt(holder.hItem.mFid11));
                Context context = v.getContext();
                Intent intent = new Intent(context, MDownloadSignedFormA.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);
            }
        });
<<<<<<< HEAD
*/


        holder.linearAction.setBackgroundColor(BColor);
/*
        holder.btnDownlode.setText("Downlode");
*/
        holder.linearAction.setVisibility(View.VISIBLE);
        holder.linearAction.setBackgroundColor(BColor);
        String Labels =this.getLabelFromDb("l_612_Form_btnDownloadUnsignedForm",R.string.l_612_Form_btnDownloadUnsignedForm);
        holder.btnDownlode.setText(Labels);

        holder.linearAction2.setVisibility(View.GONE);
        holder.linearAction2.setBackgroundColor(BColor);
        Labels =this.getLabelFromDb("l_612_Form_btnUploadSignedForm",R.string.l_612_Form_btnUploadSignedForm);
        holder.btnUploadSignedForm.setText(Labels);

        holder.linearAction2.setVisibility(View.GONE);
        holder.linearAction3.setBackgroundColor(BColor);
        Labels =this.getLabelFromDb("l_612_Form_btnDownloadSignedForm",R.string.l_612_Form_btnDownloadSignedForm);
        holder.btnDownloadSignedForm.setText(Labels);




        holder.lblFormName.setText(aObjList.get(holder.getAdapterPosition()).mFname3);
        holder.lblFormName.setBackgroundColor(BColor);
        holder.lblFormId.setText(aObjList.get(holder.getAdapterPosition()).mFid11);
        holder.lblFormId.setBackgroundColor(BColor);
        holder.lblFormLearner.setText(aObjList.get(holder.getAdapterPosition()).mFlearner4);
        holder.lblFormLearner.setBackgroundColor(BColor);
        holder.lblFormHostEmp.setText(aObjList.get(holder.getAdapterPosition()).mFhostEmp5);
        holder.lblFormHostEmp.setBackgroundColor(BColor);
        holder.lblFormLeadEmp.setText(aObjList.get(holder.getAdapterPosition()).mFleadEmp6);
        holder.lblFormLeadEmp.setBackgroundColor(BColor);

        holder.lblFormCompletion.setText(aObjList.get(holder.getAdapterPosition()).mFcomplition7);
        holder.lblFormCompletion.setBackgroundColor(BColor);


        holder.btnDownlode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popUp(view,aObjList.get(holder.getAdapterPosition()).aId2);
            }
        });


        holder.lblFormName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).mFname3;
                ((BaseAPC)activityInCall).showTooltip(holder.lblFormName,sToolTip,4);
            }
        });
        holder.lblFormId.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).mFid11;
                ((BaseAPC)activityInCall).showTooltip(holder.lblFormId,sToolTip,4);
            }
        });
        holder.lblFormLearner.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).mFlearner4;
                ((BaseAPC)activityInCall).showTooltip(holder.lblFormLearner,sToolTip,4);
            }
        });

        holder.lblFormHostEmp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).mFhostEmp5;
                ((BaseAPC)activityInCall).showTooltip(holder.lblFormHostEmp,sToolTip,4);
            }
        });

        holder.lblFormLeadEmp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).mFleadEmp6;
                ((BaseAPC)activityInCall).showTooltip(holder.lblFormLeadEmp,sToolTip,4);
            }
        });

        holder.lblFormCompletion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).mFcomplition7;
                ((BaseAPC)activityInCall).showTooltip(holder.lblFormCompletion,sToolTip,4);
            }
        });
    }

    private void popUp(final View view, final int form_id) {
        PopupMenu popup = new PopupMenu(baseActivityContext, view);
        popup.getMenuInflater().inflate(R.menu.popup_menu,popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Context context;
                switch (menuItem.getItemId()){

                    case R.id.one:
                        //do do
                        // Toast.makeText(baseActivityContext, "one:"+menuItem.getItemId(), Toast.LENGTH_SHORT).show();
                        Bundle inputUri = new Bundle();
                        String Form_id = String.valueOf(form_id);
                        inputUri.putString("form_id", Form_id);
                        inputUri.putString("generator", "Form_id");
                        context = view.getContext();
                        Intent intent = new Intent(context, MDownloadUnSignedFormA.class);
                        intent.putExtras(inputUri);
                        context.startActivity(intent);

                        break;

                    case R.id.two:
                        //do do

                        //Toast.makeText(baseActivityContext, "two:"+menuItem.getItemId(), Toast.LENGTH_SHORT).show();
                        Context context1 = view.getContext();
                        Intent intent1 = new Intent(context1,MUploadSignedFormA.class);
                        context1.startActivity(intent1);

                        break;


                    case R.id.three:
                        //do do
                        //Toast.makeText(baseActivityContext, "three:"+menuItem.getItemId(), Toast.LENGTH_SHORT).show();
                        Context context2 = view.getContext();
                        Intent intent2 = new Intent(context2, MDownloadSignedFormA.class);
                        context2.startActivity(intent2);

                        break;


                    default:
                        break;


                }

                return false;
            }
        });

        popup.show();
    }


    @Override
    public int getItemCount() {
        if(aObjList!=null) {
            return aObjList.size();
        }
        return 0;
    }

    public class FormsHolder extends RecyclerView.ViewHolder {
        public final View hView;
        public MFormsObj.Item hItem;
        public final TableRow tRow;
        public final TextView lblFormName,lblFormId,lblFormLearner,lblFormHostEmp,lblFormLeadEmp,lblFormCompletion,lblAction;

       public Button btnDownlode,btnUploadSignedForm,btnDownloadSignedForm;

        public final LinearLayout linearAction,linearAction2,linearAction3;
        public FormsHolder(View itemView) {
            super(itemView);
            hView = itemView;
            tRow= (TableRow) itemView.findViewById(R.id.mform_Row);

            linearAction = (LinearLayout) itemView.findViewById(R.id.linearAction);
            linearAction2 = (LinearLayout) itemView.findViewById(R.id.linearAction2);
            linearAction3 = (LinearLayout) itemView.findViewById(R.id.linearAction3);


            lblFormName= (TextView) itemView.findViewById(R.id.lblFormName);
            lblFormId= (TextView) itemView.findViewById(R.id.lblFormId);
            lblFormLearner= (TextView) itemView.findViewById(R.id.lblFormLearner);
            lblFormHostEmp= (TextView) itemView.findViewById(R.id.lblFormHostEmp);
            lblFormLeadEmp= (TextView) itemView.findViewById(R.id.lblFormLeadEmp);
            lblFormCompletion= (TextView) itemView.findViewById(R.id.lblFormCompletion);
            lblAction= (TextView) itemView.findViewById(R.id.lblAction);

            btnDownlode= (Button) itemView.findViewById(R.id.btnDownlode);
            btnUploadSignedForm= (Button) itemView.findViewById(R.id.btnUploadSignedForm);
            btnDownloadSignedForm= (Button) itemView.findViewById(R.id.btnDownloadSignedForm);
        }
    }
}
