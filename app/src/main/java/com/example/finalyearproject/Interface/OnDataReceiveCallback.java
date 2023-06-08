package com.example.finalyearproject.Interface;

import com.example.finalyearproject.Domain.ProductDomain;

import java.util.ArrayList;

public interface OnDataReceiveCallback {
    void onDataReceived(ArrayList list);

    void onDataReceived(int n);
}
