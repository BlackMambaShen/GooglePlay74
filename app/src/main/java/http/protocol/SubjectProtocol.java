package http.protocol;

import com.example.liang.googleplay74.domain.AppInfo;
import com.example.liang.googleplay74.domain.SubjectInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/*
专题网络请求
 */
public class SubjectProtocol extends BaseProtocol<ArrayList<SubjectInfo>> {
    @Override
    public String getKey() {
        return "gamelist1";
    }

    @Override
    public String getParams() {
        return null;
    }

    @Override
    public ArrayList<SubjectInfo> parseData(String result) {
        try {
            JSONArray ja=new JSONArray(result);
            ArrayList<SubjectInfo>list=new ArrayList<SubjectInfo>();
            for (int i = 0; i <ja.length() ; i++) {
                JSONObject jo1 = ja.getJSONObject(i);
                SubjectInfo info=new SubjectInfo();
                info.des=jo1.getString("des");
                info.downloadUrl=jo1.getString("downloadUrl");
                info.iconUrl=jo1.getString("iconUrl");
                info.id=jo1.getString("id");
                info.name=jo1.getString("name");
                info.packageName=jo1.getString("packageName");
                info.size=jo1.getLong("size");
                info.stars= (float) jo1.getDouble("stars");
                list.add(info);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
