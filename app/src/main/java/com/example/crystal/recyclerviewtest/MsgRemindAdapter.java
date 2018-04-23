package com.example.crystal.recyclerviewtest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.ref.PhantomReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Crystal on 2018/4/22.
 */

public class MsgRemindAdapter extends RecyclerView.Adapter<MsgRemindAdapter.RemindViewHolder> implements ItemSlideHelper.Callback {

    private Context mContext;

    private List<MsgVo> mData = new ArrayList<>();

    private RecyclerView mRecylerView;

    public MsgRemindAdapter(Context context,List<MsgVo> mDatas){
        this.mContext = context;
        this.mData = mDatas;
    }

    @Override
    public RemindViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_msg_remind,parent,false);
        return new RemindViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RemindViewHolder holder, int position) {
        if(mData.get(position).isChecked()){
            holder.msgRemindPoint.setBackgroundResource(R.drawable.shape_remind_point_gray);
        }else {
            holder.msgRemindPoint.setBackgroundResource(R.drawable.shape_remind_point_theme);
        }
        holder.tvRemindTitle.setText(mData.get(position).getTitle());
        holder.tvRemindContent.setText(mData.get(position).getContent());

        holder.llMsgRemindMain.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                addData(mData.size());
            }
        });
        holder.tvMsgRemindCheck.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                mData.get(holder.getAdapterPosition()).setChecked(true);
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
        holder.tvMsgRemindDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeData(holder.getAdapterPosition());
            }
        });


    }

    @Override
    public int getItemCount() {
        if(mData!=null){
            return mData.size();
        }
        return 0;
    }

    private void addData(int position) {
        MsgVo vo = new MsgVo();
        if(position%2==1){
            vo.setChecked(false);
            vo.setTitle("隔壁得二蛋");
            vo.setContent("对方撤回了一条消息");
        }else {
            vo.setChecked(true);
            vo.setTitle("Zircal");
            vo.setContent("LET'S GO");
        }
        mData.add(position,vo);
        notifyItemInserted(position);
    }
    private void removeData(int position){
        mData.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecylerView = recyclerView;
        mRecylerView.addOnItemTouchListener(new ItemSlideHelper(mRecylerView.getContext(),this));
    }

    @Override
    public int getHorizontalRange(RecyclerView.ViewHolder holder) {
        if(holder.itemView instanceof LinearLayout){
            ViewGroup viewGroup = (ViewGroup) holder.itemView;
            return viewGroup.getChildAt(1).getLayoutParams().width+
                    viewGroup.getChildAt(2).getLayoutParams().width;
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder getChildViewHolder(View childView) {
        return mRecylerView.getChildViewHolder(childView);
    }

    @Override
    public View findTargetView(float x, float y) {
        return mRecylerView.findChildViewUnder(x,y);
    }


    public class RemindViewHolder extends RecyclerView.ViewHolder{
        @InjectView(R.id.msg_remind_point)
        View msgRemindPoint;
        @InjectView(R.id.tv_remind_title)
        TextView tvRemindTitle;
        @InjectView(R.id.tv_remind_content)
        TextView tvRemindContent;
        @InjectView(R.id.ll_msg_remind_main)
        LinearLayout llMsgRemindMain;
        @InjectView(R.id.tv_msg_remind_check)
        TextView tvMsgRemindCheck;
        @InjectView(R.id.tv_msg_remind_delete)
        TextView tvMsgRemindDelete;

        public RemindViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this,itemView);
        }
    }
}
