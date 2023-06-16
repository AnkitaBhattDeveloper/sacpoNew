package za.co.sacpo.grantportal.xCubeLib.adapter.old;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.model.MHolydays;

/**
 * Created by xcube-06 on 8/8/18.
 */

public class HolidaysAdapter  extends RecyclerView.Adapter<HolidaysAdapter.CustomViewHolder> {
      private List<MHolydays> mHolydaysList;

      public HolidaysAdapter(List<MHolydays> mHolydaysList)
      {

      this.mHolydaysList = mHolydaysList;
      }

     @Override
     public HolidaysAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holydays_listing, parent, false);
        return new HolidaysAdapter.CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HolidaysAdapter.CustomViewHolder holder, int position) {
        MHolydays mHolydays = mHolydaysList.get(position);
        holder.txHoli.setText(mHolydays.getHoliday());
        holder.txDate.setText(mHolydays.getDate());

    }
    @Override
    public int getItemCount() {
        if (mHolydaysList != null) {
            return mHolydaysList.size();
        }
        return 0;
        }
        public class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView txHoli,txDate;
        public TextView lblDate,lblHolidays;
        public CustomViewHolder(View view) {
            super(view);
            txHoli = (TextView) view.findViewById(R.id.txHoli);
            txDate = (TextView) view.findViewById(R.id.txDate);
            lblHolidays = (TextView) view.findViewById(R.id.lblHolidays);
            lblDate = (TextView) view.findViewById(R.id.lblDate);

        }
    }

}
