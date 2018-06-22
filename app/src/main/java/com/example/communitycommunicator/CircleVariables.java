package com.example.communitycommunicator;

/**
 * Created by mAni on 16/05/2018.
 */

public class CircleVariables {

    private String circlename , circledesc, circleimage	, createdby	, joinby ;
    private int id , totalmembers ;

    public CircleVariables(String circlename, String circledesc, String circleimage, String createdby, String joinby, int id, int totalmembers) {
        this.circlename = circlename;
        this.circledesc = circledesc;
        this.circleimage = circleimage;
        this.createdby = createdby;
        this.joinby = joinby;
        this.id = id;
        this.totalmembers = totalmembers;
    }

    public String getCirclename() {
        return circlename;
    }

    public void setCirclename(String circlename) {
        this.circlename = circlename;
    }

    public String getCircledesc() {
        return circledesc;
    }

    public void setCircledesc(String circledesc) {
        this.circledesc = circledesc;
    }

    public String getCircleimage() {
        return circleimage;
    }

    public void setCircleimage(String circleimage) {
        this.circleimage = circleimage;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getJoinby() {
        return joinby;
    }

    public void setJoinby(String joinby) {
        this.joinby = joinby;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotalmembers() {
        return totalmembers;
    }

    public void setTotalmembers(int totalmembers) {
        this.totalmembers = totalmembers;
    }
}
