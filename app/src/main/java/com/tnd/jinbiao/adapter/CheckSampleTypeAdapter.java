package com.tnd.jinbiao.adapter;

import java.util.List;


import com.tnd.jinbiao.model.SampleTypeModel;
import com.tnd.multifuction.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class CheckSampleTypeAdapter extends BaseAdapter {

    private Context context = null;
    private List<SampleTypeModel> list;

    public CheckSampleTypeAdapter(Context context, List<SampleTypeModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (list != null && list.size() > 0) {
            return list.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        if (list != null && list.size() > 0) {
            return list.get(arg0);
        } else {
            return 0;
        }

    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {

        ViewHolder holer = null;

        if (arg1 == null) {
            holer = new ViewHolder();
            arg1 = View.inflate(context, R.layout.check_persion_listview_item,
                    null);
            holer.box = (CheckBox) arg1
                    .findViewById(R.id.check_persion_checkbox);
            holer.name = (TextView) arg1.findViewById(R.id.check_persion_name);
            arg1.setTag(holer);
        }

        holer = (ViewHolder) arg1.getTag();
        if (list != null && list.size() > 0) {
            if (list.get(arg0) != null) {
                final SampleTypeModel model = list.get(arg0);
                if (model != null) {
                    holer.box.setChecked(model.is_check);
                    if (model.getName().toString() != null && model.getName().toString().length() > 0) {
                        holer.name.setText(model.getName());
                    }

                }


                holer.box.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                        model.setIs_check(arg1);
                    }
                });


            }
        }


        return arg1;
    }

    static class ViewHolder {
        CheckBox box;
        TextView name;
    }
}
