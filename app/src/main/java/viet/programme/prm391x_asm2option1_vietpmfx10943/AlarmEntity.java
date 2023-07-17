package viet.programme.prm391x_asm2option1_vietpmfx10943;

import java.io.Serializable;

public class AlarmEntity implements Serializable {
    private long id;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private String message;
    private String time;
    private boolean isDone;
    private long longTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public long getLongTime() {
        return longTime;
    }

    public void setLongTime(long longTime) {
        this.longTime = longTime;
    }

    public AlarmEntity(long id, int year, int month, int day, int hour, int minute, String message, String time, boolean isDone, long longTime) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.message = message;
        this.time = time;
        this.isDone = isDone;
        this.longTime = longTime;
    }
}
