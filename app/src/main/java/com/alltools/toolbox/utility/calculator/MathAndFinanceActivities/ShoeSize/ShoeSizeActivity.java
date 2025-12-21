package com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.ShoeSize;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import com.alltools.toolbox.utility.calculator.R;

public class ShoeSizeActivity extends AppCompatActivity implements ShoeSize {
    BoyShoeAdapter boyShoeAdapter;
    GirlsShoeAdapter girlsShoeAdapter;
    MenShoeAdapter menShoeAdapter;
    RecyclerView rvBoy;
    RecyclerView rvGirl;
    RecyclerView rvMen;
    RecyclerView rvWoman;
    Toolbar toolbar;
    WomenShoeAdapter womenShoeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_shoe_size);
            findAllViewById();
            setRecyclerViewParams();
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }
    }





    private void setRecyclerViewParams() {
        this.womenShoeAdapter = new WomenShoeAdapter(this);
        this.rvWoman.setLayoutManager(new LinearLayoutManager(this));
        this.rvWoman.setAdapter(this.womenShoeAdapter);
        this.menShoeAdapter = new MenShoeAdapter(this);
        this.rvMen.setLayoutManager(new LinearLayoutManager(this));
        this.rvMen.setAdapter(this.menShoeAdapter);
        this.boyShoeAdapter = new BoyShoeAdapter(this);
        this.rvBoy.setLayoutManager(new LinearLayoutManager(this));
        this.rvBoy.setAdapter(this.boyShoeAdapter);
        this.girlsShoeAdapter = new GirlsShoeAdapter(this);
        this.rvGirl.setLayoutManager(new LinearLayoutManager(this));
        this.rvGirl.setAdapter(this.girlsShoeAdapter);
    }

    private void findAllViewById() {
        this.rvWoman = (RecyclerView) findViewById(R.id.rv_shoe_size_women);
        this.rvMen = (RecyclerView) findViewById(R.id.rv_shoe_size_men);
        this.rvBoy = (RecyclerView) findViewById(R.id.rv_shoe_size_boy);
        this.rvGirl = (RecyclerView) findViewById(R.id.rv_shoe_size_girl);
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            setResult(-1, new Intent());
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        setResult(-1, new Intent());
        finish();
    }



    public class WomenShoeAdapter extends RecyclerView.Adapter<WomenShoeAdapter.MyViewHolder> {
        Context context;
        private LayoutInflater inflater;

        private WomenShoeAdapter(Context context) {
            this.inflater = LayoutInflater.from(context);
            this.context = context;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new MyViewHolder(this.inflater.inflate(R.layout.row_shoe_size, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
            if (i == 0) {
                myViewHolder.tvUS.setBackgroundResource(R.drawable.table_header_cell_bg);
                myViewHolder.tvEuro.setBackgroundResource(R.drawable.table_header_cell_bg);
                myViewHolder.tvUk.setBackgroundResource(R.drawable.table_header_cell_bg);
                myViewHolder.tvInches.setBackgroundResource(R.drawable.table_header_cell_bg);
                myViewHolder.tvCm.setBackgroundResource(R.drawable.table_header_cell_bg);
                myViewHolder.tvUS.setTextColor(ShoeSizeActivity.this.getResources().getColor(R.color.tools_button_color));
                myViewHolder.tvEuro.setTextColor(ShoeSizeActivity.this.getResources().getColor(R.color.tools_button_color));
                myViewHolder.tvUk.setTextColor(ShoeSizeActivity.this.getResources().getColor(R.color.tools_button_color));
                myViewHolder.tvInches.setTextColor(ShoeSizeActivity.this.getResources().getColor(R.color.tools_button_color));
                myViewHolder.tvCm.setTextColor(ShoeSizeActivity.this.getResources().getColor(R.color.tools_button_color));
                myViewHolder.tvTitle.setText("Women's Shoe Size");
                myViewHolder.tvUS.setText("US");
                myViewHolder.tvEuro.setText("EURO");
                myViewHolder.tvUk.setText("UK");
                myViewHolder.tvInches.setText("INCHES");
                myViewHolder.tvCm.setText("CM");
                return;
            }
            myViewHolder.tvTitle.setVisibility(View.GONE);
            myViewHolder.tvUS.setBackgroundResource(R.drawable.table_content_cell_bg);
            myViewHolder.tvEuro.setBackgroundResource(R.drawable.table_content_cell_bg);
            myViewHolder.tvUk.setBackgroundResource(R.drawable.table_content_cell_bg);
            myViewHolder.tvInches.setBackgroundResource(R.drawable.table_content_cell_bg);
            myViewHolder.tvCm.setBackgroundResource(R.drawable.table_content_cell_bg);
            myViewHolder.tvUS.setText(ShoeSize.US[i]);
            myViewHolder.tvEuro.setText(ShoeSize.EURO[i]);
            myViewHolder.tvUk.setText(ShoeSize.UK[i]);
            myViewHolder.tvInches.setText(ShoeSize.INCHES[i]);
            myViewHolder.tvCm.setText(ShoeSize.CM[i]);
        }

        @Override
        public int getItemCount() {
            return ShoeSize.US.length;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView tvCm;
            TextView tvEuro;
            TextView tvInches;
            TextView tvTitle;
            TextView tvUS;
            TextView tvUk;

            @Override
            public void onClick(View view) {
            }

            public MyViewHolder(View view) {
                super(view);
                this.tvTitle = (TextView) view.findViewById(R.id.tv_title);
                this.tvUS = (TextView) view.findViewById(R.id.tv_us);
                this.tvEuro = (TextView) view.findViewById(R.id.tv_euro);
                this.tvUk = (TextView) view.findViewById(R.id.tv_uk);
                this.tvInches = (TextView) view.findViewById(R.id.tv_inches);
                this.tvCm = (TextView) view.findViewById(R.id.tv_cm);
            }
        }
    }

    public class MenShoeAdapter extends RecyclerView.Adapter<MenShoeAdapter.MyViewHolder> {
        Context context;
        private LayoutInflater inflater;

        private MenShoeAdapter(Context context) {
            this.inflater = LayoutInflater.from(context);
            this.context = context;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new MyViewHolder(this.inflater.inflate(R.layout.row_shoe_size, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
            if (i == 0) {
                myViewHolder.tvUS.setBackgroundResource(R.drawable.table_header_cell_bg);
                myViewHolder.tvEuro.setBackgroundResource(R.drawable.table_header_cell_bg);
                myViewHolder.tvUk.setBackgroundResource(R.drawable.table_header_cell_bg);
                myViewHolder.tvInches.setBackgroundResource(R.drawable.table_header_cell_bg);
                myViewHolder.tvCm.setBackgroundResource(R.drawable.table_header_cell_bg);
                myViewHolder.tvUS.setTextColor(ShoeSizeActivity.this.getResources().getColor(R.color.tools_button_color));
                myViewHolder.tvEuro.setTextColor(ShoeSizeActivity.this.getResources().getColor(R.color.tools_button_color));
                myViewHolder.tvUk.setTextColor(ShoeSizeActivity.this.getResources().getColor(R.color.tools_button_color));
                myViewHolder.tvInches.setTextColor(ShoeSizeActivity.this.getResources().getColor(R.color.tools_button_color));
                myViewHolder.tvCm.setTextColor(ShoeSizeActivity.this.getResources().getColor(R.color.tools_button_color));
                myViewHolder.tvTitle.setText("Men's Shoe Size");
                myViewHolder.tvUS.setText("US");
                myViewHolder.tvEuro.setText("EURO");
                myViewHolder.tvUk.setText("UK");
                myViewHolder.tvInches.setText("INCHES");
                myViewHolder.tvCm.setText("CM");
                return;
            }
            myViewHolder.tvTitle.setVisibility(View.GONE);
            myViewHolder.tvUS.setBackgroundResource(R.drawable.table_content_cell_bg);
            myViewHolder.tvEuro.setBackgroundResource(R.drawable.table_content_cell_bg);
            myViewHolder.tvUk.setBackgroundResource(R.drawable.table_content_cell_bg);
            myViewHolder.tvInches.setBackgroundResource(R.drawable.table_content_cell_bg);
            myViewHolder.tvCm.setBackgroundResource(R.drawable.table_content_cell_bg);
            myViewHolder.tvUS.setText(ShoeSize.MENS_US[i]);
            myViewHolder.tvEuro.setText(ShoeSize.MENS_EURO[i]);
            myViewHolder.tvUk.setText(ShoeSize.MENS_UK[i]);
            myViewHolder.tvInches.setText(ShoeSize.MENS_INCHES[i]);
            myViewHolder.tvCm.setText(ShoeSize.MENS_CM[i]);
        }

        @Override
        public int getItemCount() {
            return ShoeSize.MENS_US.length;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView tvCm;
            TextView tvEuro;
            TextView tvInches;
            TextView tvTitle;
            TextView tvUS;
            TextView tvUk;

            @Override
            public void onClick(View view) {
            }

            public MyViewHolder(View view) {
                super(view);
                this.tvTitle = (TextView) view.findViewById(R.id.tv_title);
                this.tvUS = (TextView) view.findViewById(R.id.tv_us);
                this.tvEuro = (TextView) view.findViewById(R.id.tv_euro);
                this.tvUk = (TextView) view.findViewById(R.id.tv_uk);
                this.tvInches = (TextView) view.findViewById(R.id.tv_inches);
                this.tvCm = (TextView) view.findViewById(R.id.tv_cm);
            }
        }
    }

    public class BoyShoeAdapter extends RecyclerView.Adapter<BoyShoeAdapter.MyViewHolder> {
        Context context;
        private LayoutInflater inflater;

        private BoyShoeAdapter(Context context) {
            this.inflater = LayoutInflater.from(context);
            this.context = context;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new MyViewHolder(this.inflater.inflate(R.layout.row_shoe_size, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
            if (i == 0) {
                myViewHolder.tvUS.setBackgroundResource(R.drawable.table_header_cell_bg);
                myViewHolder.tvEuro.setBackgroundResource(R.drawable.table_header_cell_bg);
                myViewHolder.tvUk.setBackgroundResource(R.drawable.table_header_cell_bg);
                myViewHolder.tvInches.setBackgroundResource(R.drawable.table_header_cell_bg);
                myViewHolder.tvCm.setBackgroundResource(R.drawable.table_header_cell_bg);
                myViewHolder.tvUS.setTextColor(ShoeSizeActivity.this.getResources().getColor(R.color.tools_button_color));
                myViewHolder.tvEuro.setTextColor(ShoeSizeActivity.this.getResources().getColor(R.color.tools_button_color));
                myViewHolder.tvUk.setTextColor(ShoeSizeActivity.this.getResources().getColor(R.color.tools_button_color));
                myViewHolder.tvInches.setTextColor(ShoeSizeActivity.this.getResources().getColor(R.color.tools_button_color));
                myViewHolder.tvCm.setTextColor(ShoeSizeActivity.this.getResources().getColor(R.color.tools_button_color));
                myViewHolder.tvTitle.setText("Boys's Shoe Size");
                myViewHolder.tvUS.setText("US");
                myViewHolder.tvEuro.setText("EURO");
                myViewHolder.tvUk.setText("UK");
                myViewHolder.tvInches.setText("INCHES");
                myViewHolder.tvCm.setText("CM");
                return;
            }
            myViewHolder.tvTitle.setVisibility(View.GONE);
            myViewHolder.tvUS.setBackgroundResource(R.drawable.table_content_cell_bg);
            myViewHolder.tvEuro.setBackgroundResource(R.drawable.table_content_cell_bg);
            myViewHolder.tvUk.setBackgroundResource(R.drawable.table_content_cell_bg);
            myViewHolder.tvInches.setBackgroundResource(R.drawable.table_content_cell_bg);
            myViewHolder.tvCm.setBackgroundResource(R.drawable.table_content_cell_bg);
            myViewHolder.tvUS.setText(ShoeSize.BOYS_US[i]);
            myViewHolder.tvEuro.setText(ShoeSize.BOYS_EURO[i]);
            myViewHolder.tvUk.setText(ShoeSize.BOYS_UK[i]);
            myViewHolder.tvInches.setText(ShoeSize.BOYS_INCHES[i]);
            myViewHolder.tvCm.setText(ShoeSize.BOYS_CM[i]);
        }

        @Override
        public int getItemCount() {
            return ShoeSize.MENS_US.length;
        }


        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView tvCm;
            TextView tvEuro;
            TextView tvInches;
            TextView tvTitle;
            TextView tvUS;
            TextView tvUk;

            @Override
            public void onClick(View view) {
            }

            public MyViewHolder(View view) {
                super(view);
                this.tvTitle = (TextView) view.findViewById(R.id.tv_title);
                this.tvUS = (TextView) view.findViewById(R.id.tv_us);
                this.tvEuro = (TextView) view.findViewById(R.id.tv_euro);
                this.tvUk = (TextView) view.findViewById(R.id.tv_uk);
                this.tvInches = (TextView) view.findViewById(R.id.tv_inches);
                this.tvCm = (TextView) view.findViewById(R.id.tv_cm);
            }
        }
    }

    public class GirlsShoeAdapter extends RecyclerView.Adapter<GirlsShoeAdapter.MyViewHolder> {
        Context context;
        private LayoutInflater inflater;

        private GirlsShoeAdapter(Context context) {
            this.inflater = LayoutInflater.from(context);
            this.context = context;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new MyViewHolder(this.inflater.inflate(R.layout.row_shoe_size, viewGroup, false));
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
            if (i == 0) {
                myViewHolder.tvUS.setBackgroundResource(R.drawable.table_header_cell_bg);
                myViewHolder.tvEuro.setBackgroundResource(R.drawable.table_header_cell_bg);
                myViewHolder.tvUk.setBackgroundResource(R.drawable.table_header_cell_bg);
                myViewHolder.tvInches.setBackgroundResource(R.drawable.table_header_cell_bg);
                myViewHolder.tvCm.setBackgroundResource(R.drawable.table_header_cell_bg);
                myViewHolder.tvUS.setTextColor(ShoeSizeActivity.this.getResources().getColor(R.color.tools_button_color));
                myViewHolder.tvEuro.setTextColor(ShoeSizeActivity.this.getResources().getColor(R.color.tools_button_color));
                myViewHolder.tvUk.setTextColor(ShoeSizeActivity.this.getResources().getColor(R.color.tools_button_color));
                myViewHolder.tvInches.setTextColor(ShoeSizeActivity.this.getResources().getColor(R.color.tools_button_color));
                myViewHolder.tvCm.setTextColor(ShoeSizeActivity.this.getResources().getColor(R.color.tools_button_color));
                myViewHolder.tvTitle.setText("Girl's Shoe Size");
                myViewHolder.tvUS.setText("US");
                myViewHolder.tvEuro.setText("EURO");
                myViewHolder.tvUk.setText("UK");
                myViewHolder.tvInches.setText("INCHES");
                myViewHolder.tvCm.setText("CM");
                return;
            }
            myViewHolder.tvTitle.setVisibility(View.GONE);
            myViewHolder.tvUS.setBackgroundResource(R.drawable.table_content_cell_bg);
            myViewHolder.tvEuro.setBackgroundResource(R.drawable.table_content_cell_bg);
            myViewHolder.tvUk.setBackgroundResource(R.drawable.table_content_cell_bg);
            myViewHolder.tvInches.setBackgroundResource(R.drawable.table_content_cell_bg);
            myViewHolder.tvCm.setBackgroundResource(R.drawable.table_content_cell_bg);
            myViewHolder.tvUS.setText(ShoeSize.GIRLS_US[i]);
            myViewHolder.tvEuro.setText(ShoeSize.GIRLS_EURO[i]);
            myViewHolder.tvUk.setText(ShoeSize.GIRLS_UK[i]);
            myViewHolder.tvInches.setText(ShoeSize.GIRLS_INCHES[i]);
            myViewHolder.tvCm.setText(ShoeSize.GIRLS_CM[i]);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return ShoeSize.MENS_US.length;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* loaded from: classes.dex */
        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView tvCm;
            TextView tvEuro;
            TextView tvInches;
            TextView tvTitle;
            TextView tvUS;
            TextView tvUk;

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
            }

            public MyViewHolder(View view) {
                super(view);
                this.tvTitle = (TextView) view.findViewById(R.id.tv_title);
                this.tvUS = (TextView) view.findViewById(R.id.tv_us);
                this.tvEuro = (TextView) view.findViewById(R.id.tv_euro);
                this.tvUk = (TextView) view.findViewById(R.id.tv_uk);
                this.tvInches = (TextView) view.findViewById(R.id.tv_inches);
                this.tvCm = (TextView) view.findViewById(R.id.tv_cm);
            }
        }

}
}