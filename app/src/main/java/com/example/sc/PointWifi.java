package com.example.sc;

import java.util.ArrayList;
import java.util.List;

public class PointWifi
{
    public List<Element> WifiInfoList;
    public String name;
    public PointWifi()
    {

    }
    public PointWifi(List<Element> WifiInfoList,String name)
    {
        this.name=name;
        this.WifiInfoList=WifiInfoList;
    }
    public PointWifi(String WifiInfoList,String name)
    {
        this.name=name;
        List<Element> ListWifi=new ArrayList<>();
        String[] WifiInfo = WifiInfoList.split("\n");

        for (int i=0;i<WifiInfo.length;i++)
        {
            String[] Wifi = WifiInfo[i].split(" ");
            ListWifi.add(new Element(Wifi[0],Wifi[1],Wifi[1],Wifi[2]));
        }
        this.WifiInfoList=ListWifi;
    }
}
