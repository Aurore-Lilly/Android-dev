package com.example.comp.ccg;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entities.Pack;
import entities.UserData;


public class PurchasePacks extends AppCompatActivity {


    private ArrayList<Pack> itemArrayList;
    private ArrayList<UserData> itemUserArrayList;//List items Array
    private MyAppAdapter myAppAdapter; //Array Adapter
    private ListView listPacksView; // ListView
    private boolean success = false; // boolean
    private TextView dataNumPacks,dataGold;
    private Button button;

    //private static final String DB_URL = "jdbc:mysql://DATABASE_IP/DATABASE_NAME";
    private static final String DB_URL = "jdbc:mysql://192.168.0.101:3306/hw4"; //"jdbc:mysql://DATABASE_IP/DATABASE_NAME";
    private static final String USER = "android";
    private static final String PASS = "";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_packs);

        listPacksView = (ListView) findViewById(R.id.listPacksView); //ListView Declaration
        itemArrayList = new ArrayList<Pack>(); // Arraylist Initialization
        itemUserArrayList = new ArrayList<UserData>();
        dataNumPacks =  findViewById(R.id.pp_user_data_num_pack);
        dataGold =  findViewById(R.id.pp_user_data_gold);
        button =  findViewById(R.id.pp_list_buy_pack);

        // Calling Async Task
        SyncData orderData = new SyncData();
        orderData.execute("");

        // Calling Async Task
        UserSyncData userData = new UserSyncData();
        userData.execute("");




    }



    public void buyPacks(String collection_id) {
       String msg ="";

        Integer coll_id = Integer.parseInt(collection_id);
        buyingPacks  packs = new buyingPacks(coll_id);
        packs.execute("");


        Toast.makeText(PurchasePacks.this, msg + "", Toast.LENGTH_LONG).show();
        // Calling Async Task
        UserSyncData userData = new UserSyncData();
        userData.execute("");

    }

    // Async Task has three overrided methods,
    private class SyncData extends AsyncTask<String, String, String> {
        String msg = "Internet/DB_Credentials/Windows_FireWall_TurnOn Error, See Android Monitor in the bottom For details!";
        ProgressDialog progress;

        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
            progress = ProgressDialog.show(PurchasePacks.this, "Synchronising",
                    "ListView Loading! Please Wait...", true);
        }

        @Override
        protected String doInBackground(String... strings)  // Connect to the database, write query and add items to array list
        {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); //Connection Object
                if (conn == null) {
                    success = false;
                } else {
                    // Change below query according to your own database.
                    String query = "SELECT _id,name,price FROM collection";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs != null) // if resultset not null, I add items to itemArraylist using class created
                    {
                        while (rs.next()) {
                            try {
                                itemArrayList.add(new Pack(rs.getInt(1), rs.getString(2),rs.getDouble(3)));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }

                        msg = "Found";
                        success = true;
                    } else {
                        msg = "No Data found!";
                        success = false;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Writer writer = new StringWriter();
                e.printStackTrace(new PrintWriter(writer));
                msg = writer.toString();
                success = false;
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String msg) // disimissing progress dialoge, showing error and setting up my ListView
        {
            progress.dismiss();
            Toast.makeText(PurchasePacks.this, msg + "", Toast.LENGTH_LONG).show();
            if (success == false) {
            } else {
                try {
                    myAppAdapter = new MyAppAdapter(itemArrayList, PurchasePacks.this);
                    listPacksView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    listPacksView.setAdapter(myAppAdapter);

                } catch (Exception ex) {

                }

            }
        }
    }

    public class MyAppAdapter extends BaseAdapter         //has a class viewholder which holds
    {
        public class ViewHolder {
            TextView pp_list_pack_name;
            TextView pp_list_pack_price;
            Button pp_list_buy_pack;

            View.OnClickListener listener = new View.OnClickListener(){ // the book's action
                @Override
                public void onClick(View v) {
                    // the default action for all lines
                    buyPacks((String) v.getTag());
                }
            };
        }

        public List<Pack> parkingList;

        public Context context;
        ArrayList<Pack> arraylist;

        private MyAppAdapter(List<Pack> apps, Context context) {
            this.parkingList = apps;
            this.context = context;
            arraylist = new ArrayList<Pack>();
            arraylist.addAll(parkingList);
        }

        @Override
        public int getCount() {
            return parkingList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) // inflating the layout and initializing widgets
        {

            View rowView = convertView;
            ViewHolder viewHolder = null;
            if (rowView == null) {
                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.list_purchase_packs, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.pp_list_pack_name =  rowView.findViewById(R.id.pp_list_pack_name);
                viewHolder.pp_list_pack_price =  rowView.findViewById(R.id.pp_list_pack_price);
                viewHolder.pp_list_buy_pack =  rowView.findViewById(R.id.pp_list_buy_pack);
                rowView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            // here setting up names and images
            viewHolder.pp_list_pack_name.setText(parkingList.get(position).getName() + "");
            viewHolder.pp_list_pack_price.setText(parkingList.get(position).getPrice() + "");
            viewHolder.pp_list_buy_pack.setText("BUY");
            viewHolder.pp_list_buy_pack.setTag(Integer.toString(parkingList.get(position).getId()));
            viewHolder.pp_list_buy_pack.setOnClickListener(viewHolder.listener);

            return rowView;
        }
    }



    // Async Task has three overrided methods,
    private class UserSyncData extends AsyncTask<String, String, String> {
        String msg = "Internet/DB_Credentials/Windows_FireWall_TurnOn Error, See Android Monitor in the bottom For details!";
        ProgressDialog progress;

        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
            progress = ProgressDialog.show(PurchasePacks.this, "Synchronising",
                    "ListView Loading! Please Wait...", true);
        }

        @Override
        protected String doInBackground(String... strings)  // Connect to the database, write query and add items to array list
        {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); //Connection Object
                if (conn == null) {
                    success = false;
                } else {
                    // Change below query according to your own database.
                    String query = "SELECT t1.id,t1.current_gold,COUNT(t2.fk_user_id) AS packets_num FROM user t1 INNER JOIN user_packets t2 ON t2.fk_user_id = t1.id GROUP BY t2.fk_user_id";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs != null) // if resultset not null, I add items to itemArraylist using class created
                    {
                        while (rs.next()) {
                            try {
                                itemUserArrayList.add(new UserData(rs.getInt(1), rs.getString(2), rs.getInt(2)));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }

                        msg = "Found";
                        success = true;
                    } else {
                        msg = "No Data found!";
                        success = false;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Writer writer = new StringWriter();
                e.printStackTrace(new PrintWriter(writer));
                msg = writer.toString();
                success = false;
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String msg) // disimissing progress dialoge, showing error and setting up my ListView
        {
            progress.dismiss();
            Toast.makeText(PurchasePacks.this, msg + "", Toast.LENGTH_LONG).show();
            if (success == false) {
            } else {
                try {
                    Object data = itemUserArrayList.get(0);
                    int a = ((UserData) data).getNum_packs();
                    dataNumPacks.setText(Integer.toString(a));
                    dataGold.setText(((UserData) data).getCurrentGold());
                    dataGold.setTag(((UserData) data).getId());

                } catch (Exception ex) {

                }

            }
        }
    }





    // Async Task has three overrided methods,
    public class buyingPacks extends AsyncTask<String, String, String> {



        private int collection_id ;

        public buyingPacks(int collection_id){
            this.collection_id = collection_id;
        }

        Integer user_id = (Integer) dataGold.getTag();



        String msg = "Internet/DB_Credentials/Windows_FireWall_TurnOn Error, See Android Monitor in the bottom For details!";
        ProgressDialog progress;

        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
            progress = ProgressDialog.show(PurchasePacks.this, "Synchronising",
                    "ListView Loading! Please Wait...", true);
        }

        @Override
        protected String doInBackground(String... strings)  // Connect to the database, write query and add items to array list
        {


            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); //Connection Object
                if (conn == null) {
                    success = false;
                } else {
                    Toast.makeText(PurchasePacks.this, user_id + " - " + collection_id, Toast.LENGTH_LONG).show();
                    // Change below query according to your own database.hih
                    String query1 = "INSERT INTO user_packets(fk_user_id,fk_packet_id) VALUES('"+user_id+"','"+collection_id+"');";
                    Statement stmt1 = conn.createStatement();
                    ResultSet rs1 = stmt1.executeQuery(query1);
                    if (rs1 != null)
                    {
                        msg = "Insert";
                        success = true;
                    } else {
                        msg = "Error!";
                        success = false;
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
                Writer writer = new StringWriter();
                e.printStackTrace(new PrintWriter(writer));
                msg = writer.toString();
                success = false;
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String msg) // disimissing progress dialoge, showing error and setting up my ListView
        {
            progress.dismiss();
            Toast.makeText(PurchasePacks.this, msg + "", Toast.LENGTH_LONG).show();
            if (success == false) {
            } else {
                try {
                    Object data = itemUserArrayList.get(0);
                    int a = ((UserData) data).getNum_packs();
                    dataNumPacks.setText(Integer.toString(a));
                    dataGold.setText(((UserData) data).getCurrentGold());
                    dataGold.setTag(((UserData) data).getId());

                } catch (Exception ex) {

                }

            }
        }
    }




}
