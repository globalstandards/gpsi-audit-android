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
import globalstd.globalaudit.objects.Supplier;

/**
 * Created by Gabriel Vazquez on 10/03/2017.
 */

public class SupplierAdapter  extends RecyclerView.Adapter<SupplierAdapter.ViewHolder>{
    Context context;
    List<Supplier> listSuppliers;
    OnItemClickListener onItemClickListener;


    public SupplierAdapter(Context context) {
        this.context = context;
    }

    public SupplierAdapter(Context context, List<Supplier> listSuppliers) {
        this.context = context;
        this.listSuppliers = listSuppliers;

        //fontOpenSans = Typeface.createFromAsset(context.getAssets(), "fonts/OpenSansCondLight.ttf");
        //fontOpenSansBold = Typeface.createFromAsset(context.getAssets(), "fonts/OpenSansCondBold.ttf");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_supplier, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.txtName.setText(listSuppliers.get(position).getName());
        holder.txtEmail.setText(listSuppliers.get(position).getEmail());
        holder.txtTel.setText(listSuppliers.get(position).getTel());
    }


    @Override
    public int getItemCount() {
        return listSuppliers.size();
    }

    public static String byIdString(Context context, String name) {
        Resources res = context.getResources();
        return res.getString(res.getIdentifier(name, "string", context.getPackageName()));
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CardView cv;
        public TextView txtName;
        public TextView txtEmail;
        public TextView txtTel;

        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtEmail = (TextView) itemView.findViewById(R.id.txtEmail);
            txtTel = (TextView) itemView.findViewById(R.id.txtTel);
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

