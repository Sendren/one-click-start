package po;


import com.alibaba.fastjson.JSON;

public class AppInfo {
    private String name;
    private String absolutePath;
    private String id;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public AppInfo(String name, String absolutePath, String id) {
        this.name = name;
        this.absolutePath = absolutePath;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
