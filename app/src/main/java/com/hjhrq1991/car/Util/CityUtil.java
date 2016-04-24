package com.hjhrq1991.car.Util;

import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.hjhrq1991.car.R;
import com.hjhrq1991.car.db.CityDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author hjhrq1991 created at 1/13/16 19 52.
 * @Package com.hjhrq1991.car
 * @Description:
 */
public class CityUtil {

    static String regex = "(.*?)\\,";
    static Pattern pattern = Pattern.compile(regex);

    public static void getList(Context mContext) {
        final InputStream inputStream = mContext.getResources().openRawResource(R.raw.city);

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<CityDB> mAreaList = new ArrayList<>();//需要aid(id全部)、cid(ID前7位)、pid(ID前5位)、区名称
                List<CityDB> mCityList = new ArrayList<>();//需要cid(ID前7位)、pid(ID前5位)、市名称
                List<CityDB> mProvList = new ArrayList<>();//需要pid(ID前5位)、省名称

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                BufferedReader reader = new BufferedReader(inputStreamReader);
//                StringBuffer stringBuffer = new StringBuffer("");
                String line;
                try {
                    while ((line = reader.readLine()) != null) {

                        String aid = "";
                        String prov = "";
                        String city = "";
                        String area = "";

                        Matcher matcher = pattern.matcher(line + ",");

                        ArrayList<String> list = new ArrayList<String>();

                        // 循环解析出数组，每组数据末尾加上"&"，方便解析
                        while (matcher.find()) {
                            list.add(matcher.group(1));
                        }
                        for (int i = 0; i < list.size(); i++) {
                            String string = list.get(i);
                            if (i == 0) {
                                aid = string;
                            } else if (i == 1) {
                                area = string;
                            } else if (i == 2) {
                                city = string;
                            } else if (i == 3) {
                                prov = string;
                            }
                        }

                        String pid = aid.substring(0, 5);
                        String cid = aid.substring(0, 7);

                        if (mProvList.size() < 1) {
                            CityDB pModel = new CityDB();
                            pModel.pid = pid;
                            pModel.name = prov;
                            pModel.type = 1;
                            mProvList.add(pModel);
                        } else {
                            if (!mProvList.get(mProvList.size() - 1).pid.equals(pid)) {
                                CityDB pModel = new CityDB();
                                pModel.pid = pid;
                                pModel.name = prov;
                                pModel.type = 1;
                                mProvList.add(pModel);
                            }
                        }

                        if (mCityList.size() < 1) {
                            CityDB cModel = new CityDB();
                            cModel.pid = pid;
                            cModel.cid = cid;
                            cModel.name = city;
                            cModel.type = 2;
                            mCityList.add(cModel);
                        } else {
                            if (!mCityList.get(mCityList.size() - 1).cid.equals(cid)) {
                                CityDB cModel = new CityDB();
                                cModel.pid = pid;
                                cModel.cid = cid;
                                cModel.name = city;
                                cModel.type = 2;
                                mCityList.add(cModel);
                            }
                        }

                        CityDB aModel = new CityDB();
                        aModel.pid = pid;
                        aModel.cid = cid;
                        aModel.aid = aid;
                        aModel.name = area;
                        aModel.type = 3;
                        mAreaList.add(aModel);
                    }

//                    System.out.println("hrq------省列表" + mProvList.size() + "-----市" + mCityList.size() + "-----区" + mAreaList.size());

                    ActiveAndroid.beginTransaction();
                    try {
                        for (CityDB item : mProvList) {
                            item.save();
                        }
                        for (CityDB item : mCityList) {
                            item.save();
                        }
                        for (CityDB item : mAreaList) {
                            item.save();
                        }
                        ActiveAndroid.setTransactionSuccessful();
                    } finally {
                        ActiveAndroid.endTransaction();
                    }


                } catch (IOException e) {

                } finally {
                    try {
                        reader.close();
                        inputStreamReader.close();
                        inputStream.close();
                    } catch (IOException e) {

                    }
                }
            }
        }).start();
    }
}
