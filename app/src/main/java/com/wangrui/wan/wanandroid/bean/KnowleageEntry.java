package com.wangrui.wan.wanandroid.bean;

import java.util.List;

public class KnowleageEntry {

    private String name;
    private List<ChildrenEntry> childrenEntries;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChildrenEntry> getChildrenEntries() {
        return childrenEntries;
    }

    public void setChildrenEntries(List<ChildrenEntry> childrenEntries) {
        this.childrenEntries = childrenEntries;
    }

    public static class ChildrenEntry {
        private String childrenName;
        private int childrenId;

        public String getChildrenName() {
            return childrenName;
        }

        public void setChildrenName(String childrenName) {
            this.childrenName = childrenName;
        }

        public int getChildrenId() {
            return childrenId;
        }

        public void setChildrenId(int childrenId) {
            this.childrenId = childrenId;
        }
    }
}
