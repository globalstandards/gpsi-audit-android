package globalstd.globalaudit.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import globalstd.globalaudit.R;
import globalstd.globalaudit.objects.Plan;

/**
 * Created by Gabriel Vazquez on 21/03/2017.
 */

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder>{
    Context context;
    List<Plan> listPlans;
    OnItemClickListener onItemClickListener;


    public PlanAdapter(Context context) {
        this.context = context;
    }

    public PlanAdapter(Context context, List<Plan> listPlans) {
        this.context = context;
        this.listPlans = listPlans;

        //fontOpenSans = Typeface.createFromAsset(context.getAssets(), "fonts/OpenSansCondLight.ttf");
        //fontOpenSansBold = Typeface.createFromAsset(context.getAssets(), "fonts/OpenSansCondBold.ttf");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plan, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.txtDate.setText(listPlans.get(position).getDate());
        holder.txtTime.setText(listPlans.get(position).getTime());
        holder.txtNameAuditor.setText(listPlans.get(position).getAuditor().getName()+" "+listPlans.get(position).getAuditor().getLastName());
        holder.txtPosition.setText(listPlans.get(position).getAuditor().getPosition());
        holder.txtProcess.setText(listPlans.get(position).getProcess());

    }


    @Override
    public int getItemCount() {
        return listPlans.size();
    }

    public static String byIdString(Context context, String name) {
        Resources res = context.getResources();
        return res.getString(res.getIdentifier(name, "string", context.getPackageName()));
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CardView cv;
        public TextView txtDate;
        public TextView txtTime;
        public TextView txtNameAuditor;
        public TextView txtPosition;
        public TextView txtProcess;

        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            txtDate = (TextView) itemView.findViewById(R.id.txtDate);
            txtTime = (TextView) itemView.findViewById(R.id.txtTime);
            txtNameAuditor = (TextView) itemView.findViewById(R.id.txtNameAuditor);
            txtPosition = (TextView) itemView.findViewById(R.id.txtPosition);
            txtProcess = (TextView) itemView.findViewById(R.id.txtProcess);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(itemView, getPosition());
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.onItemClickListener = mItemClickListener;
    }

}

