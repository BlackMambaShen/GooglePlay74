package http.protocol;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import Utils.IOUtils;
import Utils.StringUtils;
import Utils.UIUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*
访问网络的基类
 */
public abstract class BaseProtocol<T> {
    public T getData(int index){
        //先判断是否有缓存，有的话就加载缓存
        String result = getCache(index);
        if (StringUtils.isEmpty(result)){//如果没有缓存
            //请求服务器
             result = getDataFromServer(index);
        }
        //开始解析
        if (result!=null){
            T data = parseData(result);
            return data;
        }
        return null;
    }

    //从网络获取数据
    //index表示的是从哪个位置开始返回20条数据,用于分页
    private String getDataFromServer(int index) {
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(UIUtils.getIpConfig()+"app/"+getKey()).build();
        try {
            Response response = client.newCall(request).execute();
            String result = response.body().string();
            if (result!=null){
                System.out.println("访问结果:"+result);
            }
            //写缓存
            if (StringUtils.isEmpty(result)){
                setCache(index,result);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取网络的关键词，子类必须实现
    public abstract String getKey();

    //获取网络链接参数，子类必须实现
    public abstract String getParams();

    //写缓存
    //以URL为key，以json为value
    public void setCache(int index,String json){
        //url文件名，json文件内容，保存在本地
        File cacheDir = UIUtils.getContext().getCacheDir();//本应用的缓存文件夹
        //生成缓存文件
        File cacheFile=new File(cacheDir,getKey()+"?index="+index+getParams());
        FileWriter writer=null;
        try {
             writer=new FileWriter(cacheFile);
             //缓存失效的截止时间
             long deadline=System.currentTimeMillis()+30*60*1000;//半个小时有效期
             writer.write(deadline+"\n");//在第一行写入缓存时间,换行
             writer.write(json);//写入json
             writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.close(writer);
        }
    }

    public String getCache(int index){
        //url文件名，json文件内容，保存在本地
        File cacheDir = UIUtils.getContext().getCacheDir();//本应用的缓存文件夹
        //生成缓存文件
        File cacheFile=new File(cacheDir,getKey()+"?index="+index+getParams());
        //判断缓存是否存在
        if (cacheFile.exists()){
            //判断缓存是否有效
            BufferedReader reader=null;
            try {
                reader=new BufferedReader(new FileReader(cacheFile));
                String deadline = reader.readLine();
                long deadtime = Long.parseLong(deadline);
                if (System.currentTimeMillis()<deadtime){//当前时间小于截止时间，说明缓存有效
                    //说明缓存有效
                    StringBuffer sb=new StringBuffer();
                    String line;
                    while ((line=reader.readLine())!=null){
                        //继续往下读
                        sb.append(line);
                    }
                    return sb.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                IOUtils.close(reader);
            }
        }
        return null;
    }

    //解析数据,子类必须实现
    public abstract T parseData(String result);
}
