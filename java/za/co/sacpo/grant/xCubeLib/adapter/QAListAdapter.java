package za.co.sacpo.grant.xCubeLib.adapter;

import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.dataObj.QAObj;

public class QAListAdapter  extends PagerAdapter {
    private Context context;
    private List<QAObj> dataObjectList;
    private LayoutInflater layoutInflater;


    public  QAListAdapter(Context context,List<QAObj> dataObjectList)
    {
        this.context=context;
        this.dataObjectList=dataObjectList;
        this.layoutInflater=(LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return dataObjectList.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (View) object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view=this.layoutInflater.inflate(R.layout.slider_row,container,false);
        TextView txtQuizeNo=(TextView)view.findViewById(R.id.txtQuizeNo);
        TextView txtDescription1=(TextView)view.findViewById(R.id.txtDescription1);
        TextView txtDescription2=(TextView)view.findViewById(R.id.txtDescription2);
        TextView txtDescription3=(TextView)view.findViewById(R.id.txtDescription3);
        TextView txtDescription4=(TextView)view.findViewById(R.id.txtDescription4);

        txtQuizeNo.setText(this.dataObjectList.get(position).getQuizNo());
        txtDescription1.setText(this.dataObjectList.get(position).getOpA());
        txtDescription2.setText(this.dataObjectList.get(position).getOpB());
        txtDescription3.setText(this.dataObjectList.get(position).getOpC());
        txtDescription4.setText(this.dataObjectList.get(position).getOpD());

        container.addView(view);
        return view;
    }

}

