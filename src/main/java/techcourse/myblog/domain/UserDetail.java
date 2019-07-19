package techcourse.myblog.domain;

import javax.persistence.*;

@Entity
public class UserDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String snsGithub;

    @Column
    private String snsFacebook;

    public UserDetail() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
