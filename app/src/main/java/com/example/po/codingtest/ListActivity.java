package com.example.po.codingtest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class ListActivity extends Activity
{
    private ListView listView;
    private ListAdapter adapter;
    private TextView list;
    private Button reset;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView_items);
        list = (TextView) findViewById(R.id.list_length);

        adapter = new ListAdapter(this);
        listView.setAdapter(adapter);

        reset = (Button) findViewById(R.id.reset_Btn);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTask();
                Toast.makeText(ListActivity.this, "List가 초기화 되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        startTask();
    }

    private void startTask() {
        new AppTask().execute();
    }

    private void createDialog() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder Dialog = new AlertDialog.Builder(ListActivity.this);
                Dialog.setTitle(adapter.getItem(position).appName);
                Dialog.setIcon(android.R.drawable.ic_input_delete);
                GetApp item = adapter.getItem(position);
                String data = "내용\n" + item.appPackage;
                Dialog.setMessage(data);

                Dialog.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.remove(position);
                        listView.clearChoices();
                        Toast.makeText(ListActivity.this, "남은 항목 수 = " + adapter.getCount(), Toast.LENGTH_SHORT).show();
                    }
                });

                Dialog.setNegativeButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.rebuild();
                    }
                });
                Dialog.create().show();
            }
        });
    }

    private class AppTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            adapter.rebuild();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            adapter.notifyDataSetChanged();

            list.setText("총 항목 수 : " + adapter.getCount());
            createDialog();
        }
    };
}
