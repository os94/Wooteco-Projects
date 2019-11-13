package sql.dto;

import java.util.Objects;

public class HobbyDto {
    private String hobby;
    private double percent;

    public HobbyDto(String hobby, double percent) {
        this.hobby = hobby;
        this.percent = percent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HobbyDto hobbyDto = (HobbyDto) o;
        return Double.compare(hobbyDto.percent, percent) == 0 &&
                Objects.equals(hobby, hobbyDto.hobby);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hobby, percent);
    }

    @Override
    public String toString() {
        return "HobbyDto{" +
                "hobby='" + hobby + '\'' +
                ", percent=" + percent +
                '}';
    }
}
