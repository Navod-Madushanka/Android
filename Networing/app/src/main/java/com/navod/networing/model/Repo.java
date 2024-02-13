package com.navod.networing.model;

import com.google.gson.annotations.SerializedName;

public class Repo {
    @SerializedName("id")
    private String id;
    @SerializedName("node_id")
    private String nodeId;
    @SerializedName("name")
    private String name;
    @SerializedName("full_name")
    private String fullName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
