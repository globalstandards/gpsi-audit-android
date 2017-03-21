package globalstd.globalaudit.adapters;

import globalstd.globalaudit.objects.Auditor;

/**
 * Created by Gabriel Vazquez on 16/03/2017.
 */


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


public class AuditorAdapter extends RecyclerView.Adapter<AuditorAdapter.ViewHolder>{
    Context context;
    List<Auditor> listAuditors;
    OnItemClickListener onItemClickListener;


    public AuditorAdapter(Context context) {
        this.context = context;
    }

    public AuditorAdapter(Context context, List<Auditor> listAuditors) {
        this.context = context;
        this.listAuditors = listAuditors;

        //fontOpenSans = Typeface.createFromAsset(context.getAssets(), "fonts/OpenSansCondLight.ttf");
        //fontOpenSansBold = Typeface.createFromAsset(context.getAssets(), "fonts/OpenSansCondBold.ttf");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_auditor, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.txtName.setText(listAuditors.get(position).getName()+" "+listAuditors.get(position).getLastName());
        holder.txtPosition.setText(listAuditors.get(position).getPosition());
    }


    @Override
    public int getItemCount() {
        return listAuditors.size();
    }

    public static String byIdString(Context context, String name) {
        Resources res = context.getResources();
        return res.getString(res.getIdentifier(name, "string", context.getPackageName()));
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CardView cv;
        public TextView txtName;
        public TextView txtPosition;

        public ViewHolder(View itemView) {
            super(itemView);

            cv = (CardView) itemView.findViewById(R.id.cv);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtPosition = (TextView) itemView.findViewById(R.id.txtPosition);
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

