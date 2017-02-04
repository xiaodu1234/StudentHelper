package Bean;

/**
 * Created by duchaoqiang on 2017/1/5.
 * 成绩
 */
public class GradeBean {
    private String schoolYear;//学年
    private String schoolTerm;//学期
    private String yearPoint;//学年绩点
    private String termPoint;//学期绩点
    private String couseNum;//课程编号
    private String courseName;//课程名称
    private String teachMethod;//授课方式
    private String courseType;//课程类型
    private String courseCategory;//课程类别
    private String hours;//总学时
    private String credit;//学分
    private String score;//课程分数
    private String examMethod;//考核方式
    private String status; //审核状态

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public String getSchoolTerm() {
        return schoolTerm;
    }

    public void setSchoolTerm(String schoolTerm) {
        this.schoolTerm = schoolTerm;
    }

    public String getTermPoint() {
        return termPoint;
    }

    public void setTermPoint(String termPoint) {
        this.termPoint = termPoint;
    }

    public String getYearPoint() {
        return yearPoint;
    }

    public void setYearPoint(String yearPoint) {
        this.yearPoint = yearPoint;
    }

    public String getCouseNum() {
        return couseNum;
    }

    public void setCouseNum(String couseNum) {
        this.couseNum = couseNum;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeachMethod() {
        return teachMethod;
    }

    public void setTeachMethod(String teachMethod) {
        this.teachMethod = teachMethod;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getCourseCategory() {
        return courseCategory;
    }

    public void setCourseCategory(String courseCategory) {
        this.courseCategory = courseCategory;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getExamMethod() {
        return examMethod;
    }

    public void setExamMethod(String examMethod) {
        this.examMethod = examMethod;
    }
}
