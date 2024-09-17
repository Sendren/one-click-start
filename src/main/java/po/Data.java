package po;

import com.alibaba.fastjson.JSON;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@lombok.Data
@Builder
public class Data {
    private List<AppInfo> startAllList;
    private List<AppInfo> startFavorList;


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public static Data init() {
        return Data.builder().startAllList(new ArrayList<>()).startFavorList(new ArrayList<>()).build();
    }
}
