package globalstd.globalaudit.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import globalstd.globalaudit.R;
import globalstd.globalaudit.objects.Audit;

/**
 * Created by Gabriel Vazquez on 28/02/2017.
 */

public class AuditCertificationAdapter extends RecyclerView.Adapter<AuditCertificationAdapter.ViewHolder>{
    Context context;
    List<Audit> listAudits;
    OnItemClickListener onItemClickListener;

    public Typeface fontOpenSans;
    public Typeface fontOpenSansBold;

    public AuditCertificationAdapter(Context context) {
        this.context = context;
    }

    public AuditCertificationAdapter(Context context, List<Audit> listAudits) {
        this.context = context;
        this.listAudits = listAudits;

        //fontOpenSans = Typeface.createFromAsset(context.getAssets(), "fonts/OpenSansCondLight.ttf");
        //fontOpenSansBold = Typeface.createFromAsset(context.getAssets(), "fonts/OpenSansCondBold.ttf");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_audit_certification, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.txtStandard.setText(listAudits.get(position).getStandard());
        holder.txtStatus.setText(listAudits.get(position).getStatus().getStatus());
        holder.txtDate.setText(listAudits.get(position).getData().getData());
        holder.txtHour.setText(listAudits.get(position).getData().getHour());
        holder.txtLeaderAuditor.setText(listAudits.get(position).getLeaderAuditor().getName()+" "+listAudits.get(position).getLeaderAuditor().getLastName());
    }

    private String getStatus(int status, TextView txtStatus){
        String statu="";
        if(status==0){
            statu="incomplete_2";
            txtStatus.setTextColor(context.getResources().getColor(R.color.md_orange_A400));
        }else{
            statu="complete";
            txtStatus.setTextColor(context.getResources().getColor(R.color.green));
        }
        return statu;

    }

    @Override
    public int getItemCount() {
        return listAudits.size();
    }

    public static String byIdString(Context context, String name) {
        Resources res = context.getResources();
        return res.getString(res.getIdentifier(name, "string", context.getPackageName()));
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CardView cv;
        public TextView txtStandard;
        public TextView txtStatus;
        public TextView txtLeaderAuditor;
        public TextView txtDate;
        public TextView txtHour;

        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            txtStandard = (TextView) itemView.findViewById(R.id.txtStandard);
            txtStatus = (TextView) itemView.findViewById(R.id.txtStatus);
            txtLeaderAuditor = (TextView) itemView.findViewById(R.id.txtLeaderAuditor);
            txtDate = (TextView) itemView.findViewById(R.id.txtDate);
            txtHour = (TextView) itemView.findViewById(R.id.txtHour);

            cv.setOnClickListener(this);
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

