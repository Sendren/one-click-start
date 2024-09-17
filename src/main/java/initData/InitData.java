package initData;

import com.alibaba.fastjson.JSON;
import po.AppInfo;
import utils.FileUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class InitData {
//    public static void main(String[] args) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
//        AppInfo aa = new AppInfo("2023总行岗位认证考纲-开发实践-Java", "F:\\考试相关\\2023总行岗位认证考纲-开发实践-Java.pdf", LocalDateTime.now().format(formatter));
//        AppInfo bb = new AppInfo("VPN远程办公使用指南20220211", "C:\\Users\\sendren\\Downloads\\VPN远程办公使用指南20220211.docx", LocalDateTime.now().format(formatter));
//        AppInfo cc = new AppInfo("Core Temp", "E:\\software\\Core Temp\\Core Temp.exe", LocalDateTime.now().format(formatter));
//        AppInfo ee = new AppInfo("wifi密码", "C:\\Users\\sendren\\Desktop\\wifi密码.txt", LocalDateTime.now().format(formatter));
//        ArrayList<AppInfo> list = new ArrayList<>();
//        list.add(aa);
//        list.add(bb);
//        list.add(cc);
//        list.add(ee);
//        list.sort((a, b) -> {
//            LocalDateTime aTime = LocalDateTime.parse(a.getId(), formatter);
//            LocalDateTime bTime = LocalDateTime.parse(b.getId(), formatter);
//            return aTime.isAfter(bTime) ? -1 : 1;
//        });
//        String s = JSON.toJSONString(list);
//        FileUtil.saveFile(s, "data\\data.json");
//
//    }
}
