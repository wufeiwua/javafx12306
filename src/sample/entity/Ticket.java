package sample.entity;

/**
 * 余票信息
 */
public class Ticket {
    /** 车次 */
    private String cc;
    /** 始发站名字 */
    private String sfmz;
    /** 到达站名字 */
    private String ddmz;
    /** 出发时间 */
    private String cfsj;
    /** 到达时间 */
    private String ddsj;
    /** 历时 */
    private String lishi;
    /** 高级软卧 */
    private String gjrw;
    /** 软卧 */
    private String rw;
    /** 无座 */
    private String wz;
    /** 硬卧 */
    private String yw;
    /** 硬座 */
    private String yz;
    /** 二等座 */
    private String ed;
    /** 一等座 */
    private String yd;
    /** 商务座，特等座 */
    private String td;

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getSfmz() {
        return sfmz;
    }

    public void setSfmz(String sfmz) {
        this.sfmz = sfmz;
    }

    public String getDdmz() {
        return ddmz;
    }

    public void setDdmz(String ddmz) {
        this.ddmz = ddmz;
    }

    public String getCfsj() {
        return cfsj;
    }

    public void setCfsj(String cfsj) {
        this.cfsj = cfsj;
    }

    public String getDdsj() {
        return ddsj;
    }

    public void setDdsj(String ddsj) {
        this.ddsj = ddsj;
    }

    public String getLishi() {
        return lishi;
    }

    public void setLishi(String lishi) {
        this.lishi = lishi;
    }

    public String getGjrw() {
        return gjrw;
    }

    public void setGjrw(String gjrw) {
        this.gjrw = gjrw;
    }

    public String getRw() {
        return rw;
    }

    public void setRw(String rw) {
        this.rw = rw;
    }

    public String getWz() {
        return wz;
    }

    public void setWz(String wz) {
        this.wz = wz;
    }

    public String getYw() {
        return yw;
    }

    public void setYw(String yw) {
        this.yw = yw;
    }

    public String getYz() {
        return yz;
    }

    public void setYz(String yz) {
        this.yz = yz;
    }

    public String getEd() {
        return ed;
    }

    public void setEd(String ed) {
        this.ed = ed;
    }

    public String getYd() {
        return yd;
    }

    public void setYd(String yd) {
        this.yd = yd;
    }

    public String getTd() {
        return td;
    }

    public void setTd(String td) {
        this.td = td;
    }

    @Override
    public String toString() {
        return String.format("%-8s\t%-8s\t%-8s\t%-8s\t%-8s\t%-8s\t%-8s\t%-8s\t%-8s\t%-8s\t%-8s\t%-8s\t%-8s\t%-8s",
                cc, sfmz, ddmz, cfsj, ddsj, lishi, gjrw, rw, wz, yw, yz, ed, yd, td);
    }
}
