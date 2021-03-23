package com.czy.lib_log.printer;

import android.app.Activity;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class HiViewPrinter implements HiLogPrinter {

    private RecyclerView recyclerView;
    private LogAdapter adapter;
    private ViewGroup logcatview;


    public HiViewPrinter(Activity activity) {
        if (!HiLogManager.getInstance().getConfig().enable()) {
            return;
        }
        if (activity == null) return;
        FrameLayout rootView = activity.findViewById(android.R.id.content);
        logcatview = (ViewGroup) LayoutInflater.from(activity).inflate(R.layout.logcatview, null);
        initLogCatView(rootView);
        //添加一个右下角按钮
        addFlotingView(rootView);
    }


    private String tag = "";

    public void initLogCatView(FrameLayout rootView) {
        recyclerView = logcatview.findViewById(R.id.rv_content);
        adapter = new LogAdapter();
        adapter.addAll(HiLog.historyLogs);
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        TextView closeView = logcatview.findViewById(R.id.btn_close);
        closeView.setOnClickListener(v -> {
            rootView.removeView(logcatview);
        });


        //2. 清空按钮
        TextView clearView = logcatview.findViewById(R.id.btn_clear);
        clearView.setGravity(Gravity.CENTER);
        clearView.setOnClickListener(v -> {
            adapter.getLogs().clear();
            adapter.notifyDataSetChanged();
        });

        TextInputEditText tagEdit = logcatview.findViewById(R.id.etTag);
        tagEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String trim = editable.toString().trim();
                 filterLog(currentType,trim);
            }
        });
        TabLayout tabLayout = logcatview.findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0://所有日志信息
                        adapter.getLogs().addAll(HiLog.historyLogs);
                        adapter.notifyDataSetChanged();
                        break;
                    case 1:
                        filterLog(HiLogType.V, tag);
                        break;
                    case 2:
                        filterLog(HiLogType.D, tag);
                        break;
                    case 3:
                        filterLog(HiLogType.I, tag);
                        break;
                    case 4:
                        filterLog(HiLogType.W, tag);
                        break;
                    case 5:
                        filterLog(HiLogType.E, tag);
                        break;
                    default:

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
    private int currentType = -1;
    private void filterLog(int type, String tag) {
        currentType = type;
        adapter.getLogs().clear();
        for (HiLogMo historyLog : HiLog.historyLogs) {
            if (historyLog.level == type &&(tag.isEmpty()||tag.equals(historyLog.tag))) {
                adapter.getLogs().add(historyLog);
            }
        }
        adapter.notifyDataSetChanged();
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
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) DisplayInfoUtils.getInstance().getHeightPixels() * 4 / 5);
        params.gravity = Gravity.BOTTOM;
        rootView.addView(logcatview, params);


        recyclerView.scrollToPosition(adapter.getItemCount() - 1);
    }


    @Override
    public void print(@NonNull HiLogConfig config, int level, String tag, @NonNull String printString) {
        recyclerView.post(() -> {
            // 将log展示添加到recycleView
            adapter.addItem(new HiLogMo(System.currentTimeMillis(), level, tag, printString));
            // 滚动到对应的位置
            recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
        });
    }

    private static class LogAdapter extends RecyclerView.Adapter<LogViewHolder> {
        private List<HiLogMo> logs = new ArrayList<>();

        void addItem(HiLogMo logItem) {
            logs.add(logItem);
            notifyItemInserted(logs.size() - 1);
        }

        void addAll(List<HiLogMo> datas) {
            logs.addAll(datas);
            notifyDataSetChanged();
        }

        public List<HiLogMo> getLogs() {
            return logs;
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
