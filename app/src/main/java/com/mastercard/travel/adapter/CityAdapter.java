package com.mastercard.travel.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.anypresence.sdk.master_travel_ecomm.models.City;
import com.mastercard.travel.R;
import com.mastercard.travel.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diego.rotondale on 23/09/14.
 */
public class CityAdapter extends ArrayAdapter<City> {
    private List<City> cities = new ArrayList<City>();
    private Context context;

    public CityAdapter(Context context, List<City> cities) {
        super(context, R.layout.item_cities);
        this.context = context;
        this.cities = cities;
        super.addAll(cities);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final CityHolder holder;
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            holder = new CityHolder();
            view = inflater.inflate(R.layout.item_cities, parent, false);
            holder.title = (TextView) view.findViewById(R.id.title);
            holder.image = (NetworkImageView) view.findViewById(R.id.city_image);
            holder.desc = (TextView)view.findViewById(R.id.description);
            view.setTag(holder);
        } else {
            holder = (CityHolder) view.getTag();
        }

        final City city = getItem(position);
        holder.title.setText(city.getCityName());
        holder.desc.setText(city.getDesc());
        ImageUtil.setImageUrl(holder.image, city.getImageUrl(),this.context);
        return view;
    }


    private static class CityHolder {
        TextView title;
        TextView desc;
        NetworkImageView image;
    }

//    class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
//        private final WeakReference<ImageView> imageViewReference;
//        private int data = 0;
//
//        public BitmapWorkerTask(ImageView imageView) {
//            // Use a WeakReference to ensure the ImageView can be garbage collected
//            imageViewReference = new WeakReference<ImageView>(imageView);
//        }
//
//        // Decode image in background.
//        @Override
//        protected Bitmap doInBackground(Integer... params) {
//
//            return ImageUtil.getRoundedCornerBitmap(ImageUtil.getBitmapFromAsset(getContext(), "places.jpg"));
//        }
//
//        // Once complete, see if ImageView is still around and set bitmap.
//        @Override
//        protected void onPostExecute(Bitmap bitmap) {
//            if (imageViewReference != null && bitmap != null) {
//                final ImageView imageView = imageViewReference.get();
//                if (imageView != null) {
//                    imageView.setBackgroundDrawable(new BitmapDrawable(bitmap));
//                    imageView.setImageBitmap(bitmap);
//                }
//            }
//        }
//    }
//
//    public void loadBitmap(int resId, ImageView imageView) {
//        BitmapWorkerTask task = new BitmapWorkerTask(imageView);
//        task.execute(resId);
//    }

}
