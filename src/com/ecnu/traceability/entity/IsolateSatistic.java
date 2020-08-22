package com.ecnu.traceability.entity;

import sun.tools.tree.NewArrayExpression;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class IsolateSatistic {
    private Integer id;

    private int jan_in;
    private int jan_out;

    private int feb_in;
    private int feb_out;

    private int mar_in;
    private int mar_out;

    private int apri_in;
    private int apri_out;

    private int may_in;
    private int may_out;

    private int june_in;
    private int june_out;

    private int july_in;
    private int july_out;

    private int aug_in;
    private int aug_out;

    private int sep_in;
    private int sep_out;

    private int oct_in;
    private int oct_out;

    private int nov_in;
    private int nov_out;

    private int dec_in;
    private int dec_out;

    private Date date;

    public IsolateSatistic() {
    }

    public IsolateSatistic(Integer id, int jan_in, int jan_out, int feb_in, int feb_out, int mar_in, int mar_out, int apri_in, int apri_out, int may_in, int may_out, int june_in, int june_out, int july_in, int july_out, int aug_in, int aug_out, int sep_in, int sep_out, int oct_in, int oct_out, int nov_in, int nov_out, int dec_in, int dec_out, Date date) {
        this.id = id;
        this.jan_in = jan_in;
        this.jan_out = jan_out;
        this.feb_in = feb_in;
        this.feb_out = feb_out;
        this.mar_in = mar_in;
        this.mar_out = mar_out;
        this.apri_in = apri_in;
        this.apri_out = apri_out;
        this.may_in = may_in;
        this.may_out = may_out;
        this.june_in = june_in;
        this.june_out = june_out;
        this.july_in = july_in;
        this.july_out = july_out;
        this.aug_in = aug_in;
        this.aug_out = aug_out;
        this.sep_in = sep_in;
        this.sep_out = sep_out;
        this.oct_in = oct_in;
        this.oct_out = oct_out;
        this.nov_in = nov_in;
        this.nov_out = nov_out;
        this.dec_in = dec_in;
        this.dec_out = dec_out;
        this.date = date;
    }

    public IsolateSatistic(int jan_in, int jan_out, int feb_in, int feb_out, int mar_in, int mar_out, int apri_in, int apri_out, int may_in, int may_out, int june_in, int june_out, int july_in, int july_out, int aug_in, int aug_out, int sep_in, int sep_out, int oct_in, int oct_out, int nov_in, int nov_out, int dec_in, int dec_out, Date date) {
        this.jan_in = jan_in;
        this.jan_out = jan_out;
        this.feb_in = feb_in;
        this.feb_out = feb_out;
        this.mar_in = mar_in;
        this.mar_out = mar_out;
        this.apri_in = apri_in;
        this.apri_out = apri_out;
        this.may_in = may_in;
        this.may_out = may_out;
        this.june_in = june_in;
        this.june_out = june_out;
        this.july_in = july_in;
        this.july_out = july_out;
        this.aug_in = aug_in;
        this.aug_out = aug_out;
        this.sep_in = sep_in;
        this.sep_out = sep_out;
        this.oct_in = oct_in;
        this.oct_out = oct_out;
        this.nov_in = nov_in;
        this.nov_out = nov_out;
        this.dec_in = dec_in;
        this.dec_out = dec_out;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getJan_in() {
        return jan_in;
    }

    public void setJan_in(int jan_in) {
        this.jan_in = jan_in;
    }

    public int getJan_out() {
        return jan_out;
    }

    public void setJan_out(int jan_out) {
        this.jan_out = jan_out;
    }

    public int getFeb_in() {
        return feb_in;
    }

    public void setFeb_in(int feb_in) {
        this.feb_in = feb_in;
    }

    public int getFeb_out() {
        return feb_out;
    }

    public void setFeb_out(int feb_out) {
        this.feb_out = feb_out;
    }

    public int getMar_in() {
        return mar_in;
    }

    public void setMar_in(int mar_in) {
        this.mar_in = mar_in;
    }

    public int getMar_out() {
        return mar_out;
    }

    public void setMar_out(int mar_out) {
        this.mar_out = mar_out;
    }

    public int getApri_in() {
        return apri_in;
    }

    public void setApri_in(int apri_in) {
        this.apri_in = apri_in;
    }

    public int getApri_out() {
        return apri_out;
    }

    public void setApri_out(int apri_out) {
        this.apri_out = apri_out;
    }

    public int getMay_in() {
        return may_in;
    }

    public void setMay_in(int may_in) {
        this.may_in = may_in;
    }

    public int getMay_out() {
        return may_out;
    }

    public void setMay_out(int may_out) {
        this.may_out = may_out;
    }

    public int getJune_in() {
        return june_in;
    }

    public void setJune_in(int june_in) {
        this.june_in = june_in;
    }

    public int getJune_out() {
        return june_out;
    }

    public void setJune_out(int june_out) {
        this.june_out = june_out;
    }

    public int getJuly_in() {
        return july_in;
    }

    public void setJuly_in(int july_in) {
        this.july_in = july_in;
    }

    public int getJuly_out() {
        return july_out;
    }

    public void setJuly_out(int july_out) {
        this.july_out = july_out;
    }

    public int getAug_in() {
        return aug_in;
    }

    public void setAug_in(int aug_in) {
        this.aug_in = aug_in;
    }

    public int getAug_out() {
        return aug_out;
    }

    public void setAug_out(int aug_out) {
        this.aug_out = aug_out;
    }

    public int getSep_in() {
        return sep_in;
    }

    public void setSep_in(int sep_in) {
        this.sep_in = sep_in;
    }

    public int getSep_out() {
        return sep_out;
    }

    public void setSep_out(int sep_out) {
        this.sep_out = sep_out;
    }

    public int getOct_in() {
        return oct_in;
    }

    public void setOct_in(int oct_in) {
        this.oct_in = oct_in;
    }

    public int getOct_out() {
        return oct_out;
    }

    public void setOct_out(int oct_out) {
        this.oct_out = oct_out;
    }

    public int getNov_in() {
        return nov_in;
    }

    public void setNov_in(int nov_in) {
        this.nov_in = nov_in;
    }

    public int getNov_out() {
        return nov_out;
    }

    public void setNov_out(int nov_out) {
        this.nov_out = nov_out;
    }

    public int getDec_in() {
        return dec_in;
    }

    public void setDec_in(int dec_in) {
        this.dec_in = dec_in;
    }

    public int getDec_out() {
        return dec_out;
    }

    public void setDec_out(int dec_out) {
        this.dec_out = dec_out;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public void updateData(int month, String flag) {

        switch (month + "_" + flag) {
            case "1_in":
                this.jan_in += 1;
                break;
            case "1_out":
                this.jan_out += 1;
                break;
            case "2_in":
                this.feb_in += 1;
                break;
            case "2_out":
                this.feb_out += 1;
                break;
            case "3_in":
                this.mar_in += 1;
                break;
            case "3_out":
                this.mar_out += 1;
                break;
            case "4_in":
                this.apri_in += 1;
                break;
            case "4_out":
                this.apri_out += 1;
                break;
            case "5_in":
                this.may_in += 1;
                break;
            case "5_out":
                this.may_out += 1;
                break;
            case "6_in":
                this.june_in += 1;
                break;
            case "6_out":
                this.june_out += 1;
                break;
            case "7_in":
                this.july_in += 1;
                break;
            case "7_out":
                this.july_out += 1;
                break;
            case "8_in":
                this.aug_in += 1;
                break;
            case "8_out":
                this.aug_out += 1;
                break;
            case "9_in":
                this.sep_in += 1;
                break;
            case "9_out":
                this.sep_out += 1;
                break;
            case "10_in":
                this.oct_in += 1;
                break;
            case "10_out":
                this.oct_out += 1;
                break;
            case "11_in":
                this.nov_in += 1;
                break;
            case "11_out":
                this.nov_out += 1;
                break;
            case "12_in":
                this.dec_in += 1;
                break;
            case "12_out":
                this.dec_out += 1;
                break;
            default:
                break;

        }

        this.date = new Date();

    }


    @Override
    public String toString() {
        String str=this.jan_in+"\t"+this.jan_out+"\t"+this.feb_in+"\t"+this.feb_out;
        return str;
    }
}
