package com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.ClothSize;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alltools.toolbox.utility.calculator.R;

import java.util.Objects;


public class ClothingMenFragment extends Fragment implements Clothing {
   TableViewAdapter1 adapter;
    RecyclerView recyclerView;


    public ClothingMenFragment() {
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override

    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        try {
            return layoutInflater.inflate(R.layout.fragment_clothing_men, viewGroup, false);
        } catch (Exception unused) {
            return super.onCreateView(layoutInflater, viewGroup, bundle);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle bundle) {
        try {
            super.onViewCreated(view, bundle);
            findViewByIds();
            initParams();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initParams() {
        this.adapter = new TableViewAdapter1(getActivity());
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.recyclerView.setAdapter(this.adapter);
    }

    private void findViewByIds() {
        this.recyclerView = (RecyclerView) requireActivity().findViewById(R.id.rec_cloth_men);
    }

    public class TableViewAdapter1 extends RecyclerView.Adapter<TableViewAdapter1.ViewHolder1> {
        Context context;
        private final LayoutInflater inflater;

        private TableViewAdapter1(Context context) {
            this.inflater = LayoutInflater.from(context);
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder1 onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new ViewHolder1(this.inflater.inflate(R.layout.row_common_size, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder1 myViewHolder, int i) {
            if (i == 0) {
                myViewHolder.tvContent1.setBackgroundResource(R.drawable.table_header_cell_bg);
                myViewHolder.tvContent2.setBackgroundResource(R.drawable.table_header_cell_bg);
                myViewHolder.tvContent3.setBackgroundResource(R.drawable.table_header_cell_bg);
                myViewHolder.tvContent4.setBackgroundResource(R.drawable.table_header_cell_bg);
                myViewHolder.tvContent5.setBackgroundResource(R.drawable.table_header_cell_bg);
                myViewHolder.tvContent1.setTextColor(ClothingMenFragment.this.getResources().getColor(R.color.tools_button_color));
                myViewHolder.tvContent2.setTextColor(ClothingMenFragment.this.getResources().getColor(R.color.tools_button_color));
                myViewHolder.tvContent3.setTextColor(ClothingMenFragment.this.getResources().getColor(R.color.tools_button_color));
                myViewHolder.tvContent4.setTextColor(ClothingMenFragment.this.getResources().getColor(R.color.tools_button_color));
                myViewHolder.tvContent5.setTextColor(ClothingMenFragment.this.getResources().getColor(R.color.tools_button_color));
                myViewHolder.tvContent1.setText("US");
                myViewHolder.tvContent2.setText("UK");
                myViewHolder.tvContent3.setText("FR");
                myViewHolder.tvContent4.setText("IT");
                myViewHolder.tvContent5.setText("SML");
                return;
            }
            myViewHolder.tvContent1.setBackgroundResource(R.drawable.table_content_cell_bg);
            myViewHolder.tvContent2.setBackgroundResource(R.drawable.table_content_cell_bg);
            myViewHolder.tvContent3.setBackgroundResource(R.drawable.table_content_cell_bg);
            myViewHolder.tvContent4.setBackgroundResource(R.drawable.table_content_cell_bg);
            myViewHolder.tvContent5.setBackgroundResource(R.drawable.table_content_cell_bg);
            myViewHolder.tvContent1.setText(Clothing.MEN_CLOTHING_US[i]);
            myViewHolder.tvContent2.setText(Clothing.MEN_CLOTHING_UK[i]);
            myViewHolder.tvContent3.setText(Clothing.MEN_CLOTHING_FR[i]);
            myViewHolder.tvContent4.setText(Clothing.MEN_CLOTHING_IT[i]);
            myViewHolder.tvContent5.setText(Clothing.MEN_CLOTHING_SML[i]);
        }

        @Override
        public int getItemCount() {
            return Clothing.MEN_CLOTHING_US.length;
        }

        public class ViewHolder1 extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView tvContent1;
            TextView tvContent2;
            TextView tvContent3;
            TextView tvContent4;
            TextView tvContent5;

            @Override
            public void onClick(View view) {
            }

            public ViewHolder1(View view) {
                super(view);
                this.tvContent1 = (TextView) view.findViewById(R.id.tv_content_1);
                this.tvContent2 = (TextView) view.findViewById(R.id.tv_content_2);
                this.tvContent3 = (TextView) view.findViewById(R.id.tv_content_3);
                this.tvContent4 = (TextView) view.findViewById(R.id.tv_content_4);
                this.tvContent5 = (TextView) view.findViewById(R.id.tv_content_5);
            }
        }
    }


}