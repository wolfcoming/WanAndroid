package com.czy.lib_log.printer;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.czy.lib_base.utils.display.DisplayInfoUtils;
import com.czy.lib_log.HiLog;
import com.czy.lib_log.HiLogConfig;
import com.czy.lib_log.HiLogManager;
import com.czy.lib_log.HiLogMo;
import com.czy.lib_log.HiLogType;
import com.czy.lib_log.R;

import java.util.ArrayList;
import java.util.List;

public class HiViewPrinter implements HiLogPrinter {

    private RecyclerView recyclerView;
    private LogAdapter adapter;


    public HiViewPrinter(Activity activity) {
        if(!HiLogManager.getInstance().getConfig().enable()){
            return;
        }
        if (activity == null) return;
        FrameLayout rootView = activity.findViewById(android.R.id.content);
        recyclerView = new RecyclerView(activity);
        adapter = new LogAdapter();
        adapter.addAll(HiLog.historyLogs);
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        //添加一个右下角按钮
        addFlotingView(rootView);
    }

    private void addFlotingView(FrameLayout rootView) {
        TextView floatingView = new TextView(rootView.getContext());
        floatingView.setBackgroundColor(Color.GREEN);
        floatingView.setPadding(10, 20, 10, 20);
        floatingView.setText("HiLog");
        floatingView.setTextColor(Color.BLACK);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.BOTTOM | Gravity.END;
        layoutParams.bottomMargin = 100;
        rootView.addView(floatingView, layoutParams);
        floatingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogView(rootView);
            }
        });
    }

    /**
     * 展示LogView
     *
     * @param rootView
     */
    public void showLogView(FrameLayout rootView) {
        FrameLayout.LayoutParams params =
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) DisplayInfoUtils.getInstance().getHeightPixels()*3/4);
        params.gravity = Gravity.BOTTOM;

        //创建logView
        LinearLayout logView = new LinearLayout(rootView.getContext());

        logView.setClickable(true);
        logView.setOrientation(LinearLayout.VERTICAL);
        logView.setBackgroundColor(Color.BLACK);
        //1. logView的关闭按钮
        TextView closeView = new TextView(rootView.getContext());
        closeView.setGravity(Gravity.CENTER);
        FrameLayout.LayoutParams logViewParam =
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        logView.addView(closeView, logViewParam);
        closeView.setText("Close");
        closeView.setBackgroundColor(Color.GRAY);
        closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logView.removeAllViews();
                rootView.removeView(logView);
            }
        });
        //2. logView的显示内容
        logView.addView(recyclerView);
        int px = (int) DisplayInfoUtils.getInstance().dp2px(10);
        recyclerView.setPadding(px,px,px,3*px);
        recyclerView.scrollToPosition(adapter.getItemCount() -1);

        rootView.addView(logView, params);
    }


    @Override
    public void print(@NonNull HiLogConfig config, int level, String tag, @NonNull String printString) {
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                // 将log展示添加到recycleView
                adapter.addItem(new HiLogMo(System.currentTimeMillis(), level, tag, printString));
                // 滚动到对应的位置
                recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
            }
        });
    }

    private static class LogAdapter extends RecyclerView.Adapter<LogViewHolder> {
        private List<HiLogMo> logs = new ArrayList<>();

        void addItem(HiLogMo logItem) {
            logs.add(logItem);
            notifyItemInserted(logs.size() - 1);
        }

        void addAll(List<HiLogMo> datas){
            logs.addAll(datas);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hilog_item, parent, false);
            return new LogViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
            HiLogMo logItem = logs.get(position);

            int color = getHighlightColor(logItem.level);
            holder.tvTag.setTextColor(color);
            holder.tvMsg.setTextColor(color);

            holder.tvTag.setText(logItem.getFlattened());
            holder.tvMsg.setText(logItem.log);
        }

        @Override
        public int getItemCount() {
            return logs.size();
        }

        /**
         * 跟进log级别获取不同的高了颜色
         *
         * @param logLevel log 级别
         * @return 高亮的颜色
         */
        private int getHighlightColor(int logLevel) {
            int highlight;
            switch (logLevel) {
                case HiLogType.V:
                    highlight = 0xffbbbbbb;
                    break;
                case HiLogType.D:
                    highlight = 0xffffffff;
                    break;
                case HiLogType.I:
                    highlight = 0xff6a8759;
                    break;
                case HiLogType.W:
                    highlight = 0xffbbb529;
                    break;
                case HiLogType.E:
                    highlight = 0xffff6b68;
                    break;
                default:
                    highlight = 0xffffff00;
                    break;
            }
            return highlight;
        }

    }

    private static class LogViewHolder extends RecyclerView.ViewHolder {

        TextView tvTag;
        TextView tvMsg;

        public LogViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMsg = itemView.findViewById(R.id.message);
            tvTag = itemView.findViewById(R.id.tag);
        }
    }
}
