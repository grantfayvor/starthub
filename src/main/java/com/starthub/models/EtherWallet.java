package com.starthub.models;

/**
 * Created by Harrison on 6/4/2018.
 */
public class EtherWallet {

    private String address;
    private String password;
    private String pathToWallet;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPathToWallet() {
        return pathToWallet;
    }

    public void setPathToWallet(String pathToWallet) {
        this.pathToWallet = pathToWallet;
    }
}
