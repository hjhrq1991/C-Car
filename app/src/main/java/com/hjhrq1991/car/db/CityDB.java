package com.hjhrq1991.car.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * @author hjhrq1991 created at 1/13/16 19 53.
 * @Package com.hjhrq1991.car.Model
 * @Description:
 */
@Table(name = "city")
public class CityDB extends Model {

    /**
     * 名称
     */
    @Column(name = "name")
    public String name;
    /**
     * 省ID
     */
    @Column(name = "pid")
    public String pid;
    /**
     * 市ID
     */
    @Column(name = "cid")
    public String cid;
    /**
     * 区域ID
     */
    @Column(name = "aid")
    public String aid;
    /**
     * 类型：1：省份，2：市，3：县/区
     */
    @Column(name = "type")
    public int type;

    public CityDB() {
    }

    public CityDB(String name, String pid, String cid, String aid, int type) {
        this.name = name;
        this.pid = pid;
        this.cid = cid;
        this.aid = aid;
        this.type = type;
    }

    public static List<CityDB> getAll() {
        return new Select()
                .from(CityDB.class)
                .execute();
    }

    public static List<CityDB> getProv() {
        return new Select()
                .from(CityDB.class)
                .where("type=?", 1)
                .execute();
    }

    public static List<CityDB> getCity(String pid) {
        return new Select()
                .from(CityDB.class)
                .where("pid = ? and type=?", pid, 2)
                .execute();
    }

    public static List<CityDB> getArea(String cid) {
        return new Select()
                .from(CityDB.class)
                .where("cid = ? and type=?", cid, 3)
                .execute();
    }

    /**
     * 通过pid查询省份
     *
     * @param pid
     * @return
     */
    public static CityDB getProvbyPid(String pid) {
        return new Select()
                .from(CityDB.class)
                .where("pid = ? and type=?", pid, 1)
                .executeSingle();
    }

    /**
     * 通过cid查询市
     *
     * @param cid
     * @return
     */
    public static CityDB getCityByCid(String cid) {
        return new Select()
                .from(CityDB.class)
                .where("cid = ? and type=?", cid, 2)
                .executeSingle();
    }

    /**
     * 通过aid查询县区
     *
     * @param aid
     * @return
     */
    public static CityDB getAreaByAid(String aid) {
        return new Select()
                .from(CityDB.class)
                .where("aid = ? and type=?", aid, 3)
                .executeSingle();
    }

    /**
     * 清空数据库
     */
    public static void deletAll() {
        new Delete()
                .from(CityDB.class)
                .execute();
    }
}
