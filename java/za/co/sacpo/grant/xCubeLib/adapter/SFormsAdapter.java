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
import za.co.sacpo.grant.xCubeLib.dataObj.SFormsObj;
import za.co.sacpo.grant.xCubeLib.db.DbHelper;
import za.co.sacpo.grant.xCubeStudent.forms.DownloadMentorSignedFormA;
import za.co.sacpo.grant.xCubeStudent.forms.SDownloadFormA;
import za.co.sacpo.grant.xCubeStudent.forms.SDownloadLeadEmployerSignedFormA;
import za.co.sacpo.grant.xCubeStudent.forms.SDownloadUnSignFormA;
import za.co.sacpo.grant.xCubeStudent.forms.SUploadFormA;

/**
 * Created by xcube-06 on 7/8/18.
 */

public class SFormsAdapter extends RecyclerView.Adapter<SFormsAdapter.FormsHolder> {
    private List<SFormsObj.Item> aObjList;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    protected DbHelper dbSetaObj;
    String Labels;

    public SFormsAdapter(List<SFormsObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall) {
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
                .inflate(R.layout.sform_row, parent, false);
        return new FormsHolder(itemView);
    }
    private void popup(final View view, final int form_id) {


        PopupMenu popup = new PopupMenu(baseActivityContext, view);
        popup.getMenuInflater().inflate(R.menu.popup_menu,popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){

                    case R.id.one:
                        //do do
                       // Toast.makeText(baseActivityContext, "one:"+menuItem.getItemId(), Toast.LENGTH_SHORT).show();
                        Bundle inputUri = new Bundle();
                        String Form_id = String.valueOf(form_id);
                        inputUri.putString("form_id", Form_id);
                        inputUri.putString("generator", "form_id");
                        Context context = view.getContext();
                        Intent intent = new Intent(context, SDownloadUnSignFormA.class);
                        intent.putExtras(inputUri);
                        context.startActivity(intent);

                        break;

                    case R.id.two:
                        //do do

                        //Toast.makeText(baseActivityContext, "two:"+menuItem.getItemId(), Toast.LENGTH_SHORT).show();
                        Context context1 = view.getContext();
                        Intent intent1 = new Intent(context1,SUploadFormA.class);
                        context1.startActivity(intent1);
                        
                        break;


                    case R.id.three:
                        //do do
                        //Toast.makeText(baseActivityContext, "three:"+menuItem.getItemId(), Toast.LENGTH_SHORT).show();
                        Context context2 = view.getContext();
                        Intent intent2 = new Intent(context2, SDownloadFormA.class);
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
    public void onBindViewHolder(final FormsHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem=aObjList.get(holder.getAdapterPosition());
        int BColor;
        if(holder.getAdapterPosition()==0){
            BColor = res.getColor(R.color.row_head_1);
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


                holder.linearLAction.setVisibility(View.VISIBLE);
            Labels =this.getLabelFromDb("l_606_SForm_Downloadform",R.string.l_606_SForm_Downloadform);
                holder.btnAction.setText(Labels);
                holder.lblFormLearner.setVisibility(View.GONE);

                holder.linearLAction2.setVisibility(View.VISIBLE);
            Labels =this.getLabelFromDb("l_607_SForm_DownloadMentorsignedform",R.string.l_606_SForm_DownloadMentorsignedform);
                holder.btnAction2.setText(Labels);
                holder.lblFormHostEmp.setVisibility(View.GONE);



                holder.linearLAction3.setVisibility(View.VISIBLE);
            Labels =this.getLabelFromDb("l_607_SForm_DownloadLeadEmpSignedform",R.string.l_606_SForm_DownloadLeadEmpSignedform);
                holder.btnAction3.setText(Labels);
                holder.lblFormLeadEmp.setVisibility(View.GONE);



            if((holder.getAdapterPosition()%2)==0){
                BColor=res.getColor(R.color.row_even);
            }
            else{
                BColor=res.getColor(R.color.row_odd);
            }
        }



        holder.btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popup(view,aObjList.get(holder.getAdapterPosition()).aId2);
            }
        });

        holder.btnAction2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              /*  Bundle inputUri = new Bundle();
                String form_id = String.valueOf(holder.hItem.aId2);
                String Form_id = String.valueOf(form_id);
                inputUri.putString("Form_id", Form_id);
                inputUri.putString("generator", "Form_id");
                Context context = view.getContext();
                Intent intent = new Intent(context, DownloadMentorSignedFormA.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);
*/

                Bundle inputUri = new Bundle();
                String form_id = String.valueOf(holder.hItem.aId2);
                inputUri.putString("form_id", form_id);
                inputUri.putString("date_input", "");
                inputUri.putString("generator", "sForm");
                Context context = view.getContext();
                Intent intent = new Intent(context, DownloadMentorSignedFormA.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);


            }
        });

        holder.btnAction3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(baseActivityContext, "CLICKED:"+getClass(), Toast.LENGTH_SHORT).show();
              /*  Bundle inputUri = new Bundle();
                inputUri.putString("generator", "Form_id");
                Context context = view.getContext();
                Intent intent = new Intent(context, SDownloadLeadEmployerSignedFormA.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);*/

               /* Bundle inputUri = new Bundle();
                inputUri.putInt("form_id", holder.hItem.aId2);
                Context context = view.getContext();
                Intent intent = new Intent(context, SDownloadLeadEmployerSignedFormA.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);*/
               /* Bundle inputUri = new Bundle();
                inputUri.putInt("f_id", holder.hItem.aId2);
                Context context = view.getContext();
                Intent intent = new Intent(context,SDownloadLeadEmployerSignedFormA.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);*/
                Bundle inputUri = new Bundle();
                String form_id = String.valueOf(holder.hItem.aId2);
                inputUri.putString("form_id", form_id);
                inputUri.putString("date_input", "");
                inputUri.putString("generator", "sForm");
                Context context = view.getContext();
                Intent intent = new Intent(context, SDownloadLeadEmployerSignedFormA.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);



            }
        });



        holder.lblFormName.setText(aObjList.get(holder.getAdapterPosition()).sFname3);
        holder.lblFormName.setBackgroundColor(BColor);
        holder.lblFormId.setText(aObjList.get(holder.getAdapterPosition()).sFid11);
        holder.lblFormId.setBackgroundColor(BColor);
        holder.lblFormLearner.setText(aObjList.get(holder.getAdapterPosition()).sFlearner4);
        holder.lblFormLearner.setBackgroundColor(BColor);
        holder.lblFormHostEmp.setText(aObjList.get(holder.getAdapterPosition()).sFhostEmp5);
        holder.lblFormHostEmp.setBackgroundColor(BColor);
        holder.lblFormLeadEmp.setText(aObjList.get(holder.getAdapterPosition()).sFleadEmp6);
        holder.lblFormLeadEmp.setBackgroundColor(BColor);
        holder.lblFormCompletion.setText(aObjList.get(holder.getAdapterPosition()).sFcomplition7);
        holder.lblFormCompletion.setBackgroundColor(BColor);



        holder.lblFormName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).sFname3;
                ((BaseAPC)activityInCall).showTooltip(holder.lblFormName,sToolTip,4);
            }
        });
        holder.lblFormId.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).sFid11;
                ((BaseAPC)activityInCall).showTooltip(holder.lblFormId,sToolTip,4);
            }
        });
        holder.lblFormLearner.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).sFlearner4;
                ((BaseAPC)activityInCall).showTooltip(holder.lblFormLearner,sToolTip,4);
            }
        });

        holder.lblFormHostEmp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).sFhostEmp5;
                ((BaseAPC)activityInCall).showTooltip(holder.lblFormHostEmp,sToolTip,4);
            }
        });

        holder.lblFormLeadEmp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).sFleadEmp6;
                ((BaseAPC)activityInCall).showTooltip(holder.lblFormLeadEmp,sToolTip,4);
            }
        });

        holder.lblFormCompletion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).sFcomplition7;
                ((BaseAPC)activityInCall).showTooltip(holder.lblFormCompletion,sToolTip,4);
            }
        });
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
        public SFormsObj.Item hItem;
        public final TableRow tRow;
        public final Button btnAction,btnAction2,btnAction3;
       public final LinearLayout linearLAction,linearLAction2,linearLAction3;
        public final TextView lblFormName,lblFormId,lblFormLearner,lblFormHostEmp,lblFormLeadEmp,lblFormCompletion;//,lblAction;

        public FormsHolder(View itemView) {
            super(itemView);
            hView = itemView;
            tRow= (TableRow) itemView.findViewById(R.id.form_Row);




            lblFormName= (TextView) itemView.findViewById(R.id.lblFormName);
            lblFormId= (TextView) itemView.findViewById(R.id.lblFormId);
            lblFormLearner= (TextView) itemView.findViewById(R.id.lblFormLearner);
            lblFormHostEmp= (TextView) itemView.findViewById(R.id.lblFormHostEmp);
            lblFormLeadEmp= (TextView) itemView.findViewById(R.id.lblFormLeadEmp);
            lblFormCompletion= (TextView) itemView.findViewById(R.id.lblFormCompletion);


            btnAction= (Button) itemView.findViewById(R.id.btnAction);
            btnAction2= (Button) itemView.findViewById(R.id.btnAction2);
            btnAction3= (Button) itemView.findViewById(R.id.btnAction3);


          // lblAction= (TextView) itemView.findViewById(R.id.lblAction);

           linearLAction= (LinearLayout) itemView.findViewById(R.id.linearLAction);
           linearLAction2= (LinearLayout) itemView.findViewById(R.id.linearLAction2);
           linearLAction3= (LinearLayout) itemView.findViewById(R.id.linearLAction3);



        }
    }
}
