package http.protocol;

import com.example.liang.googleplay74.domain.AppInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Utils.UIUtils;

/*
首页详情网络访问
 */
public class HomeDetailProtocol extends BaseProtocol<AppInfo> {

    public String packageName;
    public HomeDetailProtocol(String packageName){
        this.packageName=packageName;
    }
    @Override
    public String getKey() {
        return packageName + "/" + packageName;
    }

    @Override
    public String getParams() {
        return null;
    }

    @Override
    public AppInfo parseData(String result) {
//        result= UIUtils.getIpConfig()+"app/" + packageName
//                + "/" + packageName;
        try {
            JSONObject jo=new JSONObject(result);
            AppInfo info=new AppInfo();
            info.des=jo.getString("des");
            info.downloadUrl=jo.getString("downloadUrl");
            info.iconUrl=jo.getString("iconUrl");
            info.id=jo.getString("id");
            info.name=jo.getString("name");
            info.packageName=jo.getString("packageName");
            info.size=jo.getLong("size");
            info.stars= (float) jo.getDouble("stars");
            info.author=jo.getString("author");
            info.date=jo.getString("date");
            info.downloadNum=jo.getString("downloadNum");
            info.version=jo.getString("version");
            //解析安全信息
            ArrayList<AppInfo.SafeInfo>safe=new ArrayList<AppInfo.SafeInfo>();
            JSONArray ja = jo.getJSONArray("safe");
            for (int i = 0; i <ja.length() ; i++) {
                JSONObject jo1 = ja.getJSONObject(i);
                AppInfo.SafeInfo safeInfo=new AppInfo.SafeInfo();
                safeInfo.safeDes=jo1.getString("safeDes");
                safeInfo.safeDesUrl=jo1.getString("safeDesUrl");
                safeInfo.safeUrl=jo1.getString("safeUrl");
                safe.add(safeInfo);
            }
            info.safe=safe;
            //解析截图信息
            JSONArray ja1 = jo.getJSONArray("screen");
            ArrayList<String> screen=new ArrayList<String>();
            for (int i = 0; i <ja1.length() ; i++) {
                String pic=ja1.getString(i);
                screen.add(pic);
            }
            info.screen=screen;
            return info;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
