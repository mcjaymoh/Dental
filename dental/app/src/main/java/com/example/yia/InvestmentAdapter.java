package com.example.yia;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class InvestmentAdapter extends ArrayAdapter<String> {

	private final Context context;
	private final String[] investmenttitle;
	private final String[] investmentcategory;
	private final String[] investmentid;
	private final String[] investmentdetails;
	public InvestmentAdapter(Context context, String[] investmenttitle, String[] investmentcategory, String[] investmentid, String[] investmentdetails) {
		super(context, R.layout.gridinvestments, investmenttitle);
		// TODO Auto-generated constructor stub
		
		this.context=context;
		this.investmenttitle = investmenttitle;
		this.investmentcategory = investmentcategory;
		this.investmentid = investmentid;
		this.investmentdetails = investmentdetails;
	}
	
	public View getView(int position,View view,ViewGroup parent) {
		LayoutInflater inflater=LayoutInflater.from(getContext());
		View rowView=inflater.inflate(R.layout.gridinvestments, null,true);

		LinearLayout ll_newsbg = (LinearLayout) rowView.findViewById(R.id.ll_investmentbg);
		if(position%2 == 0){
			ll_newsbg.setBackgroundResource(R.drawable.shadoweffect2);
		}
		
		TextView tv_newstopic = (TextView) rowView.findViewById(R.id.tv_investmenttitle);
		tv_newstopic.setText(investmenttitle[position]);
		if(position == 0){
			tv_newstopic.setTextColor(Color.parseColor("#001f3f"));
		}if(position == 2){
			tv_newstopic.setTextColor(Color.parseColor("#3d9970"));
		}if(position == 1){
			tv_newstopic.setTextColor(Color.parseColor("#39cccc"));
		}
		TextView tv_newspostdate = (TextView) rowView.findViewById(R.id.tv_investmentcategory);
		tv_newspostdate.setText(investmentcategory[position]);
		
		TextView tv_newsid = (TextView) rowView.findViewById(R.id.tv_investmentid);
		tv_newsid.setText(investmentid[position]);
		
		TextView tv_newsdetails = (TextView) rowView.findViewById(R.id.tv_investmentdetails);
		tv_newsdetails.setText(investmentdetails[position]);
		
		return rowView;
		
	};
	
}
