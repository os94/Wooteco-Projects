package techcourse.myblog.dto;

public class MyPageRequestDto {
    private String name;
    private String snsGithub;
    private String snsFacebook;

    public MyPageRequestDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSnsGithub() {
        return snsGithub;
    }

    public void setSnsGithub(String snsGithub) {
        this.snsGithub = snsGithub;
    }

    public String getSnsFacebook() {
        return snsFacebook;
    }

    public void setSnsFacebook(String snsFacebook) {
        this.snsFacebook = snsFacebook;
    }
}
