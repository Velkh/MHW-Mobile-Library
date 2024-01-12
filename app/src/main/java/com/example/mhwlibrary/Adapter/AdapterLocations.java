package com.example.mhwlibrary.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mhwlibrary.R;
import com.squareup.picasso.Picasso;

import com.example.mhwlibrary.Model.Model_Locations;

import java.util.ArrayList;
import java.util.List;
public class AdapterLocations extends BaseAdapter implements Filterable {
    private Context context;
    private ArrayList<Model_Locations> locationsList = new ArrayList<>();
    private ArrayList<Model_Locations> filteredList = new ArrayList<>(); // Add filtered list
    private LocationsFilter locationsFilter;

    public AdapterLocations(Context context, ArrayList<Model_Locations> locationsList) {
        this.context = context;
        this.locationsList = locationsList;
        this.filteredList = new ArrayList<>(locationsList); // Initialize filtered list
    }

    @Override
    public int getCount() {
        return filteredList.size(); // Use filtered list size
    }

    @Override
    public Object getItem(int i) {
        return filteredList.get(i); // Use filtered list
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void setLocationsList(ArrayList<Model_Locations> locationsList) {
        this.locationsList = locationsList;
        this.filteredList = new ArrayList<>(locationsList);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView = view;

        if (itemView == null) {
            itemView = LayoutInflater.from(context)
                    .inflate(R.layout.item_locations, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(itemView);
            itemView.setTag(viewHolder);
        }

        ViewHolder viewHolder = (ViewHolder) itemView.getTag();

        Model_Locations locations = (Model_Locations) getItem(i);
        viewHolder.bind(locations);
        return itemView;
    }
//
    private static class ViewHolder {
        private TextView txtLocName;
        private ImageView imgPhoto;

        ViewHolder(View view) {
            txtLocName = view.findViewById(R.id.txtLocations);
            imgPhoto = view.findViewById(R.id.img_photo);
        }

        void bind(Model_Locations locations) {
            txtLocName.setText(locations.getMaps_name());
            if (imgPhoto != null) {
                Picasso.get()
                        .load(locations.getMaps_image())
                        .error(R.drawable.user)
                        .into(imgPhoto);
            } else {
                // Log an error or handle the case where imgPhoto is null
                Log.e("AdapterLocations", "ImageView (imgPhoto) is null in bind method");
            }
        }
    }

    @Override
    public Filter getFilter() {
        if (locationsFilter == null) {
            locationsFilter = new AdapterLocations.LocationsFilter(this, locationsList);
        }
        return locationsFilter;
    }

    private static class LocationsFilter extends Filter {
        private final AdapterLocations adapter;
        private final List<Model_Locations> originalList;
        private final List<Model_Locations> filteredList;

        LocationsFilter(AdapterLocations adapter, List<Model_Locations> originalList) {
            super();
            this.adapter = adapter;
            this.originalList = new ArrayList<>(originalList);
            this.filteredList = new ArrayList<>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            filteredList.clear();
            final FilterResults results = new FilterResults();

            if (charSequence.length() == 0) {
                filteredList.addAll(originalList);
            } else {
                final String filterPattern = charSequence.toString().toLowerCase().trim();

                for (final Model_Locations locations : originalList) {
                    if (locations.getMaps_name().toLowerCase().contains(filterPattern)) {
                        filteredList.add(locations);
                    }
                }
            }

            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            adapter.filteredList.clear();
            adapter.filteredList.addAll((List<Model_Locations>) filterResults.values);
            adapter.notifyDataSetChanged();
        }
    }
}
