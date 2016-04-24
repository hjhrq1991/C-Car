package com.hjhrq1991.tool.Base;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 扩展子菜单直接使用
 * Created by hjhrq1991 on 15/6/8.
 */
public abstract class SimpleExpandableBaseAdapter<T, V> extends BaseExpandableListAdapter {
    protected Context mContext;
    protected List<T> keys;
    protected Map<T, List<V>> datas;

    public SimpleExpandableBaseAdapter(Context mContext) {
        this.mContext = mContext;
        keys = new ArrayList<>();
        datas = new HashMap<>();
    }

    @Override
    public int getGroupCount() {
        return keys.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return datas.get(keys.get(groupPosition)).size();
    }

    public T getChild(int groupPosition) {
        return keys.get(groupPosition);
    }

    @Override
    public List<V> getGroup(int groupPosition) {
        return datas.get(keys.get(groupPosition));
    }

    @Override
    public V getChild(int groupPosition, int childPosition) {
        return datas.get(keys.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return childPosition;
    }

    /**
     * 添加所有数据
     *
     * @param elem
     * @param datas
     */
    public void addAll(List<T> elem, Map<T, List<V>> datas) {
        keys.addAll(elem);
        datas.putAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 添加多个子成员
     *
     * @param groupIndex
     * @param elem
     */
    public void addAllChild(int groupIndex, List<V> elem) {
        datas.get(keys.get(groupIndex)).addAll(elem);
        notifyDataSetChanged();
    }

    /**
     * 添加单个子成员
     *
     * @param groupIndex
     * @param elem
     */
    public void addChild(int groupIndex, V elem) {
        datas.get(keys.get(groupIndex)).add(elem);
        notifyDataSetChanged();
    }

    /**
     * 删除父成员
     *
     * @param groupIndex
     */
    public void removeGroup(int groupIndex) {
        datas.remove(keys.get(groupIndex));
        keys.remove(groupIndex);
        notifyDataSetChanged();
    }

    /**
     * 删除父成员
     *
     * @param elem
     */
    public void removeGroup(T elem) {
        keys.remove(elem);
        datas.remove(elem);
        notifyDataSetChanged();
    }

    /**
     * 删除子成员
     *
     * @param groupIndex
     * @param elem
     */
    public void removeChild(int groupIndex, T elem) {
        datas.get(keys.get(groupIndex)).remove(elem);
        notifyDataSetChanged();
    }

    /**
     * 删除子成员
     *
     * @param groupIndex
     * @param childIndex
     */
    public void removeChild(int groupIndex, int childIndex) {
        datas.get(keys.get(groupIndex)).remove(childIndex);
        notifyDataSetChanged();
    }

    /**
     * 全部替换
     *
     * @param elem
     * @param elems
     */
    public void replaceAll(List<T> elem, Map<T, List<V>> elems) {
        keys.clear();
        datas.clear();
        keys.addAll(elem);
        datas.putAll(elems);
        notifyDataSetChanged();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = View.inflate(mContext, getGroupLayoutResource(), null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return getGroupView(groupPosition, isExpanded, convertView, holder);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = View.inflate(mContext, getChildLayoutResource(), null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return getChildView(groupPosition, childPosition, isLastChild, convertView, holder);
    }

    /**
     * 使用该getGroupView方法替换原来的getGroupView方法，需要子类实现
     *
     * @param groupPosition
     * @param isExpanded
     * @param convertView
     * @param viewHolder
     * @return
     */
    public abstract View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewHolder viewHolder);

    /**
     * 使用该getChildView方法替换原来的getChildView方法，需要子类实现
     *
     * @param groupPosition
     * @param childPosition
     * @param isLastChild
     * @param convertView
     * @param viewHolder
     * @return
     */
    public abstract View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewHolder viewHolder);

    /**
     * 返回对应的父group layout
     *
     * @return
     */
    public abstract int getGroupLayoutResource();

    /**
     * 返回对应的子view layout
     *
     * @return
     */
    public abstract int getChildLayoutResource();

    public class ViewHolder {
        private SparseArray<View> views = new SparseArray<View>();
        private View convertView;

        public ViewHolder(View convertView) {
            this.convertView = convertView;
        }

        @SuppressWarnings("unchecked")
        public <T extends View> T getView(int resId) {
            View v = views.get(resId);
            if (null == v) {
                v = convertView.findViewById(resId);
                views.put(resId, v);
            }
            return (T) v;
        }
    }
}
