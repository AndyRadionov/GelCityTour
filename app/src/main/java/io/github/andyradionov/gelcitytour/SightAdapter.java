package io.github.andyradionov.gelcitytour;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import io.github.andyradionov.gelcitytour.data.Sight;


/**
 * @author Andrey Radionov
 */

public class SightAdapter extends RecyclerView.Adapter<SightAdapter.ProductViewHolder> {

    private Context mContext;
    private OnSightItemClickListener mClickListener;
    private Cursor mCursor;

    public interface OnSightItemClickListener {
        void onSightItemClick(Sight sight);
    }

    public SightAdapter(Context context, OnSightItemClickListener clickListener) {
        mContext = context;
        mClickListener = clickListener;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_category_sight, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (null == mCursor) return 0;
        return mCursor.getCount();
    }

    void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    private Sight getSight(int position) {
        mCursor.moveToPosition(position);
        String category = mCursor.getString(MainActivity.INDEX_SIGHT_CATEGORY);
        String name = mCursor.getString(MainActivity.INDEX_SIGHT_NAME);
        String address = mCursor.getString(MainActivity.INDEX_SIGHT_ADDRESS);
        String description = mCursor.getString(MainActivity.INDEX_SIGHT_DESCRIPTION);
        String image = mCursor.getString(MainActivity.INDEX_SIGHT_IMAGE);
        return new Sight(category, name, address, description, image);
    }

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mSightThumbNail;
        private TextView mSightName;
        private TextView mSightAddress;

        ProductViewHolder(View itemView) {
            super(itemView);
            mSightThumbNail = itemView.findViewById(R.id.iv_thumbnail);
            mSightName = itemView.findViewById(R.id.tv_sight_name);
            mSightAddress = itemView.findViewById(R.id.tv_sight_address);

            itemView.setOnClickListener(this);
        }

        void bind(int position) {
            Sight sight = getSight(position);

            mSightName.setText(String.valueOf(sight.getName()));
            mSightAddress.setText(sight.getAddress());

            int imageId = mContext
                    .getResources()
                    .getIdentifier(sight.getImage(), "drawable", mContext.getPackageName());
            mSightThumbNail.setImageResource(imageId);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Sight sight = getSight(position);

            mClickListener.onSightItemClick(sight);
        }
    }
}
