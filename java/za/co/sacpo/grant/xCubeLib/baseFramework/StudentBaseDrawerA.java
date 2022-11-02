package za.co.sacpo.grant.xCubeLib.baseFramework;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import za.co.sacpo.grant.LoginA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.asyncCalls.TokenDelete;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.session.OlumsGrantSession;
import za.co.sacpo.grant.xCubeStudent.SEditProfileA;
import za.co.sacpo.grant.xCubeStudent.SDashboardDA;
import za.co.sacpo.grant.xCubeStudent.SUploadPicA;
import za.co.sacpo.grant.xCubeStudent.attendance.SAttDA;
import za.co.sacpo.grant.xCubeStudent.attendance.SOutOfRangeA;
import za.co.sacpo.grant.xCubeStudent.bank.SBankDA;
import za.co.sacpo.grant.xCubeStudent.feedback.SLearnerReportsList;
import za.co.sacpo.grant.xCubeStudent.forms.SFormsA;
import za.co.sacpo.grant.xCubeStudent.grant.SGrantDetailsA;
import za.co.sacpo.grant.xCubeStudent.leaves.SLeavesDA;
import za.co.sacpo.grant.xCubeStudent.messages.SChatA;
import za.co.sacpo.grant.xCubeStudent.queries.SContactSupportPrivateA;
import za.co.sacpo.grant.xCubeStudent.queries.SQueriesGroupA;
import za.co.sacpo.grant.xCubeStudent.stipends.SStipendsDA;
import za.co.sacpo.grant.xCubeStudent.SChangePasswordA;
import za.co.sacpo.grant.xCubeStudent.upload.UploadCv;


public abstract class StudentBaseDrawerA extends BaseDrawerA {
    private String ActivityId="175";
    private TokenDelete mTokenDeleteTask = null;
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer != null){
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.d_r_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_change_password) {
            outBundle = new Bundle();
            Intent intent = new Intent(baseApcContext,SChangePasswordA.class);
            printLogs(LogTag, "onOptionsItemSelected", "action_change_password"+CAId);
            outBundle.putString("generator",CAId);
            intent.putExtras(outBundle);
            startActivity(intent);
            finish();
        }

        else if (id == R.id.action_edit_profile) {
            Intent intent = new Intent(baseApcContext,SEditProfileA.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_upload_profile_pic) {
            Intent intent = new Intent(baseApcContext,SUploadPicA.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_Home) {
            Intent intent = new Intent(baseApcContext,SDashboardDA.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.action_uploadCv) {
            Intent intent = new Intent(baseApcContext, UploadCv.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_privatePolicy) {
            Uri uri = Uri.parse(URLHelper.DOMAIN_URL+"/policy-and-guidelines"); // call url
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_logout) {
            clearSession(this.getApplicationContext());
            mTokenDeleteTask = new TokenDelete(baseApcContext);
            mTokenDeleteTask.execute((Void) null);
            Intent intent = new Intent(baseApcContext, LoginA.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            baseApcContext.startActivity(intent);
            finish();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item ) {
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            Intent intent = new Intent(baseApcContext,SDashboardDA.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_attendance) {
            Intent intent = new Intent(baseApcContext,SAttDA.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_leave) {
            Intent intent = new Intent(baseApcContext,SLeavesDA.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_bank_details) {
            Intent intent = new Intent(baseApcContext,SBankDA.class);
            startActivity(intent);
            finish();
        }

        else if (id == R.id.nav_grants_detail) {
            Bundle inputUri = new Bundle();
            inputUri.putString("generator", "103");
            Intent intent = new Intent(baseApcContext,SGrantDetailsA.class);
            intent.putExtras(inputUri);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_forms) {
            Intent intent = new Intent(baseApcContext,SFormsA.class);
            startActivity(intent);
            finish();
        }


        else if (id == R.id.nav_stipends) {
            Intent intent = new Intent(baseApcContext,SStipendsDA.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_out_of_range) {
            Intent intent = new Intent(baseApcContext,SOutOfRangeA.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_post_query) {
            Bundle inputUri = new Bundle();
            inputUri.putString("generator", "103");
            Intent intent = new Intent(baseApcContext,SContactSupportPrivateA.class);
            intent.putExtras(inputUri);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_view_queries) {
            Intent intent = new Intent(baseApcContext, SQueriesGroupA.class);
            startActivity(intent);
            finish();
        }
        else if (id == R.id.nav_comments) {
            Intent intent = new Intent(baseApcContext, SLearnerReportsList.class);
            startActivity(intent);
            finish();
        }

        else if (id == R.id.nav_logout) {
            clearSession(this.getApplicationContext());
            mTokenDeleteTask = new TokenDelete(baseApcContext);
            mTokenDeleteTask.execute((Void) null);
            Intent intent = new Intent(baseApcContext,LoginA.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            baseApcContext.startActivity(intent);
            finish();
        } else if(id==R.id.itSupervisor){
            Bundle inputUri = new Bundle();
            grantSessionObj = new OlumsGrantSession(baseApcContext);
            String mentor_id = grantSessionObj.getGrantMentorId();
            String mentor_name = grantSessionObj.getGrantMentorName();
            inputUri.putString("fId", mentor_id);
            inputUri.putString("fIsGroup", "0");
            inputUri.putString("fName",mentor_name);
            inputUri.putString("fImage","");
            inputUri.putString("generator", "103");
            Intent intent = new Intent(baseApcContext, SChatA.class);
            intent.putExtras(inputUri);
            startActivity(intent);
            finish();
        }
        else if(id==R.id.itSupervisorEmail){
            Bundle inputUri = new Bundle();
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setData(Uri.parse("mailto:"));
            intent.setType("plain/text");
            grantSessionObj = new OlumsGrantSession(baseApcContext);
            String email = grantSessionObj.getGrantMentorEmail();
            intent.putExtra(Intent.EXTRA_EMAIL, new String[] { email });
            startActivity(Intent.createChooser(intent, "Send Email"));
        }
        else if(id==R.id.itSupervisorCell){
            Bundle inputUri = new Bundle();
            grantSessionObj = new OlumsGrantSession(baseApcContext);
            String cell = grantSessionObj.getGrantMentorCell();
            Uri call = Uri.parse("tel:" + cell);
            Intent surf = new Intent(Intent.ACTION_DIAL, call);
            startActivity(surf);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}