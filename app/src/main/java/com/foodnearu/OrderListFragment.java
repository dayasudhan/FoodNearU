package com.foodnearu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import com.foodnearu.R;
//import com.foodnearu.Utils.Constants;
import com.foodnearu.order.Address;

import com.foodnearu.order.HotelMenuItem;
import com.foodnearu.order.Order;
import com.foodnearu.order.OrderAdapter;
import com.foodnearu.orderDetail;
import com.foodnearu.order.Tracker;

public class OrderListFragment extends Fragment {

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ORDERS = "orders";
    private static final String TAG_CUSTOMER = "customer";
    private static final String TAG_ID = "id";
    private static final String TAG_ID2 = "_id";
    private static final String TAG_NAME = "fname";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_ADDRESS = "address";
    private static final String TAG_PHONE = "phone";
    private static final String TAG_TRACKER = "tracker";
    private static final String TAG_CURRENT_STATUS = "current_status";
    private static final String TAG_MENU = "menu";
    private static final String TAG_BILL_VALUE = "bill_value";
    private static final String TAG_DELIVERY_CHARGE = "deliveryCharge";
    private static final String TAG_TOTAL_COST = "totalCost";

    SharedPreferences pref;
    ArrayList<Order> orderList;
    // ArrayList<Order> totalorderList;
    // ArrayList<Order> todayorderList;
    String vendor_email;
    OrderAdapter adapter;
    JSONArray orderJarray;
    View rootview;
    ListView listView;

//    public OrderListFragment() {
//        this.vendor_email = vendor_email;
//    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.new_order_list, container, false);
        listView = (ListView) rootview.findViewById(R.id.listView_vendor);

        orderList = new ArrayList<Order>();
        if (((MainActivity) getActivity()).isTodayMenuselected()) {
            ((MainActivity) getActivity())
                    .setActionBarTitle("Today's Order");
        } else {
            ((MainActivity) getActivity())
                    .setActionBarTitle("Order List");
        }
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            vendor_email = bundle.getString("sessionId");
        }
//        pref = getActivity().getSharedPreferences("Khaanavali", 0);
//        vendor_email = pref.getString("email", "name");


        bindView();
        adapter = new OrderAdapter(getActivity().getApplicationContext(), R.layout.new_order_list_item, orderList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long id) {
                // TODO Auto-generated method stub
                //          Toast.makeText(getActivity().getApplicationContext(), orderList.get(position).getCustomer().getName(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), orderDetail.class);
                Gson gson = new Gson();
                String order = gson.toJson(orderList.get(position));
                intent.putExtra("order", order);
//
                startActivity(intent);
            }
        });

        setHasOptionsMenu(true);
        return rootview;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        int itemId = item.getItemId();
        String btnName = null;

        switch (itemId) {

            case R.id.menu_refresh:
                bindView();
                return true;
//            case R.id.menu_help:
//                return true;
            default:
                return false;
            // Android home
        }

        //  Snackbar.make(layout, "Button " + btnName, Snackbar.LENGTH_SHORT).show();

    }

    public void bindView() {
        orderList.clear();
        String order_url = "https://foodnearu.com/Driver/api_getDriverOrders/";
//        if(((MainActivity) getActivity()).isTodayMenuselected()) {
//            order_url = order_url.concat("today/");
//        }
        order_url = order_url.concat(vendor_email);
        new JSONAsyncTask(getActivity(), listView).execute(order_url);
    }

    public class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;

        ListView mListView;
        Activity mContex;

        public JSONAsyncTask(Activity contex, ListView gview) {
            this.mListView = gview;
            this.mContex = contex;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading, please wait");
            dialog.setTitle("Connecting server");
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {

                //------------------>>
                HttpGet httppost = new HttpGet(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);

                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = response.getEntity();


                    String data = EntityUtils.toString(entity);
                    JSONObject jObject = new JSONObject(data);
                    if (jObject.has(TAG_SUCCESS)) {
                        if (jObject.get(TAG_SUCCESS).equals(true)) {
                            if (jObject.has(TAG_ORDERS)) {

                                JSONArray jarray = jObject.getJSONArray(TAG_ORDERS);

                                for (int i = 0; i < jarray.length(); i++) {
                                    JSONObject jObject2 = jarray.getJSONObject(i);

                                    if (jObject2.has(TAG_ORDERS)) {
                                        JSONObject object = jObject2.getJSONObject(TAG_ORDERS);
                                        Order ordr = new Order();

                                        if (object.has(TAG_NAME)) {
                                            ordr.setFname(object.getString(TAG_NAME));
                                        }
                                        if (object.has(TAG_PHONE)) {
                                            ordr.setPhone(object.getString(TAG_PHONE));
                                        }
                                        if (object.has("address1")) {
                                            ordr.setAddress1(object.getString("address1"));
                                        }
                                        if (object.has("city")) {
                                            ordr.setCity(object.getString("city"));
                                        }
                                        if (object.has("zip")) {
                                            ordr.setZip(object.getString("zip"));
                                        }
                                        if (object.has("state")) {
                                            ordr.setState(object.getString("state"));
                                        }
                                        if (object.has("lat")) {
                                            ordr.setLat(object.getString("lat"));
                                        }
                                        if (object.has("long")) {
                                            ordr.setLongitude(object.getString("long"));
                                        }
                                        if (object.has("deliveryTime")) {
                                            ordr.setDeliveryTime(object.getString("deliveryTime"));
                                        }
                                        if (object.has("tip")) {
                                            ordr.setTip(object.getString("tip"));
                                        }
                                        if (object.has("created")) {
                                            ordr.setCreated(object.getString("created"));
                                        }
                                        if (object.has("preorder_date")) {
                                            ordr.setPreorder_date(object.getString("preorder_date"));
                                        }
                                        if (object.has("preorder_time")) {
                                            ordr.setPreorder_time(object.getString("preorder_time"));
                                        }
                                        if (object.has("Order_Delivery_Status")) {
                                            ordr.setOrder_Delivery_Status(object.getString("Order_Delivery_Status"));
                                        }

                                        if (object.has("payment_mode")) {
                                            ordr.setPayment_mode(object.getString("payment_mode"));
                                        }
                                        if (object.has("delivery")) {
                                            ordr.setDelivery(object.getBoolean("delivery"));
                                        }
                                        if (object.has("is_third_party")) {
                                            ordr.setIs_third_party(object.getString("is_third_party"));
                                        }
                                        if (object.has("extras_price")) {
                                            ordr.setExtras_price(object.getString("extras_price"));
                                        }
                                        if (object.has(TAG_ID)) {
                                            ordr.setId(object.getString(TAG_ID));
                                        }

                                        if (object.has(TAG_CURRENT_STATUS)) {
                                            ordr.setCurrent_status(object.getString(TAG_CURRENT_STATUS));
                                        }

//                                    if (object.has(TAG_MENU)) {
//                                        ArrayList<HotelMenuItem> hotelMenuItemList = new ArrayList<HotelMenuItem>();
//                                        JSONArray menuarr = object.getJSONArray(TAG_MENU);
//                                        for (int j = 0; j < menuarr.length(); j++) {
//                                            JSONObject menuobject = menuarr.getJSONObject(j);
//                                            HotelMenuItem menu = new HotelMenuItem();
//                                            menu.setName(menuobject.getString("name"));
//                                            menu.setNo_of_order(menuobject.getString("no_of_order"));
//                                            hotelMenuItemList.add(menu);
//                                        }
//                                        ordr.setHotelMenuItems(hotelMenuItemList);
//                                    }
//
//
//                                    if (object.has(TAG_TRACKER)) {
//                                        ArrayList<Tracker> trackerDetails = new ArrayList<Tracker>();
//                                        JSONArray trackerarr = object.getJSONArray(TAG_TRACKER);
//                                        for (int j = 0; j < trackerarr.length(); j++) {
//                                            JSONObject trackerobject = trackerarr.getJSONObject(j);
//                                            Tracker tracker = new Tracker();
//                                            tracker.setStatus(trackerobject.getString("status"));
//                                            tracker.setTime(trackerobject.getString("time"));
//                                            if (trackerobject.has("reason")) {
//                                                tracker.setReason(trackerobject.getString("reason"));
//                                            }
//                                            if (j == 0) {
//                                                Date getDate = null;
//                                                SimpleDateFormat existingUTCFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//                                                try {
//                                                    getDate = existingUTCFormat.parse(tracker.getTime());
//                                                } catch (java.text.ParseException e) {
//                                                    e.printStackTrace();
//                                                }
//                                                //   isTodayOrder = DateUtils.isToday(getDate.getTime());
//                                            }
//                                            trackerDetails.add(tracker);
//                                        }
//                                        ordr.setTrackerDetail(trackerDetails);
//                                    }
//                                    if (object.has(TAG_BILL_VALUE)) {
//                                        try {
//                                            int intValue = object.getInt(TAG_BILL_VALUE);
//                                            ordr.setBill_value(intValue);
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                    if (object.has(TAG_DELIVERY_CHARGE)) {
//                                        try {
//                                            int intValue = object.getInt(TAG_DELIVERY_CHARGE);
//                                            ordr.setDeliveryCharge(intValue);
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                    if (object.has(TAG_TOTAL_COST)) {
//                                        try {
//                                            int intValue = object.getInt(TAG_TOTAL_COST);
//                                            ordr.setTotalCost(intValue);
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }

                                    orderList.add(ordr);
                                    }
                                }
                            }

//                        if(isTodayOrder)
//                        {
//                            todayorderList.add(ordr);
//                        }
                        }
                        //Collections.sort(orderList);

                        return true;
                    }
                }

            } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            dialog.cancel();
            adapter.notifyDataSetChanged();
            if (result == false) {
                // Toast.makeText(getActivity().getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

            }


        }

    }
}
