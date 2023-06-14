package za.co.sacpo.grant.xCubeLib.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPC;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAdapter;
import za.co.sacpo.grant.xCubeLib.dataObj.MStuListObj;
import za.co.sacpo.grant.xCubeLib.db.DbHelper;
import za.co.sacpo.grant.xCubeMentor.announcement.MAnnouncement;
import za.co.sacpo.grant.xCubeMentor.attendance.MAttSummaryA;
import za.co.sacpo.grant.xCubeMentor.attendance.MCurrentAttA;
import za.co.sacpo.grant.xCubeMentor.claims.MPendingClaimsA;
import za.co.sacpo.grant.xCubeMentor.claims.MPastClaimA;
import za.co.sacpo.grant.xCubeMentor.feedback.MStudentReports;
import za.co.sacpo.grant.xCubeMentor.grant.MGrantDetailsA;
import za.co.sacpo.grant.xCubeMentor.leaves.MSLeaveListA;
import za.co.sacpo.grant.xCubeMentor.notes.MAddNote;
import za.co.sacpo.grant.xCubeMentor.notes.MNoteList;
import za.co.sacpo.grant.xCubeMentor.student.StudentA;
import za.co.sacpo.grant.xCubeMentor.training.MTrainingPlanA;
import za.co.sacpo.grant.xCubeMentor.upload.MDocumentCenterA;
import za.co.sacpo.grant.xCubeMentor.workX.MChangeSWorkXA;
import za.co.sacpo.grant.xCubeMentor.workX.MWorkXsDA;

public class MStuListAdapter extends RecyclerView.Adapter<MStuListAdapter.ViewHolder> implements BaseAdapter{
    private List<MStuListObj.Item> aObjList;
    public Context baseActivityContext;
    public AppCompatActivity activityInCall;
    protected DbHelper dbSetaObj;
    String Labels;
    private BaseAdapter bAI;
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

    public MStuListAdapter(List<MStuListObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall) {
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
    public MStuListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.m_list_dashboard_row, parent, false);
        return new MStuListAdapter.ViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(final MStuListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem=aObjList.get(holder.getAdapterPosition());
        int BColor;
        int BColorRedLink;

        Drawable BColorD;
        BColor = res.getColor(R.color.red_link_back_even);
        int showProcess1 = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).show_process_1);
        int showProcess2 = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).show_process_2);
        int showProcess3 = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).show_process_3);
        int showProcess4 = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).show_process_4);
        int showProcess5 = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).show_process_5);
        int showProcess6 = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).show_process_6);
        int showProcess7 = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).show_process_7);
        if(holder.getAdapterPosition()==0){
            BColor = res.getColor(R.color.row_head_1);

            holder.separator1.setText("#");
            holder.separator2.setText("#");
            holder.separator3.setText("#");
            holder.separator4.setText("#");
            holder.separator5.setText("#");
            holder.separator6.setText("#");
            holder.separator7.setText("#");
            holder.separator8.setText("#");

            holder.separator1.setBackgroundColor(baseActivityContext.getResources().getColor(R.color.colorPrimaryDark));
            holder.separator2.setBackgroundColor(baseActivityContext.getResources().getColor(R.color.colorPrimaryDark));
            holder.separator3.setBackgroundColor(baseActivityContext.getResources().getColor(R.color.colorPrimaryDark));
            holder.separator4.setBackgroundColor(baseActivityContext.getResources().getColor(R.color.colorPrimaryDark));
            holder.separator5.setBackgroundColor(baseActivityContext.getResources().getColor(R.color.colorPrimaryDark));
            holder.separator6.setBackgroundColor(baseActivityContext.getResources().getColor(R.color.colorPrimaryDark));
            holder.separator7.setBackgroundColor(baseActivityContext.getResources().getColor(R.color.colorPrimaryDark));
            holder.separator8.setBackgroundColor(baseActivityContext.getResources().getColor(R.color.colorPrimaryDark));

            Labels=this.getLabelFromDb("t_head_M401_LI",R.string.t_head_M401_LI);
            holder.tvC1R1C1.setText(Labels);
            holder.tvC1R1C1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                String sToolTip = getLabelFromDb("t_head_M401_LI",R.string.t_head_M401_LI);;
                ((BaseAPC) activityInCall).showTooltip(holder.tvC1R1C1, sToolTip, 4);
                }
            });

            Labels=this.getLabelFromDb("t_head_M401_ALERTS",R.string.t_head_M401_ALERTS);
            holder.tvC1R3C0.setText(Labels);
            holder.tvC1R3C0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                String sToolTip = getLabelFromDb("t_head_M401_ALERTS",R.string.t_head_M401_ALERTS);;
                ((BaseAPC) activityInCall).showTooltip(holder.tvC1R3C0, sToolTip, 4);
                }
            });

            Labels=this.getLabelFromDb("t_head_M401_NOTES",R.string.t_head_M401_NOTES);
            holder.tvC1R3C1.setText(Labels);
            holder.tvC1R3C1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = getLabelFromDb("t_head_M401_NOTES",R.string.t_head_M401_NOTES);;
                    ((BaseAPC) activityInCall).showTooltip(holder.tvC1R3C1, sToolTip, 4);
                }
            });
            Labels=this.getLabelFromDb("t_head_M401_LN",R.string.t_head_M401_LN);
            holder.tvC1R3C2.setText(Labels);
            holder.tvC1R3C2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = getLabelFromDb("t_head_M401_LN",R.string.t_head_M401_LN);;
                    ((BaseAPC) activityInCall).showTooltip(holder.tvC1R3C2, sToolTip, 4);
                }
            });

            Labels=this.getLabelFromDb("t_head_M401_LS",R.string.t_head_M401_LS);
            holder.tvC1R3C3.setText(Labels);
            holder.tvC1R3C3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = getLabelFromDb("t_head_M401_LS",R.string.t_head_M401_LS);;
                    ((BaseAPC) activityInCall).showTooltip(holder.tvC1R3C3, sToolTip, 4);
                }
            });
            Labels=this.getLabelFromDb("t_head_M401_SD",R.string.t_head_M401_SD);
            holder.tvC1R3C4.setText(Labels);
            holder.tvC1R3C4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = getLabelFromDb("t_head_M401_SD",R.string.t_head_M401_SD);;
                    ((BaseAPC) activityInCall).showTooltip(holder.tvC1R3C4, sToolTip, 4);
                }
            });
            Labels=this.getLabelFromDb("t_head_M401_ED",R.string.t_head_M401_ED);
            holder.tvC1R3C5.setText(Labels);
            holder.tvC1R3C5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = getLabelFromDb("t_head_M401_ED",R.string.t_head_M401_ED);;
                    ((BaseAPC) activityInCall).showTooltip(holder.tvC1R3C5, sToolTip, 4);
                }
            });
            Labels=this.getLabelFromDb("t_head_M401_SN",R.string.t_head_M401_SN);
            holder.tvC1R3C6.setText(Labels);
            holder.tvC1R3C6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = getLabelFromDb("t_head_M401_SN",R.string.t_head_M401_SN);;
                    ((BaseAPC) activityInCall).showTooltip(holder.tvC1R3C6, sToolTip, 4);
                }
            });
            Labels=this.getLabelFromDb("t_head_M401_MB",R.string.t_head_M401_MB);
            holder.tvC1R3C7.setText(Labels);
            holder.tvC1R3C7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = getLabelFromDb("t_head_M401_MB",R.string.t_head_M401_MB);;
                    ((BaseAPC) activityInCall).showTooltip(holder.tvC1R3C7, sToolTip, 4);
                }
            });
            Labels=this.getLabelFromDb("t_head_M401_P1",R.string.t_head_M401_P1);
            holder.tvC2R1C1.setText(Labels);
            holder.tvC2R1C1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = getLabelFromDb("t_head_M401_P1",R.string.t_head_M401_P1);;
                    ((BaseAPC) activityInCall).showTooltip(holder.tvC2R1C1, sToolTip, 4);
                }
            });
            Labels=this.getLabelFromDb("t_head_M401_ATTR",R.string.t_head_M401_ATTR);
            holder.tvC2R2C1.setText(Labels);
            holder.tvC2R2C1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = getLabelFromDb("t_head_M401_ATTR",R.string.t_head_M401_ATTR);;
                    ((BaseAPC) activityInCall).showTooltip(holder.tvC2R2C1, sToolTip, 4);
                }
            });
            Labels=this.getLabelFromDb("t_head_M401_VPATT",R.string.t_head_M401_VPATT);
            holder.tvC2R3C1.setText(Labels);
            holder.tvC2R3C1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = getLabelFromDb("t_head_M401_VPATT",R.string.t_head_M401_VPATT);;
                    ((BaseAPC) activityInCall).showTooltip(holder.tvC2R3C1, sToolTip, 4);
                }
            });
            Labels=this.getLabelFromDb("t_head_M401_EATT",R.string.t_head_M401_EATT);
            holder.tvC2R3C2.setText(Labels);


                holder.tvC2R3C2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String sToolTip = getLabelFromDb("t_head_M401_EATT", R.string.t_head_M401_EATT);
                        ;
                        ((BaseAPC) activityInCall).showTooltip(holder.tvC2R3C2, sToolTip, 4);
                    }
                });

            Labels=this.getLabelFromDb("t_head_M401_P2",R.string.t_head_M401_P2);
            holder.tvC3R1C1.setText(Labels);
            holder.tvC3R1C1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = getLabelFromDb("t_head_M401_P2",R.string.t_head_M401_P2);;
                    ((BaseAPC) activityInCall).showTooltip(holder.tvC3R1C1, sToolTip, 4);
                }
            });
            Labels=this.getLabelFromDb("t_head_M401_LA",R.string.t_head_M401_LA);
            holder.tvC3R2C1.setText(Labels);
            holder.tvC3R2C1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = getLabelFromDb("t_head_M401_LA",R.string.t_head_M401_LA);;
                    ((BaseAPC) activityInCall).showTooltip(holder.tvC3R2C1, sToolTip, 4);
                }
            });
            Labels=this.getLabelFromDb("t_head_M401_LT",R.string.t_head_M401_LT);
            holder.tvC3R3C1.setText(Labels);
            holder.tvC3R3C1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = getLabelFromDb("t_head_M401_LT",R.string.t_head_M401_LT);;
                    ((BaseAPC) activityInCall).showTooltip(holder.tvC3R3C1, sToolTip, 4);
                }
            });
            Labels=this.getLabelFromDb("t_head_M401_LPA",R.string.t_head_M401_LPA);
            holder.tvC3R3C2.setText(Labels);
            holder.tvC3R3C2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = getLabelFromDb("t_head_M401_LPA",R.string.t_head_M401_LPA);;
                    ((BaseAPC) activityInCall).showTooltip(holder.tvC3R3C2, sToolTip, 4);
                }
            });
            Labels=this.getLabelFromDb("t_head_M401_P3",R.string.t_head_M401_P3);
            holder.tvC4R1C1.setText(Labels);
            holder.tvC4R1C1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = getLabelFromDb("t_head_M401_P3",R.string.t_head_M401_P3);;
                    ((BaseAPC) activityInCall).showTooltip(holder.tvC4R1C1, sToolTip, 4);
                }
            });
            Labels=this.getLabelFromDb("t_head_M401_STIPEND",R.string.t_head_M401_STIPEND);
            holder.tvC4R2C1.setText(Labels);
            holder.tvC4R2C1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = getLabelFromDb("t_head_M401_STIPEND",R.string.t_head_M401_STIPEND);;
                    ((BaseAPC) activityInCall).showTooltip(holder.tvC4R2C1, sToolTip, 4);
                }
            });
            Labels=this.getLabelFromDb("t_head_M401_STIAPP",R.string.t_head_M401_STIAPP);
            holder.tvC4R3C1.setText(Labels);
            holder.tvC4R3C1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = getLabelFromDb("t_head_M401_STIAPP",R.string.t_head_M401_STIAPP);;
                    ((BaseAPC) activityInCall).showTooltip(holder.tvC4R3C1, sToolTip, 4);
                }
            });
            Labels=this.getLabelFromDb("t_head_M401_STIPEN",R.string.t_head_M401_STIPEN);
            holder.tvC4R3C2.setText(Labels);
            holder.tvC4R3C2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = getLabelFromDb("t_head_M401_STIPEN",R.string.t_head_M401_STIPEN);;
                    ((BaseAPC) activityInCall).showTooltip(holder.tvC4R3C2, sToolTip, 4);
                }
            });
            Labels=this.getLabelFromDb("t_head_M401_P4",R.string.t_head_M401_P4);
            holder.tvC5R1C1.setText(Labels);
            holder.tvC5R1C1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = getLabelFromDb("t_head_M401_P4",R.string.t_head_M401_P4);;
                    ((BaseAPC) activityInCall).showTooltip(holder.tvC5R1C1, sToolTip, 4);
                }
            });
            Labels=this.getLabelFromDb("t_head_M401_GPS",R.string.t_head_M401_GPS);
            holder.tvC5R2C1.setText(Labels);
            holder.tvC5R2C1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = getLabelFromDb("t_head_M401_GPS",R.string.t_head_M401_GPS);;
                    ((BaseAPC) activityInCall).showTooltip(holder.tvC5R2C1, sToolTip, 4);
                }
            });
            Labels=this.getLabelFromDb("t_head_M401_WORKX",R.string.t_head_M401_WORKX);
            holder.tvC5R3C1.setText(Labels);
            holder.tvC5R3C1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = getLabelFromDb("t_head_M401_WORKX",R.string.t_head_M401_WORKX);;
                    ((BaseAPC) activityInCall).showTooltip(holder.tvC5R3C1, sToolTip, 4);
                }
            });
            Labels=this.getLabelFromDb("t_head_M401_ASSWORKX",R.string.t_head_M401_ASSWORKX);
            holder.tvC5R3C2.setText(Labels);
            holder.tvC5R3C2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = getLabelFromDb("t_head_M401_ASSWORKX",R.string.t_head_M401_ASSWORKX);;
                    ((BaseAPC) activityInCall).showTooltip(holder.tvC5R3C2, sToolTip, 4);
                }
            });
            Labels=this.getLabelFromDb("t_head_M401_P5",R.string.t_head_M401_P5);
            holder.tvC6R1C1.setText(Labels);
            holder.tvC6R1C1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = getLabelFromDb("t_head_M401_P5",R.string.t_head_M401_P5);;
                    ((BaseAPC) activityInCall).showTooltip(holder.tvC6R1C1, sToolTip, 4);
                }
            });
            Labels=this.getLabelFromDb("t_head_M401_VREPORTS",R.string.t_head_M401_VREPORTS);
            holder.tvC6R2C1.setText(Labels);
            holder.tvC6R2C1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = getLabelFromDb("t_head_M401_VREPORTS",R.string.t_head_M401_VREPORTS);;
                    ((BaseAPC) activityInCall).showTooltip(holder.tvC6R2C1, sToolTip, 4);
                }
            });
            Labels=this.getLabelFromDb("t_head_M401_REPORTSCOMP",R.string.t_head_M401_REPORTSCOMP);
            holder.tvC6R3C1.setText(Labels);
            holder.tvC6R3C1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = getLabelFromDb("t_head_M401_REPORTSCOMP",R.string.t_head_M401_REPORTSCOMP);;
                    ((BaseAPC) activityInCall).showTooltip(holder.tvC6R3C1, sToolTip, 4);
                }
            });
            Labels=this.getLabelFromDb("t_head_M401_REPORTSPEND",R.string.t_head_M401_REPORTSPEND);
            holder.tvC6R3C2.setText(Labels);
            holder.tvC6R3C2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = getLabelFromDb("t_head_M401_REPORTSPEND",R.string.t_head_M401_REPORTSPEND);;
                    ((BaseAPC) activityInCall).showTooltip(holder.tvC6R3C2, sToolTip, 4);
                }
            });
            Labels=this.getLabelFromDb("t_head_M401_P6",R.string.t_head_M401_P6);
            holder.tvC7R1C1.setText(Labels);
            holder.tvC7R1C1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = getLabelFromDb("t_head_M401_P6",R.string.t_head_M401_P6);;
                    ((BaseAPC) activityInCall).showTooltip(holder.tvC7R1C1, sToolTip, 4);
                }
            });
            Labels=this.getLabelFromDb("t_head_M401_TP",R.string.t_head_M401_TP);
            holder.tvC7R3C1.setText(Labels);
            holder.tvC7R3C1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = getLabelFromDb("t_head_M401_TP",R.string.t_head_M401_TP);;
                    ((BaseAPC) activityInCall).showTooltip(holder.tvC7R3C1, sToolTip, 4);
                }
            });
            Labels=this.getLabelFromDb("t_head_M401_P7",R.string.t_head_M401_P7);
            holder.tvC8R1C1.setText(Labels);
            holder.tvC8R1C1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = getLabelFromDb("t_head_M401_P7",R.string.t_head_M401_P7);;
                    ((BaseAPC) activityInCall).showTooltip(holder.tvC8R1C1, sToolTip, 4);
                }
            });
            Labels=this.getLabelFromDb("t_head_M401_UD",R.string.t_head_M401_UD);
            holder.tvC8R3C1.setText(Labels);
            holder.tvC8R3C1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = getLabelFromDb("t_head_M401_UD",R.string.t_head_M401_UD);;
                    ((BaseAPC) activityInCall).showTooltip(holder.tvC8R3C1, sToolTip, 4);
                }
            });


            holder.tvC1R3C0.setTypeface(holder.tvC1R3C0.getTypeface(), Typeface.BOLD);
            holder.tvC1R3C1.setTypeface(holder.tvC1R3C1.getTypeface(), Typeface.BOLD);
            holder.tvC1R3C2.setTypeface(holder.tvC1R3C2.getTypeface(), Typeface.BOLD);
            holder.tvC1R3C3.setTypeface(holder.tvC1R3C3.getTypeface(), Typeface.BOLD);
            holder.tvC1R3C4.setTypeface(holder.tvC1R3C4.getTypeface(), Typeface.BOLD);
            holder.tvC1R3C5.setTypeface(holder.tvC1R3C5.getTypeface(), Typeface.BOLD);
            holder.tvC1R3C6.setTypeface(holder.tvC1R3C6.getTypeface(), Typeface.BOLD);
            holder.tvC1R3C7.setTypeface(holder.tvC1R3C7.getTypeface(), Typeface.BOLD);
            holder.tvC2R3C1.setTypeface(holder.tvC2R3C1.getTypeface(), Typeface.BOLD);
            holder.tvC2R3C2.setTypeface(holder.tvC2R3C2.getTypeface(), Typeface.BOLD);
            holder.tvC3R3C1.setTypeface(holder.tvC3R3C1.getTypeface(), Typeface.BOLD);
            holder.tvC3R3C2.setTypeface(holder.tvC3R3C2.getTypeface(), Typeface.BOLD);
            holder.tvC4R3C1.setTypeface(holder.tvC4R3C1.getTypeface(), Typeface.BOLD);
            holder.tvC4R3C2.setTypeface(holder.tvC4R3C2.getTypeface(), Typeface.BOLD);
            holder.tvC5R3C1.setTypeface(holder.tvC5R3C1.getTypeface(), Typeface.BOLD);
            holder.tvC5R3C2.setTypeface(holder.tvC5R3C2.getTypeface(), Typeface.BOLD);
            holder.tvC6R3C1.setTypeface(holder.tvC6R3C1.getTypeface(), Typeface.BOLD);
            holder.tvC6R3C2.setTypeface(holder.tvC6R3C2.getTypeface(), Typeface.BOLD);
            holder.tvC7R3C1.setTypeface(holder.tvC7R3C1.getTypeface(), Typeface.BOLD);
            holder.tvC8R3C1.setTypeface(holder.tvC8R3C1.getTypeface(), Typeface.BOLD);






        }
        else {
            int BWhite = res.getColor(R.color.white);
            if ((holder.getAdapterPosition() % 2) == 0) {
                BColor = res.getColor(R.color.row_even);
                BColorRedLink = res.getColor(R.color.red_link_back_even);

            } else {
                BColor = res.getColor(R.color.row_odd);
                BColorRedLink = res.getColor(R.color.red_link_back_odd);
            }
            String posNP = String.valueOf(holder.getAdapterPosition());
            //String pos = "00"+holder.getAdapterPosition();
            String pos = String.format("%1$3s", posNP).replace(' ', '0');
            int ProcessDate1 = res.getColor(R.color.process_1_color_data);
            int ProcessDate2 = res.getColor(R.color.process_2_color_data);
            int ProcessDate3 = res.getColor(R.color.process_3_color_data);


           /* Log.i("color",R.color.process_1_color_data+"---"+pos);
            holder.LLC1.setBackgroundColor(ProcessDate1);
            holder.LLC2.setBackgroundColor(ProcessDate1);
            holder.LLC3.setBackgroundColor(ProcessDate2);
            holder.LLC4.setBackgroundColor(ProcessDate3);
            holder.LLC5.setBackgroundColor(ProcessDate1);
            holder.LLC6.setBackgroundColor(ProcessDate2);
            holder.LLC7.setBackgroundColor(ProcessDate1);
            holder.LLC8.setBackgroundColor(ProcessDate3);*/


            holder.separator1.setText(pos);
            holder.separator2.setText(pos);
            holder.separator3.setText(pos);
            holder.separator4.setText(pos);
            holder.separator5.setText(pos);
            holder.separator6.setText(pos);
            holder.separator7.setText(pos);
            holder.separator8.setText(pos);

            holder.separator1.setBackgroundColor(baseActivityContext.getResources().getColor(R.color.colorPrimaryDark));
            holder.separator2.setBackgroundColor(baseActivityContext.getResources().getColor(R.color.colorPrimaryDark));
            holder.separator3.setBackgroundColor(baseActivityContext.getResources().getColor(R.color.colorPrimaryDark));
            holder.separator4.setBackgroundColor(baseActivityContext.getResources().getColor(R.color.colorPrimaryDark));
            holder.separator5.setBackgroundColor(baseActivityContext.getResources().getColor(R.color.colorPrimaryDark));
            holder.separator6.setBackgroundColor(baseActivityContext.getResources().getColor(R.color.colorPrimaryDark));
            holder.separator7.setBackgroundColor(baseActivityContext.getResources().getColor(R.color.colorPrimaryDark));
            holder.separator8.setBackgroundColor(baseActivityContext.getResources().getColor(R.color.colorPrimaryDark));


            holder.tvC1R3C0.setTextColor(res.getColor(R.color.row_link));
            holder.tvC1R3C0.setPaintFlags(holder.tvC1R3C0.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            holder.tvC1R3C0.getPaint().setUnderlineText(true);

            holder.tvC1R3C1.setTextColor(res.getColor(R.color.row_link));
            holder.tvC1R3C1.setPaintFlags(holder.tvC1R3C1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            holder.tvC1R3C1.getPaint().setUnderlineText(true);

            holder.tvC1R3C2.setTextColor(res.getColor(R.color.row_link));
            holder.tvC1R3C2.setPaintFlags(holder.tvC1R3C2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            holder.tvC1R3C2.getPaint().setUnderlineText(true);

/*            holder.tvC1R3C3.setTextColor(res.getColor(R.color.row_link));
            holder.tvC1R3C4.setTextColor(res.getColor(R.color.row_link));
            holder.tvC1R3C5.setTextColor(res.getColor(R.color.row_link));
            holder.tvC1R3C6.setTextColor(res.getColor(R.color.row_link));*/
            holder.tvC1R3C7.setTextColor(res.getColor(R.color.row_link));
            holder.tvC1R3C7.setPaintFlags(holder.tvC1R3C7.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            holder.tvC1R3C7.getPaint().setUnderlineText(true);
            holder.tvC2R3C1.setTextColor(res.getColor(R.color.row_link));
            holder.tvC2R3C1.setPaintFlags(holder.tvC2R3C1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            holder.tvC2R3C1.getPaint().setUnderlineText(true);
            if (showProcess1 == 1) {
                holder.tvC2R3C2.setTextColor(res.getColor(R.color.row_link));
                holder.tvC2R3C2.setPaintFlags(holder.tvC2R3C2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                holder.tvC2R3C2.getPaint().setUnderlineText(true);
            }
            if (showProcess2 == 1) {
                holder.tvC3R3C1.setTextColor(res.getColor(R.color.row_link));
                holder.tvC3R3C1.setPaintFlags(holder.tvC3R3C1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                holder.tvC3R3C1.getPaint().setUnderlineText(true);
                holder.tvC3R3C2.setTextColor(res.getColor(R.color.row_link));
                holder.tvC3R3C2.setPaintFlags(holder.tvC3R3C2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                holder.tvC3R3C2.getPaint().setUnderlineText(true);
            }
            holder.tvC4R3C1.setTextColor(res.getColor(R.color.row_link));
            holder.tvC4R3C1.setPaintFlags(holder.tvC4R3C1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            holder.tvC4R3C1.getPaint().setUnderlineText(true);
            holder.tvC4R3C2.setTextColor(res.getColor(R.color.row_link));
            holder.tvC4R3C2.setPaintFlags(holder.tvC4R3C2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            holder.tvC4R3C2.getPaint().setUnderlineText(true);
            if (showProcess4 == 1) {
                holder.tvC5R3C1.setTextColor(res.getColor(R.color.row_link));
                holder.tvC5R3C1.setPaintFlags(holder.tvC5R3C1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                holder.tvC5R3C1.getPaint().setUnderlineText(true);
                holder.tvC5R3C2.setTextColor(res.getColor(R.color.row_link));
                holder.tvC5R3C2.setPaintFlags(holder.tvC5R3C2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                holder.tvC5R3C2.getPaint().setUnderlineText(true);
            }
            if (showProcess5 == 1) {
                holder.tvC6R3C1.setTextColor(res.getColor(R.color.row_link));
                holder.tvC6R3C1.setPaintFlags(holder.tvC6R3C1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                holder.tvC6R3C1.getPaint().setUnderlineText(true);
                holder.tvC6R3C2.setTextColor(res.getColor(R.color.row_link));
                holder.tvC6R3C2.setPaintFlags(holder.tvC6R3C2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                holder.tvC6R3C2.getPaint().setUnderlineText(true);
            }
            if (showProcess6 == 1) {
                holder.tvC7R3C1.setTextColor(res.getColor(R.color.row_link));
                holder.tvC7R3C1.setPaintFlags(holder.tvC7R3C1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                holder.tvC7R3C1.getPaint().setUnderlineText(true);
            }
            if (showProcess7 == 1) {
                holder.tvC8R3C1.setTextColor(res.getColor(R.color.row_link));
                holder.tvC8R3C1.setPaintFlags(holder.tvC8R3C1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                holder.tvC8R3C1.getPaint().setUnderlineText(true);
            }
            holder.tvC1R3C0.setBackgroundColor(BColor);
            holder.tvC1R3C1.setBackgroundColor(BColor);
            holder.tvC1R3C2.setBackgroundColor(BColor);
            holder.tvC1R3C3.setBackgroundColor(BColor);
            holder.tvC1R3C4.setBackgroundColor(BColor);
            holder.tvC1R3C5.setBackgroundColor(BColor);
            holder.tvC1R3C6.setBackgroundColor(BColor);
            holder.tvC1R3C7.setBackgroundColor(BColor);


            holder.tvC2R3C1.setBackgroundColor(ProcessDate1);
            int pendingA = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).patt9);
            if (showProcess1 == 1) {
                if (pendingA > 0) {
                    holder.tvC2R3C2.setBackgroundResource(R.color.red_link);
                    holder.tvC2R3C2.setTextColor(BWhite);
                } else {
                    holder.tvC2R3C2.setBackgroundColor(ProcessDate1);
                }
            } else {
                holder.tvC2R3C2.setBackgroundColor(ProcessDate1);
            }
            holder.tvC3R3C1.setBackgroundColor(ProcessDate2);

            if (showProcess2 == 1) {
                int leaveA = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).lpending12);
                if (leaveA > 0) {
                    holder.tvC3R3C2.setBackgroundResource(R.color.red_link);
                    holder.tvC3R3C2.setTextColor(BWhite);
                } else {
                    holder.tvC3R3C2.setBackgroundColor(ProcessDate2);///
                }
            } else {
                holder.tvC3R3C2.setBackgroundColor(ProcessDate2);
            }
            holder.tvC4R3C1.setBackgroundColor(ProcessDate3);

            int stipendPA = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).stpending14);
            if (stipendPA > 0) {
                holder.tvC4R3C2.setBackgroundResource(R.color.red_link);
                holder.tvC4R3C2.setTextColor(BWhite);
            } else {
                holder.tvC4R3C2.setBackgroundColor(ProcessDate3);///
            }


            holder.tvC5R3C1.setBackgroundColor(ProcessDate1);////
            holder.tvC5R3C2.setBackgroundColor(ProcessDate1);////
            int rworkx = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).workx15);
            if (showProcess4 == 1) {
                if (rworkx == 0) {
                    holder.tvC5R3C1.setBackgroundResource(R.color.red_link);
                    holder.tvC5R3C1.setTextColor(BWhite);
                } else {
                    holder.tvC5R3C1.setBackgroundColor(ProcessDate1);////
                }
                int rworkxassP = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).isWorkXassingPending26);
                if (rworkxassP == 0) {
                    holder.tvC5R3C2.setBackgroundResource(R.color.red_link);
                    holder.tvC5R3C2.setTextColor(BWhite);
                    holder.tvC5R3C2.setText("PENDING");
                } else {
                    holder.tvC5R3C1.setBackgroundColor(ProcessDate1);////
                    holder.tvC5R3C2.setText(aObjList.get(holder.getAdapterPosition()).workasl16);
                }
            } else {
                holder.tvC5R3C1.setBackgroundColor(ProcessDate1);////
                holder.tvC5R3C1.setBackgroundColor(ProcessDate1);////
                holder.tvC5R3C1.setText(" - ");
                holder.tvC5R3C2.setText(" - ");
            }

            holder.tvC6R3C1.setBackgroundColor(ProcessDate3);
            holder.tvC6R3C2.setBackgroundColor(ProcessDate3);
            if (showProcess5 == 1) {
                int rPending = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).rpending19);
                if (rPending > 0) {
                    holder.tvC6R3C2.setBackgroundResource(R.color.red_link);
                    holder.tvC6R3C2.setTextColor(BWhite);
                } else {
                    holder.tvC6R3C2.setBackgroundColor(ProcessDate3);////
                }
            }

            if (showProcess6 == 1) {
                int programP = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).isTpPending28);
                if (programP == 0) {
                    holder.tvC7R3C1.setBackgroundResource(R.color.red_link);
                    holder.tvC7R3C1.setTextColor(BWhite);
                    holder.tvC7R3C1.setText(aObjList.get(holder.getAdapterPosition()).tp20);
                } else {
                    holder.tvC7R3C1.setBackgroundColor(ProcessDate1);//
                    holder.tvC7R3C1.setText("DOWNLOAD");
                }
            } else {
                holder.tvC7R3C1.setText(" - ");
                holder.tvC7R3C1.setBackgroundColor(ProcessDate1);
            }

            holder.tvC8R3C1.setBackgroundColor(ProcessDate2);


            holder.tvC1R3C0.setText(aObjList.get(holder.getAdapterPosition()).alertcount23);
            holder.tvC1R3C1.setText(aObjList.get(holder.getAdapterPosition()).notes3);
            holder.tvC1R3C2.setText(aObjList.get(holder.getAdapterPosition()).lname4);

            holder.tvC1R3C3.setText(aObjList.get(holder.getAdapterPosition()).lstatus5);
            holder.tvC1R3C4.setText(aObjList.get(holder.getAdapterPosition()).sdate6);
            holder.tvC1R3C5.setText(aObjList.get(holder.getAdapterPosition()).edate6);
            holder.tvC1R3C6.setText(aObjList.get(holder.getAdapterPosition()).seta7);
            holder.tvC1R3C7.setText(aObjList.get(holder.getAdapterPosition()).le8);
            ///tvC2R3C1
            if (showProcess7 == 1) {
                holder.tvC8R3C1.setText("VIEW");
            } else {
                holder.tvC8R3C1.setText(" - ");
            }

            holder.tvC2R3C1.setText(aObjList.get(holder.getAdapterPosition()).patt9 + " Days");
            if (showProcess1 == 1) {
                holder.tvC2R3C2.setText(aObjList.get(holder.getAdapterPosition()).catt10 + " Days");
            } else {
                holder.tvC2R3C2.setText(" - ");
            }
            if (showProcess2 == 1) {
                holder.tvC3R3C1.setText(aObjList.get(holder.getAdapterPosition()).leave11);
                holder.tvC3R3C2.setText(aObjList.get(holder.getAdapterPosition()).lpending12);
            } else {
                holder.tvC3R3C1.setText(" - ");
                holder.tvC3R3C2.setText(" - ");
            }
            holder.tvC4R3C1.setText(aObjList.get(holder.getAdapterPosition()).pstipend13);
            holder.tvC4R3C2.setText(aObjList.get(holder.getAdapterPosition()).stpending14);
            if (showProcess4 == 1) {
                holder.tvC5R3C1.setText(aObjList.get(holder.getAdapterPosition()).workx15);
            } else {
                holder.tvC5R3C1.setText(" - ");
            }
            if (showProcess5 == 1) {
                holder.tvC6R3C1.setText(aObjList.get(holder.getAdapterPosition()).reports18);
                holder.tvC6R3C2.setText(aObjList.get(holder.getAdapterPosition()).rpending19);
            } else {
                holder.tvC6R3C1.setText(" - ");
                holder.tvC6R3C2.setText(" - ");
            }
            //holder.tvC8R3C1.setText(aObjList.get(holder.getAdapterPosition()).doc21);


            holder.tvC1R1C1.setVisibility(View.GONE);
            holder.tvC1R2C1.setVisibility(View.GONE);
            holder.tvC2R1C1.setVisibility(View.GONE);
            holder.tvC2R2C1.setVisibility(View.GONE);
            holder.tvC3R1C1.setVisibility(View.GONE);
            holder.tvC3R2C1.setVisibility(View.GONE);
            holder.tvC4R1C1.setVisibility(View.GONE);
            holder.tvC4R2C1.setVisibility(View.GONE);
            holder.tvC5R1C1.setVisibility(View.GONE);
            holder.tvC5R2C1.setVisibility(View.GONE);
            holder.tvC6R1C1.setVisibility(View.GONE);
            holder.tvC6R2C1.setVisibility(View.GONE);
            holder.tvC7R1C1.setVisibility(View.GONE);
            holder.tvC7R2C1.setVisibility(View.GONE);
            holder.tvC8R1C1.setVisibility(View.GONE);
            holder.tvC8R2C1.setVisibility(View.GONE);

            holder.tvC1R3C0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle inputUri = new Bundle();
                    String user_id = String.valueOf(holder.hItem.aId2);
                    inputUri.putString("student_id", user_id);
                    inputUri.putString("student_name", aObjList.get(position).lname4);
                    inputUri.putString("generator", "M401");
                    //Log.i("student"+user_id,aObjList.get(position).lname4);
                    Context context = view.getContext();
                    Intent intent = new Intent(context, MAnnouncement.class);
                    intent.putExtras(inputUri);
                    context.startActivity(intent);
                }
            });

            holder.tvC1R3C1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle inputUri = new Bundle();
                    String user_id = String.valueOf(holder.hItem.aId2);
                    inputUri.putString("user_id", user_id);

                    String grant_id = String.valueOf(holder.hItem.grantid22);
                    inputUri.putString("grant_id", grant_id);

                    inputUri.putString("student_name", aObjList.get(position).lname4);
                    inputUri.putString("generator", "M401");


                    Context context = view.getContext();
                    Intent intent = new Intent(context, MNoteList.class);
                    intent.putExtras(inputUri);
                    context.startActivity(intent);
                }
            });

            holder.tvC1R3C2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle inputUri = new Bundle();
                    String student_id = String.valueOf(holder.hItem.aId2);
                    inputUri.putString("student_id", student_id);
                    String student_name = String.valueOf(holder.hItem.lname4);
                    inputUri.putString("student_name", student_name);
                    inputUri.putString("generator", "M401");
                    Context context = view.getContext();
                    Intent intent = new Intent(context, StudentA.class);
                    intent.putExtras(inputUri);
                    context.startActivity(intent);
                }
            });
            holder.tvC1R3C7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle inputUri = new Bundle();
                    String student_id = String.valueOf(holder.hItem.aId2);
                    inputUri.putString("student_id", student_id);

                    String g_d_id = String.valueOf(holder.hItem.grantid22);
                    inputUri.putString("g_d_id", g_d_id);
                    inputUri.putString("generator", "M401");
                    Context context = v.getContext();
                    Intent intent = new Intent(context, MGrantDetailsA.class);
                    intent.putExtras(inputUri);
                    context.startActivity(intent);
                }
            });
            holder.tvC2R3C1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle inputUri = new Bundle();

                    String student_id = String.valueOf(holder.hItem.aId2);
                    inputUri.putString("student_id", student_id);
                    String m_student_name = String.valueOf(holder.hItem.lname4);
                    inputUri.putString("m_student_name", m_student_name);

                    inputUri.putString("generator", "M401");
                    Context context = view.getContext();
                    Intent intent = new Intent(context, MAttSummaryA.class);
                    intent.putExtras(inputUri);
                    context.startActivity(intent);
                }
            });
            if (showProcess1 == 1) {
                holder.tvC2R3C2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle inputUri = new Bundle();

                        String student_id = String.valueOf(holder.hItem.aId2);
                        inputUri.putString("student_id", student_id);
                        String m_student_name = String.valueOf(holder.hItem.lname4);
                        inputUri.putString("m_student_name", m_student_name);

                        String start_date = String.valueOf(holder.hItem.sdate6);
                        inputUri.putString("start_date", start_date);
                        String end_date = String.valueOf(holder.hItem.edate6);
                        inputUri.putString("end_date", end_date);

                        inputUri.putString("generator", "M401");
                        Context context = view.getContext();
                        Intent intent = new Intent(context, MCurrentAttA.class);
                        intent.putExtras(inputUri);
                        context.startActivity(intent);
                    }
                });
            }
            holder.tvC3R3C2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle inputUri = new Bundle();
                    String attendance_id = String.valueOf(holder.hItem.aId2);
                    String m_student_name = String.valueOf(holder.hItem.lname4);
                    inputUri.putString("attendance_id", attendance_id);
                    String student_id = String.valueOf(holder.hItem.aId2);
                    inputUri.putString("student_id", student_id);
                    inputUri.putString("m_student_name", m_student_name);
                    inputUri.putString("generator", "M401");
                    Context context = view.getContext();
                    Intent intent = new Intent(context, MSLeaveListA.class);
                    intent.putExtras(inputUri);
                    context.startActivity(intent);
                }
            });
            holder.tvC3R3C1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle inputUri = new Bundle();
                    String attendance_id = String.valueOf(holder.hItem.aId2);
                    String m_student_name = String.valueOf(holder.hItem.lname4);
                    inputUri.putString("attendance_id", attendance_id);
                    String student_id = String.valueOf(holder.hItem.aId2);
                    inputUri.putString("student_id", student_id);
                    inputUri.putString("m_student_name", m_student_name);
                    inputUri.putString("generator", "M401");
                    Context context = view.getContext();
                    Intent intent = new Intent(context, MSLeaveListA.class);
                    intent.putExtras(inputUri);
                    context.startActivity(intent);
                }
            });

            holder.tvC4R3C1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle inputUri = new Bundle();

                    String student_id = String.valueOf(holder.hItem.aId2);
                    inputUri.putString("student_id", student_id);
                    String m_student_name = String.valueOf(holder.hItem.lname4);
                    inputUri.putString("m_student_name", m_student_name);

                    inputUri.putString("generator", "M401");
                    Context context = view.getContext();
                    Intent intent = new Intent(context, MPastClaimA.class);
                    intent.putExtras(inputUri);
                    context.startActivity(intent);
                }
            });
            holder.tvC4R3C2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle inputUri = new Bundle();

                    String student_id = String.valueOf(holder.hItem.aId2);
                    inputUri.putString("student_id", student_id);
                    String m_student_name = String.valueOf(holder.hItem.lname4);
                    inputUri.putString("m_student_name", m_student_name);

                    inputUri.putString("generator", "M401");
                    Context context = view.getContext();
                    Intent intent = new Intent(context, MPendingClaimsA.class);
                    intent.putExtras(inputUri);
                    context.startActivity(intent);
                }
            });
            if (showProcess5 == 1) {
                holder.tvC6R3C1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle inputUri = new Bundle();

                        String student_id = String.valueOf(holder.hItem.aId2);
                        inputUri.putString("student_id", student_id);
                        String m_student_name = String.valueOf(holder.hItem.lname4);
                        inputUri.putString("student_name", m_student_name);

                        inputUri.putString("generator", "M401");
                        Context context = view.getContext();
                        Intent intent = new Intent(context, MStudentReports.class);
                        intent.putExtras(inputUri);
                        context.startActivity(intent);
                    }
                });
                holder.tvC6R3C2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle inputUri = new Bundle();

                        String student_id = String.valueOf(holder.hItem.aId2);
                        inputUri.putString("student_id", student_id);
                        String m_student_name = String.valueOf(holder.hItem.lname4);
                        inputUri.putString("student_name", m_student_name);

                        inputUri.putString("generator", "M401");
                        Context context = view.getContext();
                        Intent intent = new Intent(context, MStudentReports.class);
                        intent.putExtras(inputUri);
                        context.startActivity(intent);
                    }
                });
            }
            if (showProcess4 == 1) {
                holder.tvC5R3C1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle inputUri = new Bundle();

                        String student_id = String.valueOf(holder.hItem.aId2);
                        inputUri.putString("student_id", student_id);
                        String m_student_name = String.valueOf(holder.hItem.lname4);
                        inputUri.putString("m_student_name", m_student_name);

                        inputUri.putString("generator", "M401");
                        Context context = view.getContext();
                        Intent intent = new Intent(context, MWorkXsDA.class);
                        intent.putExtras(inputUri);
                        context.startActivity(intent);
                    }
                });
                holder.tvC5R3C2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle inputUri = new Bundle();

                        String student_id = String.valueOf(holder.hItem.aId2);
                        inputUri.putString("student_id", student_id);
                        String m_student_name = String.valueOf(holder.hItem.lname4);
                        inputUri.putString("mwX_student_name4", m_student_name);

                        inputUri.putString("generator", "M401");
                        Context context = view.getContext();
                        Intent intent = new Intent(context, MChangeSWorkXA.class);
                        intent.putExtras(inputUri);
                        context.startActivity(intent);
                    }
                });
            }
            if (showProcess6 == 1){
                holder.tvC7R3C1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle inputUri = new Bundle();
                        String student_id = String.valueOf(holder.hItem.aId2);
                        inputUri.putString("student_id", student_id);
                        String m_student_name = String.valueOf(holder.hItem.lname4);
                        inputUri.putString("mwX_student_name4", m_student_name);
                        inputUri.putString("generator", "M401");
                        Context context = view.getContext();
                        Intent intent = new Intent(context, MTrainingPlanA.class);
                        intent.putExtras(inputUri);
                        context.startActivity(intent);
                        /*TODO::ON HOLD */
                    }
                });
        }
        if(showProcess7==1) {
            holder.tvC8R3C1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle inputUri = new Bundle();

                    String student_id = String.valueOf(holder.hItem.aId2);
                    inputUri.putString("student_id", student_id);
                    String m_student_name = String.valueOf(holder.hItem.lname4);
                    inputUri.putString("m_student_name", m_student_name);

                    inputUri.putString("generator", "M401");
                    Context context = view.getContext();
                    Intent intent = new Intent(context, MDocumentCenterA.class);
                    intent.putExtras(inputUri);
                    context.startActivity(intent);
                    /*TODO::ON HOLD */
                }
            });
        }



        }



    }
    private void popup (final View view, final int userId) {

        PopupMenu popup = new PopupMenu(baseActivityContext, view);
        popup.getMenuInflater().inflate(R.menu.note_menu,popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent;
                String user_id;
                Context context;
                int itemId = menuItem.getItemId();
                if (itemId == R.id.addNote) {
                    Bundle inputUri2 = new Bundle();
                    user_id = String.valueOf(userId);
                    inputUri2.putString("user_id", user_id);
                    inputUri2.putString("date_input", "");
                    inputUri2.putString("generator", "M401");
                    context = view.getContext();
                    intent = new Intent(context, MAddNote.class);
                    intent.putExtras(inputUri2);
                    context.startActivity(intent);
                } else if (itemId == R.id.notList) {
                    Bundle inputUri = new Bundle();
                    user_id = String.valueOf(userId);
                    inputUri.putString("user_id", user_id);
                    inputUri.putString("date_input", "");
                    inputUri.putString("generator", "M401");
                    context = view.getContext();
                    intent = new Intent(context, MNoteList.class);
                    intent.putExtras(inputUri);
                    context.startActivity(intent);
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View hView;
        public MStuListObj.Item hItem;
        public final TableRow tRow;
        public final LinearLayout LLC1,LLC2,LLC3,LLC4,LLC5,LLC6,LLC7,LLC8;
        public final  TextView tvC1R3C0, tvC1R3C1,tvC1R3C2,tvC1R3C3,tvC1R3C4,tvC1R3C5,tvC1R3C6,tvC1R3C7;
        public final  TextView tvC2R3C1,tvC2R3C2,tvC3R3C1,tvC3R3C2,tvC4R3C1,tvC4R3C2,tvC5R3C1,tvC5R3C2,tvC6R3C1,tvC6R3C2,tvC7R3C1,tvC8R3C1;
        public final TextView separator1,separator2,separator3,separator4,separator5,separator6,separator7,separator8;
        public final TextView tvC1R1C1,tvC1R2C1,tvC2R1C1,tvC2R2C1,tvC3R1C1,tvC3R2C1,tvC4R1C1,tvC4R2C1,tvC5R1C1,tvC5R2C1,tvC6R1C1,tvC6R2C1,tvC7R1C1,tvC7R2C1,tvC8R1C1,tvC8R2C1;
        public ViewHolder(View itemView) {
            super(itemView);
            hView = itemView;
            tRow= (TableRow) itemView.findViewById(R.id.student_list_Row);
            separator1 = itemView.findViewById(R.id.separator1);
            separator2 = itemView.findViewById(R.id.separator2);
            separator3 = itemView.findViewById(R.id.separator3);
            separator4 = itemView.findViewById(R.id.separator4);
            separator5 = itemView.findViewById(R.id.separator5);
            separator6 = itemView.findViewById(R.id.separator6);
            separator7 = itemView.findViewById(R.id.separator7);
            separator8 = itemView.findViewById(R.id.separator8);

            LLC1 = itemView.findViewById(R.id.LLC2R3C1);
            LLC2 = itemView.findViewById(R.id.LLC2R3C1);
            LLC3 = itemView.findViewById(R.id.LLC3R3C1);
            LLC4 = itemView.findViewById(R.id.LLC4R3C1);
            LLC5 = itemView.findViewById(R.id.LLC5R3C1);
            LLC6 = itemView.findViewById(R.id.LLC6R3C1);
            LLC7 = itemView.findViewById(R.id.LLC7R3C1);
            LLC8 = itemView.findViewById(R.id.LLC8R3C1);

            tvC1R3C0 = itemView.findViewById(R.id.tvC1R3C0);
            tvC1R3C1 = itemView.findViewById(R.id.tvC1R3C1);
            tvC1R3C2 = itemView.findViewById(R.id.tvC1R3C2);
            tvC1R3C3 = itemView.findViewById(R.id.tvC1R3C3);
            tvC1R3C4 = itemView.findViewById(R.id.tvC1R3C4);
            tvC1R3C5 = itemView.findViewById(R.id.tvC1R3C5);
            tvC1R3C6 = itemView.findViewById(R.id.tvC1R3C6);
            tvC1R3C7 = itemView.findViewById(R.id.tvC1R3C7);
            tvC2R3C1 = itemView.findViewById(R.id.tvC2R3C1);
            tvC2R3C2 = itemView.findViewById(R.id.tvC2R3C2);
            tvC3R3C1 = itemView.findViewById(R.id.tvC3R3C1);
            tvC3R3C2 = itemView.findViewById(R.id.tvC3R3C2);
            tvC4R3C1 = itemView.findViewById(R.id.tvC4R3C1);
            tvC4R3C2 = itemView.findViewById(R.id.tvC4R3C2);
            tvC5R3C1 = itemView.findViewById(R.id.tvC5R3C1);
            tvC5R3C2 = itemView.findViewById(R.id.tvC5R3C2);
            tvC6R3C1 = itemView.findViewById(R.id.tvC6R3C1);
            tvC6R3C2 = itemView.findViewById(R.id.tvC6R3C2);
            tvC7R3C1 = itemView.findViewById(R.id.tvC7R3C1);
            tvC8R3C1 = itemView.findViewById(R.id.tvC8R3C1);


            tvC1R1C1 = itemView.findViewById(R.id.tvC1R1C1);
            tvC1R2C1 = itemView.findViewById(R.id.tvC1R2C1);
            tvC2R1C1 = itemView.findViewById(R.id.tvC2R1C1);
            tvC2R2C1 = itemView.findViewById(R.id.tvC2R2C1);
            tvC3R1C1 = itemView.findViewById(R.id.tvC3R1C1);
            tvC3R2C1 = itemView.findViewById(R.id.tvC3R2C1);
            tvC4R1C1 = itemView.findViewById(R.id.tvC4R1C1);
            tvC4R2C1 = itemView.findViewById(R.id.tvC4R2C1);
            tvC5R1C1 = itemView.findViewById(R.id.tvC5R1C1);
            tvC5R2C1 = itemView.findViewById(R.id.tvC5R2C1);
            tvC6R1C1 = itemView.findViewById(R.id.tvC6R1C1);
            tvC6R2C1 = itemView.findViewById(R.id.tvC6R2C1);
            tvC7R1C1 = itemView.findViewById(R.id.tvC7R1C1);
            tvC7R2C1 = itemView.findViewById(R.id.tvC7R2C1);
            tvC8R1C1 = itemView.findViewById(R.id.tvC8R1C1);
            tvC8R2C1 = itemView.findViewById(R.id.tvC8R2C1);


        }
    }
}