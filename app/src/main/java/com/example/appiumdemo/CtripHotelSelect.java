package com.example.appiumdemo;

import android.widget.TableRow;

import org.aspectj.weaver.ast.And;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
public class CtripHotelSelect {

    public static void main(String[] args){
        //AndroidDriver driver = CtripCitySelect.getDriver();

    }

    public static void mainEnter(AndroidDriver driver){
        CtripHotelSelect.goHotelList(driver);
        if(selectBrandTab(driver)){
//            while(!CtripHotelSelect.isScrollBottom(driver)){
//                expandBrand(driver);
//                CtripHotelSelect.scrollDownBrandList(driver);
//            }
            btn_sumbit_Brand(driver);
            sleep(2000);
            while(true) {
                clickAllHotel(driver);
                initHotelDetail(driver);
                while(true){

                    getHotelDetailRoomItem(driver);
                    scrollHotelDetailDown(driver);
                }
            }
        }
    }

    public static void sleep(long millis){
        try {
            Thread.sleep(millis);
        }catch (Exception ex1){
            ex1.printStackTrace();
        }
    }

    public static void goHotelList(AndroidDriver driver){
        try {
            MobileElement btn = (MobileElement) driver.findElementById("ctrip.android.hotel:id/btn_inquire");
            btn.click();
        }catch (Exception ex1){
            ex1.printStackTrace();
        }
    }

    public static boolean selectBrandTab(AndroidDriver driver){
        try {
            MobileElement filterBtn = (MobileElement) driver.findElementById("ctrip.android.hotel:id/all_list_sort_button_top_bar_layout");
            List<MobileElement> list = filterBtn.findElementsByXPath("//android.widget.LinearLayout/android.widget.TextView");
            if(list!=null&&list.size()>0){
                for(MobileElement element:list){
                    if("筛选".equals(element.getText())){
                        element.click();
                        break;
                    }
                }
            }

            try {
                Thread.sleep(3000);
            }catch (Exception ex1){
                ex1.printStackTrace();
            }


            MobileElement tabParent = (MobileElement) driver.findElementById("ctrip.android.hotel:id/tabs");

            List<MobileElement> tabList = tabParent.findElementsByXPath("//android.widget.LinearLayout/android.widget.TextView");

            for(MobileElement element:tabList){
                if("品牌".equals(element.getText())){
                    element.click();
                    return true;
                    //break;
                }
            }


        }catch (Exception ex1){
            ex1.printStackTrace();
        }
        return false;
    }

    public static boolean isScrollBottom(AndroidDriver driver){
        MobileElement groups = (MobileElement) driver.findElementById("ctrip.android.hotel:id/groups");
        try {
            List<MobileElement> title = groups.findElementsByXPath("//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView");
            if(title!=null&&title.size()>0){
                for(MobileElement tit:title){
                    if("携程服务".equals(tit.getText())){
                        return true;
                    }
                }
            }
        }catch (Exception ex1){
            ex1.printStackTrace();
        }
        return false;
    }

    public static void expandBrand(AndroidDriver driver){
        MobileElement groups = (MobileElement) driver.findElementById("ctrip.android.hotel:id/groups");
        List<MobileElement> list = groups.findElementsByXPath("//android.widget.LinearLayout");

        for(MobileElement brandSection:list){

            allBrandFilterClick(brandSection);

            List<MobileElement> brandTextList = brandSection.findElementsByXPath("//android.widget.LinearLayout/android.widget.TextView");
            if(brandTextList!=null&&brandTextList.size()>0){
                int expandIndex = 1;
                if(brandTextList.size()==3){
                    expandIndex=2;
                }
                if("展开".equals(brandTextList.get(expandIndex).getText())){
                    brandTextList.get(expandIndex).click();
                }
            }

            allBrandFilterClick(brandSection);
        }

    }

    private static void allBrandFilterClick(MobileElement brandSection){

        List<MobileElement> brandList = brandSection.findElementsByXPath("//android.support.v7.widget.RecyclerView");

        if(brandList!=null&&brandList.size()>0){
            //获取所有的item
            //List<MobileElement> brandTXT = brandList.get(0).findElementsByXPath("//android.widget.RelativeLayout/android.widget.TextView");
            List<MobileElement> brandTXTLayout = brandList.get(0).findElementsByXPath("//android.widget.RelativeLayout");
            //List<MobileElement> brandBG = brandList.get(0).findElementsByXPath("//android.widget.RelativeLayout/android.widget.ImageView");
            for(MobileElement brandLayout:brandTXTLayout){
                //System.out.println(brand.getText());
                List<MobileElement> brandTXT =brandLayout.findElementsByXPath("//android.widget.TextView");
                List<MobileElement> brandBG = brandLayout.findElementsByXPath("//android.widget.ImageView");
                if((brandBG==null||brandBG.size()==0)&&brandTXT!=null&&brandTXT.size()>0){
                    if(isWehotelBrand(brandTXT.get(0).getText())){
                        brandTXT.get(0).click();
                    }
                }
            }
//            if(brandBG!=null&&brandBG.size()>0){
//
//            }else{
//                if(brandTXT!=null&&brandTXT.size()>0){
//                    if(isWehotelBrand(brandTXT.get(0).getText())){
//                        brandTXT.get(0).click();
//                    }
//                }
//            }
        }
    }

    private static boolean isWehotelBrand(String brand){
        return true;
    }

    public static void scrollDownBrandList(AndroidDriver driver){
        (new TouchAction(driver))
                .press(PointOption.point(470,658)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3)))
                .moveTo(PointOption.point(470,610)).release()
                .perform();
    }


    public static void btn_sumbit_Brand(AndroidDriver driver){
        MobileElement btn = (MobileElement)driver.findElementById("ctrip.android.hotel:id/btn_submit");
        btn.click();
    }


    public static void clickAllHotel(AndroidDriver driver){
        //获取酒店列表的listview对象
        MobileElement listContainer = (MobileElement) driver.findElementById("ctrip.android.hotel:id/bottom_refresh_list");
        //获取酒店列表中的所有item，并且做循环判断
        List<MobileElement> allHotelItem = listContainer.findElementsByXPath("//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.RelativeLayout");
        for(MobileElement hotelItem:allHotelItem){
            List<MobileElement> hotelName = hotelItem.findElementsByXPath("//android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView");
            if(hotelName!=null&&hotelName.size()>0){
                if(hasSelectedHotel(hotelName.get(0).getText())){
                    continue;
                }else{
                    hotelItem.click();
                    return;
                }
            }
        }
    }

    private static List<String> hasSelecedHotelName = new ArrayList<>();

    private static boolean hasSelectedHotel(String hotelName){
        if(hasSelecedHotelName.contains(hotelName)){
            return true;
        }else{
            hasSelecedHotelName.add(hotelName);
            return false;
        }
    }

    public static String getCurrentHotelName(){
        return hasSelecedHotelName.get(hasSelecedHotelName.size()-1);
    }

    private static void scrollHotelListDown(AndroidDriver driver){
        (new TouchAction(driver))
                .press(PointOption.point(470,658)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3)))
                .moveTo(PointOption.point(470,610)).release()
                .perform();
    }


    private static void scrollHotelDetailDown(AndroidDriver driver){
        (new TouchAction(driver))
                .press(PointOption.point(1,658)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
                .moveTo(PointOption.point(1,550)).release()
                .perform();
    }


    static List<String> hasSelectedRoomName = new ArrayList<>();

    static MobileElement currentExpandRoom = null;
    static String currentExpandRoomName = "";

    private static void getHotelDetailRoomItem(AndroidDriver driver){

        MobileElement list_view = (MobileElement) driver.findElementById("ctrip.android.hotel:id/mListView");


        List<MobileElement> room_infos = driver.findElements(By.id("ctrip.android.hotel.detail:id/mBasicRoomItem"));

//        while (room_infos==null&&room_infos.size()==0){
//            scrollHotelDetailDown(driver);
//        }

        if(room_infos!=null&&room_infos.size()>0) {

            for(int roomIndex=0;roomIndex<room_infos.size();roomIndex++){
                MobileElement expandElement = isCanExpand(room_infos.get(roomIndex));

                if(expandElement!=null) {

                    if(isInView(expandElement)) {
                        getAllPlans(driver,list_view);
                        //可以展开
                        expandRoom(room_infos.get(roomIndex),expandElement);
                        //遍历所有的展开项目
                        getAllPlans(driver,list_view);
                    }

                }else{
                    //不能展开，获取价格
                    try {
                        if(isInView(room_infos.get(roomIndex))) {
                            MobileElement room_name_element = getRoomItemName(room_infos.get(roomIndex));
                            MobileElement room_price_element = getRoomItemPrice(room_infos.get(roomIndex));

                            String room_name = room_name_element.getText();
                            String room_price = room_price_element.getText();
                            System.out.println("不能展开，直接取信息：" + room_name + "," + room_price);
                        }
                    }catch (Exception ex1){
                        ex1.printStackTrace();
                    }

                }
            }


//            if (currentExpandRoom == null) {
//                for(int i=0;i<room_infos.size();i++) {
//
//                    MobileElement expandElement = isCanExpand(room_infos.get(0));
//                    if (expandElement != null) {
//                        //说明可以展开,展开后输出房型名称和价格
//                        currentExpandRoom = room_infos.get(0);
//                        expandRoom(driver, room_infos.get((0)));
//
//                    } else {
//                        //说明不可以展开，输出房型名称和价格
//
//                    }
//                }
//            } else {
//                if(room_infos.size()>1){
//                    expandRoom(driver, room_infos.get((1)));
//                }
//            }
        }

//        for(int i=0;i<room_infos.size();i++){
//            //获取room_info的信息
//            String room_txt="";
//            List<MobileElement> room_name = room_infos.get(i).findElements(By.id("ctrip.android.hotel.detail:id/room_item_name"));
//            if(room_name!=null&&room_name.size()>0){
//                room_txt = room_name.get(0).getText();
//                System.out.println("dongbingbin:"+room_txt);
//            }
//            try {
//                List<MobileElement> arrow = room_infos.get(i).findElements(By.id("ctrip.android.hotel.detail:id/base_room_arrow"));
//                if(arrow!=null&&arrow.size()>0&&arrow.get(0).isDisplayed()){
//                    if(!hasSelectedRoomName.contains(room_txt)) {
//                        arrow.get(0).click();
//
//                        hasSelecedHotelName.add(room_txt);
//                    }
//                }
//            }catch (Exception ex1){
//                ex1.printStackTrace();
//            }
//
//
//            List<MobileElement> plans = list_view.findElementsByXPath("//android.widget.RelativeLayout[@resource-id=\"ctrip.android.hotel.detail:id/mBasicRoomItem\"]["+(i+1)+"]/following-sibling::*");
//
//
//            for(MobileElement plan:plans){
//                if("android.widget.LinearLayout".equals(plan.getTagName())){
//                    try {
//                        String planName = plan.findElementById("ctrip.android.hotel.detail:id/room_item_name").getText();
//                        String planPrice = plan.findElementById("ctrip.android.hotel.detail:id/price_info_text_view").getText();
//                        System.out.println("dongbingbin:planName:"+planName+",planPrice:"+planPrice);
//                    }catch (Exception ex1){
//                        ex1.printStackTrace();
//                    }
//
//                }
//            }
//
//        }

//        for(MobileElement room_info:room_infos){
//            room_info.findElementsByXPath("//following-sibling::android.widget.LinearLayout[@content-desc=\"hotel_detail_room_item\"]");
//            System.out.println("dongbignbin");
//        }
    }

    public static boolean isInView(MobileElement element){
        //return element.isDisplayed()&&element.getLocation().getY()<1000&&element.getLocation().getY()>317;
        return element.getLocation().getY()<1000&&element.getLocation().getY()>317;
    }

    public static MobileElement isCanExpand(MobileElement roomElement){
        try{
            MobileElement arrow = (MobileElement)roomElement.findElementById("ctrip.android.hotel.detail:id/base_room_arrow");
            if(arrow!=null&&isInView(arrow)){
                return arrow;
            }else{
                return null;
            }
        }catch (Exception ex1){
            ex1.printStackTrace();
        }
        return null;

    }


    static MobileElement currentExpandRoomElement;

    public static void expandRoom(MobileElement roomElement,MobileElement expandElement){
        List<MobileElement> room_name = roomElement.findElementsById("ctrip.android.hotel.detail:id/room_item_name");
        String room_txt = "";
        if(room_name!=null&&room_name.size()>0){
            room_txt = room_name.get(0).getText();
            System.out.println("dongbingbin:"+CtripCitySelect.getCurrentSelectedCity());
            System.out.println("dongbingbin:"+getCurrentHotelName());
            System.out.println("dongbingbin:"+room_txt);
        }
        try {
            //MobileElement arrow = (MobileElement)roomElement.findElementById("ctrip.android.hotel.detail:id/base_room_arrow");
            if(expandElement!=null&&isInView(expandElement)){
                if(!hasSelectedRoomName.contains(room_txt)) {
                    expandElement.click();
                    currentExpandRoomName = room_txt;
                    hasSelectedRoomName.add(room_txt);
                    currentExpandRoomElement = roomElement;
                }
            }
        }catch (Exception ex1){
            ex1.printStackTrace();
        }
    }

    public static MobileElement getRoomItemName(MobileElement roomItemInfo){
        try {
            return roomItemInfo.findElementById("ctrip.android.hotel.detail:id/room_item_name");
        }catch (Exception ex1){
            ex1.printStackTrace();
        }
        return null;
    }

    public static MobileElement getRoomItemPrice(MobileElement roomItemInfo){
        try {
            return roomItemInfo.findElementById("ctrip.android.hotel.detail:id/price_info_text_view");
        }catch (Exception ex1){
            ex1.printStackTrace();
        }
        return null;
    }

//    public static void getAllPlans(AndroidDriver driver,MobileElement list_view){
//        if(currentExpandRoomElement!=null){
//            MobileElement dividerRoom = currentExpandRoomElement.findElementById("ctrip.android.hotel:id/divider_up");
//
//            int dividerY= dividerRoom.getLocation().getY();
//
//
//            List<MobileElement> splite_ids = driver.findElementsById("ctrip.android.hotel:id/room_item_split_line");
//
//            List<MobileElement> planNames =list_view.findElementsById("ctrip.android.hotel.detail:id/room_item_name");
//            List<MobileElement> planInfos = list_view.findElementsById("ctrip.android.hotel.detail:id/price_info_text_view");
//
//
//            List<MobileElement> planNames1 = new ArrayList<>();
//            List<MobileElement> planInfos1 = new ArrayList<>();
//            if(planNames.size()>0&&planInfos.size()>0&&splite_ids.size()>0) {
//                for (MobileElement element : planNames) {
//                    if (element.getLocation().getY() > dividerY&&isLowerThanSplitRoomDivider(splite_ids,element)) {
//                        planNames1.add(element);
//                    }
//                }
//
//                for (MobileElement element : planInfos) {
//                    if (element.getLocation().getY() > dividerY && isLowerThanSplitRoomDivider(splite_ids,element)) {
//                        planInfos1.add(element);
//                    }
//                }
//            }
//            if(planNames1.size()==planInfos1.size()){
//                for(int i=0;i<planInfos1.size();i++){
//                    String price = planInfos1.get(i).getText();
//                    String name = planNames1.get(i).getText();
//                    System.out.println("dongbingbin:planName:" + name + ",planPrice:" + price);
//                }
//            }else{
//                System.out.println("dongbingbin 房型和价格数量不等");
//            }
//        }
//    }
//
//    public static boolean isLowerThanSplitRoomDivider(List<MobileElement> splite_dividers,MobileElement targetElement){
//        int y = targetElement.getLocation().getY();
//        for(MobileElement divider:splite_dividers){
//            if(y<divider.getLocation().getY()){
//                return true;
//            }
//        }
//        return false;
//    }


    public static void getAllPlans(AndroidDriver driver,MobileElement list_view){

        List<MobileElement> plans = list_view.findElementsByXPath("//android.widget.LinearLayout[@content-desc=\"hotel_detail_room_item\"]");
        for(MobileElement plan:plans) {

            if ("android.widget.LinearLayout".equals(plan.getTagName())) {
                try {
                    if(isInView(plan)) {
                        String planName = plan.findElementById("ctrip.android.hotel.detail:id/room_item_name").getText();
                        String planPrice = plan.findElementById("ctrip.android.hotel.detail:id/price_info_text_view").getText();
                        System.out.println("dongbingbin:planName:" + planName + ",planPrice:" + planPrice);
                    }
                } catch (Exception ex1) {
                    ex1.printStackTrace();
                }

            }

        }
    }

    public static void initHotelDetail(AndroidDriver driver){
        MobileElement shop_look = null;
        while ((shop_look =isLookRoomExist(driver))==null){
            scrollHotelDetailDown(driver);
        }
        try {
            shop_look.click();
        }catch (Exception ex1){
            ex1.printStackTrace();
        }
    }

    public static MobileElement isLookRoomExist(AndroidDriver driver){
        try {
            MobileElement shop_look = (MobileElement) driver.findElement(By.id("ctrip.android.hotel:id/shop_look_room"));
            return shop_look;
        }catch (Exception ex1){
            ex1.printStackTrace();
        }
        return null;
    }

}