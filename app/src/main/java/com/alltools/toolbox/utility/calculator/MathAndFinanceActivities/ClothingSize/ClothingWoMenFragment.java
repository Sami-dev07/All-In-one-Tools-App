package com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.ClothingSize;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.ClothSize.Clothing;
import com.alltools.toolbox.utility.calculator.R;

public class ClothingWoMenFragment extends Fragment implements Clothing {
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.clothing_women_size, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewByIds(view);
        initParams();
    }

    private void findViewByIds(View view) {
        recyclerView = view.findViewById(R.id.rec_cloth_women);
    }

    private void initParams() {
        TableViewAdapter adapter = new TableViewAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    public static class TableViewAdapter extends RecyclerView.Adapter<TableViewAdapter.MyViewHolder> {
        private final LayoutInflater inflater;

        public TableViewAdapter(Context context) {
            this.inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.row_common_size, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            if (position == 0) {
                setupHeaderRow(holder);
            } else {
                setupContentRow(holder, position);
            }
        }

        @Override
        public int getItemCount() {
            return Clothing.WOMEN_CLOTHING_US.length;
        }

        @SuppressLint("SetTextI18n")
        private void setupHeaderRow(MyViewHolder holder) {
            int headerBackground = R.drawable.table_header_cell_bg;
            int headerTextColor = holder.itemView.getResources().getColor(R.color.tools_button_color);

            setBackgroundAndTextColor(holder, headerBackground, headerTextColor);

            holder.tvContent1.setText("US");
            holder.tvContent2.setText("UK");
            holder.tvContent3.setText("FR");
            holder.tvContent4.setText("DE");
            holder.tvContent5.setText("SML");
        }

        private void setupContentRow(MyViewHolder holder, int position) {
            int contentBackground = R.drawable.table_content_cell_bg;

            setBackground(holder, contentBackground);

            holder.tvContent1.setText(Clothing.WOMEN_CLOTHING_US[position]);
            holder.tvContent2.setText(Clothing.WOMEN_CLOTHING_UK[position]);
            holder.tvContent3.setText(Clothing.WOMEN_CLOTHING_FR[position]);
            holder.tvContent4.setText(Clothing.WOMEN_CLOTHING_DE[position]);
            holder.tvContent5.setText(Clothing.WOMEN_CLOTHING_SML[position]);
        }

        private void setBackgroundAndTextColor(MyViewHolder holder, int backgroundResource, int textColor) {
            setBackground(holder, backgroundResource);
            setTextColor(holder, textColor);
        }

        private void setBackground(MyViewHolder holder, int backgroundResource) {
            holder.tvContent1.setBackgroundResource(backgroundResource);
            holder.tvContent2.setBackgroundResource(backgroundResource);
            holder.tvContent3.setBackgroundResource(backgroundResource);
            holder.tvContent4.setBackgroundResource(backgroundResource);
            holder.tvContent5.setBackgroundResource(backgroundResource);
        }

        private void setTextColor(MyViewHolder holder, int textColor) {
            holder.tvContent1.setTextColor(textColor);
            holder.tvContent2.setTextColor(textColor);
            holder.tvContent3.setTextColor(textColor);
            holder.tvContent4.setTextColor(textColor);
            holder.tvContent5.setTextColor(textColor);
        }

        public static class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tvContent1;
            TextView tvContent2;
            TextView tvContent3;
            TextView tvContent4;
            TextView tvContent5;

            public MyViewHolder(View itemView) {
                super(itemView);
                tvContent1 = itemView.findViewById(R.id.tv_content_1);
                tvContent2 = itemView.findViewById(R.id.tv_content_2);
                tvContent3 = itemView.findViewById(R.id.tv_content_3);
                tvContent4 = itemView.findViewById(R.id.tv_content_4);
                tvContent5 = itemView.findViewById(R.id.tv_content_5);
            }
        }
    }
}
