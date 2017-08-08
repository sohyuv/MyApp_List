package com.example.po.codingtest;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends BaseAdapter
{
    private Context context;
    private PackageManager packageManager;
    private List<ApplicationInfo> appList;
    private ArrayList<GetApp> listData = new ArrayList<GetApp>();

    public ListAdapter(Context context)
    {
        super();
        this.context = context;
    }

    public int getCount()
    {
        return listData.size();
    }

    public GetApp getItem(int arg0)
    {
        return listData.get(arg0);
    }

    public long getItemId(int arg0)
    {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_list_item, null);

            holder.image = (ImageView) convertView.findViewById(R.id.app_image);
            holder.name = (TextView) convertView.findViewById(R.id.app_name);
            holder.pacakge = (TextView) convertView.findViewById(R.id.app_package);
            convertView.setTag(holder);
        }

        else {
            holder = (ViewHolder) convertView.getTag();
        }

        GetApp data = listData.get(position);

        if (data.image != null) {
            holder.image.setImageDrawable(data.image);
        }

        holder.name.setText(data.appName);
        holder.pacakge.setText(data.appPackage);

        return convertView;
    }

    public void rebuild()
    {
        if (appList == null) {
            packageManager = context.getPackageManager();
            appList= packageManager.getInstalledApplications(0);
        }

        listData.clear();
        GetApp addInfo;

        for (ApplicationInfo app : appList) {
            addInfo = new GetApp();
            addInfo.image = app.loadIcon(packageManager);
            addInfo.appName = app.loadLabel(packageManager).toString();
            addInfo.appPackage = app.packageName;
            listData.add(addInfo);
        }
    }

    public void remove(int position)
    {
        listData.remove(position);
        this.notifyDataSetChanged();
    }


    private class ViewHolder
    {
        public ImageView image;
        public TextView name;
        public TextView pacakge;
    }
}
