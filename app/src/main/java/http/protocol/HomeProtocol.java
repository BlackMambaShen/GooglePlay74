package http.protocol;

import com.example.liang.googleplay74.domain.AppInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeProtocol extends BaseProtocol<ArrayList<AppInfo>> {
    private ArrayList<String> pictures;

    @Override
    public String getKey() {
        return "homelist0";
    }

    @Override
    public String getParams() {
        return "";//如果沒有參數，就傳空串，不要傳null
    }

    @Override
    public ArrayList<AppInfo> parseData(String result) {
        //使用JsonObject解析方式：如果遇到{}，就是JsonObject,如果遇到[]，就是jsonArray
        try {
            JSONObject jo=new JSONObject(result);
            JSONArray ja = jo.getJSONArray("list");
            //解析應用列表數據
            ArrayList<AppInfo>appInfoList=new ArrayList<AppInfo>();
            for (int i = 0; i <ja.length(); i++) {
                JSONObject jo1 = ja.getJSONObject(i);
                AppInfo info=new AppInfo();
                 info.des=jo1.getString("des");
                info.downloadUrl=jo1.getString("downloadUrl");
                info.iconUrl=jo1.getString("iconUrl");
                info.id=jo1.getString("id");
                info.name=jo1.getString("name");
                info.packageName=jo1.getString("packageName");
                info.size=jo1.getLong("size");
                info.stars= (float) jo1.getDouble("stars");
                appInfoList.add(info);
            }
            //初始化輪播條的數據
            JSONArray ja1 = jo.getJSONArray("picture");
            pictures=new ArrayList<String>();
            for (int i = 0; i <ja1.length() ; i++) {
                String pic = ja1.getString(i);
                pictures.add(pic);
            }
            return appInfoList;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<String> getPictureList(){
        return pictures;
    }
}
