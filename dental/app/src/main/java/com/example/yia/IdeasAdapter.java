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

public class IdeasAdapter extends ArrayAdapter<String> {

	private final Context context;
	
	private final String[] ideaid;
	private final String[] ideatitle;
	private final String[] ideadetails;
	public IdeasAdapter(Context context, String[] ideaid, String[] ideatitle, String[] ideadetails) {
		super(context, R.layout.gridideas, ideatitle);
		// TODO Auto-generated constructor stub
		
		this.context=context;
		this.ideaid = ideaid;
		this.ideatitle = ideatitle;
		this.ideadetails = ideadetails;
	}
	
	public View getView(int position,View view,ViewGroup parent) {
		LayoutInflater inflater=LayoutInflater.from(getContext());
		View rowView=inflater.inflate(R.layout.gridideas, null,true);

		LinearLayout ll_ideabg = (LinearLayout) rowView.findViewById(R.id.ll_ideabg);
		if(position%2 == 0){
			ll_ideabg.setBackgroundResource(R.drawable.shadoweffect2);
		}
		
		TextView tv_ideatitle = (TextView) rowView.findViewById(R.id.tv_ideatitle);
		tv_ideatitle.setText(ideatitle[position]);
		if(position == 0){
			tv_ideatitle.setTextColor(Color.parseColor("#001f3f"));
		}if(position == 2){
			tv_ideatitle.setTextColor(Color.parseColor("#3d9970"));
		}if(position == 1){
			tv_ideatitle.setTextColor(Color.parseColor("#39cccc"));
		}
		
		TextView tv_ideaid = (TextView) rowView.findViewById(R.id.tv_ideaid);
		tv_ideaid.setText(ideaid[position]);
		
		TextView tv_ideadetails = (TextView) rowView.findViewById(R.id.tv_ideadetails);
		tv_ideadetails.setText(ideadetails[position]);
		
		return rowView;
		
	};
	
}
