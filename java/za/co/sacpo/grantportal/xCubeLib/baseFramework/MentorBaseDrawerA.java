package za.co.sacpo.grantportal.xCubeLib.baseFramework;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import za.co.sacpo.grantportal.LoginA;
import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.asyncCalls.TokenDelete;

import za.co.sacpo.grantportal.xCubeLib.component.URLHelper;
import za.co.sacpo.grantportal.xCubeMentor.MChangePassword;
import za.co.sacpo.grantportal.xCubeMentor.MEditProfileA;

import za.co.sacpo.grantportal.xCubeMentor.MUploadPicA;
import za.co.sacpo.grantportal.xCubeMentor.MDashboardDA;
import za.co.sacpo.grantportal.xCubeMentor.queries.MContactSupportPrivateA;
import za.co.sacpo.grantportal.xCubeMentor.queries.MQueriesGroupA;


public abstract class MentorBaseDrawerA extends BaseDrawerA {
    private TokenDelete mTokenDeleteTask = null;
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.d_m_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_change_password) {
            outBundle = new Bundle();
            Intent intent = new Intent(baseApcContext,MChangePassword.class);
            printLogs(LogTag, "onOptionsItemSelected", "action_change_password"+CAId);
            outBundle.putString("generator",CAId);
            intent.putExtras(outBundle);
            startActivity(intent);
            finish();
        }

        else if (id == R.id.action_edit_profile) {
            Intent intent = new Intent(baseApcContext,MEditProfileA.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_upload_profile_pic) {
            Intent intent = new Intent(baseApcContext, MUploadPicA.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.action_Home) {
            Intent intent = new Intent(baseApcContext,MDashboardDA.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_privatePolicy) {
            Uri uri = Uri.parse(URLHelper.DOMAIN_URL+"/policy-and-guidelines"); // call url
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            return true;
        }
       /* else if (id == R.id.action_update_mobile) {
            Intent intent = new Intent(baseApcContext,MUpdateMobile.class);
            startActivity(intent);
            return true;
        }*/
        else if (id == R.id.action_logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("DO YOU WANT TO LOGOUT THE APP ?");
            //  builder.setMessage("DO YOU WANT TO LOGOUT THE APP ?");
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing but close the dialog
                    clearSession(getApplicationContext());
                    mTokenDeleteTask = new TokenDelete(baseApcContext);
                    mTokenDeleteTask.execute((Void) null);
                    Intent intent = new Intent(baseApcContext, LoginA.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    baseApcContext.startActivity(intent);
                    finish();
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            Intent intent = new Intent(baseApcContext,MDashboardDA.class);
            startActivity(intent);
            finish();
        }/* else if (id == R.id.nav_workX) {
            Bundle inputUri = new Bundle();
            inputUri.putString("generator", "205");
            Intent intent = new Intent(baseApcContext,MWorkXsDA.class);
            intent.putExtras(inputUri);
            startActivity(intent);
            finish();
        }*/
        else if (id == R.id.nav_view_queries) {
            Intent intent = new Intent(baseApcContext, MQueriesGroupA.class);
            startActivity(intent);
            finish();
        }/*else if (id == R.id.nav_feedbackReports) {
            Intent intent = new Intent(baseApcContext, MStudentFeedback.class);
            startActivity(intent);
            finish();
        }else if (id == R.id.nav_Ass_work) {
            Intent intent = new Intent(baseApcContext, MAssignWorkstationA.class);
            startActivity(intent);
            finish();
        }else if (id == R.id.nav_workingWeek) {
            Intent intent = new Intent(baseApcContext, MChangeOffDay.class);
            startActivity(intent);
            finish();
        }else if (id == R.id.nav_claim_approval) {
            Bundle inputUri = new Bundle();
            inputUri.putString("generator", "205");
            Intent intent = new Intent(baseApcContext, MPendingClaimsA.class);
            intent.putExtras(inputUri);
            startActivity(intent);
            finish();
        } */
        else if (id == R.id.nav_post_query) {
            Bundle inputUri = new Bundle();
            inputUri.putString("generator", "312");
            Intent intent = new Intent(baseApcContext,MContactSupportPrivateA.class);
            intent.putExtras(inputUri);
            startActivity(intent);
            finish();
        }/* else if (id == R.id.nav_GrantDetails) {
            Bundle inputUri = new Bundle();
            inputUri.putString("generator", "312");
            Intent intent = new Intent(baseApcContext,MGrantDetailsAM.class);
            intent.putExtras(inputUri);
            startActivity(intent);
            finish();
        }*//* else if (id == R.id.nav_reports) {
            Bundle inputUri = new Bundle();
            inputUri.putString("generator", "312");
            Intent intent = new Intent(baseApcContext,MContactSupportPrivateA.class);
            intent.putExtras(inputUri);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_details) {
            Bundle inputUri = new Bundle();
            inputUri.putString("generator", "312");
            Intent intent = new Intent(baseApcContext,MContactSupportPrivateA.class);
            intent.putExtras(inputUri);
            startActivity(intent);
            finish();
        }*/

        else if (id == R.id.nav_logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("DO YOU WANT TO LOGOUT THE APP ?");
            //  builder.setMessage("DO YOU WANT TO LOGOUT THE APP ?");
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing but close the dialog
                    clearSession(getApplicationContext());
                    mTokenDeleteTask = new TokenDelete(baseApcContext);
                    mTokenDeleteTask.execute((Void) null);
                    Intent intent = new Intent(baseApcContext, LoginA.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    baseApcContext.startActivity(intent);
                    finish();
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
