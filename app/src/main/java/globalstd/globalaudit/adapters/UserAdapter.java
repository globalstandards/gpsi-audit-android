package globalstd.globalaudit.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import globalstd.globalaudit.R;
import globalstd.globalaudit.objects.Supplier;
import globalstd.globalaudit.objects.User;

/**
 * Created by Gabriel Vazquez on 14/03/2017.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
    Context context;
    List<User> listUsers;
    UserAdapter.OnItemClickListener onItemClickListener;

    public UserAdapter(Context context) {
        this.context = context;
    }

    public UserAdapter(Context context, List<User> listUsers) {
        this.context = context;
        this.listUsers = listUsers;

        //fontOpenSans = Typeface.createFromAsset(context.getAssets(), "fonts/OpenSansCondLight.ttf");
        //fontOpenSansBold = Typeface.createFromAsset(context.getAssets(), "fonts/OpenSansCondBold.ttf");
    }

    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final UserAdapter.ViewHolder holder, final int position) {
        Glide.with(this.context)
                .load(listUsers.get(position).getImageSmall())
                .asBitmap()
                .into(holder.imgProfile);
        holder.txtName.setText(listUsers.get(position).getName());
        holder.txtEmail.setText(listUsers.get(position).getEmail());
        holder.txtRole.setText(listUsers.get(position).getRole());
        holder.txtLenguaje.setText(listUsers.get(position).getLenguaje());
    }

    @Override
    public int getItemCount() {
        return listUsers.size();
    }

    public static String byIdString(Context context, String name) {
        Resources res = context.getResources();
        return res.getString(res.getIdentifier(name, "string", context.getPackageName()));
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CardView cv;
        public CircleImageView imgProfile;
        public TextView txtName;
        public TextView txtEmail;
        public TextView txtRole;
        public TextView txtLenguaje;


        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            imgProfile = (CircleImageView) itemView.findViewById(R.id.imgProfile);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtEmail = (TextView) itemView.findViewById(R.id.txtEmail);
            txtRole = (TextView) itemView.findViewById(R.id.txtRole);
            txtLenguaje = (TextView) itemView.findViewById(R.id.txtLenguaje);

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

    public void setOnItemClickListener(final UserAdapter.OnItemClickListener mItemClickListener) {
        this.onItemClickListener = mItemClickListener;
    }

}

