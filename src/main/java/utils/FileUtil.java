package utils;

import com.alibaba.fastjson.JSON;
import po.AppInfo;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class FileUtil {

    /**
     * 删除指定文件夹下的文件
     *
     * @param pathName
     */
    public static void deleteDataIfExit(String pathName) {
        File baseFile = new File("data");
        File[] listFiles = baseFile.listFiles();
        List<File> files = Arrays.asList(listFiles);
        files.forEach(File::deleteOnExit);
    }

    public static void saveFile(String dataString, String savePath) {
        try (InputStream inputStream = new ByteArrayInputStream(dataString.getBytes());
             FileOutputStream outputStream = new FileOutputStream(savePath)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            System.out.println("保存完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readFile(String readPath) {
        File file = new File(readPath);
        try (InputStream inputStream = new FileInputStream(file)) {
            return readInputStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 把输入流的内容转化成字符串
     *
     * @param is
     * @return
     */
    public static String readInputStream(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int length = 0;
            byte[] buffer = new byte[1024];
            while ((length = is.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }
            is.close();
            baos.close();
            //或者用这种方法
            //byte[] result=baos.toByteArray();
            //return new String(result);
            return baos.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "获取失败";
        }
    }



    public static void main(String[] args) {
        String s1 = FileUtil.readFile("..\\data.json");
        List<AppInfo> appInfos = JSON.parseArray(s1, AppInfo.class);

        System.out.println(s1);
        System.out.println(appInfos.size());

    }
}
