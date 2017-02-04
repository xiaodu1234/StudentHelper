package Bean;

import java.util.ArrayList;

/**
 * Created by duchaoqiang on 2017/1/10.
 * 每个学期的数据
 */
public class TermBean {
    private ArrayList<GradeBean> termInfo;

    public ArrayList<GradeBean> getTermInfo() {
        return termInfo;
    }

    public void setTermInfo(ArrayList<GradeBean> termInfo) {
        this.termInfo = termInfo;
    }
}

