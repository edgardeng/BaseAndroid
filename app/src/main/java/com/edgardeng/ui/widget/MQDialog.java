package com.edgardeng.ui.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.app.Dialog;

import com.edgardeng.baseandroid.R;


/**
 *
 * @author EdgarDeng
 * @date 2014-11-16
 * @version 
 * @weibo http://weibo.com/edgardeng
 */
public class MQDialog extends Dialog  implements OnClickListener{
	
	protected final int PADDING_BUTTON 	= 15;
	protected final int PADDING_TITLE 	= 20;
	protected final int SIZE_TITLE 		= 22;
	protected final int SIZE_MESSAGE	= 18;
	protected final int SIZE_BUTTON		= 16;
	
	private static int EDIT_ID = 200;	// CONTENTS
	
	protected  int TITLE_ID 	 = 199;	
	private final int MESSAGE_ID = 198;
	
	private int COSTUME_ID = 200;	//
	
	private final int BTN_ID = 190;	//
 
	
	private RelativeLayout mainview;
	private View addedView;
	
	private TextView titletv;
	private TextView message_tv;
	
	private Context context;
	
	private List<EditText> edits;
	private List<TextView> labels;
	private List<Button> btns;
	
	protected CharSequence title;
	private CharSequence message;
	private CharSequence[] label_texts;
	private CharSequence[] btn_texts;
	
	private int label_count;
	private int btn_count;
	
	private OnButtonClickListener onbtnClicker;
	private OnBtnClickListener okClicker;
	private int dialogTag;
	
	public MQDialog(Context context) {
		super(context, R.style.MQDialog);
		this.context = context;
	}
	public MQDialog(Context context,int theme) {
		super(context,theme);
		this.context = context;
	}
	
	public MQDialog(Context context,String[] ls) {
		this(context);
		if(ls!=null){
			this.label_count = ls.length;
			this.label_texts = ls;
		}
	}
	
	public MQDialog(Context context,int theme,String[] ls) {
		super(context,theme);		
		this.context = context;
		if(ls!=null){
			this.label_count = ls.length;
			this.label_texts = ls;
		}
	}
	
	/**   */
	@Override
	public void onCreate(Bundle saved){
		super.onCreate(saved);
		initTitleMessage();
		initView();
	}
	
	public ViewGroup getContentView( ){
		return mainview;
	}

	/**  */
	@Override
	public void setTitle(CharSequence s){
		title = s;
		if(titletv!=null)
			titletv.setText(title);
	}
	

	public void setMessage(CharSequence s){
		message = s;
		if(message_tv!=null)
			message_tv.setText(message);
	}
	public void setMessage(int stringid){
		message = context.getString(stringid);
		if(message_tv!=null)
			message_tv.setText(stringid);
	}
	

	public void setButtonTexts(CharSequence[] strs){
		btn_texts = strs;
		btn_count = btn_texts.length;
	}

	public void setButtonTexts(int[] stringids){
		if(stringids!=null){
			this.btn_count = stringids.length;
			btn_texts =  new String[btn_count];
			for(int i=0;i<btn_count;i++){
				btn_texts[i] = context.getString(stringids[i]);
			}
		}
	}



	
	/**   */
	public void setButtonColor(int color){
		
	}
	/**     */
	public void setButtonBgId(int recid){
		
	}
	
	/**  label  */
	public void setLabelText(CharSequence[] ls){
		label_count = ls.length;
		label_texts = ls;
		if(labels!=null)
			for(int i = 0;i<label_count;i++){
				labels.get(i).setText(ls[i]);
			}
	}
	
	public TextView labelTextAt(int position){
		if(labels!=null)
			return labels.get(position);
		else
			return null;
	}
	public EditText editTextAt(int index){
		if(edits!=null)
			return edits.get(index);
		else
			return null;
	}
	
	public int countOfLabel(){
		return label_count;
	}
	
	public int getDialogTag(){
		return this.dialogTag;
	}
	public void setDialogTag(int tag){
		this.dialogTag = tag;
	}
	
	private void initTitleMessage(){
		mainview = new RelativeLayout(context);
		RelativeLayout.LayoutParams lp_main = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT); 
		mainview.setLayoutParams(lp_main);
		setContentView(mainview);
		
		if(title!=null){
			titletv = new TextView(context);
			titletv.setText(title);
			titletv.setTextColor(Color.BLACK);
			titletv.setTextSize(SIZE_TITLE);
			titletv.setGravity(Gravity.CENTER);
			RelativeLayout.LayoutParams lp_title = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
			lp_title.setMargins(0,PADDING_TITLE,0,PADDING_TITLE);
			titletv.setId(TITLE_ID); 
			mainview.addView(titletv, lp_title);
		}
		
		if(message!=null){
			message_tv = new TextView(context);
			message_tv.setText(message);
			message_tv.setTextSize(SIZE_MESSAGE);//context.getResources().getDimension(R.dimen.size_message_dialog)
			message_tv.setTextColor(Color.DKGRAY);
			RelativeLayout.LayoutParams lp_message = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
			lp_message.addRule(RelativeLayout.BELOW,TITLE_ID);
			lp_message.setMargins(20,0,20,20);
			message_tv.setId(--TITLE_ID);
			mainview.addView(message_tv, lp_message);
		}
	}
	
	protected void initView(){
		initEditView();
		initAddedView();
		initButtonView();
	}
	public View getAddedView() {
		return addedView;
	}
	public void setAddedView(View addedView) {
		this.addedView = addedView;
	}
	private void initAddedView(){
		if(addedView!=null){
			addedView.setId(++EDIT_ID);
			RelativeLayout.LayoutParams lp_lly = new RelativeLayout.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
			
			if(label_count!=0)
				lp_lly.addRule(RelativeLayout.BELOW, EDIT_ID-1);
			else
				lp_lly.addRule(RelativeLayout.BELOW, TITLE_ID);
			
			lp_lly.setMargins(0, 10, 0, 0);
			lp_lly.addRule(RelativeLayout.CENTER_HORIZONTAL);
			mainview.addView(addedView,lp_lly);
		}
	}
	private void initEditView(){
		if(label_count!=0){
			edits = new ArrayList<EditText>();	
			labels = new ArrayList<TextView>();
			int i = 0;
			while(i<label_count){
				TextView label = new TextView(context);

				label.setText(label_texts[i]);
				label.setTextColor(Color.BLACK);
				label.setTextSize(16);
				RelativeLayout.LayoutParams lp_label = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
				lp_label.setMargins(10,2,0,0);
				if(i!=0){
					lp_label.addRule(RelativeLayout.BELOW, EDIT_ID);
					lp_label.addRule(RelativeLayout.ALIGN_RIGHT, EDIT_ID-1);
				}else{
					if(title!=null)
						lp_label.addRule(RelativeLayout.BELOW, TITLE_ID);
				}
					
				label.setId(++EDIT_ID);
				mainview.addView(label, lp_label);
				labels.add(label);
				
				EditText edit = new EditText(context);
				RelativeLayout.LayoutParams lp_edit = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
				lp_edit.setMargins(10,2,2,0);	
				lp_edit.addRule(RelativeLayout.RIGHT_OF, EDIT_ID);
				if(i != 0)
					lp_edit.addRule(RelativeLayout.BELOW, EDIT_ID-1);
				else
					lp_edit.addRule(RelativeLayout.BELOW, TITLE_ID);
					
				edit.setId(++EDIT_ID);
				mainview.addView(edit, lp_edit );				
				edits.add(edit);
				
				i++;
			}
		}
	}
	
	private void initButtonView(){
		
		// text for buttons
		if(btn_texts == null){
			btn_texts = new String[]{context.getString(R.string.dialog_action_cancel),context.getString(R.string.dialog_action_ok)};
			btn_count = 2;
		}
		
		int aboveid = 0;//
		if(label_count == 0 && this.addedView == null)
			aboveid  = TITLE_ID;
		else
			aboveid  = EDIT_ID;
		
		if(btn_texts!= null){
			
			//-----------------------------------   -----------------------
			LinearLayout btn_ll= new LinearLayout(context);
			btn_ll.setId(++EDIT_ID);
			RelativeLayout.LayoutParams lp_lly = new RelativeLayout.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
			lp_lly.addRule(RelativeLayout.BELOW, aboveid); 
			lp_lly.setMargins(0, 10, 0, 0);
			btn_ll.setHorizontalGravity(LinearLayout.HORIZONTAL);
			mainview.addView(btn_ll,lp_lly);

			//-----------------------------------   -----------------------
			View h_line = new View(context);
			h_line.setBackgroundColor(Color.GRAY);
			RelativeLayout.LayoutParams lp_line = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1); 
			lp_line.addRule(RelativeLayout.BELOW, aboveid); 
			lp_line.setMargins(0, 10, 0, 0);
			mainview.addView(h_line,lp_line);
			
			//-----------------------------------   -----------------------
			
			btns = new ArrayList<Button>();
			int i = 0;
			while(i<btn_count){
				
				RelativeLayout btn_rl= new RelativeLayout(context);
				LinearLayout.LayoutParams lp_rl = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT); 
				lp_rl.weight = 1;
				btn_ll.addView(btn_rl,lp_rl);
				
				
				Button btn = new Button(context);
				btn = new Button(context); 
				btn.setText(btn_texts[i]);
				btn.setTextSize(18);
				btn.setTextColor(0xff2090ff);
				btn.setPadding(0,10,0,10);
				btn.setOnClickListener(this);
				btn.setId(BTN_ID - i);
				RelativeLayout.LayoutParams lp_btn = new RelativeLayout.LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT); 
				
				switch(btn_count){
					case 1:
						btn.setBackgroundResource(R.drawable.dialog_bottom);
						break;
					case 2:
					case 3:
						if(i ==0)
							btn.setBackgroundResource(R.drawable.dialog_bottom_left);
						else if(i==btn_count-1)
							btn.setBackgroundResource(R.drawable.dialog_bottom_right);
						else
							btn.setBackgroundResource(R.drawable.dialog_bottom_rect);
						break;
				}
				
				btn_rl.addView(btn,lp_btn);
//				mainview.addView(btn,lp_btn);
				btns.add(btn);
				i++;
				
				//-----------------------------------  -----------------------
				if(i<btn_count){
					View v_line = new View(context);
					v_line.setBackgroundColor(Color.GRAY);
					RelativeLayout.LayoutParams lp_v = new RelativeLayout.LayoutParams(1,ViewGroup.LayoutParams.WRAP_CONTENT);
//							1,ViewGroup.LayoutParams.WRAP_CONTENT); 
//					lp_v.addRule(RelativeLayout.RIGHT_OF, BTN_ID - i+1);
					lp_v.addRule(RelativeLayout.ALIGN_TOP, BTN_ID - i+1);
					lp_v.addRule(RelativeLayout.ALIGN_BOTTOM, BTN_ID - i+1);
					lp_v.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
					btn_rl.addView(v_line,lp_v);
//					mainview.addView(v_line,lp_v);
				}
				
			}
		}
		
	}


	@Override
	public void onClick(View v) {
		try{
			int index =  BTN_ID - v.getId();
			onClickAt(index);
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	protected void onClickAt(int index){
		//
		List<String> edittexts = null;
		if(edits!=null){
			edittexts = new ArrayList<String>();
			for(EditText t:edits){
				edittexts.add(t.getText().toString());
			}
			for(EditText t:edits){
				t.setText("");
			}
		}
		//
		if(onbtnClicker!=null){
			onbtnClicker.OnButtonClick(this,index, edittexts); 
		}else if(okClicker!=null){
			okClicker.onBtnClick(this, index==1, edittexts);
		}else
			this.dismiss(); 
	}
	
	public OnButtonClickListener getOnButtonClickListener( ){
		return onbtnClicker;
	}
	public void setOnButtonClickListener(OnButtonClickListener listener){
		this.onbtnClicker = listener;
	}
	
	public OnBtnClickListener getOnBtnClickListener( ){
		return okClicker;
	}
	public void setOnBtnClickListener(OnBtnClickListener listener){
		this.okClicker = listener;
	}



	public interface OnBtnClickListener{
		public void onBtnClick(MQDialog dialog, boolean isPositiveBtn,List<String> edit_texts);}
	public interface OnButtonClickListener{
		public void OnButtonClick(MQDialog dialog,int btnPosition,List<String> edit_texts);
	}
	
}

