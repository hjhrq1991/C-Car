package com.hjhrq1991.car.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

/**
 * @author hjhrq1991 created at 2/23/16 17 16.
 * @Package com.hjhrq1991.car.db
 * @Description:网络请求数据缓存，需保证每种缓存只存在一条数据
 */
@Table(name = "Cache")
public class CacheDB extends Model {

    /**
     * 具体缓存内容
     */
    @Column(name = "content")
    public String content;
    /**
     * 缓存时间
     */
    @Column(name = "time")
    public String time;
    /**
     * 缓存类型：1.汽油；2.天气
     */
    @Column(name = "type")
    public int type;

    /**
     * 查询某个类型缓存
     *
     * @param type
     * @return
     */
    public static CacheDB getCache(int type) {
        return new Select()
                .from(CacheDB.class)
                .where("type=?", type)
                .executeSingle();
    }

}
