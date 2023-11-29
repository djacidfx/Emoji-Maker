package com.demo.example.emoji.models;

import com.demo.example.emoji.filters.GPUImageFilterTools;


public class FilterTypeObject {
    private GPUImageFilterTools.FilterType filter;
    private int imgFilter;
    private String nameFilter;
    private int values;

    public FilterTypeObject(String str, GPUImageFilterTools.FilterType filterType, int i) {
        this.nameFilter = str;
        this.filter = filterType;
        this.values = i;
    }

    public FilterTypeObject(GPUImageFilterTools.FilterType filterType, int i) {
        this.filter = filterType;
        this.values = i;
    }

    public int getImgFilter() {
        return this.imgFilter;
    }

    public void setImgFilter(int i) {
        this.imgFilter = i;
    }

    public String getNameFilter() {
        return this.nameFilter;
    }

    public void setNameFilter(String str) {
        this.nameFilter = str;
    }

    public GPUImageFilterTools.FilterType getFilter() {
        return this.filter;
    }

    public void setFilter(GPUImageFilterTools.FilterType filterType) {
        this.filter = filterType;
    }

    public int getValues() {
        return this.values;
    }

    public void setValues(int i) {
        this.values = i;
    }
}
