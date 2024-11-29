package infrastructure.repositories.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Optional;

@Entity(name="candidates")
public class Candidate extends PanacheEntityBase {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String photo;
    @Column(name="given_name")
    private String givenName;
    @Column(name="family_name")
    private String familyName;
    private String email;
    private String phone;
    @Column(name="job_title")
    private String jobTitle;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public static Candidate fromDomain(domain.Candidate domain) {
        Candidate entity = new Candidate();

        entity.id = domain.id();
        entity.photo = domain.photo().orElse(null);
        entity.givenName = domain.givenName();
        entity.familyName = domain.familyName();
        entity.email = domain.email();
        entity.phone = domain.phone().orElse(null);
        entity.jobTitle = domain.jobTitle().orElse(null);

        return entity;
    }

    public static domain.Candidate toDomain(Candidate entity) {
        return new domain.Candidate(
                entity.id,
                Optional.ofNullable(entity.photo),
                entity.givenName,
                entity.familyName,
                entity.email,
                Optional.ofNullable(entity.phone),
                Optional.ofNullable(entity.jobTitle)
        );
    }

}
