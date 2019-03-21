package com.foodnearu.order;

import java.util.ArrayList;



public class Order {
    public Restaurent getRestaurent() {
        return restaurent;
    }

    public void setRestaurent(Restaurent restaurent) {
        this.restaurent = restaurent;
    }

    public String getCurrent_status() {
        return current_status;
    }

    public void setCurrent_status(String current_status) {
        this.current_status = current_status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getPreorder_date() {
        return preorder_date;
    }

    public void setPreorder_date(String preorder_date) {
        this.preorder_date = preorder_date;
    }

    public String getPreorder_time() {
        return preorder_time;
    }

    public void setPreorder_time(String preorder_time) {
        this.preorder_time = preorder_time;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrder_Delivery_Status() {
        return Order_Delivery_Status;
    }

    public void setOrder_Delivery_Status(String order_Delivery_Status) {
        Order_Delivery_Status = order_Delivery_Status;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public boolean getDelivery() {
        return delivery;
    }

    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getIs_third_party() {
        return is_third_party;
    }

    public void setIs_third_party(String is_third_party) {
        this.is_third_party = is_third_party;
    }

    public String getExtras_price() {
        return extras_price;
    }

    public void setExtras_price(String extras_price) {
        this.extras_price = extras_price;
    }

    Restaurent restaurent;
    String current_status;
    String id;

    String address1;
    String city;
    String zip;
    String state;
    String deliveryTime;
    String fname;
    String total;
    String tip;
    String created;
    String preorder_date;
    String preorder_time;
    String phone;
    String Order_Delivery_Status;
    String payment_mode;
    boolean delivery;
    String lat;
    String longitude;
    String is_third_party;
    String extras_price;

    public Order()
    {

    }



}
