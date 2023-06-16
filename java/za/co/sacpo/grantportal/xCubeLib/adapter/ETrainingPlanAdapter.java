package za.co.sacpo.grantportal.xCubeLib.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseAPC;
import za.co.sacpo.grantportal.xCubeLib.component.URLHelper;

import za.co.sacpo.grantportal.xCubeLib.dataObj.ETrainingPlanObj;
import za.co.sacpo.grantportal.xCubeLib.db.DbHelper;
import za.co.sacpo.grantportal.xCubeMentor.training.MEditTrainingPlanA;

public class ETrainingPlanAdapter  extends RecyclerView.Adapter<ETrainingPlanAdapter.AttHolder> {
    private List<ETrainingPlanObj.Item> aObjList;
    private String CAId;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    protected DbHelper dbSetaObj;
    String Labels;
    BaseAPC baseAPC = new BaseAPC() {
        @Override
        protected void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cAId) {

        }

        @Override
        protected void initializeViews() {

        }

        @Override
        protected void initializeListeners() {

        }

        @Override
        protected void initializeInputs() {

        }

        @Override
        protected void initializeLabels() {

        }

        @Override
        protected void setLayoutXml() {

        }

        @Override
        protected void verifyVersion() {

        }

        @Override
        protected void fetchVersionData() {

        }

        @Override
        protected void internetChangeBroadCast() {

        }
    };
    //Todo: Table Width Problem
    public ETrainingPlanAdapter(List<ETrainingPlanObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall) {
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
    public AttHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.existance_training_plan, parent, false);
        return new AttHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AttHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem = aObjList.get(holder.getAdapterPosition());
        int BColor;
        if (holder.getAdapterPosition() == 0) {
            BColor = res.getColor(baseAPC.getTextcolorResourceId("row_head"));

            holder.lblAction1.setVisibility(View.VISIBLE);
            holder.lblAction1.setTextColor(res.getColor(R.color.white));
            holder.btnAction1.setVisibility(View.GONE);
            holder.lblAction1.setTypeface(holder.lblAction1.getTypeface(), Typeface.BOLD);
            holder.btnAction1.setVisibility(View.GONE);
            holder.lblAction1.setTypeface(holder.lblAction1.getTypeface(), Typeface.BOLD);
            holder.lblAction1.setTextColor(res.getColor(R.color.white));

            holder.lblTitle.setBackgroundColor(BColor);
            holder.lblTrainingPlan.setBackgroundColor(BColor);
            holder.lblAction1.setBackgroundColor(BColor);

            holder.lblTitle.setTextColor(res.getColor(R.color.white));
            holder.lblTitle.setTypeface(holder.lblTitle.getTypeface(), Typeface.BOLD);

            holder.lblTrainingPlan.setTextColor(res.getColor(R.color.white));
            holder.lblTrainingPlan.setTypeface(holder.lblTrainingPlan.getTypeface(), Typeface.BOLD);

            String title = aObjList.get(holder.getAdapterPosition()).atitle3;
            holder.lblTitle.setText(title);
            holder.lblTrainingPlan.setText(aObjList.get(holder.getAdapterPosition()).aTPlan4);
            holder.lblAction1.setText(aObjList.get(holder.getAdapterPosition()).aIsEdit6);


        } else {
            if ((holder.getAdapterPosition() % 2) == 0) {
                //BColor=res.getColor(R.color.row_even);
                BColor = res.getColor(R.color.row_even);
            } else {
                BColor = res.getColor(R.color.row_odd);
            }

            holder.linearLAction1.setBackgroundColor(BColor);

            holder.lblAction1.setText(aObjList.get(holder.getAdapterPosition()).aIsEdit6);
            holder.lblAction1.setBackgroundColor(BColor);

            holder.lblTitle.setText(aObjList.get(holder.getAdapterPosition()).atitle3);
            holder.lblTitle.setBackgroundColor(BColor);

            holder.lblTrainingPlan.setText(aObjList.get(holder.getAdapterPosition()).aTPlan4);
            holder.lblTrainingPlan.setBackgroundColor(BColor);

            holder.linearLAction1.setBackgroundColor(BColor);

            if (holder.getAdapterPosition() > 0) {
                int aEdit = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).aIsEdit6);
                if (aEdit == 1) {
                    holder.lblAction1.setVisibility(View.GONE);
                    holder.btnAction1.setVisibility(View.VISIBLE);
                    holder.btnAction1.setText("EDIT");
                    holder.btnAction1.setBackground(res.getDrawable(baseAPC.getDrwabaleResourceId("themed_small_button")));
                    holder.btnAction1.setTextColor(res.getColor(baseAPC.getTextcolorResourceId("btn_textColor")));
                } else {
                    holder.lblAction1.setVisibility(View.VISIBLE);
                    holder.lblAction1.setText("-");
                    holder.btnAction1.setVisibility(View.GONE);
                }
            }

            holder.lblTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = aObjList.get(holder.getAdapterPosition()).atitle3;
                    ((BaseAPC) activityInCall).showTooltip(holder.lblTitle, sToolTip, 4);
                }
            });

            holder.lblTrainingPlan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = aObjList.get(holder.getAdapterPosition()).aTPlan4;
                    ((BaseAPC) activityInCall).showTooltip(holder.lblTrainingPlan, sToolTip, 4);
                }
            });
            int aIsFile = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).aIsFile5);
            if(aIsFile==1){
                holder.lblTrainingPlan.setTextColor(res.getColor(R.color.row_link));
                holder.lblTrainingPlan.setPaintFlags(holder.lblTrainingPlan.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                holder.lblTrainingPlan.getPaint().setUnderlineText(true);
                holder.lblTrainingPlan.setText("VIEW");
                holder.lblTrainingPlan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Context context = v.getContext();
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        String download_url = holder.hItem.aTPlan4;
                        URL url = null;
                        try {
                            url = new URL(URLHelper.DOMAIN_URL+""+download_url);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        intent.setData(Uri.parse(String.valueOf(url)));
                        context.startActivity(intent);

                    }
                });

            }
            if (holder.getAdapterPosition() > 0) {

                int aEdit = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).aIsEdit6);
                if (aEdit == 1) {
                    holder.lblAction1.setVisibility(View.GONE);
                    holder.btnAction1.setVisibility(View.VISIBLE);
                    holder.btnAction1.setText("EDIT");

                }
            }


            holder.lblTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = aObjList.get(holder.getAdapterPosition()).atitle3;
                    ((BaseAPC) activityInCall).showTooltip(holder.lblTitle, sToolTip, 4);
                }
            });

            holder.btnAction1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle inputUri = new Bundle();
                    String training_id = String.valueOf(holder.hItem.aId2);
                    String training_title = String.valueOf(holder.hItem.atitle3);
                    inputUri.putString("training_id", training_id);
                    inputUri.putString("training_title", training_title);
                    inputUri.putString("generator", "M312");
                    Context context = view.getContext();
                    Intent intent = new Intent(context, MEditTrainingPlanA.class);
                    intent.putExtras(inputUri);
                    context.startActivity(intent);
                }
            });

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
        public ETrainingPlanObj.Item hItem;
        public final TableRow tRow;
        public final TextView lblTitle , lblTrainingPlan , lblAction1 ;
        public  final Button btnAction1 ;
        public final LinearLayout linearLAction1 ;
        public AttHolder(View itemView) {
            super(itemView);
            hView = itemView;
            tRow= (TableRow) itemView.findViewById(R.id.tRow);
            btnAction1= (Button) itemView.findViewById(R.id.btnAction1);
            lblTrainingPlan= (TextView) itemView.findViewById(R.id.lblTrainingPlan);
            lblTitle= (TextView) itemView.findViewById(R.id.lblTitle);
            lblAction1 = (TextView) itemView.findViewById(R.id.lblAction1);
            linearLAction1 = itemView.findViewById(R.id.linearLAction1);
        }
    }
}


