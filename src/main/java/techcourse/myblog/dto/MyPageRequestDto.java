package techcourse.myblog.dto;

public class MyPageRequestDto {
    private String name;
    private String snsGithub;
    private String snsFacebook;

    public MyPageRequestDto(String name, String snsGithub, String snsFacebook) {
        this.name = name;
        this.snsGithub = snsGithub;
        this.snsFacebook = snsFacebook;
    }

    public String getName() {
        return name;
    }

    public String getSnsGithub() {
        return snsGithub;
    }

    public String getSnsFacebook() {
        return snsFacebook;
    }
}
