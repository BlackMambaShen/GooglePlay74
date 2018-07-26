package http.protocol;

import com.example.liang.googleplay74.domain.CategoryInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/*
分类模块请求网络
 */
public class CategoryProtocol extends BaseProtocol<ArrayList<CategoryInfo>> {
    @Override
    public String getKey() {
        return null;
    }

    @Override
    public String getParams() {
        return null;
    }

    @Override
    public ArrayList<CategoryInfo> parseData(String result) {
        try {
            result= "[{title:'游戏',infos:[{url1:'image/category_game_0.jpg',url2:'image/category_game_1.jpg',url3:'image/category_game_2.jpg',name1:'休闲',name2:'棋牌',name3:'益智'},{url1:'image/category_game_3.jpg',url2:'image/category_game_4.jpg',url3:'image/category_game_5.jpg',name1:'射击',name2:'体育',name3:'儿童'},{url1:'image/category_game_6.jpg',url2:'image/category_game_7.jpg',url3:'image/category_game_8.jpg',name1:'网游',name2:'角色',name3:'策略'},{url1:'image/category_game_9.jpg',url2:'image/category_game_10.jpg',url3:'',name1:'经营',name2:'竞速',name3:''}]},{title:'应用',infos:[{url1:'image/category_app_0.jpg',url2:'image/category_app_1.jpg',url3:'image/category_app_2.jpg',name1:'浏览器',name2:'输入法',name3:'健康'},{url1:'image/category_app_3.jpg',url2:'image/category_app_4.jpg',url3:'image/category_app_5.jpg',name1:'效率',name2:'教育',name3:'理财'},{url1:'image/category_app_6.jpg',url2:'image/category_app_7.jpg',url3:'image/category_app_8.jpg',name1:'阅读',name2:'个性化',name3:'购物'},{url1:'image/category_app_9.jpg',url2:'image/category_app_10.jpg',url3:'image/category_app_11.jpg',name1:'资讯',name2:'生活',name3:'工具'},{url1:'image/category_app_12.jpg',url2:'image/category_app_13.jpg',url3:'image/category_app_14.jpg',name1:'出行',name2:'通讯',name3:'拍照'},{url1:'image/category_app_15.jpg',url2:'image/category_app_16.jpg',url3:'image/category_app_17.jpg',name1:'社交',name2:'影音',name3:'安全'}]}]";
            JSONArray ja=new JSONArray(result);
            ArrayList<CategoryInfo>list=new ArrayList<CategoryInfo>();
            for (int i = 0; i <ja.length() ; i++) {//遍历大分类,2次
                JSONObject jo = ja.getJSONObject(i);
                //初始化标题对象
                if (jo.has("title")){//判断是否有title这个字段
                    CategoryInfo titleInfo=new CategoryInfo();
                    titleInfo.title = jo.getString("title");
                    titleInfo.isTitle=true;
                    list.add(titleInfo);
                }
                //初始化分类对象
                if (jo.has("infos")){
                    JSONArray ja1 = jo.getJSONArray("infos");
                    for (int j = 0; j <ja1.length(); j++) {//遍历小分类
                        JSONObject jo1 = ja1.getJSONObject(j);
                        CategoryInfo info=new CategoryInfo();
                        info.name1= jo1.getString("name1");
                        info.name2= jo1.getString("name2");
                        info.name3= jo1.getString("name3");
                        info.url1= jo1.getString("url1");
                        info.url2= jo1.getString("url2");
                        info.url3= jo1.getString("url3");
                        info.isTitle=false;
                        list.add(info);
                    }
                }
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
