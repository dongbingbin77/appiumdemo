package com.example.appiumdemo;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;



public class SalaryShowAppTest {

    private final String TAG=getClass().getName();
    private String mPackageName="ctrip.android.view";

    @Test
    public void case1(){
        UiDevice mDevice=UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());//获取设备用例

        try {
            if(!mDevice.isScreenOn()){  
                mDevice.wakeUp();//唤醒屏幕
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        //mDevice.pressHome();  //按home键

        startAPP(mPackageName);  //启动app
        mDevice.waitForWindowUpdate(mPackageName, 5 * 2000);//等待app
        closeAPP(mDevice,mPackageName);//关闭app
    }

    private void startAPP(String sPackageName){
        Context mContext = InstrumentationRegistry.getContext();

        Intent myIntent = mContext.getPackageManager().getLaunchIntentForPackage(sPackageName);  //通过Intent启动app
        mContext.startActivity(myIntent);
    }

    private void closeAPP(UiDevice uiDevice,String sPackageName){
        //Log.i(TAG, "closeAPP: ");
        try {
            uiDevice.executeShellCommand("am force-stop "+sPackageName);//通过命令行关闭app
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startAPP(UiDevice uiDevice,String sPackageName, String sLaunchActivity){
        try {
            uiDevice.executeShellCommand("am start -n "+sPackageName+"/"+sLaunchActivity);//通过命令行启动app
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
