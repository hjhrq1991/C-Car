package com.hjhrq1991.car.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.hjhrq1991.tool.Util.TimeUtils;

import java.util.List;

/**
 * @author hjhrq1991 created at 1/10/16 09 40.
 * @Package com.hjhrq1991.car.db
 * @Description:
 */
@Table(name = "consume")
public class ConsumeDB extends Model {

    /**
     * 项目名称
     */
    @Column(name = "name")
    public String name;

    /**
     * 记录日期
     */
    @Column(name = "date")
    public long date;

    /**
     * 总花费
     */
    @Column(name = "total")
    public float total;

    /**
     * 单价
     */
    @Column(name = "price")
    public float price;

    /**
     * 加油量
     */
    @Column(name = "oil")
    public float oil;

    /**
     * 里程数
     */
    @Column(name = "mileage")
    public float mileage;

    /**
     * 项目类型:1.加油，2.洗车，3.停车，4.过路费，5.违章，6.保险，7维修，8贷款，9.其他
     */
    @Column(name = "type")
    public int type;

    /**
     * 备注
     */
    @Column(name = "remark")
    public String remark;

    public ConsumeDB() {
    }

    public ConsumeDB(int type) {
        this.type = type;
    }

    public static void deletAll() {
        new Delete()
                .from(ConsumeDB.class)
                .execute();
    }

    public static void delecteFromId(long id) {
        new Delete()
                .from(ConsumeDB.class)
                .where("id=?", id)
                .execute();
    }

    public static List<ConsumeDB> getAll() {
        return new Select()
                .from(ConsumeDB.class)
                .orderBy("date DESC")
                .execute();
    }

    /**
     * 查找某类型全部数据
     *
     * @param type 类型
     * @return
     */
    public static List<ConsumeDB> getFromType(int type) {
        return new Select()
                .from(ConsumeDB.class)
                .where("type=?", type)
                .orderBy("date DESC")
                .execute();
    }

    /**
     * 查找某个时间段内某类型全部数据
     *
     * @param type      类型
     * @param startTime 起始时间
     * @param endTime   结束时间
     * @return
     */
    public static List<ConsumeDB> getFromType(int type, long startTime, long endTime) {
        return new Select()
                .from(ConsumeDB.class)
                .where("type=? and date>=? and date<?", type, startTime, endTime)
                .orderBy("date DESC")
                .execute();
    }

    /**
     * 查找某个时间段内所有类型全部数据
     *
     * @param startTime 起始时间
     * @param endTime   结束时间
     * @return
     */
    public static List<ConsumeDB> getData(long startTime, long endTime) {
        if (startTime == 0 || endTime == 0) {
            return new Select()
                    .from(ConsumeDB.class)
                    .orderBy("date ASC")
                    .execute();
        } else {
            return new Select()
                    .from(ConsumeDB.class)
                    .where("date>=? and date<?", startTime, endTime)
                    .orderBy("date ASC")
                    .execute();
        }
    }

    /**
     * 查找某个时间段内所有类型全部数据
     *
     * @param startTime 起始时间
     * @param endTime   结束时间
     * @return
     */
    public static List<ConsumeDB> getDataByType(long startTime, long endTime, int type) {
        if (startTime == 0 || endTime == 0) {
            return new Select()
                    .from(ConsumeDB.class)
                    .where("type=?", type)
                    .orderBy("date ASC")
                    .execute();
        } else {
            return new Select()
                    .from(ConsumeDB.class)
                    .where("date>=? and date<? and type=?", startTime, endTime, type)
                    .orderBy("date ASC")
                    .execute();
        }
    }

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    public static ConsumeDB getFromId(long id) {
        return new Select()
                .from(ConsumeDB.class)
                .where("id=?", id)
                .executeSingle();
    }

    /**
     * 分页查找全部类型数据
     *
     * @param offset 分页限制
     * @param date   起始date
     * @return
     */
    public static List<ConsumeDB> getAll(int offset, long date) {
        if (date <= 0) {
            return new Select()
                    .from(ConsumeDB.class)
                    .limit(offset)
                    .orderBy("date DESC")
                    .execute();
        } else {
            return new Select()
                    .from(ConsumeDB.class)
                    .where("date<?", date)
                    .limit(offset)
                    .orderBy("date DESC")
                    .execute();
        }
    }

    /**
     * 分页查找某类型数据
     *
     * @param type   类型
     * @param offset 分页限制
     * @param date   起始date
     * @return
     */
    public static List<ConsumeDB> getFromType(int type, int offset, long date) {
        if (date <= 0) {
            return new Select()
                    .from(ConsumeDB.class)
                    .where("type=?", type)
                    .limit(offset)
                    .orderBy("date DESC")
                    .execute();
        } else {
            return new Select()
                    .from(ConsumeDB.class)
                    .where("type=? and date<?", type, date)
                    .limit(offset)
                    .orderBy("date DESC")
                    .execute();
        }
    }

    /**
     * 查找加油类型的数据，以date升序排序
     *
     * @return
     */
    public static List<ConsumeDB> selectGasType() {
        return new Select()
                .from(ConsumeDB.class)
                .where("type=? ", 1)
                .orderBy("date ASC")
                .execute();
    }

}
