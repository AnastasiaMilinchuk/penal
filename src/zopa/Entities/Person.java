package zopa.Entities;

import zopa.Entities.InnerEntities.Project;
import zopa.Entities.InnerEntities.UserEducation;

import java.util.Date;
import java.util.List;

/**
 * Created by milinchuk on 11/24/14.
 */
public class Person extends User{
    private Date birthday;
    private UserEducation education;
    private List<Project> projects;

    private List<String> followings;
    private List<String> skills;

    public Person(){
    }

    // Setters

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public void setFollowings(List<String> followings) {
        this.followings = followings;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setEducation(UserEducation education) {
        this.education = (UserEducation) education;
    }


    // Getters

    public Date getBirthday() {
        return birthday;
    }

    public UserEducation getEducation() {
        return education;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public List<String> getFollowings() {
        return followings;
    }

    public List<String> getSkills() {
        return skills;
    }

}