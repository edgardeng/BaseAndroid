package com.edgardeng.data.model;

/**
 * @author Edgar Deng (http:weibo.com/edgardeng)
 * @date 16/4/9
 */
public class Repo {
    public   String  name;
    public   String  full_name;
    public   String id;
    public   String html_url;

    public String toString(){
        return "name:"+name+",full_name:"+full_name+",html_url:"+html_url;
    }

}
