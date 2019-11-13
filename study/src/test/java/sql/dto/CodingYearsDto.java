package sql.dto;

import java.util.Objects;

public class CodingYearsDto {
    private String DeveloperType;
    private double AvgOfYears;

    public CodingYearsDto(String developerType, double avgOfYears) {
        DeveloperType = developerType;
        AvgOfYears = avgOfYears;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodingYearsDto that = (CodingYearsDto) o;
        return Double.compare(that.AvgOfYears, AvgOfYears) == 0 &&
                Objects.equals(DeveloperType, that.DeveloperType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(DeveloperType, AvgOfYears);
    }

    @Override
    public String toString() {
        return "CodingYearsDto{" +
                "DeveloperType='" + DeveloperType + '\'' +
                ", AvgOfYears=" + AvgOfYears +
                '}';
    }
}
