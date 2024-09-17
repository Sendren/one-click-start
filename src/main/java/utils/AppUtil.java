package utils;

import java.awt.*;
import java.io.File;

public class AppUtil {
    public static void startProgram(String programPath){
        System.out.println("启动应用程序：" + programPath);
        try {
            Desktop.getDesktop().open(new File(programPath));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("应用程序：" + programPath + "不存在！");
        }

    }
}
