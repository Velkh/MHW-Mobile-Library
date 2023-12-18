package com.example.mhwlibrary.Adapter;

import android.content.Context;
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

import com.example.mhwlibrary.Model.Model_Monsters;

import java.util.ArrayList;
import java.util.List;
public class AdapterMonsters extends BaseAdapter implements Filterable {
    private Context context;
    private ArrayList<Model_Monsters> monstersList = new ArrayList<>();
    private ArrayList<Model_Monsters> filteredList = new ArrayList<>(); // Add filtered list
    private MonstersFilter monstersFilter;

    public AdapterMonsters(Context context, ArrayList<Model_Monsters> monstersList) {
        this.context = context;
        this.monstersList = monstersList;
        this.filteredList = new ArrayList<>(monstersList); // Initialize filtered list
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

    public void setMonstersList(ArrayList<Model_Monsters> monstersList) {
        this.monstersList = monstersList;
        this.filteredList = new ArrayList<>(monstersList);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView = view;

        if (itemView == null) {
            itemView = LayoutInflater.from(context)
                    .inflate(R.layout.item_monsters, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(itemView);
            itemView.setTag(viewHolder);
        }

        ViewHolder viewHolder = (ViewHolder) itemView.getTag();

        Model_Monsters monsters = (Model_Monsters) getItem(i);
        viewHolder.bind(monsters);
        return itemView;
    }

    private static class ViewHolder {
        private TextView txtMonsName, txtMonsType;
        private ImageView imgPhoto;

        ViewHolder(View view) {
            txtMonsName = view.findViewById(R.id.txt_monsName);
            txtMonsType = view.findViewById(R.id.txt_monsType);
            imgPhoto = view.findViewById(R.id.img_photo);
        }

        void bind(Model_Monsters monsters) {
            txtMonsName.setText(monsters.getMons_name());
            txtMonsType.setText(monsters.getMons_type());

            Picasso.get()
                    .load(monsters.getMons_image())
                    .error(R.drawable.user) // Gambar dari drawable jika terjadi kesalahan
                    .into(imgPhoto);

        }
    }

    @Override
    public Filter getFilter() {
        if (monstersFilter == null) {
            monstersFilter = new MonstersFilter(this, monstersList);
        }
        return monstersFilter;
    }

    private static class MonstersFilter extends Filter {
        private final AdapterMonsters adapter;
        private final List<Model_Monsters> originalList;
        private final List<Model_Monsters> filteredList;

        MonstersFilter(AdapterMonsters adapter, List<Model_Monsters> originalList) {
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

                for (final Model_Monsters monsters : originalList) {
                    if (monsters.getMons_name().toLowerCase().contains(filterPattern)) {
                        filteredList.add(monsters);
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
            adapter.filteredList.addAll((List<Model_Monsters>) filterResults.values);
            adapter.notifyDataSetChanged();
        }
    }
}
