package za.co.sacpo.grant.xCubeLib.baseFramework;


import android.content.Intent;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.component.CropCircleTransformation;
import za.co.sacpo.grant.xCubeLib.session.OlumsGrantSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsStudentSession;
import za.co.sacpo.grant.xCubeMentor.MEditProfileA;
import za.co.sacpo.grant.xCubeStudent.SEditProfileA;

public abstract class BaseDrawerA extends BaseAPCPrivate
        implements NavigationView.OnNavigationItemSelectedListener {

    protected void initDrawer(){
        String email = userSessionObj.getUserEmail();
        String fname = userSessionObj.getUserFName();
        String sname = userSessionObj.getUserSName();
        String thumb = userSessionObj.getUserThumb();
        String userType = userSessionObj.getUserType();
        String uType = userSessionObj.getUserTypeName();
        String uMobile = userSessionObj.getUserMobile();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        printLogs(LogTag,"initDrawer","initDrawer toolbar drawer_layout");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View v = navigationView.getHeaderView(0);
        TextView txtUsername = (TextView) v.findViewById(R.id.layout_user_name);
        txtUsername.setText(fname + " " + sname+" ("+uType+")");

        TextView txtEmail = (TextView) v.findViewById(R.id.layout_email);
        txtEmail.setText(email);

        TextView txtCell = (TextView) v.findViewById(R.id.layout_cell);
        txtCell.setText(uMobile);
        if(userType.equals("2")) {
            studentSessionObj = new OlumsStudentSession(baseApcContext);
            String grantId = "";
            grantId = studentSessionObj.getGrantId();
            int grantIdInt =0;
            if(!TextUtils.isEmpty(grantId)) {
                grantIdInt=Integer.parseInt(grantId);
            }
            if(grantIdInt>0) {
                grantSessionObj = new OlumsGrantSession(baseApcContext);
                String mentorName = grantSessionObj.getGrantMentorName();
                navigationView.getMenu().findItem(R.id.itSupervisor).setTitle(mentorName);
                navigationView.getMenu().findItem(R.id.itSupervisor).setVisible(true);
                String mentorEmail = grantSessionObj.getGrantMentorEmail();
                navigationView.getMenu().findItem(R.id.itSupervisorEmail).setTitle(mentorEmail);
                navigationView.getMenu().findItem(R.id.itSupervisorEmail).setVisible(true);
                String mentorCell = grantSessionObj.getGrantMentorCell();
                navigationView.getMenu().findItem(R.id.itSupervisorCell).setTitle(mentorCell);
                navigationView.getMenu().findItem(R.id.itSupervisorCell).setVisible(true);
            }
        }
        ImageView ImgView = (ImageView) v.findViewById(R.id.layout_user_image);
        if(!thumb.equals("")) {
            Picasso.get().load(thumb).resize(130,130).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).networkPolicy(NetworkPolicy.NO_CACHE).placeholder(R.mipmap.user).error(R.mipmap.user).transform(new CropCircleTransformation()).into(ImgView);
        }else{
            ImgView.setImageResource(R.drawable.app_logo_1);
        }

        LinearLayout LLHeadTop = (LinearLayout) v.findViewById(R.id.headerTop);

        if(userType.equals("2")) {
            LLHeadTop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(baseApcContext, SEditProfileA.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
        else if(userType.equals("5")) {
            LLHeadTop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(baseApcContext, MEditProfileA.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

        printLogs(LogTag,"initDrawer","initDrawer nav_view");
    }

  }
