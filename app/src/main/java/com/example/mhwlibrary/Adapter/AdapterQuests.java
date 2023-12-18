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

import com.example.mhwlibrary.Model.Model_Quests;

import java.util.ArrayList;
import java.util.List;
public class AdapterQuests extends BaseAdapter implements Filterable {
    private Context context;
    private ArrayList<Model_Quests> questsList = new ArrayList<>();
    private ArrayList<Model_Quests> filteredList = new ArrayList<>(); // Add filtered list
    private QuestsFilter questsFilter;

    public AdapterQuests(Context context, ArrayList<Model_Quests> questsList) {
        this.context = context;
        this.questsList = questsList;
        this.filteredList = new ArrayList<>(questsList); // Initialize filtered list
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

    public void setQuestsList(ArrayList<Model_Quests> questsList) {
        this.questsList = questsList;
        this.filteredList = new ArrayList<>(questsList);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView = view;

        if (itemView == null) {
            itemView = LayoutInflater.from(context)
                    .inflate(R.layout.item_quests, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(itemView);
            itemView.setTag(viewHolder);
        }

        ViewHolder viewHolder = (ViewHolder) itemView.getTag();

        Model_Quests quest = (Model_Quests) getItem(i);
        viewHolder.bind(quest);
        return itemView;
    }

    private static class ViewHolder {
        private TextView txtQname, txtQtype;
        private ImageView imgPhoto;

        ViewHolder(View view) {
            txtQname = view.findViewById(R.id.txt_qName);
            txtQtype = view.findViewById(R.id.txt_qType);
            imgPhoto = view.findViewById(R.id.img_photo);
        }

        void bind(Model_Quests quest) {
            txtQname.setText(quest.getQuest_name());
            txtQtype.setText(quest.getQuest_type());

            Picasso.get()
                    .load(quest.getQuest_image())
                    .error(R.drawable.user) // Gambar dari drawable jika terjadi kesalahan
                    .into(imgPhoto);
        }
    }

    @Override
    public Filter getFilter() {
        if (questsFilter == null) {
            questsFilter = new AdapterQuests.QuestsFilter(this, questsList);
        }
        return questsFilter;
    }

    private static class QuestsFilter extends Filter {
        private final AdapterQuests adapter;
        private final List<Model_Quests> originalList;
        private final List<Model_Quests> filteredList;

        QuestsFilter(AdapterQuests adapter, List<Model_Quests> originalList) {
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

                for (final Model_Quests quest : originalList) {
                    if (quest.getQuest_name().toLowerCase().contains(filterPattern)) {
                        filteredList.add(quest);
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
            adapter.filteredList.addAll((List<Model_Quests>) filterResults.values);
            adapter.notifyDataSetChanged();
        }
    }
}
