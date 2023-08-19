package com.sp.restuarantlist;

public class Restaurant {
    private String restaurantName = "";
    private String restaurantAddress = "";
    private String restaurantTel = "";
    private String restaurantType = "";

    public String getName() {
        return restaurantName;
    }
    public void setName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getAddress() {
        return restaurantAddress;
    }
    public void setAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    public String getTelephone() {
        return restaurantTel;
    }
    public void setTelephone(String restaurantTel) {
        this.restaurantTel = restaurantTel;
    }

    public String getRestaurantType() {
        return restaurantType;
    }
    public void setRestaurantType(String restaurantType) {
        this.restaurantType = restaurantType;
    }

    @Override
    public String toString() {
        return (getName());
    }
}
