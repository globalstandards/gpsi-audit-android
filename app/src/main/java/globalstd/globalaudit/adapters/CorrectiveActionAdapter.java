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
import globalstd.globalaudit.objects.Auditor;
import globalstd.globalaudit.objects.Cars;

/**
 * Created by Gabriel Vazquez on 17/03/2017.
 */

public class CorrectiveActionAdapter  extends RecyclerView.Adapter<CorrectiveActionAdapter.ViewHolder>{
    Context context;
    List<Cars> listCars;
    CorrectiveActionAdapter.OnItemClickListener onItemClickListener;


    public CorrectiveActionAdapter(Context context) {
        this.context = context;
    }

    public CorrectiveActionAdapter(Context context, List<Cars> listCars) {
        this.context = context;
        this.listCars = listCars;

        //fontOpenSans = Typeface.createFromAsset(context.getAssets(), "fonts/OpenSansCondLight.ttf");
        //fontOpenSansBold = Typeface.createFromAsset(context.getAssets(), "fonts/OpenSansCondBold.ttf");
    }

    @Override
    public CorrectiveActionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_corrective_action, parent, false);
        return new CorrectiveActionAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final CorrectiveActionAdapter.ViewHolder holder, final int position) {
        holder.txtRef.setText(listCars.get(position).getRef());
        holder.txtStatus.setText(listCars.get(position).getStatus());
        holder.txtNameAuditor.setText(listCars.get(position).getAuditor().getName()+" "+listCars.get(position).getAuditor().getLastName());

        holder.txtDate.setText(listCars.get(position).getDate());
    }


    @Override
    public int getItemCount() {
        return listCars.size();
    }

    public static String byIdString(Context context, String name) {
        Resources res = context.getResources();
        return res.getString(res.getIdentifier(name, "string", context.getPackageName()));
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CardView cv;
        public TextView txtRef;
        public TextView txtStatus;
        public TextView txtNameAuditor;
        public TextView txtDate;

        public ViewHolder(View itemView) {
            super(itemView);

            cv = (CardView) itemView.findViewById(R.id.cv);
            txtRef = (TextView) itemView.findViewById(R.id.txtRef);
            txtStatus = (TextView) itemView.findViewById(R.id.txtStatus);
            txtNameAuditor = (TextView) itemView.findViewById(R.id.txtNameAuditor);
            txtDate = (TextView) itemView.findViewById(R.id.txtDate);
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

    public void setOnItemClickListener(final CorrectiveActionAdapter.OnItemClickListener mItemClickListener) {
        this.onItemClickListener = mItemClickListener;
    }

}


